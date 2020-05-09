<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增费用类型" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" >
	$(function(){
		$("#add_submit").click(function(){
			checkLogin();
			var type = $("#type").val();
			var amount = $("#amount").val();
			var comments = $("#comments").val();
			if(type == -1){
				warnErrorTips("type","typeError","请选择费用类型");
				return false;
			}
			if(amount == ''){
				warnErrorTips("amount","amountError","请输入费用");
				return false;
			}
			var reg = /^(\-?)(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
			if(!reg.test(amount)){
				warnErrorTips("amount","amountError","只允许输入数字，小数点后只允许保留两位");
				return false;
			}
			if(comments.length > 256){
				warnErrorTips("comments","commentsError","备注内容不允许超过256个字符");//文本框ID和提示用语
				return false;
			}
			$.ajax({
				url:page_url+'/aftersales/order/updateAfterSalesCost.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						window.parent.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
		})
	})

</script>

	<div class="form-list  form-tips5">
		<form action="" method="post" id="myform" enctype="multipart/form-data">
			<ul>
				<li>
					<div class="infor_name">
                        <span>*</span>
                        <lable>费用类型</lable>
                    </div>
                    <div class="f_left ">
                    	<div class="form-blanks ">
                    		<select name="type" id="type">
                                <option value="-1"  >-请选择-</option>
                                <c:forEach var="list" items="${costTypes}" >
                                	<c:choose>
                                		<c:when test="${list.sysOptionDefinitionId eq costVo.type}">
                                			<option value="${list.sysOptionDefinitionId }" selected="selected">${list.comments }</option>
                                		</c:when>
                                		<c:otherwise>
                                			<option value="${list.sysOptionDefinitionId }" >${list.comments }</option>
                                		</c:otherwise>
                                	</c:choose>
                                </c:forEach>
                            </select>
                    	</div>
                    	<div id="typeError"></div>
                    </div>
				</li>
				
				<li>
					<div class="infor_name">
                        <span>*</span>
                        <lable>费用</lable>
                    </div>
                    <div class="f_left  ">
                        <input type="text" class="input-largest" name="amount" id="amount" value="${costVo.amount }">
                        <div id="amountError"></div>
                    </div>
				</li>
				
				<li>
					<div class="infor_name">
                        <lable>备注</lable>
                    </div>
					
					<div class="f_left ">
                        <div class="form-blanks ">
                         	<textarea name="comments" id="comments" placeholder="" rows="5" class="wid60">${costVo.comments }</textarea>
	                     </div>
	                     <div id="commentsError"></div>
                    </div>
				</li>
			</ul>
			
			<div class="add-tijiao tcenter">
				<input type="hidden" name="afterSalesId" value="${afterSalesRecord.afterSalesId }">
				<input type="hidden" name="afterSalesCostId" value="${costVo.afterSalesCostId }">
				<button type="submit" id="add_submit">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
	<%@ include file="../../common/footer.jsp"%>