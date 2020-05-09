<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="成交客户统计" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
	<div class="main-container">
        <div class="showing-card showing-card3">
            <ul>
                <li>
                    <div class="card-container">
                        <div class="card-title blue-title">
                            成交客户数
                        </div>
                        <div class="card-content blue-content3">
                            207,895
                        </div>
                    </div>
                </li>
                <li>
                    <div class="card-container month-data">
                        <div class="card-title yellow-title">
                            客单价
                        </div>
                        <div class="card-content yellow-content3">
                            207,895
                        </div>
                    </div>
                </li>
                <li>
                    <div class="card-container personal-data">
                        <div class="card-title green-title">
                            均单价
                        </div>
                        <div class="card-content green-content3">
                            207,895
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="charts">
            <div class="charts-title">成交客户数与交易额</div>
            <div class="charts-container charts-container1">
                    <div class="form-list chart-form-list">
                            <ul>
                                <li>
                                    <div class="form-tips wid84">
                                        <lable>请选择统计对象</lable>
                                    </div>
                                    <div class="f_left ">
                                        <div class="form-blanks">
                                            <select class="wid18">
                                                <option selected="selected">科研业务部门</option>
                                                <option></option>
                                            </select>
                                            <select class="wid18">
                                                <option selected="selected">业务一组</option>
                                                <option></option>
                                            </select>
                                            <select class="wid18">
                                                <option selected="selected">Kim.gan</option>
                                                <option></option>
                                            </select>
                                            <span class="bt-small bt-bg-style bg-light-blue">查询</span>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                    </div>
                    <div class="ptb10" id="cjkhsycje_div" style="width:100%;height:400px;">
                    </div>
            </div>
        </div>
         <div class="charts">
            <div class="charts-title">A类客户统计表</div>
            <div class="charts-container charts-container1">
                    <div class="form-list chart-form-list">
                            <ul>
                                <li>
                                     <div class="form-tips wid84">
                                        <lable>请选择统计对象</lable>
                                    </div>
                                    <div class="f_left ">
                                        <div class="form-blanks">
                                            <select class="wid18">
                                                <option selected="selected">科研业务部门</option>
                                                <option></option>
                                            </select>
                                            <select class="wid18">
                                                <option selected="selected">业务一组</option>
                                                <option></option>
                                            </select>
                                            <select class="wid18">
                                                <option selected="selected">Kim.gan</option>
                                                <option></option>
                                            </select>
                                            <span class="bt-small bt-bg-style bg-light-blue">查询</span>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                    </div>
                    <div class="ptb10">
                        1234
                    </div>
            </div>
        </div>
          <div class="charts">
            <div class="charts-title">A类客户统计表</div>
            <div class="charts-container charts-container1">
                    <div class="form-list chart-form-list">
                            <ul>
                                <li>
                                    <div class="f_left ">
                                        <div class="form-blanks">
                                            <input class="Wdate f_left " type="text" placeholder="请选择月份" onClick="WdatePicker()">
                                            <input class="Wdate f_left wid18" type="text" placeholder="请选择月份" onClick="WdatePicker()">
                                            <span class="bt-small bt-bg-style bg-light-blue">导出成交客户统计表</span>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                    </div>
                    <div class="ptb10" id="alkhtj_div" style="width:100%;height:400px;"></div>
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
	        data:['成交客户', '交易额']
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
	            name: '万元',
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
	            name:'成交客户',
	            type:'bar',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        },
	        {
	            name:'交易额',
	            type:'line',
	            yAxisIndex: 1,
	            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
	        }
	    ]
	};
	echarts.init(document.getElementById('cjkhsycje_div')).setOption(option);
	
	
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
	        data:['客户数', '交易额']
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
	            name: '万元',
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
	            name:'客户数',
	            type:'bar',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        },
	        {
	            name:'交易额',
	            type:'line',
	            yAxisIndex: 1,
	            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
	        }
	    ]
	};
	echarts.init(document.getElementById('alkhtj_div')).setOption(option);

</script>
<%@ include file="../../common/footer.jsp"%>