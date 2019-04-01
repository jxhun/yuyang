package cn.com.yuyang.bean;

import java.sql.Date;

public class XinXiSouSuoBean {
    private int id;//登陆者的人员档案id
    private String riQi;//搜索条件：日期
    private String guanJianCi;//搜索条件：关键词
    private String token;   //token

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRiQi() {
        return riQi;
    }

    public void setRiQi(String riQi) {
        this.riQi = riQi;
    }

    public String getGuanJianCi() {
        return guanJianCi;
    }

    public void setGuanJianCi(String guanJianCi) {
        this.guanJianCi = guanJianCi;
    }
}
