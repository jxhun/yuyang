package cn.com.yuyang.service;

import cn.com.yuyang.pojo.Haoyoubiao;
import cn.com.yuyang.pojo.HaoyoubiaoMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/20
 * Description: 通讯录service
 * Version: V1.0
 */
@Service
public class TongXunLuService {

    private final HaoyoubiaoMapper haoyoubiaoMapper;

    /**
     * haoyoubiaoMapper注入到该类
     *
     * @param haoyoubiaoMapper haoyoubiaoMapper对象
     */
    public TongXunLuService(HaoyoubiaoMapper haoyoubiaoMapper) {
        this.haoyoubiaoMapper = haoyoubiaoMapper;
    }

    /**
     * 这个方法用来查询该人的好友
     *
     * @param dangAnId
     * @return
     */
    public List<Map<String, Object>> chaXunTongXunLu(Integer dangAnId) {
        List<Haoyoubiao> list = haoyoubiaoMapper.chaXunTongXunLu(dangAnId);  // 得到查询结果
        List<Map<String, Object>> mapList = new ArrayList<>();  // 这个list用来封装取出结果的map
        for (Haoyoubiao hy : list) {
            Map<String, Object> map = new HashMap<>(); // 这个map来取出内容
            map.put("buMenMingCheng", hy.getRenyuandangan().getBumen().getBuMenMingCheng()); // 得到部门名称存入
            map.put("xingMing", hy.getRenyuandangan().getXingMing()); // 得到姓名存入
            map.put("shouJiHaoMa", hy.getDengLu().getShouJiHaoMa()); // 得到手机号码存入
            map.put("jinJiLianXiDianHua", hy.getRenyuandangan().getJinJiLianXiDianHua()); // 得到好友的第二个电话
            map.put("id", hy.getRenyuandangan().getId()); // 得到好友的id
            mapList.add(map);  // 存入值得map存入list
        }

        return mapList;
    }

    /**
     * 通过前端传入的好友档案id得到好友的具体信息，封装一个map返回到controller层
     * @param id 好友的档案id
     * @return  返回一个组装好的结果map
     */
    public Map<String, Object> selectHaoYou(Integer id) {
        Map<String, Object> map = new HashMap<>(); // 这个map来取出内容
        Haoyoubiao haoyoubiao = haoyoubiaoMapper.selectHaoYou(id);
        map.put("buMenMingCheng", haoyoubiao.getRenyuandangan().getBumen().getBuMenMingCheng());  // 拿到部门名称
        map.put("xingMing", haoyoubiao.getRenyuandangan().getXingMing());  // 得到好友姓名
        map.put("shouJiHaoMa", haoyoubiao.getDengLu().getShouJiHaoMa());// 得到好友手机号码
        map.put("jinJiLianXiDianHua", haoyoubiao.getRenyuandangan().getJinJiLianXiDianHua());// 得到好友紧急联系电话
        map.put("zhiWuMingCheng", haoyoubiao.getRenyuandangan().getZhiwubiao().getZhiWuMingCheng());// 得到好友职务名称
        map.put("gonghao", haoyoubiao.getDengLu().getGongHao());// 得到好友工号
        map.put("beiZhu", haoyoubiao.getRenyuandangan().getBeiZhu());// 得到好友备注
        map.put("youXiang", haoyoubiao.getRenyuandangan().getYouXiang());// 得到好友邮箱
        map.put("xingBie", haoyoubiao.getRenyuandangan().getXingBie());// 得到好友性别
        map.put("touXiang", haoyoubiao.getRenyuandangan().getTouXiang());// 得到好友头像地址
        return map;
    }
}
