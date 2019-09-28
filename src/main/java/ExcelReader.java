import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 원진
 * @contact godjin519@gmail.com
 */
public class ExcelReader
{
    private Workbook workbook = null;
    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    /**
     * 생성자
     */
    public ExcelReader() {}

    /**
     * 생성자<br>
     * 경로에 위치한 엑셀 파일을 연다.
     * @param path 경로
     */
    public ExcelReader(final String path) throws IOException, InvalidFormatException
    {
        workbook = WorkbookFactory.create(new File(path));
    }

    /**
     * 경로에 위치한 엑셀 파일을 연다.
     * @param path 경로
     */
    public void open(final String path) throws IOException, InvalidFormatException
    {
        if (workbook != null)
            close();

        workbook = WorkbookFactory.create(new File(path));
    }

    /**
     * 엑셀 파일을 닫는다.
     */
    public void close()
    {
        try
        {
            workbook.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        workbook = null;
    }

    /**
     * 행 단위로 워크시트 데이터를 읽고, {@link Table} 인스턴스에 담아서 반환한다.
     * @param sheetIndex 워크시트 인덱스(0부터 시작)
     * @param recordClass 레코드 자료구조 클래스 정보
     * @param usingFirstColumnAsId 첫번째 열의 값을 레코드 id로 사용할지 여부(false: 행 번호)
     * @param <T> 레코드 자료구조
     * @return 워크시트 데이터가 담긴 {@link Table} 인스턴스
     */
    public <T> Table<T> read(final int sheetIndex, final Class<T> recordClass, final boolean usingFirstColumnAsId) throws IllegalAccessException
    {
        Table<T> table = new Table<>(recordClass);

        final Sheet SHEET = workbook.getSheetAt(sheetIndex);
        final int FIRST_ROW_NUM = SHEET.getFirstRowNum();
        final Row FIRST_ROW = SHEET.getRow(FIRST_ROW_NUM);

        readHeaders(FIRST_ROW, table);

        readContents(
                SHEET,
                FIRST_ROW_NUM + 1,
                FIRST_ROW.getFirstCellNum(),
                FIRST_ROW.getLastCellNum() - 1,
                usingFirstColumnAsId,
                table,
                recordClass);

        return table;
    }

    /**
     * 헤더를 읽고, 테이블에 저장한다.
     * @param headerRow 헤더가 위치한 행
     * @param table 테이블
     */
    private void readHeaders(final Row headerRow, Table<?> table)
    {
        headerRow.forEach(cell -> table.addHeader(DATA_FORMATTER.formatCellValue(cell)));
    }

    /**
     * 행 단위로 레코드 객체를 만들고, 컨텐츠를 저장한다.<br>
     * 테이블에 id, 레코드를 반영한다.
     * @param sheet 워크 시트
     * @param firstRecordRowIdx 첫번째 레코드가 위치한 행의 인덱스
     * @param firstColumnIdx 첫번째 열의 인덱스
     * @param lastColumnIdx 마지막 열의 인덱스
     * @param usingFirstColumnAsId 첫번째 열의 값을 레코드 id로 사용할지 여부
     * @param table 테이블
     * @param recordClass 레코드 자료구조 클래스 정보
     * @param <T> 레코드 자료구조
     */
    private <T> void readContents(final Sheet sheet, final int firstRecordRowIdx, final int firstColumnIdx, final int lastColumnIdx, final boolean usingFirstColumnAsId, final Table<T> table, final Class<T> recordClass) throws IllegalAccessException
    {
        List<Field> contents = GenericClass.getDeclaredFields(recordClass);

        for (int rowNum = firstRecordRowIdx; rowNum <= sheet.getLastRowNum(); ++rowNum)
        {
            final Row ROW = sheet.getRow(rowNum);

            T record = null;
            try
            {
                record = GenericClass.getInstance(recordClass);
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }

            String id = Integer.toString(rowNum);

            if (usingFirstColumnAsId)
                id = readRecordWithId(ROW, record, contents, firstColumnIdx, lastColumnIdx);
            else
                readRecordWithoutId(ROW, record, contents, firstColumnIdx, lastColumnIdx);

            table.set(id, record);
        }
    }

    /**
     * 첫번째 열의 값을 id로 사용한다.<br>
     * 두번째 열부터 값을 컨텐츠로 저장한다.
     * @param row 레코드가 위치한 행
     * @param record 레코드
     * @param contents 컨텐츠
     * @param firstColumnIdx 첫번째 열의 인덱스
     * @param lastColumnIdx 마지막 열의 인덱스
     * @param <T> 레코드 자료구조
     * @return id
     */
    private <T> String readRecordWithId(final Row row, T record, List<Field> contents, final int firstColumnIdx, final int lastColumnIdx) throws IllegalAccessException
    {
        for (int contentIdx = 0, columnIdx = (firstColumnIdx + 1); columnIdx <= lastColumnIdx; ++contentIdx, ++columnIdx)
            contents.get(contentIdx)
                    .set(record, DATA_FORMATTER.formatCellValue(row.getCell(columnIdx)));

        return DATA_FORMATTER.formatCellValue(row.getCell(firstColumnIdx));
    }

    /**
     * 첫번째 열의 값을 id로 사용하지 않는다.<br>
     * 첫번째 열부터 값을 컨텐츠로 저장한다.
     * @param row 레코드가 위치한 행
     * @param record 레코드
     * @param contents 컨텐츠
     * @param firstColumnIdx 첫번째 열의 인덱스
     * @param lastColumnIdx 마지막 열의 인덱스
     * @param <T> 레코드 자료구조
     */
    private <T> void readRecordWithoutId(final Row row, T record, List<Field> contents, final int firstColumnIdx, final int lastColumnIdx) throws IllegalAccessException
    {
        for (int contentIdx = 0, columnIdx = firstColumnIdx; columnIdx <= lastColumnIdx; ++contentIdx, ++columnIdx)
            contents.get(contentIdx)
                    .set(record, DATA_FORMATTER.formatCellValue(row.getCell(columnIdx)));
    }
}
