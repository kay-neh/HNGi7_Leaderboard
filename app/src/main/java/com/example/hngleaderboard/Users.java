package com.example.hngleaderboard;

public class Users {

    String index,name,slackHandle,email,slackPoint;
    int photo;

    public Users(String index,String name, String slackHandle, String email, String slackPoint, int photo) {
        this.index = index;
        this.name = name;
        this.slackHandle = slackHandle;
        this.email = email;
        this.slackPoint = slackPoint;
        this.photo = photo;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlackHandle() {
        return slackHandle;
    }

    public void setSlackHandle(String slackHandle) {
        this.slackHandle = slackHandle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlackPoint() {
        return slackPoint;
    }

    public void setSlackPoint(String slackPoint) {
        this.slackPoint = slackPoint;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
