package cn.com.yuyang.controller;

import cn.com.yuyang.bean.QingJiaShenHeBean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.Qingjiabiao;
import cn.com.yuyang.service.BuMenGuanLiService;
import cn.com.yuyang.service.QingJiaService;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/qingJiaShenHe")
public class QingJiaShenHeController {
    @Autowired
    private final QingJiaService qingJiaService;
    public QingJiaShenHeController(QingJiaService qingJiaService){
        this.qingJiaService = qingJiaService;
    }

    /**
     * 向前端返回该审核人收到的所有请假申请和该审核人的上层审核部门名称和部门负责人id
     * @param qingJiaShenHeBean
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectAll",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> selectAll(@RequestBody(required = false)QingJiaShenHeBean qingJiaShenHeBean, HttpServletRequest request){
//        request.getSession().setAttribute(SessionKey.BUMENID,3);
//        request.getSession().setAttribute(SessionKey.QINGJIASHENPI,1);
//        request.getSession().setAttribute(SessionKey.TOKEN,"toKen");
        //判断token值是否相同
        if(qingJiaShenHeBean.getToken()==null||(qingJiaShenHeBean.getToken()!=null &&!request.getSession().getAttribute(SessionKey.TOKEN).equals(qingJiaShenHeBean.getToken()))){
            Map<String,Object> selectAllMap = new HashMap<>();
            selectAllMap.put("RETURNCODE",-1);
            selectAllMap.put("MSG","请求超时");
            return selectAllMap;
        }else {
            if ((int)request.getSession().getAttribute(SessionKey.QINGJIASHENPI) == 0) {
                Map<String, Object> selectAllMap = new HashMap<>();
                selectAllMap.put("RETURNCODE", -1);
                selectAllMap.put("MSG", "没有权限");
                return selectAllMap;
            } else {
                Integer buMenId = (Integer) request.getSession().getAttribute(SessionKey.BUMENID);
                long buMenJiBie = qingJiaService.selectBuMenJiBie(buMenId).getBuMenJiBie();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList1 = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                Map<String, Object> map0 = new HashMap<String, Object>();
                int jiBie = 0;
                if (buMenJiBie > 1) {
                    jiBie = (int) (buMenJiBie - 1);
                }
                //查询请假审批提交的上级部门名和部门负责人id
                List<Bumen> shangCengShenPiRenList = qingJiaService.selectShangCengShenPiRen(jiBie);
                for (Bumen bumen : shangCengShenPiRenList) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("buMenMingCheng", bumen.getBuMenMingCheng());
                    map.put("dangAnId", bumen.getDangAnId());
                    arrayList1.add(map);
                }
                //提交到该审批人的请假申请
                List<Qingjiabiao> qingJiaShenHeList = qingJiaService.selectAll(qingJiaShenHeBean);
                for (Qingjiabiao qingjiabiao : qingJiaShenHeList) {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("id", qingjiabiao.getId());
                    map1.put("xingMing", qingjiabiao.getRenyuandangan().getXingMing());
                    map1.put("touXiang", qingjiabiao.getRenyuandangan().getTouXiang());
                    map1.put("leiXIng", qingjiabiao.getLeiXing());
                    map1.put("qiShiShiJian", qingjiabiao.getQiShiShiJian());
                    map1.put("zhongZhiShiJian", qingjiabiao.getZhongZhiShiJian());
                    long tianShu = (qingjiabiao.getZhongZhiShiJian().getTime() - qingjiabiao.getQiShiShiJian().getTime()) / (1000 * 60 * 60 * 24) + 1;
                    map1.put("tianShu", tianShu);
                    map1.put("zhuangTai", qingjiabiao.getZhuangTai());
                    map1.put("qingJiaShiYou", qingjiabiao.getQingJiaShiYou());
                    map1.put("shenQingShiJian", qingjiabiao.getShenQingShiJian());
                    map1.put("chuLiZhuangTai", qingjiabiao.getChuLiZhuangTai());
                    arrayList2.add(map1);
                }
                map0.put("shangCengShenPiRen", arrayList1);
                map0.put("qingJiaDATA", arrayList2);
                arrayList.add(map0);
                Map<String, Object> selectAllMap = new HashMap<>();
                selectAllMap.put("RETURNCODE", 200);
                selectAllMap.put("MSG", "msg");
                selectAllMap.put("DATA", arrayList);
//            selectAllMap.put("TOKEN", "token");
                return selectAllMap;
            }
        }
    }

    /**
     * 更新数据库中审批过的请假信息状态及处理状态
     * @param qingJiaShenHeBean
     * @return
     */
    @RequestMapping(value = "/updateShenHe",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> updateShenHe(@RequestBody(required = false)QingJiaShenHeBean qingJiaShenHeBean, HttpServletRequest request){
//        request.getSession().setAttribute(SessionKey.BUMENID,3);
//        request.getSession().setAttribute(SessionKey.QINGJIASHENPI,1);
//        request.getSession().setAttribute(SessionKey.TOKEN,"toKen");
        //判断token值是否相同
        if(qingJiaShenHeBean.getToken()==null||(qingJiaShenHeBean.getToken()!=null &&!request.getSession().getAttribute(SessionKey.TOKEN).equals(qingJiaShenHeBean.getToken()))){
            Map<String,Object> selectAllMap = new HashMap<>();
            selectAllMap.put("RETURNCODE",-1);
            selectAllMap.put("MSG","请求超时");
            return selectAllMap;
        }else {
            if ((int)request.getSession().getAttribute(SessionKey.QINGJIASHENPI) == 0) {
                Map<String, Object> selectAllMap = new HashMap<>();
                selectAllMap.put("RETURNCODE", -1);
                selectAllMap.put("MSG", "没有权限");
                return selectAllMap;
            } else {
                qingJiaService.updateShenHe(qingJiaShenHeBean);
                ArrayList arrayList = new ArrayList();
                Map<String, Object> selectAllMap = new HashMap<>();
                selectAllMap.put("RETURNCODE", 200);
                selectAllMap.put("MSG", "审批成功");
                selectAllMap.put("DATA", arrayList);
                selectAllMap.put("TOKEN", "token");
                return selectAllMap;
            }
        }
    }

    /**
     * 提交请假申请到上层审批人处，将数据库中审批人id更新为上层审批人id
     * @param qingJiaShenHeBean
     * @return
     */
    @RequestMapping(value = "/tiJiaoShenHe",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> tiJiaoShenHe(@RequestBody(required = false)QingJiaShenHeBean qingJiaShenHeBean, HttpServletRequest request ){
//        request.getSession().setAttribute(SessionKey.BUMENID,3);
//        request.getSession().setAttribute(SessionKey.QINGJIASHENPI,1);
//        request.getSession().setAttribute(SessionKey.TOKEN,"toKen");
        //判断token值是否相同
        if(qingJiaShenHeBean.getToken()==null||(qingJiaShenHeBean.getToken()!=null &&!request.getSession().getAttribute(SessionKey.TOKEN).equals(qingJiaShenHeBean.getToken()))){
            Map<String,Object> selectAllMap = new HashMap<>();
            selectAllMap.put("RETURNCODE",-1);
            selectAllMap.put("MSG","请求超时");
            return selectAllMap;
        }else {
            if ((int)request.getSession().getAttribute(SessionKey.QINGJIASHENPI) == 0) {
                Map<String, Object> selectAllMap = new HashMap<>();
                selectAllMap.put("RETURNCODE", -1);
                selectAllMap.put("MSG", "没有权限");
                return selectAllMap;
            } else {
                qingJiaService.tiJiaoShenHe(qingJiaShenHeBean);
                ArrayList arrayList = new ArrayList();
                Map<String, Object> tiJiaoShenHeMap = new HashMap<>();
                tiJiaoShenHeMap.put("RETURNCODE", 200);
                tiJiaoShenHeMap.put("MSG", "向上提交请假审核成功");
                tiJiaoShenHeMap.put("DATA", arrayList);
                return tiJiaoShenHeMap;
            }
        }
    }

}

