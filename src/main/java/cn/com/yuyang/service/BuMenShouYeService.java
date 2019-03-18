package cn.com.yuyang.service;

import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.BumenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: huxiaoyi
 * Date: 2019/03/18
 * Description:
 * Version: V1.0
 */
@Service
public class BuMenShouYeService {

    public BuMenShouYeService(){

    }

    @Autowired
    private BumenMapper bumenMapper;

    public BumenMapper getBumenMapper() {
        return bumenMapper;
    }
    public List<Bumen> shouye(){

        return bumenMapper.selectAll();
    }

}
