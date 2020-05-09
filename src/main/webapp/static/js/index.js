var curWwwPath = window.document.location.href;
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
if(projectName != '/erp'){
    projectName = "";
}
var page_url = curWwwPath.substring(0, pos) + projectName;
$(window).on("load", function() {
    var userId = $("#userId").val();
    queryNoReadMsgNum(userId,"");
    query(userId);
    var websocketUrl = $("#websocketUrl").val();
    var target = websocketUrl +"websocket/echo?userId="+userId;
    var ws;//websocket实例
    var lockReconnect = false;//避免重复连接
    var wsUrl = target;
    var conNum = 1;//重连次数
    var isOne = 0;//是否心跳重连
    function createWebSocket(url) {
        try {
            ws = new WebSocket(url);
            initEventHandle();
        } catch (e) {
            reconnect(url,isOne);
        }
    }

    function initEventHandle() {
        ws.onclose = function () {
            reconnect(wsUrl,isOne);
        };
        ws.onerror = function () {
            reconnect(wsUrl,isOne);
        };
        //连接已建立，调用此方法
        ws.onopen = function () {
            //心跳检测重置
            heartCheck.reset().start();
        };
        ws.onmessage = function (data) {
            //如果获取到消息，心跳检测重置
            if(data.data!="HeartBeat"){
                $(".messageContainer").empty();
                var str = data.data;
                var title = str.substring(str.indexOf("title:")+6, str.lastIndexOf("content:"));
                var content = str.substring(str.indexOf("content:")+8, str.lastIndexOf("url:"));
                var url = str.substring(str.indexOf("url:")+4, str.length);
                //获取url中messageId的值
                var params = url.split("&");
                var param = params[params.length-1];
                var messageId = "";
                if(param.split("=")[0] == "messageId"){
                    messageId  = param.split("=")[1];
                }
                var divShow = '<div class="del"><div class="messageTip"> '+
                    '<i class="iconmessages"></i> <span class="messageTip-title" >【'+title+'】</span> <i class="iconmessagecha" onclick="closeMessage()"></i>'+
                    '</div>'+
                    '<div class="messagecontent pos_rel">'+
                    '<span style="cursor:pointer; display:inline-block; height: 40px;line-height: 18px;overflow: hidden;text-overflow: ellipsis;overflow: hidden;" onclick=_click("'+title+'","'+url+'","'+userId+'","'+messageId+'");>'+content+'</span> <i class="iconarrow"></i>'+
                    '</div></div>';

                $(".messageContainer").append(divShow);
                //添加消息提醒的小红点
                $("#side-bar-frame").contents().find("#msg").css('display','');
                $("#side-bar-frame").contents().find("#msg").addClass('newstips');
                queryNoReadMsgNum(userId,"");
            }
            //拿到任何消息都说明当前连接是正常的
            //isOne = 0;
            heartCheck.reset().start();
        }
    }

    function reconnect(url,isOne) {
        if(lockReconnect) return;
        lockReconnect = true;
        //没连接上会一直重连，设置延迟避免请求过多
        setTimeout(function () {
            url = "";
            //url = "ws://"+curWwwPath.substring(7, pos) + pathName.substring(0, pathName.substr(1).indexOf('/') + 1)+"/websocket/echo?userId="+userId+"&type=1";//表示重新连接
            url = websocketUrl +"websocket/echo?userId="+userId;
            createWebSocket(url);
            lockReconnect = false;
        }, 5000);
    }

    //心跳检测
    var heartCheck = {
        timeout: 10000,//50秒发送一次心跳
        dataTimeout : 5000,//10秒没收到心跳回复，重连
        timeoutObj: null,
        serverTimeoutObj: null,
        reset: function(){
            clearTimeout(this.timeoutObj);
            clearTimeout(this.serverTimeoutObj);
            return this;
        },
        start: function(){
            var self = this;
            this.timeoutObj = setTimeout(function(){
                //这里发送一个心跳，后端收到后，返回一个心跳消息，
                //onmessage拿到返回的心跳就说明连接正常
                ws.send("HeartBeat");
                self.serverTimeoutObj = setTimeout(function(){//如果超过一定时间还没重置，说明后端主动断开了
                    //isOne = 1;
                    ws.onclose();//如果onclose会执行reconnect，我们执行ws.onclose()就行了.如果直接执行reconnect 会触发onclose导致重连两次
                }, self.dataTimeout)
            }, this.timeout)
        }
    }
    createWebSocket(wsUrl);
})
window.onunload = function() {
}
function ws_send(info) {
}
function _click(title,url,userId,messageId) {
    var id=title;
    var name =title;
    var uri = url;
    var closable = 1;
    var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
    self.parent.closableTab.addTab(item);
    self.parent.closableTab.resizeMove();
    $(".messageContainer").empty();

    queryNoReadMsgNum(userId,messageId);
}
//左侧边栏动态消息
function _clickDynamic(userId) {
    var closable = 1;
    var item = { 'id': '消息列表', 'name': '消息列表', 'url': '/system/message/index.do?userId='+userId+'&category=627&isView=0', 'closable': closable == 1 ? true : false };
    self.closableTab.addTab(item);
    self.closableTab.resizeMove();
    $(".messageContainer").empty();
}
function closeMessage(){
    $('.messageContainer').empty();
}
//5秒轮询查询用户所有未读消息
function query(userId) {
    setInterval(function () {
        $.ajax({
            url : page_url + '/system/message/getAllMessageNoread.do',
            data : {
                "userId":userId
            },
            type : "POST",
            dataType : "json",
            success : function(data) {
                if(data.data!=null){
                    queryNoReadMsgNum(userId,"");
                    for (var i=data.data.length-1;i>=0;i--){
                        $(".messageContainer").empty();
                        var str = data.data[i];
                        str.url += "&messageId="+str.messageId;
                        var divShow = '<div class="del"><div class="messageTip"> '+
                            '<i class="iconmessages"></i> <span class="messageTip-title" >【'+str.title+'】</span> <i class="iconmessagecha" onclick="closeMessage()"></i>'+
                            '</div>'+
                            '<div class="messagecontent pos_rel">'+
                            '<span style="cursor:pointer; display:inline-block; height: 40px;line-height: 18px;overflow: hidden;text-overflow: ellipsis;overflow: hidden;" onclick=_click("'+str.title+'","'+str.url+'","'+userId+'","'+str.messageId+'");>'+str.title+'</span>'+
                            '</div></div>';

                        $(".messageContainer").append(divShow);
                        //添加消息提醒的小红点
                        $("#side-bar-frame").contents().find("#msg").css('display','');
                        $("#side-bar-frame").contents().find("#msg").addClass('newstips');
                    }

                }
            },
            error: function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    }, 300000);
}
//查询未读的消息数量
function queryNoReadMsgNum(userId,messageId){
    $.ajax({
        url : page_url + '/system/message/queryNoReadMsgNum.do',
        data : {
            "userId":userId,
            "messageId":messageId
        },
        type : "POST",
        dataType : "json",
        success : function(data) {
            if(data.code !=-1){
                if(data.data!=0){
                    //有未读商机
                    $("#messageContainer").attr("class","messageContainer msg-show");
                    $(".msg-always-wrap").show();
                    if (data.data>=999){
                        $("#msgNum").html('<a style="color: red" href="javascript:void(0);" link="/system/message/index.do" onclick="_clickDynamic('+userId+');">999+</a>');
                    } else {
                        $("#msgNum").html('<a style="color: red" href="javascript:void(0);" link="/system/message/index.do" onclick="_clickDynamic('+userId+');">'+data.data+'</a>');
                    }
                }else{
                    //无未读商机
                    $("#messageContainer").attr("class","messageContainer");
                    $(".msg-always-wrap").hide();
                }
            }

        },
        error: function(data){
            if(data.status ==1001){
                layer.alert("当前操作无权限")
            }
        }
    });
}
