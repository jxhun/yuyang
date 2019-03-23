package cn.com.yuyang.util;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/19
 * Description: 传入session中的key
 * Version: V1.0
 */
public class SessionKey {

    public final static String DANGANID = "dangAnId"; // 登录成功后传入session的档案id
    public final static String DENGLUID = "dengLuId"; // 登录成功后传入session的登录id
    public final static String CHAKANKAOQIN = "chaKanKaoQin"; // 登录成功后传入session的查看考勤权限，有权限为1，无权限为0
    public final static String QINGJIASHENPI = "qinJiaShenPi"; // 登录成功后传入session的请假审批权限，有权限为1，无权限为0
    public final static String QUANXIANGUANLI = "quanXianGuanLi"; // 登录成功后传入session的部门操作权限，有权限为1，无权限为0
    public final static String CHAKANYUANGONG = "chaKanYuanGong"; // 登录成功后传入session的查看员工权限，有权限为1，无权限为0
    public final static String CAOZUOYUANGONG = "caoZuoYuanGong"; // 登录成功后传入session的操作员工权限，有权限为1，无权限为0
    public final static String FABUXIUGAIGONGGAO = "faBuXiuGaiGongGao"; // 登录成功后传入session的发布修改公告权限，有权限为1，无权限为0
    public final static String DAIBANSHIXIANG = "daiBanShiXiang"; // 登录成功后传入session的待办事项权限，有权限为1，无权限为0
    public final static String TOKEN = "Token"; // 登录成功后传入session的token
    public final static String XINGMING = "xingMing"; // 登录成功后传入session的姓名
    public final static String BUMENID = "buMenId"; // 登录成功后传入session的部门的id
    public final static String FUJIANDIZHI = "fuJianDiZhi"; // 上传的附件地址
    public final static String SIYAO = "加密私钥"; // 加密的私钥
}
