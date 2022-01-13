package com.example.kasztelanzarnow.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuperModel implements Parcelable {

    public SuperModel() {
    }

    public SuperModel(String teamName, String date, String nameEvent) {
        this.teamName = teamName;
        this.date = date;
        this.nameEvent = nameEvent;
    }

    public SuperModel(String teamName, String date, String nameEvent, List<Player> players) {
        this.teamName = teamName;
        this.date = date;
        this.nameEvent = nameEvent;
        this.players = players;
    }

    String teamName;
    String date;
    String nameEvent;
    List<Player> players = new ArrayList<>();
   // HashMap<String, Player> players = new HashMap<>();


    protected SuperModel(Parcel in) {
        teamName = in.readString();
        date = in.readString();
        nameEvent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teamName);
        dest.writeString(date);
        dest.writeString(nameEvent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SuperModel> CREATOR = new Creator<SuperModel>() {
        @Override
        public SuperModel createFromParcel(Parcel in) {
            return new SuperModel(in);
        }

        @Override
        public SuperModel[] newArray(int size) {
            return new SuperModel[size];
        }
    };

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


}
