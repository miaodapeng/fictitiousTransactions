<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="物流数据统计 " scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
	<div class="form-list chart-form-list form-tips4">
        <ul style="margin-bottom:0;">
            <li style="margin-bottom:0;">
                <div class="form-tips" style="margin-top:2px; ">
                    <lable>请选择年份</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks ">
                        <input class="Wdate f_left input-smaller96 mr10" type="text" name="year" placeholder="请选择年份" onClick="WdatePicker({dateFmt:'yyyy'})" value="${year}">
                        <span class="bt-small bt-bg-style bg-light-blue " onclick="search();">查询</span>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="main-container" style="padding-top:0;">
        <div class="charts">
            <div class="charts-title">业务运费统计</div>
            <div class="charts-container">
            	<div id="ywyftj" style="width:100%;height:400px;"></div>
            </div>
        </div>
        
        <div class="charts">
            <div class="charts-title">快递运费统计</div>
            <div class="charts-container">
            	<div id="kdyftj" style="width:100%;height:400px;"></div>
            </div>
        </div>
        
       <div class="charts">
            <div class="charts-title">快递件数统计</div>
            <div class="charts-container">
            	<div id="kdjstj" style="width:100%;height:400px;"></div>
            </div>
        </div>
    </div>
<script type="text/javascript">
	search();
	function search(){
		checkLogin();
		var logisticsNames ='';
		
		var businessNames = '';
		
		var businessFreightDatas = [];
		var logisticsFreightDatas = [];
		var logisticsPiecesDatas = [];
	
		$.ajax({
			url:page_url+'/datacenter/logistics/getLogisticsHomePageMsg.do',
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					logisticsNames = data.data.logisticsNames;
					
					businessNames = data.data.businessNames;
				
					
					for ( var i = 0; i < data.data.childrenEchartsDataVoList.length; i++) {
						businessFreightDatas.push({
		                     name : data.data.childrenEchartsDataVoList[i].name,
		                     type : 'bar',
		                     stack : '总量',
		                     label : {
		                         normal : {
		                             show : true,
		                             position : 'insideRight'
		                         }
		                     },
		                     data : data.data.childrenEchartsDataVoList[i].childrenList
		                 });
		             }
					for ( var i = 0; i < data.data.logisticsFreightList.length; i++) {
						logisticsFreightDatas.push({
		                     name : data.data.logisticsFreightList[i].name,
		                     type : 'bar',
		                     stack : '总量',
		                     label : {
		                         normal : {
		                             show : true,
		                             position : 'insideRight'
		                         }
		                     },
		                     data : data.data.logisticsFreightList[i].childrenList
		                 });
		             }
					for ( var i = 0; i < data.data.logisticsPiecesList.length; i++) {
						logisticsPiecesDatas.push({
		                     name : data.data.logisticsPiecesList[i].name,
		                     type : 'bar',
		                     stack : '总量',
		                     label : {
		                         normal : {
		                             show : true,
		                             position : 'insideRight'
		                         }
		                     },
		                     data : data.data.logisticsPiecesList[i].childrenList
		                 });
		             }
					
				}else{
					layer.alert(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	
		  var option = {
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    legend: {
				        data: businessNames
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    xAxis:  {
				        type: 'category',
				        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				        
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: businessFreightDatas
				};    
		  // 使用刚指定的配置项和数据显示图表。
		  echarts.init(document.getElementById('ywyftj')).setOption(option);
		  
		  var option = {
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    legend: {
				        data: logisticsNames
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    xAxis:  {
				        type: 'category',
				        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				        
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: logisticsFreightDatas
				};    
		// 使用刚指定的配置项和数据显示图表。
		echarts.init(document.getElementById('kdyftj')).setOption(option);
	
		var option = {
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    legend: {
				        data: logisticsNames
				    },
				    grid: {
				        left: '3%',
				        right: '4%',
				        bottom: '3%',
				        containLabel: true
				    },
				    xAxis:  {
				        type: 'category',
				        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				        
				    },
				    yAxis: {
				        type: 'value'
				    },
				    series: logisticsPiecesDatas
				};    
		// 使用刚指定的配置项和数据显示图表。
		echarts.init(document.getElementById('kdjstj')).setOption(option);
	}
</script>
<%@ include file="../../common/footer.jsp"%>