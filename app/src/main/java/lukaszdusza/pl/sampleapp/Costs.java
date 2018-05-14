package lukaszdusza.pl.sampleapp;

import java.io.Serializable;

public class Costs implements Serializable{

    private String date;
    private String subject;
    private int cost;

    public Costs(String date, String subject, int cost) {
        this.date = date;
        this.subject = subject;
        this.cost = cost;
    }

    public Costs() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Costs{" +
                "currentDate='" + date + '\'' +
                ", subject='" + subject + '\'' +
                ", cost=" + cost +
                '}';
    }
}
