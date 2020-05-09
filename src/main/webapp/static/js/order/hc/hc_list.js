
// 初始化
$(function() 
{
	
});

function submitForm(id)
{
	$("#"+id).submit();
}

function formReset(id)
{
	reset();
	submitForm(id);
}
function getUserList() {
	var orgId = $("select[name='orgId']").val();
	if (orgId == -1){
		orgId = 0;
	}
	$.ajax({
		type : "POST",
		url : page_url + '/order/hc/getUserListByOrgId.do',
		data : {orgId:orgId},
		dataType : 'json',
		success : function(data) {
			if (data.code == 0){
				var userList = data.listData;
				var html = "<option value=\"-1\">全部</option>";
				console.log(userList.length );
				if (userList!=null && userList.length > 0) {
					for (var i = 0; i < userList.length; i++) {
						if (userList[i]!=null){
							html = html + "<option value=\"" + userList[i].userId + "\">" + userList[i].username + "</option>";
						}
					}
				}
				$("select[name='optUserId']").empty();
				$("select[name='optUserId']").append(html);
			}else{
				layer.alert(data.message);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限");
			}
		}
	});
}