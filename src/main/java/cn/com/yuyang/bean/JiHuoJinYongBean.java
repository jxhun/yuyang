package cn.com.yuyang.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/19
 * Description:
 * Version: V1.0
 */
public class JiHuoJinYongBean {
    private String dangAnId;
    private String[] dengluIds;
    private String zhuangTai;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDangAnId() {
        return dangAnId;
    }

    public void setDangAnId(String dangAnId) {
        this.dangAnId = dangAnId;
    }

    public String[] getDengluIds() {
        return dengluIds;
    }

    public void setDengluIds(String[] dengluIds) {
        System.out.println(dengluIds[0]);
        System.out.println(dengluIds[1]);
        this.dengluIds = dengluIds;
    }

    public String getZhuangTai() {
        return zhuangTai;
    }

    public void setZhuangTai(String zhuangTai) {
        this.zhuangTai = zhuangTai;
    }
}
