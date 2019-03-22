package cn.com.yuyang.service;

import cn.com.yuyang.bean.JiHuoJinYongBean;
import cn.com.yuyang.bean.QuanXianChaXunBean;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.ZhiwubiaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/19
 * Description:
 * Version: V1.0
 */
@Service
public class QuanXianService {
    private ZhiwubiaoMapper zhiwubiaoMapper;

    @Autowired
    public QuanXianService(ZhiwubiaoMapper zhiwubiaoMapper) {
        this.zhiwubiaoMapper = zhiwubiaoMapper;
    }

    public ZhiwubiaoMapper getZhiwubiaoMapper() {
        return zhiwubiaoMapper;
    }

    /**
     * 负责进mapper执行权限的搜索功能
     *
     * @param quanXianChaXunBean controller层获取的bean
     * @return 在此处对结果集处理为json格式，返给controller
     */
    public List<Map<String, Object>> selectQuangXian(QuanXianChaXunBean quanXianChaXunBean) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        // 处理为json
        for (Denglu denglu : zhiwubiaoMapper.selectQuangXian(quanXianChaXunBean)) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("id", denglu.getId());
            map1.put("zhuangTai", denglu.getZhuangTai());
            map1.put("gongHao", denglu.getGongHao());
            map1.put("xingMing", denglu.getRenyuandangan().getXingMing());
            map1.put("ruZhiShiJian", denglu.getRenyuandangan().getRuZhiShiJian());
            map1.put("buMenMingCheng", denglu.getRenyuandangan().getBumen().getBuMenMingCheng());
            map1.put("zhiWuMingCheng", denglu.getRenyuandangan().getZhiwubiao().getZhiWuMingCheng());
            mapList.add(map1);
        }
        return mapList;
    }

    /**
     * 检索批量处理中是否存在条件不符的对象
     *
     * @param jiHuoJinYongBean 从controller层获取到的带有目标id数组与执行命令
     * @return 返回装有信息数的list的长度
     */
    public int selectYaoQiu(JiHuoJinYongBean jiHuoJinYongBean) {
        return zhiwubiaoMapper.selectYaoQiu(jiHuoJinYongBean).size();
    }

    /**
     * 执行批量处理
     *
     * @param jiHuoJinYongBean 不存在条件不符对象后调用更新方法，获取bean中的数组与执行命令
     */
    public void updateZhuangTai(JiHuoJinYongBean jiHuoJinYongBean) {
        zhiwubiaoMapper.updateZhuangTai(jiHuoJinYongBean);
    }

}
