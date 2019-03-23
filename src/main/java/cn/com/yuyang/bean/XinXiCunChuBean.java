package cn.com.yuyang.bean;

import java.sql.Timestamp;

public class XinXiCunChuBean {
    private int id;             //信息的id
    private int faXinId;        //发信人id
    private int shouXinId;      //收信人id
    private String xinXiBiaoTi; //信息标题
    private String xinXiNeiRong;//信息内容
    private int zhuangTai;      //信息状态
    private Timestamp xinXiShiJian; //信息发送时间
    private String fuJianDiZhi;     //附件地址
    private int faSongZhuangTai;    //发送状态
    private int shouJianRenShouCangZhuangTai;   //收件人收藏状态
    private int faJianRenShouCangZhuangTai;     //发件人收藏状态
    private int yiDuZhuangTai;              //已读状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFaXinId() {
        return faXinId;
    }

    public void setFaXinId(int faXinId) {
        this.faXinId = faXinId;
    }

    public int getShouXinId() {
        return shouXinId;
    }

    public void setShouXinId(int shouXinId) {
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

    public int getZhuangTai() {
        return zhuangTai;
    }

    public void setZhuangTai(int zhuangTai) {
        this.zhuangTai = zhuangTai;
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

    public int getFaSongZhuangTai() {
        return faSongZhuangTai;
    }

    public void setFaSongZhuangTai(int faSongZhuangTai) {
        this.faSongZhuangTai = faSongZhuangTai;
    }

    public int getShouJianRenShouCangZhuangTai() {
        return shouJianRenShouCangZhuangTai;
    }

    public void setShouJianRenShouCangZhuangTai(int shouJianRenShouCangZhuangTai) {
        this.shouJianRenShouCangZhuangTai = shouJianRenShouCangZhuangTai;
    }

    public int getFaJianRenShouCangZhuangTai() {
        return faJianRenShouCangZhuangTai;
    }

    public void setFaJianRenShouCangZhuangTai(int faJianRenShouCangZhuangTai) {
        this.faJianRenShouCangZhuangTai = faJianRenShouCangZhuangTai;
    }

    public int getYiDuZhuangTai() {
        return yiDuZhuangTai;
    }

    public void setYiDuZhuangTai(int yiDuZhuangTai) {
        this.yiDuZhuangTai = yiDuZhuangTai;
    }
}
