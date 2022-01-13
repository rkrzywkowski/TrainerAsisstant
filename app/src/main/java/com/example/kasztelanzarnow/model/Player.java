package com.example.kasztelanzarnow.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private String name;
    private String surname;
    private String dateOfBirth;
    private String phoneNumber;
    private String position;
    private int amountMatches;
    private int amountTrainings;
    private int amountEvents;
    private boolean isOnEvent;
    private int amountYellowCard;
    private int amountRedCard;
    private double amountGoals;
    private double averageMark;

    public Player() {

    }

    public Player(String name, String surname,String dateOfBirth, String phoneNumber, String position){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.amountMatches = 0;
        this.amountTrainings = 0;
        this.amountEvents = 0;
        this.amountYellowCard = 0;
        this.amountRedCard = 0;
        this.amountGoals = 0;
        this.averageMark = 0;
        isOnEvent = false;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };


    public int getAmountYellowCard() { return amountYellowCard; }

    public void setAmountYellowCard(int amountYellowCard) { this.amountYellowCard = amountYellowCard; }

    public int getAmountRedCard() { return amountRedCard; }

    public void setAmountRedCard(int amountRedCard) { this.amountRedCard = amountRedCard; }

    public double getAmountGoals() { return amountGoals; }

    public void setAmountGoals(int amountGoals) { this.amountGoals = amountGoals; }

    public double getAverageMark() { return averageMark; }

    public void setAverageMark(int averageMark) { this.averageMark = averageMark; }

    public int getAmountMatches() {
        return amountMatches;
    }

    public void setAmountMatches(int amountMatches) {
        this.amountMatches = amountMatches;
    }

    public int getAmountTrainings() {
        return amountTrainings;
    }

    public void setAmountTrainings(int amountTrainings) {
        this.amountTrainings = amountTrainings;
    }

    public int getAmountEvents() {
        return amountEvents;
    }

    public void setAmountEvents(int amountEvents) {
        this.amountEvents = amountEvents;
    }

    protected Player(Parcel in) {
        name = in.readString();
        surname = in.readString();
        dateOfBirth = in.readString();
        phoneNumber = in.readString();
        position = in.readString();
        amountTrainings = in.readInt();
        amountMatches = in.readInt();
        amountEvents = in.readInt();
        amountYellowCard = in.readInt();
        amountRedCard = in.readInt();
        amountGoals = in.readDouble();
        averageMark = in.readDouble();
        isOnEvent = in.readByte() != 0;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPosition() {
        return position;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isOnEvent() { return isOnEvent; }

    public void setOnEvent(boolean onEvent) { isOnEvent = onEvent; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(dateOfBirth);
        parcel.writeString(phoneNumber);
        parcel.writeString(position);
        parcel.writeInt(amountMatches);
        parcel.writeInt(amountTrainings);
        parcel.writeInt(amountEvents);
        parcel.writeInt(amountYellowCard);
        parcel.writeInt(amountRedCard);
        parcel.writeDouble(averageMark);
        parcel.writeDouble(amountGoals);
        parcel.writeByte((byte) (isOnEvent ? 1 : 0));
    }
}
