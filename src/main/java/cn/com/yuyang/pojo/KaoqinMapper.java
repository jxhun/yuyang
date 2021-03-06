package cn.com.yuyang.pojo;


import cn.com.yuyang.bean.GeRenKaoQinBean;
import cn.com.yuyang.bean.KaoQinGuanLiBean;

import java.util.List;
import java.util.Map;

public interface KaoqinMapper {

    /**
     * 查询个人考勤
     * @param geRenKaoQinBean
     * @return
     */
    List<Kaoqin> selectUser(GeRenKaoQinBean geRenKaoQinBean);

    /**
     * 查询请假详情
     * @param geRenKaoQinBean
     * @return
     */
    List<Kaoqin> selectBing(GeRenKaoQinBean geRenKaoQinBean);

    List<Kaoqin> selectKaoQinGuanLi(KaoQinGuanLiBean kaoQinGuanLiBean);

    Map<String,Integer> selectCount(String dangAnId);

    /**
     * excle上传
     */
    void excle(List<Kaoqin> list);
}