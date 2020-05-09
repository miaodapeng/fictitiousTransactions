<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售主管" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/china.js?rnd=<%=Math.random()%>"></script>

	<div class="main-container">
		<div class="data-doc-exchange mb10">
            <ul class="f_left">
                <li class="active" style= 'border-radius: 2px;'>销售主管</li>
            </ul>
            <ul class="f_right setbox">
                <li class="mr20">
                     <div class="addtitle" tabTitle='{"num":"leader-para","link":"./home/page/configSaleGoalPage.do","title":"设定销售目标"}'>
                        <i class="iconsettarget"></i>
                        <span>设定销售目标</span>
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
                        <div class="charts-container tcenter">
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
                        <div class="charts-container tcenter">
                        	<div class="charts-data-erp">
                               <p>到款<span class="my-money" id="mwc">0</span>万</p>
                               <p>目标<span id="mgo">0</span>万</p>
                            </div>
                            <div class="charts-show" id="month_main" style="width:100%;height:250px;"></div>
                        </div>
                    </div>
                </li>
                <c:forEach items="${userList}" var="user">
                	<li>
	                    <div class="charts">
	                        <div class="charts-title">本月-${user.username}</div>
	                        <div class="charts-container tcenter">
		                        <div class="charts-data-erp">
	                               <p>到款<span class="my-money" id="mwc_${user.userId}">0</span>万</p>
	                               <p>目标<span id="mgo_${user.userId}">0</span>万</p>
	                            </div>
	                            <div id="month_main_${user.userId}" style="width:100%;height:250px;"></div>
	                        </div>
	                    </div>
	                </li>
                </c:forEach>
            </ul>
        </div>
    </div>
<script type="text/javascript">
	var goalData ='';//目标
	var cpData = '';//完成
	var salerGoalData = '';//销售人员目标
	var salerCpData = '';//人员完成
	
	$.ajax({
		url:page_url+'/home/page/getMyHomePageMsg.do',
		dataType : "json",
		async: false,
		success:function(data)
		{
			if(data.code==0 && data.data != null){
				goalData = data.data.goalList;
				cpData = data.data.completeList;
				salerGoalData = data.data.salesGoalSettingList;
				salerCpData = data.data.moneyMap;
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
	
	
	//年度总目标
	var date=new Date;
	var year=date.getFullYear(); 
	var month=date.getMonth();
		
	var yearSum = Number(0);//年度总目标
	var quarterSum = Number(0);//季度总目标
	var monthSum = Number(0);//月度总目标
	for(var i = 0; i < goalData.length; i++){
		yearSum += Number(goalData[i]);
		if(i < 3){
			quarterSum += Number(goalData[i]);
		}else if(i >= 3 && i < 6){
			quarterSum += Number(goalData[i]);
		}else if(i >= 6 && i < 9){
			quarterSum += Number(goalData[i]);
		}else if(i >= 9 && i < 12){
			quarterSum += Number(goalData[i]);
		}
		if(i == month){
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
		if(i < 3){
			quarterwc += Number(cpData[i]);
		}else if(i >= 3 && i < 6){
			quarterwc += Number(cpData[i]);
		}else if(i >= 6 && i < 9){
			quarterwc += Number(cpData[i]);
		}else if(i >= 9 && i < 12){
			quarterwc += Number(cpData[i]);
		}
		if(i == month){
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
	                max:quarterSum.toFixed(2),// 最大值
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
		            data: [{value: quarterwc, name: ''}]
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
	                max:yearSum.toFixed(2),// 最大值
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
		            data: [{value: yearwc, name: ''}]
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
		//人员月度完成
		for(var i = 0; i<salerGoalData.length; i++){
			var wc = '';
			for(var key in salerCpData){
				if(key == salerGoalData[i].userId){
					wc = salerCpData[key];
				}
			}
			if(wc == ''){
				wc = 0;
			}
				$("#mgo_"+salerGoalData[i].userId).html(salerGoalData[i].goal);
				$("#mwc_"+salerGoalData[i].userId).html(wc);
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
	                        max:salerGoalData[i].goal.toFixed(2),// 最大值
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
	        	echarts.init(document.getElementById('month_main_'+salerGoalData[i].userId)).setOption(option);
		}
    
</script>
<%@ include file="../../common/footer.jsp"%>