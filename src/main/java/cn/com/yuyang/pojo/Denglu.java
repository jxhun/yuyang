package cn.com.yuyang.pojo;

import java.sql.Timestamp;

public class Denglu {

    private long id;
    private long dangAnId;
    private byte[] miMa;
    private long dengLuCiShu;
    private Timestamp zuiHouYiCiDengLuShiJian;
    private long cuoWuCiShu;
    private Timestamp dongJieShiJian;
    private long zhuangTai;
    private String gongHao;
    private String shouJiHaoMa;
    private String siYao;
    private Renyuandangan renyuandangan;

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


    public long getDengLuCiShu() {
        return dengLuCiShu;
    }

    public void setDengLuCiShu(long dengLuCiShu) {
        this.dengLuCiShu = dengLuCiShu;
    }

    public Timestamp getZuiHouYiCiDengLuShiJian() {
        return zuiHouYiCiDengLuShiJian;
    }

    public void setZuiHouYiCiDengLuShiJian(Timestamp zuiHouYiCiDengLuShiJian) {
        this.zuiHouYiCiDengLuShiJian = zuiHouYiCiDengLuShiJian;
    }

    public long getCuoWuCiShu() {
        return cuoWuCiShu;
    }

    public void setCuoWuCiShu(long cuoWuCiShu) {
        this.cuoWuCiShu = cuoWuCiShu;
    }

    public Timestamp getDongJieShiJian() {
        return dongJieShiJian;
    }

    public void setDongJieShiJian(Timestamp dongJieShiJian) {
        this.dongJieShiJian = dongJieShiJian;
    }

    public long getZhuangTai() {
        return zhuangTai;
    }

    public void setZhuangTai(long zhuangTai) {
        this.zhuangTai = zhuangTai;
    }

    public String getGongHao() {
        return gongHao;
    }

    public void setGongHao(String gongHao) {
        this.gongHao = gongHao;
    }

    public String getShouJiHaoMa() {
        return shouJiHaoMa;
    }

    public void setShouJiHaoMa(String shouJiHaoMa) {
        this.shouJiHaoMa = shouJiHaoMa;
    }

    public byte[] getMiMa() {
        return miMa;
    }

    public void setMiMa(byte[] miMa) {
        this.miMa = miMa;
    }

    public String getSiYao() {
        return siYao;
    }

    public void setSiYao(String siYao) {
        this.siYao = siYao;
    }

    public Renyuandangan getRenyuandangan() {
        return renyuandangan;
    }

    public void setRenyuandangan(Renyuandangan renyuandangan) {
        this.renyuandangan = renyuandangan;
    }
}
