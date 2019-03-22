package cn.com.yuyang.service;

import cn.com.yuyang.bean.GeRenKaoQinBean;
import cn.com.yuyang.bean.KaoQinGuanLiBean;
import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.pojo.KaoqinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    public KaoQinService(KaoqinMapper kaoqinMapper){
        this.kaoqinMapper = kaoqinMapper;
    }
    public KaoqinMapper getKaoqinMapper() {
        return kaoqinMapper;
    }

    public List<Map<String,Object>> selectUser(GeRenKaoQinBean geRenKaoQinBean){
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(Kaoqin kaoqin:kaoqinMapper.selectUser(geRenKaoQinBean)){
            Map<String,Object> map1 = new HashMap<>();
            map1.put("dangTianRiQi",kaoqin.getDangTianRiQi().toString());
            if (kaoqin.getShangWuShangBan() != null){
                map1.put("shangWuShangBan",kaoqin.getShangWuShangBan().toString());
            }
            else {
                map1.put("shangWuShangBan","/");
            }
            if (kaoqin.getXiaWuXiaBan() != null){
                map1.put("xiaWuXiaBan",kaoqin.getXiaWuXiaBan().toString());
            }
            else {
                map1.put("xiaWuXiaBan","/");
            }
            map1.put("zhuangTai",kaoqin.getZhuangTai());
            map1.put("qingJiaZhuangTai",kaoqin.getQingJiaZhuangTai());
            mapList.add(map1);
        }
        return mapList;
    }
    public List<Map<String,Object>> selectBing(GeRenKaoQinBean geRenKaoQinBean){
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(Kaoqin kaoqin:kaoqinMapper.selectBing(geRenKaoQinBean)){
            Map<String,Object> map1 = new HashMap<>();
            map1.put("dangTianRiQi",kaoqin.getDangTianRiQi().toString());
            map1.put("qingJiaShiYou",kaoqin.getQingjiabiao().getQingJiaShiYou());
            map1.put("zhuangTai",kaoqin.getQingjiabiao().getZhuangTai());
            mapList.add(map1);
        }
        return mapList;
    }
    public List<Kaoqin> selectKaoQinGuanLi(KaoQinGuanLiBean kaoQinGuanLiBean){
        return kaoqinMapper.selectKaoQinGuanLi(kaoQinGuanLiBean);
    }

    public Map<String,Integer> selectCount (String dangAnId){
        return kaoqinMapper.selectCount(dangAnId);
    }
}
