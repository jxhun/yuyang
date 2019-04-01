package cn.com.yuyang.controller;

import cn.com.yuyang.bean.GeRenQingJiaBean;
import cn.com.yuyang.bean.QingJiaShenHeBean;
import cn.com.yuyang.pojo.Qingjiabiao;
import cn.com.yuyang.service.QingJiaService;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/kaoqin")
public class GeRenQingJiaController {
    @Autowired
    private final QingJiaService qingJiaService;
    public GeRenQingJiaController(QingJiaService qingJiaService){
        this.qingJiaService = qingJiaService;
    }
    @RequestMapping(value = "/qingjia",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> selectAll(@RequestBody(required = false) GeRenQingJiaBean geRenQingJiaBean, HttpServletRequest request){

        if (geRenQingJiaBean!= null && geRenQingJiaBean.getToken()!= null && !request.getSession().getAttribute(SessionKey.TOKEN).equals(geRenQingJiaBean.getToken())) {
            Map<String, Object> selectAllMap = new HashMap<>();
            ArrayList arrayList = new ArrayList();
            selectAllMap.put("returnCode", 408);
            selectAllMap.put("MSG", "请求超时");
            selectAllMap.put("DATA",arrayList);
            return selectAllMap;
        } else {
            String bmid = String .valueOf(request.getSession().getAttribute(SessionKey.BUMENID));
            Integer buMenId = Integer.parseInt(bmid);
            String fuZheRenId = String.valueOf(qingJiaService.selectFuZeRen2(buMenId));
            if(geRenQingJiaBean.getShenQingRen().equals(fuZheRenId) ){
                qingJiaService.geRenQingJia2(geRenQingJiaBean);
            }
            else{
                qingJiaService.geRenQingJia(geRenQingJiaBean);
            }

        ArrayList arrayList = new ArrayList();
        Map<String,Object> selectAllMap = new HashMap<>();
        selectAllMap.put("returnCode",200);
        selectAllMap.put("MSG","请假申请已发出");
        selectAllMap.put("DATA",arrayList);
        return selectAllMap;
    }
    }
}

