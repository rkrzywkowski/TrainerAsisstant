package com.example.kasztelanzarnow.model;

public class Event {

    String Name;
    String date;
    String note;

    public Event(String opportunity, String date, String note) {
        this.Name = opportunity;
        this.date = date;
        this.note = note;
    }

    public Event(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
