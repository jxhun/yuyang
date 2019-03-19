package cn.com.yuyang.pojo;


public class Gonggao {

    private long id;
    private long dangAnId;
    private String gongGaoMingCheng;
    private java.sql.Timestamp faBuShiJian;
    private String lanMu;
    private String neiRong;
    private String fuJianDiZhi;
    private long buMenId;
    private long liuLanShu;
    private long zhuangTai;
    private Renyuandangan renyuandangan;   // 人事员档案对象

    public Renyuandangan getRenyuandangan() {
        return renyuandangan;
    }

    public void setRenyuandangan(Renyuandangan renyuandangan) {
        this.renyuandangan = renyuandangan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getDangAnId() {
        return dangAnId;
    }

    public void setDangAnId(long dangAnId) {
        this.dangAnId = dangAnId;
    }


    public String getGongGaoMingCheng() {
        return gongGaoMingCheng;
    }

    public void setGongGaoMingCheng(String gongGaoMingCheng) {
        this.gongGaoMingCheng = gongGaoMingCheng;
    }


    public java.sql.Timestamp getFaBuShiJian() {
        return faBuShiJian;
    }

    public void setFaBuShiJian(java.sql.Timestamp faBuShiJian) {
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


    public String getFuJianDiZhi() {
        return fuJianDiZhi;
    }

    public void setFuJianDiZhi(String fuJianDiZhi) {
        this.fuJianDiZhi = fuJianDiZhi;
    }


    public long getBuMenId() {
        return buMenId;
    }

    public void setBuMenId(long buMenId) {
        this.buMenId = buMenId;
    }


    public long getLiuLanShu() {
        return liuLanShu;
    }

    public void setLiuLanShu(long liuLanShu) {
        this.liuLanShu = liuLanShu;
    }


    public long getZhuangTai() {
        return zhuangTai;
    }

    public void setZhuangTai(long zhuangTai) {
        this.zhuangTai = zhuangTai;
    }

}
