package cn.com.yuyang.pojo;


import cn.com.yuyang.bean.*;

import java.util.List;

public interface RenyuandanganMapper {
    /**
     * 这个方法用来查询权限
     *
     * @param id
     * @return
     */
    List<Renyuandangan> selectQuanXian(long id);

    /**
     * 查询这个人是否存在
     * @param bum
     * @return
     */
    Renyuandangan selectGeRenChaXun(Bumenbean bum);

    /**
     * 查询部门人数,以及部门信息
     * @param bum
     * @return
     */
    List<Bumenbean> selectCount(Bumenbean bum);

    /** 修改员工信息
     *
     * @param yuanGongBean
     * @return
     */
    Integer updateYuanGong(YuanGongBean yuanGongBean);

    /** 修改员工登录密码
     *
     * @param yuanGongBean
     * @return
     */
    Integer updateDenglu(YuanGongBean yuanGongBean);

    /** 停用员工登录
     *
     * @param yuanGongBean
     * @return
     */
    Integer deleteDenglu(YuanGongBean yuanGongBean);


    /** 删除员工信息
     *
     * @param yuanGongBean
     * @return
     */
    Integer deleteRenyuandangan(YuanGongBean yuanGongBean);

    Renyuandangan selectHaoYou(IdBean idBean);

    /**
     * 重载这个方法，用于个人设置个人查询
     * @param yuanGongBean
     * @return
     */
    Renyuandangan selectHaoYou(YuanGongBean yuanGongBean);

    Integer chaXunBuMenId(GongGaoBean gongGaoBean);


    /**
     * 用于更新个人设置
     * @param yuanGongBean
     * @return
     */
     Renyuandangan  updateXinXi(YuanGongBean yuanGongBean);

    /**
     * 用于更新个人设置
     * @param denglu
     * @return
     */
     Denglu  updateXinXi2(Denglu denglu);


    /**
     * 此方法是查询本人的工作日志
     * @return
     */
    List<Gongzuojilu> shouYeRiZhi(GongGaoBean gongGaoBean);

    /**
     * 此方法根据每一次如果点击了公告则更新人员档案里面点击公告的时间
     * @param gongGaoBean
     */
    void  updateGongGaoDianJi(GongGaoBean gongGaoBean);

    /**
     * 根据人员档案ID更改头像地址
     * @param renyuandangan
     */
    void  updateTouXiang(Renyuandangan renyuandangan);
}