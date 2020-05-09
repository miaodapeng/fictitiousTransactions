<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="公司运营指标" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/echarts/echarts.min.js?rnd=<%=Math.random()%>"></script>
	<div class="main-container">
        <div class="charts">
            <div class="charts-title">月度到款额</div>
            <div class="charts-container">
            </div>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>