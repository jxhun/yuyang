<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/14
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<script src="js/jquery-3.3.1.min.js"></script>
<script>
    $(function () {
        // var json = {"id":8,"shangCengShenPiRen":2,"toKen":"toKen"};
        // var json = {"shenPiRen":2,"toKen":"toKen"};
        // var json = {"id":6,"chuLiZhuangTai":1,"zhuangTai":1,"toKen":"toKen"};
        var json = {"shenQingRen":"5","leiXing":"调休","qiShiShiJian":"2019-3-26","zhongZhiShiJian":"2019-3-28","qingJiaShiYou":"123321","toKen":"toKen"};
        // var json = {"xingMing":"","buMenId":"2","endTime":"2019-03-03","toKen":"toKen"};
        $.ajax({
            // url:"/qingJiaShenHe/updateShenHe",   // 请求路径
            // url:"/qingJiaShenHe/tiJiaoShenHe",
            url:"/kaoqin/qingjia",
            // url:"/kaoqin/chakan",
            // url:"/qingJiaShenHe/selectAll",
            // url:"/kaoqin/selectBuMen",
            type:"post",            // 请求的方式，不区分大小写
            cache:false,            // 关闭缓存，目的是为了避免部分浏览器缓存加载出错(IE)
            contentType:"application/json",
            data:JSON.stringify(json),
            datatype:"json",        // 返回类型，text文本、html页面、json数据
            success:function(response){
                console.log("返回: " + response);
                console.log("数据: " + JSON.stringify(response));
            },
            error:function(response){
                console.log("出错返回: " + response);
                console.log("出错数据: " + JSON.stringify(response));
            }
        });
    });
</script>

<body>

</body>
</html>
