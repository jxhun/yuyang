package cn.com.yuyang.bean;

public class XinXiFaSongBean {
    private int id;                     //发件人档案id
    private String xinXiZhuTi;          //信息的主题
    private String xinXiNeiRong;        //信息的内容
    private int shouJianId;   //收件人档案id

    public int getShouJianId() {
        return shouJianId;
    }

    public void setShouJianId(int shouJianId) {
        this.shouJianId = shouJianId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getXinXiZhuTi() {
        return xinXiZhuTi;
    }

    public void setXinXiZhuTi(String xinXiZhuTi) {
        this.xinXiZhuTi = xinXiZhuTi;
    }

    public String getXinXiNeiRong() {
        return xinXiNeiRong;
    }

    public void setXinXiNeiRong(String xinXiNeiRong) {
        this.xinXiNeiRong = xinXiNeiRong;
    }

}
