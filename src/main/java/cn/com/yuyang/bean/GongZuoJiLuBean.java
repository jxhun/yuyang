package cn.com.yuyang.bean;

import java.sql.Timestamp;

public class GongZuoJiLuBean {
    private int id;         //该条工作日志的id
    private  int dangAnId;//档案ID
    private String neiRong; //该条工作日志的内容
    private Timestamp shiJian;//工作日志提交时间或存入草稿箱时间
    private String leiXing;;   //工作日志类型
    private String xingMing; //工作日志提交人姓名
    private int zhuangTai;  //工作日志审阅状态，0为未审阅，1为已审阅
    private String pingLunNeiRong;//工作日志的评论内容
    private Timestamp riQi;     //评论日期
    private String pingLunRen; //评论人姓名
    private  int riZhiPingLunId;//评论日志ID；
    private  int jieShouRenId;//评论人ID；
    private String token;   //前台传来token

    private  int[]  ids;//id的数组

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
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

    public int getRiZhiPingLunId() {
        return riZhiPingLunId;
    }

    public void setRiZhiPingLunId(int riZhiPingLunId) {
        this.riZhiPingLunId = riZhiPingLunId;
    }

    public int getJieShouRenId() {
        return jieShouRenId;
    }

    public void setJieShouRenId(int jieShouRenId) {
        this.jieShouRenId = jieShouRenId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNeiRong() {
        return neiRong;
    }

    public void setNeiRong(String neiRong) {
        this.neiRong = neiRong;
    }

    public Timestamp getShiJian() {
        return shiJian;
    }

    public void setShiJian(Timestamp shiJian) {
        this.shiJian = shiJian;
    }

    public String getLeiXing() {
        return leiXing;
    }

    public void setLeiXing(String leiXing) {
        this.leiXing = leiXing;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public int getZhuangTai() {
        return zhuangTai;
    }

    public void setZhuangTai(int zhuangTai) {
        this.zhuangTai = zhuangTai;
    }

    public String getPingLunNeiRong() {
        return pingLunNeiRong;
    }

    public void setPingLunNeiRong(String pingLunNeiRong) {
        this.pingLunNeiRong = pingLunNeiRong;
    }

    public Timestamp getRiQi() {
        return riQi;
    }

    public void setRiQi(Timestamp riQi) {
        this.riQi = riQi;
    }

    public String getPingLunRen() {
        return pingLunRen;
    }

    public void setPingLunRen(String pingLunRen) {
        this.pingLunRen = pingLunRen;
    }
}
