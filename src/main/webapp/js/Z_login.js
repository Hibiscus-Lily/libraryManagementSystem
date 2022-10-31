$(function () {
    console.log(document.cookie)

    // 页面初始化生成验证码
    createCode('#loginCode');
    // 验证码切换
    $('#loginCode').click(function () {
        createCode('#loginCode');
    });
    // 登陆事件
    $('#loginBtn').click(function () {
        login();
    });
    // 注册事件
    $('#loginRegister').click(function () {
        register();
    });
});


// 生成验证码
function createCode(codeID) {
    let code = "";
    // 验证码长度
    const codeLength = 4;
    // 验证码dom元素
    const checkCode = $(codeID);
    // 验证码随机数
    const random = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
    for (let i = 0; i < codeLength; i++) {
        // 随机数索引
        const index = Math.floor(Math.random() * 36);
        code += random[index];
    }
    // 将生成的随机验证码赋值
    checkCode.val(code);
}


// 校验验证码、用户名、密码
function validateCode(inputID, codeID) {
    const inputCode = $(inputID).val().toUpperCase();
    const cardCode = $(codeID).val();
    const loginUsername = $('#loginUsername').val();
    const loginPassword = $('#loginPassword').val();
    if ($.trim(loginUsername) === '' || $.trim(loginUsername).length <= 0) {
        layer.msg("用户名不能为空");
        createCode('#loginCode');

        return false;
    }
    if ($.trim(loginPassword) === '' || $.trim(loginPassword).length <= 0) {
        layer.msg("密码不能为空");
        createCode('#loginCode');

        return false;
    }
    // if (inputCode.length <= 0) {
    //     layer.msg("验证码不能为空");
    //     createCode('#loginCode');
    //
    //     return false;
    // }
    // if (inputCode !== cardCode) {
    //     layer.msg("请输入正确验证码");
    //     createCode('#loginCode');
    //
    //     return false;
    // }
    return true;
}


// 登录流程
function login() {
    if (!validateCode('#loginCard', '#loginCode')) {
        //阻断提示
    } else {
        const loginLoadIndex = layer.load(2);
        $('#loginBtn').val("正在登录...");
        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/libraryManagementSystem/user/userLogin/',
            data: {
                "account": $('#loginUsername').val(),
                "passwordRSA": passwordEncryption($('#loginPassword').val())
            },
            success: function (data) {
                layer.close(loginLoadIndex);
                layer.msg(data.msg)
                if (data.code === 0) {
                    let jurisdiction = (data.data["jurisdiction"])
                    if (jurisdiction === 0) {
                        setTimeout(function () {
                            window.location.href = '../admin.html';
                        }, 3000);
                    }
                    if (jurisdiction === 1) {
                        setTimeout(function () {
                            window.location.href = '../admin.html';
                        }, 3000);
                    } else {
                        createCode('#loginCode');
                        layer.close(loginLoadIndex);
                        $('#loginBtn').val("登录");
                    }
                } else {
                    createCode('#loginCode');
                    layer.close(loginLoadIndex);
                    $('#loginBtn').val("登录");
                }
            },
            complete: function (xhr) { //请求完成后，获取fileName，处理数据
                localStorage.setItem('token', xhr.getResponseHeader("token"))
            },
            error: function () {
                layer.msg("请求错误")
                layer.close(loginLoadIndex);
                $('#loginBtn').val("登录");
                setTimeout(function () {
                    location.reload();
                }, 3000);

            }
        });
    }

}


// 注册流程
function register() {
    layer.open({
        type: '1',
        content: $('.registerPage'),
        title: '注册',
        area: ['430px', '400px'],
        btn: ['注册', '重置', '取消'],
        closeBtn: '1',
        btn1: function (index) {
            //注册回调
            layer.close(index);
            const registerUsername = $('#registerUsername').val();
            const registerPassword = $('#registerPassword').val();
            const registerWellPassword = $('#registerWellPassword').val();
            const selectValue = $('#roleSelect option:selected').val();
            const params = {};
            params.registerUsername = registerUsername;
            params.registerPassword = registerPassword;
            params.registerWellPassword = registerWellPassword;
            params.selectValue = selectValue;
            const registerLoadIndex = layer.load(2);
            $.ajax({
                type: 'post',
                url: window.location.protocol + '//' + window.location.host + '/security-web/register.do',
                dataType: 'json',
                data: JSON.stringify(params),
                contentType: 'application/json',
                success: function (data) {
                    layer.close(registerLoadIndex);
                    layer.msg(data);
                },
                error: function () {
                    layer.close(registerLoadIndex);
                    layer.alert("请求超时！")
                }
            });
        },

    })
}

//密码加密处理
function passwordEncryption(password) {
    const PUBLIC_KEY = '-----BEGIN PUBLIC KEY----- MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCODnqqHY+9aBdERXjNe+qlMjuMApfJyWUqwiv4diobtgQPSao//BrfVXPTcoLJtODLZ4EdG2+Hw2/I1a3MHg5Gl26ejXwVfmuaIfEsPDMW++tNSXZRoTHFWFNSTdNLLYFqbomaVRhZDhqDEbHrheD6Ag33cxzCCFt50Reb99YilQIDAQAB -----END PUBLIC KEY-----';
    const encrypt = new JSEncrypt();
    const MD5 = (hex_md5(password))
    const passwordMD5 = hex_md5(MD5)
    encrypt.setPublicKey(PUBLIC_KEY);
    return encrypt.encrypt(passwordMD5)
}