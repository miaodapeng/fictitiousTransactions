<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后数据" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
	<div class="main-container">
        <div class="charts">
            <div class="charts-title">销售售后订单数量统计</div>
            <div class="charts-container">
                 <div id="xsddshtj_main" style="width:100%;height:400px;"></div>
            </div>
        </div>
         <div class="charts">
            <div class="charts-title">销售订单退货率统计 </div>
            <div class="charts-container">
            	<div id="shddthltj_main" style="width:100%;height:400px;"></div>
            </div>    
        </div>
         <div class="charts">
            <div class="charts-title">售后收支统计 </div>
            <div class="charts-container">
            	<div id="shsztj_main" style="width:100%;height:400px;"></div>
             </div>    
        </div>
        <div class="charts">
            <div class="charts-title">销售订单退货金额统计 </div>
            <div class="charts-container">
            	<div id="xsddthjetj_main" style="width:100%;height:400px;"></div>
             </div>    
        </div>
         <div class="charts-multi charts-two">
            <ul>
                <li>
                    <div class="charts">
                        <div class="charts-title">销售订单售后类型占比</div>
                        <div class="charts-container">
                        	<div class="charts-sale-charts" id="xsddshlxzb_main" style="width:100%;height:300px;">
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">销售订单退货原因</div>
                        <div class="charts-container">
                        	<div class="charts-sale-charts" id="xsddthyy_main" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">销售订单换货原因</div>
                        <div class="charts-container">
                        	<div class="charts-sale-charts" id="xsddhhyy_main" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">第三方售后类型占比</div>
                        <div class="charts-container">
                        	<div class="charts-sale-charts" id="dsfshlxzb_main" style="width:100%;height:300px;"></div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
         <div class="charts">
            <div class="charts-title">数据导出</div>
            <div class="charts-container charts-container1">
                <div class="form-list chart-form-list">
                    <ul>
                        <li>
                            <div class="f_left ">
                                <div class="form-blanks">
                                    <input class="Wdate f_left wid18" type="text" placeholder="请选择月份" onClick="WdatePicker()" id="startDate">
                                    <input class="Wdate f_left wid18" type="text" placeholder="请选择月份" onClick="WdatePicker()" id="endDate">
                                    <!-- <span class="bt-small bt-bg-style bg-light-blue">导出售后数据统计</span> -->
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="ptb10">
					<span class="bt-small bg-light-blue bt-bg-style" onclick="exportAfterGoodsDetailList()">导出售后商品明细</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="exportBussinessDetailList()">导出商机明细</span> 
					<span class="bt-small bg-light-blue bt-bg-style" onclick="exportGoodsCodeDetailList()">确定导出商品识别码维度明细表</span> 
                </div>
            </div>
        </div>
          
    </div>
