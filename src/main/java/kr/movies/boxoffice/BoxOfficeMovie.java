package kr.movies.boxoffice;

public class BoxOfficeMovie {
    private String rank;
    private String title;
    private String worldWide;
    private String domestic;
    private String foreign;

    public BoxOfficeMovie() {}

    public BoxOfficeMovie(String rank, String title, String worldWide, String domestic, String foreign) {
        this.rank = rank;
        this.title = title;
        this.worldWide = worldWide;
        this.domestic = domestic;
        this.foreign = foreign;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorldWide() {
        return worldWide;
    }

    public void setWorldWide(String worldWide) {
        this.worldWide = worldWide;
    }

    public String getDomestic() {
        return domestic;
    }

    public void setDomestic(String domestic) {
        this.domestic = domestic;
    }

    public String getForeign() {
        return foreign;
    }

    public void setForeign(String foreign) {
        this.foreign = foreign;
    }

    @Override
    public String toString() {
        return "kr.movies.boxoffice.BoxOfficeMovie{" +
                "rank='" + rank + '\'' +
                ", title='" + title + '\'' +
                ", worldWide='" + worldWide + '\'' +
                ", domestic='" + domestic + '\'' +
                ", foreign='" + foreign + '\'' +
                '}';
    }
}
