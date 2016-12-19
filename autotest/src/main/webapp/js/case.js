/**
 * Created by xn056839 on 2016/12/6.
 */
$(function  getMethodName() {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: "number3.aspx/GetJson",//传入后台的地址/方法
        data: "{'RID':'123'}",//参数，这里是一个json语句
        success: function (data) {
            var result = data.d;
            alert(result);
        },
        error: function (err) {
            alert("err:" + err);
        }
    });
});