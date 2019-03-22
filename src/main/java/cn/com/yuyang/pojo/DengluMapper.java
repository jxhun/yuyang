package cn.com.yuyang.pojo;


import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DengluMapper {
    /**
     * 根据传入参数查询员工信息
     */
    List<Denglu> selectChaXun(@Param("dangAnId") long dangAnId,@Param("buMenId") long buMenId,
                              @Param("gongHao") String gongHao, @Param("xingMing") String xingMing,
                              @Param("startTime") String startTime, @Param("endTime") String endTime);

    //登录成功更新登录成功次数和登录成功时间
    void updateLogin(Denglu denglu);

    //判断手机号码或者工号是否存在
    Denglu loginByShoujiHaoMa(Denglu denglu);

    //根据ID查询登录表，人员档案表，职务表里面的信息
    Denglu selectChaXun1(Denglu denglu);

    //根据ID，用户名存在，密码错误的情况更新错误次数
    void updateCuoWuCiShu(Denglu denglu);

    //根据ID更改用户的状态
    void  updateDongJie(Denglu denglu);

    //根据ID查询冻结时间
    Denglu selectByDongJiShiJiaan(Denglu denglu);

    //根据ID更新解冻状态
    void updateJieDong(Denglu denglu);
}