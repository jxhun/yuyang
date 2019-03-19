package cn.com.yuyang.bean;

import java.sql.Timestamp;

public class XinXiBean {
    private long id;                //信息id
    private long faXinId;           //发信人档案id
    private long shouXinId;         //收信人档案id
    private String xinXiBiaoTi;     //信息的主题
    private String xinXiNeiRong;    //信息的内容
    private Timestamp xinXiShiJian;    //信息发送的时间
    private String fuJianDiZhi;     //信息附件地址
    private long faSongZhuangTai;   //信息发送状态，0为草稿箱，1为已发送
    private long shouCangZhuangTai; //收藏状态，true为收藏，false为未收藏
    private String faJianRenXingMing;  //发件人姓名
    private String shouJianRenXingMing; //收件人姓名

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFaXinId() {
        return faXinId;
    }

    public void setFaXinId(long faXinId) {
        this.faXinId = faXinId;
    }

    public long getShouXinId() {
        return shouXinId;
    }

    public void setShouXinId(long shouXinId) {
        this.shouXinId = shouXinId;
    }

    public String getXinXiBiaoTi() {
        return xinXiBiaoTi;
    }

    public void setXinXiBiaoTi(String xinXiBiaoTi) {
        this.xinXiBiaoTi = xinXiBiaoTi;
    }

    public String getXinXiNeiRong() {
        return xinXiNeiRong;
    }

    public void setXinXiNeiRong(String xinXiNeiRong) {
        this.xinXiNeiRong = xinXiNeiRong;
    }



    public Timestamp getXinXiShiJian() {
        return xinXiShiJian;
    }

    public void setXinXiShiJian(Timestamp xinXiShiJian) {
        this.xinXiShiJian = xinXiShiJian;
    }

    public String getFuJianDiZhi() {
        return fuJianDiZhi;
    }

    public void setFuJianDiZhi(String fuJianDiZhi) {
        this.fuJianDiZhi = fuJianDiZhi;
    }

    public long getFaSongZhuangTai() {
        return faSongZhuangTai;
    }

    public void setFaSongZhuangTai(long faSongZhuangTai) {
        this.faSongZhuangTai = faSongZhuangTai;
    }

    public long getShouCangZhuangTai() {
        return shouCangZhuangTai;
    }

    public void setShouCangZhuangTai(long shouCangZhuangTai) {
        this.shouCangZhuangTai = shouCangZhuangTai;
    }

    public String getFaJianRenXingMing() {
        return faJianRenXingMing;
    }

    public void setFaJianRenXingMing(String faJianRenXingMing) {
        this.faJianRenXingMing = faJianRenXingMing;
    }

    public String getShouJianRenXingMing() {
        return shouJianRenXingMing;
    }

    public void setShouJianRenXingMing(String shouJianRenXingMing) {
        this.shouJianRenXingMing = shouJianRenXingMing;
    }
}
