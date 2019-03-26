package cn.com.yuyang.bean;

public class KaoQinGuanLiBean {
    private String xingMing;
    private String buMenMingCheng;
    private String startTime;
    private String endTime;
    private int buMenId;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getBuMenId() {
        return buMenId;
    }

    public void setBuMenId(int buMenId) {
        this.buMenId = buMenId;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getBuMenMingCheng() {
        return buMenMingCheng;
    }

    public void setBuMenMingCheng(String buMenMingCheng) {
        this.buMenMingCheng = buMenMingCheng;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
