package cn.com.yuyang.bean;

import org.omg.CORBA.PRIVATE_MEMBER;

public class CaoGaoFaSongBean {
    private int id;                 //发件人的登陆id
    private int shouJianRenId;      //收件人id
    private  String zhuTi;          //信息主题
    private String neiRong;         //信息内容
    private int xinXiId;            //信息id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShouJianRenId() {
        return shouJianRenId;
    }

    public void setShouJianRenId(int shouJianRenId) {
        this.shouJianRenId = shouJianRenId;
    }

    public String getZhuTi() {
        return zhuTi;
    }

    public void setZhuTi(String zhuTi) {
        this.zhuTi = zhuTi;
    }

    public String getNeiRong() {
        return neiRong;
    }

    public void setNeiRong(String neiRong) {
        this.neiRong = neiRong;
    }

    public int getXinXiId() {
        return xinXiId;
    }

    public void setXinXiId(int xinXiId) {
        this.xinXiId = xinXiId;
    }
}
