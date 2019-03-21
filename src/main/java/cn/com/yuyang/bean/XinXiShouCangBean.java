package cn.com.yuyang.bean;

public class XinXiShouCangBean {
    private int id;                 //登陆者的档案id
    private int xinXiId;            //需要修改收藏状态的信息的id
    private int shouCangZhuangTai;  //需要修改收藏状态的信息的当前状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXinXiId() {
        return xinXiId;
    }

    public void setXinXiId(int xinXiId) {
        this.xinXiId = xinXiId;
    }

    public int getShouCangZhuangTai() {
        return shouCangZhuangTai;
    }

    public void setShouCangZhuangTai(int shouCangZhuangTai) {
        this.shouCangZhuangTai = shouCangZhuangTai;
    }
}
