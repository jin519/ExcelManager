package record_data_structure;

public class Person
{
    private String name;
    private String engName;
    private String gender;
    private String part;
    private String filmoNum;
    private String filmography;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEngName()
    {
        return engName;
    }

    public void setEngName(String engName)
    {
        this.engName = engName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getPart()
    {
        return part;
    }

    public void setPart(String part)
    {
        this.part = part;
    }

    public String getFilmoNum()
    {
        return filmoNum;
    }

    public void setFilmoNum(String filmoNum)
    {
        this.filmoNum = filmoNum;
    }

    public String getFilmography()
    {
        return filmography;
    }

    public void setFilmography(String filmography)
    {
        this.filmography = filmography;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "name='" + name + '\'' +
                ", engName='" + engName + '\'' +
                ", gender='" + gender + '\'' +
                ", part='" + part + '\'' +
                ", filmoNum='" + filmoNum + '\'' +
                ", filmography='" + filmography + '\'' +
                '}';
    }
}
