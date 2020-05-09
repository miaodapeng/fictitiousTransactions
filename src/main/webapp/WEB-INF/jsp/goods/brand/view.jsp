<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="品牌详情" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="parts">
		<table class="table table-bordered table-striped table-condensed table-centered">
            <tbody>
                <tr>
                    <td class="wid15">商品品牌</td>
                    <td class="">
                    	<c:if test="${brand.brandNature eq 1}">国产品牌</c:if>
                    	<c:if test="${brand.brandNature eq 2}">进口品牌</c:if>
                    </td>
                </tr>
                <tr>
                    <td class="wid15">品牌名称</td>
                    <td class="">${brand.brandName}</td>
                </tr>
                
                <tr>
                    <td class="wid15">品牌LOGO</td>
                    <td ><a href="http://${brand.logoDomain}${brand.logoUri}" target="_blank">${brand.logoUriName}</a></td>
                </tr>
                <tr>
                    <td class="wid15">品牌网址</td>
                    <td >
                    ${brand.brandWebsite}
                    </td>
                </tr>
                <tr>
                    <td class="wid15">品牌故事</td>
                    <td >${brand.description }
	                     
                   </td>
                </tr>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>