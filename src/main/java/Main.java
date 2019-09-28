import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import record_data_structure.Company;
import record_data_structure.Movie;
import record_data_structure.Person;
import record_data_structure.Profile;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, InvalidFormatException, IllegalAccessException
    {
        ExcelReader excelReader = new ExcelReader("read_test.xlsx");
        Table<Movie> movie = excelReader.read(0, Movie.class, true);
        Table<Person> person = excelReader.read(1, Person.class, true);
        Table<Company> company = excelReader.read(2, Company.class, true);
        excelReader.close();

        excelReader.open("read_test2.xlsx");
        Table<Profile> profile = excelReader.read(0, Profile.class, false);
        excelReader.close();

        System.out.println("파일을 모두 읽었습니다.");

        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.set("영화", movie);
        excelWriter.set("영화인", person);
        excelWriter.set("영화사", company);
        excelWriter.set("프로필", profile);
        excelWriter.write("write_test.xlsx");

        System.out.println("파일 쓰기가 완료되었습니다.");
    }
}
