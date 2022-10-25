/**
 * 查询图书页
 * 查询图书页搜索栏相关事件(点击搜索,表格重载)
 * @param table
 */
const token = localStorage.getItem('token');

export function queryBook(table) {
    const $ = layui.$, active = {
        reload: function () {
            const search = $('#search');
            if (search.val() === '') {
                loadTable(table)
            } else {
                //执行重载
                table.reload('searchBook', {
                    url: 'http://localhost:8080/libraryManagementSystem/book/' + search.val(),
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
export function loadTable(table) {
    //第一个实例
    table.render({
        elem: '#searchBook'
        , url: 'http://localhost:8080/libraryManagementSystem/book/allBookInformation', //数据接口
        headers: {
            "token": token
        },
        page: true //开启分页
        , limit: 30
        , parseData: function (res) {
            layer.msg(res.msg)
            if (res.code !== 0) {
                setTimeout(function () {
                    window.location.href = "./page/user.html"
                }, 1000)
            }
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.list //解析数据列表
            };
        }
        , cols: [[ //表头
            {field: 'title', title: '书名', sort: true, fixed: 'left'}
            , {field: 'author', title: '作者', sort: true}
            , {field: 'press', title: '出版社'}
            , {field: 'year', title: '出版日期'}
            , {field: 'isbn', title: 'isbn'}
            , {field: 'borrowing', title: '借阅情况',align: 'center', templet: '#titleTpl'}
            , {fixed: 'right', align: 'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器

        ]],
    });

    //工具条事件
    table.on('tool(searchBook)', function (obj) { //注：tool 是工具条事件名，lay-filter="对应的值"
        const data = obj.data; //获得当前行数据
        const layEvent = obj.event; //获得 lay-event 对应的值
        sessionStorage.setItem("allJson", JSON.stringify(data));//将获取到的json字符串，保存到键为allJson中。
        //删除
        if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                $.ajax({
                    url: "http://localhost:8080/libraryManagementSystem/book/" + data.title,
                    type: "DELETE",
                    headers: {
                        "token": token
                    },
                    success: function (data) {
                        layer.msg(data.msg)
                    }

                })
            });
            //编辑
        } else if (layEvent === 'edit') {
            layer.open({
                skin: 'layui-layer-molv',
                type: 2,
                title: '编辑图书信息',
                area: ['480px', '430px'],
                shade: 0.8, // 遮罩层透明度
                id: 'LAY_layuipro', //设定一个id，防止重复弹出
                resize: false, //是否允许拉伸
                moveType: 1, //拖拽模式，0或者1
                content: 'page/queryBookPagePopUpForm.html?' + data,
                scrollbar: false,
            });
            //获取子页面的相关数据
            window.getSubPageElements = function (data) {
                if (data !== "") {
                    $.ajax({
                        url: "http://localhost:8080/libraryManagementSystem/book",
                        type: "PUT",
                        data: JSON.stringify(data),
                        headers: {
                            "content-type": "application/json; charset=utf-8" // 或者添加这一行
                            , "token": token
                        },
                        success: function (res) {
                            //更新相关条目数据
                            obj.update({
                                title: data.title,
                                author: data.author,
                                press: data.press,
                                year: data.year,
                                isbn: data.isbn,
                            });
                            layer.msg(res.msg)
                        },
                        error: function () {
                            layer.msg("更新失败,请重新尝试")
                        }

                    })

                }
            }

        }
    });

}
