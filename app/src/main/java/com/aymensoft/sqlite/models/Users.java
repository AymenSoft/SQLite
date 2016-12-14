package com.aymensoft.sqlite.models;

public class Users {

    private int userid;
    private String username;

    public Users(int userid, String username) {
        this.userid = userid;
        this.username = username;
    }

    public Users(){}

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                '}';
    }
}
