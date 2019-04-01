package cn.com.yuyang.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/30
 * Description:这个bean用来导出操作记录
 * Version: V1.0
 */
public class CaoZuoJiLuBean {

    private String xingMing;  // 姓名
    private String kaiShiShiJian;//开始时间
    private String jieShuShiJian;//结尾时间

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getKaiShiShiJian() {
        return kaiShiShiJian;
    }

    public void setKaiShiShiJian(String kaiShiShiJian) {
        this.kaiShiShiJian = kaiShiShiJian;
    }

    public String getJieShuShiJian() {
        return jieShuShiJian;
    }

    public void setJieShuShiJian(String jieShuShiJian) {
        this.jieShuShiJian = jieShuShiJian;
    }
}
