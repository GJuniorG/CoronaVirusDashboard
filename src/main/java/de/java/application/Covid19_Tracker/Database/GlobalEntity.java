package de.java.application.Covid19_Tracker.Database;


import javax.persistence.*;

@Entity
public class GlobalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "latestTotalCases")
    private int latestTotalCases;

    @Column(name = "diffFromPrevDay")
    private int diffFromPrevDay;

    @Column(name = "totalRecovered")
    private int totalRecovered;

    @Column(name = "totalDeaths")
    private int totalDeaths;

    @Column(name = "deathFromPreviousDay")
    private int deathFromPreviousDay;

    @Column(name = "recoveredFromPreviousDay")
    private int recoveredFromPreviousDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDeathFromPreviousDay() {
        return deathFromPreviousDay;
    }

    public void setDeathFromPreviousDay(int deathFromPreviousDay) {
        this.deathFromPreviousDay = deathFromPreviousDay;
    }

    public int getRecoveredFromPreviousDay() {
        return recoveredFromPreviousDay;
    }

    public void setRecoveredFromPreviousDay(int recoveredFromPreviousDay) {
        this.recoveredFromPreviousDay = recoveredFromPreviousDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }
}
