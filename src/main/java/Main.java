import record.Company;
import record.Movie;
import record.Person;
import record.Test;

public class Main
{
    public static void main(String[] args)
    {
        ExcelReader excelReader = new ExcelReader("read_test.xlsx");
        Table<Movie> movieTable = excelReader.read(0, Movie.class, true);
        Table<Person> personTable = excelReader.read(1, Person.class, true);
        Table<Company> companyTable = excelReader.read(2, Company.class, true);
        Table<Test> testTable = excelReader.read(3, Test.class, false);

        System.out.println("파일을 모두 읽었습니다.");

        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.set("영화", movieTable);
        excelWriter.set("영화인", personTable);
        excelWriter.set("영화사", companyTable);
        excelWriter.set("테스트", testTable);
        excelWriter.write("write_test.xlsx");

        System.out.println("파일 쓰기가 완료되었습니다.");
    }
}
