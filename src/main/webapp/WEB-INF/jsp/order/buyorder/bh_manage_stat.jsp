<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/common.jsp"%>
<div class="fixtablelastline" >
	预计<span class="warning-color1">30</span>天备货量<span class="warning-color1">${maybeSaleNum }</span>件，预计资金占用总额<span class="warning-color1">${maybeOccupyAmount }</span>，已选择<span class="warning-color1" id="selectNum">0</span>件，预计资金占用总额<span class="warning-color1" id="selectAmount">0.00</span>
</div>
<%@ include file="../../common/footer.jsp"%>