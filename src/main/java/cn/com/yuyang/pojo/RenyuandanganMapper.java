package cn.com.yuyang.pojo;


import cn.com.yuyang.bean.Bumenbean;
import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.bean.IdBean;
import cn.com.yuyang.bean.YuanGongBean;

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

    /** 将新增员工信息插入人员档案表
     *
     * @param yuanGongBean
     * @return
     */
    Integer insertRenyuandangan(YuanGongBean yuanGongBean);

    /** 通过身份证，查出员工的档案id
     *
     * @param yuanGongBean
     * @return
     */
    long selectId(YuanGongBean yuanGongBean);

    /** 将员工登录信息插入登录表
     *
     * @param yuanGongBean
     * @return
     */
    Integer insertDenglu(YuanGongBean yuanGongBean);
}