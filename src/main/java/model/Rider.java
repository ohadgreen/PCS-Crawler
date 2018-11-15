package model;

public class Rider {
    private String displayName;
    private String normName;
    private String team;
    private String nationality;
    private String age;
    private String height;
    private String weight;
    private String pageUrl;
    private int proWins;
    private int grandTours;
    private int classicRaces;

    public Rider() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNormName() {
        return normName;
    }

    public void setNormName(String normName) {
        this.normName = normName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getProWins() {
        return proWins;
    }

    public void setProWins(int proWins) {
        this.proWins = proWins;
    }

    public int getGrandTours() {
        return grandTours;
    }

    public void setGrandTours(int grandTours) {
        this.grandTours = grandTours;
    }

    public int getClassicRaces() {
        return classicRaces;
    }

    public void setClassicRaces(int classicRaces) {
        this.classicRaces = classicRaces;
    }

    @Override
    public String toString() {
        return "Rider{" +
                "displayName='" + displayName + '\'' +
                ", normName='" + normName + '\'' +
                ", team='" + team + '\'' +
                ", nationality='" + nationality + '\'' +
                ", age='" + age + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", proWins=" + proWins +
                ", grandTours=" + grandTours +
                ", classicRaces=" + classicRaces +
                '}';
    }
}
