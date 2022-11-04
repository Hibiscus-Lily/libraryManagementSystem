layui.use(['table'], function () {
    //加载事件
    const table = layui.table

    loadTable(table)
    queryBook(table)
});


const token = localStorage.getItem('token');

function queryBook(table) {
    const $ = layui.$, searchByTitle = {
        reload: function () {
            const search = $('#searchByTitle');
            if (search.val() === '') {
                loadTable(table)
            } else {
                //执行重载
                table.reload('loanForm', {
                    url: 'http://localhost:8080/libraryManagementSystem/commonuser/borrow/getAllBorrowingRecordsForBookTile?title=' + search.val(),
                    headers: {
                        "token": token
                    },
                    page: true //开启分页
                    , limit: 30,
                    parseData: function (res) {
                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.total, //解析数据长度
                            "data": res.data.list //解析数据列表
                        };
                    }
                });
            }

        }
    }
    $('#searchByTitle_reload').on('click', function () {
        const type = $(this).data('type');
        searchByTitle[type] ? searchByTitle[type].call(this) : '';
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
        elem: '#loanForm'
        , url: 'http://localhost:8080/libraryManagementSystem/commonuser/borrow/findAllBorrowingRecordsByUserAccount', //数据接口
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
            {field: 'title', title: '书名', sort: true,fixed: 'left'}
            , {field: 'account', title: 'account', sort: true}
            , {field: 'borrowingTime', title: '借阅时间', align: 'center', templet: '#borrowingTime'}
            , {field: 'bookReturnTime', title: '还书时间', align: 'center', templet: '#bookReturnTime'}
            , {field: 'estimatedReturnTime', title: '预计还书时间', align: 'center', templet: '#estimatedReturnTime'}

        ]],
    });
}

