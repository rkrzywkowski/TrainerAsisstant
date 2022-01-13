package com.example.kasztelanzarnow.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String password;
    private String phone;
    private String email;
  //  private List<Team>teams = new ArrayList<>();

    public User(){

    }

    public User(String login, String password, String phone, String email){
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.email = email;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public List<Team> getTeams() {
//        return teams;
//    }

    //public void addTeam(Team team){ teams.add(team); }

//    public void teamToTeam(List<Team> team){Lo
//        this.teams = team;
//    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

}