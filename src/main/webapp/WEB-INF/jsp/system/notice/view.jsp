<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="查看帮助" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
      <div class="main-container">
       	 <div class="parts">
            <table class="table">
            <thead>
            	<tr>
                        <th style="font-size:16px;font-weight:bold;">${notice.title}</th>
	             </tr> 
            </thead>
                <tbody>
                	
	                <tr>
                        <td style="text-align:left;">${notice.content}</td>
                   </tr> 
                </tbody>
            </table>
        </div>
   	</div>
</div>
<%@ include file="../../common/footer.jsp"%>