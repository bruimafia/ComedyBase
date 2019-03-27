package com.example.gukov.comedybase;

public class Comedy {

    private int id, year, number;
    private String comedy, director;

    public Comedy(int id, int year, int number, String comedy, String director) {
        this.id = id;
        this.year = year;
        this.number = number;
        this.comedy = comedy;
        this.director = director;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComedy() {
        return comedy;
    }

    public void setComedy(String comedy) {
        this.comedy = comedy;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

}