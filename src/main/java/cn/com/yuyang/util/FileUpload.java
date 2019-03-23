package cn.com.yuyang.util;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/23
 * Description: 文件上传工具类
 * Version: V1.0
 */
public class FileUpload {

    /**
     * 这个文件用来上传文件
     *
     * @param articleFile 接收上传文件
     * @param request     得到session
     * @return 成功返回路径
     */
    public static String executeImport(MultipartFile articleFile, HttpServletRequest request) {
        String originalFilename = articleFile.getOriginalFilename();
        String luJingHouZui = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));// 文件存储路径后缀
        String jueDuiLuJing = request.getSession().getServletContext().getRealPath(File.separator) + File.separator;  // 得到文件绝对路径
        String luJingQianZui = "img" + File.separator;  // 图片上传路径前缀
        String luJing = "/img/" + luJingHouZui;
        if (originalFilename != null && (originalFilename.endsWith(".doc") || originalFilename.endsWith(".docx") ||
                originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"))) {
            luJingQianZui = "office" + File.separator;  // office文件上传路径前缀
            luJing = "/office/" + luJingHouZui;  // 得到文件路径
        }
        File file = new File(jueDuiLuJing + luJingQianZui + luJingHouZui);  // 组装上传路径得到文件路径
        System.out.println(jueDuiLuJing + luJingQianZui + luJingHouZui);
        try {
            articleFile.transferTo(file);      // 上传
            return luJing;   // 上传成功return地址路径
        } catch (Exception e) {
//            e.printStackTrace();
            return null;   // 文件上传失败返回null
        }
    }
}
