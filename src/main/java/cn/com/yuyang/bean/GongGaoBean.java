package cn.com.yuyang.bean;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/19
 * Description: 这个bean对象用来接收前端的参数
 * Version: V1.0
 */
public class GongGaoBean {

    private int dangAnId;   // 档案id
    private String token;   // 前端传入的token
    private String gongGaoMingCheng;  // 公告名称
    private java.sql.Timestamp faBuShiJian;   // 发布时间
    private String lanMu;    // 栏目
    private String neiRong;  // 公告内容
    private long buMenId;   // 部门id
    private long zhuangTai;  // 状态
    private String buMenMingCheng;  // 部门名称
    private String fuJianDiZhi;  // 附件地址

    public String getFuJianDiZhi() {
        return fuJianDiZhi;
    }

    public void setFuJianDiZhi(String fuJianDiZhi) {
        this.fuJianDiZhi = fuJianDiZhi;
    }

    public String getBuMenMingCheng() {
        return buMenMingCheng;
    }

    public void setBuMenMingCheng(String buMenMingCheng) {
        this.buMenMingCheng = buMenMingCheng;
    }

    public String getGongGaoMingCheng() {
        return gongGaoMingCheng;
    }

    public void setGongGaoMingCheng(String gongGaoMingCheng) {
        this.gongGaoMingCheng = gongGaoMingCheng;
    }

    public Timestamp getFaBuShiJian() {
        return faBuShiJian;
    }

    public void setFaBuShiJian(Timestamp faBuShiJian) {
        this.faBuShiJian = faBuShiJian;
    }

    public String getLanMu() {
        return lanMu;
    }

    public void setLanMu(String lanMu) {
        this.lanMu = lanMu;
    }

    public String getNeiRong() {
        return neiRong;
    }

    public void setNeiRong(String neiRong) {
        this.neiRong = neiRong;
    }

    public long getBuMenId() {
        return buMenId;
    }

    public void setBuMenId(long buMenId) {
        this.buMenId = buMenId;
    }

    public long getZhuangTai() {
        return zhuangTai;
    }

    public void setZhuangTai(long zhuangTai) {
        this.zhuangTai = zhuangTai;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDangAnId() {
        return dangAnId;
    }

    public void setDangAnId(int dangAnId) {
        this.dangAnId = dangAnId;
    }
}
