<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加资金流水" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
$(function(){
	$("#myform").submit(function(){
		var comments = $("#comments").val();
		if(comments!="" && comments.length>512){
			warnTips("comments","交易备注长度应该在0-512个字符之间");
			return false;
		}
		waitWindowNew('no');
		$.ajax({
			url:page_url+'/finance/buyorder/payApplyPass.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			success:function(data)
			{
				if (data.code == -1) {
					layer.alert(data.message);
				}else if(data.code == 1){
                    layer.alert(data.message);
                }
				else {
					refreshPageList(data);
				}
			}
		});
		return false;
	})
})
</script>
<div class="form-list">
    <form method="post" id="myform" action="">
        <ul>
        	<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>业务类型</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                        	<c:forEach var="list" items="${bussinessTypeList}">
	                    	<li>
                                <c:if test="${list.sysOptionDefinitionId == 525}">
                                <label>${list.title}</label>
                                </c:if>
                            </li>
		                    </c:forEach>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易方式</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                            <c:forEach var="list" items="${traderModeList}">
	                    	<li>
                                <c:if test="${list.sysOptionDefinitionId == payApplyInfo.traderMode}">
                                <label>${list.title}</label>
                                </c:if>
                            </li>
		                    </c:forEach>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易主体</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                            <li>
                            	<c:if test="${payApplyInfo.traderSubject == 1}">
                                <label>对公</label>
                                </c:if>
                                <c:if test="${payApplyInfo.traderSubject == 2}">
                                <label>对私</label>
                                </c:if>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                	<span>*</span>
                    <lable>交易金额</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <fmt:formatNumber type="number" value="${payApplyInfo.amount}" pattern="0.00" maxFractionDigits="2" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                	<span>*</span>
                    <lable>交易名称</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	${payApplyInfo.traderName}
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>交易备注</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="comments" id="comments" value="" />
                    </div>
                    <div><span class="font-grey9 mt5">友情提示<br>1、该操作仅作记录使用，实际操作请通过支付平台进行；</span></div>
                </div>
            </li>
            <!-- 如果付款方式是银行时 -->
            <c:if test="${payApplyInfo.getTraderMode() eq 521}">
              <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>银企直联</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <ul>
                            <c:forEach var="stn" items="${payTypeName}">
                            <li>
                                 <input type="radio" name="paymentType" value="${stn.sysOptionDefinitionId}" ${stn.sysOptionDefinitionId == 642?"checked":""}>
                                 <label>${stn.title}</label>
                            </li>
							</c:forEach>
                            </ul>
                        </div>
                    </div>
                </li>
              </c:if>
        </ul>
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="payApplyId" value="${id}">
        	<input type="hidden" name="taskId" value="${taskId}">
        	<input type="hidden" name="formToken" value="${formToken}"/>
            <button type="submit">提交</button>
            <button class="dele" type="button" id="close-layer">取消</button>
        </div>
    </form>
</div>
<%@ include file="../../common/footer.jsp"%>