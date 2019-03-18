package cn.com.yuyang.service;

import cn.com.yuyang.pojo.Bumen;
import cn.com.yuyang.pojo.Denglu;
import cn.com.yuyang.pojo.DengluMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/18
 * Description:公告管理service层
 * Version: V1.0
 */
@Service
public class GongGaoService {

    @Autowired
    private final DengluMapper dengluMapper;

    /**
     * 构造方法注入dengluMapper对象
     * @param dengluMapper
     */
    public GongGaoService(DengluMapper dengluMapper) {
        this.dengluMapper = dengluMapper;
        System.out.println("-----GongGaoService()------");
    }

    public DengluMapper getDengluMapper() {
        return dengluMapper;
    }

    /**
     * 这个方法用来查询用户的权限
     * 查询结果为List<Bumen>的集合
     * @return
     */
    public List<Bumen> selectQuanXian() {
        return dengluMapper.selectQuanXian();
    }

}
