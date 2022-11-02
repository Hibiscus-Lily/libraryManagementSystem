/**
 * 查询用户
 */

layui.use(['table'], function () {


    //加载事件
    const table = layui.table


    loadTable(table)
    queryBook(table)
});


const token = localStorage.getItem('token');

function queryBook(table) {
    const $ = layui.$, active = {
        reload: function () {
            const search = $('#searchUserSearchBox');
            if (search.val() === '') {
                loadTable(table)
            } else {
                //执行重载
                table.reload('searchUser', {
                    url: 'http://localhost:8080/libraryManagementSystem/admin/user/' + search.val(),
                    headers: {
                        "token": token
                    },
                    parseData: function (res) {
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": 1, //解析数据长度
                            "data": res.data //解析数据列表
                        };
                    }
                });
            }

        }
    };

    $('.layui-btn').on('click', function () {
        const type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
}

//加载图书列表
/**
 * 查询图书页
 * 加载图书列表并加载工具条事件
 * @param table
 */
function loadTable(table) {
    //第一个实例
    table.render({
        elem: '#searchUser'
        , url: 'http://localhost:8080/libraryManagementSystem/admin/user/getAllUserInformation', //数据接口
        headers: {
            "token": token
        },
        page: true //开启分页
        , limit: 30
        , parseData: function (res) {
            if (res.code === 0) {
                notify.success(res.msg, "topRight");
            } else {
                notify.error(res.msg, "topRight");
            }
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        }, error: function () {
            notify.error("数据请求失败请稍后尝试", "topRight");

        }
        , cols: [[ //表头
            {field: 'username', title: '昵称', sort: true, fixed: 'left'}
            , {field: 'account', title: '账号', sort: true}
            , {field: 'password', title: '密码'}
            , {field: 'state', title: '账号状态', align: 'center', templet: '#accountStatus'}
            , {field: 'jurisdiction', title: '权限', align: 'center', templet: '#competence'}
            , {field: 'loginStatus', title: '登录状态', align: 'center', templet: '#loginStatus'}
            , {fixed: 'right', align: 'center', toolbar: '#searchUserToolbar'} //这里的toolbar值是模板元素的选择器

        ]],
    });

    //工具条事件
    table.on('tool(searchUser)', function (obj) { //注：tool 是工具条事件名，lay-filter="对应的值"
        const data = obj.data; //获得当前行数据
        const layEvent = obj.event; //获得 lay-event 对应的值
        sessionStorage.setItem("allJson", JSON.stringify(data));//将获取到的json字符串，保存到键为allJson中。
        //删除
        if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                $.ajax({
                    url: "http://localhost:8080/libraryManagementSystem/admin/user/" + data.title,
                    type: "DELETE",
                    headers: {
                        "token": token
                    },
                    success: function (res) {
                        if (res.data === true) {
                            notify.success(res.msg, "topRight");
                        } else {
                            notify.error(res.msg, "topRight");
                        }
                    }, error: function () {
                        notify.error("删除失败请稍后尝试", "topRight");
                    }

                })
            });
            //编辑
        } else if (layEvent === 'edit') {
            layer.open({
                skin: 'layui-layer-molv',
                type: 2,
                title: '编辑图书信息',
                area: ['500px', '500px'],
                offset: '10px',
                id: 'LAY_layuipro', //设定一个id，防止重复弹出
                content: '../page/Y_userUpdatePage.html?' + data,
                scrollbar: false,
            });


            //获取子页面的相关数据
            window.getSubPageElements = function (data) {

                if (data !== "") {
                    $.ajax({
                        url: "http://localhost:8080/libraryManagementSystem/admin/user",
                        type: "PUT",
                        data: JSON.stringify({
                            "username":data["nickname"],
                            "account":data["account"],
                            "password":data["password"],
                            "state":data["accountStatus"],
                            "jurisdiction":data["competence"],
                            "loginStatus":data["loginStatus"]
                        }),
                        headers: {
                            "content-type": "application/json; charset=utf-8" // 或者添加这一行
                            , "token": token
                        },
                        success: function (res) {
                            //更新相关条目数据
                            obj.update({
                                "username":data["nickname"],
                                "account":data["account"],
                                "password":data["password"],
                                "state":data["accountStatus"],
                                "jurisdiction":data["competence"],
                                "loginStatus":data["loginStatus"]
                            });
                            if (res.data === true) {
                                notify.success(res.msg, "topRight");
                            } else {
                                notify.error(res.msg, "topRight");
                            }

                        },
                        error: function () {
                            notify.success("更新失败,请重新尝试", "topRight");
                        }

                    })
                }
            }
        }
    });
}

