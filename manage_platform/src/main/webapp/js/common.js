function reload(){
    window.location.reload();
}

//发送请求保存params操作
function savePost(path,urlParamStr,_this) {
    $.ajax({
        type: "POST",
        url: path,
        data: urlParamStr,
        success: function (result) {
            if (result.code == 0) {
                data = result.data;
                alert("保存成功");
                $(_this).parent().parent().find("input[name='id']").val(data.id);
            } else {
                alert("保存失败：" + result.message);
            }
        },
        error: function (result) {
            alert(result.message);
        }
    });

}

function deletePost(id,url,_this) {
    $.MsgBox.Confirm("删除提示","确定要执行删除操作吗",function(){
        if(null != id && "undefined"!= id ){
            $.post(url,{id:id},function(data,status){
                if(0!=data.code){
                    alert(data.message);
                } else {
                    $(_this).parent().parent().remove();
                }
            });
        }
        $(_this).parent().parent().remove();
    })
}