import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import record_data_structure.Company;
import record_data_structure.Movie;
import record_data_structure.Person;
import record_data_structure.Profile;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, InvalidFormatException, IllegalAccessException, InstantiationException
    {
        ExcelReader excelReader = new ExcelReader("read_test.xlsx");
        final Table movieTable = excelReader.read(0, Movie.class, true);
        final Table personTable = excelReader.read(1, Person.class, true);
        final Table companyTable = excelReader.read(2, Company.class, true);
        excelReader.close();

        excelReader.open("read_test2.xlsx");
        final Table profileTable = excelReader.read(0, Profile.class, false);
        excelReader.close();

        System.out.println("파일을 모두 읽었습니다.");

        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.set("영화", movieTable);
        excelWriter.set("영화인", personTable);
        excelWriter.set("영화사", companyTable);
        excelWriter.write("write_test.xlsx");

        excelWriter.set("프로필", profileTable);
        excelWriter.write("write_test2.xlsx");

        System.out.println("파일 쓰기가 완료되었습니다.");
    }
}
