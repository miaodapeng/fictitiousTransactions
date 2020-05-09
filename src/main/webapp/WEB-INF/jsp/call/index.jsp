<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <meta content="text/html; charset=GB18030" http-equiv="content-type" />
    <title>电话操控按健</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/content.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/call-panel-layer.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/libs/jquery/plugins/layer/mylayer.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/call/call.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/common.js?rnd=<%=Math.random()%>"></script>
</head>
<body onload="window_onload();" style="height: 70px;overflow: hidden;">
    <div class="layer-content call-panel">
        <div class="call-queue">
            <div class="call-queue-list-title">呼入队列</div>
            <ul class="call-queue-list">
            </ul>
        </div>
        <div class="panel-content">
            <div class="title-bar">
                <div class="button-bar">
                    <ul class="button1-pos">
                        <li>
                        <a href="javascript:void(0);" class="button button2 bg-light-blue bt-bg-style bt-smaller" onclick="cmdSetParam_onclick();" id="btn_logon">登录</a>
                        <a href="javascript:void(0);" class="button button2 bg-light-red bt-bg-style bt-smaller" onclick="cmdSetParam_offclick();" style="display: none;" id="btn_logoff">退出</a>
                        <input type="hidden" id="serverIp" value="${callParams.callServerIp }" />
                        <input type="hidden" id="devNum" value="${user.userDetail.ccNumber }" />
                        <input type="hidden" id="agentID" value="${user.number }" />
                        <input type="hidden" id="agentName" value="${user.username }" />
                        <input type="hidden" id="agentType" value="${agentType }" />
                        <input type="hidden" id="txtSK" value="${txtSK }" />
                        </li>
                        <li class="only-text mr10">工号:<span class="value">${user.number }</span></li>
                        <li class="only-text mr10">名称:<span class="value">${user.username }</span></li>
                        <li class="only-text mr10">分机号:<span class="value">${user.userDetail.ccNumber }</span></li>
                        <li id="model" style="display: none;" class="mr10">模式
                            <select name="model" onchange="changeModel();">
                            	<option value="0">普通模式</option>
                            	<option value="1">下班模式</option>
                            	<option value="3">外拨模式</option>
                            	<c:if test="${agentType == 1 }">
                            	<option value="4">班长模式</option>
                            	</c:if>
                            </select>
                        </li>
                        <li id="status" style="display: none;" class="mr10">工作状态
                            <select name="status" onchange="changeStatus();">
                                <option value="4">正常工作</option>
                                <option value="0">小休</option>
                                <option value="1">开会</option>
                                <option value="2">就餐</option>
                                <option value="3">其它工作</option>
                            </select>
                        </li>
                        <li id="consult" style="display: none;"><a href="javascript:void(0);" onclick="consult();" class="button button2 icon icon2 bg-light-green bt-bg-style bt-smaller">内部咨询</a></li>
                        <li id="cancelPrepareConsult" style="display: none;"><a href="javascript:void(0);" onclick="cancelConsult();" class="button button3 icon icon2 bg-light-red bt-bg-style bt-smaller">取消咨询</a></li>
                        <li id="cancelConsult" style="display: none;"><a href="javascript:void(0);" onclick="finishConsult();" class="button button3 icon icon2 bg-light-red bt-bg-style bt-smaller">结束咨询</a></li>
                        
                        <li id="transfer" style="display: none;"><a href="javascript:void(0);" onclick="transfer();" class="button button1 icon icon6 bg-light-green bt-bg-style bt-smaller">转接</a></li>
                        
                        <li id="consultTransfer" style="display: none;"><a href="javascript:void(0);" onclick="consultTransfer();" class="button button2 icon icon6 bg-light-green bt-bg-style bt-smaller">咨询转接</a></li>
                        
                        <li id="cancelDial" style="display: none;"><a href="javascript:void(0);" onclick="cancelDial();" class="button button4 icon icon8 bg-light-red bt-bg-style bt-smaller">取消</a></li>
                        
                        <li id="hold" style="display: none;"><a href="javascript:void(0);" onclick="hold();" class="button button1 icon icon5 bg-light-green bt-bg-style bt-smaller">保持</a></li>
                        <li id="fetchHold" style="display: none;"><a href="javascript:void(0);" onclick="fetchHold();" class="button button3 icon icon5 bg-light-red bt-bg-style bt-smaller">取回</a></li>
                        
                        <li id="dropCall" style="display: none;"><a href="javascript:void(0);" onclick="dropCall();" class="button button4 icon icon3 bg-light-red bt-bg-style bt-smaller">结束</a></li>
                        <li id="finishWrapup" style="display: none;"><a href="javascript:void(0);" onclick="finishWrapup();" class="button button1 icon icon7 bg-light-green bt-bg-style bt-smaller">完成</a></li>
                        
                        <li id="callInner" style="display: none;"><a href="javascript:void(0);" onclick="prepareCallInner();" class="button button1 icon icon1 bg-light-green bt-bg-style bt-smaller">内呼</a></li>
                        <li id="cancelPrepareCallInner" style="display: none;"><a href="javascript:void(0);" class="button button3 icon icon1 bg-light-red bt-bg-style bt-smaller">取消内呼</a></li>
                        <li id="cancelCallInner" style="display: none;"><a href="javascript:void(0);" onclick="cancelCallInner();" class="button button3 icon icon1 bg-light-red bt-bg-style bt-smaller">取消内呼</a></li>
                        
                        <li id="monitor" style="display: none;"><a href="javascript:void(0);" onclick="prepareMonitor();" class="button button3 icon icon4 bg-light-red bt-bg-style bt-smaller">监听</a></li>
                        <li id="cancelPrepareMonitor" style="display: none;"><a href="javascript:void(0);" class="button button3 icon icon4 bg-light-red bt-bg-style bt-smaller">取消监听</a></li>
                        <li id="finishMonitor" style="display: none;"><a href="javascript:void(0);" onclick="finishMonitor();" class="button button3 icon icon4 bg-light-red bt-bg-style bt-smaller">取消监听</a></li>
                    </ul>
                </div>
                <div class="clear"></div>
            </div>
            <div class="title-bar">
                <div class="panel-message">
                    <ul>
                        <li><span id="model_span"></span>：<span class="value2" id="trace_model">未连接</span></li>
                        <li>提示信息：<span class="value2" id="trace_msg"></span></li>
                        <li id="call" style="display: none;"><span id="phone_type">呼入/呼出电话</span>：<span class="value" id="phone_span"></span></li>
                        <li id="time" style="display: none;">通话时长：<span class="value" id="time_span"></span></li>
                        <li style="display: none;">正在咨询分机：<span class="value"></span></li>
                    </ul>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div style="height: 0;overflow: hidden;">
        <div id='iptooldiv'></div> 
	    <div id='softphonediv'></div>
	    </div>
    </div>
