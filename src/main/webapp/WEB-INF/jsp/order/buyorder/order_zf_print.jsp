<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单打印" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link href='<%=basePath%>static/css/order_print/system.css?rnd=<%=Math.random()%>'
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href='<%=basePath%>static/css/order_print/ui.theme.css?rnd=<%=Math.random()%>'
	type="text/css" media="all">
<link type="text/css" rel="stylesheet"
	href='<%=basePath%>static/css/order_print/layer.css?rnd=<%=Math.random()%>'
	id="skinlayercss">
<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
	<script type="text/javascript"
	src='<%=basePath%>static/js/order/saleorder/order_print.js?rnd=<%=Math.random()%>'></script>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/general.css?rnd=<%=Math.random()%>">
    <link  type="text/css" rel="stylesheet" href="<%=basePath%>static/css/manage.css?rnd=<%=Math.random()%>" />
<body>
   <div id="print-contract">
        <div class="">
             <div class="contract-head">
                <c:if test="${ buyorderVo.orgId == 36}">
				    <img src="<%=basePath%>static/images/logo2.jpg" />
				  </c:if>
				  <c:if test="${buyorderVo.orgId != 36 }">
				    <img src="<%=basePath%>static/images/logo1.jpg" class="" />
				  </c:if>
                <div class="contract-number">
                  <div>合同号码: ${buyorderVo.buyorderNo }</div>
                  <div>制单日期: 
                  <c:if test="${buyorderVo.validTime ==0 }">
                  ${currTime}
                  </c:if>
                  <c:if test="${buyorderVo.validTime !=0}">
                  <date:date value ="${buyorderVo.validTime }" format="yyyy-MM-dd"/>
                  </c:if>
                  </div>
                </div>
            </div>
            <div class="contract-head-title">采购合同</div>
            <div class="contract-print-table">
                <table>
                  <tbody>
                    <tr class="jiayi">
                      <td><span>买方：</span><span>南京贝登医疗股份有限公司</span></td>
                      <td><span>卖方：</span><span>${ buyorderVo.traderName}</span></td>
                    </tr>
                    <tr>
                      <td>
                        <div>                    
                                                             采购人员：${detail.realName  }<c:if test="${detail.sex eq 0}">&nbsp;女士</c:if><c:if test="${detail.sex eq 1}">&nbsp;先生</c:if>
                        </div>
                        <div>
                          <span>联系方式：</span>
                          <span>${detail.mobile }/${detail.telephone }</span>
                        </div>
                         <div>
                          <span>开票信息：</span>
                          <span></span>
                        </div>
                         <div class="overflow-hidden">
                          <span style="width:70px;float:left;">注册地址/电话：</span>
                       
                         <span style="float:left;display:inline-block;max-width:-moz-calc(100% - 70px);max-width:-webkit-calc(100% - 70px);max-width:calc(100% - 70px);">南京市白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层/${detail.mobile }/${detail.telephone }</span>
                        </div>
                         <div>
                          <span>开户行：</span>
                          <span>中国建设银行江苏南京中山南路支行</span>
                        </div>
                         <div>
                          <span>税号：</span>
                          <span>91320100589439066H</span>
                        </div>
                        <div>
                          <span>账号：</span>
                          <span>32001881236052503686</span>
                        </div>
                      </td>
                      <td>
                        <div>
                          <span>销售人员：</span>
                          <span>${ buyorderVo.traderContactName}<c:if test="${buyorderVo.sex eq 0}">&nbsp;女士</c:if><c:if test="${buyorderVo.sex eq 1}">&nbsp;先生</c:if>
                          </span>
                        </div>
                        <div>
                          <span>联系方式：</span>
                          <span>${buyorderVo.traderContactTelephone }/${buyorderVo.traderContactMobile }</span>
                        </div>
                        <div>
                          <span>付款信息：</span>
                          <span></span>
                        </div>
                        <div>
                          <span>开户行：</span>
                          <span>${buyorderVo.bank }</span>
                        </div>
                        <div>
                          <span>账号：</span>
                          <span>${buyorderVo.bankAccount }</span>
                        </div>
                        <div>
                          <span>税号：</span>
                          <span>${buyorderVo.taxNum }</span>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <div class="contract-print-title2 pl10">发票及货物接收信息</div>
                  <table>
                    <tbody>
                      <tr>
                        <td>
                          <div>
                            <span> 收票单位：</span>
                            <span>南京贝登医疗股份有限公司</span>
                          </div>
                           <div>
                            <span>收票人：</span>
                            <span>${detail.realName }/${detail.mobile }/${detail.telephone }</span>
                          </div>
                           <div>
                          </div>
                           <div>
                            <span>地址：</span>
                            <span>南京市白下高新技术产业园永丰大道紫霞路斯坦德电子商务大厦北楼三层</span>
                          </div>
                        </td>
                        <td>
                          <div>
                            <span>收货人：</span>
                            <span>${buyorderVo.takeTraderContactName }/${buyorderVo.takeTraderContactMobile }/${buyorderVo.takeTraderContactTelephone }</span>
                          <div>
                            <span>地址：</span>
                            <span>${buyorderVo.takeTraderArea}${buyorderVo.takeTraderAddress}</span>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                </table>
                <div class="contract-tips"><span style="font-weight:bold">提醒：</span>卖方发送货物必须附上送货单，并在送货单上注明双方签订合同时的订单号；卖方开具的发票必须注明双方签订合同时的订单号；否则会影响后续合作和付款。</div>
                <div class="contract-print-title2 pl10">一、产品信息：</div>
                <table class="print-product-table">
                    <tbody>
                      <tr class="font-bold tdsmall">
                        <td style="width:4%;">序号</td>
                        <td style="width:6%;">订货号</td>
                        <td style="width:26%;">产品名称</td>
                        <td style="">品牌</td>
                        <td style="">型号</td>
                        <td style="width:4%;">数量</td>
                        <td style="width:4%;">单位</td>
                        <td style="width:10%">单价(元）</td>
                        <td style="width:10%;">金额(元）</td>
                        <td style="width:6%;">货期<br/><span style="font-size:0.75em;">(工作日)</span></td>
                        <td>备注</td>
                      </tr>
                      <c:set var="count" value="1"></c:set>
						<c:forEach var="list" items="${buyorderGoodsList}" varStatus="num">
						<c:if test="${list.isDelete eq 0}">
						<tr>
							<td class="" style="width: 4%;"> ${count}</td>
							<c:set var="count" value="${count+1}"></c:set>
							<td style="width: 6%;">${list.sku }</td>
							<td style="width: 26%; text-align:left;" >${ list.goodsName}</td>
							<td style="" >${list.brandName }</td>
							<td style="">${list.model }</td>
							<td style="width: 4%;">${list.num }</td>
							<td style="width: 4%;">${list.unitName }</td>
							<td  style="width: 10%;">${list.prices }</td>
							<td  style="width: 10%;">${list.allPrice}</td>
							<td  style="width: 5%;">${list.deliveryCycle}</td>
							<td  >${list.insideComments }</td>
					    </tr>
					    </c:if>
					</c:forEach>
					
                      <tr>
                        <td>合计</td>
                        <td colspan="8" style="overflow: hidden;">
                            <div class="total-count f_left">（大写）${chineseNumberTotalPrice }</div>
                            <div class="total-count-num f_right">${totalAmount}</div>
                        </td>
                        <td colspan="2"></td>
                      </tr>
                    </tbody>
                </table>
                <div class="contract-print-title2">二、付款方式&发票类型：</div>
                <div class="contract-details">
                       1、 付款方式：
                      <c:if test="${buyorderVo.paymentType == 419}">
						先款后货，预付100% ，预付金额${buyorderVo.prepaidAmount }元
						</c:if>
						<c:if test="${buyorderVo.paymentType == 420}">
						先货后款，预付80%，预付金额${buyorderVo.prepaidAmount }元，账期支付${buyorderVo.accountPeriodAmount }元
						</c:if>
						<c:if test="${buyorderVo.paymentType == 421}">
						先货后款，预付50%，预付金额${buyorderVo.prepaidAmount }元，账期支付${buyorderVo.accountPeriodAmount }元
						</c:if>
						<c:if test="${buyorderVo.paymentType == 422}">
						先货后款，预付30%，预付金额${buyorderVo.prepaidAmount }元，账期支付${buyorderVo.accountPeriodAmount }元
						</c:if>
						<c:if test="${buyorderVo.paymentType == 423}">
						先货后款，预付0%，预付金额${buyorderVo.prepaidAmount }元，账期支付${buyorderVo.accountPeriodAmount }元
						</c:if>
						<c:if test="${buyorderVo.paymentType == 424}">
						自定义，预付金额${buyorderVo.prepaidAmount }元，账期支付${buyorderVo.accountPeriodAmount }元，尾款${buyorderVo.retainageAmount }元
						，尾款期限${buyorderVo.retainageAmountMonth }月
						</c:if>
                      <br/>
                       2、 发票类型：
                       <c:forEach var="list" items="${invoiceTypes}">
	                    <c:if test="${buyorderVo.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
	                  </c:forEach>
                </div>
                <div class="contract-print-title2">三、产品交付说明：</div>
                <div class="contract-details">
                      1、 关于运费：<c:forEach var="list" items="${yfTypes}">
	                    <c:if test="${buyorderVo.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
	                  </c:forEach><br/>
                      2、 当收到买方对产品与订单不符，或质量不合格声明时，卖方负责更换产品。买方由此而生的所有费用，应由卖方负担。<br/>
                      3、 交货期：如因卖方原因造成货物不能在订单规定的日期完成交货，卖方需承担延迟交付的责任，卖方每工作日赔偿买方已付款金额的0.3%作为违约赔偿金，直到收到货物为止。如到合同规定的交期后2周，卖方仍不能交货，买方有权提出退货，卖方必须全额退还买方的订货预付款。
                </div>
                <div class="contract-print-title2">四、 售后、质量保证：</div>
                <div class="contract-details">
                      1、 卖方保证供应的产品符合国家标准或相关行业标准，且为全新、原装产品。<br/>
                      2、 卖方按照产品说明书内的质保条件及质保期对产品提供质保承诺。质保时间自买方收到货物时开始计算。<br/>
                      3、 卖方按照国家或行业标准提供质保、售后服务，提供维修或更换服务。<br/>
                      4、 买方在收到货物后，1）由于产品质量原因导致无法使用，且经卖方确认不具有可修复性和可更换性，买方可以要求退货。2）定制类产品出现以上质量问题，卖方需提供维修或退货服务。
                </div>
                <div class="contract-print-title2">五、保密条款：</div>
                <div class="contract-details">
                                           买方向卖方提供的与此订单有关的所有信息，包括但不限于关于产品、商业机密、图纸、文件、商标，卖方必须保密，且未经买方事先书面同意，卖方不得公开或以其他方式向第三方透露。<br/>
                </div>
                <div class="contract-print-title2"> 六、合同撤销：</div>
                <div class="contract-details">
                                           合同生效后，卖方不得单方面撤销合同，若单方面撤销合同，需赔偿此合同总额的20%作为赔偿金。
                </div>
                <div class="contract-print-title2">七、合同争议处理:</div>
                <div class="contract-details">
                                          对于合同争议，双方需本着友好精神协商解决，协商不成，可将争议提请买方所在地人民法院诉讼。
                </div>
                <div class="contract-print-title2"> 八、合同效力：</div>
                <div class="contract-details">
                                         本合同双方盖章或签字生效，传真件、扫描件与原件具有同等法律效力。
                </div>
                <div class="contract-print-title2"> 九、 保密条款：</div>
                <div class="contract-details">
                                        保密条款（此次货物由卖方直接发送给买方客户）<br>
					1. 卖方以买方名义发货，即：发货单的发件方为 买方公司名称 ，联系人为 买方销售负责人 。<br>
					2. 卖方发货时，不能夹带卖方任何联系方式，如名片、便签等。<br>
					3. 卖方发货时，需要附上送货单，送货单上的送货单位显示我公司名称，不能泄露卖方与买方约定的任何信息（尤其是价格）。<br>
					4. 卖方发货时，不能夹带卖方开具给买方的发票。<br>
					5. 买方的客户，买方有权自主维护。若买方客户有意越过买方与卖方直接合作，卖方应拒绝为买方客户报价，并将情况及时告知买方。<br>
					6. 保密条款中，卖方若有任何一条违约情况发生，最低罚金按照总货款的200％赔付给买方。<br>
                </div>
                 <div class="contract-print-title2">十、补充条款：</div>
                 <c:if test="${!empty buyorderVo.additionalClause}">
                <div class="contract-details">${buyorderVo.additionalClause }</div>
                </c:if>
                <c:if test="${empty buyorderVo.additionalClause}">
                <div class="contract-details">无。</div>
                </c:if>
                <div class="contract-print-sign">
                  <ul>
                      <li style="margin-left: 50px;padding:32px 45px 0px 45px;">
                      <c:if test="${buyorderVo.validStatus ==1}">
                        <img src="<%=basePath%>static/images/order_sign_b.png" style="position:absolute; top: 0;left: 0; z-index: 123;">
                       </c:if>
                        <div class="sign-name">买方：南京贝登医疗股份有限公司</div>
                        <div class="sign-place">
                            <ul>
                              <li>
                                  <div style="margin-top:29px;">${ detail.realName }</div>
                                  <div>授权人签字</div>
                              </li>
                              <li>
                                  <div style="margin-top:29px;">
                                  <c:if test="${buyorderVo.validTime ==0 }">
				                  ${currTime}
				                  </c:if>
				                  <c:if test="${buyorderVo.validTime !=0}">
				                  <date:date value ="${buyorderVo.validTime }" format="yyyy-MM-dd"/>
				                  </c:if>
                                  </div>
                                  <div>日期</div>
                              </li>
                            </ul>
                        </div>
                      </li>
                       <li style="float: right; margin-right: 50px;overflow:visible;position: relative; padding:32px 0 28px 0;">
                        <div class="sign-name">卖方：${ buyorderVo.traderName}</div>
                        <div class="sign-place">
                            <ul>
                              <li>
                                  <div></div>
                                  <div>授权人签字</div>
                              </li>
                              <li>
                                  <div></div>
                                  <div>日期</div>
                              </li>
                            </ul>
                        </div>
                      </li>
                      <div class='clear'></div>
                  </ul>
                </div>
                <div style="page-break-before: always;">
                  <table cellpadding="0" cellspacing="0" width="100%" border="0"
					height="90">
					<tbody>
						<tr>
							<td colspan="2" class="align_c"
								style="padding-top: 15px; padding-bottom: 15px;text-align:center"><b
								style="font-size: 14px;">委托直接发货说明</b></td>
						</tr>
						<tr>
							<td colspan="2">致 <b>${ buyorderVo.traderName}</b> <br> 兹委托贵司 <b>${ buyorderVo.buyorderNo}</b>
								货物直接发至我司客户处（发货信息请见合同）。非常感谢贵司对于
								<c:if test="${ buyorderVo.companyId ==1}">
								贝登工作的支持，贝登坚信，
								</c:if>
								
								<div>通过这样的合作，未来我们将更加深入地展开合作，更加彼此信任。</div>
								<div> 特将发货注意事项，进行如下说明：</div>
								<div> 1、快递单书写内容：</div>
								<c:if test="${ buyorderVo.companyId ==1}">
								<div style='margin-left:15px;'>发货公司名字---南京贝登医疗股份有限公司 </div>
								<div style='margin-left:15px;'> 寄件人姓名------贝登物流部</div>
								<div style='margin-left:15px;'>
									联系电话---------15951606728
								</div>
								</c:if>
							
								 2、请<b>勿将</b>任何有贵司信息的物品跟随货物一起发出，如：<b>印有贵司名字的包装、胶带，贵司人员的名片；产品图册以及贵司的送货单</b>。<br>
								3、请将产品仔细包装，以保证产品在抵达目的地时包装完好。<br> <br> 再次感谢贵司对
								<c:if test="${ buyorderVo.companyId ==1}">
								贝登
								</c:if>
								
								工作的支持！<br>
							<br>
							<br>
							</td>
						</tr>
					</tbody>
				</table>
                
                </div>
            </div>
        </div>
         <div class='tcenter mb15'>
		<span class=" bt-small bt-bg-style bg-light-blue"  id="btnPrint">打印</span>
    	</div> 
   </div>
   <script>
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