package cn.com.yuyang.bean;

public class XinXiShanChuBean {

    private  int id;    //登陆者的档案id
    private int[] xinXiId;    //需要删除的信息的id
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

    public int[] getXinXiId() {
        return xinXiId;
    }

    public void setXinXiId(int[] xinXiId) {
        this.xinXiId = xinXiId;
    }
}
