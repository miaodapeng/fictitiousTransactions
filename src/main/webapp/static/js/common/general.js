var del = function(id, api, msg) {
	if (id > 0) {
		layer.confirm(msg + "，您是否确认该操作？", {
			btn : [ '确定', '取消' ]
		}, function() {
			layer.closeAll('dialog');
			$.ajax({
				type : "POST",
				url : api,
				data : {
					'id' : id
				},
				dataType : 'json',
				success : function(data) {
					refreshNowPageList(data);
					return data;
				},
				error:function(XMLHttpRequest, textStatus) {
					checkLogin();
				}
				
			});
		}, function() {
		});
	}
}
var delForStr = function(id, api, msg) {
	if ($.trim(id) != "") {
		layer.confirm(msg + "，您是否确认该操作？", {
			btn : [ '确定', '取消' ]
		// 按钮
		}, function() {
			layer.closeAll('dialog');
			$.ajax({
				type : "POST",
				url : api,
				data : {
					'id' : id
				},
				dataType : 'json',
				success : function(data) {
					refreshNowPageList(data);
					return data;
				},
				error:function(XMLHttpRequest, textStatus) {
					checkLogin();
				}
			});
		}, function() {
		});
	}
}
var delAll = function(ids, api, msg) {
	if (ids > 0) {
		layer.confirm(msg, {
			btn : [ '确定', '取消' ]
		// 按钮
		}, function() {
			layer.closeAll('dialog');
			$.ajax({
				type : "POST",
				url : api,
				data : {
					'ids' : ids
				},
				dataType : 'json',
				success : function(data) {
					refreshNowPageList(data);
					return data;
				},
				error:function(XMLHttpRequest, textStatus) {
					checkLogin();
				}
			});
		}, function() {
		});
	}
}

$(function() {
	$("#searchSpan").click(function() {
		$("#search").submit();
	})
});
/**
 * 观察者
 */
(function ($) {

    var o = $({});

    $.subscribe = function () {
        o.on.apply(o, arguments);
    };

    $.unsubscribe = function () {
        o.off.apply(o, arguments);
    };

    $.publish = function () {
        o.trigger.apply(o, arguments);
    };

} (jQuery));



