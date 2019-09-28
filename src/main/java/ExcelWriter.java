import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 원진
 * @contact godjin519@gmail.com
 */
public class ExcelWriter
{
    private Map<String, Table<?>> sheetTableMap = new LinkedHashMap<>();

    /**
     * 생성할 워크시트 명과 데이터를 기록할 테이블을 설정한다.
     * @param sheetName 워크시트 명
     * @param table 테이블
     */
    public void set(final String sheetName, final Table<?> table)
    {
        sheetTableMap.put(sheetName, table);
    }

    /**
     * 경로에 위치한 엑셀 파일에 설정 순서대로 워크시트를 만들고 테이블 데이터를 기록한다.
     * @param path 경로
     */
    public void write(final String path) throws IOException, IllegalAccessException
    {
        Workbook workbook = new XSSFWorkbook();
        writeTables(workbook);

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);

        fileOutputStream.close();
        workbook.close();

        sheetTableMap.clear();
    }

    /**
     * 엑셀 파일에 설정 순서대로 워크시트를 만들고 테이블 데이터를 기록한다.
     * @param workbook 엑셀 파일
     */
    private void writeTables(Workbook workbook) throws IllegalAccessException
    {
        for (String sheetName : sheetTableMap.keySet())
        {
            Sheet sheet = workbook.createSheet(sheetName);

            final Table<?> TABLE = sheetTableMap.get(sheetName);
            final List<String> HEADERS = TABLE.getHeaders();
            writeHeaders(sheet, HEADERS);

            final List<Field> CONTENTS = GenericClass.getDeclaredFields(TABLE.getRecordClass());

            int rowNum = 1;
            for (final String ID : TABLE.getIds())
            {
                Row row = sheet.createRow(rowNum++);
                final Object RECORD = TABLE.getRecord(ID);

                if (HEADERS.size() > CONTENTS.size())
                    writeRecordWithId(row, CONTENTS, ID, RECORD);
                else
                    writeRecordWithoutId(row, CONTENTS, ID, RECORD);
            }
        }
    }

    /**
     * 헤더를 워크시트에 기록한다.
     * @param sheet 워크시트
     * @param headers 헤더
     */
    private void writeHeaders(final Sheet sheet, final List<String> headers)
    {
        Row firstRow = sheet.createRow(0);

        for (int headerIdx = 0; headerIdx < headers.size(); ++headerIdx)
            firstRow.createCell(headerIdx).setCellValue(headers.get(headerIdx));
    }

    /**
     * id를 첫번째 열에 기록한다.<br>
     * 컨텐츠를 두번째 열부터 기록한다.
     * @param row 레코드가 위치한 행
     * @param contents 컨텐츠
     * @param id id
     * @param record 레코드
     */
    private void writeRecordWithId(final Row row, final List<Field> contents, final String id, final Object record) throws IllegalAccessException
    {
        row.createCell(0).setCellValue(id);

        for (int contentIdx = 0, columnIdx = 1; contentIdx < contents.size(); ++contentIdx, ++columnIdx)
            row.createCell(columnIdx).setCellValue(String.valueOf(contents.get(contentIdx).get(record)));
    }

    /**
     * id를 첫번째 열에 기록하지 않는다.<br>
     * 컨텐츠를 첫번째 열부터 기록한다.
     * @param row 레코드가 위치한 행
     * @param contents 컨텐츠
     * @param id id
     * @param record 레코드
     */
    private void writeRecordWithoutId(final Row row, final List<Field> contents, final String id, final Object record) throws IllegalAccessException
    {
        for (int contentIdx = 0; contentIdx < contents.size(); ++contentIdx)
            row.createCell(contentIdx).setCellValue(String.valueOf(contents.get(contentIdx).get(record)));
    }
}
