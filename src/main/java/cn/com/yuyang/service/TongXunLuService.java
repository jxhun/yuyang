package cn.com.yuyang.service;

import cn.com.yuyang.bean.IdBean;
import cn.com.yuyang.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope(value = "prototype")
public class TongXunLuService {

    private final BumenMapper bumenMapper;

    private final RenyuandanganMapper renyuandanganMapper;

    /**
     * Mapper注入到该类
     *
     * @param bumenMapper         bumenMapper对象
     * @param renyuandanganMapper renyuandanganMapper对象
     */
    @Autowired
    public TongXunLuService(BumenMapper bumenMapper, RenyuandanganMapper renyuandanganMapper) {
        this.bumenMapper = bumenMapper;
        this.renyuandanganMapper = renyuandanganMapper;
    }

    /**
     * 这个方法用来查询通讯录，用户第一次进入通讯录页面判断bean对象中存储的有什么数据
     * 第一个判断，如果用户点击搜索，那么必然传入了姓名或者手机号码，如果用户点击了部门，那么必然会传入部门id
     * 如果用户第一次进入页面，那么必然不会传入姓名，手机号，部门id
     *
     * @param idBean 里面存储的有姓名或者手机号码或者id
     * @return
     */
    public List<Map<String, Object>> chaXunTongXunLu(IdBean idBean) {
        List<Map<String, Object>> mapList = new ArrayList<>();  // 这个list用来封装取出结果的map
        // 如果姓名或者手机号码不为空，也不为空字符串，那么说明用户点击了搜索并传入了数据,
        // 如果部门id不为0，那么说明用户点击了部门，那么就通过这个部门id查询部门下的人员
        // 这两个判断放一起的原因是因为，如果用户点击搜索那么就不会传入部门id，如果用户点击部门那么也不会传入手机号码或者姓名
        if (idBean != null && (idBean.getXingMing() != null && !idBean.getXingMing().trim().equals("") ||
                idBean.getShouJiHaoMa() != null && !idBean.getShouJiHaoMa().trim().equals("") || idBean.getBuMenId() != 0)) {
            Bumen bumen = bumenMapper.selectRenYuan(idBean);  // 调用查询人员方法,一对多
            List<Renyuandangan> renyuandanganList = bumen.getRenyuandanganList(); // 得到人员list
            for (Renyuandangan ry : renyuandanganList) {
                Map<String, Object> map = new HashMap<>(); // 这个map来取出内容
                map.put("buMenMingCheng", bumen.getBuMenMingCheng()); // 得到部门名称存入
                map.put("xingMing", ry.getXingMing()); // 得到姓名存入
                map.put("shouJiHaoMa", ry.getDenglu().getShouJiHaoMa()); // 得到手机号码存入
                map.put("jinJiLianXiDianHua", ry.getJinJiLianXiDianHua()); // 得到好友的第二个电话
                map.put("zhiWuMingCheng", ry.getZhiwubiao().getZhiWuMingCheng());// 得到职务名称
                map.put("id", ry.getId()); // 得到好友的id
                mapList.add(map);  // 存入值得map存入list
            }
        } else if (idBean != null && idBean.getBuMenId() == 0) {  // 如果部门id为0，那么说明用户是点击通讯录进入通讯录，这个时候只能看到的是部门
            List<Bumen> bumenList = bumenMapper.selectBuMen();
            for (Bumen bm : bumenList) {  //循环取出查询结果
                Map<String, Object> bmMap = new HashMap<>();  // 用一个map来组装查询得到的结果
                bmMap.put("buMenMingCheng", bm.getBuMenMingCheng());  // 得到部门 名称
                bmMap.put("buMenId", bm.getId());  // 得到部门id
                mapList.add(bmMap); // 存入map
            }
        }
        System.out.println("mapList" + mapList);
        return mapList;
    }

    /**
     * 通过前端传入的好友档案id得到好友的具体信息，封装一个map返回到controller层
     *
     * @param idBean 好友的档案id
     * @return 返回一个组装好的结果map
     */
    public Map<String, Object> selectHaoYou(IdBean idBean) {
        Map<String, Object> map = new HashMap<>(); // 这个map来取出内容
        Renyuandangan renyuandangan = renyuandanganMapper.selectHaoYou(idBean);
        map.put("buMenMingCheng", renyuandangan.getBumen().getBuMenMingCheng());  // 拿到部门名称
        map.put("xingMing", renyuandangan.getXingMing());  // 得到好友姓名
        map.put("shouJiHaoMa", renyuandangan.getDenglu().getShouJiHaoMa());// 得到好友手机号码
        map.put("jinJiLianXiDianHua", renyuandangan.getJinJiLianXiDianHua());// 得到好友紧急联系电话
        map.put("zhiWuMingCheng", renyuandangan.getZhiwubiao().getZhiWuMingCheng());// 得到好友职务名称
        map.put("gonghao", renyuandangan.getDenglu().getGongHao());// 得到好友工号
        map.put("beiZhu", renyuandangan.getBeiZhu());// 得到好友备注
        map.put("youXiang", renyuandangan.getYouXiang());// 得到好友邮箱
        map.put("xingBie", renyuandangan.getXingBie());// 得到好友性别
        map.put("touXiang", renyuandangan.getTouXiang());// 得到好友头像地址
        return map;
    }
}
