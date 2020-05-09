<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认审核" scope="application" />
<%@ include file="../../common/common.jsp"%>
 <div class="formpublic">
            <form method="post" action="">
                <ul>
                   <li>
					<div class="infor_name">
						<span>*</span>
						<lable for='name'>审核不通过原因</lable>
					</div>
					<div class="f_left">
						<input type="text" name="orgName" id="orgName" class="input-larger" value="" />
					</div>
				</li>
                </ul>
                <div class="add-tijiao tcenter">
                    <button type="submit">提交</button>
                    <button class="dele" type="button" id="close-layer">取消</button>
                </div>
           </form>
</div>

<%@ include file="../../common/footer.jsp"%>