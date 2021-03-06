<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售总经理" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/china.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
	//var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:38%;top:32%;"></i></div>';
	//$("body").prepend(div);
</script>
	<div class="main-container">
		<div class="data-doc-exchange mb10">
            <ul class="f_left">
                <li class="active">销售总经理</li>
            </ul>
            <ul class="f_right setbox">
                <li class="mr20">
                     <div class="addtitle" tabTitle='{"num":"leader-para","link":"./home/page/configSaleGoalPage.do","title":"设定销售目标"}'>
                        <i class="iconsettarget"></i>
                        <span>设定销售目标</span>
                    </div>
                </li>
                <li>
                     <div class="addtitle" tabTitle='{"num":"setsaletarget","link":"./home/page/configSaleParamsPage.do","title":"参数设置"}'>
                        <i class="iconsetparameter"></i>
                        <span>参数设置</span>
                    </div>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
        <div class="charts">
            <div class="charts-title">月度到款额</div>
            <div class="charts-container">
            	<div id="yddke_main" style="width:100%;height:400px;"></div>
            </div>
        </div>
        <div class="charts-multi charts-three">
            <ul>
                <li>
                    <div class="charts">
                        <div class="charts-title">本年度完成率</div>
                        <div class="charts-container">
                        	<div class="charts-data-erp">
                               <p>到款<span class="my-money" id="ywc">0</span>万</p>
                               <p>目标<span id="ygo">0</span>万</p>
                            </div>
                            <div class="charts-show" id="year_main" style="width:100%;height:250px;">
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">本季度完成率</div>
                        <div class="charts-container">
                        	<div class="charts-data-erp">
                               <p>到款<span class="my-money" id="qwc">0</span>万</p>
                               <p>目标<span id="qgo">0</span>万</p>
                            </div>
                            <div class="charts-show" id="quarter_main" style="width:100%;height:250px;"></div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">本月度完成率</div>
                        <div class="charts-container">
                        	<div class="charts-data-erp">
                               <p>到款<span class="my-money" id="mwc">0</span>万</p>
                               <p>目标<span id="mgo">0</span>万</p>
                            </div>
                            <div class="charts-show" id="month_main" style="width:100%;height:250px;"></div>
                        </div>
                    </div>
                </li>
                <c:forEach items="${orgList}" var="org">
                	<li>
	                    <div class="charts">
	                        <div class="charts-title">本月-${org.orgName}</div>
	                        <div class="charts-container">
		                        <div class="charts-data-erp">
	                               <p>到款<span class="my-money" id="mwc_${org.orgId}">0</span>万</p>
	                               <p>目标<span id="mgo_${org.orgId}">0</span>万</p>
	                            </div>
	                            <div id="month_main_${org.orgId}" style="width:100%;height:250px;"></div>
	                        </div>
	                    </div>
	                </li>
                </c:forEach>
            </ul>
        </div>
        <div class="charts-multi charts-two">
            <ul>
                <li>
                    <div class="charts">
                        <div class="charts-title">本月销售额占比</div>
                        <div class="charts-container">
                            <div class="charts-sale-charts" id="byxse_main" style="width:100%;height:300px;">
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">本月订单数占比</div>
                        <div class="charts-container">
                            <div class="charts-sale-charts" id="bydds_main" style="width:100%;height:300px;"></div>
                        </div>
                    </div>
                </li>
           </ul>
        </div>
        <div class="charts">
            <div class="charts-title">客户分布图</div>
            <div class="charts-container" id="cusmap">
            	<div class="charts-department-detail">
                   <ul>
                       <li class='inputfloat'>
                           <input type="checkbox" name="customerNature" checked="checked" value="465"><span>经销商</span>
                           <input type="checkbox" name="customerNature" checked="checked" value="466"><span>终端</span>
                       </li>
                   </ul>
                 </div>
            	<div id="khfbt_main" style="width:100%;height:500px;"></div>
            </div>
        </div>
        
    </div>
