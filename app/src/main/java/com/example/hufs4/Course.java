package com.example.hufs4;

public class Course {

    String Code; //학수번호
    String Grade; //학년
    String Title; //교과목명
    String Instructor; //담당교수
    String Credit; //학점
    String Time; //시간
    String Schedule; //강의시간/강의실
    String Sugang_num; //수강인원수
    String Limit_num; //수강제한인원수
    String Note; //비고
    String Junpil; //전필
    String Cyber; //사이버강의
    String Muke; //무크
    String Foreign; //원어
    String Team; //팀티칭

    public String getCode() { return Code; }

    public void setCode(String code) {
        Code = code;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInstructor() {
        return Instructor;
    }

    public void setInstructor(String instructor) {
        Instructor = instructor;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSchedule() {
        return Schedule;
    }

    public void setSchedule(String schedule) {
        Schedule = schedule;
    }

    public String getSugang_num() {
        return Sugang_num;
    }

    public void setSugang_num(String sugang_num) {
        Sugang_num = sugang_num;
    }

    public String getLimit_num() {
        return Limit_num;
    }

    public void setLimit_num(String limit_num) {
        Limit_num = limit_num;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getJunpil() {
        return Junpil;
    }

    public void setJunpil(String junpil) {
        Junpil = junpil;
    }

    public String getCyber() {
        return Cyber;
    }

    public void setCyber(String cyber) {
        Cyber = cyber;
    }

    public String getMuke() {
        return Muke;
    }

    public void setMuke(String muke) {
        Muke = muke;
    }

    public String getForeign() {
        return Foreign;
    }

    public void setForeign(String foreign) {
        Foreign = foreign;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public Course(String code, String grade, String title, String instructor, String credit, String time, String schedule, String sugang_num, String limit_num, String note, String junpil, String cyber, String muke, String foreign, String team) {
        Code = code;
        Grade = grade;
        Title = title;
        Instructor = instructor;
        Credit = credit;
        Time = time;
        Schedule = schedule;
        Sugang_num = sugang_num;
        Limit_num = limit_num;
        Note = note;
        Junpil = junpil;
        Cyber = cyber;
        Muke = muke;
        Foreign = foreign;
        Team = team;
    }
}
