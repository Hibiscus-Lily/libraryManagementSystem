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
            const search = $('#search');
            if (search.val() === '') {
                loadTable(table)
            } else {
                //执行重载
                table.reload('searchBook', {
                    url: 'http://localhost:8080/libraryManagementSystem/commonuser/book/' + search.val(),
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
        elem: '#searchBook'
        , url: 'http://localhost:8080/libraryManagementSystem/commonuser/book/allBookInformation', //数据接口
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
            notify.error("请求异常请稍后尝试", "topRight");
        }
        , cols: [[ //表头
            {field: 'title', title: '书名', sort: true, fixed: 'left'}
            , {field: 'author', title: '作者', sort: true}
            , {field: 'press', title: '出版社'}
            , {field: 'year', title: '出版日期'}
            , {field: 'isbn', title: '国际标准书号'}
            , {field: 'state', title: '借阅情况', align: 'center', templet: '#state'}
            , {fixed: 'right', align: 'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器

        ]],
    });

    //工具条事件
    table.on('tool(searchBook)', function (obj) { //注：tool 是工具条事件名，lay-filter="对应的值"
        console.log(data)
        const layEvent = obj.event; //获得 lay-event 对应的值
        //删除
        if (layEvent === 'borrow') {
            layer.confirm('真的行么', function (index) {
                layer.close(index);
                const data = {
                    "title": obj.data.title,
                    "account": token,
                    "time": Date.parse(new Date()) / 1000
                }
                $.ajax({
                    url: "http://localhost:8080/libraryManagementSystem/commonuser/book",
                    type: "POST",
                    data: JSON.stringify(data),
                    headers: {
                        "content-type": "application/json; charset=utf-8" // 或者添加这一行
                        , "token": token
                    },
                    success: function (res) {
                        //更新相关条目数据
                        obj.update({
                            state: 1,
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
            });
            //编辑
        }
    });
}