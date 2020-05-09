<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品统计" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
	<div class="main-container">
          <div class="charts-multi charts-three">
            <ul>
                <li>
                    <div class="charts">
                        <div class="charts-title">产品级别占比</div>
                        <div class="charts-container" id="cpjbzb_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">产品销售额占比</div>
                        <div class="charts-container" id="cpxsezb_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">产品采购额占比</div>
                        <div class="charts-container" id="cpcgezb_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="charts">
            <div class="charts-title">产品级别与交易额</div>
            <div class="charts-container" id="cpjbyjye_div" style="width:100%;height:400px;">
            </div>
        </div>
         <div class="charts">
            <div class="charts-title">产品销售额与采购额</div>
            <div class="charts-container" id="cpxseycge_div" style="width:100%;height:400px;">
            </div>
        </div>
         <div class="charts">
            <div class="charts-title">产品新增与废弃数量</div>
            <div class="charts-container" id="cpxzyfqsl_div" style="width:100%;height:400px;">
            </div>
        </div>
    </div>
<script type="text/javascript">
	var option = {
	    title : {
	        text: '',
	        subtext: '',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'right',
	        data: ['直接访问','邮件营销','联盟广告']
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
	                {value:234, name:'联盟广告'}
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
	echarts.init(document.getElementById('cpjbzb_div')).setOption(option);
	
	var option = {
	    title : {
	        text: '',
	        subtext: '',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'right',
	        data: ['直接访问','邮件营销','联盟广告']
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
	                {value:234, name:'联盟广告'}
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
	echarts.init(document.getElementById('cpxsezb_div')).setOption(option);
	
	var option = {
	    title : {
	        text: '',
	        subtext: '',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'right',
	        data: ['直接访问','邮件营销','联盟广告']
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
	                {value:234, name:'联盟广告'}
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
	echarts.init(document.getElementById('cpcgezb_div')).setOption(option);
	
	
	var option = {
	    title: {
	        text: ''
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['核心产品','一般产品','一次性产品']
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
	            name:'核心产品',
	            type:'line',
	            stack: '总量',
	            data:[21.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        },
	        {
	            name:'一般产品',
	            type:'line',
	            stack: '总量',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 26.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        },
	        {
	            name:'一次性产品',
	            type:'line',
	            stack: '总量',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 15.6, 12.2, 32.6, 20.0, 6.4, 3.3]
	        }
	    ]
	};
	echarts.init(document.getElementById('cpjbyjye_div')).setOption(option);

</script>
<%@ include file="../../common/footer.jsp"%>