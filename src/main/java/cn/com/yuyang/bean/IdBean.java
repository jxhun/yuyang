package cn.com.yuyang.bean;

public class IdBean {
    private int id; //登陆者的人员档案id

    private int dangAnId; //登陆者的人员档案id

    private int buMenId; // 部门的id
    private String xingMing;   // 姓名
    private String shouJiHaoMa; // 手机号码

    private String token; //登录者的token

    public int getBuMenId() {
        return buMenId;
    }

    public void setBuMenId(int buMenId) {
        this.buMenId = buMenId;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getShouJiHaoMa() {
        return shouJiHaoMa;
    }

    public void setShouJiHaoMa(String shouJiHaoMa) {
        this.shouJiHaoMa = shouJiHaoMa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDangAnId() {
        return dangAnId;
    }

    public void setDangAnId(int dangAnId) {
        this.dangAnId = dangAnId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
