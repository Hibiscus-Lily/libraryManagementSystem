$(function () {

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
    if (inputCode.length <= 0) {
        layer.msg("验证码不能为空");
        createCode('#loginCode');

        return false;
    }
    if (inputCode !== cardCode) {
        layer.msg("请输入正确验证码");
        createCode('#loginCode');

        return false;
    }
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
            url: 'http://localhost:8080/libraryManagementSystem/login/userLogin',
            data: {
                "account": $('#loginUsername').val(),
                "passwordRSA": passwordEncryption($('#loginPassword').val())
            },
            success: function (res) {
                layer.close(loginLoadIndex);
                const account = (res["data"]["account"])
                console.log(account)
                //code码为0
                if (res["code"] === 0) {
                    if (account !== null) {
                        let jurisdiction = (res["data"]["jurisdiction"])
                        if (jurisdiction === 0) {
                            notify.success(res["msg"], "topRight")
                            setTimeout(function () {
                                window.location.href = 'book.html';
                            }, 3000);
                        } else if (jurisdiction === 1) {
                            notify.success(res["msg"], "topRight")
                            setTimeout(function () {
                                window.location.href = 'admin.html';
                            }, 3000);
                        } else {
                            createCode('#loginCode');
                            layer.close(loginLoadIndex);
                            notify.error("ERROR", "topRight")
                            $('#loginBtn').val("登录");
                        }
                    } else {
                        createCode('#loginCode');
                        layer.close(loginLoadIndex);
                        notify.error(res["msg"], "topRight")
                        $('#loginBtn').val("登录");
                    }

                } else {
                    //code码不为0
                    notify.error(res["msg"], "topRight")
                    createCode('#loginCode');
                    layer.close(loginLoadIndex);
                    $('#loginBtn').val("登录");
                }
            },
            complete: function (xhr) { //请求完成后，获取fileName，处理数据
                localStorage.setItem('token', xhr.getResponseHeader("token"))
            },
            error: function () {
                notify.error('请求错误', "topRight")
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
        btn: ['注册'],
        closeBtn: '1',
        btn1: function (index) {
            const registerUsername = $('#registerUsername').val();
            const registerPassword = $('#registerPassword').val();
            const registerWellPassword = $("#registerWellPassword").val();

            if (registerUsername === '') {
                notify.error("账号为空", "topRight")
            } else if (registerPassword === '') {
                notify.error("密码为空", "topRight")
            } else if (registerWellPassword === '') {
                notify.error("确认密码为空", "topRight")
            } else if (registerWellPassword !== registerPassword) {
                notify.error("密码不同", "topRight")
            } else if (registerWellPassword === registerPassword && registerUsername !== '') {
                const MD5 = (hex_md5(registerPassword))  //第一次加密
                const passwordMD5 = hex_md5(MD5)  //第二次加密
                const params = {};
                params.username = registerUsername;
                params.account = registerUsername;
                params.password = passwordMD5;
                //注册回调
                const registerLoadIndex = layer.load(2);
                $.ajax({
                    type: 'post',
                    url: 'http://localhost:8080/libraryManagementSystem/login',
                    dataType: 'json',
                    data: JSON.stringify(params),
                    contentType: 'application/json',
                    success: function (data) {
                        layer.close(registerLoadIndex);
                        if (data.data === true) {
                            notify.success(data.msg, "topRight")
                            layer.close(index);
                            $('#registerPassword').val("");
                            $('#registerUsername').val('');
                            $("#registerWellPassword").val('');
                            $("#passwordVerification").fadeOut(0)
                        } else if (data.data === false) {
                            notify.error(data.msg, "topRight")
                        } else {
                            notify.error("注册失败请稍后尝试", "topRight")
                        }

                    },
                    error: function () {
                        layer.close(registerLoadIndex);
                        layer.alert("请求超时！")
                    }
                });
            }
            return false
        },
        cancel: function (index) {
            layer.close(index)
            $('#registerPassword').val("");
            $('#registerUsername').val('');
            $("#registerWellPassword").val('');
            $("#passwordVerification").fadeOut(0)
            return false;
        }

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

/**
 * 密码检验
 */
//密码框值不变，监听确认密码框
$(function () {
    $("#passwordVerification").fadeOut(0)
    $("#registerWellPassword").bind("input propertychange", function () {
        //获取密码框的值
        const registerPassword = $("#registerPassword").val()
        const registerWellPassword = $("#registerWellPassword").val()
        if (registerPassword !== '') {
            if (registerWellPassword === registerPassword) {
                $("#passwordVerification").fadeOut(500)
            }
            if (registerWellPassword !== registerPassword) {
                if (registerWellPassword === '') {
                    $("#passwordVerification").fadeOut(500)
                } else {
                    $("#passwordVerification").fadeIn(500)

                }
            }
        }
    });
    //确认密码框值不变，监听密码框
    $("#registerPassword").bind("input propertychange", function () {
        const registerWellPassword = $("#registerWellPassword").val()
        const registerPassword = $("#registerPassword").val()
        if (registerWellPassword !== '') {
            if (registerPassword === registerWellPassword) {
                $("#passwordVerification").fadeOut(500)
            }
            if (registerPassword !== registerWellPassword) {
                if (registerPassword === '') {
                    $("#passwordVerification").fadeOut(500)
                } else {
                    $("#passwordVerification").fadeIn(500)

                }
            }
        }
    });
});






