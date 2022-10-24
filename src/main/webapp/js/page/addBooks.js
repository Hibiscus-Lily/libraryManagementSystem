//添加图书页
const token = localStorage.getItem('token');

export function addBook(form, laydate) {
    //日期
    laydate.render({
        elem: '#year'
        , type: 'month'
    });
    //自定义验证规则
    form.verify({
        title: function (value) {
            console.log(value)
            if (value === '') {
                return '标题不能为空';
            }
        }
    });


    //提交事件
    form.on('submit(addBook)', function (data) {
        $.ajax({
            url: "http://localhost:8080/libraryManagementSystem/book/",
            type: "POST",
            data: JSON.stringify(data.field),
            headers: {
                "content-type": "application/json; charset=utf-8",// 或者添加这一行
                "token": token
            },
            success: function (res) {
                //表单赋值
                form.val('addBook', {
                    "title": ''
                    , "author": ''
                    , "press": ''
                    , "year": ''
                    , "ISBN": ''
                });
                layer.msg(res.msg)

            },
            error: function () {
                layer.msg("添加失败,请重新尝试")
            }
        })
        return false;
    });
}


