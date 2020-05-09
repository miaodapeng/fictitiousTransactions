//获取当前页面请求路径
var curWwwPath = window.document.location.href;
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
if(projectName != '/erp'){
	projectName = "";
}
var page_url = curWwwPath.substring(0, pos) + projectName;
var parentWindow = $(window.parent.document);
//弹框关闭按钮：添加执行事件
var index;
$(document).ready(function(){
	var $title_name = $(window.parent.document).find('.active').eq(1).children('iframe').contents().find('title');
	console.log($title_name)
	if("首页" != $(window.parent.document).find('.active').eq(0).find('span').html()){
		if($title_name != undefined && $title_name.text()!=undefined && $title_name.text()!=""){
			if($title_name.text() != $(window.parent.document).find('.active').eq(0).find('span').html()){
				$(window.parent.document).find('.active').eq(0).find('span').html($title_name.text());
				$(window.parent.document).find('.active').eq(0).find('span').attr("title",$title_name.text());
			}
		}
	};
	if(parent.layer!=undefined){
		try{
		index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
		$('#cancle').on('click', function() {
			parent.layer.close(index); //执行关闭
		});
		}catch (e) {

		}
	}
	
	//回车事件
    document.onkeydown = function(e){
        if(!e){
         e = window.event;
        }
        if((e.keyCode || e.which) == 13){
       	 	$('.warning').remove();//移除错误提示
       	 	$("a.layui-layer-btn0").click();//触发layer.alert弹框确认按钮事件
        }
    }
    //第一个文本框获取光标
//    $("form :input").eq(0).focus();
    //if($("form :input[type='text']").val()=='' && $("form :input[type='text']").length>0){
    	$("form :input[type='text']").each(function(){
    		if($(this).attr("class").indexOf("Wdate")==-1){
    			$(this).focusEnd();
    			$("form :input").blur(function(){$(this).val($(this).val().trim());});
    			return false;
    		}
    	});
    //}
    
    //为列表页搜索框添加回车事件
    if($("#search")!=undefined && $("#search").html()!=undefined){
    	$("#search :input").keydown(function (e) {
    	      if (e.which == 13) {
    	    	  $("#search :input").each(function(){
    	    		  $(this).val($(this).val()==null?"":$(this).val().trim());
    	    	  })
    	        $("#search").submit();
    	        return false;
    	      }
    	})
    }
    
    $("input[name='checkAll']").click(function(){
		if($(this).is(':checked')){
			$("input[name='checkOne']").each(function(){
				$(this).prop("checked",true);
			});
		}else{
			$("input[name='checkOne']").each(function(){
				$(this).prop("checked",false);
			});
		}
		
		var isCheckAll = 1;
		$("input[name='checkOne']").each(function(){
			if(!$(this).is(':checked')){
				isCheckAll = 0;
			}
		});
		if (isCheckAll == 1) {
			$("input[name='checkAll']").prop("checked",true);
		} else {
			$("input[name='checkAll']").prop("checked",false);
		}
	});
	
	$("input[name='checkOne']").click(function(){
		var isCheckAll = 1;
		$("input[name='checkOne']").each(function(){
			if(!$(this).is(':checked')){
				isCheckAll = 0;
			}
		});
		if (isCheckAll == 1) {
			$("input[name='checkAll']").prop("checked",true);
		} else {
			$("input[name='checkAll']").prop("checked",false);
		}
	});
	var id1 = parentWindow.find('.active').eq(1).attr('id');
	parentWindow.find('.active').eq(1).find('#loadingNewData-'+id1).remove();
	var frontPageId = parentWindow.find('.active').eq(1).children('iframe').attr('data-frontpageid');
	if(frontPageId != undefined && frontPageId != ""){
		$('#' + frontPageId, window.parent.document).find('.tip-loadingNewData').remove();
		//	var frameId = window.frameElement && window.frameElement.id || '';
		var frameObj = window.frameElement;
	}

});
//--------------------设定光标在文本框内容最后的位置-------------------
$.fn.setCursorPosition = function(position) {
	if (this.lengh == 0)
		return this;
	return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
	if (this.lengh == 0)
		return this;
	input = this[0];

	if (input.createTextRange) {
		var range = input.createTextRange();
		range.collapse(true);
		range.moveEnd('character', selectionEnd);
		range.moveStart('character', selectionStart);
		range.select();
	} else if (input.setSelectionRange) {
		input.focus();
		input.setSelectionRange(selectionStart, selectionEnd);
	}

	return this;
}

$.fn.focusEnd = function() {
	this.setCursorPosition(this.val().length);
} 
// ---------------------------------------------------------------------

