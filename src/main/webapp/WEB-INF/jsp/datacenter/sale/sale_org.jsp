<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售部门数据" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
	<div class="form-list chart-form-list form-tips7">
        <ul style="margin-bottom:0;">
            <li style="margin-bottom:0;">
                <div class="form-tips" style="margin-top:2px; ">
                    <lable>请选择销售部门</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks ">
                        <select class="wid18 ">
                            <option selected="selected ">请选择</option>
                            <option></option>
                        </select>
                        <span class="bt-small bt-bg-style bg-light-blue ">查询</span>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="main-container" style="padding-top:0;">
        <div class="charts ">
            <div class="charts-title ">月度到款额</div>
            <div class="charts-container " id="yddke_div" style="width:100%;height:400px;">
            </div>
        </div>
        <div class="charts ">
            <div class="charts-title">客单价</div>
            <div class="charts-container " id="kdj_div" style="width:100%;height:400px;">
            </div>
        </div>
        <div class="charts ">
            <div class="charts-title">成交客户占比</div>
            <div class="charts-container " id="cjkhzb_div" style="width:100%;height:400px;">
            </div>
        </div>
    </div>
<script type="text/javascript">
	var option = {
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
	        data:['目标','完成','环比','同比']
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
	            name: '到款额',
	            min: 0,
	            max: 250,
	            interval: 50,
	            axisLabel: {
	                formatter: '{value}'
	            }
	        },
	        {
	            type: 'value',
	            name: '%',
	            min: 0,
	            max: 25,
	            interval: 5,
	            axisLabel: {
	                formatter: '{value}'
	            }
	        }
	    ],
	    series: [
	        {
	            name:'目标',
	            type:'bar',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        },
	        {
	            name:'完成',
	            type:'bar',
	            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
	        },
	        {
	            name:'环比',
	            type:'line',
	            yAxisIndex: 1,
	            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
	        },
	        {
	            name:'同比',
	            type:'line',
	            yAxisIndex: 1,
	            data:[12.0, 20.2, 3.3, 4.5, 5.3, 10.2, 20.3, 23.4, 13.0, 16.5, 12.0, 16.2]
	        }
	    ]
	};
	echarts.init(document.getElementById('yddke_div')).setOption(option);
	
	
	var option = {
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
	        data:['客单价','环比','同比']
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
	            name: '',
	            min: 0,
	            max: 250,
	            interval: 50,
	            axisLabel: {
	                formatter: '{value}'
	            }
	        },
	        {
	            type: 'value',
	            name: '%',
	            min: 0,
	            max: 25,
	            interval: 5,
	            axisLabel: {
	                formatter: '{value}'
	            }
	        }
	    ],
	    series: [
	        {
	            name:'客单价',
	            type:'bar',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        },
	        {
	            name:'环比',
	            type:'line',
	            yAxisIndex: 1,
	            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
	        },
	        {
	            name:'同比',
	            type:'line',
	            yAxisIndex: 1,
	            data:[12.0, 20.2, 3.3, 4.5, 5.3, 10.2, 20.3, 23.4, 13.0, 16.5, 12.0, 16.2]
	        }
	    ]
	};
	echarts.init(document.getElementById('kdj_div')).setOption(option);
	
	
	var option = {
	    title: {
	        text: ''
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
	        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [
	        {
	            name:'成交占比',
	            type:'line',
	            stack: '总量',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        }
	    ]
	};
	echarts.init(document.getElementById('cjkhzb_div')).setOption(option);
</script>
<%@ include file="../../common/footer.jsp"%>