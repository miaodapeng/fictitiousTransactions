<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="物流总监" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/china.js?rnd=<%=Math.random()%>"></script>

	<div class="main-container">
		<div class="data-doc-exchange mb10">
            <ul class="f_left">
                <li class="active" style= 'border-radius: 2px;'>物流总监</li>
            </ul>
            <ul class="f_right setbox">
                <li>
                     <div class="addtitle" tabTitle='{"num":"setsaletarget","link":"./home/page/configSaleParamsPage.do","title":"参数设置"}'>
                        <i class="iconsetparameter"></i>
                        <span>参数设置</span>
                    </div>
                </li>
            </ul>
            <div class="clear"></div>
        </div>

        <div class="charts-multi charts-two">
            <ul>
                <li>
                    <div class="charts">
                        <div class="charts-title">本月运费支出占比</div>
                        <div class="charts-container">
                            <div class="charts-sale-charts" id="byyfzczb_main" style="width:100%;height:300px;"></div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">本月业务发货占比</div>
                        <div class="charts-container tcenter">
                           <div class="charts-sale-charts" id="byywfhzb_main" style="width:100%;height:300px;"></div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        
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
        
       <div class="charts">
            <div class="charts-title">出入库统计</div>
            <div class="charts-container">
            	<div id="crktj" style="width:100%;height:400px;"></div>
            </div>
       </div>
        
       <div class="charts">
            <div class="charts-title">库存周转率</div>
            <div class="charts-container">
            	<div id="kczzl" style="width:100%;height:400px;"></div>
            </div>
        </div>
    </div>
