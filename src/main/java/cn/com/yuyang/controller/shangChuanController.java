package cn.com.yuyang.controller;

import cn.com.yuyang.bean.IdBean;
import cn.com.yuyang.util.SessionKey;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/17
 * Description:
 * Version: V1.0
 */
@Controller
@RequestMapping("/user")
public class shangChuanController {
    //    1、 进入上传 页面
    @RequestMapping(value = {"/importArticle", "/importArticle.html"})
    @ResponseBody
    public Map<String, Object> importArticle() {
        Map<String, Object> map = new HashMap<>();
        map.put("returncode", 200);
        return map;
    }

    //    2、执行上传操作
    @RequestMapping(value = {"/executeImport"}, method = RequestMethod.POST)
    @ResponseBody
    public String executeImport(@RequestBody MultipartFile articleFile, HttpServletRequest request) throws Exception {
        String originalFilename = articleFile.getOriginalFilename();
        String luJingHouZui = System.currentTimeMillis() + originalFilename.substring(originalFilename.indexOf("."));// 文件存储路径后缀
        String jueDuiLuJing = request.getSession().getServletContext().getRealPath(File.separator) + File.separator;
        String luJingQianZui = "img" + File.separator;  // 图片上传路径前缀
//        System.out.println(luJingQianZui);
        request.getSession().setAttribute(SessionKey.FUJIANDIZHI, "/img/" + luJingHouZui);  // 传入工程内路径到session
        if (originalFilename != null && (originalFilename.endsWith(".doc") || originalFilename.endsWith(".docx") ||
                originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"))) {
            luJingQianZui = "office" + File.separator;  // office文件上传路径前缀
            request.getSession().setAttribute(SessionKey.FUJIANDIZHI, "/office/" + luJingHouZui);  // 传入工程内路径到session
        }
        File file = new File(jueDuiLuJing + luJingQianZui + luJingHouZui);
//        System.out.println(luJingHouZui);
        try {
            articleFile.transferTo(file);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }
}
