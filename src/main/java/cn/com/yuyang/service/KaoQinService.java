package cn.com.yuyang.service;

import cn.com.yuyang.bean.GeRenKaoQinBean;
import cn.com.yuyang.bean.KaoQinGuanLiBean;
import cn.com.yuyang.pojo.*;
import cn.com.yuyang.util.POIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/18
 * Description:
 * Version: V1.0
 */
@Service
public class KaoQinService {
    private KaoqinMapper kaoqinMapper;
    private BumenMapper bumenMapper;
    private QingjiabiaoMapper qingjiabiaoMapper;

    @Autowired
    public KaoQinService(KaoqinMapper kaoqinMapper, BumenMapper bumenMapper, QingjiabiaoMapper qingjiabiaoMapper) {
        this.kaoqinMapper = kaoqinMapper;
        this.bumenMapper = bumenMapper;
        this.qingjiabiaoMapper = qingjiabiaoMapper;
    }

    public KaoqinMapper getKaoqinMapper() {
        return kaoqinMapper;
    }

    public List<Map<String, Object>> selectUser(GeRenKaoQinBean geRenKaoQinBean) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Kaoqin kaoqin : kaoqinMapper.selectUser(geRenKaoQinBean)) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("dangTianRiQi", kaoqin.getDangTianRiQi().toString());
            if (kaoqin.getShangWuShangBan() != null) {
                map1.put("shangWuShangBan", kaoqin.getShangWuShangBan().toString());
            } else {
                map1.put("shangWuShangBan", "/");
            }
            if (kaoqin.getXiaWuXiaBan() != null) {
                map1.put("xiaWuXiaBan", kaoqin.getXiaWuXiaBan().toString());
            } else {
                map1.put("xiaWuXiaBan", "/");
            }
            map1.put("zhuangTai", kaoqin.getZhuangTai());
            map1.put("qingJiaZhuangTai", kaoqin.getQingJiaZhuangTai());
            mapList.add(map1);
        }
        return mapList;
    }

    public List<Map<String, Object>> selectBing(GeRenKaoQinBean geRenKaoQinBean) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Kaoqin kaoqin : kaoqinMapper.selectBing(geRenKaoQinBean)) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("dangTianRiQi", kaoqin.getDangTianRiQi().toString());
            map1.put("qingJiaShiYou", kaoqin.getQingjiabiao().getQingJiaShiYou());
            map1.put("zhuangTai", kaoqin.getQingjiabiao().getZhuangTai());
            mapList.add(map1);
        }
        return mapList;
    }

    /**
     * 该方法用于查询考勤管理的数据
     *
     * @param kaoQinGuanLiBean
     * @return 返回一个Kaoqin对象类型的list
     */
    public List<Kaoqin> selectKaoQinGuanLi(KaoQinGuanLiBean kaoQinGuanLiBean) {
        return kaoqinMapper.selectKaoQinGuanLi(kaoQinGuanLiBean);
    }

    /**
     * 该方法用于查询部门的级别
     *
     * @param buMenId 部门id
     * @return 返回一个Bumen对象
     */
    public Bumen selectBuMenJiBie(Integer buMenId) {
        return bumenMapper.selectBuMenJiBie(buMenId);
    }

    /**
     * 该方法用于查询所有的部门名称和id
     *
     * @return 返回一个Bumen对象类型的list
     */
    public List<Bumen> selectBuMen() {
        return bumenMapper.selectBuMen();
    }


    public Map<String, Integer> selectCount(String dangAnId) {
        return kaoqinMapper.selectCount(dangAnId);
    }

    /**
     * 这个方法用来导入excle
     *
     * @param request 用来得到项目绝对路径
     * @return 返回结果map
     */
    public Map<String, Object> excle(HttpServletRequest request, String luJing) {
        Map<String, Object> map = new HashMap<>();  // 这个map用来存入结果
        String path = request.getSession().getServletContext().getRealPath(File.separator) + luJing;  // 使用绝对路径和文件路径拼接得到文件路径
        POIUtil poiUtil = new POIUtil();
        List<List<String>> lists = poiUtil.readExcelForPOI(path);  // 得到上传的excle组合成的集合
        List<Kaoqin> kqlist = new ArrayList<>();   // 这个list用来装传入数据库的数据
        for (int i = 1; i < lists.size(); i++) {// 循环取出封装
            Kaoqin kaoqin = new Kaoqin();  // 这个对相应用来封装excle没行的数据
            kaoqin.setDangAnId(Integer.parseInt(lists.get(i).get(0)));  // 得到档案id，存入对象
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Qingjiabiao> qingJiaList = qingjiabiaoMapper.chaXunDangTianQingJia(lists.get(i).get(1));  // 查询得到当天请假人的集合
            if (qingJiaList.size() != 0) {   // 如果查询出来有人请假
                for (Qingjiabiao qinJia : qingJiaList) {  // 循环取出请假人
                    if (qinJia.getShenQingRen() == Integer.parseInt(lists.get(i).get(0))) {  // 如果当天该人有请假信息
                        kaoqin.setQingJiaZhuangTai(qinJia.getLeiXing());   // 请假状态存入请假类型
                        kaoqin.setQingJiaBiaoId(qinJia.getShenQingRen());  // 存入请假表id方便查询
                    } else {  // 如果没有请假，那么状态标记正常
                        kaoqin.setQingJiaZhuangTai("正常");   // 请假状态存入请假类型
                    }
                }
            } else {  // 如果没有人请假，请假状态全为正常
                kaoqin.setQingJiaZhuangTai("正常");   // 请假状态存入请假类型
            }
            try {
                kaoqin.setShangWuShangBan(new Timestamp(simpleDateFormat.parse(lists.get(i).get(1)).getTime()));// 上午上班时间存入
                kaoqin.setXiaWuXiaBan(new Timestamp(simpleDateFormat.parse(lists.get(i).get(2)).getTime()));  // 下午下班时间
                kaoqin.setZhuangTai(Integer.parseInt(lists.get(i).get(3)));  // 存入状态
                kaoqin.setDangTianRiQi(new Date(simpleDateFormat.parse(lists.get(i).get(1)).getTime()));  // 得到当天日期

                kqlist.add(kaoqin);  // 存入对象到list
            } catch (ParseException e) {
                map.put("returncode", -1);
                map.put("MSG", "excle格式错误，请检查excle格式");
            }
        }
        map.put("returncode", 200);
        map.put("MSG", "上传成功");
        kaoqinMapper.excle(kqlist);
        return map;
    }
}
