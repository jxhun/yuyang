package cn.com.yuyang.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/04
 * Description: AOP
 * Version: V1.0
 */
public class AopXml {
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("userbean方法执行之后 并且返回值，切入的名字 : " +
                joinPoint.getSignature().getName() + "，返回的内容 : " + result);
    }
}