// 判断当前字符串是否以str结束
if (typeof String.prototype.endsWith != 'function') {
  String.prototype.endsWith = function (str){
	  if(str != undefined && str != null){
		  return this.slice(-str.length) == str;
	  }
  };
}

//刷新父级页面(添加、删除)
function refreshPageList(data){
	if(data.code == 0){
//		layer.alert('操作成功', {
//			closeBtn: 0,
//			btn: ['确定'] //按钮
//		}, function(){
			if((parent.$("#searchSpan") != undefined) && (parent.$("#searchSpan").html() != undefined)){
				if(parent.$("#winPageNo").val()!=undefined && parent.$("#choosePageSize").val()!=undefined){
					var $obj = parent.$("body").children("div.content").children("div.searchfunc").children("form");
					$obj.append("<input type='hidden' name='pageNo' value='"+parent.$("#winPageNo").val()+"'/>"
							+"<input type='hidden' name='pageSize' value='"+parent.$("#choosePageSize").val()+"'/>");
				}
				
				parent.$("#searchSpan").click();
			}else if(($("#searchSpan") != undefined) && ($("#searchSpan").html() != undefined)){
				if($("#winPageNo").val()!=undefined && $("#choosePageSize").val()!=undefined){
					var $obj = $("body").children("div.content").children("div.searchfunc").children("form");
					$obj.append("<input type='hidden' name='pageNo' value='"+$("#winPageNo").val()+"'/>"+"<input type='hidden' name='pageSize' value='"+$("#choosePageSize").val()+"'/>");
				}
				
				$("#searchSpan").click();
			}else{
				var parentUrl = (window.parent.location).toString();
				if(parentUrl.endsWith('main.do')){
					if(parent.$("#searchSpan")==undefined || parent.$("#searchSpan").html()==undefined){
						window.location.reload();
						return;
					}else{
						if(parent.$("#winPageNo").val()!=undefined && parent.$("#choosePageSize").val()!=undefined){
							var $obj = parent.$("body").children("div.content").children("div.searchfunc").children("form");
							$obj.append("<input type='hidden' name='pageNo' value='"+parent.$("#winPageNo").val()+"'/>"+"<input type='hidden' name='pageSize' value='"+parent.$("#choosePageSize").val()+"'/>");
						}
						parent.$("#searchSpan").click();
					}
				}else{
					window.parent.location.reload();
				}
			}
			if(parent.layer!=undefined){
				parent.layer.close(index);
			}
//		});
	}
	if(data.code == -1){
		if(data.listData != null){
			$(".service-return-error").remove();
			var error = "<ul>";
			var errors = data.listData;
			for(var i=0;i<errors.length;i++){
				error += "<li>"+(i+1)+"、"+errors[i]['defaultMessage']+"</li>";
			}
			error += "</ul>";
			$("form").before("<div class='service-return-error'>"
					+ error
					+"</div>");
		}else{
			var msg = data.message != '' ? data.message : '操作失败';
//		$("form :input").eq(0).focus();
			layer.alert(msg);
		}
	}
	
}
//刷新父级当前数据页面(编辑)
function refreshNowPageList(data){
	if(data.code == 0){
//		layer.alert('操作成功', {
//			  closeBtn: 0,
//			  btn: ['确定'] //按钮
//			}, function(){
			if((parent.$("#searchSpan") != undefined) && (parent.$("#searchSpan").html() != undefined)){
				if(parent.$("#winPageNo") != undefined && parent.$("#winPageNo").val() != undefined){
					var $obj = parent.$("body").children("div.content").children("div.searchfunc").children("form");
					$obj.append("<input type='hidden' name='pageNo' value='"+parent.$("#winPageNo").val()+"'/>"
							+"<input type='hidden' name='pageSize' value='"+parent.$("#choosePageSize").val()+"'/>");
				}
				parent.$("#searchSpan").click();
				return false;
			}else if(($("#searchSpan") != undefined) && ($("#searchSpan").html() != undefined)){
				if($("#winPageNo") != undefined && $("#winPageNo").val() != undefined){
					var $obj = $("body").children("div.content").children("div.searchfunc").children("form");
					$obj.append("<input type='hidden' name='pageNo' value='"+$("#winPageNo").val()+"'/>"
							+"<input type='hidden' name='pageSize' value='"+$("#choosePageSize").val()+"'/>");
				}
				// $obj.submit();
				$("#searchSpan").click();
				return false;
			}else{
				window.location.reload();
			}
			
			layer.close(index);
//		});
	}
	if(data.code == -1){
		if(data.listData != null){
			$(".service-return-error").remove();
			var error = "<ul>";
			var errors = data.listData;
			for(var i=0;i<errors.length;i++){
				error += "<li>"+(i+1)+"、"+errors[i]['defaultMessage']+"</li>";
			}
			error += "</ul>";
			$("form").before("<div class='service-return-error'>"
					+ error
					+"</div>");
		}else{
			var msg = data.message != '' ? data.message : '操作失败';
	//		$("form :input").eq(0).focus();
			layer.alert(msg);
		}
	}
}

