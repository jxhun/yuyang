package cn.com.yuyang.pojo;


import cn.com.yuyang.bean.JiHuoJinYongBean;
import cn.com.yuyang.bean.QuanXianChaXunBean;
import cn.com.yuyang.bean.ZhiWuBean;

import java.util.List;

public interface ZhiwubiaoMapper {
    // 用于权限管理的方法支撑

    // 搜索权限信息
    List<Denglu> selectQuangXian(QuanXianChaXunBean quanXianChaXunBean);
    // 查询不符合条件人数
    List<Denglu> selectYaoQiu(JiHuoJinYongBean jiHuoJinYongBean);
    // 执行激活禁用
    void updateZhuangTai(JiHuoJinYongBean jiHuoJinYongBean);


    // 用于职务管理的方法支撑

    // 查询职务信息
    List<Zhiwubiao> selectZhiWu(ZhiWuBean zhiWuBean);
    // 查询此职务下是否还有绑定的人员档案
    List<Renyuandangan> selectZhiWuRen(Integer zhiWuId);
    // 删除职务
    void deleteZhiWu(Integer zhiWuId);
    // 新增职务
    void insertZhiWu(Zhiwubiao zhiwubiao);
    // 更新职务
    void updateZhiWu(Zhiwubiao zhiwubiao);

    /** 查询除经理外所有的职务名称及ID
     *
     * @return
     */
    List<Zhiwubiao> selectZhiwu2();

    /** 查询所有职务名称及ID
     *
     * @return
     */
    List<Zhiwubiao> selectZhiwu3();

    /** 查询职务的ID
     *
     * @param zhiwubiao
     * @return
     */
    Integer selectId(Zhiwubiao zhiwubiao);

    /** 修改职务的名称
     *
     * @param zhiwubiao
     * @return
     */
    Integer updateZhiWuMingCheng(Zhiwubiao zhiwubiao);

}