</body>

<!-- 登录失败 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtLogonFailed(ErrNum)">
// <!CDATA[
    return csSoftPhone_evtLogonFailed(ErrNum);
// ]]>
</script>

<!-- 设置模式 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtSetWorkModeSucc(WorkMode)">
// <!CDATA[
    return csSoftPhone_evtSetWorkModeSucc(WorkMode);
// ]]>
</script>

<!-- 设置模式失败 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtSetWorkModeFailed(ErrNum)">
// <!CDATA[
    return csSoftPhone_evtSetWorkModeFailed(ErrNum);
// ]]>
</script>

<!-- 离席失败 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtLeaveFailed(ErrNum)">
// <!CDATA[
    return csSoftPhone_evtLeaveFailed(ErrNum);
// ]]>
</script>

<!-- 复席失败 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtResumeWorkFailed(ErrNum)">
// <!CDATA[
    return csSoftPhone_evtResumeWorkFailed(ErrNum);
// ]]>
</script>

<!-- 外拨 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtDialBegin">
// <!CDATA[
    return csSoftPhone_evtDialBegin();
// ]]>
</script>

<!-- 通话-->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtDialSucc">
// <!CDATA[
    return csSoftPhone_evtDialSucc();
// ]]>
</script>

<!-- 取消外拨失败 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtCancelDialFailed(ErrNum)">
// <!CDATA[
    return csSoftPhone_evtCancelDialFailed(ErrNum);
// ]]>
</script>

<!-- 取消外拨成功 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtDialFailed(ErrNum)">
// <!CDATA[
    return csSoftPhone_evtDialFailed(ErrNum);
// ]]>
</script>

<!-- 来电 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtCallArrive">
// <!CDATA[
    return csSoftPhone_evtCallArrive();
// ]]>
</script>

<!-- 接通电话 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtDevConnected">
// <!CDATA[
    return csSoftPhone_evtDevConnected();
// ]]>
</script>

<!-- 取回保持 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtFetchHoldSucc">
// <!CDATA[
    return csSoftPhone_evtFetchHoldSucc();
// ]]>
</script>

<!-- 内呼准备-->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtPrepareCallInnerSucc">
// <!CDATA[
    return csSoftPhone_evtPrepareCallInnerSucc();
// ]]>
</script>

<!-- 内呼开始振铃 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtCallInnerSucc">
// <!CDATA[
    return csSoftPhone_evtCallInnerBegin();
// ]]>
</script>

<!-- 内呼接通电话 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtCallInnerSucc">
// <!CDATA[
    return csSoftPhone_evtCallInnerSucc();
// ]]>
</script>

<!-- 刷新列表 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtRefreshAgentListSucc">
// <!CDATA[
    return csSoftPhone_evtRefreshAgentListSucc();
// ]]>
</script>

<!-- 结束 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtCallDroped">
// <!CDATA[
    return csSoftPhone_evtCallDroped();
// ]]>
</script>

<!-- 完成话后 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtFinishWrapupSucc">
// <!CDATA[
    return csSoftPhone_evtFinishWrapupSucc();
// ]]>
</script>

<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtNRTServiceArrive">
// <!CDATA[
    //Add you code here 
     var sk,sk1 ,ani;
     sk = csSoftPhone.getPRVCOInfo("SK");
     sk1 = csSoftPhone.getPRVCOInfo("SK");
// ]]>
</script>

<!-- 坐席状态变更 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtStateChange(State)">
// <!CDATA[
    return csSoftPhone_evtStateChange(State);
// ]]>
</script>

<!-- 呼叫队列 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtQueueChange">
// <!CDATA[
    return csSoftPhone_evtQueueChange();
// ]]>
</script>

<!-- 准备监听 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtPrepareMonitorSucc">
// <!CDATA[
    return csSoftPhone_evtPrepareMonitorSucc();
// ]]>
</script>

<!-- 监听 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtMonitorAgentSucc">
// <!CDATA[
    return csSoftPhone_evtMonitorAgentSucc();
// ]]>
</script>

<!-- 结束监听 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtFinishMonitorSucc">
// <!CDATA[
    return csSoftPhone_evtFinishMonitorSucc();
// ]]>
</script>

<!-- 转接 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtOneStepTranferStart">
// <!CDATA[
    return csSoftPhone_evtOneStepTranferStart();
