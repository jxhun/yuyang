package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.bean.GongZuoJiLuBean;
import cn.com.yuyang.bean.GongZuoRiZhiBean;
import cn.com.yuyang.pojo.Gongzuojilu;

import java.util.HashMap;
import java.util.List;

public interface GongzuojiluMapper {
    // 查询自己的工作日志
    List<Gongzuojilu> selectRiZhiByDangAnId(GongZuoRiZhiBean gongZuoRiZhiBean);
    // 查询自己下属的工作日志（员工没有）
    List<Gongzuojilu> selectRiZhiByBuMenId(GongZuoRiZhiBean gongZuoRiZhiBean);
    // 写日志
    Integer selectFuZeRen(Long dangAnId);
    Integer selectShenYueRen(Long dangAnId);
    void insertRiZhi(Gongzuojilu gongzuojilu);

    // 编辑日志
    void updateRiZhi(Gongzuojilu gongzuojilu);
    // 删除日志
    void deleteRiZhi(Integer id);


    // 查询自己的工作日志
    List<GongZuoJiLuBean> selectRiZhiByDangAnId(GongZuoJiLuBean gongZuoJiLuBean);
    // 查询自己下属的工作日志（员工没有）
    List<GongZuoJiLuBean> selectRiZhiByBuMenId(GongZuoJiLuBean gongZuoJiLuBean);
    // 写日志
    Integer selectFuZeRen(int dangAnId);
    Integer selectShenYueRen(int dangAnId);
    // 编辑日志时的查询
    Gongzuojilu selectRiZhiById(Integer id);

    // 删除日志
    void deleteCaoGao(GongZuoJiLuBean gongZuoJiLuBean);

    int countRiZhi(GongGaoBean gongGaoBean);

    //插入草稿
    void insertCaoGao(GongZuoJiLuBean gongZuoJiLuBean);

    //查询草稿
    List<HashMap<String,Object>> selectCaoGao(GongZuoJiLuBean gongZuoJiLuBean);

    //编辑草稿
    void updateCaoGao(GongZuoJiLuBean gongZuoJiLuBean);

    // 发表草稿
    void updateZhuangTai(GongZuoJiLuBean gongZuoJiLuBean);




}