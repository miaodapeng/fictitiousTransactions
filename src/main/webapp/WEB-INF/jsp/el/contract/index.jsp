<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="合同管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
    <div class="content">
        <div class="searchfunc">
        	<form action="${pageContext.request.contextPath}/el/contract/index.do" method="post" id="search">
        		<ul>
	                <li>
	            		<label class="infor_name">合同号</label>
	            		<input type="text" class="input-middle" name="contractNumber" id="contractNumber" value="${contract.contractNumber }"/>
            		</li>

					<li>
						<label class="infor_name">意向合同</label>
						<select id="status" name="status" style="width:178px">
							<option value="">--请选择--</option>
							<option value="0" <c:if test="${contract.status == '0'}">selected</c:if>>是</option>
							<option value="1" <c:if test="${contract.status == '1'}">selected</c:if>> 否</option>
						</select>
					</li>

                    <li>
                        <label class="infor_name">审核状态</label>
                        <select id="auditStatus" name="auditStatus" style="width:178px">
                            <option value="">--请选择--</option>
                            <option value="0" <c:if test="${contract.auditStatus == '0'}">selected</c:if>>未审核</option>
                            <option value="1" <c:if test="${contract.auditStatus == '1'}">selected</c:if>>审核中</option>
                            <option value="2" <c:if test="${contract.auditStatus == '2'}">selected</c:if>>审核通过</option>
                            <option value="3" <c:if test="${contract.auditStatus == '3'}">selected</c:if>>审核不通过</option>
                        </select>
                    </li>
           		</ul>
           		<div class="tcenter">
	            	<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
	            	<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
					<span class="bt-small bg-light-blue bt-bg-style addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"./el/contract/toAddContract.do?flag=at","title":"新增合同"}'>新增合同</span>
            	</div>
            </form>
        </div>
        <div  class="normal-list-page">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="sorts">序号</th>
						<th class="wid5">合同id</th>
						<th class="wid10">客户名</th>
                        <th class="wid5">合同号</th>
                        <th class="wid10">签约时间</th>
						<th class="wid10">合同开始时间</th>
						<th class="wid10">合同结束时间</th>
                        <th class="wid10">添加时间</th>
						<th class="wid5">归属销售人</th>
                        <th class="wid5">审批状态</th>
						<th class="wid5">是否意向</th>
						<th class="wid5">是否有效合同</th>
						<th class="wid5">产品推送状态</th>
						<th class="wid5">合同推送状态</th>
						<th class="wid5">是否确认</th>
						<th class="wid15">操作</th>
                    </tr>
                </thead>
                <tbody class="company">
					<c:if test="${not empty contractList}">
						<c:forEach items="${contractList}" var="contract" varStatus="status">
							<tr>
								<td>${status.count}</td>

								<td>
									${contract.elContractId}
								</td>
								<td>
									${contract.traderName}
								</td>
								<td>
									${contract.contractNumber}
								</td>
								<td>
									<date:date value="${contract.signDate} " />
								</td>
								<td>
									<date:date value="${contract.contractValidityDateStart} " />
								</td>
								<td>
									<date:date value="${contract.contractValidityDateEnd} " />
								</td>
								<td>
									<date:date value="${contract.addTime} " />
								</td>
								<td>
									${contract.ownerName}
								</td>
                                <td>
                                    <c:if test="${contract.auditStatus == '0'}">未审核</c:if>
                                    <c:if test="${contract.auditStatus == '1'}">审核中</c:if>
                                    <c:if test="${contract.auditStatus == '2'}">审核通过</c:if>
                                    <c:if test="${contract.auditStatus == '3'}">审核不通过</c:if>
                                </td>
								<td>
									<c:if test="${contract.status == '0'}">是</c:if>
									<c:if test="${contract.status == '1'}">否</c:if>
								</td>

								<td>
									<c:if test="${contract.effctiveStatus == '0'}">无效</c:if>
									<c:if test="${contract.effctiveStatus == '1'}">有效</c:if>
								</td>

								<td>
									<c:if test="${contract.productSynStatus == '0'}">否</c:if>
									<c:if test="${contract.productSynStatus == '1'}">同步成功</c:if>
									<c:if test="${contract.productSynStatus == '2'}">同步失败</c:if>
								</td>
								<td>
									<c:if test="${contract.contractSynStatus == '0'}">否</c:if>
									<c:if test="${contract.contractSynStatus == '1'}">同步成功</c:if>
									<c:if test="${contract.contractSynStatus == '2'}">同步失败</c:if>
								</td>

								<td>
									<c:if test="${contract.confirmStatus == '0'}">未确认</c:if>
									<c:if test="${contract.confirmStatus == '1'}">已确认</c:if>
								</td>

								<td>
                                    <c:if test="${auditFlag == 'true'}">
                                        <span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"200px","title":"合同审批","link":"./toConractAudit.do?contractId=${contract.elContractId}"}'>
                                            审批
                                        </span>
                                    </c:if>

                                    <c:if test="${auditFlag == 'false'}">

                                        <c:if test="${contract.effctiveStatus == '0'}">
                                            <!--未审核或者审核不通过 -->
                                            <c:if test="${contract.auditStatus == '0' || contract.auditStatus == '3'}">
                                                <a class="addtitle" href="javascript:void(0);" tabTitle='{"link":"./el/contract/toEditContract.do?contractId=${contract.elContractId}","title":"合同编辑"}'>修改</a>
                                                <a onclick="submitContractValidator(${contract.elContractId});">提交审批</a>
                                            </c:if>
                                        </c:if>

                                        <c:if test="${contract.effctiveStatus == '1'}">
                                            <a onclick="synContractSkuToEl(${contract.elContractId});">同步产品</a>
                                            <a onclick="synContractToEl(${contract.elContractId});">同步合同</a>
                                        </c:if>

                                    </c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
                </tbody>
            </table>
        	<c:if test="${empty contractList}">
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
	        <tags:page page="${page}"/>
        </div>
    </div>
    <script type="text/javascript" >

		/**
		 * 提价合同审批
		 */
		function submitContractValidator(contractId) {
            checkLogin();
            layer.confirm("您确认要提交审批么？", {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type: "POST",
                    url: "./submitContractValidator.do?contractId="+contractId,
                    dataType:'json',
                    success: function(data){
                        if(data.code == '0'){
                            refreshPageList(data);
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
            }, function(){
            });
		}
		
		function synContractSkuToEl(contractId){
			checkLogin();
			layer.confirm("您确认要同步合同产品么？", {
				btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./synContractSkuToEl.do?contractId="+contractId,
					dataType:'json',
					success: function(data){
						if(data.code == '0'){
                            refreshPageList(data);
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
			}, function(){
			});
		}

		function synContractToEl(contractId){
			checkLogin();
			layer.confirm("您确认要同步合同么？", {
				btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: "./synContractToEl.do?contractId="+contractId,
					dataType:'json',
					success: function(data){
						if(data.code == '0'){
                            refreshPageList(data);
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
			}, function(){
			});
		}

		function updateContractStatus(contractId,type){
			checkLogin();
			var msg = "您确认要失效该合同么？";
			var url = "./invalidContract.do?contractId="+contractId;
			if(type == 1){
				msg = "您确认要生效该合同么？";
				url = "./effctiveContract.do?contractId="+contractId;
			}
			layer.confirm(msg, {
				btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					type: "POST",
					url: url,
					dataType:'json',
					success: function(data){
						refreshPageList(data);//刷新父页面列表数据
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				});
			}, function(){
			});
		}
	</script>
	<%@ include file="../../common/footer.jsp"%>