// ]]>
</script>

<!-- 转接成功 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtOneStepTranferSucc">
// <!CDATA[
    return csSoftPhone_evtOneStepTranferSucc();
// ]]>
</script>

<!-- 转接失败 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtOneStepTranferFailed">
// <!CDATA[
    return csSoftPhone_evtOneStepTranferFailed();
// ]]>
</script>

<!-- 内部咨询 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtConsultStart">
// <!CDATA[
    return csSoftPhone_evtConsultStart();
// ]]>
</script>

<!-- 内部咨询成功 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtConsultSucc">
// <!CDATA[
    return csSoftPhone_evtConsultSucc();
// ]]>
</script>

<!-- 内部咨询结束-->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtConsultOver">
// <!CDATA[
    return csSoftPhone_evtConsultOver();
// ]]>
</script>

<!-- 内部咨询失败 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtConsultFailed">
// <!CDATA[
    return csSoftPhone_evtConsultFailed();
// ]]>
</script>

<!-- 咨询转接失败 -->
<script language="javascript" type="text/javascript" for="csSoftPhone" event="evtConsultTransferSucc">
// <!CDATA[
    return csSoftPhone_evtConsultTransferSucc();
// ]]>
</script>

<SCRIPT ID=clientcode LANGUAGE=javascript>
/**
 * 模式后面的提示语句
 */
function TraceModel(msg)
{
	$("#trace_model").html(msg);
}
/**
 * 模式类型
 */
function TraceModelName(msg)
{
	$("#model_span").html(msg);
}
/**
 * 提示信息
 */
function Trace(msg)
{
	$("#trace_msg").html(msg);
}

/**
 * 呼入呼出类型
 */
function TracePhoneType(msg){
	$("#phone_type").html(msg);
}

var click = 1;
/**
 * 初始化
 */
function cmdSetParam_onclick() {
    csSoftPhone.ServerIP = $("#serverIp").val() ;//服务器ID
    csSoftPhone.LocalIP ="" ;//本机ID
    csSoftPhone.DevNum = $("#devNum").val() ;//分机号
    csSoftPhone.AgentINS =  $("#devNum").val(); //INS 与DEV相同
    csSoftPhone.AgentID = $("#agentID").val();//工号
    csSoftPhone.AgentName = $("#agentName").val();//客服名称
    //设置第二台Server的IP信息
    csSoftPhone.setInitParam( "SERVERIPEX","0.0.0.0");   //第二台ServerIP地址，"0.0.0.0"代表不启用双机
    csSoftPhone.setInitParam( "SERVERPORTEX", "0");        //第二台Server的监听端口，"0"代表不启用双机，缺省Server端口为3000
    
    csSoftPhone.setInitParam ("AGENTWRAPUPSECONDS", 60);  //如果需要自动完成，设置自动完成的秒数
    csSoftPhone.setInitParam ("DEPARTMENTID", 1) ;        //如果座席所属部门ID，该ID在后面的管理部门，管理部门中使用
    csSoftPhone.setInitParam ("DEPARTMENTNAME", "客服");  //部门名称
    csSoftPhone.setInitParam ("RELATEDDEPARTMENTS", "1,2"); //关联部门，用于实时消息、内呼和咨询的座席列表显示时进行过滤，多个部门用逗号进行分割
    csSoftPhone.setInitParam ("MANAGEDEPARTMENETS", "1");  //管理部门，用于显示监控列表时的过滤，多个部门用逗号进行分割
    csSoftPhone.setInitParam ("DEBUGMODE", "1");  //调试模式
    csSoftPhone.setInitParam ("DEBUGTRACE", "1");  //日志
//     csSoftPhone.setInitParam ("GETLOCALSETTIONG", "0");  //日志
		
    //是否监听
    if($("#agentType").val() == 1)
        csSoftPhone.AgentType = 1;
    else
        csSoftPhone.AgentType = 0;
    csSoftPhone.SK =  $("#txtSK").val() ;

////***连接后自动登录
	if($("#serverIp").val() != "" && $("#devNum").val() != "" && $("#agentID").val() != "" && $("#agentName").val() != ""){
		//csSoftPhone.Logon();
	     $.ajax({
			type:'get',
			url:page_url+'/system/call/getisinit.do',
			dataType:'json',
			async:false,
			success:function(data){
				if(data.code==-1){
				    if(csSoftPhone.connectServer()== false)
	                	Trace("连接Server失败！");
	                else
	                	TraceModel("已连接");
				////***连接后自动登录
					csSoftPhone.Logon();
				}else{
					click++;
					if(click == 5){
						if(csSoftPhone.connectServer()== false)
		                	Trace("连接Server失败！");
		                else
		                	TraceModel("已连接");
					////***连接后自动登录
						csSoftPhone.Logon();
					}else{
	    				$("#btn_logon").show();//登录
	    				Trace("坐席已在其它页面登录！");
					}
					
					
				}
			}
		});  
	}
    	

    //window.parent.callout('17705185247');
}
/**
 * 登出
 */
function cmdSetParam_offclick(){
	csSoftPhone.Logoff();
}

/**
 * 更改模式
 */
function changeModel(){
	csSoftPhone.setWorkMode($("select[name='model']").val());
}

/**
 * 离席复席
 */
