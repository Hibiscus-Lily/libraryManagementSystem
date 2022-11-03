layui.extend({
    layselect: '{/}../lib/modules/layselect', // {/}的意思即代表采用自有路径，即不跟随 base 路径
})
layui.use(['form', 'layselect'], function () {
    const select = layui.layselect, form = layui.form;
    const token = localStorage.getItem('token');

    select.render({
        elem: "#competence",
        option: [
            {code: '0', codeName: '普通用户'},
            {code: '1', codeName: '管理员'},
        ],
        select: '0',
    });
    select.render({
        elem: "#accountStatus",
        option: [
            {code: '0', codeName: '正常'},
            {code: '1', codeName: '封禁'},],
        select: '0',
    });
    select.render({
        elem: "#loginStatus",
        option: [
            {code: '0', codeName: '离线'},
            {code: '1', codeName: '在线'},],
        select: '0',
    });
    //自定义验证规则
    form.verify({
        nickname: function (value) {
            if (value === '') {
                return '昵称不能为空';
            }
        },
        account: function (value) {
            if (value === '') {
                return '账号不能为空';
            }
        },

        password: function (value) {
            if (value === '') {
                return '密码不能为空';
            }
            if (value.length !== 32 && value.length >= 16) {
                return '密码过长';
            }
        }
    });
    //提交事件
    form.on('submit(submitButton)', function (data) {

        let passwordMD5;
        if (data.field.password.length !== 32) {
            const MD5 = (hex_md5(data.field.password))
            passwordMD5 = hex_md5(MD5)
        } else {
            passwordMD5 = data.field.password
        }
        const data2 = {
            "username": data.field.nickname
            , "account": data.field.account
            , "password": passwordMD5
            , "state": parseInt(data.field.accountStatus)
            , "jurisdiction": parseInt(data.field.competence)
            , "loginStatus": parseInt(data.field.loginStatus)
        };

        $.ajax({
            url: "http://localhost:8080/libraryManagementSystem/admin/user",
            type: "POST",
            data: JSON.stringify(data2),
            headers: {
                "content-type": "application/json; charset=utf-8",// 或者添加这一行
                "token": token
            },
            success: function (res) {
                //表单赋值
                form.val('example', {
                    "nickname": ''
                    , "account": ''
                    , "password": ''
                    , "accountStatus": '0',
                    "competence": '0',
                    "loginStatus": '0'
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
});

$("#reset").click(function () {
    layui.use(['form', 'util', 'laydate'], function () {
        const form = layui.form;

        form.val('example', {
            "account": '',
            "accountStatus": '0',
            "competence": '0',
            "loginStatus": '0'
        });
    });
});

