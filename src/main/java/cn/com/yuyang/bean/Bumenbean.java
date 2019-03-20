package cn.com.yuyang.bean;

import cn.com.yuyang.pojo.Renyuandangan;

/**
 * Created with IntelliJ IDEA.
 * User: huxiaoyi
 * Date: 2019/03/19
 * Description:
 * Version: V1.0
 */
public class Bumenbean {
    private long id;
    private String buMenMingCheng;
    private String buMenJianJie;
    private String fuZeRen;
    private long dangAnId;
    private java.sql.Timestamp chuangJianShiJian;
    private int count;

    public String getFuZeRen() {
        return fuZeRen;
    }

    public void setFuZeRen(String fuZeRen) {
        this.fuZeRen = fuZeRen;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getBuMenMingCheng() {
        return buMenMingCheng;
    }

    public void setBuMenMingCheng(String buMenMingCheng) {
        this.buMenMingCheng = buMenMingCheng;
    }


    public String getBuMenJianJie() {
        return buMenJianJie;
    }

    public void setBuMenJianJie(String buMenJianJie) {
        this.buMenJianJie = buMenJianJie;
    }


    public long getDangAnId() {
        return dangAnId;
    }

    public void setDangAnId(long dangAnId) {
        this.dangAnId = dangAnId;
    }


    public java.sql.Timestamp getChuangJianShiJian() {
        return chuangJianShiJian;
    }

    public void setChuangJianShiJian(java.sql.Timestamp chuangJianShiJian) {
        this.chuangJianShiJian = chuangJianShiJian;
    }

}