function changeStatus(){
	var status = csSoftPhone.getAgentInfo('AGENTSTATE');
	if($("select[name='status']").val()!=4){
		if(status == 3){
        	csSoftPhone.Leave($("select[name='status']").val());
		}else{
			if(status == 23){
				Trace("仅在正常工作状态下，允许更改离席原因");
			}else{
    			Trace("仅在空闲状态下，允许离席");
			}
		}
	}else{
		if(status == 23){
    		csSoftPhone.ResumeWork();
		}else{
			Trace("仅在非正常状态下，允许复席");
		}
	}
}

/**
 * 取消外拨
 */
function cancelDial(){
	csSoftPhone.cancelDial();
}
/**
 * 挂机
 */
function dropCall(){
	csSoftPhone.dropCall();
}
/**
 * 完成
 */
function finishWrapup(){
	csSoftPhone.finishWrapup();
}

/**
 * 准备监听
 */
function prepareMonitor(){
	csSoftPhone.prepareMonitor();
}
/**
 * 结束监听
 */
function finishMonitor(){
	csSoftPhone.finishMonitor();
}

/**
 * 准备内呼
 */
function prepareCallInner(){
	csSoftPhone.prepareCallInner();
}
/**
 * 取消内呼
 */
function cancelCallInner(){
	csSoftPhone.cancelCallInner();
}

var alertype=0;//弹出层类型1转接 2内部咨询
/**
 * 咨询准备
 */
function consult(){
	alertype=2;
	csSoftPhone.refreshAgentList(2);
}

/**
 * 取消咨询准备
 */
function cancelConsult(){
	TraceModel("通话");
    Trace("取消咨询，开始通话");
	csSoftPhone.cancelConsult();
}
/**
 * 取消咨询
 */
function finishConsult(){
	csSoftPhone.finishConsult();
}
/**
 * 咨询转接
 */
function consultTransfer(){
	csSoftPhone.consultTransfer();
}

/**
 * 转接准备
 */
function transfer(){
	alertype=1;
	csSoftPhone.refreshAgentList(2);
}

/**
 * 保存
 */
function hold(){
	csSoftPhone.hold();
}
/**
 * 取回保存
 */
function fetchHold(){
	csSoftPhone.fetchHold();
}

var c=0  
var t  
/**
 * 计时器
 */
function timedCount()  
{  
    hour = parseInt(c / 3600);// 小时数  
    min = parseInt(c / 60);// 分钟数  
    if(min>=60){  
        min=min%60  
    }  
    lastsecs = c % 60;  
    if(hour<10){
    	hour = '0'+hour;
    }
    if(min<10){
    	min = '0'+min;
    }
    if(lastsecs<10){
    	lastsecs = '0'+lastsecs;
    }

    $("#time_span").html(hour+":"+min+":"+lastsecs);
    c=c+1  
    t=setTimeout("timedCount()",1000);   
}  

/**
 * 计时器归0
 */
function stopCount()  
{  
    c=0;  
    clearTimeout(t);  
}  

var e = 60;
var et;
function timeEnd(){
	e--;
	TraceModel("话后");
	Trace("通话结束-自动完成时间:"+e);
	if(e==0){
		finishWrapup();
	}else{
		et = setTimeout("timeEnd()",1000);
	}
}

function clearTimeEnd(){
	e=60;
	clearTimeout(et);  
}

/**
 * 外拨计时
 */
function timeEndOut(msg){
	e--;
	Trace(msg+"-自动完成时间:"+e);
	if(e==0){
		finishWrapup();
	}else{
		et = setTimeout("timeEndOut('"+msg+"')",1000);
	}
}

</SCRIPT>
<SCRIPT ID=clientEventHandlersJS LANGUAGE=javascript>
/**
 * 浏览器判断
 */
function isIE() { //ie?  
    if (!!window.ActiveXObject || "ActiveXObject" in window)  
        return true;  
    else  
        return false;  
} 
/**
 * 自动加载软电话
 */
