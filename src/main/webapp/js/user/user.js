layui.extend({
    layselect: '{/}../lib/modules/layselect', // {/}的意思即代表采用自有路径，即不跟随 base 路径
})
const token = localStorage.getItem('token');


layui.use(['element'], function () {
    const element = layui.element;

    $.ajax({
        url: 'http://localhost:8080/libraryManagementSystem/commonuser/user/information',
        headers: {
            "token": token
        },
        success: function (data) {
            if (data.code === 0) {
                $('#avatarurl').attr('src',data["data"]["avatarurl"])

                data = JSON.stringify(data["data"])
                localStorage.setItem('data', data)
                notify.success("登录成功", "topRight");
            } else {
                notify.error(data.msg, "topRight");
                setTimeout(function () {
                    window.location.href = "../page/Z_logIn.html"
                }, 1000)
            }
        }

    })


    /**
     * 导航页面内容切换
     */
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
        iframe.height = $("#layui-body")[0].clientHeight - 4
    }
}


/**
 * 退出登录
 */
function signOut() {
    $.ajax({
        url: "http://localhost:8080/libraryManagementSystem/login/SignOut",
        type: "POST",
        data: {
            "account": JSON.parse(localStorage.getItem('data'))["account"]
        },
        success: function (data) {
            if (data.code === 0) {
                localStorage.clear()
                notify.success("注销成功", "topRight");
                setTimeout(function () {
                    window.location.href = "../page/Z_logIn.html"
                }, 1000)
            } else {
                notify.error(data.msg, "topRight");
            }
        }, error: function () {
            notify.error("请求失败", "topRight");
        }
    })
}



