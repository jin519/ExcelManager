package record;

public class Movie
{
    private String name;
    private String engName;
    private String genre;
    private String productCountry;
    private String showTime;
    private String releaseDate;
    private String rating;
    private String director;
    private String actor;
    private String staff;
    private String company;

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

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getProductCountry()
    {
        return productCountry;
    }

    public void setProductCountry(String productCountry)
    {
        this.productCountry = productCountry;
    }

    public String getShowTime()
    {
        return showTime;
    }

    public void setShowTime(String showTime)
    {
        this.showTime = showTime;
    }

    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public String getActor()
    {
        return actor;
    }

    public void setActor(String actor)
    {
        this.actor = actor;
    }

    public String getStaff()
    {
        return staff;
    }

    public void setStaff(String staff)
    {
        this.staff = staff;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    @Override
    public String toString()
    {
        return "Movie{" +
                "name='" + name + '\'' +
                ", engName='" + engName + '\'' +
                ", genre='" + genre + '\'' +
                ", productCountry='" + productCountry + '\'' +
                ", showTime='" + showTime + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating='" + rating + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", staff='" + staff + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
