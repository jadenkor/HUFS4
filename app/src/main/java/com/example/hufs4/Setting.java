package com.example.hufs4;

public class Setting {

    String UserID;
    String HufsNotice;
    String BachelorNotice;
    String ScholarshipNotice;
    String ENotice;
    String EAssignment;
    String ELecturenote;
    String EAssignment2;
    String ECyberclass;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getHufsNotice() {
        return HufsNotice;
    }

    public void setHufsNotice(String hufsNotice) {
        HufsNotice = hufsNotice;
    }

    public String getBachelorNotice() {
        return BachelorNotice;
    }

    public void setBachelorNotice(String bachelorNotice) {
        BachelorNotice = bachelorNotice;
    }

    public String getScholarshipNotice() {
        return ScholarshipNotice;
    }

    public void setScholarshipNotice(String scholarshipNotice) {
        ScholarshipNotice = scholarshipNotice;
    }

    public String getENotice() {
        return ENotice;
    }

    public void setENotice(String ENotice) {
        this.ENotice = ENotice;
    }

    public String getEAssignment() {
        return EAssignment;
    }

    public void setEAssignment(String EAssignment) {
        this.EAssignment = EAssignment;
    }

    public String getELecturenote() {
        return ELecturenote;
    }

    public void setELecturenote(String ELecturenote) {
        this.ELecturenote = ELecturenote;
    }

    public String getEAssignment2() {
        return EAssignment2;
    }

    public void setEAssignment2(String EAssignment2) {
        this.EAssignment2 = EAssignment2;
    }

    public String getECyberclass() {
        return ECyberclass;
    }

    public void setECyberclass(String ECyberclass) {
        this.ECyberclass = ECyberclass;
    }



    public Setting(String userID, String hufsNotice, String bachelorNotice, String scholarshipNotice, String eNotice, String eAssignment, String eLecturenote, String eAssignment2, String eCyberclass) {
        UserID = userID;
        HufsNotice = hufsNotice;
        BachelorNotice = bachelorNotice;
        ScholarshipNotice = scholarshipNotice;
        this.ENotice = eNotice;
        this.EAssignment = eAssignment;
        this.ELecturenote = eLecturenote;
        this.EAssignment2 = eAssignment2;
        this.ECyberclass = eCyberclass;
    }


}
