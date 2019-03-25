package cn.com.yuyang.pojo;

import cn.com.yuyang.bean.GeRenQingJiaBean;
import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.bean.QingJiaShenHeBean;
import cn.com.yuyang.pojo.Qingjiabiao;

import java.util.List;

public interface QingjiabiaoMapper {
    /**
     * 根据人员ID查询为处理的请假消息为几条
     *
     * @param gongGaoBean
     * @return
     */
    int countQingJia(GongGaoBean gongGaoBean);

    List<Qingjiabiao> selectAll(QingJiaShenHeBean qingJiaShenHeBean);

    void updateShenHe(QingJiaShenHeBean qingJiaShenHeBean);

    void geRenQingJia(GeRenQingJiaBean geRenQingJiaBean);

    void tiJiaoShenHe(QingJiaShenHeBean qingJiaShenHeBean);

    /**
     * 查询当天请假人的档案id和请假事项
     * @return 返回请假id和请假事项
     */
    List<Qingjiabiao> chaXunDangTianQingJia(String shiJian);

}