package cn.com.yuyang.bean;

//用于删除功能中的查询方法的查询结果接收
public class XinXiShanChuChaXunBean {
    private int faXinId;       //发信人id
    private int shouXinId;      //收信人id
    private int zhuangTai;      //状态，0为都没删除，1为只有发信人删除，2为只有收信人删除，-1为都删除

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

    public int getZhuangTai() {
        return zhuangTai;
    }

    public void setZhuangTai(int zhuangTai) {
        this.zhuangTai = zhuangTai;
    }
}
