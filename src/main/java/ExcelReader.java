import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelReader
{
    private Workbook workbook = null;
    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    /**
     * 생성자
     * @param path 파일 경로
     */
    public ExcelReader(final String path)
    {
        setPath(path);
    }

    /**
     * 파일 경로를 설정한다.
     * @param path 파일 경로
     */
    public void setPath(final String path)
    {
        if (workbook != null)
            closeWorkbook();

        openWorkbook(path);
    }

    /**
     * 행 단위로 워크시트 데이터를 읽고, {@link Table} 인스턴스에 담아서 반환한다.
     * @param sheetIndex 워크시트 인덱스(0부터 시작)
     * @param recordClass 레코드 자료구조 클래스 정보
     * @param usingFirstColumnAsId 첫번째 열의 값을 레코드 id로 사용할지 여부(false: 행 번호)
     * @param <T> 레코드 자료구조
     * @return 워크시트 데이터가 담긴 {@link Table} 인스턴스
     */
    public <T> Table<T> read(final int sheetIndex, final Class<T> recordClass, final boolean usingFirstColumnAsId)
    {
        Table<T> table = new Table<>(recordClass);

        final Sheet SHEET = workbook.getSheetAt(sheetIndex);
        final int FIRST_ROW_NUM = SHEET.getFirstRowNum();
        final Row FIRST_ROW = SHEET.getRow(FIRST_ROW_NUM);

        readHeaders(FIRST_ROW, table);
        readContents(
                SHEET, FIRST_ROW_NUM + 1, FIRST_ROW.getFirstCellNum(), FIRST_ROW.getLastCellNum(),
                usingFirstColumnAsId, table, recordClass);

        return table;
    }

    private void readHeaders(final Row firstRow, Table<?> table)
    {
        firstRow.forEach(cell -> table.addHeader(DATA_FORMATTER.formatCellValue(cell)));
    }

    private <T> void readContents(
            final Sheet sheet, final int secondRowIdx, final int firstColumnIdx, final int lastColumnIdx,
            final boolean usingFirstColumnAsId, final Table<T> table, final Class<T> recordClass)
    {
        List<Field> contents = GenericClass.getDeclaredFields(recordClass);

        for (int rowNum = secondRowIdx; rowNum <= sheet.getLastRowNum(); ++rowNum)
        {
            final Row ROW = sheet.getRow(rowNum);
            T record = GenericClass.getInstance(recordClass);
            String id = Integer.toString(rowNum);

            if (usingFirstColumnAsId)
                id = readRecordWithId(ROW, record, contents, firstColumnIdx, lastColumnIdx);
            else
                readRecordWithoutId(ROW, record, contents, firstColumnIdx, lastColumnIdx);

            table.set(id, record);
        }
    }

    private <T> String readRecordWithId(final Row row, T record, List<Field> contents, final int firstColumnNum, final int lastColumnNum)
    {
        for (int contentIdx = 0, cellNum = (firstColumnNum + 1); cellNum < lastColumnNum; ++contentIdx, ++cellNum)
            setContent(contents.get(contentIdx), record, DATA_FORMATTER.formatCellValue(row.getCell(cellNum)));

        return DATA_FORMATTER.formatCellValue(row.getCell(firstColumnNum));
    }

    private <T> void readRecordWithoutId(final Row row, T record, List<Field> contents, final int firstColumnNum, final int lastColumnNum)
    {
        for (int contentIdx = 0, cellNum = firstColumnNum; cellNum < lastColumnNum; ++contentIdx, ++cellNum)
            setContent(contents.get(contentIdx), record, DATA_FORMATTER.formatCellValue(row.getCell(cellNum)));
    }

    private <T> void setContent(Field content, final T record, final String cellValue)
    {
        try
        {
            if (!cellValue.isEmpty())
                content.set(record, cellValue);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private void openWorkbook(final String path)
    {
        try
        {
            workbook = WorkbookFactory.create(new File(path));
        }
        catch (IOException | InvalidFormatException e)
        {
            e.printStackTrace();
        }
    }

    private void closeWorkbook()
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
}
