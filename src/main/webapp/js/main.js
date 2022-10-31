//引入相关JS文件
import {addBook} from './book_Add.js';
import {loadTable, queryBook} from './book_Load';


layui.use(['element', 'table', 'form', 'laydate'], function () {


    //加载事件
    const element = layui.element;
    const table = layui.table
    const form = layui.form;
    const laydate = layui.laydate;

    //获取相关元素
    const box = document.getElementById("data");   //获取dataDOM节点
    const boxData = box.getElementsByClassName('transform')  //获取data下class="transform"的所有子节点

    //隐藏和显示的相关操作
    $(boxData).hide()
    $(boxData.item(0)).show()
    $(box).show()


    //执行页面相关函数


    //查询图书页面
    //加载图书表格
    loadTable(table)
    //搜索栏相关事件
    queryBook(table)


    //添加图书页面
    addBook(form, laydate)


    //导航页面内容切换
    element.on('nav(pageContentSwitch)', function (data) {
        //layui-body页面内容切换
        $(window).ready(function () {
            if (data[0].attributes[1] != null) {
                let index
                index = data[0].attributes[1].nodeValue;
                $(boxData.item(index)).show().siblings(boxData).hide();
            }
        })
    });
});

