package cn.com.yuyang.service;

import cn.com.yuyang.bean.GongZuoJiLuBean;
import cn.com.yuyang.bean.GongZuoRiZhiBean;
import cn.com.yuyang.pojo.Gongzuojilu;
import cn.com.yuyang.pojo.GongzuojiluMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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




    /**
     * 查看自己的工作记录
     * @param gongZuoJiLuBean 存储档案id查询到人，时间区间查询，状态查询
     * @return gongZuoJiLuBean
     */
    public List<GongZuoJiLuBean> selectRiZhiByDangAnId(GongZuoJiLuBean gongZuoJiLuBean){
        return gongzuojiluMapper.selectRiZhiByDangAnId(gongZuoJiLuBean);
    }

    /**
     * 查看手下的工作记录
     * @param gongZuoJiLuBean 存储档案id查询到部门id，时间区间查询，状态查询
     * @return 查询结果集
     */
    public List<GongZuoJiLuBean> selectRiZhiByBuMenId(GongZuoJiLuBean gongZuoJiLuBean){
        return gongzuojiluMapper.selectRiZhiByBuMenId(gongZuoJiLuBean);
    }


    /**
     * 删除日志
     * @param gongZuoJiLuBean 指定的日志id
     */
    public void deleteCaoGao(GongZuoJiLuBean gongZuoJiLuBean){
        gongzuojiluMapper.deleteCaoGao(gongZuoJiLuBean);
    }

    /**
     * 根据档案ID查询部门负责人ID
     * @param dangAnId
     * @return
     */
    public Integer selectFuZeRen(int dangAnId){

        return  gongzuojiluMapper.selectFuZeRen(dangAnId);
    }

    /**
     * 插入草稿
     * @param gongZuoJiLuBean
     */
    public void insertCaoGao(GongZuoJiLuBean gongZuoJiLuBean){
        // 查询操作人员是否为经理级别
        if (gongzuojiluMapper.selectFuZeRen(gongZuoJiLuBean.getDangAnId()) == 0){
            // 不是经理级别就会调用方法查到审阅人id并封装进POJO
            gongZuoJiLuBean.setJieShouRenId(gongzuojiluMapper.selectShenYueRen(gongZuoJiLuBean.getDangAnId()));
        }
        else {
            // 是经理级别就直接将董事id封装入审阅人id
            gongZuoJiLuBean.setJieShouRenId(1);
        }
        gongzuojiluMapper.insertCaoGao(gongZuoJiLuBean);

    }


    /**
     * 点击草稿插入草稿
     * @param gongZuoJiLuBean
     * @return
     */
    public List<HashMap<String,Object>> selectCaoGao(GongZuoJiLuBean gongZuoJiLuBean){
        return  gongzuojiluMapper.selectCaoGao(gongZuoJiLuBean);
    };

    /**
     * 更新草稿内容
     * @param gongZuoJiLuBean
     * @return
     */
    public void updateCaoGao(GongZuoJiLuBean gongZuoJiLuBean){
        gongzuojiluMapper.updateCaoGao(gongZuoJiLuBean);

    }

    /**
     * 发表草稿的内容
     * @param gongZuoJiLuBean
     */
    public void updateZhuangTai(GongZuoJiLuBean gongZuoJiLuBean){
        gongzuojiluMapper.updateZhuangTai(gongZuoJiLuBean);

    }

}