<script type="text/javascript">
	var tbData = '';//同比
	var hbData = '';//环比
	var logisticsNames ='';
	var logisticsAmount = '';
	var businessNames = '';
	var businessNums = '';
	var businessFreightDatas = [];
	var logisticsFreightDatas = [];
	var logisticsPiecesDatas = [];
	var outWarehouseList = '';
	var enterWarehouseList = '';
	var stockTurnoverList = '';
	
	$.ajax({
		url:page_url+'/home/page/getLogisticsHomePageMsg.do',
		dataType : "json",
		async: false,
		success:function(data)
		{
			if(data != null && data.code==0 && data.data != null){
				tbData = data.data.anList;
				hbData = data.data.momList;
				logisticsNames = data.data.logisticsNames;
				logisticsAmount = data.data.logisticsAmount;
				businessNames = data.data.businessNames;
				businessNums = data.data.businessNums;
				
				outWarehouseList = data.data.outWarehouseList;
				enterWarehouseList = data.data.enterWarehouseList;
				stockTurnoverList = data.data.stockTurnoverList;
				
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
				//layer.alert(data.message);
			}
		},
		error:function(data){
			if(data.status ==1001){
				layer.alert("当前操作无权限")
			}
		}
	});
	
	 var indexdata = {na:logisticsNames,va:logisticsAmount};	
     var option = {
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
        	    },
        	    legend: {
        	        orient: 'vertical',
        	        left: 'right',
        	        data: logisticsNames
        	    },
        	    series : [
        	        {
        	            name: '',
        	            type: 'pie',
        	            radius : '80%',
        	            center: ['50%', '50%'],
        	            data:(function(){
                         var res = [];
                         var len = 0;
                         for(var i=0,size=indexdata.na.length;i<size;i++) {
 	                        res.push({
 		                        name: indexdata.na[i],
 		                        value: indexdata.va[i]
                         	});
                         }
                         return res;
                         })()
        	        }
        	    ]
        	};
      // 使用刚指定的配置项和数据显示图表。
         echarts.init(document.getElementById('byyfzczb_main')).setOption(option);
      
      
         var saledata = {na:businessNames,va:businessNums};
         var option = {
            	    tooltip : {
            	        trigger: 'item',
            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
            	    },
            	    legend: {
            	        orient: 'vertical',
            	        left: 'right',
            	        data: businessNames
            	    },
            	    series : [
            	        {
            	            name: '',
            	            type: 'pie',
            	            radius : '80%',
            	            center: ['50%', '50%'],
            	            data:(function(){
                             var res = [];
                             var len = 0;
                             for(var i=0,size=saledata.na.length;i<size;i++) {
     	                        res.push({
     		                        name: saledata.na[i],
     		                        value: saledata.va[i]
                             	});
                             }
                             return res;
                             })()
            	        }
            	    ]
            	};
          // 使用刚指定的配置项和数据显示图表。
      echarts.init(document.getElementById('byywfhzb_main')).setOption(option);
          
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

    var option = {// 指定图表的配置项和数据
       	    title : {
       	        text: ''
       	    },
       	    tooltip : {
       	        trigger: 'axis'
       	    },
       	    legend: {
       	        data:['入库','出库']
       	    },
       	    toolbox: {
       	        show : true,
       	        feature : {
       	            dataView : {show: true, readOnly: false},
       	            magicType : {show: true, type: ['bar']},
       	            restore : {show: true},
       	            saveAsImage : {show: true}
       	        }
       	    },
       	    calculable : true,
       	    xAxis : [
       	        {
       	            type : 'category',
       	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
       	        }
       	    ],
       	    yAxis : [
       	         {
       	            type: 'value',
       	            name: '商品件数'
       	            //min: 0,
       	            //max: 2000,
       	            //interval: 500
       	        }
              ],
       	    series : [
       	        {
       	            name:'入库',
       	            type:'bar',
       	         	barWidth : 20,
    	   	        itemStyle: { 
    	                 normal:{  
    	                     color: function (params){
    	                         var colorList = ['#5282E4'];
    	                         return colorList[params.dataIndex];
    	                     }
    	                 }
    	            },
       	            data:enterWarehouseList
       	        },
       	        {
       	            name:'出库',
       	            type:'bar',
       	         	barWidth : 20,
    	   	        itemStyle: { 
    	                 normal:{  
    	                     color: function (params){
    	                         var colorList = ['#9CCC66'];
    	                         return colorList[params.dataIndex];
    	                     }
    	                 }
    	            },
       	            data:outWarehouseList 
       	         
       	        }
       	    ]
       	};
        // 使用刚指定的配置项和数据显示图表。
        echarts.init(document.getElementById('crktj')).setOption(option);
        
        var option = {// 指定图表的配置项和数据
           	    title : {
           	        text: ''
           	    },
           	    tooltip : {
           	        trigger: 'axis'
           	    },
           	    legend: {
           	        data:['库存周转率','环比','同比']
           	    },
           	    toolbox: {
           	        show : true,
           	        feature : {
           	            dataView : {show: true, readOnly: false},
           	            magicType : {show: true, type: ['bar','line' ]},
           	            restore : {show: true},
           	            saveAsImage : {show: true}
           	        }
           	    },
           	    calculable : true,
           	    xAxis : [
           	        {
           	            type : 'category',
           	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
           	        }
           	    ],
           	    yAxis : [
           	         {
           	            type: 'value',
           	            name: ''
           	            //min: 0,
           	            //max: 2000,
           	            //interval: 500
           	        },
                  	{
                      type: 'value',
                      name: '',
                      //min: -50,
                      //max: 50,
                      //interval: 25,
                      axisLabel: {
                          formatter: '{value} %'
                      }
                  }],
           	    series : [
           	        {
           	            name:'库存周转率',
           	            type:'bar',
           	         	barWidth : 20,
        	   	        itemStyle: { 
        	                 normal:{  
        	                     color: function (params){
        	                         var colorList = ['#5282E4'];
        	                         return colorList[params.dataIndex];
        	                     }
        	                 }
        	            },
           	            data: stockTurnoverList 
           	        },
           	     	{
           	            name:'环比',
           	            type:'line',
           	         	yAxisIndex: 1,
        	   	        itemStyle: { 
        	                 normal:{
        	                	 lineStyle:{  
                                     color:'#F6D15D'  
                                 }
        	                 }
        	            },
           	            data:hbData
           	         
           	        },
           	     	{
           	            name:'同比',
           	            type:'line',
           	         	yAxisIndex: 1,
        	   	        itemStyle: { 
        	                 normal:{
        	                	 lineStyle:{  
                                     color:'#FE963B'  
                                 }
        	                 }
        	            },
           	            data:tbData 
           	         
           	        }
           	    ]
           	};
            // 使用刚指定的配置项和数据显示图表。
            echarts.init(document.getElementById('kczzl')).setOption(option);
          
    
    
</script>
<%@ include file="../../common/footer.jsp"%>