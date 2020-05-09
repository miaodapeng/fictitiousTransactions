<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="打印报价单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<head>
	<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/general.css?rnd=<%=Math.random()%>">
    <link  type="text/css" rel="stylesheet" href="<%=basePath%>static/css/manage.css?rnd=<%=Math.random()%>" />

</head>
<body>
  <div id="print-contract">
       <div class="">
              <div class="print-quote-list">
                  <div class="print-quote-logo">
                  <c:if test="${ quoteVo.customerType == 426}">
				    <img src="<%=basePath%>static/images/logo2.jpg" />
				  </c:if>
				  <c:if test="${ quoteVo.customerType == 427 or  quoteVo.customerType ==0}">
				    <img src="<%=basePath%>static/images/logo1.jpg" class="" />
				  </c:if>
                  </div>
                  <div class="print-quote-title">
                      <div class="quote-list-title">报价单</div>
                      <div class="quote-list-number">
                          <div><span>报价单号：</span><span>${quoteVo.quoteorderNo }</span></div>
                          <div><span>报价日期：</span><span>${currTime }</span></div>
                      </div>
                  </div>
              </div>
              <div class="print-quote-part1">
                      <ul class="f_left">
                          <li><span>甲方：</span><span>${quoteVo.traderName }</span></li>
                          <li><span>接收人：</span><span>${quoteVo.name }
                          <c:if test="${quoteVo.sex eq 0}">
						      女士
						  </c:if>
						  <c:if test="${quoteVo.sex eq 1}">
						     先生
						  </c:if></span></li>
                          <li><span>电话：</span><span>${quoteVo.telephone }</span></li>
                          <li><span>手机：</span><span>${quoteVo.mobile }</span></li>
                          <li><span>邮箱：</span><span>${quoteVo.email }</span></li>
                      </ul>
                       <ul class=" f_right">
                          <li><span>乙方：</span><span>南京贝登医疗股份有限公司</span></li>
                          <li><span>发送人：</span><span>${detailUser.realName }
                          <c:if test="${detailUser.sex eq 0}">
						      女士
						  </c:if>
						  <c:if test="${detailUser.sex eq 1}">
						     先生
						  </c:if></span></li>
                          <li><span>电话：</span><span>${detailUser.telephone }</span></li>
                          <li><span>手机：</span><span>${detailUser.mobile }</span></li>
                          <li><span>邮箱：</span><span>${detailUser.email }</span></li>
                      </ul>
               </div>
              <div class="print-quote-part2" style="">
                        尊敬的 ${quoteVo.name }<c:if test="${quoteVo.sex eq 0}">
			女士
			</c:if>
			<c:if test="${quoteVo.sex eq 1}">
			先生
			</c:if> ：<br/>
                        非常感谢您选择贝登 ，作为领先的<c:if test="${ quoteVo.customerType == 426}">科研仪器</c:if><c:if test="${ quoteVo.customerType == 427 or quoteVo.customerType ==0}">医疗器械</c:if>一站式采购服务商，我们坚持以“客户第一”的经营理念，为您提供专业的选购服务。
                        相信这次报价是我们进一步合作的开始。
              </div>
              <div class="print-quote-part3 contract-print-table">
                    <table class="print-product-table" style="margin-bottom: 0;">
                        <tbody>
                           <tr class="font-bold tdsmall">
                            <td style="width:4%;">序号</td>
                            <td style="width:94px">图片</td>
                            <td style="width:33%;">产品信息</td>
                            <td style="width:9%;">数量/单位</td>
                            <td style="width:10%;">单价(元)</td>
                            <td style="width:11%;">合计(元)</td>
                        <td style="width:50px;">货期<br><span style="font-size: 0.75em;font-weight: 500;">（工作日）</span></td>

                            <td>备注</td>
                          </tr>
                           <c:set var="count" value="1"></c:set>
                           <c:forEach var="list" items="${quoteGoodsList}" varStatus="num"> 
				              <c:if test="${list.isDelete eq 0 }">
                           <tr class="border-dotted">
                              <td  align="center">${count}</td>
				            	<c:set var="count" value="${count+1}"></c:set>
                              <td align="center" nowrap="">
                              <c:if test="${empty list.domain or empty list.uri}">
                              <img style="width:90px;height: 70px;"  src="<%=basePath%>static/images/nopic.jpg">
                              </c:if>
                              <c:if test="${not empty list.domain and not empty list.uri}">
                              <img style="width:90px;height: 70px;" src="http://${list.domain}${list.uri}" onerror="javascript:this.src='<%=basePath%>static/images/nopic.jpg'" >
                              </c:if>
                              </td>
                     <td style="text-align: left;">
                       <b>产品名称：</b>${list.goodsName}<br/>
                       <b>订货号：</b>${list.sku}<br/>
                       <b>品牌：</b>${list.brandName}<br/>
                       <b>型号：</b>${list.model}
                     </td>
					<td align="center" nowrap="">${list.num }/${list.unitName }</td>
					<td align="center" nowrap="">${list.price }</td>
					<td align="center" nowrap="">${list.price*list.num }</td>
					<td class="center" nowrap="">${list.deliveryCycle }</td>
				    <td class="center" >${list.goodsComments }</td>
                             
                          </tr> 
                          </c:if>
				</c:forEach>
                        </tbody>
                   </table>
                    <table class="print-product-table print-product-table1" style="border-top: none;">
                        <tbody>
                          <tr class="font-bold">
                            <td style="width:calc(4% + 94px);width:-webkit-calc(4% + 94px);width:-moz-calc(4% + 94px);">价格有效期</td>
                            <td style="width:21%;">付款方式</td>
                            <td style="width:21%;">发票类型</td>
                            <td style="width:21%;">产品配送费</td>
                            <td >金额（元）</td>
                          </tr>
                         <tr>
									<td>${ quoteVo.period}天</td>
									<td>
									<c:if test="${quoteVo.paymentType == 419}">
									先款后货，预付100%
									</c:if>
									<c:if test="${quoteVo.paymentType == 420}">
									先货后款，预付80%
									</c:if>
									<c:if test="${quoteVo.paymentType == 421}">
									先货后款，预付50%
									</c:if>
									<c:if test="${quoteVo.paymentType == 422}">
									先货后款，预付30%
									</c:if>
									<c:if test="${quoteVo.paymentType == 423}">
									先货后款，预付0%
									</c:if>
									<c:if test="${quoteVo.paymentType == 424}">
									自定义
									</c:if>
									</td>
									<td>
									<c:forEach var="list" items="${invoiceTypes}">
			                    	<c:if test="${quoteVo.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
			                        </c:forEach>
									</td>
									<td>
									<c:forEach var="list" items="${yfTypes}">
					                    <c:if test="${quoteVo.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
					                  </c:forEach>
									</td>
									<td>${totalAmount }</td>
								</tr>
                        </tbody>
                   </table>
              </div>
              <div class="print-quote-part4">
                  一、包装方式：纸箱（特殊产品除外）<br/>
                  二、质保期限：按照产品的质保条件及质保期对产品提供质保承诺。<br/>
                  三、退换货：<br/>
                      <span style="padding-left: 24px;width:760px;"">
                          1.甲方单方面原因导致的选型、购买数量等错误，造成的退换货要求，将不被接受。<br/>
                     2.关于换货：合同货品属于定制类产品、易耗品、一次性卫生用品、产品技术性能决定无法更换的产品不属于可更换范围内。<br/>
                     3.关于退货：如果甲方所收到的货品10天之内：1）非定制类商品如由于质量原因导致无法使用，且无法通过技术维修恢复正常使用功能，甲方有权要求乙方退货；2）定制类商品如出现质量原因，乙方提供后续维修，但不提供退货服务。<br/>
                 
                      </span>
                  <div style="overflow:hidden;">
                       <span style="float: left;width: 24px;">四、</span><span  style="float: left; width:730px;">
                          由于贝登库存变化较大，为了使您如期收到产品，请在您订购前再次向为您服务的产品工程师确认货期。如有其他任何问题或任何我可协助的事宜 , 敬请随时与贝登客户服务中心联系,谢谢!服务热线4006-999-569
                  若需了解更多产品信息，请登入贝登网站 http://www.vedeng.com<br/>
                     </span>
                  </div>
                
                    <c:if test="${!empty quoteVo.additionalClause}">
							<div style="overflow:hidden;">
							 <span style="float: left;width: 24px;">
									五、</span><span  style="float: left; width:730px;">补充条款：${quoteVo.additionalClause }</span>
							</div>
                  </c:if>
                  备注：贵司同意上述价格和条款，请及时与为您服务的产品工程师联系，签订正式的合同。
              </div>
                <div class="print-quote-part5" style="page-break-inside: avoid;">
                <ul class="">
                  <li class="f_left">
                     <div class="parter-a">甲方：${quoteVo.traderName }</div>
                     <div class="print-quote-sign">
                         <div style="margin-top:60px;">授权人签字盖章</div>
                         <div style="margin-top:60px;">日期</div>
                     </div>
                  </li>
                  <li class="f_right ">
                     <div class="pos_rel" style="padding-bottom:10px;">
                     	 <div class="parter-a">乙方：南京贝登医疗股份有限公司</div>
                       	<img src="<%=basePath%>static/images/bj_pic.png" style="position: absolute;z-index: 13;top: 0;left: 0;">
                     
                     <div class="print-quote-sign">
                    
                         <div class="hetongdayinzhang ">
                           
                          <div style="margin-top:34px;border:none;">${detailUser.realName }</div>      
                          <div style="margin-top:0px;">授权人签字盖章</div>
                         </div>
                         <div style="margin-top:40px;border:none;padding-left:34px;">${currTime }</div>
                         <div style="margin-top:0;">日期</div>
                     </div>
                     </div>
                  </li>
                </ul>
               </div>
              <div class="print-quote-part1 print-quote-part6" style="page-break-inside: avoid;">
                      <ul class="f_left">
                          <li><span><b>账户名称：</b></span><span style="font-weight: normal;">${quoteVo.traderName } </span></li>
                          <li><span><b>注册地址电话：</b> </span><span>${quoteVo.regaddress }/${quoteVo.regtel }</span></li>
                          <li><span><b>开户行及账号：</b></span><span>${quoteVo.bank }/${quoteVo.bankAccount }</span></li>
                          <li><span><b>税号： </b></span><span>${quoteVo.taxNum }</span></li>
                      </ul>
                       <ul class=" f_right">
                          <li><span><b>账户名称：</b></span><span style="font-weight: normal;">南京贝登医疗股份有限公司 </span></li>
                          <li><span><b>开户行：</b></span><span> 中国建设银行江苏南京中山南路支行 </span></li>
                          <li><span><b>帐号：</b></span><span> 32001881236052503686  </span></li>
                          <li><span><b>税号：</b></span><span> 91320100589439066H </span></li>
                      </ul>
               </div>
       </div>
        <div class='tcenter mb15'>
		<span class=" bt-small bt-bg-style bg-light-blue"  id="btnPrint">打印</span>
    	</div> 
   </div>
   <script type="text/javascript">
       $("#btnPrint").click(function() {
           $("#btnPrint").hide();
           if (window.ActiveXObject || "ActiveXObject" in window){
               $("#print-contract").printArea({
                   mode:'popup'
               });
           }else{
               $("#print-contract").printArea();
           }
           $("#btnPrint").show();
       });
   </script>
<%@ include file="../../common/footer.jsp"%>