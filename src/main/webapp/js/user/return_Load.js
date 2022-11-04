layui.use(['table'], function () {
    //加载事件
    const table = layui.table
    loadTable(table)
});


const token = localStorage.getItem('token');

//加载图书列表
/**
 * 查询图书页
 * 加载图书列表并加载工具条事件
 * @param table
 */
function loadTable(table) {
    //第一个实例
    table.render({
        elem: '#searchBook'
        , url: 'http://localhost:8080/libraryManagementSystem/commonuser/borrow/findAllUnreturnedBooks', //数据接口
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
                setTimeout(function () {
                    window.location.href = "../../page/Z_logIn.html"
                }, 1000)
            }
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        }, error: function () {
            notify.error("请求异常请稍后尝试", "topRight");
        }
        , cols: [[ //表头
            {field: 'title', title: '书名', sort: true}
            , {field: 'borrowingTime', title: '借阅时间', align: 'center', templet: '#borrowingTime'}
            , {field: 'estimatedReturnTime', title: '预计还书时间', align: 'center', templet: '#estimatedReturnTime'}
            , {fixed: 'right', align: 'center', toolbar: '#returnBook'} //这里的toolbar值是模板元素的选择器

        ]],
    });

    //工具条事件
    table.on('tool(searchBook)', function (obj) { //注：tool 是工具条事件名，lay-filter="对应的值"
        const layEvent = obj.event; //获得 lay-event 对应的值
        //删除
        if (layEvent === 'returnBook') {
            layer.confirm('真的行么', function (index) {
                layer.close(index);
                const data = {
                    "title": obj.data.title,
                    "bookReturnTime": Date.parse(new Date()) / 1000
                }
                $.ajax({
                    url: "http://localhost:8080/libraryManagementSystem/commonuser/borrow",
                    type: "Delete",
                    data: JSON.stringify(data),
                    headers: {
                        "content-type": "application/json; charset=utf-8" // 或者添加这一行
                        , "token": token
                    },
                    success: function (res) {
                        if (res.data === true) {
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            notify.success(res.msg, "topRight");
                        } else {
                            notify.error(res.msg, "topRight");
                        }
                    },
                    error: function () {
                        notify.success("还书失败,请重新尝试", "topRight");
                    }

                })
            });
        }

    });
}