function window_onload() {
	//alert("Onload");
	//如果需要加载时自动连接Server
	var html = "";
	var htmlip = "";
	
	if(isIE()){
		html = " &nbsp;<object id='csSoftPhone'  classid='CLSID:0E431331-67B9-4C56-AB1E-A68E1E6726DA' codebase='CrystalSoftPhone32.cab#Version=3,2,0,44'"
		+" style='width: 600; height: 65pt'><param name='ServerIP' value='127.0.0.1'/> <param name='LocalIP' value='127.0.0.1'/> <param name='INS' value='6001'/> <param name='SK' value='1001|1002'/>"
		+"<param name='AgentID' value='1001'/> <param name='AgentName' value='Demo'/></object>";

		htmlip = " <object id='tlLocalIP'        classid='CLSID:AE0F9523-7A87-4299-BBFA-F242057FCA58'        codebase='CrystalSoftPhone32.CAB#version=3,2,0,44'       style='width: 10pt; height: 10pt'></object>";
	
	}else{
		html = "<object id='csSoftPhone' TYPE='application/x-itst-activex' clsid='{0E431331-67B9-4C56-AB1E-A68E1E6726DA}'"
		+" event_evtLogonFailed='csSoftPhone_evtLogonFailed'"
		+" event_evtSetWorkModeSucc='csSoftPhone_evtSetWorkModeSucc'"
		+" event_evtSetWorkModeFailed='csSoftPhone_evtSetWorkModeFailed'"
		+" event_evtLeaveFailed='csSoftPhone_evtLeaveFailed'"
		+" event_evtResumeWorkFailed='csSoftPhone_evtResumeWorkFailed'"
		+" event_evtDialBegin='csSoftPhone_evtDialBegin'"
		+" event_evtDialFailed='csSoftPhone_evtDialFailed'"
		+" event_evtCancelDialFailed='csSoftPhone_evtCancelDialFailed'"
		+" event_evtCallArrive='csSoftPhone_evtCallArrive'"
		+" event_evtFinishWrapupSucc='csSoftPhone_evtFinishWrapupSucc'"
		+" event_evtStateChange='csSoftPhone_evtStateChange'"
		+" event_evtDialSucc='csSoftPhone_evtDialSucc'"
		+" event_evtCallDroped='csSoftPhone_evtCallDroped'"
		+" event_evtDevConnected='csSoftPhone_evtDevConnected'"
		+" event_evtFetchHoldSucc='csSoftPhone_evtFetchHoldSucc'"
		+" event_evtQueueChange='csSoftPhone_evtQueueChange'"
		+" event_evtCallInnerBegin='csSoftPhone_evtCallInnerBegin'"
		+" event_evtCallInnerSucc='csSoftPhone_evtCallInnerSucc'"
		+" event_evtPrepareCallInnerSucc='csSoftPhone_evtPrepareCallInnerSucc'"
		+" event_evtRefreshAgentListSucc='csSoftPhone_evtRefreshAgentListSucc'"
		+" event_evtPrepareMonitorSucc='csSoftPhone_evtPrepareMonitorSucc'"
		+" event_evtMonitorAgentSucc='csSoftPhone_evtMonitorAgentSucc'"
		+" event_evtFinishMonitorSucc='csSoftPhone_evtFinishMonitorSucc'"
		+" event_evtOneStepTranferStart='csSoftPhone_evtOneStepTranferStart'"
		+" event_evtOneStepTranferSucc='csSoftPhone_evtOneStepTranferSucc'"
		+" event_evtOneStepTranferFailed='csSoftPhone_evtOneStepTranferFailed'"
		+" event_evtConsultTransferSucc='csSoftPhone_evtConsultTransferSucc'"
		+" event_evtConsultOver='csSoftPhone_evtConsultOver'"
		+" style='width: 100%; height: 65pt'> </object>";
		htmlip = "<object id='tlLocalIP' TYPE='application/x-itst-activex' clsid='{AE0F9523-7A87-4299-BBFA-F242057FCA58}' style='width: 10pt; height: 10pt'> </object>";
	}
	$("#softphonediv").html(html);
	$("#iptooldiv").html(htmlip);
   //如果需要加载时自动连接Server
   cmdSetParam_onclick();  
}

/**
 * 登录失败事件
 */
function csSoftPhone_evtLogonFailed(ErrNum)
{
	window_onload();
	$("#btn_logon").show();//登录
    $("#btn_logoff").hide();//登出
    $("#model").hide();//模式
    $("#status").hide();//状态
	TraceModel("签入失败");
	Trace("签入失败:"+csSoftPhone.getErrDesc(ErrNum));
}


/**
 * 去电弹屏
 */
function csSoftPhone_evtDialBegin()
{
    var phone = csSoftPhone.getCOInfo ("DNIS");
    phone=phone.replace("#","");
    phone=phone.replace(/\b(0+)/gi,"");
    var reg=/^1[34578]{1}\d{9}$/;
    if(reg.test(phone)){
    	phone = phone;
    }else{
        if(phone.length  > 8){
        	phone = '0'+phone;
        }
    }
    var coid = csSoftPhone.getCOInfo("COID");

    //回拨更新记录信息
    if(self.parent.document.getElementById("callFailedId").val != undefined && self.parent.document.getElementById("callFailedId").val > 0){
    	var callFailedId = self.parent.document.getElementById("callFailedId").val;
	    if(callFailedId){
	    	$.ajax({
				type:'get',
				url:page_url+'/system/call/editcallfailedcoid.do',
				data:{'callFailedId':callFailedId,'coid':coid},
				dataType:'json',
				async:false,
				success:function(data){
					if(data.code==0){
						self.parent.document.getElementById("callFailedId").val = '0';
					}
				}
			});
	    }
    }
    
    
	calloutScreen(phone,coid);
	$("#phone_type").html('呼出电话');
	$("#phone_span").html(phone);

}



/**
 * 更改模式
 */
function csSoftPhone_evtSetWorkModeSucc(WorkMode){
	var model_name = '普通模式';
    switch(WorkMode){
        case 0:
        	model_name = '普通模式';
        	//关闭监听
        	$("#monitor").hide();
            break;
        case 1:
        	model_name = '下班模式';
        	//关闭监听
        	$("#monitor").hide();
            break;
        case 3:
        	model_name = '外拨模式';
        	//关闭监听
        	$("#monitor").hide();
            break;
        case 4:
        	model_name = '班长模式';
        	//打开监听
        	$("#monitor").show();
            break;
    }
    TraceModelName(model_name);
}


/**
 * 更改模式失败
 */
function csSoftPhone_evtSetWorkModeFailed(ErrNum){
	Trace(csSoftPhone.getErrDesc(ErrNum));
}

/**
 * 离席失败
 */
function csSoftPhone_evtLeaveFailed(ErrNum){
	Trace("仅在正常工作状态下，允许更改离席原因");
}

/**
 * 复位失败
 */
function csSoftPhone_evtResumeWorkFailed(ErrNum){
	var status = csSoftPhone.getAgentInfo('AGENTSTATE');
	if(status != 23){
		Trace("仅在离席状态下，允许复席");
	}
}
/**
 * 外拨取消
 */
function csSoftPhone_evtDialFailed(ErrNum){
	//timeEndOut(csSoftPhone.getErrDesc(ErrNum));
}
/**
 * 取消外拨失败
 */
