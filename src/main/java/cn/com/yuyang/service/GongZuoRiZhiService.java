package cn.com.yuyang.service;

import cn.com.yuyang.bean.GongZuoRiZhiBean;
import cn.com.yuyang.pojo.Gongzuojilu;
import cn.com.yuyang.pojo.GongzuojiluMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/22
 * Description:
 * Version: V1.0
 */
@Service
public class GongZuoRiZhiService {
    private GongzuojiluMapper gongzuojiluMapper;
    @Autowired
    public GongZuoRiZhiService(GongzuojiluMapper gongzuojiluMapper){
        this.gongzuojiluMapper = gongzuojiluMapper;
    }
    public GongzuojiluMapper getGongzuojiluMapper() {
        return gongzuojiluMapper;
    }

    /**
     * 查看自己的工作记录
     * @param gongZuoRiZhiBean 存储档案id查询到人，时间区间查询，状态查询
     * @return 查询结果集
     */
    public List<Gongzuojilu> selectRiZhiByDangAnId(GongZuoRiZhiBean gongZuoRiZhiBean){
        return gongzuojiluMapper.selectRiZhiByDangAnId(gongZuoRiZhiBean);
    }

    /**
     * 查看手下的工作记录
     * @param gongZuoRiZhiBean 存储档案id查询到部门id，时间区间查询，状态查询
     * @return 查询结果集
     */
    public List<Gongzuojilu> selectRiZhiByBuMenId(GongZuoRiZhiBean gongZuoRiZhiBean){
        return gongzuojiluMapper.selectRiZhiByBuMenId(gongZuoRiZhiBean);
    }

    /**
     * 写日志功能
     * @param gongzuojilu 工作日志内容直接新增
     */
    public void insertRiZhi(Gongzuojilu gongzuojilu){
        // 查询操作人员是否为经理级别
        if (gongzuojiluMapper.selectFuZeRen(gongzuojilu.getDangAnId()) == 0){
            // 不是经理级别就会调用方法查到审阅人id并封装进POJO
            gongzuojilu.setJieShouRenId(gongzuojiluMapper.selectShenYueRen(gongzuojilu.getDangAnId()));
        }
        else {
            // 是经理级别就直接将董事id封装入审阅人id
            gongzuojilu.setJieShouRenId(1);
        }
        // 执行
        gongzuojiluMapper.insertRiZhi(gongzuojilu);
    }

    /**
     * 编辑日志跳转时查询，备用
     * @param id 选定的日志id
     * @return 返回日志内容
     */
    public Gongzuojilu selectRiZhiById(Integer id){
        return gongzuojiluMapper.selectRiZhiById(id);
    }

    /**
     * 编辑完成后提交
     * @param gongzuojilu 编辑后的日志内容
     */
    public void updateRiZhi(Gongzuojilu gongzuojilu){
        gongzuojiluMapper.updateRiZhi(gongzuojilu);
    }

    /**
     * 删除日志
     * @param id 指定的日志id
     */
    public void deleteRiZhi(Integer id){
        gongzuojiluMapper.deleteRiZhi(id);
    }
}
