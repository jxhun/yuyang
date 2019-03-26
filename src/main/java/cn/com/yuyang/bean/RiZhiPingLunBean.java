package cn.com.yuyang.bean;

import java.sql.Timestamp;

public class RiZhiPingLunBean {
    private int id;     //登陆者状态id
    private String pingLunNeiRong;  //评论的内容
    private int riZhiId;   //评论的日志id
    private Timestamp riQi; //评论的时间
    private String token;   //前台传来token

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getRiQi() {
        return riQi;
    }

    public void setRiQi(Timestamp riQi) {
        this.riQi = riQi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPingLunNeiRong() {
        return pingLunNeiRong;
    }

    public void setPingLunNeiRong(String pingLunNeiRong) {
        this.pingLunNeiRong = pingLunNeiRong;
    }

    public int getRiZhiId() {
        return riZhiId;
    }

    public void setRiZhiId(int riZhiId) {
        this.riZhiId = riZhiId;
    }
}
