package cn.com.yuyang.pojo;


import java.util.List;

public class Bumen {

  private long id;
  private String buMenMingCheng;
  private String buMenJianJie;
  private long dangAnId;
  private java.sql.Timestamp chuangJianShiJian;
  private int buMenJiBie;


  public int getBuMenJiBie() {
    return buMenJiBie;
  }

  public void setBuMenJiBie(int buMenJiBie) {
    this.buMenJiBie = buMenJiBie;
  }

  private List<Renyuandangan> renyuandanganList;

  public List<Renyuandangan> getRenyuandanganList() {
    return renyuandanganList;
  }

  public void setRenyuandanganList(List<Renyuandangan> renyuandanganList) {
    this.renyuandanganList = renyuandanganList;
  }

  private Renyuandangan renyuandangan;

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
