package cn.com.yuyang.service;

import cn.com.yuyang.pojo.Kaoqin;
import cn.com.yuyang.pojo.KaoqinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
public class GeRenKaoQinService {
    private KaoqinMapper kaoqinMapper;
    @Autowired
    public GeRenKaoQinService(KaoqinMapper kaoqinMapper){
        this.kaoqinMapper = kaoqinMapper;
    }

    public List<Kaoqin> selectUser(int dangAnId, String startDate, String endDate, String date){
        return kaoqinMapper.selectUser(dangAnId,startDate,endDate,date);
    }
}
