package model;

import java.util.List;

public class Team {
    private String displayName;
    private String normName;
    private String classification;
    private String pageUrl;
    private List<String> riderList;

    public Team() {
    }

    public Team(String displayName) {
        this.displayName = displayName;
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
    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
    public List<String> getRiderList() {
        return riderList;
    }

    public void setRiderList(List<String> riderList) {
        this.riderList = riderList;
    }
    @Override
    public String toString() {
        return "Team{" +
                "displayName='" + displayName + '\'' +
                ", normName='" + normName + '\'' +
                ", classification='" + classification + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", riderList=" + riderList +
                '}';
    }
}
