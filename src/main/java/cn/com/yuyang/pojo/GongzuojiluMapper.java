package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.bean.GongZuoRiZhiBean;
import cn.com.yuyang.pojo.Gongzuojilu;

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
    // 编辑日志时的查询
    Gongzuojilu selectRiZhiById(Integer id);
    // 编辑日志
    void updateRiZhi(Gongzuojilu gongzuojilu);
    // 删除日志
    void deleteRiZhi(Integer id);
    int countRiZhi(GongGaoBean gongGaoBean);


}