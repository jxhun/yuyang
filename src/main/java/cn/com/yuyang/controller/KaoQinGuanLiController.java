package cn.com.yuyang.controller;

import cn.com.yuyang.bean.KaoQinGuanLiBean;
import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.service.KaoQinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/kaoqin")
public class KaoQinGuanLiController {
    @Autowired
    private final KaoQinService kaoQinGuanLiService;
    public  KaoQinGuanLiController(KaoQinService kaoQinGuanLiService){
        this.kaoQinGuanLiService =kaoQinGuanLiService;
    }
    @RequestMapping(value = "/chakan",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> selectKaoQinGuanLi(@RequestBody(required = false) KaoQinGuanLiBean kaoQinGuanLiBean){
//        public Map<String,Object> selectKaoQinGuanLi(){
//        KaoQinGuanLiBean kaoQinGuanLiBean = new KaoQinGuanLiBean();
//        kaoQinGuanLiBean.setXingMing(" ");
//        kaoQinGuanLiBean.setBuMenMingCheng("部");
//        kaoQinGuanLiBean.setStartTime("2019-03-16");
//        kaoQinGuanLiBean.setEndTime("2019-03-20");
//        System.out.println(kaoQinGuanLiBean.getXingMing());
        List<Kaoqin> kaoQinGuanLiList= kaoQinGuanLiService.selectKaoQinGuanLi(kaoQinGuanLiBean);
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();

        for (Kaoqin kaoqin :kaoQinGuanLiList){
            System.out.println(kaoqin.getRenyuandangan().getBumen().getBuMenMingCheng());
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("dangTianRiQi",kaoqin.getDangTianRiQi());
            map.put("xingMing",kaoqin.getRenyuandangan().getXingMing());
            map.put("buMenMingCheng",kaoqin.getRenyuandangan().getBumen().getBuMenMingCheng());
            map.put("shangWuShangBan",kaoqin.getShangWuShangBan().toString());
            map.put("xiaWuXiaBan",kaoqin.getXiaWuXiaBan().toString());
            map.put("zhuangTai",kaoqin.getZhuangTai());
            map.put("qingJiaZhuangTai",kaoqin.getQingJiaZhuangTai());
            arrayList.add(map);
        }
        System.out.println("kaoQin");
        Map<String,Object> kaoQinGuanLiMap = new HashMap<>();
        kaoQinGuanLiMap.put("RETURNCODE",200);
        kaoQinGuanLiMap.put("MSG","msg");
        kaoQinGuanLiMap.put("DATA",arrayList);
        kaoQinGuanLiMap.put("TOKEN","token");
        return kaoQinGuanLiMap;
    }

}
