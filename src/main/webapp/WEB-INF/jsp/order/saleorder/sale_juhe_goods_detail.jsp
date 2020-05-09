<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品主页" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/view.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
(function ($) {
    $(function(){
// 销售主页
        // 多条项目点击选中一个，其他隐藏
        function chooseOne(obj,e){
            var a=obj.a,b=obj.b;
            var a_li=a.find('li'),b_li=b.find('li');
                a_li.each(function(){
                    $(this).on(e,function(){
                     var _index=$(this).index();
                    $(this).addClass('active').siblings().removeClass('active');
                     b_li.eq(_index).addClass('active').siblings().removeClass('active');
                })
            })
        }
        var vd_choose_title=$('.vd-choose-title'),vd_choose_content=$('.vd-choose-content');
        var choose_one={a:vd_choose_title,b:vd_choose_content};
        chooseOne(choose_one,'click');//第二个参数，mouseover/click --悬浮/点击
        
        //编辑问答
        var arr = [];
        $('.ask-answer-edit').click(function(){
        	checkLogin();
            $('.ask-answer').each(function(i){
                var ask_answer = $(this).find('div').eq(1).text();
                var quOrAn = $(this).find('div').eq(0).text();
                if(quOrAn == '问'){
                	$(this).find('div').eq(1).html('<input type="text" name="ques" style="width:100%;display:inline-block;margin-top:-2px;" value="'+ask_answer+'">');
                }else{
                	$(this).find('div').eq(1).html('<input type="text" name="anss" style="width:100%;display:inline-block;margin-top:-2px;" value="'+ask_answer+'">');
                }
                
            })
            $('.ask-answer-edit').hide();
            $('.ask-answer-add').hide();
            $('.ask-answer-finish').show();
            $('.ask-answer-goback').show();
        })
        var null_input =$('<input type="text" class="ask-answer-input input-null" placeholder="内容不允许为空"/>');
        $('.ask-answer-finish').click(function(){
        	checkLogin();
        	var flag = false;
        	$("input").removeClass("input-null");
        	$("input").attr("placeholder","");
              $('.ask-answer').each(function(i){
	                var ask_answer_0 = $(this).find('div').eq(1).find('input').val();
	                var ask_answer_1 = $(this).siblings('.ask-answer').find('div').eq(1).find('input').val();
	                if(ask_answer_0 != '' && ask_answer_0 != undefined && (ask_answer_1 == '' || ask_answer_1 == undefined)){
	                	flag = true;
	                	$(this).siblings('.ask-answer').find('div').eq(1).find('input').addClass("input-null").attr("placeholder","内容不允许为空");
	                	return false;
	                }
	                if(ask_answer_0 != '' && ask_answer_0 != undefined && ask_answer_0.length > 256){
	                	layer.alert("问题的字符长度不允许超过256个字符");
	                	$(this).addClass("input-null");
	                	flag = true;
	                	return false;
	                }
	                if(ask_answer_1 != '' && ask_answer_1 != undefined && ask_answer_1.length > 256){
	                	layer.alert("答案的字符长度不允许超过256个字符");
	                	$(this).addClass("input-null");
	                	flag = true;
	                	return false;
	                }
            	})  
            if(flag){
            	return false;
            }
            $.ajax({
      			url:page_url + '/order/saleorder/saveGoodsVoFaqs.do',
      			data:$('#myform').serialize(),
      			type:"POST",
      			dataType : "json",
      			async: false,
      			success:function(data)
      			{
      				 if(data.code==0){
      					$(".ask-answers").empty();
      					var div = '';
      					for(var i = 0;i < data.data.length; i++){
      						div +='<div class="ask-answers-item">';
                   			if(data.data[i].parentId == 0){
                   				div +='<div class="ask-answer">'+
	                                    '<div class="polymer-question">问</div>'+
	                                    '<div>'+data.data[i].content+'</div>'+
	                                  '</div>';
                   				for(var j = 0;j < data.data.length; j++){
                   					if(data.data[i].goodsFaqId == data.data[j].parentId){
                   						div += '<div class="ask-answer">'+
			                                        '<div class="polymer-answer">答</div>'+
			                                        '<div>'+data.data[j].content+'</div>'+
			                                     '</div>'+
			                        '</div>';
                   					}
                   				}       
                   			}    
      					}
      					$(".ask-answers").append(div);
      					if(data.data.length > 0){
      						$('.ask-answer-edit').show();
      					}
      		            $('.ask-answer-add').show();
      		            $('.ask-answer-finish').hide();
      		            $('.ask-answer-goback').hide();
      				}else{
      					layer.alert(data.message);
      				}
      			},
      			error:function(data){
      				if(data.status ==1001){
      					layer.alert("当前操作无权限");
      				}
      			}
      		}); 
            
        })
        $('.ask-answer-add').click(function(){
        	checkLogin();
            var _html = $('<div class="ask-answers-item"><div class="ask-answer"><div class="polymer-question">问</div><div><input type="text" name="que" style="width:100%;display:inline-block;margin-top:-2px;"/></div></div><div class="ask-answer"><div class="polymer-answer">答</div><div><input type="text" name="ans" style="width:100%;display:inline-block;margin-top:-2px;"/></div></div></div>');
            $('.ask-answer-edit').hide();
            $('.ask-answer-add').hide();
            $('.ask-answer-finish').show();
            $('.ask-answer-goback').show();
            $('.ask-answers').append(_html);
    	})
    	
    	$(".ask-answer-goback").click(function(){
    		checkLogin();
    		index = layer.confirm("已编辑问题未保存，您是否继续该操作？", {
  			  btn: ['确定','取消'] //按钮
  			}, function(){
  				$.ajax({
  	      			url:page_url + '/order/saleorder/getGoodsVoFaqs.do',
  	      			data:{"goodsId":$("input[name='goodsId']").val()},
  	      			type:"POST",
  	      			dataType : "json",
  	      			async: false,
  	      			success:function(data)
  	      			{
  	      				 if(data.code==0){
  	      					$(".ask-answers").empty();
  	      					var div = '';
  	      					for(var i = 0;i < data.data.length; i++){
  	      						div +='<div class="ask-answers-item">';
  	                   			if(data.data[i].parentId == 0){
  	                   				div +='<div class="ask-answer">'+
  		                                    '<div class="polymer-question">问</div>'+
  		                                    '<div>'+data.data[i].content+'</div>'+
  		                                  '</div>';
  	                   				for(var j = 0;j < data.data.length; j++){
  	                   					if(data.data[i].goodsFaqId == data.data[j].parentId){
  	                   						div += '<div class="ask-answer">'+
  				                                        '<div class="polymer-answer">答</div>'+
  				                                        '<div>'+data.data[j].content+'</div>'+
  				                                     '</div>'+
  				                        '</div>';
  	                   					}
  	                   				}       
  	                   			}    
  	      					}
  	      					$(".ask-answers").append(div);
  	      					if(data.data.length > 0){
  	      						$('.ask-answer-edit').show();
  	      					}
  	      		            $('.ask-answer-add').show();
  	      		            $('.ask-answer-finish').hide();
  	      		            $('.ask-answer-goback').hide();
  	      				}else{
  	      					layer.alert(data.message);
  	      				}
  	      			},
  	      			error:function(data){
  	      				if(data.status ==1001){
  	      					layer.alert("当前操作无权限");
  	      				}
  	      			}
  	      		});
  			layer.close(index);
  			});
    		
    	})
    	
//商品详情页商品规格悬浮
    var product_a = $('#product-fix');
    if (product_a.html() != undefined && product_a.html() != '') {
        var product_pos = product_a.offset().top+30;
        var product_pos2 = product_a.offset().top-30;
    }
    $(window).scroll(function(){
        var sroll_top = $(window).scrollTop();
        if(sroll_top > product_pos){
            product_a.addClass('product-fix');
        }else{
            product_a.removeClass('product-fix');
        }
    })
        product_a.find('li').each(function(){
            $(this).click(function(){
                $("html,body").animate({scrollTop:product_pos2});
            })
        })
   
    })
})(jQuery)


     function downloadFile(url) {   
		$.ajax({
			url:page_url + '/order/saleorder/downloadFile.do',
			data:{"url":url},
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				 if(data.code==0){
					 layer.alert(data.message);
				}else{
					layer.alert(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限");
				}
			}
		});
    } 

