package cn.com.yuyang.pojo;


import cn.com.yuyang.bean.JiHuoJinYongBean;
import cn.com.yuyang.bean.QuanXianChaXunBean;

import java.util.List;

public interface ZhiwubiaoMapper {
    List<Denglu> selectQuangXian(QuanXianChaXunBean quanXianChaXunBean);
    List<Denglu> selectTest(JiHuoJinYongBean jiHuoJinYongBean);
    void update(JiHuoJinYongBean jiHuoJinYongBean);
}