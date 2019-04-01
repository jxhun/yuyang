package cn.com.yuyang.pojo;


public class Gongzuojilu {

    private long id;
    private String leiXing;
    private java.sql.Timestamp shiJian;
    private long dangAnId;
    private long zhuangTai;
    private long jieShouRenId;
    private Rizhipinglun rizhipinglun;
    private String neiRong;
    private long riZhiPingLunId;
    private Renyuandangan renyuandangan;

    public Renyuandangan getRenyuandangan() {
        return renyuandangan;
    }

    public void setRenyuandangan(Renyuandangan renyuandangan) {
        this.renyuandangan = renyuandangan;
    }

    public String getNeiRong() {
        return neiRong;
    }

    public void setNeiRong(String neiRong) {
        this.neiRong = neiRong;
    }

    public long getRiZhiPingLunId() {
        return riZhiPingLunId;
    }

    public void setRiZhiPingLunId(long riZhiPingLunId) {
        this.riZhiPingLunId = riZhiPingLunId;
    }

    public Rizhipinglun getRizhipinglun() {
        return rizhipinglun;
    }

    public void setRizhipinglun(Rizhipinglun rizhipinglun) {
        this.rizhipinglun = rizhipinglun;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getLeiXing() {
        return leiXing;
    }

    public void setLeiXing(String leiXing) {
        this.leiXing = leiXing;
    }


    public java.sql.Timestamp getShiJian() {
        return shiJian;
    }

    public void setShiJian(java.sql.Timestamp shiJian) {
        this.shiJian = shiJian;
    }


    public long getDangAnId() {
        return dangAnId;
    }

    public void setDangAnId(long dangAnId) {
        this.dangAnId = dangAnId;
    }


    public long getZhuangTai() {
        return zhuangTai;
    }

    public void setZhuangTai(long zhuangTai) {
        this.zhuangTai = zhuangTai;
    }


    public long getJieShouRenId() {
        return jieShouRenId;
    }

    public void setJieShouRenId(long jieShouRenId) {
        this.jieShouRenId = jieShouRenId;
    }


}
