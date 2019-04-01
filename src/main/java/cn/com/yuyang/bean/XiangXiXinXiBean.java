package cn.com.yuyang.bean;

public class XiangXiXinXiBean {
    private int id;         //登陆者的档案id
    private int xinXiId;    //查看的信息id
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXinXiId() {
        return xinXiId;
    }

    public void setXinXiId(int xinXiId) {
        this.xinXiId = xinXiId;
    }
}
