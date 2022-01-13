package com.example.kasztelanzarnow.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team {
    private String teamName;
    private HashMap<String, Player> players = new HashMap<>();


    public Team(String teamName) { this.teamName = teamName; }
    public Team(){ }

    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

//    public void addPlayer(Player player){
//        players.put(player);
//    }

    public String getTeamName(){ return teamName; }

    public HashMap<String, Player> getPlayers(){ return players; };

    @Override
    public String toString() {
        return " " + teamName + " ";
    }
}
