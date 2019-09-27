package record;

public class Company
{
    private String name;
    private String engName;
    private String ceoName;
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

    public String getCeoName()
    {
        return ceoName;
    }

    public void setCeoName(String ceoName)
    {
        this.ceoName = ceoName;
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
        return "Company{" +
                "name='" + name + '\'' +
                ", engName='" + engName + '\'' +
                ", ceoName='" + ceoName + '\'' +
                ", part='" + part + '\'' +
                ", filmoNum='" + filmoNum + '\'' +
                ", filmography='" + filmography + '\'' +
                '}';
    }
}