function csSoftPhone_evtCancelDialFailed(ErrNum){
	Trace(csSoftPhone.getErrDesc(ErrNum));
}

/**
 * 来电
 */
function csSoftPhone_evtCallArrive()
{
    var ANI="";
    var SK="";
    var COID = "";
    var DNIS = "";
    ANI=csSoftPhone.getCOInfo ("ANI");
    DNIS=csSoftPhone.getCOInfo ("DNIS");
    SK= csSoftPhone.getCOInfo("SK");
    COID= csSoftPhone.getCOInfo("COID");
    var type = csSoftPhone.getCOInfo('17');
    if(ANI.length !=4 ){ //内呼不弹屏
    	callin(ANI);
    }
    Trace ("收到从" + ANI+">>>"+DNIS + "来的电话");
    $("#phone_span").html(ANI+">>>"+DNIS);
    var type = csSoftPhone.getCOInfo('17');
    switch(type){
    case '0':
    	$("#phone_type").html('呼入电话');
        TraceModel("通话准备(振铃)");
        break;
    case '1':
    	$("#phone_type").html('呼出电话');
        break;
    case '2':
    	$("#phone_type").html('内部通话');
    	TraceModel("内呼中(振铃)");
        break;
    }
}

/**
 * 通话结束
 */
function csSoftPhone_evtCallDroped(){
	if(c*1>0){//接通电话
    	stopCount();
    	timeEnd();
	}
}


/**
 * 完成操作
 */
function csSoftPhone_evtFinishWrapupSucc(){
	clearTimeEnd();
}


/**
 * 外拨成功事件
 */
function csSoftPhone_evtDialSucc()
{
	timedCount();  
	var type = csSoftPhone.getCOInfo('17');
    switch(type){
    case '0':
    	$("#phone_type").html('呼入电话');
    	TraceModel("通话");
        Trace("呼入成功，开始通话");
        break;
    case '1':
    	$("#phone_type").html('呼出电话');
    	TraceModel("通话");
        Trace("外拨成功，开始通话");
        break;
    case '2':
    	$("#phone_type").html('内部通话');
    	TraceModel("内部通话");
        Trace("内呼成功，开始内部通话");
        break;
    }
}

/**
 * 呼入-接通电话
 */
function csSoftPhone_evtDevConnected(){
	timedCount();  
	var type = csSoftPhone.getCOInfo('17');
    switch(type){
    case '0':
        $("#phone_type").html('呼入电话');
    	TraceModel("通话");
        Trace("呼入成功，开始通话");
        break;
    case '1':
        $("#phone_type").html('呼出电话');
    	TraceModel("通话");
        Trace("外拨成功，开始通话");
        break;
    case '2':
    	$("#phone_type").html('内部通话');
    	TraceModel("内部通话");
        Trace("内呼成功，开始内部通话");
        break;
    }
}

function csSoftPhone_evtFetchHoldSucc(){
	TraceModel("通话");
	Trace("取回成功，开始通话");
}

/**
 * 内呼准备
 */
function csSoftPhone_evtPrepareCallInnerSucc(){
	csSoftPhone.refreshAgentList(3);
}

/**
 * 监听准备
 */
function csSoftPhone_evtPrepareMonitorSucc(){
	csSoftPhone.refreshAgentList(1);
}

/**
 * 监听成功
 */
function csSoftPhone_evtMonitorAgentSucc(){
    var DNIS = csSoftPhone.getCOInfo('DNIS');
    var agent = window.parent.document.getElementById('monitorAgent').value;
    window.parent.document.getElementById('monitorAgent').value = '';

    var type = csSoftPhone.getCOInfo('17');
    var STR = '';
    switch(type){
    case '0':
    	STR = "呼入";
        break;
    case '1':
    	STR = "呼出";
        break;
    case '2':
    	STR = "内呼";
        break;
    }
	TraceModel("监听");
    Trace("正在监听"+agent+"("+STR+DNIS+")");
}
/**
 * 结束监听成功
 */
function csSoftPhone_evtFinishMonitorSucc(){
    timeEnd();
}
/**
 * 转接开始
 */
function csSoftPhone_evtOneStepTranferStart(){
	TraceModel("转接");
    Trace("正在为你转接...");
}
/**
 * 转接开始
 */
function csSoftPhone_evtOneStepTranferSucc(){
	stopCount();
	timeEnd();
}
/**
 * 转接失败
 */
function csSoftPhone_evtOneStepTranferFailed(){
	
}
/**
 * 咨询转接成功
 */
function csSoftPhone_evtConsultTransferSucc(){
	stopCount();
	timeEnd();
}
/**
 * 咨询结束
 */
function csSoftPhone_evtConsultOver(){
	//$("#fetchHold").hide();//取回保持
	csSoftPhone.fetchHold();
}

/**
 * 坐席刷新
 */
