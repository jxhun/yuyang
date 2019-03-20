package cn.com.yuyang.bean;

public class IdBean {
    private int id; //登陆者的人员档案id

    private int dangAnId; //登陆者的人员档案id

    private String token; //登录者的token

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDangAnId() {
        return dangAnId;
    }

    public void setDangAnId(int dangAnId) {
        this.dangAnId = dangAnId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
