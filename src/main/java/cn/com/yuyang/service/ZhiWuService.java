package cn.com.yuyang.service;

import cn.com.yuyang.bean.ZhiWuBean;
import cn.com.yuyang.pojo.Mingancaozuo;
import cn.com.yuyang.pojo.MingancaozuoMapper;
import cn.com.yuyang.pojo.Zhiwubiao;
import cn.com.yuyang.pojo.ZhiwubiaoMapper;
import cn.com.yuyang.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wong
 * Date: 2019/03/20
 * Description:
 * Version: V1.0
 */
@Service
public class ZhiWuService {

    private final MingancaozuoMapper mingancaozuoMapper;

    private ZhiwubiaoMapper zhiwubiaoMapper;

    @Autowired
    public ZhiWuService(ZhiwubiaoMapper zhiwubiaoMapper, MingancaozuoMapper mingancaozuoMapper) {
        this.zhiwubiaoMapper = zhiwubiaoMapper;
        this.mingancaozuoMapper = mingancaozuoMapper;
    }

    /**
     * 查询职务详细信息
     *
     * @param zhiWuBean 查询内容
     * @return pojo对象，全字段需求，不用做任何处理
     */
    public List<Zhiwubiao> selectZhiWu(ZhiWuBean zhiWuBean) {
        return zhiwubiaoMapper.selectZhiWu(zhiWuBean);
    }

    /**
     * 查询指定职务id下是否有档案存在
     *
     * @param zhiWuId 将此id执行查询
     * @return 返回结果集包含的信息数
     */
    public int selectZhiWuRen(Integer zhiWuId) {
        return zhiwubiaoMapper.selectZhiWuRen(zhiWuId).size();
    }

    /**
     * 执行删除操作
     *
     * @param zhiWuId 将指定id传入mapper
     */
    public void deleteZhiWu(Integer zhiWuId, HttpServletRequest request) {
        zhiwubiaoMapper.deleteZhiWu(zhiWuId);
    }

    /**
     * 执行新增操作
     *
     * @param zhiwubiao 将新增的内容传入
     */
    public void insertZhiWu(Zhiwubiao zhiwubiao, HttpServletRequest request) {
        zhiwubiaoMapper.insertZhiWu(zhiwubiao);
    }

    /**
     * 执行修改功能
     *
     * @param zhiwubiao 将修改后的内容传入
     */
    public void updateZhiWu(Zhiwubiao zhiwubiao, HttpServletRequest request) {
        zhiwubiaoMapper.updateZhiWu(zhiwubiao);
    }

    /**
     * 这个方法用来新增操作记录
     *
     * @param request 用来获取session
     */
    public void minGanXinZeng(HttpServletRequest request) {
        Mingancaozuo mingancaozuo = (Mingancaozuo) request.getSession().getAttribute(SessionKey.MGSJ);  // 得到AOP方法中传出的对象
        if (mingancaozuo != null) {
            String fangFaMing = mingancaozuo.getFangFaMingCheng();  // 得到方法名称
            mingancaozuo.setXingMing((String) request.getSession().getAttribute(SessionKey.XINGMING)); // 得到姓名存入对象
            mingancaozuo.setDangAnId((Long) request.getSession().getAttribute(SessionKey.DANGANID)); // 得到档案id存入对象
            if (fangFaMing.equals("insertZhiWu")) {  // 如果方法名是新增，那么就传入方法名称为新增职务
                mingancaozuo.setFangFaMingCheng("新增职务");
            } else if (fangFaMing.equals("deleteZhiWu")) {  // 如果方法名是删除，那么就传入方法名称为删除职务
                mingancaozuo.setFangFaMingCheng("删除职务");
            } else if (fangFaMing.equals("updateZhiWu")) {  // 如果方法名是编辑，那么就传入方法名称为编辑职务
                mingancaozuo.setFangFaMingCheng("编辑职务");
            }
            mingancaozuoMapper.xinZeng(mingancaozuo); // 调用方法，传入对象
        }
    }
}
