package com.example.uploadingpdf;

public class MyModel {
    String name,date,duration;
    int Incoming;
    String person;
    String call_type;
    String number;
    public MyModel(String name, String date, String duration, int Incoming, String person, String call_type) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.Incoming=Incoming;
        this.person=person;
        this.call_type=call_type;;
    }
    public MyModel(String number, String name, String date, String duration, int Incoming, String person, String call_type) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.Incoming=Incoming;
        this.person=person;
        this.call_type=call_type;
        this.number=number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCall_type() {
        return call_type;
    }

    public void setCall_type(String call_type) {
        this.call_type = call_type;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public int getIncoming() {
        return Incoming;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
