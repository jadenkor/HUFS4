package com.example.hufs4;

public class Notice {

    String course;
    String content;
    String date;

    public Notice(String course, String content, String date) {
        this.course = course;
        this.content = content;
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
