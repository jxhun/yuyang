package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.pojo.Gonggao;

import java.util.List;
import java.util.Map;

public interface GonggaoMapper {

    /**
     * 这个方法用来查询当前部门所能查看的所有公告
     * @param map  登录用户的部门id及档案id
     * @return  成功返回Gonggao对象集合list
     */
    List<Gonggao> selectGongGao(Map<String, Integer> map);

    /**
     * 这个方法用来查询当前用户已发布的公告和未发布的公告
     * @param map map存储有档案id和已发布或者未发布的状态
     * @return
     */
    Integer selectGongGaoFaBu(Map<String, Integer> map);

    /**
     * 这个方法用来插入新的公告
     * @param gongGaoBean 公告bean对象，用来存储前端传入的值
     */
    void newInsertGongGao(GongGaoBean gongGaoBean);
}