//页面字段排序
function sort(field,order){
	field = field.toUpperCase();
	order = order.toUpperCase();
	var $obj = $("thead").children("tr.sort");//获取子元素-class='sort'防止页面多个thead标签
	var obj_id = Math.random();//生成随机数
	//组装form表单
	var form = $('<form></form>');
	form.attr('action', pathName);
	form.attr('method', 'post');
	form.attr('id', 'sort'+obj_id);
	var my_input;
	$obj.children("th").each(function(i){//循环th标签
		if($(this).attr("abbr")!=undefined){//获取属性值
			if($(this).attr("abbr").toUpperCase()==field){//属性值与排序字段相等
				//验证降序、升序加载对应图标
				if(order=="DESC"){
					$(this).html($(this).html()+" DESC");
				}else if(order=="ASC"){
					$(this).html($(this).html()+" ASC");
				}
			}
			//为th标签添加点击事件
			$(this).on('click', function(j){
				if($(this).attr("abbr").toUpperCase()==field){
					//拼装参数
					if(order=="DESC"){
						my_input = $('<input type="hidden" name="sortField" value="'+field+'"/><input type="hidden" name="order" value="ASC"/>');
					}else{
						my_input = $('<input type="hidden" name="sortField" value="'+field+'"/><input type="hidden" name="order" value="DESC"/>');
					}
				}else{//初次点击偏排序，默认降序
					//拼装参数
					my_input = $('<input type="hidden" name="sortField" value="'+$(this).attr("abbr").toUpperCase()+'"/><input type="hidden" name="order" value="DESC"/>');
				}
				form.append(my_input);//input添加到form表单中
				form.appendTo($obj);//form表单添加到tr标签后
				form.submit();//表单提交
			    return false;//取消链接的默认动作
			});
		}
	});
};

//文本框验证--定位光标
function warn2Tips(id,txt){
	$("form :input").parents('div').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	$("#"+id).siblings('.warning').remove();
	$("#"+id).parent().after('<div class="warning">'+txt+'</div>');
	$("#"+id).focus();
	$("#"+id).addClass("errorbor");
	return false;
}

//文本框验证--定位光标
function warnTips(id,txt){
	$("form :input").parents('li').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	$("#"+id).siblings('.warning').remove();
	$("#"+id).after('<div class="warning">'+txt+'</div>');
	$("#"+id).focus();
	$("#"+id).addClass("errorbor");
	return false;
}

//文本框验证--无定位光标
function warnTipsDate(id,txt){
	$("form :input").parents('li').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	$("#"+id).siblings('.warning').remove();
	$("#"+id).after('<div class="warning">'+txt+'</div>');
	$("#"+id).addClass("errorbor");
	return false;
}
//文本框验证通过去掉错误提示
function delWarnTips(id){
	$("form :input").parents('li').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	$("#"+id).siblings('.warning').remove();
}

//文本框验证--
function warnErrorTips(id,errorId,txt){
	$("form :input").parents('li').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	$("#"+errorId).siblings('.warning').remove();
	$("#"+errorId).after('<div class="warning">'+txt+'</div>');
	$("#"+id).focus();
	$("#"+id).addClass("errorbor");
	return false;
}

//form表单重置
function reset(){
	$("form").find("input[type='text']").val('');
	$.each($("form select"),function(i,n){
		$(this).children("option:first").prop("selected",true);
	});
	//客户性质重置
	if($("select[name='customerProperty']") != undefined){
		$("select[name='customerProperty'] option:gt(0)").remove();
	}
	$("#cusProperty").val("");
	//地区市 区信息重置
	if($("select[name='city']") != undefined){
		$("select[name='city'] option:gt(0)").remove();
	}
	if($("select[name='zone']") != undefined){
		$("select[name='zone'] option:gt(0)").remove();
	}
}

//字符长度（中英文）
function strlen(str){
    var len = 0;
    for (var i=0; i<str.length; i++) { 
     var c = str.charCodeAt(i); 
    //单字节加1 
     if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) { 
       len++; 
     } 
     else { 
      len+=2; 
     } 
    } 
    return len;
}
//打开页面链接
function goUrl(url){
	location.href=url;
	//parent.document.getElementById('tab_frame_1').src=url;
}

