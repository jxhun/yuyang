package cn.com.yuyang.service;

import cn.com.yuyang.bean.CaoZuoJiLuBean;
import cn.com.yuyang.pojo.Mingancaozuo;
import cn.com.yuyang.pojo.MingancaozuoMapper;
import cn.com.yuyang.util.POIUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/30
 * Description:
 * Version: V1.0
 */
@Service
public class CaoZuoJiLuService {
    private final MingancaozuoMapper mingancaozuoMapper;

    @Autowired
    public CaoZuoJiLuService(MingancaozuoMapper mingancaozuoMapper) {
        this.mingancaozuoMapper = mingancaozuoMapper;
    }

    /**
     * 这个方法用来导出操作记录到excle
     *
     * @param caoZuoJiLuBean 操作记录bean
     * @return 成功返回true false
     */
    public boolean daoChu(CaoZuoJiLuBean caoZuoJiLuBean, HttpServletResponse response) throws IOException {
        if (caoZuoJiLuBean == null) {   // 如果caoZuoJiLuBean为空，说明用户什么也没有传递，那么就new一个对象存值
            caoZuoJiLuBean = new CaoZuoJiLuBean();
        }
        String kaiShiShiJian = caoZuoJiLuBean.getKaiShiShiJian();  // 开始时间
        String jieShuShiJian = caoZuoJiLuBean.getJieShuShiJian();  // 结束时间
        // 如果开始时间为空并且结尾时间为空，那么就默认查询最近30天的记录
        if ((kaiShiShiJian == null || kaiShiShiJian.equals("")) && (jieShuShiJian == null || jieShuShiJian.equals(""))) {
            // 传入结尾时间，默认时间为当天的后面一天，这样查询
            caoZuoJiLuBean.setJieShuShiJian("" + new java.sql.Date(System.currentTimeMillis() + 86400000L)); // 86400000L为1天的毫秒数
            // 传入开始时间，默认开始时间就是当前时间减去30天
            caoZuoJiLuBean.setKaiShiShiJian("" + new java.sql.Date(System.currentTimeMillis() - 2592000000L));// 2592000000L 为一个月的毫秒数
        }  // 如果结尾时间为空，那么开始时间不为空
        else if (jieShuShiJian == null || jieShuShiJian.equals("")) {
            //首先将结尾时间转为毫秒数
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                // 传入结尾时间，默认时间为开始时间+一个月的时间
                caoZuoJiLuBean.setJieShuShiJian("" + new java.sql.Date(simpleDateFormat.parse(kaiShiShiJian).getTime() +
                        2592000000L)); // 2592000000L 为一个月的毫秒数
            } catch (ParseException e) {
//                    e.printStackTrace();
                return false; // 如果格式转换出错，那么直接返回false
            }
        }// 如果开始时间为空，那么代表结尾时间不为空
        else if (kaiShiShiJian == null || kaiShiShiJian.equals("")) {
            //首先将结尾时间转为毫秒数
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                // 传入开始时间，默认开始时间就是结尾时间减去30天
                caoZuoJiLuBean.setKaiShiShiJian("" + new java.sql.Date(simpleDateFormat.parse(jieShuShiJian).getTime() -
                        2592000000L)); // 2592000000L 为一个月的毫秒数
            } catch (ParseException e) {
//                    e.printStackTrace();
                return false; // 如果格式转换出错，那么直接返回false
            }
        }
        List<Mingancaozuo> list = mingancaozuoMapper.daoChuChaXun(caoZuoJiLuBean); // 得到查询结果
        POIUtil poiUtil = new POIUtil();
        List<List<String>> lists = new ArrayList<>();
        List<String> only_list = new ArrayList<>();
        only_list.add("序号"); // 表头
        only_list.add("操作人姓名"); // 存入数据到excel
        only_list.add("操作方法");
        only_list.add("操作内容");
        only_list.add("操作时间");
        lists.add(only_list);
        for (int i = 0; i < list.size(); i++) {
            only_list = new ArrayList<>();
            only_list.add("" + (i + 1)); // 存入数据到excel,序号
            only_list.add(list.get(i).getXingMing()); // 存入数据到excel
            only_list.add(list.get(i).getFangFaMingCheng()); // 操作方法存入list
            only_list.add(list.get(i).getFangFaMingCheng() == null ? "无" : list.get(i).getFangFaMingCheng()); // 如果操作内容为null，那么返回给excle为无
            String shiJian = list.get(i).getShiJian().toString(); // 得到时间
            only_list.add(shiJian.substring(0, shiJian.indexOf("."))); // 去掉毫秒数
            lists.add(only_list);
        }
        Workbook workbook = poiUtil.creatExcelForPOI(lists, "第一页");
        if (workbook != null) {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xls");  // 导出的文件名是当前时间为文件名
            //获取响应报文输出流对象
            ServletOutputStream out = response.getOutputStream();
            //输出
            workbook.write(out);
            out.flush();
            out.close();
        } else {
            response.setCharacterEncoding("utf-8");
            response.getWriter().print("系统服务出错!");
            return false; // 失败return false
        }

        return true;   // 成功返回 true
    }

}
