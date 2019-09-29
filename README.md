# ExcelManager

> 본 프로젝트는 엑셀 파일을 자바 코드 상에서 자유롭게 읽고 쓰기 위하여 개발되었습니다.<br>
  빌드 도구로 Maven을 사용합니다.

하나의 엑셀 파일안에는 워크시트가 다수 존재할 수 있습니다.<br>
저는 각 워크시트를 Table 제네릭 타입으로 관리하도록 하였습니다.<br>
제가 정의한 Table은 다음과 같습니다.

<img src=md/fig1.png><br>

ExcelManager는 ExcelReader, ExcelWriter로 구성됩니다.<br>
동작방식은 다음과 같습니다.

- ExcelReader
  - 경로에 위치한 엑셀 파일을 엽니다.
  - 워크시트 별 테이블 인스턴스를 반환합니다.
  - 엑셀 파일을 닫습니다.
- ExcelWriter
  - 생성할 워크시트 명과 데이터를 기록할 테이블을 설정합니다.
  - 경로에 위치한 엑셀 파일에 설정 순서대로 워크시트를 만들고, 테이블 데이터를 기록합니다.
  
다음은 사용 예시입니다.

```java
  // read
  ExcelReader excelReader = new ExcelReader("read_test.xlsx");
  
  // 첫번째 열의 값을 레코드 id로 사용합니다.
  final Table movieTable = excelReader.read(0, Movie.class, true);
  final Table personTable = excelReader.read(1, Person.class, true);
  final Table companyTable = excelReader.read(2, Company.class, true);
  excelReader.close();

  // 첫번째 열의 값을 레코드 id로 사용하지 않습니다.
  excelReader.open("read_test2.xlsx");
  final Table profileTable = excelReader.read(0, Profile.class, false);
  excelReader.close();

  // write
  ExcelWriter excelWriter = new ExcelWriter();
  
  excelWriter.set("영화", movieTable);
  excelWriter.set("영화인", personTable);
  excelWriter.set("영화사", companyTable);
  excelWriter.write("write_test.xlsx");

  excelWriter.set("프로필", profileTable);
  excelWriter.write("write_test2.xlsx");
```

실행 결과입니다.
<table>
  <tr>
    <td width="50%"><img src=md/fig2.png></td>
    <td><img src=md/fig3.png></td>
  </tr>
  <tr>
    <td>read_test.xlsx 파일 내 영화 워크시트를 일부 발췌하였습니다.</td>
    <td>read_test2.xlsx 파일 내 프로필 워크시트를 일부 발췌하였습니다.</td></td>
  </tr>
  <tr>
    <td width="50%"><img src=md/fig4.png></td>
    <td><img src=md/fig5.png></td>
  </tr>
  <tr>
    <td>write_test.xlsx 파일 내 영화 워크시트를 일부 발췌하였습니다. id를 기준으로 레코드를 정렬합니다.</td>
    <td>write_test2.xlsx 파일 내 프로필 워크시트를 일부 발췌하였습니다.</td></td>
  </tr>
</table>
