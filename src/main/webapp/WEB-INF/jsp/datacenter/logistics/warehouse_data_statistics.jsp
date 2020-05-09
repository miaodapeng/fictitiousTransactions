<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="仓存数据统计 " scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
	<div class="main-container">
        <div class="charts">
            <div class="charts-title">库存周转率</div>
            <div class="charts-container" id="kczzl_div" style="width:100%;height:400px;">
                 
            </div>
        </div>
         <div class="charts">
            <div class="charts-title">出入库统计</div>
            <div class="charts-container" id="crktj_div" style="width:100%;height:400px;">
             </div>    
        </div>
    </div>
<script type="text/javascript">
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
	            name:'库存周转率',
	            type:'line',
	            stack: '总量',
	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
	        }
	    ]
	};
	echarts.init(document.getElementById('kczzl_div')).setOption(option);
	
	
	var option = {
	    title: {
	        text: '',
	        subtext: ''
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	        data: ['入库', '出库']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    yAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01]
	    },
	    xAxis: {
	        type: 'category',
	        data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
	    },
	    series: [
	        {
	            name: '入库',
	            type: 'bar',
	            data: [18203, 23489, 29034, 104970, 131744, 630230,18203, 23489, 29034, 104970, 131744, 630230]
	        },
	        {
	            name: '出库',
	            type: 'bar',
	            data: [19325, 23438, 31000, 121594, 134141, 681807,18203, 23489, 29034, 104970, 131744, 630230]
	        }
	    ]
	};
	echarts.init(document.getElementById('crktj_div')).setOption(option);
</script>
<%@ include file="../../common/footer.jsp"%>