package com.bb.reservationapp.model;

import java.io.Serializable;

public class Guest {
    private int guestId;
    private String name;
    private String gender;
    private String checkDate;
    private String room;

    public Guest() {
    }

    public Guest(int guestId, String name, String gender, String checkDate, String room) {
        this.guestId = guestId;
        this.name = name;
        this.gender = gender;
        this.checkDate = checkDate;
        this.room = room;
    }

    public Guest(String name, String gender, String checkDate, String room) {
        this.name = name;
        this.gender = gender;
        this.checkDate = checkDate;
        this.room = room;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

}
