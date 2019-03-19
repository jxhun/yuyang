package cn.com.yuyang.pojo;


import java.util.List;

public interface ZhiwubiaoMapper {
    List<Denglu> selectQuangXian(String xingMing, String gongHao, String buMen, String zhuWu);
}