</script>
	<div class="main-container">
        <div class="polymer-main-page">
            <div class="polymer-part1">
                    <div class="polymer-img f_left">
	                    <c:if test="${not empty goods.uri && empty goods.domain}">
	                    	<img src="http://${domain}${goods.uri}">
	                    </c:if>
                    	<c:if test="${not empty goods.uri && not empty goods.domain}">
	                    	<img src="http://${goods.domain}${goods.uri}">
	                    </c:if>
	                    <c:if test="${ empty goods.uri}">
	                    	<img src="">
	                    </c:if>
                    </div>
                    <div class="polymer-detail f_left">
                        <div class="polymer-pro-name">${goods.goodsName}</div>
                        <ul>
                            <li>
                                <span>别名:</span>
                                <span>${goods.aliasName}<c:if test="${empty goods.aliasName }">暂无数据</c:if></span>
                            </li>
                            <li>
                                <span>规格:</span>
                                <span>${goods.spec}<c:if test="${empty goods.spec }">暂无数据</c:if></span>
                            </li>
                            <li>
                                <span>包装尺寸:</span>
                                <span> 长度${goods.packageLength} cm 宽度${goods.packageWidth} cm 高度 ${goods.packageHeight} cm 毛重 ${goods.grossWeight} kg</span>
                            </li>
                        </ul>
                        
                    </div>
            </div>
            <div class="polymer-part2">
                <ul>
                    <li>
                        <div class="f_left"><i class="icon iconzan"></i></div>
                        <div class="polymer-bright-point">
                            <div class="polymer-bright-title">
                                产品亮点
                            </div>
                            <div>
                                <ul>
                                    <li>${goods.advantage}<c:if test="${empty goods.advantage }">暂无数据</c:if></li>
                                </ul>
                            </div>
                        </div>
                    </li>
                     <li>
                        <div class="f_left"><i class="icon iconspeech"></i></div>
                        <div class="polymer-bright-point">
                            <div class="polymer-bright-title">
                                简单话术
                            </div>
                            <div>
                                <ul>
                                    <li>${goods.sellingWords}<c:if test="${empty goods.sellingWords }">暂无数据</c:if></li>
                                </ul>
                            </div>
                        </div>
                    </li>
                     <li>
                        <div class="f_left"><i class="icon iconzixun"></i></div>
                        <div class="polymer-bright-point ">
                            <div class="polymer-bright-title">
                                报价咨询
                            </div>
                            <div>
                               <table class="table">
                                   <thead >
                                       <tr style="background: #fff;">
                                           <th>非公立机构价</th>
                                           <th>公立价</th>
                                           <th>经销商指导价</th>
                                       </tr>
                                   </thead>
                                   <tbody>
                                   		<c:forEach var="list" items="${goodsChannelPriceList}" varStatus="statu">
	                                       <tr>
	                                           <td><fmt:formatNumber type="number" value="${list.privatePrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                                           <td><fmt:formatNumber type="number" value="${list.publicPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                                           <td><fmt:formatNumber type="number" value="${list.distributionPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                                       </tr>
                                       </c:forEach>
                                       <c:if test="${empty goodsChannelPriceList }">
                                       		<tr>
	                                           <td colspan="3">暂无数据</td>
	                                       </tr>
                                       </c:if>
                                   </tbody>
                               </table>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="polymer-part3">
                <div class="vd-choose-one pb30">
                      <div class="vd-choose-title polymer-choose-title" id="product-fix">
                        <ul>
                            <li class="active">
                                <div>常见问题</div>
                            </li>
                            <li>
                                <div>注意事项</div>
                            </li>
                             <li>
                                <div>彩页</div>
                            </li>
                            <li>
                                <div>选型辅助</div>
                            </li>
                            <li>
                                <div>配件耗材</div>
                            </li>
                            <li>
                                <div>竞品分析</div>
                            </li>
                            <li>
                                <div>关联产品</div>
                            </li>
                            <li>
                                <div>适用科室</div>
                            </li>
                        </ul>
                      </div>
                      <div class="vd-choose-content polymer-choose-content">
                         <ul>
                            <li class="active">
	                            <form action="" id="myform">
	                            	<div class="ask-answers">
		                                <c:if test="${not empty goods.goodsFaqs }">
		                                	<c:forEach items="${goods.goodsFaqs }" var="faq">
		                                		<div class="ask-answers-item">
		                                			<c:if test="${faq.parentId eq 0 }">
				                                        <div class="ask-answer">
				                                            <div class="polymer-question">问</div>
				                                            <div>${faq.content}</div>
				                                         </div>
			                                        </c:if>
			                                        <c:forEach items="${goods.goodsFaqs }" var="an">
			                                        	<c:if test="${faq.goodsFaqId eq an.parentId}">
					                                         <div class="ask-answer">
					                                            <div class="polymer-answer">答</div>
					                                            <div>${an.content}</div>
					                                         </div>
				                                         </c:if>
			                                         </c:forEach>
			                                    </div>
		                                	</c:forEach>
		                                </c:if>
	                                </div>
	                                <div class="ask-answer-contoller">
	                                	<c:if test="${sessionScope.curr_user.username eq 'njadmin' || sessionScope.curr_user.positType eq 608 || sessionScope.curr_user.positType eq 311 || (sessionScope.curr_user.positType eq 310 && sessionScope.curr_user.positLevel ne 445)}">
	                                		<span class="bt-small bt-bg-style bg-light-blue ask-answer-add"> 新增 </span>
		                                    <span class="bt-small bt-bg-style bg-light-blue ask-answer-edit <c:if test="${empty goods.goodsFaqs }">none</c:if>">编辑</span>
		                                    <span class="bt-small bt-bg-style bg-light-blue ask-answer-finish none">完成</span>
		                                    <span class="bt-small bt-bg-style bg-light-blue ask-answer-goback none">返回</span>
	                                	</c:if>
	                                </div>
	                                <input type="hidden" name="goodsId" value="${goods.goodsId}">
	                            </form>
                            </li>
                            <li>
                                <div class="attention-items">
                                    <div>
                                        <dl>
                                            <dt>售后内容：
	                                            <c:forEach items="${goods.goodsAftersList}" var="list" varStatus="status">
							                    	<c:choose>
														<c:when test="${list.attributeId eq 655}">
															包安装&nbsp;&nbsp;
														</c:when>
														<c:when test="${list.attributeId eq 656}">
															包运输&nbsp;&nbsp;
														</c:when>
														<c:when test="${list.attributeId eq 657}">
															包培训&nbsp;&nbsp;
														</c:when>
														<c:otherwise>
														</c:otherwise>
													</c:choose>
							                    </c:forEach>
						                    </dt>
                                            <dt>质保年限： ${goods.warrantyPeriod}</dt>
                                            <dt>退货条件：<c:if test="${goods.isRefund eq 0}">不允许退货</c:if><c:if test="${goods.isRefund eq 1}">允许退货</c:if></dt>
                                            <dt>现货货期： ${goods.delivery}</dt>
                                            <dt>运输重量： ${goods.transportWeight}</dt>
                                            <dt>是否包含运费：<c:if test="${goods.isHvaeFreight eq 0}">否</c:if><c:if test="${goods.isHvaeFreight eq 1}">是</c:if></dt>
                                        </dl>
                                    </div>
                                </div>
                            </li>
                             <li>
                                <div class="pl15">
                                    <span class="mr10">宣传彩页</span>
                                    <c:forEach items="${goods.brochureList }" var="gd">
                                    	 <a href="http://${gd.domain}/${gd.uri}" target="_blank" style="color: #0099ff;">${gd.name}</a>
                                    	 <i class="icon icondownload ml10" onclick="downloadFile('http://${gd.domain}/${gd.uri}');"></i>
                                    	
                                    </c:forEach>
                                </div>
                            </li>
                            <li>
                                <div class="pl15">
                                    <span class="mr10">选型文档</span>
                                    <c:if test="${not empty goods.assistantUri }">
                                    	<a href="http://${goods.assistantDomain}/${goods.assistantUri}" target="_blank" style="color: #0099ff;" >${goods.assistantName} </a>
                                    	<i class="icon icondownload ml10" onclick="downloadFile('http://${goods.assistantDomain}/${goods.assistantUri}');"></i>
                                    </c:if>
                                    
                                </div>
                            </li>
                            <li>
                                <div>
                                    <table class="table">
                                       <thead>
                                           <tr style="background: #fff;">
                                               <th class="wid10">序号</th>
                                               <th>商品名称</th>
                                               <th class="wid25">订货号</th>
                                           </tr>
                                       </thead>
                                       <tbody>
                                      	   <c:forEach var="list" items="${listPackage}" varStatus="statu">
                                     	   	   <tr>
	                                               <td>${statu.count}</td>
		                    					   <td>${list.goodsName}</td>
	                                               <td>${list.sku}</td>
	                                           </tr>
                                           </c:forEach>
                                           <c:if test="${empty listPackage }">
								            	<tr>
								                	<td colspan="3">暂无关联商品!</td>
								                </tr>
							       			</c:if>
                                       </tbody>
                                   </table>
                                </div>
                            </li>
                            <li>
                                <div class="competitive-analysis">
                                    <dl>
                                        <dt>${goods.competitiveAnalysis}
                                        <c:if test="${empty goods.competitiveAnalysis}">
				                        		暂无数据
				                     	</c:if>
				                     </dt>
                                    </dl>
                                </div>
                            </li>
                            <li>
                                <div>
                                     <table class="table">
                                       <thead>
                                           <tr style="background: #fff;">
                                               <th class="wid10">序号</th>
                                               <th>商品名称</th>
                                               <th class="wid23">订货号</th>
                                               <th class="wid23">品牌</th>
                                               <th class="wid23">型号</th>
                                           </tr>
                                       </thead>
                                       <tbody>
                                           <c:forEach var="list" items="${listRecommend}" varStatus="statu">
								                <tr>
								                    <td>${statu.count}</td>
								                    <td>${list.goodsName}</td>
								                    <td>${list.sku}</td>
								                    <td>${list.brandName}</td>
								                    <td>${list.model}</td>
								                </tr>
							            	</c:forEach>
							            	<c:if test="${empty listRecommend}">
								            	<tr>
								                	<td colspan="5">暂无关联商品信息!</td>
								                </tr>
							       			</c:if>
			                          </tbody>
                                   </table>
                                </div>
                            </li>
                            <li>
                                <div>
                                    <c:forEach items="${goodsSysOptionAttributeList}" var="list" varStatus="status">
			                        	<c:if test="${list.attributeType  == 320}">
				                        ${list.title}&nbsp;
				                        </c:if>
			                        </c:forEach>
			                        <c:if test="${empty goodsSysOptionAttributeList}">
				                        暂无数据
				                     </c:if>
                                </div>
                            </li>
                        </ul>
                      </div>
                </div>
            </div>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>