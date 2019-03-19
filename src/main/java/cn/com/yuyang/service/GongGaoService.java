package cn.com.yuyang.service;

import cn.com.yuyang.bean.GongGaoBean;
import cn.com.yuyang.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/18
 * Description:公告管理service层
 * Version: V1.0
 */
@Service
public class GongGaoService {


    @Autowired
    private final GonggaoMapper gonggaoMapper;  // 用于公告操作

    @Autowired
    private final BumenMapper bumenMapper;  // 用于新增公告里面通过部门名称查询部门id

    /**
     * 构造方法注入gonggaoMapper,bumenMapper对象
     *
     * @param gonggaoMapper 公告mapper
     * @param bumenMapper   部门mapper
     */
    public GongGaoService(GonggaoMapper gonggaoMapper, BumenMapper bumenMapper) {
        this.gonggaoMapper = gonggaoMapper;
        this.bumenMapper = bumenMapper;
        System.out.println("-----GongGaoService()------");
    }


    /**
     * 这个方法用来查询当前登录人部门权限能看到的所有公告
     *
     * @param buMenId  部门的id
     * @param dangAnId 发布人的档案id
     * @return
     */
    public List<Gonggao> selectGongGao(Integer buMenId, Integer dangAnId) {
        Map<String, Integer> map = new HashMap<>();   // 用一个map来存储调用方法的参数
        map.put("buMenId", buMenId);    // 存入部门id
        map.put("dangAnId", dangAnId);  // 存入档案id

        return gonggaoMapper.selectGongGao(map);   // 调用查询方法，返回查询结果
    }

    /**
     * 这个方法用来查询登录用户已发布或者未发布的公告条数
     *
     * @param dangAnId  登录人的档案id
     * @param zhuangTai 已发布或者未发布的状态
     * @return 成功返回条数
     */
    public Integer selectGongGaoFaBu(Integer dangAnId, Integer zhuangTai) {
        Map<String, Integer> map = new HashMap<>();   // 用一个map来存储调用方法的参数
        map.put("dangAnId", dangAnId);    // 存入档案id
        map.put("zhuangTai", zhuangTai);  // 存入状态
        return gonggaoMapper.selectGongGaoFaBu(map);   // 调用查询方法得到结果
    }


    /**
     * 通过部门名称查询部门id，由于前端传入的是一个名称，所以需要查询
     *
     * @param gongGaoBean
     * @return
     */
    public Integer selectBuMenId(GongGaoBean gongGaoBean) {
        Bumen bumen = new Bumen(); // 这个对象是为了调用部门mapper中的 部门查询方法，由于该模块只有一个需要用到部门对象的地方，不考虑直接注入
        bumen.setBuMenMingCheng(gongGaoBean.getBuMenMingCheng());  // 存入部门名称到bumen对象
//        bumen.setBuMenMingCheng("科技部");
        return bumenMapper.selectOne(bumen);                  // 调用查询方法查询部门id
    }


    /**
     * 这个方法用来新增公告
     *
     * @param gongGaoBean
     */
    public void newInsertGongGao(GongGaoBean gongGaoBean) {
//        gongGaoBean.setGongGaoMingCheng("这是一条公告");
//        gongGaoBean.setLanMu("公告2");
//        gongGaoBean.setNeiRong("萨达所大所大所阿达大大");
//        gongGaoBean.setZhuangTai(1);
//        gongGaoBean.setDangAnId(1);
//        gongGaoBean.setFuJianDiZhi("F://1231321321");
        gonggaoMapper.newInsertGongGao(gongGaoBean);
    }
}
