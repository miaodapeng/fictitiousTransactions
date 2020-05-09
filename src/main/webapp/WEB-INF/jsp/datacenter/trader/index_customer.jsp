<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="客户数据统计" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
	<div class="main-container">
        <div class="showing-card showing-card2">
            <ul>
                <li>
                    <div class="card-container">
                        <div class="card-title blue-title">
                            客户数
                        </div>
                        <div class="card-content blue-content2">
                            ${totalCustomerNum}
                        </div>
                    </div>
                </li>
                <li>
                    <div class="card-container month-data">
                        <div class="card-title yellow-title">
                            成交客户数
                        </div>
                        <div class="card-content yellow-content2">
                            ${totalTraderCustomerNum}
                        </div>
                    </div>
                </li>
                <li>
                    <div class="card-container personal-data">
                        <div class="card-title green-title">
                            多次成交客户数
                        </div>
                        <div class="card-content green-content2">
                            ${totalManyTraderCustomerNum}
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="charts-multi charts-three">
            <ul>
                <li>
                    <div class="charts">
                        <div class="charts-title">客户性质占比</div>
                        <div class="charts-container" id="customer_nature_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">客户等级占比</div>
                        <div class="charts-container" id="customer_level_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">成交客户占比</div>
                        <div class="charts-container" id="trader_customer_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">客户分部门占比</div>
                        <div class="charts-container" id="org_customer_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">成交客户分部门占比</div>
                        <div class="charts-container" id="org_trader_customer_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
                <li>
                    <div class="charts">
                        <div class="charts-title">多次成交客户分部门占比</div>
                        <div class="charts-container" id="org_many_trader_customer_div" style="width:100%;height:300px;">
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="charts">
            <div class="charts-title">客户新增与流失</div>
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
            <div class="charts-title">数据导出</div>
            <div class="charts-container charts-container1">
                    <div class="form-list chart-form-list">
                            <ul>
                                <li>
                                    <div class="f_left ">
                                        <div class="form-blanks">
                                            <input class="Wdate f_left " type="text" placeholder="请选择月份" onClick="WdatePicker()">
                                            <input class="Wdate f_left wid18" type="text" placeholder="请选择月份" onClick="WdatePicker()">
                                            <span class="bt-small bt-bg-style bg-light-blue">导出客户数据统计表</span>
                                            <span class="bt-small bt-bg-style bg-light-blue">导出客户新增与流失统计表</span>
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
    </div>
<script type="text/javascript">
	var customerNatureLegendList = ${customerNatureLegendList};
	var customerNatureData = ${customerNatureData};
    var option = {
   	    title : {
   	        text: ''
   	    },
   	    tooltip : {
   	        trigger: 'item',
   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
   	    },
   	    legend: {
   	        orient: 'vertical',
   	        left: 'right',
   	        data: customerNatureLegendList
   	    },
   	    series : [
   	        {
   	            name: '客户性质占比',
   	            type: 'pie',
   	            radius : '55%',
   	            center: ['50%', '60%'],
   	            data:customerNatureData,
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
    echarts.init(document.getElementById('customer_nature_div')).setOption(option);
 
 	var customerLevelLegendList = ${customerLevelLegendList};
 	var customerLevelData = ${customerLevelData};
    var option = {
     	    title : {
     	        text: ''
     	    },
     	    tooltip : {
     	        trigger: 'item',
     	        formatter: "{a} <br/>{b} : {c} ({d}%)"
     	    },
     	    legend: {
     	        orient: 'vertical',
     	        left: 'right',
     	        data: customerLevelLegendList
     	    },
     	    series : [
     	        {
     	            name: '客户等级占比',
     	            type: 'pie',
     	            radius : '55%',
     	            center: ['50%', '60%'],
     	            data:customerLevelData,
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
      echarts.init(document.getElementById('customer_level_div')).setOption(option);
   
   	  var cjkhLegendList = ${cjkhLegendList};
      var echartsDataCjkhList = ${echartsDataCjkhList};
      var option = {
       	    title : {
       	        text: ''
       	    },
       	    tooltip : {
       	        trigger: 'item',
       	        formatter: "{a} <br/>{b} : {c} ({d}%)"
       	    },
       	    legend: {
       	        orient: 'vertical',
       	        left: 'right',
       	        data: cjkhLegendList
       	    },
       	    series : [
       	        {
       	            name: '成交客户占比',
       	            type: 'pie',
       	            radius : '55%',
       	            center: ['50%', '60%'],
       	            data:echartsDataCjkhList,
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
        echarts.init(document.getElementById('trader_customer_div')).setOption(option);
     
        var orgCustomerLegendList = ${orgCustomerLegendList};
        var orgCustomerFromObject = ${orgCustomerFromObject};
        var option = {
         	    title : {
         	        text: ''
         	    },
         	    tooltip : {
         	        trigger: 'item',
         	        formatter: "{a} <br/>{b} : {c} ({d}%)"
         	    },
         	    legend: {
         	        orient: 'vertical',
         	        left: 'right',
         	        data: orgCustomerLegendList
         	    },
         	    series : [
         	        {
         	            name: '客户分部门占比',
         	            type: 'pie',
         	            radius : '55%',
         	            center: ['50%', '60%'],
         	            data:orgCustomerFromObject,
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
          echarts.init(document.getElementById('org_customer_div')).setOption(option);
       
          var orgTraderCustomerLegendList = ${orgTraderCustomerLegendList};
          var orgTraderCustomerFromObject = ${orgTraderCustomerFromObject};
          var option = {
           	    title : {
           	        text: ''
           	    },
           	    tooltip : {
           	        trigger: 'item',
           	        formatter: "{a} <br/>{b} : {c} ({d}%)"
           	    },
           	    legend: {
           	        orient: 'vertical',
           	        left: 'right',
           	        data: orgTraderCustomerLegendList
           	    },
           	    series : [
           	        {
           	            name: '成交客户分部门占比',
           	            type: 'pie',
           	            radius : '55%',
           	            center: ['50%', '60%'],
           	            data:orgTraderCustomerFromObject,
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
            echarts.init(document.getElementById('org_trader_customer_div')).setOption(option);
         
            var orgManyTraderCustomerLegendList = ${orgManyTraderCustomerLegendList};
            var orgManyTraderCustomerFromObject = ${orgManyTraderCustomerFromObject};
            var option = {
             	    title : {
             	        text: ''
             	    },
             	    tooltip : {
             	        trigger: 'item',
             	        formatter: "{a} <br/>{b} : {c} ({d}%)"
             	    },
             	    legend: {
             	        orient: 'vertical',
             	        left: 'right',
             	        data: orgManyTraderCustomerLegendList
             	    },
             	    series : [
             	        {
             	            name: '多次成交客户分部门占比',
             	            type: 'pie',
             	            radius : '55%',
             	            center: ['50%', '60%'],
             	            data: orgManyTraderCustomerFromObject,
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
              echarts.init(document.getElementById('org_many_trader_customer_div')).setOption(option);
</script>
<%@ include file="../../common/footer.jsp"%>