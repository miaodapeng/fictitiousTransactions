$(function(){
});
// 验证筛选查询
function search() {
    checkLogin();
    var begin = $("#beginValue").val();
    var end =  $("#endValue").val();
    if(eval(begin) > eval(end)){
        layer.alert("筛选项沟通时长开始不允许大于结束");
        return false;
    }
    $("#search").submit();
}

