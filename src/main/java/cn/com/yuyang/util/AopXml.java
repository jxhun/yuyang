package cn.com.yuyang.util;

import cn.com.yuyang.pojo.Mingancaozuo;
import cn.com.yuyang.pojo.MingancaozuoMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/04
 * Description: AOP
 * Version: V1.0
 */
public class AopXml {

    public AopXml() {
        System.out.println("------------------------AOP初始化-------------------");
    }

    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes(); // 获取RequestAttributes
        System.out.println("看看AOP跑了几次");
        HttpServletRequest request = attr.getRequest(); // 获取request

        HttpSession session = request.getSession();  // 获取session
        String fangFaName = joinPoint.getSignature().getName();  // 得到执行的方法名

        if (fangFaName != null) { // 如果方法名不为空
            Mingancaozuo mingancaozuo = new Mingancaozuo();  // 这个对象来储存结果
            mingancaozuo.setFangFaMingCheng(fangFaName); // 存入方法名
            mingancaozuo.setReturnNeiRong(String.valueOf(result)); // 存入返回内容
            session.setAttribute(SessionKey.MGSJ, mingancaozuo); // list存入session
//            test.check(request);
        }
    }

    public void logBefore(JoinPoint joinPoint) {
        System.out.println("userbean方法执行之前，切入的名字 : " + joinPoint.getSignature().getName());
    }

}
