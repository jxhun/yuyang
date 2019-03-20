package cn.com.yuyang.service;

import cn.com.yuyang.bean.GeRenKaoQinBean;
import cn.com.yuyang.bean.KaoQinGuanLiBean;
import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.pojo.KaoqinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Kaoqin> selectUser(GeRenKaoQinBean geRenKaoQinBean){
        return kaoqinMapper.selectUser(geRenKaoQinBean);
    }
    public List<Kaoqin> selectBing(GeRenKaoQinBean geRenKaoQinBean){
        return kaoqinMapper.selectBing(geRenKaoQinBean);
    }
    public List<Kaoqin> selectKaoQinGuanLi(KaoQinGuanLiBean kaoQinGuanLiBean){
        return kaoqinMapper.selectKaoQinGuanLi(kaoQinGuanLiBean);
    }
}
