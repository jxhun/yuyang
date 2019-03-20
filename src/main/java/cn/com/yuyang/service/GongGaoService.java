package cn.com.yuyang.service;

import cn.com.yuyang.bean.Bumenbean;
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
     * @param gongGaoBean  公告bean，里面存储的有部门的id，档案登录人的档案id
     * @return
     */
    public List<Gonggao> selectGongGao(GongGaoBean gongGaoBean) {
        return gonggaoMapper.selectGongGao(gongGaoBean);   // 调用查询方法，返回查询结果
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
        Bumenbean bumen = new Bumenbean(); // 这个对象是为了调用部门mapper中的 部门查询方法，由于该模块只有一个需要用到部门对象的地方，不考虑直接注入
        bumen.setBuMenMingCheng(gongGaoBean.getBuMenMingCheng());  // 存入部门名称到bumen对象
//        bumen.setBuMenMingCheng("科技部");
        return bumenMapper.selectOne(bumen);                  // 调用查询方法查询部门id
    }


    /**
     * 这个方法用来新增公告
     * 通过前端传入的公告信息来新增公告，如果部门id为0，那么就是全体员工可看
     * @param gongGaoBean  公告bean
     */
    public void newInsertGongGao(GongGaoBean gongGaoBean) {
        gonggaoMapper.newInsertGongGao(gongGaoBean);
    }

    /**
     * 这个方法用来修改浏览数
     * @param gongGaoBean 公告bean对象，存储的有前端传入的公告id和浏览数
     */
    public void xiuGaiLiuLanShu(GongGaoBean gongGaoBean){
        gongGaoBean.setLiuLanShu(gongGaoBean.getLiuLanShu() + 1); // 拿到浏览数+1传入
        gonggaoMapper.xiuGaiLiuLanShu(gongGaoBean);  // 调用mapper浏览数修改方法
    }

    /**
     *公告详情查看
     * @param gongGaoBean 公告bean对象，其中存储有公告的id
     * @return 返回一个Gonggao对象
     */
    public Gonggao gongGaoXiangQing(GongGaoBean gongGaoBean){
        return gonggaoMapper.gongGaoXiangQing(gongGaoBean);  // 调用查询公告详情方法
    }
}
