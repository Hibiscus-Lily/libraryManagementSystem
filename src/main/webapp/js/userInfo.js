const token = localStorage.getItem('token');

layui.use('laytpl', function () {
    const laytpl = layui.laytpl;

    const data = JSON.parse(localStorage.getItem('data'));
    console.log(data)
    $('#avatar').attr('src', data["avatarurl"])

    document.body.style.backgroundImage='url('+data["background"]+')'

    const getTpl = document.getElementById('demo').innerHTML
        , view = document.getElementById('view');
    laytpl(getTpl).render(data, function (html) {
        view.innerHTML = html;
    });
})


