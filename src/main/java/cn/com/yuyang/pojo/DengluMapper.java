package cn.com.yuyang.pojo;


import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DengluMapper {
    /**
     * 根据传入参数查询员工信息
     */
    List<Denglu> selectChaXun(@Param("buMenId") Integer buMenId, @Param("gongHao") String gongHao,
                              @Param("xingMing") String xingMing,
                              @Param("startTime") String startTime, @Param("endTime") String endTime);
}