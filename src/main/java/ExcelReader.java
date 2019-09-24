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

    public ExcelReader() {}

    public ExcelReader(final String path)
    {
        setPath(path);
    }

    public void setPath(final String path)
    {
        if (workbook != null)
            closeWorkbook();

        openWorkbook(path);
    }

    /**
     * 행 단위로 워크시트 데이터를 읽고, {@link Table} 인스턴스에 담아서 반환한다.
     * @param sheetIndex 워크시트 인덱스(0부터 시작)
     * @param dataStructureClass 자료구조 클래스
     * @param usingFirstColumnAsId 첫번째 열의 값을 레코드 id로 사용할지 여부(false: 행 번호)
     * @param <T> 자료구조
     * @return 워크시트 데이터가 담긴 {@link Table} 인스턴스
     */
    public <T> Table<T> read(final int sheetIndex, final Class<T> dataStructureClass, final boolean usingFirstColumnAsId)
    {
        Table<T> table = new Table<>();

        TypeWrapper<T> DATA_STRUCTURE = new TypeWrapper<>(dataStructureClass);
        List<Field> contents = DATA_STRUCTURE.getDeclaredFields();

        final Sheet SHEET = workbook.getSheetAt(sheetIndex);
        final int FIRST_ROW_NUM = SHEET.getFirstRowNum();
        final Row FIRST_ROW = SHEET.getRow(FIRST_ROW_NUM);

        FIRST_ROW.forEach(CELL -> table.addField(DATA_FORMATTER.formatCellValue(CELL)));

        final int FIRST_COLUMN_NUM = FIRST_ROW.getFirstCellNum();
        final int LAST_COLUMN_NUM = FIRST_ROW.getLastCellNum();

        for (int rowNum = (FIRST_ROW_NUM + 1); rowNum <= SHEET.getLastRowNum(); ++rowNum)
        {
            final Row ROW = SHEET.getRow(rowNum);
            T record = DATA_STRUCTURE.getInstance();
            String id = Integer.toString(rowNum);

            if (usingFirstColumnAsId)
                id = readRecordWithId(ROW, record, contents, FIRST_COLUMN_NUM, LAST_COLUMN_NUM);
            else
                readRecordWithoutId(ROW, record, contents, FIRST_COLUMN_NUM, LAST_COLUMN_NUM);

            table.set(id, record);
        }

        return table;
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
