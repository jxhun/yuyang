package cn.com.yuyang.controller;

import cn.com.yuyang.service.GongGaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/18
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/GongGao")
public class GongGaoController {

    @Autowired
    private final GongGaoService gongGaoService;

    public GongGaoController(GongGaoService gongGaoService) {
        this.gongGaoService = gongGaoService;
        System.out.println("-----GongGaoController()------");
    }

    /**
     * 点击公告，查询数据库公告(GongGao)表，查询所有公告，进行分页每页3条公告，公告按照时间先后顺序排序。
     * 注意：前台需要做分页，所以后台返回时，需要返回总条数。
     * 前端传入操作人id
     *
     * @param dangAnId 前端传入登录人的档案id
     * @return 成功返回成功的json字符串，map格式
     */
    @RequestMapping(value = {"/seleceAll"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selectAll(@RequestParam Integer dangAnId) {


        Map<String, Object> map = new HashMap<>();


        return map;
    }
}
