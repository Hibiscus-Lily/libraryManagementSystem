layui.use(['element'], function () {
    const element = layui.element;


    //导航页面内容切换
    element.on('nav(pageContentSwitch)', function (data) {
        //layui-body页面内容切换
        $(window).ready(function () {
            let url;
            if (data[0].attributes[1] != null) {
                url = data[0].attributes[1].nodeValue;
                document.getElementById("layui-body").innerHTML = '<iframe width="100%" style="border: 0" src="' + url + '" onload="setIframeHeight(this)"></iframe>';
            }
        })
    });

})

// noinspection JSUnusedGlobalSymbols
/**
 * ifrme自适应页面高度，需要设定min-height
 * @param iframe
 */
function setIframeHeight(iframe) {
    if (iframe) {
        iframe.height = $("#layui-body")[0].clientHeight -4
    }
}