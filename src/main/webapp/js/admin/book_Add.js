//添加图书页
layui.extend({
    layselect: '{/}../lib/modules/layselect', // {/}的意思即代表采用自有路径，即不跟随 base 路径
})
layui.use(['element', 'table', 'form', 'laydate', 'layselect'], function () {
    const form = layui.form;
    const select = layui.layselect;
    const laydate = layui.laydate;
    addBook(form, laydate, select)

});


function addBook(form, laydate, select) {
    const token = localStorage.getItem('token');

    select.render({
        elem: "#state",
        option: [
            {code: '0', codeName: '未借阅'},
            {code: '1', codeName: '借阅中'},
        ],
    });
    //日期
    laydate.render({
        elem: '#year'
        , type: 'month'
    });
    //自定义验证规则
    form.verify({
        title: function (value) {
            if (value === '') {
                return '标题不能为空';
            }
        }
    });


    //提交事件
    form.on('submit(addBook)', function (data) {
        console.log(data)
        $.ajax({
            url: "http://localhost:8080/libraryManagementSystem/admin/book/",
            type: "POST",
            data: JSON.stringify(data.field),
            headers: {
                "content-type": "application/json; charset=utf-8",// 或者添加这一行
                "token": token
            },
            success: function (res) {
                console.log(res)
                //表单赋值
                form.val('addBook', {
                    "title": ''
                    , "author": ''
                    , "press": ''
                    , "year": ''
                    , "ISBN": ''
                    ,"state":'0'
                });
                if (res.data === true) {
                    notify.success(res.msg, "topRight");
                } else {
                    notify.error(res.msg, "topRight");
                }
            },
            error: function () {
                notify.error("添加失败,请重试", "topRight");
            }
        })
        return false;
    });
}