<script type="text/javascript">

	var goalData ='';//目标
	var cpData = '';//完成
	var tbData = '';//同比
	var hbData = '';//环比
	var deptGoalData = '';//部门目标
	var deptCpData = '';//部门完成
	var deptNames = '';//部门名称
	var deptSaleMoney = '';//部门销售额
	var deptSaleorder = '';//部门订单数
	var proNames = '';//省份名称
	var customerSum = '';//客户数
	var minnum = '';
	var maxnum = '';
	$.ajax({
		url:page_url+'/home/page/getMyHomePageMsg.do',
		dataType : "json",
		async: false,
		success:function(data)
		{
			$(".tip-loadingNewData").remove();
			if(data.code==0 && data.data != null){
				goalData = data.data.goalList;
				cpData = data.data.completeList;
				tbData = data.data.anList;
				hbData = data.data.momList;
				deptGoalData = data.data.salesGoalSettingList;
				deptCpData = data.data.moneyMap;
				deptNames = data.data.nextDeptList;
				deptSaleMoney = data.data.deptSaleMoney;
				deptSaleorder = data.data.deptSaleorder;
				proNames = data.data.proNameList;
				customerSum = data.data.proCustomerList;
				minnum = data.data.min;
				maxnum = data.data.max;
			}else{
				$(".tip-loadingNewData").remove();
				//layer.alert(data.message);
				
			}
		},
		error:function(data){
			$(".tip-loadingNewData").remove();
			if(data.status ==1001){
				layer.alert("当前操作无权限");
				
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
   	        data:['目标','完成','环比','同比']
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
   	            name: '到款额（万元）'
   	            //min: 0,
   	            //max: 2000,
   	            //interval: 500
   	        },
          	{
              type: 'value',
              name: '百分比',
              //min: -50,
              //max: 50,
              //interval: 25,
              axisLabel: {
                  formatter: '{value} %'
              }
          }],
   	    series : [
   	        {
   	            name:'目标',
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
   	            data:goalData
   	        },
   	        {
   	            name:'完成',
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
   	            data:cpData 
   	         
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
    echarts.init(document.getElementById('yddke_main')).setOption(option);
    
    //年度总目标
    var date = new Date;
 	var year = date.getFullYear(); 
 	var month = date.getMonth();
 	
    var yearSum = Number(0);//年度总目标
    var quarterSum = Number(0);//季度总目标
    var monthSum = Number(0);//月度总目标
    for(var i = 0; i < goalData.length; i++){
    	yearSum += Number(goalData[i]);
    	if(month < 3 && i < 3){
    		quarterSum += Number(goalData[i]);
    	}else if(month < 6 && month >= 3 && i >= 3 && i < 6){
    		quarterSum += Number(goalData[i]);
    	}else if(month < 9 && month >= 6 && i >= 6 && i < 9){
    		quarterSum += Number(goalData[i]);
    	}else if(month >= 9 && i >= 9 && i < 12){
    		quarterSum += Number(goalData[i]);
    	}
    	if(month == i){
    		monthSum = Number(goalData[i]);
    	}
    	
    }
    $("#ygo").html(yearSum.toFixed(2));
    $("#qgo").html(quarterSum.toFixed(2));
    $("#mgo").html(monthSum.toFixed(2));
    //目标已完成
    var yearwc = Number(0);
    var quarterwc = Number(0);//季度完成目标
    var monthwc = Number(0);//月度完成目标
    for(var i = 0; i < cpData.length; i++){
    	yearwc += Number(cpData[i]);
    	if(month < 3 && i < 3){
    		quarterwc += Number(cpData[i]);
    	}else if(month < 6 && month >= 3 && i >= 3 && i < 6){
    		quarterwc += Number(cpData[i]);
    	}else if(month < 9 && month >= 6 && i >= 6 && i < 9){
    		quarterwc += Number(cpData[i]);
    	}else if(month >= 9 && i >= 9 && i < 12){
    		quarterwc += Number(cpData[i]);
    	}
    	if(month == i){
    		monthwc = Number(cpData[i]);
    	}
    	
    }
    $("#ywc").html(yearwc.toFixed(2));
    $("#qwc").html(quarterwc.toFixed(2));
    $("#mwc").html(monthwc.toFixed(2));
    var option = {
    	    tooltip : {
    	        formatter: "{a} <br/>{b} : {c}%"
    	    },
    	    toolbox: {
    	        feature: {
    	            restore: {},
    	            saveAsImage: {}
    	        }
    	    },
    	    grid : {  
                width : '20%' , //直角坐标轴占整页的百分比  
                height : '100%'  
            },  
    	    series: [
    	        {
    	        	name:'',
                    type:'gauge',
                    center : ['50%', '50%'],    // 默认全局居中
                    radius : '100%',
                    min:0,// 最小值
                    max:yearSum.toFixed(2),// 最大值
                    splitNumber:5,// 分割段数，默认为5
                    /* axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.5, '#ff4500'],[0.6, 'orange'],[1, '#lightgreen']],//设置刻度值的颜色
                            width: 3,
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    axisLabel: {            // 坐标轴小标记
                        textStyle: {       // 属性lineStyle控制线条样式
                            fontWeight: 'bolder',
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        length :15,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    splitLine: {           // 分隔线
                        length :25,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            width:3,
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    pointer: {           // 指针样式 
                        shadowColor : '#fff', //默认透明
                        shadowBlur: 5
                    },
                    title : {//设置标题的属性
                       offsetCenter: [0,'30%'], //标题位置
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: '',
                            fontSize: 0.1,
                            fontStyle: 'italic',
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },*/
                    detail : {
                        backgroundColor: '#fff',
                        borderWidth: 1,
                        borderColor: '#fff',
                        shadowColor : '#fff', //默认透明
                        width: 50,
                        height:50,
                        offsetCenter: [0, '50%'],       // x, y，单位px
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: '',
                            fontSize: 1,
                            color: ''              
                        }
                    },
    	            data: [{value: yearwc, name: ''}]
    	        }
    	    ]
    	};
    	echarts.init(document.getElementById('year_main')).setOption(option);	                    
    
    //本季度
    var option = {
    	    tooltip : {
    	        formatter: "{a} <br/>{b} : {c}%"
    	    },
    	    toolbox: {
    	        feature: {
    	            restore: {},
    	            saveAsImage: {}
    	        }
    	    },
    	    grid : {  
                width : '20%' , //直角坐标轴占整页的百分比  
                height : '100%'  
            },  
    	    series: [
    	        {
    	        	name:'',
                    type:'gauge',
                    center : ['50%', '50%'],    // 默认全局居中
                    radius : '100%',
                    min:0,// 最小值
                    max:quarterSum.toFixed(2),// 最大值
                    splitNumber:5,// 分割段数，默认为5
                    /*axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.5, '#ff4500'],[0.6, 'orange'],[1, '#lightgreen']],//设置刻度值的颜色
                            width: 3,
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    axisLabel: {            // 坐标轴小标记
                        textStyle: {       // 属性lineStyle控制线条样式
                            fontWeight: 'bolder',
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        length :15,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    splitLine: {           // 分隔线
                        length :25,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            width:3,
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    pointer: {           // 指针样式 
                        shadowColor : '#fff', //默认透明
                        shadowBlur: 5
                    },
                    title : {//设置标题的属性
                       offsetCenter: [0,'30%'], //标题位置
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: '',
                            fontSize: 0.1,
                            fontStyle: 'italic',
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },*/
                    detail : {
                        backgroundColor: '#fff',
                        borderWidth: 1,
                        borderColor: '#fff',
                        shadowColor : '#fff', //默认透明
                        width: 50,
                        height:50,
                        offsetCenter: [0, '50%'],       // x, y，单位px
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: '',
                            fontSize: 1,
                            color: ''              
                        }
                    },
    	            data: [{value: quarterwc, name: ''}]
    	        }
    	    ]
    	};
    	echarts.init(document.getElementById('quarter_main')).setOption(option);
    	//月度完成
    	var option = {
    	    tooltip : {
    	        formatter: "{a} <br/>{b} : {c}%"
    	    },
    	    toolbox: {
    	        feature: {
    	            restore: {},
    	            saveAsImage: {}
    	        }
    	    },
    	    grid : {  
                width : '20%' , //直角坐标轴占整页的百分比  
                height : '100%'  
            },  
    	    series: [
    	        {
    	        	name:'',
                    type:'gauge',
                    center : ['50%', '50%'],    // 默认全局居中
                    radius : '100%',
                    min:0,// 最小值
                    max:monthSum.toFixed(2),// 最大值
                    splitNumber:5,// 分割段数，默认为5
                   /* axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: [[0.5, '#ff4500'],[0.6, 'orange'],[1, '#lightgreen']],//设置刻度值的颜色
                            width: 3,
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    axisLabel: {            // 坐标轴小标记
                        textStyle: {       // 属性lineStyle控制线条样式
                            fontWeight: 'bolder',
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        length :15,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    splitLine: {           // 分隔线
                        length :25,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            width:3,
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },
                    pointer: {           // 指针样式 
                        shadowColor : '#fff', //默认透明
                        shadowBlur: 5
                    },
                    title : {//设置标题的属性
                       offsetCenter: [0,'30%'], //标题位置
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: '',
                            fontSize: 0.1,
                            fontStyle: 'italic',
                            color: '',
                            shadowColor : '#fff', //默认透明
                            shadowBlur: 10
                        }
                    },*/
                    detail : {
                        backgroundColor: '#fff',
                        borderWidth: 1,
                        borderColor: '#fff',
                        shadowColor : '#fff', //默认透明
                        width: 50,
                        height:50,
                        offsetCenter: [0, '50%'],       // x, y，单位px
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: '',
                            fontSize: 1,
                            color: ''              
                        }
                    },
    	            data: [{value: monthwc, name: ''}]
    	        }
    	    ]
    	};
    	echarts.init(document.getElementById('month_main')).setOption(option);
    	//部门月度完成
    	for(var i = 0; i<deptGoalData.length; i++){
    		var wc = '';
    		for(var key in deptCpData){
    			if(key == deptGoalData[i].orgId){
    				wc = deptCpData[key];
    			}
    		}
    		if(wc == ''){
    			wc = 0;
    		}
				$("#mgo_"+deptGoalData[i].orgId).html(deptGoalData[i].goal);
				$("#mwc_"+deptGoalData[i].orgId).html(wc);
	        	var option = {
	        	    tooltip : {
	        	        formatter: "{a} <br/>{b} : {c}%"
	        	    },
	        	    toolbox: {
	        	        feature: {
	        	            restore: {},
	        	            saveAsImage: {}
	        	        }
	        	    },
	        	    grid : {  
	                    width : '20%' , //直角坐标轴占整页的百分比  
	                    height : '100%'  
	                },  
	        	    series: [
	        	        {
	        	        	name:'',
	                        type:'gauge',
	                        center : ['50%', '50%'],    // 默认全局居中
	                        radius : '100%',
	                        min:0,// 最小值
	                        max:deptGoalData[i].goal.toFixed(2),// 最大值
	                        splitNumber:5,// 分割段数，默认为5
	                       /* axisLine: {            // 坐标轴线
	                            lineStyle: {       // 属性lineStyle控制线条样式
	                                color: [[0.5, '#ff4500'],[0.6, 'orange'],[1, '#lightgreen']],//设置刻度值的颜色
	                                width: 3,
	                                shadowColor : '#fff', //默认透明
	                                shadowBlur: 10
	                            }
	                        },
	                        axisLabel: {            // 坐标轴小标记
	                            textStyle: {       // 属性lineStyle控制线条样式
	                                fontWeight: 'bolder',
	                                color: '',
	                                shadowColor : '#fff', //默认透明
	                                shadowBlur: 10
	                            }
	                        },
	                        axisTick: {            // 坐标轴小标记
	                            length :15,        // 属性length控制线长
	                            lineStyle: {       // 属性lineStyle控制线条样式
	                                color: 'auto',
	                                shadowColor : '#fff', //默认透明
	                                shadowBlur: 10
	                            }
	                        },
	                        splitLine: {           // 分隔线
	                            length :25,         // 属性length控制线长
	                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                                width:3,
	                                color: '',
	                                shadowColor : '#fff', //默认透明
	                                shadowBlur: 10
	                            }
	                        },
	                        pointer: {           // 指针样式 
	                            shadowColor : '#fff', //默认透明
	                            shadowBlur: 5
	                        },
	                        title : {//设置标题的属性
	                           offsetCenter: [0,'30%'], //标题位置
	                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                                fontWeight: '',
	                                fontSize: 0.1,
	                                fontStyle: 'italic',
	                                color: '',
	                                shadowColor : '#fff', //默认透明
	                                shadowBlur: 10
	                            }
	                        },*/
	                        detail : {
	                            backgroundColor: '#fff',
	                            borderWidth: 1,
	                            borderColor: '#fff',
	                            shadowColor : '#fff', //默认透明
	                            width: 50,
	                            height:50,
	                            offsetCenter: [0, '50%'],       // x, y，单位px
	                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                                fontWeight: '',
	                                fontSize: 1,
	                                color: ''              
	                            }
	                        },
	        	            data: [{value: wc, name: ''}]
	        	        }
	        	    ]
	        	};
	        	echarts.init(document.getElementById('month_main_'+deptGoalData[i].orgId)).setOption(option);
    				
    			
    		
    	}
    	
    var indexdata = {na:deptNames,va:deptSaleMoney};	
    var option = {
       	    tooltip : {
       	        trigger: 'item',
       	        formatter: "{a} <br/>{b} : {c} ({d}%)"
       	    },
       	    legend: {
       	        orient: 'vertical',
       	        left: 'right',
       	        data: deptNames
       	    },
       	    series : [
       	        {
       	            name: '本月销售额占比',
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
        echarts.init(document.getElementById('byxse_main')).setOption(option);
     
     
        var saledata = {na:deptNames,va:deptSaleorder};
        var option = {
           	    tooltip : {
           	        trigger: 'item',
           	        formatter: "{a} <br/>{b} : {c} ({d}%)"
           	    },
           	    legend: {
           	        orient: 'vertical',
           	        left: 'right',
           	        data: deptNames
           	    },
           	    series : [
           	        {
           	            name: '本月订单数占比',
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
     echarts.init(document.getElementById('bydds_main')).setOption(option);
         
     var mapdata = {na:proNames,va:customerSum};
     var option = {
        	    tooltip : {
        	        trigger: 'item'
        	    },
        	    visualMap: {
        	        min: minnum,
        	        max: maxnum,
        	        left:'right',
        	        top:'1%',
        	        orient: 'horizontal',
        	        itemWidth: 15,
        	        text: ['总客户数'],// 文本，默认为数值文本
        	        color:['#20a0ff','#D2EDFF'],
        	        calculable: true
        	    },
        	    series : [
        	        {
        	            name: '中国',
        	            type: 'map',
        	            mapType: 'china',
        	            //selectedMode : 'multiple',
        	            itemStyle:{
        	                normal:{label:{show:true}},
        	                emphasis:{label:{show:true}}
        	            },
        	            data:(function(){
                            var res = [];
                            var len = 0;
                            for(var i=0,size=mapdata.na.length;i<size;i++) {
    	                        res.push({
    		                        name: mapdata.na[i],
    		                        value: mapdata.va[i]
                            	});
                            }
                            return res;
                            })()
        	        }
        	    ]
        	}; 
      echarts.init(document.getElementById('khfbt_main')).setOption(option);
      

    $("input[name='customerNature']").click(function(){
    	checkLogin();
        var value = '';
        $.each($("input[name='customerNature']"),function(i,n){
			if($(this).prop("checked")){
				value += $(this).val()+"|";
			}
		});
        if(value == ''){
        	layer.alert("客户性质不允许全为空！");
        	return false;
        }
        var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:38%;top:32%;"></i></div>';
        $("body").prepend(div);
        //echarts.init(document.getElementById('khfbt_main')).clear();
        //$("#cusmap").append('<div id="khfbt_main" style="width:100%;height:500px;"></div>')
        $.ajax({
    		url:page_url+'/home/page/getCustomerData.do',
    		data:{"customerNature":value},
    		dataType : "json",
    		success:function(data)
    		{
    			$(".tip-loadingNewData").remove();
    			if(data.code==0){
    				var mapdata = {na:data.data.proNameList,va:data.data.proCustomerList};
    			    var option = {
    			        	    tooltip : {
    			        	        trigger: 'item'
    			        	    },
    			        	    visualMap: {
    			        	        min: data.data.min,
    			        	        max: data.data.max,
    			        	        left:'right',
    			        	        top:'1%',
    			        	        orient: 'horizontal',
    			        	        itemWidth: 15,
    			        	        text: ['总客户数'],// 文本，默认为数值文本
    			        	        color:['#20a0ff','#D2EDFF'],
    			        	        calculable: true
    			        	    },
    			        	    series : [
    			        	        {
    			        	            name: '中国',
    			        	            type: 'map',
    			        	            mapType: 'china',
    			        	            //selectedMode : 'multiple',
    			        	            itemStyle:{
    			        	                normal:{label:{show:true}},
    			        	                emphasis:{label:{show:true}}
    			        	            },
    			        	            data:(function(){
    			                            var res = [];
    			                            var len = 0;
    			                            for(var i=0,size=mapdata.na.length;i<size;i++) {
    			    	                        res.push({
    			    		                        name: mapdata.na[i],
    			    		                        value: mapdata.va[i]
    			                            	});
    			                            }
    			                            return res;
    			                            })()
    			        	        }
    			        	    ]
    			        	}; 
    			      echarts.init(document.getElementById('khfbt_main')).setOption(option,true);
    			
    			}else{
    				layer.alert(data.message);
    			}
    		},
    		error:function(data){
    			$(".tip-loadingNewData").remove();
    			if(data.status ==1001){
    				layer.alert("当前操作无权限");
    			}
    		}
    	});
        
        
        
    })
    
</script>
<%@ include file="../../common/footer.jsp"%>