<script type="text/javascript">
	var saleorderAftersaleOrderSumList = '';
	var tbData = '';
	var hbData = '';
	var saleAfterSaleIncomeList = '';
	var saleAfterSalePayList = '';
	var thirdAfterSaleIncomeList = '';
	var thirdAfterSalePayList = '';
	var saleorderRefundAmonutList = '';
	var saleorderRefundAmonutMomList = '';
	var salesNames ='';
	var salesNums ='';
	var thirdNames ='';
	var thirdNums ='';
	var returnRateList = '';
	var momReturnRateList = '';
	var returnSalesNames = '';
	var returnSalesNums = '';
	var exchangeSalesNames = '';
	var exchangeSalesNums = '';
	
	$.ajax({
		url:page_url+'/datacenter/aftersales/getAftersalesDataCenter.do',
		dataType : "json",
		async: false,
		success:function(data)
		{
			if(data.code==0){
				saleorderAftersaleOrderSumList = data.data.saleorderAftersaleOrderSumList;
				tbData = data.data.anList;
				hbData = data.data.momList;
				saleAfterSaleIncomeList = data.data.saleAfterSaleIncomeList;
				saleAfterSalePayList = data.data.saleAfterSalePayList;
				thirdAfterSaleIncomeList = data.data.thirdAfterSaleIncomeList;
				thirdAfterSalePayList = data.data.thirdAfterSalePayList;
				saleorderRefundAmonutList = data.data.saleorderRefundAmonutList;
				saleorderRefundAmonutMomList = data.data.saleorderRefundAmonutMomList;
				salesNames = data.data.salesNames;
				salesNums = data.data.salesNums;
				thirdNames = data.data.thirdNames;
				thirdNums = data.data.thirdNums;
				returnRateList = data.data.returnRateList;
				momReturnRateList = data.data.momReturnRateList;
				returnSalesNames = data.data.returnSalesNames;
				returnSalesNums = data.data.returnSalesNums;
				exchangeSalesNames = data.data.exchangeSalesNames;
				exchangeSalesNums = data.data.exchangeSalesNums;
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
	
    var option = {// 指定图表的配置项和数据
   	    title : {
   	        text: ''
   	    },
   	    tooltip : {
   	        trigger: 'axis'
   	    },
   	    legend: {
   	        data:['售后订单数','同比','环比']
   	    },
   	    toolbox: {
   	        show : true,
   	        feature : {
   	            dataView : {show: true, readOnly: false},
   	            magicType : {show: true, type: ['line', 'bar']},
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
   	           // max: 400,
   	            //interval: 100
   	        }
   	      ,
          {
              type: 'value',
              name: '百分比',
             // min: -50,
              //max: 50,
              //interval: 25,
              axisLabel: {
                  formatter: '{value} %'
              }
          }
   	    ],
   	    series : [
   	        {
   	            name:'售后订单数',
   	            type:'bar',
   	         	barWidth : 30,
	   	        itemStyle: { 
	                 normal:{  
	                     color: function (params){
	                         var colorList = ['#5282E4'];
	                         return colorList[params.dataIndex];
	                     }
	                 }
	            },
   	            data:saleorderAftersaleOrderSumList
   	        },
   	     	{
   	            name:'同比',
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
   	            name:'环比',
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
    echarts.init(document.getElementById('xsddshtj_main')).setOption(option);

    var option = {// 指定图表的配置项和数据
       	    title : {
       	        text: ''
       	    },
       	    tooltip : {
       	        trigger: 'axis'
       	    },
       	    legend: {
       	        data:['销售售后收入','销售售后支出','第三方售后收入','第三方售后支出']
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
       	            name: ''
       	            //min: 0,
       	           // max: 400,
       	            //interval: 100
       	        }
       	      
       	    ],
       	    series : [
       	     	{
       	            name:'销售售后收入',
       	            type:'bar',
    	   	        itemStyle: { 
    	                 normal:{
    	                	 lineStyle:{  
                                 color:'#F6D13D'  
                             }
    	                 }
    	            },
       	            data:saleAfterSaleIncomeList 	         
       	        },
       	     	{
       	            name:'销售售后支出',
       	            type:'bar',
    	   	        itemStyle: { 
    	                 normal:{
    	                	 lineStyle:{  
                                 color:'#FE969B'  
                             }
    	                 }
    	            },
       	            data:saleAfterSalePayList 
       	         
       	        },{
       	            name:'第三方售后收入',
       	            type:'bar',
    	   	        itemStyle: { 
    	                 normal:{
    	                	 lineStyle:{  
                                 color:'#F6D15D'  
                             }
    	                 }
    	            },
       	            data:thirdAfterSaleIncomeList
       	         
       	        },
       	     	{
       	            name:'第三方售后支出',
       	            type:'bar',
    	   	        itemStyle: { 
    	                 normal:{
    	                	 lineStyle:{  
                                 color:'#FE963B'  
                             }
    	                 }
    	            },
       	            data:thirdAfterSalePayList 
       	         
       	        }
       	    ]
       	};
        // 使用刚指定的配置项和数据显示图表。
        echarts.init(document.getElementById('shsztj_main')).setOption(option);
        
        var option = {// 指定图表的配置项和数据
           	    title : {
           	        text: ''
           	    },
           	    tooltip : {
           	        trigger: 'axis'
           	    },
           	    legend: {
           	        data:['销售订单退货率统计','环比']
           	    },
           	    toolbox: {
           	        show : true,
           	        feature : {
           	            dataView : {show: true, readOnly: false},
           	            magicType : {show: true, type: ['line']},
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
                      name: '百分比',
                     // min: -50,
                      //max: 50,
                      //interval: 25,
                      axisLabel: {
                          formatter: '{value} %'
                      }
                  }
           	    ],
           	    series : [
           	     	{
           	            name:'销售订单退货率',
           	            type:'line',
        	   	        itemStyle: { 
        	                 normal:{
        	                	 lineStyle:{  
                                     color:'#FE963B'  
                                 }
        	                 }
        	            },
           	            data:returnRateList 
           	         
           	        },
           	     	{
           	            name:'环比',
           	            type:'line',
        	   	        itemStyle: { 
        	                 normal:{
        	                	 lineStyle:{  
                                     color:'#FE963B'  
                                 }
        	                 }
        	            },
           	            data:momReturnRateList 
           	         
           	        }
           	    ]
           	};
            // 使用刚指定的配置项和数据显示图表。
        echarts.init(document.getElementById('shddthltj_main')).setOption(option);
        
        var option = {// 指定图表的配置项和数据
           	    title : {
           	        text: ''
           	    },
           	    tooltip : {
           	        trigger: 'axis'
           	    },
           	    legend: {
           	        data:['销售订单退货金额','环比']
           	    },
           	    toolbox: {
           	        show : true,
           	        feature : {
           	            dataView : {show: true, readOnly: false},
           	            magicType : {show: true, type: ['bar','line']},
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
           	            name: '（万元）'
           	            //min: 0,
           	           // max: 400,
           	            //interval: 100
           	        }
           	      ,
                  {
                      type: 'value',
                      name: '百分比',
                     // min: -50,
                      //max: 50,
                      //interval: 25,
                      axisLabel: {
                          formatter: '{value} %'
                      }
                  }
           	    ],
           	    series : [
           	     	{
           	            name:'销售订单退货金额',
           	            type:'bar',
           	         	barWidth : 30,
        	   	        itemStyle: { 
        	                 normal:{
        	                	 lineStyle:{  
                                     color:'#F6D15D'  
                                 }
        	                 }
        	            },
           	            data:saleorderRefundAmonutList
           	         
           	        },
           	     	{
           	            name:'环比',
           	            type:'line',
           	         	yAxisIndex: 1,
        	   	        itemStyle: { 
        	                 normal:{
        	                	 lineStyle:{  
                                     color:'#FE963B'  
                                 }
        	                 }
        	            },
           	            data:saleorderRefundAmonutMomList 
           	         
           	        }
           	    ]
           	};
            // 使用刚指定的配置项和数据显示图表。
            echarts.init(document.getElementById('xsddthjetj_main')).setOption(option);
            
            var indexdata = {na:salesNames,va:salesNums};	
            var option = {
               	    tooltip : {
               	        trigger: 'item',
               	        formatter: "{a} <br/>{b} : {c} ({d}%)"
               	    },
               	    legend: {
               	        orient: 'vertical',
               	        left: 'right',
               	        data: salesNames
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
                echarts.init(document.getElementById('xsddshlxzb_main')).setOption(option);
            	
            	 var returndata = {na:returnSalesNames,va:returnSalesNums};	
                 var option = {
                    	    tooltip : {
                    	        trigger: 'item',
                    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    	    },
                    	    legend: {
                    	        orient: 'vertical',
                    	        left: 'right',
                    	        data: returnSalesNames
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
                                     for(var i=0,size=returndata.na.length;i<size;i++) {
             	                        res.push({
             		                        name: returndata.na[i],
             		                        value: returndata.va[i]
                                     	});
                                     }
                                     return res;
                                     })()
                    	        }
                    	    ]
                    	};
                  // 使用刚指定的配置项和数据显示图表。
                     echarts.init(document.getElementById('xsddthyy_main')).setOption(option);
                 	
           	 var exchangedata = {na:exchangeSalesNames,va:exchangeSalesNums};	
                var option = {
                   	    tooltip : {
                   	        trigger: 'item',
                   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                   	    },
                   	    legend: {
                   	        orient: 'vertical',
                   	        left: 'right',
                   	        data: exchangeSalesNames
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
                                    for(var i=0,size=exchangedata.na.length;i<size;i++) {
            	                        res.push({
            		                        name: exchangedata.na[i],
            		                        value: exchangedata.va[i]
                                    	});
                                    }
                                    return res;
                                    })()
                   	        }
                   	    ]
                   	};
                 // 使用刚指定的配置项和数据显示图表。
                echarts.init(document.getElementById('xsddhhyy_main')).setOption(option);
             
                var saledata = {na:thirdNames,va:thirdNums};
                var option = {
                   	    tooltip : {
                   	        trigger: 'item',
                   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                   	    },
                   	    legend: {
                   	        orient: 'vertical',
                   	        left: 'right',
                   	        data: thirdNames
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
             echarts.init(document.getElementById('dsfshlxzb_main')).setOption(option);

</script>
<%@ include file="../../common/footer.jsp"%>