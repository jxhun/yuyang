package cn.com.yuyang.util;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/19
 * Description: 传入session中的key
 * Version: V1.0
 */
public class SessionKey {

    public final static String DANGANID = "档案id"; // 登录成功后传入session的档案id
    public final static String DENGLUID = "登录id"; // 登录成功后传入session的登录id
    public final static String CHAKANKAOQIN = "查看考勤权限"; // 登录成功后传入session的查看考勤权限，有权限为1，无权限为0
    public final static String QINGJIASHENPI = "请假审批权限"; // 登录成功后传入session的请假审批权限，有权限为1，无权限为0
    public final static String QUANXIANGUANLI = "部门操作权限"; // 登录成功后传入session的部门操作权限，有权限为1，无权限为0
    public final static String CHAKANYUANGONG = "查看员工权限"; // 登录成功后传入session的查看员工权限，有权限为1，无权限为0
    public final static String CAOZUOYUANGONG = "操作员工权限"; // 登录成功后传入session的操作员工权限，有权限为1，无权限为0
    public final static String FABUXIUGAIGONGGAO = "发布修改公告权限"; // 登录成功后传入session的发布修改公告权限，有权限为1，无权限为0
    public final static String DAIBANSHIXIANG = "待办事项权限"; // 登录成功后传入session的待办事项权限，有权限为1，无权限为0
    public final static String TOKEN = "Token"; // 登录成功后传入session的token
    public final static String XINGMING = "姓名"; // 登录成功后传入session的姓名
    public final static String BUMENID = "部门id"; // 登录成功后传入session的部门的id
    public final static String SIYAO = "加密私钥"; // 加密的私钥
    public final static String BUMENGUANLI = "部门管理权限"; // 部门管理权限
    public final static String MGSJ = "敏感操作数据";
}