//检测登录状态，给弹出框页面使用//add by john 
function checkLogin(){
	$.ajax({
		url: page_url+'/checkSession.do',
		dataType: "json",
		type:"POST",
		async: false,
		success: function (data) {
			if (data.code==0) {
			} else {
				layer.confirm("登录超时，请重新登录", {
					btn : [ '关闭' ]
				}, function() {
					top.location.href=page_url+'/login.do';
				});
			}
		}
	});
}


//清除错误信息
function clear2ErroeMes(){
	$("form :input").parents('div').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	
}
//清除错误信息
function clearErroeMes(){
	$("form :input").parents('li').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	
}

//清除错误信息
function clear2ErrorMsg(){
	$("form :input").parents('div').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	
}

//清除错误信息
function clearErrorMsg(){
	$("form :input").parents('li').find('.warning').remove();
	$("form :input").removeClass("errorbor");
	
}

function logout(){
	$.ajax({
		url: page_url+'/logout.do',
		dataType: "json",
		type:"POST",
		async: false,
		success: function (data) {
			if (data.code==0) {
				top.location.href=page_url+'/login.do';
			} else {
				layer.alert(data.message);
			}
		},
		error:function(data){
			top.location.href=page_url+'/login.do';
		}
	});
}

/**
 * 时间格式化年-月-日输出
 */
var formatDate = function (date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    return y + '-' + m + '-' + d;  
};

function getNowDateStr(){
	var mydate = new Date();
    var y = mydate.getFullYear();  
    var m = mydate.getMonth() + 1;  
    m = m < 10 ? '0' + m : m;  
    var d = mydate.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    return y + '_' + m + '_' + d;  
};

/**
 * 获取上一个月
 * 
 * @date 格式为yyyy-mm-dd的日期，如：2014-01-25
 */
function getPreMonth(cdate) {
	var date = formatDate(cdate);
	var arr = date.split('-');
	var year = arr[0]; // 获取当前日期的年份
	var month = arr[1]; // 获取当前日期的月份
	var day = arr[2]; // 获取当前日期的日
	var days = new Date(year, month, 0);
	days = days.getDate(); // 获取当前日期中月的天数
	var year2 = year;
	var month2 = parseInt(month) - 1;
	if (month2 == 0) {
		year2 = parseInt(year2) - 1;
		month2 = 12;
	}
	var day2 = day;
	var days2 = new Date(year2, month2, 0);
	days2 = days2.getDate();
	if (day2 > days2) {
		day2 = days2;
	}
	if (month2 < 10) {
		month2 = '0' + month2;
	}
	var t2 = year2 + '-' + month2 + '-' + day2;
	return t2;
} 

/**
 * 时间字符串转时间格式
 * 
 * @param strDate
 * @returns
 */
function getDate(strDate) {    
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,    
     function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');    
    return date;    
} 

function openwinx(url,name,w,h) {
    window.open(url,name,"top=100,left=400,width=" + w + ",height=" + h + ",toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
}
/**
 * 消息推送
 * @param toUserId
 * @param templateId
 * @param parameters
 * @param type
 * @param url
 */
function ws_send(toUserId,templateId,parameters,url){
	var info = "{'toUserId':"+toUserId+",'templateId':"+templateId+",'parameters':'"+parameters+"','url':'"+url+"'}";
	self.parent.ws_send(info);
}

/**
 * 搜索列表
 * @returns
 */
function search(){
	checkLogin();
	var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
    $("body").prepend(div); //jq获取上一页框架的父级框架；
	$("#search").submit();
}

function waitWindow(str){
	if(str.toLowerCase() == "no"){
		var div = '<div id="bombBoxDivId" class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloading" style="position:absolute;left:50%;top:32%;margin-left:-10px;"></i></div>';
	    $("body").prepend(div); //jq获取上一页框架的父级框架；
	}else if(str.toLowerCase() == "off"){
		if($("#bombBoxDivId") != undefined){
			$("#bombBoxDivId").remove();
		}
	}
}

function waitWindowNew(str){
	if(str.toLowerCase() == "no"){
		var div = '<div class="tip-loadingNewData" id="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
	    $("body").prepend(div); //jq获取上一页框架的父级框架；
	}else if(str.toLowerCase() == "off"){
		if($("#tip-loadingNewData") != undefined){
			$("#tip-loadingNewData").remove();
		}
	}
}
