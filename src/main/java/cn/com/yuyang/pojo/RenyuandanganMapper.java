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
    Long selectId(YuanGongBean yuanGongBean);


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
     * @param yuanGongBean
     */
    void  updateTouXiang(YuanGongBean yuanGongBean);
    /**
     * 用于更新个人设置
     * @param yuanGongBean
     * @return
     */
    void  updateXinXi(YuanGongBean yuanGongBean);


    /**
     * 用于更新个人设置
     * @param yuanGongBean
     * @return
     */
    void  updateXinXi2(YuanGongBean yuanGongBean);

    /** 删除人员档案表，真删除
     *
     * @return
     */
    Integer shanchuRenyuandangan(YuanGongBean yuanGongBean);

    /**
     * 这个方法用来通信录的搜索
     * @param idBean 传入姓名或者手机号码
     * @return 成功返回结果list
     */
    List<Renyuandangan> chaXunRenYuan(IdBean idBean);

    /**
     * 此方法表示判断输入的旧密码是否正确
     * @param yuanGongBean
     * @return
     */
    Integer selectMiMa(YuanGongBean yuanGongBean);

    YuanGongBean selectMiMa2(YuanGongBean yuanGongBean);

    /** 修改部门负责人的部门id
     *
     * @return
     */
    Integer updateYuanGongBuMen(Bumenbean bumenbean);

    /**
     * 生日祝福
     * @param day
     * @return
     */
    List<BirthdayBean> selectShengRi(String day);




}