import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelWriter
{
    private Map<String, Table<?>> sheetTableMap = new LinkedHashMap<>();

    public <T> void set(final String sheetName, final Table<T> table)
    {
        sheetTableMap.put(sheetName, table);
    }

    public void write(final String path)
    {
        Workbook workbook = new XSSFWorkbook();
        writeTables(workbook);

        FileOutputStream fileOutputStream = openFileOutputStream(path);
        writeWorkbook(workbook, fileOutputStream);

        closeFileOutputStream(fileOutputStream);
        closeWorkbook(workbook);
    }

    private void writeTables(Workbook workbook)
    {
        sheetTableMap.forEach((sheetName, table) ->
        {
            Sheet sheet = workbook.createSheet(sheetName);

            final List<String> HEADERS = table.getHeaders();
            writeHeaders(sheet, HEADERS);

            List<Field> CONTENTS = GenericClass.getDeclaredFields(table.getRecordClass());

            int rowNum = 1;
            for (final String ID : table.getIds())
            {
                Row row = sheet.createRow(rowNum++);
                final Object RECORD = table.getRecord(ID);

                if (HEADERS.size() > CONTENTS.size())
                    writeRecordWithId(row, CONTENTS, ID, RECORD);
                else
                    writeRecordWithoutId(row, CONTENTS, ID, RECORD);
            }
        });
    }

    private void writeHeaders(final Sheet sheet, final List<String> headers)
    {
        Row firstRow = sheet.createRow(0);

        for (int headerIdx = 0; headerIdx < headers.size(); ++headerIdx)
            firstRow.createCell(headerIdx).setCellValue(headers.get(headerIdx));
    }

    private void writeRecordWithId(final Row row, final List<Field> contents, final String id, final Object record)
    {
        row.createCell(0).setCellValue(id);

        for (int contentIdx = 0, cellNum = 1; contentIdx < contents.size(); ++contentIdx, ++cellNum)
            row.createCell(cellNum).setCellValue(getContent(contents.get(contentIdx), record));
    }

    private void writeRecordWithoutId(final Row row, final List<Field> contents, final String id, final Object record)
    {
        for (int contentIdx = 0; contentIdx < contents.size(); ++contentIdx)
            row.createCell(contentIdx).setCellValue(getContent(contents.get(contentIdx), record));
    }

    private void writeWorkbook(Workbook workbook, FileOutputStream fileOutputStream)
    {
        try
        {
            workbook.write(fileOutputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String getContent(final Field content, final Object record)
    {
        try
        {
            return String.valueOf(content.get(record));
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private FileOutputStream openFileOutputStream(final String path)
    {
        FileOutputStream fileOutputStream = null;

        try
        {
            fileOutputStream = new FileOutputStream(path);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return fileOutputStream;
    }

    private void closeFileOutputStream(FileOutputStream fileOutputStream)
    {
        try
        {
            fileOutputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void closeWorkbook(Workbook workbook)
    {
        try
        {
            workbook.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
