package cn.com.yuyang.pojo;


public class Kaoqin {

  private long id;
  private long dangAnId;
  private java.sql.Timestamp shangWuShangBan;
  private java.sql.Timestamp xiaWuXiaBan;
  private long zhuangTai;
  private String qingJiaZhuangTai;
  private java.sql.Date dangTianRiQi;
  private Qingjiabiao qingjiabiao;
  private long qingJiaBiaoId;

  public long getQingJiaBiaoId() {
    return qingJiaBiaoId;
  }

  public void setQingJiaBiaoId(long qingJiaBiaoId) {
    this.qingJiaBiaoId = qingJiaBiaoId;
  }

  private Renyuandangan renyuandangan;

  public Renyuandangan getRenyuandangan() {
    return renyuandangan;
  }

  public void setRenyuandangan(Renyuandangan renyuandangan) {
    this.renyuandangan = renyuandangan;
  }

  public Qingjiabiao getQingjiabiao() {
    return qingjiabiao;
  }

  public void setQingjiabiao(Qingjiabiao qingjiabiao) {
    this.qingjiabiao = qingjiabiao;
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


  public java.sql.Timestamp getShangWuShangBan() {
    return shangWuShangBan;
  }

  public void setShangWuShangBan(java.sql.Timestamp shangWuShangBan) {
    this.shangWuShangBan = shangWuShangBan;
  }


  public java.sql.Timestamp getXiaWuXiaBan() {
    return xiaWuXiaBan;
  }

  public void setXiaWuXiaBan(java.sql.Timestamp xiaWuXiaBan) {
    this.xiaWuXiaBan = xiaWuXiaBan;
  }


  public long getZhuangTai() {
    return zhuangTai;
  }

  public void setZhuangTai(long zhuangTai) {
    this.zhuangTai = zhuangTai;
  }


  public String getQingJiaZhuangTai() {
    return qingJiaZhuangTai;
  }

  public void setQingJiaZhuangTai(String qingJiaZhuangTai) {
    this.qingJiaZhuangTai = qingJiaZhuangTai;
  }


  public java.sql.Date getDangTianRiQi() {
    return dangTianRiQi;
  }

  public void setDangTianRiQi(java.sql.Date dangTianRiQi) {
    this.dangTianRiQi = dangTianRiQi;
  }

}
