<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品总监" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/china.js?rnd=<%=Math.random()%>"></script>

	<div class="main-container">
		<div class="data-doc-exchange mb10">
            <ul class="f_left">
                <li class="active">产品总监</li>
            </ul>
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
      
        
    </div>
<script type="text/javascript">
	
    
</script>
<%@ include file="../../common/footer.jsp"%>