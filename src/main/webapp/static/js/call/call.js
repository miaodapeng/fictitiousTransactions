/**
 * 呼出电话前数据处理
 * @param phone 被叫号码
 * @param traderId 交易者id
 * @param traderType 交易者类型 1客户2供应商
 * @param callType 呼出订单类型 1商机2销售订单3报价4售后5采购订单//没有就传 0
 * @param orderId 订单id 没有就传 0
 * @param traderContactId 联系人ID 没有就传 0
 * @returns
 */
function callout(phone,traderId,traderType,callType,orderId,traderContactId){
	checkLogin();
	phone = phone.replace(/^025/,"");
	if(traderId == '' || traderId == null){
		traderId = 0;
	}
	if(traderType == '' || traderType == null){
		traderType = 0;
	}
	if(callType == '' || callType == null){
		callType = 0;
	}
	if(orderId == '' || orderId == null){
		orderId = 0;
	}
	if(traderContactId == '' || traderContactId == null){
		traderContactId = 0;
	}
	if(self.parent.document.getElementById("callcenter") != null){
		var obj = self.parent.document.getElementById("callcenter").contentWindow;
		$.ajax({
			type:'post',
			url:page_url+'/system/call/getphoneinfo.do',
			data:{phone:phone},
			dataType:'json',
			success:function(data){
				self.parent.document.getElementById("call_traderId").val=traderId;
				self.parent.document.getElementById("call_traderType").val=traderType;
				self.parent.document.getElementById("call_callType").val=callType;
				self.parent.document.getElementById("call_orderId").val=orderId;
				self.parent.document.getElementById("call_traderContactId").val=traderContactId;
				obj.document.getElementById("csSoftPhone").Dial(data.data,"");
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
}
/**
 * 呼出电话前数据处理(无弹窗)
 * @param phone 被叫号码
 * @param traderId 交易者id
 * @param traderType 交易者类型 1客户2供应商
 * @param callType 呼出订单类型 1商机2销售订单3报价4售后5采购订单//没有就传 0
 * @param orderId 订单id 没有就传 0
 * @param traderContactId 联系人ID 没有就传 0
 * @returns
 */
function calloutNoScreen(phone,traderId,traderType,callType,orderId,traderContactId){
	checkLogin();
	phone = phone.replace(/^025/,"");
	if(traderId == '' || traderId == null){
		traderId = 0;
	}
	if(traderType == '' || traderType == null){
		traderType = 0;
	}
	if(callType == '' || callType == null){
		callType = 0;
	}
	if(orderId == '' || orderId == null){
		orderId = 0;
	}
	if(traderContactId == '' || traderContactId == null){
		traderContactId = 0;
	}
	if(self.parent.document.getElementById("callcenter") != null){
		var obj = self.parent.document.getElementById("callcenter").contentWindow;
		$.ajax({
			type:'post',
			url:page_url+'/system/call/getphoneinfo.do',
			data:{phone:phone},
			dataType:'json',
			success:function(data){
				self.parent.document.getElementById("call_traderId").val=traderId;
				self.parent.document.getElementById("call_traderType").val=traderType;
				self.parent.document.getElementById("call_callType").val=callType;
				self.parent.document.getElementById("call_orderId").val=orderId;
				self.parent.document.getElementById("call_traderContactId").val=traderContactId;
				self.parent.document.getElementById("call_screen").val='0';
				obj.document.getElementById("csSoftPhone").Dial(data.data,"");
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
}
/**
 * 呼出电话弹屏
 * @param phone 被叫号码
 * @param coid 录音coid
 * @returns
 */
function calloutScreen(phone,coid){
	checkLogin();
	var obj = self.parent.document.getElementById("callcenter").contentWindow;
	var coid = obj.document.getElementById("csSoftPhone").getCOInfo("COID");
	var call_traderId = self.parent.document.getElementById("call_traderId").val != undefined ? self.parent.document.getElementById("call_traderId").val : 0;
	var call_traderType = self.parent.document.getElementById("call_traderType").val != undefined ? self.parent.document.getElementById("call_traderType").val: 0;
	var call_callType = self.parent.document.getElementById("call_callType").val != undefined ? self.parent.document.getElementById("call_callType").val: 0;
	var call_orderId = self.parent.document.getElementById("call_orderId").val != undefined ? self.parent.document.getElementById("call_orderId").val: 0;
	var call_traderContactId = self.parent.document.getElementById("call_traderContactId").val != undefined ? self.parent.document.getElementById("call_traderContactId").val: 0;
	var call_screen = self.parent.document.getElementById("call_screen").val != undefined ? self.parent.document.getElementById("call_screen").val: 0;
	
	self.parent.document.getElementById("call_traderId").val=0;
	self.parent.document.getElementById("call_traderType").val=0;
	self.parent.document.getElementById("call_callType").val=0;
	self.parent.document.getElementById("call_orderId").val=0;
	self.parent.document.getElementById("call_traderContactId").val=0;
	self.parent.document.getElementById("call_screen").val = 1;
	
	if(call_screen == 1){
		self.parent.callIndex = self.parent.layer.myopen({
			type: 2,
			shadeClose: false, //点击遮罩关闭
			closeBtn: false,
			area: ['823px','531px'],
			title: false,
			content: ['./system/call/getcallout.do?traderId='+call_traderId+'&traderType='+call_traderType+'&callType='+call_callType+'&orderId='+call_orderId+'&coid='+coid+'&traderContactId='+call_traderContactId+'&phone='+phone,'no'],
			success: function(layero, index) {
				//layer.iframeAuto(index);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}else{
		$.ajax({
			type:'get',
			url:page_url+'/system/call/getcallout.do?traderId='+call_traderId+'&traderType='+call_traderType+'&callType='+call_callType+'&orderId='+call_orderId+'&coid='+coid+'&traderContactId='+call_traderContactId+'&phone='+phone,
			dataType:'json',
			success:function(data){
				//
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
}

/**
 * 来电弹屏
 * @param phone
 * @returns
 */
function callin(phone){
	checkLogin();
	var obj = self.parent.document.getElementById("callcenter").contentWindow
	var coid = obj.document.getElementById("csSoftPhone").getCOInfo("COID");
	self.parent.callIndex = self.parent.layer.myopen({
        type: 2,
        shadeClose: false, //点击遮罩关闭
        closeBtn: false,
        area: ['823px','531px'],
        title: false,
        content: ['./system/call/getcallin.do?callType=1&callFrom=1&coid='+coid+'&phone='+phone,'no'],
        success: function(layero, index) {
            //layer.iframeAuto(index);
        },
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	
    });
}

/**
 * 新增联系
 * @param phone 被叫号码
 * @param traderId 交易者id
 * @param traderType 交易者类型
 * @param callType 呼出订单类型
 * @param orderId 订单id
 * @param traderContactId 联系人ID
 * @param callFrom 沟通方式 0呼入1呼出
 * @returns
 */
function addComm(phone,traderId,traderType,callType,orderId,traderContactId,callFrom,coid){
	checkLogin();
	if(traderId == '' || traderId == null){
		traderId = 0;
	}
	if(traderType == '' || traderType == null){
		traderType = 0;
	}
	if(callType == '' || callType == null){
		callType = 0;
	}
	if(orderId == '' || orderId == null){
		orderId = 0;
	}
	if(traderContactId == '' || traderContactId == null){
		traderContactId = 0;
	}
	if(callFrom == '' || callFrom == null){
		callFrom = 0;
	}
	var obj = self.parent.document.getElementById("callcenter").contentWindow
	self.parent.callComm = self.parent.layer.myopen({
        type: 2,
        shadeClose: false, //点击遮罩关闭
        closeBtn: false,
        area: ['880px','531px'],
        title: false,
        content: ['./system/call/getaddcomm.do?traderId='+traderId+'&traderType='+traderType+'&callType='+callType+'&orderId='+orderId+'&coid='+coid+'&traderContactId='+traderContactId+'&callFrom='+callFrom+'&phone='+phone],
        success: function(layero, index) {
            //layer.iframeAuto(index);
        },
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
    });
}

/**
 * 售后新增商机
 * @param phone
 * @param traderId
 * @returns
 */
function addbussinesschance(phone,traderId){
	checkLogin();
	if(traderId == '' || traderId == null){
		traderId = 0;
	}
	var obj = self.parent.document.getElementById("callcenter").contentWindow
	self.parent.callComm = self.parent.layer.myopen({
        type: 2,
        shadeClose: false, //点击遮罩关闭
        closeBtn: false,
        area: ['880px','531px'],
        title: false,
        content: ['./system/call/getaddbussinesschance.do?traderId='+traderId+'&phone='+phone],
        success: function(layero, index) {
            //layer.iframeAuto(index);
        },
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
    });
}

/**
 * 查看客户商机
 * @param phone
 * @returns
 */
function showEnquiry(phone){
	checkLogin();
	var obj = self.parent.document.getElementById("callcenter").contentWindow
	self.parent.callBussincessChance = self.parent.layer.myopen({
        type: 2,
        shadeClose: false, //点击遮罩关闭
        closeBtn: false,
        area: ['880px','531px'],
        title: false,
        content: ['./system/call/getbussincesschance.do?phone='+phone],
        success: function(layero, index) {
            //layer.iframeAuto(index);
        },
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
    });
}

/**
 * 录音播放
 * @param url
 * @returns
 */
function playrecord(url){
	checkLogin();
	if(url != ''){
		self.parent.layer.myopen({
			type: 2,
			shadeClose: false, //点击遮罩关闭
			area: ['360px','80px'],
			title: false,
			content: ['./system/call/getrecordpaly.do?url='+url],
			success: function(layero, index) {
				//layer.iframeAuto(index);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
	
}