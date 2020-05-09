<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售人员分析" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>

	<div class="main-container">
        <div class="form-list">
            <form method="post" action="">
                <ul>
                    <li style="margin-bottom: 0px;">
                        <div class="form-tips" style="width: 84px;">
                            <lable>请选择销售人员</lable>
                        </div>
                        <div class="f_left ">
                            <div class="form-blanks">
                            	<select class="input-middle" name="userId" id="userId">
                            	<option value="-1">请选择</option>
								<c:forEach items="${userList}" var="list">
									<option value="${list.userId}" <c:if test="${list.userId == userId}">selected="selected"</c:if>>${list.username}</option>
								</c:forEach>
								</select>
                                <span class="bt-middle bg-light-blue bt-bg-style" onclick="userSearch();">查询</span>
                            </div>
                        </div>
                    </li>
                </ul>
            </form>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    基本信息
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">入职时间</td>
                        <td><date:date value="${userInfo.addTime}" format="yyyy-MM-dd"/></td>
                        <td class="wid20">所属部门</td><td>${userInfo.orgName}</td>
                    </tr>
                    <tr>
                        <td>客户总数</td>
                        <td></td>
                        <td>到款总额</td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="data-doc-exchange mb10">
            <ul>
                <li><a href="${pageContext.request.contextPath}/datacenter/sale/saleuser.do?userId=${userId}">数据统计</a></li>
                <li class="active">数据分析</li>
            </ul>
        </div>
        <div class="charts">
            <div class="charts-title">月度到款额</div>
            <div class="charts-container">
            	<div id="yddke_main" style="width:100%;height:400px;"></div>
            </div>
        </div>
        <div class="charts">
            <div class="charts-title">新增成交客户</div>
            <div class="charts-container">
            	<div id="xzcjkh_main" style="width:100%;height:400px;"></div>
            </div>
        </div>
        <div class="charts">
            <div class="charts-title">客单价</div>
            <div class="charts-container">
            	<div id="kdj_main" style="width:100%;height:400px;"></div>
            </div>
        </div>
        <div class="charts">
            <div class="charts-title">售后数据</div>
            <div class="charts-container">
            	<div id="shsj_main" style="width:100%;height:400px;"></div>
            </div>
        </div>
    </div>
