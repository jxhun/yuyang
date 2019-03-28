package cn.com.yuyang.controller;

import cn.com.yuyang.bean.KaoQinGuanLiBean;
import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.service.KaoQinService;
import cn.com.yuyang.util.FileUpload;
import cn.com.yuyang.util.POIUtil;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/kaoqin")
public class KaoQinGuanLiController {

    private final KaoQinService kaoQinGuanLiService;

    @Autowired
    public KaoQinGuanLiController(KaoQinService kaoQinGuanLiService) {
        this.kaoQinGuanLiService = kaoQinGuanLiService;
    }

    /**
     * 根据传入姓名，部门 时间条件查询数据库考勤表的信息
     *
     * @param kaoQinGuanLiBean
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/chakan", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> selectKaoQinGuanLi(@RequestBody(required = false) KaoQinGuanLiBean kaoQinGuanLiBean, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession();
        //        request.getSession().setAttribute(SessionKey.BUMENID, 3);
//        request.getSession().setAttribute(SessionKey.CHAKANKAOQIN, 1);
//        request.getSession().setAttribute(SessionKey.TOKEN, "toKen");
        //判断token值是否相同
        String a = (String) session.getAttribute(SessionKey.TOKEN);
        System.out.println("========1=======" + kaoQinGuanLiBean.getToken());
        System.out.println("+++++++++++++++" + a);
        System.out.println(kaoQinGuanLiBean.getToken() == null || !(a.equals(kaoQinGuanLiBean.getToken())));
        if (kaoQinGuanLiBean.getToken() == null || !(a.equals(kaoQinGuanLiBean.getToken()))) {
            Map<String, Object> selectAllMap = new HashMap<>();
            ArrayList arrayList = new ArrayList();
            selectAllMap.put("RETURNCODE", -1);
            selectAllMap.put("MSG", "请求超时");
            selectAllMap.put("DATA", arrayList);
            return selectAllMap;
        } else {
            if (String.valueOf(session.getAttribute(SessionKey.CHAKANKAOQIN)).equals("0")) {
                Map<String, Object> selectAllMap = new HashMap<>();
                ArrayList arrayList = new ArrayList();
                selectAllMap.put("RETURNCODE", -1);
                selectAllMap.put("MSG", "没有权限");
                selectAllMap.put("DATA", arrayList);
                return selectAllMap;
            } else {
                if (kaoQinGuanLiBean.getEndTime() != null && kaoQinGuanLiBean.getEndTime() != "") {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date endTime = sdf.parse(kaoQinGuanLiBean.getEndTime());
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(endTime);
                    calendar.add(calendar.DATE, 1);
                    calendar.getTime();
                    kaoQinGuanLiBean.setEndTime(sdf.format(calendar.getTime()));
                }
                String msg = "msg";
                int returnCode;
                String bmid = String.valueOf(session.getAttribute(SessionKey.BUMENID));
                int buMenId = Integer.parseInt(bmid); // 得到部门id
                int buMenJiBie = kaoQinGuanLiService.selectBuMenJiBie(buMenId).getBuMenJiBie();
                if (buMenJiBie == 3) {
                    if (kaoQinGuanLiBean.getBuMenId() == 0) {
                        kaoQinGuanLiBean.setBuMenId(buMenId);
                        msg = "查询成功,结果为本部门";
                        returnCode = 200;
                    } else if (kaoQinGuanLiBean.getBuMenId() == buMenId) {
                        msg = "查询成功";
                        returnCode = 200;
                    } else if (kaoQinGuanLiBean.getBuMenId() != buMenId) {
                        kaoQinGuanLiBean.setBuMenId(-1);
                        msg = "没有权限查询该部门";
                        returnCode = -1;
                    } else {
                        kaoQinGuanLiBean.setBuMenId(-1);
                        msg = "非法请求";
                        returnCode = -1;
                    }

                } else if (buMenJiBie == 1 || buMenJiBie == 2) {
                    msg = "查询成功";
                    returnCode = 200;
                } else {
                    msg = "非法请求";
                    returnCode = -1;
                }

                List<Kaoqin> kaoQinGuanLiList = kaoQinGuanLiService.selectKaoQinGuanLi(kaoQinGuanLiBean);

                ArrayList<Map<String, Object>> arrayList = new ArrayList<>();

                for (Kaoqin kaoqin : kaoQinGuanLiList) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("dangTianRiQi", kaoqin.getDangTianRiQi());
                    map.put("xingMing", kaoqin.getRenyuandangan().getXingMing());
                    map.put("buMenMingCheng", kaoqin.getRenyuandangan().getBumen().getBuMenMingCheng());
                    map.put("shangWuShangBan", kaoqin.getShangWuShangBan());
                    map.put("xiaWuXiaBan", kaoqin.getXiaWuXiaBan());
                    map.put("zhuangTai", kaoqin.getZhuangTai());
                    map.put("qingJiaZhuangTai", kaoqin.getQingJiaZhuangTai());
                    arrayList.add(map);
                }
                Map<String, Object> kaoQinGuanLiMap = new HashMap<>();
                kaoQinGuanLiMap.put("RETURNCODE", returnCode);
                kaoQinGuanLiMap.put("MSG", msg);
                kaoQinGuanLiMap.put("DATA", arrayList);
                return kaoQinGuanLiMap;
            }
        }
    }

    /**
     * 向前端传递所有的部门名称和部门id
     *
     * @return
     */
    @RequestMapping(value = "/selectBuMen", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> selectBuMen(@RequestBody(required = false) KaoQinGuanLiBean kaoQinGuanLiBean, HttpServletRequest request) {
//        request.getSession().setAttribute(SessionKey.TOKEN, "toKen");
        //判断token值是否相同
        String a = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        System.out.println("=======2========" + kaoQinGuanLiBean.getToken());
        System.out.println("+++++++++++++++" + a);
        System.out.println(kaoQinGuanLiBean.getToken() == null || !(a.equals(kaoQinGuanLiBean.getToken())));
        if (kaoQinGuanLiBean.getToken() == null || !(a.equals(kaoQinGuanLiBean.getToken()))) {
            Map<String, Object> selectBuMenMap = new HashMap<>();
            ArrayList arrayList = new ArrayList();
            selectBuMenMap.put("RETURNCODE", -1);
            selectBuMenMap.put("MSG", "请求超时");
            selectBuMenMap.put("DATA", arrayList);
            return selectBuMenMap;
        } else {
            List<Bumen> selectBuMenList = kaoQinGuanLiService.selectBuMen();
            ArrayList arrayList = new ArrayList();
            Map<String, Object> selectBuMenMap = new HashMap<>();
            selectBuMenMap.put("RETURNCODE", 200);
            selectBuMenMap.put("MSG", "提交请假审核成功");
            selectBuMenMap.put("DATA", selectBuMenList);
            return selectBuMenMap;
        }

    }

    /**
     * 这个方法用来导入excle
     *
     * @param kaoQinGuanLiBean 存入token
     * @param request          request对象
     * @return 成功返回成功消息提醒，失败返回失败提醒
     */
    @RequestMapping(value = "/excle")
    @ResponseBody
    public Map<String, Object> shangChuanXls(@RequestBody(required = false) MultipartFile articleFile, KaoQinGuanLiBean kaoQinGuanLiBean, HttpServletRequest request) {
//        request.getSession().setAttribute(SessionKey.TOKEN, "toKen");
        String a = (String) request.getSession().getAttribute(SessionKey.TOKEN);
        System.out.println("======3=========" + kaoQinGuanLiBean.getToken());
        System.out.println("+++++++++++++++" + a);
        System.out.println(kaoQinGuanLiBean.getToken() == null || !(a.equals(kaoQinGuanLiBean.getToken())));
        if (kaoQinGuanLiBean.getToken() == null || !(a.equals(kaoQinGuanLiBean.getToken()))) {
            Map<String, Object> map = new HashMap<>();
            ArrayList arrayList = new ArrayList();
            map.put("RETURNCODE", -1);
            map.put("MSG", "请求超时");
            map.put("DATA", arrayList);
            return map;
        } else {
            String luJing = FileUpload.executeImport(articleFile, request);  // 调用文件上传方法上传文件，并且得到上传文件路径

            Map<String, Object> map = kaoQinGuanLiService.excle(request, luJing);
            return map;
        }
    }
}