function csSoftPhone_evtRefreshAgentListSucc(){
	var agentstate = csSoftPhone.getAgentInfo('AGENTSTATE');//模式
    var type = alertype;
    alertype = 0;
    
	var customer_on = {};//坐席（在线）
	var customer_leave = {};//分机（离线）

	var length = csSoftPhone.getAgentListCount();
	//坐席（在线）
	for(var i=1;i<=length*1;i++){
		var on = {};
    	var INS = csSoftPhone.getAgentListInfo(i,'INS');
    	var AGENTNAME = csSoftPhone.getAgentListInfo(i,'AGENTNAME');
    	var ST = csSoftPhone.getAgentListInfo(i,'ST');
    	var WKMD = csSoftPhone.getAgentListInfo(i,'WKMD');
    	var LEVRS = csSoftPhone.getAgentListInfo(i,'LEVRS');
    	on['INS'] = INS;
    	on['AGENTNAME'] = AGENTNAME;
    	on['ST'] = ST;
    	on['WKMD'] = WKMD;
    	customer_on[i-1]=on;
	}
	
	var length = csSoftPhone.getDevListCount();
	//分机（离线）
	for(var i=1;i<=length*1;i++){
		var leave = {};
    	var INS = csSoftPhone.getDevListInfo(i,'INS');
    	var WKMD = csSoftPhone.getAgentListInfo(i,'WKMD');
    	leave['INS'] = INS;
    	customer_leave[i-1]=leave;
	}
	
	//json字符串
	customer_on = JSON.stringify(customer_on);
	customer_leave = JSON.stringify(customer_leave);
	//url编码
	customer_leave = encodeURI(customer_leave);
	customer_on = encodeURI(customer_on);
	
	self.parent.layer.config({
        extend: 'vedeng.com/style.css', //加载您的扩展样式
        skin: 'vedeng.com'
    });
	self.parent.callAgent = self.parent.layer.myopen({
        type: 2,
        shadeClose: false, //点击遮罩关闭
        closeBtn: false,
        area: ['600px','434px'],
        title: false,
        content: ['./system/call/getagentlist.do?type='+type+'&agentState='+agentstate+'&agentOn='+customer_on+'&agentLeave='+customer_leave,'no'],
        success: function(layero, index) {
            //layer.iframeAuto(index);
        }
    });
}
/**
 * 外拨-内呼开始
 */
function csSoftPhone_evtCallInnerBegin(){
	var DNIS=csSoftPhone.getCOInfo ("DNIS");
    $("#phone_type").html('内呼电话');
    $("#phone_span").html(DNIS);
	TraceModel("内部通话");
    Trace("开始内呼，等待对方坐席接通");
}
/**
 * 外拨-内呼接通电话
 */
function csSoftPhone_evtCallInnerSucc(){
	timedCount(); 
	TraceModel("内部通话");
    Trace("内呼成功，开始内部通话");
}

/**
 * 呼叫队列
 */
function csSoftPhone_evtQueueChange(){
	var index = csSoftPhone.getQueueSize();
	var str = '';
	for(var i=1;i<=index*1;i++){
		var title='';
		var customer = '';
		var ANI = csSoftPhone.getQueueCOInfo(i, "ANI");
		$.ajax({
			type:'get',
			url:page_url+'/system/call/gettraderinfo.do',
			data:{phone:ANI},
			dataType:'json',
			async:false,
			success:function(data){
				if(data.code==0){
					title = data.data.traderName;
					customer = ' '+data.data.traderName;
				}
			}
		});
		str += "<li title="+title+">"+ANI+customer+"</li>";
	}
	$(".call-queue-list").html(str);
}

/**
 * 坐席状态变更
 */
