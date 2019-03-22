package cn.com.yuyang.service;

import cn.com.yuyang.bean.ZhiWuBean;
import cn.com.yuyang.pojo.Zhiwubiao;
import cn.com.yuyang.pojo.ZhiwubiaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/20
 * Description:
 * Version: V1.0
 */
@Service
public class ZhiWuService {
    private ZhiwubiaoMapper zhiwubiaoMapper;
    @Autowired
    public ZhiWuService(ZhiwubiaoMapper zhiwubiaoMapper){
        this.zhiwubiaoMapper = zhiwubiaoMapper;
    }
    public ZhiwubiaoMapper getZhiwubiaoMapper() {
        return zhiwubiaoMapper;
    }

    /**
     * 查询职务详细信息
     * @param zhiWuBean 查询内容
     * @return pojo对象，全字段需求，不用做任何处理
     */
    public List<Zhiwubiao> selectZhiWu(ZhiWuBean zhiWuBean){
        return zhiwubiaoMapper.selectZhiWu(zhiWuBean);
    }

    /**
     * 查询指定职务id下是否有档案存在
     * @param zhiWuId 将此id执行查询
     * @return 返回结果集包含的信息数
     */
    public int selectZhiWuRen(Integer zhiWuId){
        return zhiwubiaoMapper.selectZhiWuRen(zhiWuId).size();
    }

    /**
     * 执行删除操作
     * @param zhiWuId 将指定id传入mapper
     */
    public void deleteZhiWu(Integer zhiWuId){
        zhiwubiaoMapper.deleteZhiWu(zhiWuId);
    }

    /**
     * 执行新增操作
     * @param zhiwubiao 将新增的内容传入
     */
    public void insertZhiWu(Zhiwubiao zhiwubiao){
        zhiwubiaoMapper.insertZhiWu(zhiwubiao);
    }

    /**
     * 执行新增功能
     * @param zhiwubiao 将修改后的内容传入
     */
    public void updateZhiWu(Zhiwubiao zhiwubiao){
        zhiwubiaoMapper.updateZhiWu(zhiwubiao);
    }
}
