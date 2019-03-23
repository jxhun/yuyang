package cn.com.yuyang.bean;

import java.sql.Timestamp;

public class ShouYeBean {
    private  int  dangAnId;
    private  int countDaiBan;//未处理的代办事项
    private  int countXiaoXi;//未读消息条数
    private  int coutGongGao;//下班打卡到上班打卡之间的新的公告条数
    private Timestamp shangWuShangBan;
    private  Timestamp  xiaWuXiaBan;

    public int getDangAnId() {
        return dangAnId;
    }

    public void setDangAnId(int dangAnId) {
        this.dangAnId = dangAnId;
    }

    public Timestamp getShangWuShangBan() {
        return shangWuShangBan;
    }

    public void setShangWuShangBan(Timestamp shangWuShangBan) {
        this.shangWuShangBan = shangWuShangBan;
    }

    public Timestamp getXiaWuXiaBan() {
        return xiaWuXiaBan;
    }

    public void setXiaWuXiaBan(Timestamp xiaWuXiaBan) {
        this.xiaWuXiaBan = xiaWuXiaBan;
    }

    public int getCountDaiBan() {
        return countDaiBan;
    }

    public void setCountDaiBan(int countDaiBan) {
        this.countDaiBan = countDaiBan;
    }

    public int getCountXiaoXi() {
        return countXiaoXi;
    }

    public void setCountXiaoXi(int countXiaoXi) {
        this.countXiaoXi = countXiaoXi;
    }

    public int getCoutGongGao() {
        return coutGongGao;
    }

    public void setCoutGongGao(int coutGongGao) {
        this.coutGongGao = coutGongGao;
    }
}
