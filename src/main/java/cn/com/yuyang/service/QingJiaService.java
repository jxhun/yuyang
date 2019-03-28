package cn.com.yuyang.service;

import cn.com.yuyang.bean.GeRenQingJiaBean;
import cn.com.yuyang.bean.KaoQinGuanLiBean;
import cn.com.yuyang.bean.QingJiaShenHeBean;
import cn.com.yuyang.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QingJiaService {
    private final QingjiabiaoMapper qingjiabiaoMapper;
    private  final BumenMapper bumenMapper;
    @Autowired
    /**
     * 构造方法注入qingjiabiaoMapper,bumenMapper对象
     *
     * @param qingjiabiaoMapper  请假表Mapper
     * @param bumenMapper   部门mapper
     */
    public QingJiaService(QingjiabiaoMapper qingjiabiaoMapper ,BumenMapper bumenMapper ){

        this.qingjiabiaoMapper =qingjiabiaoMapper;
        this.bumenMapper =bumenMapper;
    }

    /**
     * 该方法用于查询向该审批人提交的请假信息
     * @param qingJiaShenHeBean
     * @return 一个Qingjiabiao对象类型的list
     */
    public List<Qingjiabiao> selectAll(QingJiaShenHeBean qingJiaShenHeBean){
        return qingjiabiaoMapper.selectAll(qingJiaShenHeBean);
    }

    /**
     * 该方法用于跟新请假的zhuangtai和chulizhuangtai到数据库
     * @param qingJiaShenHeBean
     */
    public void updateShenHe(QingJiaShenHeBean qingJiaShenHeBean){

        qingjiabiaoMapper.updateShenHe(qingJiaShenHeBean);
    }

    /**
     * 该方法用于存储个人请假申请信息到数据库
     * @param geRenQingJiaBean
     */
    public void  geRenQingJia(GeRenQingJiaBean geRenQingJiaBean){

        qingjiabiaoMapper.geRenQingJia(geRenQingJiaBean);
    }
    /**
     * 该方法用于查询部门的级别
     * @param qingJiaShenHeBean 部门id
     * @return 返回一个Bumen对象
     */
    public Bumen selectBuMenJiBie(QingJiaShenHeBean qingJiaShenHeBean){
        return bumenMapper.selectBuMenJiBie(qingJiaShenHeBean);
    }

    /**
     * 该方法用于查询该审核人的上层审批部门名称及审批部门负责人的id信息
     * @param buMenJiBie 审批人的部门级别
     * @return 部门对象类型的List
     */
    public List<Bumen> selectShangCengShenPiRen(Integer buMenJiBie){

        return bumenMapper.selectShangCengShenPiRen(buMenJiBie);
    }

    /**
     * 该方法用于将请假审核提交给上层审核人
     * @param qingJiaShenHeBean
     */
    public  void  tiJiaoShenHe (QingJiaShenHeBean qingJiaShenHeBean){
        qingjiabiaoMapper.tiJiaoShenHe(qingJiaShenHeBean);
    }

}