function csSoftPhone_evtStateChange(st)
{
// 	alert(st);
	switch(st){
	   case 0://未连接
		   $("#btn_logon").show();//登录
		   
		   $("#btn_logoff").hide();//登出
		   $("#model").hide();//模式
		   $("#status").hide();//状态
		   
		   $("#transfer").hide();//转接
		   
		   $("#consult").hide();//内部咨询
		   $("#cancelPrepareConsult").hide();//取消内部咨询准备
		   $("#cancelConsult").hide();//取消内部咨询
		   
		   $("#consultTransfer").hide();//咨询转接
		   
		   $("#cancelDial").hide();//取消外拨
		   
		   $("#hold").hide();//保持
		   $("#fetchHold").hide();//取回保持
		   
		   $("#dropCall").hide();//结束
		   $("#finishWrapup").hide();//完成
		   
		   $("#callInner").hide();//内呼
		   $("#cancelPrepareCallInner").hide();//取消内呼准备
		   $("#cancelCallInner").hide();//取消内呼
		   
		   $("#monitor").hide();//监听
		   $("#cancelPrepareMonitor").hide();//取消监听
		   $("#finishMonitor").hide();//结束监听
		   
		   $("#call").hide();
		   $("#time").hide();

		   TraceModelName("普通模式");
		   TraceModel("未登录");
		   Trace("签出成功");
		   break;
	   case 1://初始化
		   $("#btn_logon").hide();
		   
		  // $("#btn_logoff").show();
		   $("#model").show();
		   
		   //模式初始化
		   $("select[name='model']").val(0);
		   csSoftPhone.setWorkMode(0);
		   
		   //状态初始化
		   $("select[name='status']").val(4);

		   //计时器初始化
		   stopCount();
		   clearTimeEnd();
		   
		   TraceModelName("普通模式");
		   TraceModel("已登录");
		   Trace("签入成功");
		   break;
	   case 2://
		   break;
	   case 3://空闲
		   $("#cancelCallInner").hide();
		   $("#cancelPrepareCallInner").hide();
		   $("#finishWrapup").hide();
		   $("#cancelPrepareMonitor").hide();
		   $("#finishMonitor").hide();
		   $("#call").hide();
		   $("#time").hide();
		   $("#cancelPrepareConsult").hide();
		   $("#callInner").show();
		   $("#status").show();
		   //班长监听
		   if($("select[name='model']").val() == 4){
    		   $("#monitor").show();
		   }else{
			   $("#monitor").hide();
		   }
		   
		   TraceModel("空闲");
		   Trace("");
		   break;
	   case 4://
		   break;
	   case 5://通话准备(震铃)
		   $("#callInner").hide();
		   //班长监听
		   if(csSoftPhone.AgentType==1){
    		   $("#monitor").hide();
		   }
		   $("#call").show();
		   break;
	   case 6://通话
		   $("#cancelDial").hide();
		   $("#fetchHold").hide();//取回保持


		   $("#transfer").hide();//转接
		   
		   $("#cancelPrepareConsult").hide();//取消内部咨询准备
		   $("#cancelConsult").hide();//取消内部咨询
		   
		   $("#consultTransfer").hide();//咨询转接
		   
		   $("#consult").show();//内部咨询
		   $("#transfer").show();//转接
		   $("#hold").show();//保持
		   $("#dropCall").show();//结束
		   
		   $("#time").show();//结束
		   $("#call").show();//结束
		   break;
	   case 7://保持
		   $("#hold").hide();//保持
		   $("#fetchHold").show();//取回保持
		   TraceModel('保持');
		   Trace('保持成功');
		   break;
	   case 8://外拨准备(输入号码)
		   $("#callInner").hide();
		   //班长监听
		   if(csSoftPhone.AgentType==1){
    		   $("#monitor").hide();
		   }
		   break;
	   case 9://外拨中
		   $("#callInner").hide();
		   //班长监听
		   if(csSoftPhone.AgentType==1){
    		   $("#monitor").hide();
		   }
		   $("#cancelDial").show();
		   $("#call").show();
		   TraceModel('外拨中');
		   Trace('开始外拨');
		   break;
	   case 10://咨询请求中（震铃）
		   $("#transfer").hide();//转接
		   $("#consult").hide();

		   $("#hold").hide();//保持
		   
		   $("#cancelPrepareConsult").show();
		   var agent = window.parent.document.getElementById('monitorAgent').value;
		   window.parent.document.getElementById('monitorAgent').value = '';
		   
		   TraceModel('内部咨询请求中（震铃）');
		   Trace('正在呼叫座席('+agent+')');
		   break;
	   case 11://咨询接通
		   $("#cancelPrepareConsult").hide();
		   
		   $("#cancelConsult").show();
		   $("#consultTransfer").show();
		   TraceModel('内部咨询');
		   Trace('咨询成功，开始通话');
		   break;
	   case 12://
		   break;
	   case 13://
		   break;
	   case 14://
		   break;
	   case 15://
		   break;
	   case 16://监控准备(选择座席)
		   $("#callInner").hide();//内呼
		   $("#monitor").hide();//监听
		   $("#consult").hide();//内部咨询
		   $("#transfer").hide();//内部转接
		   $("#hold").hide();//保持
		   $("#dropCall").hide();//结束
		   $("#cancelPrepareMonitor").show();//取消监听
		   TraceModel('监控准备(选择座席)');
		   Trace("请选择监控的座席");
		   break;
	   case 17://监控
		   $("#cancelPrepareMonitor").hide();//取消监听
		   $("#finishMonitor").show();//取消监听
		   break;
	   case 18://
		   break;
	   case 19://话后
		   if(c<=0){
			   timeEnd();
		   }
		   $("#cancelDial").hide();
		   $("#consult").hide();//内部咨询
		   $("#transfer").hide();//内部转接
		   $("#hold").hide();//保持
		   $("#fetchHold").hide();//取回保持
		   $("#dropCall").hide();//结束
		   $("#finishMonitor").hide();
		   $("#cancelCallInner").hide();
		   $("#cancelPrepareMonitor").hide();
		   $("#cancelConsult").hide();//取消内部咨询
		   $("#consultTransfer").hide();//咨询转接
		   $("#cancelPrepareConsult").hide();
		   $("#finishWrapup").show();
		   break;
	   case 20://
		   break;
	   case 21://
		   break;
	   case 22://
		   break;
	   case 23://离席
		   $("#callInner").hide();//内呼
		   $("#monitor").hide();//监听
		   var status = csSoftPhone.getAgentInfo('LeaveReason');
		   var status_name = '';
		   switch(status){
		        case 0:
		        	status_name = '小休';
		            break;
		        case 1:
		        	status_name = '开会';
		            break;
		        case 2:
		        	status_name = '就餐';
		            break;
		        case 3:
		        	status_name = '其它工作';
		            break;
		    }
		    TraceModel('离席('+status_name+')');
		    Trace("离席成功");
		   break;
	   case 24://内呼准备(选择座席)
		   $("#monitor").hide();//监听
		   $("#callInner").hide();//内呼
		   $("#cancelPrepareCallInner").show();//取消内呼
		   TraceModel('内呼准备(选择座席)');
		   Trace("请选择内呼的座席");
		   break;
	   case 25://内呼中(震铃)
		   $("#cancelPrepareCallInner").hide();
		   $("#cancelCallInner").show();
		   $("#call").show();
		   break;
	   case 26://内部通话
		   $("#time").show();//结束
		   break;
	   case 27://
		   break;
	   case 28://
		   break;
	   case 29://
		   break;
	   case 30://
		   break;
	   case 31://
		   break;
	   case 32://
		   break;
	   case 33://
		   break;
	   case 34://
		   break;
	   case 35://
		   break;
	   case 36://
		   break;
	   case 37://
		   break;
	   case 38://
		   break;
	   case 39://
		   break;
	   case 40://
		   break;
	   case 41://
		   break;
	   case 42://
		   break;
	   case 43://
		   break;
	   case 44://
		   break;
	   case 45://
		   break;
	   case 46://
		   break;
	   case 47://
		   break;
	   case 48://
		   break;
	   case 49://
		   break;
	}
}
</SCRIPT>
</html>