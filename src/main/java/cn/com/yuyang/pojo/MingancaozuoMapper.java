package cn.com.yuyang.pojo;


import cn.com.yuyang.bean.CaoZuoJiLuBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MingancaozuoMapper {

    /**
     * 这个方法用来插入操作情况
     * @param mingancaozuo  存入了方法名，档案id
     * @return 插入成功返回1
     */
    Integer xinZeng(Mingancaozuo mingancaozuo);

    List<Mingancaozuo> daoChuChaXun(CaoZuoJiLuBean caoZuoJiLuBean);
}
