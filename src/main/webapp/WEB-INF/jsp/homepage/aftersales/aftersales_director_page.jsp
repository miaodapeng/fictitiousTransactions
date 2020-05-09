<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后总监" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/china.js?rnd=<%=Math.random()%>"></script>

	<div class="main-container">
		<div class="data-doc-exchange mb10">
           	<div class='f_left'><span class="bg-light-blue bt-bg-style bt-small">售后总监</span></div>
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
        <div class="showing-card aftersales-chief showing-card1">
            <ul>
                <li>
                    <div class="card-container">
                        <div class="card-title blue-title">
                            今日数据
                        </div>
                        <div class="card-content blue-content">
                            <ul>
	                            <c:if test="${not empty asdv && not empty asdv.todayDoList }">
	                            	<c:forEach items="${asdv.todayDoList}" var="num" varStatus="status">
	                            		<c:if test="${status.count eq 1}">
	                            			<li>
			                                    <span>总机询价量</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 2}">
	                            			<li>
			                                    <span>售后订单</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 3}">
	                            			<li>
			                                    <span>销售订单退货</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 4}">
	                            			<li>
			                                    <span>销售订单换货</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            	</c:forEach>
	                            </c:if>
                            </ul>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="card-container">
                        <div class="card-title yellow-title">
                            本月数据
                        </div>
                        <div class="card-content yellow-content">
                            <ul>
                            	<c:if test="${not empty asdv && not empty asdv.thisMonthDataList }">
	                            	<c:forEach items="${asdv.thisMonthDataList}" var="num" varStatus="status">
	                            		<c:if test="${status.count eq 1}">
	                            			<li>
			                                    <span>总机询价量</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 2}">
	                            			<li>
			                                    <span>售后订单</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 3}">
	                            			<li>
			                                    <span>销售订单售后</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 4}">
	                            			<li>
			                                    <span>第三方售后</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            	</c:forEach>
	                            </c:if>
                            </ul>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="card-container">
                        <div class="card-title green-title">
                            本年度数据
                        </div>
                        <div class="card-content green-content">
                            <ul>
                                <c:if test="${not empty asdv && not empty asdv.thisYearDataList }">
	                            	<c:forEach items="${asdv.thisYearDataList}" var="num" varStatus="status">
	                            		<c:if test="${status.count eq 1}">
	                            			<li>
			                                    <span>总机询价量</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 2}">
	                            			<li>
			                                    <span>售后订单</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 3}">
	                            			<li>
			                                    <span>销售订单售后</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 4}">
	                            			<li>
			                                    <span>第三方售后</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            	</c:forEach>
	                            </c:if>
                            </ul>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="parts set-sales-target">
            <table class="table">
                <thead>
                    <tr>
	                    <th class="wid10">售后人员</th>
                        <th>销售订单退货</th>
                        <th>销售订单换货</th>
                        <th>销售订单安调</th>
                        <th>销售订单维修</th>
                        <th>销售订单退票</th>
                        <th>销售订单退款</th>
                        <th class="wid13">销售订单技术咨询</th>
                        <th>销售订单其他</th>
                        <th>第三方安调</th>
                        <th>第三方维修</th>
                        <th>第三方退款</th>
                        <th>第三方技术咨询</th>
                        <th>第三方其他</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${asdv.afterUserList}" var="user" >
               			<tr>
                			<td>${user.username}</td>
                			<c:forEach items="${asdv.aftersaleMap}" var="mymap">
                				<c:if test="${mymap.key eq user.userId}">
                					<c:set var="contains" value="false" /> 
                					<c:set var="num" value="0" />
                					<!-- 销售订单退货 -->
               						<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 539}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 销售订单换货 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 540}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 销售订单安调 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 541}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 销售订单维修-->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 584}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 销售订单退票 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 542}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 销售订单退款 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 543}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 销售订单技术咨询 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 544}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 销售订单其他 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 545}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 第三方安调 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 550}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 第三方维修 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 585}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 第三方退款 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 551}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 第三方技术咨询 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 552}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
	                        		<!-- 第三方其他 -->
	                        		<c:forEach items="${mymap.value}" var="as">
               							 <c:if test="${as.type eq 553}">
		                        		 	<c:set var="contains" value="true" /> 
		                        		 	<c:set var="num" value="${as.orderNum}" />
		                        		 </c:if>
               						</c:forEach>
               						<c:choose>  
						    			<c:when test="${contains == true }">
						    				<c:set var="contains" value="false" /> 
						    				<td>${num}</td>
						    			</c:when>
		                        		<c:otherwise>
		                        		 	<td>0</td>
		                        		</c:otherwise> 
	                        		</c:choose>
                				</c:if>
                			</c:forEach>
	                    </tr>
               		</c:forEach>
                </tbody>
            </table>
        </div>
        
        <div class="charts">
            <div class="charts-title">销售订单售后统计</div>
            <div class="charts-container">
            	<div id="xsddshtj_main" style="width:100%;height:400px;"></div>
            </div>
       </div>
       
       <div class="charts">
            <div class="charts-title">售后收支统计</div>
            <div class="charts-container">
            	<div id="shsztj_main" style="width:100%;height:400px;"></div>
            </div>
       </div>
       
       <div class="charts">
            <div class="charts-title">销售订单退货金额统计</div>
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
                        <div class="charts-title">第三方售后类型占比</div>
                        <div class="charts-container">
                            <div class="charts-sale-charts" id="dsfshlxzb_main" style="width:100%;height:300px;"></div>
                        </div>
                    </div>
                </li>
           </ul>
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
	$.ajax({
		url:page_url+'/home/page/getAfterSalesHomePageMsg.do',
		dataType : "json",
		async: false,
		success:function(data)
		{
			if(data.code==0 && data.data != null){
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