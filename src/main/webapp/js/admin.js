layui.use(['element'], function () {
    const element = layui.element;

    //导航页面内容切换
    element.on('nav(pageContentSwitch)', function (data) {
        //layui-body页面内容切换
        $(window).ready(function () {
            let url;
            if (data[0].attributes[1] != null) {
                url = data[0].attributes[1].nodeValue;
                console.log(url)
                document.getElementById("layui-body").innerHTML  = url;
            }
        })
    });

})