<script type="text/javascript">
    // 指定图表的配置项和数据
    var option = {
   	    title : {
   	        text: '月度到款额'
   	    },
   	    tooltip : {
   	        trigger: 'axis'
   	    },
   	    legend: {
   	        data:['目标','完成']
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
   	            type : 'value'
   	        }
   	    ],
   	    series : [
   	        {
   	            name:'目标',
   	            type:'bar',
   	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
   	            markPoint : {
   	                data : [
   	                    {type : 'max', name: '最大值'},
   	                    {type : 'min', name: '最小值'}
   	                ]
   	            },
   	            markLine : {
   	                data : [
   	                    {type : 'average', name: '平均值'}
   	                ]
   	            }
   	        },
   	        {
   	            name:'完成',
   	            type:'bar',
   	            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
   	            markPoint : {
   	                data : [
   	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183},
   	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
   	                ]
   	            },
   	            markLine : {
   	                data : [
   	                    {type : 'average', name : '平均值'}
   	                ]
   	            }
   	        }
   	    ]
   	};
    // 使用刚指定的配置项和数据显示图表。
    echarts.init(document.getElementById('yddke_main')).setOption(option);
    
    // 指定图表的配置项和数据
    var option = {
   		title: {
            text: '新增成交客户'
        },
   	    tooltip: {
   	        trigger: 'axis',
   	        axisPointer: {
   	            type: 'cross',
   	            crossStyle: {
   	                color: '#999'
   	            }
   	        }
   	    },
   	    toolbox: {
   	        feature: {
   	            dataView: {show: true, readOnly: false},
   	            magicType: {show: true, type: ['line', 'bar']},
   	            restore: {show: true},
   	            saveAsImage: {show: true}
   	        }
   	    },
   	    legend: {
   	        data:['蒸发量','降水量','平均温度']
   	    },
   	    xAxis: [
   	        {
   	            type: 'category',
   	            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
   	            axisPointer: {
   	                type: 'shadow'
   	            }
   	        }
   	    ],
   	    yAxis: [
   	        {
   	            type: 'value',
   	            name: '水量',
   	            min: 0,
   	            max: 250,
   	            interval: 50,
   	            axisLabel: {
   	                formatter: '{value} ml'
   	            }
   	        },
   	        {
   	            type: 'value',
   	            name: '温度',
   	            min: 0,
   	            max: 25,
   	            interval: 5,
   	            axisLabel: {
   	                formatter: '{value} °C'
   	            }
   	        }
   	    ],
   	    series: [
   	        {
   	            name:'蒸发量',
   	            type:'bar',
   	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
   	        },
   	        {
   	            name:'降水量',
   	            type:'bar',
   	            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
   	        },
   	        {
   	            name:'平均温度',
   	            type:'line',
   	            yAxisIndex: 1,
   	            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
   	        }
   	    ]
   	};
    // 使用刚指定的配置项和数据显示图表。
    echarts.init(document.getElementById('xzcjkh_main')).setOption(option);
    
 	// 指定图表的配置项和数据
    var option = {
   		title: {
            text: '客单价'
        },
   	    tooltip: {
   	        trigger: 'axis',
   	        axisPointer: {
   	            type: 'cross',
   	            crossStyle: {
   	                color: '#999'
   	            }
   	        }
   	    },
   	    toolbox: {
   	        feature: {
   	            dataView: {show: true, readOnly: false},
   	            magicType: {show: true, type: ['line', 'bar']},
   	            restore: {show: true},
   	            saveAsImage: {show: true}
   	        }
   	    },
   	    legend: {
   	        data:['蒸发量','降水量','平均温度']
   	    },
   	    xAxis: [
   	        {
   	            type: 'category',
   	            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
   	            axisPointer: {
   	                type: 'shadow'
   	            }
   	        }
   	    ],
   	    yAxis: [
   	        {
   	            type: 'value',
   	            name: '水量',
   	            min: 0,
   	            max: 250,
   	            interval: 50,
   	            axisLabel: {
   	                formatter: '{value} ml'
   	            }
   	        },
   	        {
   	            type: 'value',
   	            name: '温度',
   	            min: 0,
   	            max: 25,
   	            interval: 5,
   	            axisLabel: {
   	                formatter: '{value} °C'
   	            }
   	        }
   	    ],
   	    series: [
   	        {
   	            name:'蒸发量',
   	            type:'bar',
   	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
   	        },
   	        {
   	            name:'降水量',
   	            type:'bar',
   	            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
   	        },
   	        {
   	            name:'平均温度',
   	            type:'line',
   	            yAxisIndex: 1,
   	            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
   	        }
   	    ]
   	};
    // 使用刚指定的配置项和数据显示图表。
    echarts.init(document.getElementById('kdj_main')).setOption(option);
    
    var option = {
   	    title : {
   	        text: '售后数据'
   	    },
   	    tooltip : {
   	        trigger: 'item',
   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
   	    },
   	    legend: {
   	        orient: 'vertical',
   	        left: 'right',
   	        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
   	    },
   	    series : [
   	        {
   	            name: '访问来源',
   	            type: 'pie',
   	            radius : '55%',
   	            center: ['50%', '60%'],
   	            data:[
   	                {value:335, name:'直接访问'},
   	                {value:310, name:'邮件营销'},
   	                {value:234, name:'联盟广告'},
   	                {value:135, name:'视频广告'},
   	                {value:1548, name:'搜索引擎'}
   	            ],
   	            itemStyle: {
   	                emphasis: {
   	                    shadowBlur: 10,
   	                    shadowOffsetX: 0,
   	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
   	                }
   	            }
   	        }
   	    ]
   	};
 // 使用刚指定的配置项和数据显示图表。
    echarts.init(document.getElementById('shsj_main')).setOption(option);
</script>
<%@ include file="../../common/footer.jsp"%>