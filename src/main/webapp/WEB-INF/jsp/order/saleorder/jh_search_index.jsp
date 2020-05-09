<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="title" value="检索产品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
    $(function(){
    	$('.front-page-fix').click(function() {
            $(this).hide('slow');
            $(window.parent.document).find('.front-page-slide').show('slow');
         })
		 $(window.parent.document).find('.front-page-close').click(function() {
		         $('.front-page-fix').show('slow');
		         $(this).parent('front-page-slide').hide('slow');
		 })
    	

        function controlWidth(obj){
             var obj_len = obj.length;
             var li_heit =  obj.eq(0).find('ul li').eq(0).outerHeight();  
                for(var i = 0; i < obj_len; i++){
                    var obj_ul = obj.eq(i).find('ul');  
                    var obj_ul_len = obj.eq(i).find('ul').length;  
                        for(var j= 0;j < obj_ul_len; j++ ){
                             var li_len_0 = obj.eq(i).find('ul').eq(j).find('li').length;
                             var ul_h_0 = li_heit * li_len_0;
                            if(ul_h_0 > 288){
                                obj.eq(i).find('ul').eq(j).addClass('control');
                            }else{
                                obj.eq(i).find('ul').eq(j).attr('class','');
                            } 
                        }
                }
        }
        controlWidth($('.control1'));
   		$('.control1 ul li').each(function(){
            $(this).click(function(){
                var this_html =$(this).find('span').eq(0).html();
                    $(this).addClass('active').siblings().removeClass('active');
                    $(this).parent('ul').siblings('.please').find('input').attr('placeholder',this_html)
            })
        }) 
        
        $("#sub").click(function(){
        	checkLogin();
        	var brandName = $("input[name='brandName']").val();
        	var model = $("input[name='model']").val();
        	var goodsName = $("input[name='goodsName']").val();
        	var one = $("input[name='one']").val();
        	var two = $("input[name='two']").val();
        	var three = $("input[name='three']").val();
        	if(brandName == '' && model == '' && goodsName == '' && one == '' && two == '' && three == ''){
        		layer.alert("请输入搜索条件");
        		return false;
        	}
        	$("#myform").submit();
        });
        
        $('#front-page-search').click(function(){
        	checkLogin();
            if($('#type-search').val() != ''){
                $('.input-research').show();
                $('.front-page-sanji').hide();
            }
            if($('#product-search').val() != '' && $('#type-search').val() == ''){
                $('.input-research').hide();
                $('.front-page-sanji').show();
            }
        })
        $('#front-page-clear').click(function(){
            $('.input-research').hide();
            $('.front-page-sanji').show();
            $('#type-search').val('');
        })
        
        $("#front-page-search").click(function(){
        	var categoryName = $("#type-search").val();
    		if(categoryName == ''){
    			warnErrorTips("type-search","ptype-searchError","内容为空不允许搜索");//文本框ID和提示用语
    			return false;
    		}
    		$.ajax({
    			url:page_url + '/order/saleorder/getSaleJHCategory.do',
    			data:{"categoryName":categoryName},
    			type:"POST",
    			dataType : "json",
    			async: false,
    			success:function(data)
    			{
    				 if(data.code==0){
    					$("#l5").empty();
    					var li = "";
    					for(var i = 0;i<data.listData.length; i++){
    						var cat =data.listData[i];
    						li += "<li><span title='"+cat.categoryName+"' onclick = 'getLevel(\""+cat.categoryName+"\","+cat.categoryId+","+cat.level+")'>"+cat.categoryName+"</span></li>";
    					}
    					$("#l5").append(li);
    					 controlWidth($('.control1'));
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
    })
    function addBg(){
    	 $('.control1 ul li').each(function(){
             $(this).click(function(){
                 var this_html =$(this).find('span').eq(0).html();
                     $(this).addClass('active').siblings().removeClass('active');
                     $(this).parent('ul').siblings('.please').find('input').attr('placeholder',this_html)
             })
         })
    }
    
     function controlWidth(obj){
      var obj_len = obj.length;
      var li_heit =  obj.eq(0).find('ul li').eq(0).outerHeight();  
         for(var i = 0; i < obj_len; i++){
             var obj_ul = obj.eq(i).find('ul');  
             var obj_ul_len = obj.eq(i).find('ul').length;  
                 for(var j= 0;j < obj_ul_len; j++ ){
                      var li_len_0 = obj.eq(i).find('ul').eq(j).find('li').length;
                      var ul_h_0 = li_heit * li_len_0;
                     if(ul_h_0 > 288){
                         obj.eq(i).find('ul').eq(j).addClass('control');
                     }else{
                         obj.eq(i).find('ul').eq(j).attr('class','');
                     } 
                 }
         }
 	 }
    function getLevel(categoryName,categoryId,level){
    	checkLogin();
    	if(level ==1){
    		$("input[name='one']").val(categoryId);
    		$("#one").val(categoryName);
    	}else if(level ==2){
    		$("input[name='two']").val(categoryId);
    		$("#one").val(categoryName.split(">")[0]);
    		$("#two").val(categoryName.split(">")[1]);
    	}else {
    		$("input[name='three']").val(categoryId);
    		$("#one").val(categoryName.split(">")[0]);
    		$("#two").val(categoryName.split(">")[1]);
    		$("#three").val(categoryName.split(">")[2]);
    		$.ajax({
    			url:page_url + '/order/saleorder/getSaleJHCategoryValueList.do',
    			data:{"categoryId":categoryId},
    			type:"POST",
    			dataType : "json",
    			async: false,
    			success:function(data)
    			{
    				 if(data.code==0){
    					$("#l4").empty();
    					var li = "";
    					for(var i = 0;i<data.listData.length; i++){
    						var cat =data.listData[i];
    						li += '<li>'+
    		                        '<div class="front-page-name1">'+cat.categoryAttributeName+'</div>'+
    		                        '<div class="front-page-name2">'+
    		                            '<div>';
    		                            for(var j = 0; j<cat.categoryAttributeValue.length; j++){
    		                            	li += '<span>'+cat.categoryAttributeValue[j].attrValue+'</span>&nbsp;&nbsp;&nbsp;&nbsp;';
    		                            }   
    		                	  li += '</div>'+
    		                        '</div>'+
    		                    '</li>';
    						  
    					}
    					$("#l4").append(li);
    					addBg();
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
    	}
    }
    
    function selectCate(categoryName,categoryId,treeNum,level){
    	checkLogin();
    	if(level ==1){
    		$("#one").val(categoryName);
    		$("input[name='one']").val(categoryId);
    		$("#two").val("").attr('placeholder',"请选择");
    		$("#three").val("").attr('placeholder',"请选择");
    		$("#l2").empty();
    		$("#l3").empty();
    		$("#l4").empty();
    		if(treeNum != null && treeNum > level){
    			$.ajax({
        			url:page_url + '/order/saleorder/getSaleJHCategory.do',
        			data:{"categoryId":categoryId,"level":level+1},
        			type:"POST",
        			dataType : "json",
        			async: false,
        			success:function(data)
        			{
        				 if(data.code==0){
        					var li = "";
        					for(var i = 0;i<data.listData.length; i++){
        						var cat =data.listData[i];
        						var len = cat.treenodes.split(",").length;
        						if(len > 2){
        							 li += "<li onclick = 'selectCate(\""+cat.categoryName+"\","+cat.categoryId+","+len+",2)'>"+
    				                			"<span>"+cat.categoryName+"</span>"+
    				                			"<span>></span>"+
    				                		"</li>"; 
        						}else{
        							 li += "<li onclick = 'selectCate(\""+cat.categoryName+"\","+cat.categoryId+","+len+",2)'>"+
    			                			"<span>"+cat.categoryName+"</span>"+
    			                			"<span></span>"+
    			                		"</li>"; 
        						}  
        					}
        					$("#l2").append(li);
        					controlWidth($('.control1'));
        					addBg();
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
    		}
    		  
    	}else if(level ==2){
    		$("#two").val(categoryName);
    		$("input[name='two']").val(categoryId);
    		$("#three").val("").attr('placeholder',"请选择");
    		$("input[name='three']").val("");
    		$("#l3").empty();
    		$("#l4").empty();
    		if(treeNum != null && treeNum > level){
    			$.ajax({
        			url:page_url + '/order/saleorder/getSaleJHCategory.do',
        			data:{"categoryId":categoryId,"level":level+1},
        			type:"POST",
        			dataType : "json",
        			async: false,
        			success:function(data)
        			{
        				 if(data.code==0){
        					
        					var li = "";
        					for(var i = 0;i<data.listData.length; i++){
        						var cat =data.listData[i];
        						li += "<li onclick = 'selectCate(\""+cat.categoryName+"\","+cat.categoryId+","+0+",3)'>"+
    			                			"<span>"+cat.categoryName+"</span>"+
    			                			"<span></span>"+
    			                		"</li>";  
        					}
        					$("#l3").append(li);
        					controlWidth($('.control1'));
        					addBg();
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
    		}
    		
    	}else if(level == 3){
    		$("#three").val(categoryName);
    		$("input[name='three']").val(categoryId);
    		$("#l4").parent("div").addClass("none");
    		$("#l4").empty();
    		$.ajax({
    			url:page_url + '/order/saleorder/getSaleJHCategoryValueList.do',
    			data:{"categoryId":categoryId},
    			type:"POST",
    			dataType : "json",
    			async: false,
    			success:function(data)
    			{
    				 if(data.code==0){
    					var li = "";
    					if(data.listData.length > 0){
    						$("#l4").parent("div").removeClass("none");
    					}
    					for(var i = 0;i<data.listData.length; i++){
    						var cat =data.listData[i];
    						li += '<li>'+
			                        '<div class="front-page-name1">'+cat.categoryAttributeName+'</div>'+
			                        '<div class="front-page-name2">'+
			                            '<div>';
			                            for(var j = 0; j<cat.categoryAttributeValue.length; j++){
			                            	li += '<span>'+cat.categoryAttributeValue[j].attrValue+'</span>&nbsp;&nbsp;&nbsp;&nbsp;';
			                            }   
			                	  li += '</div>'+
			                        '</div>'+
			                    '</li>';
    						  
    					}
    					$("#l4").append(li);
    					addBg();
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
    	}
    }
    
    function searchReset() {
    	checkLogin();
    	$("form").find("input[type='text']").val('');
    	$("#one").val("").attr('placeholder',"请选择");
    	$("#two").val("").attr('placeholder',"请选择");;
		$("#three").val("").attr('placeholder',"请选择");;
    	$("input[name='one']").val("");
		$("input[name='two']").val("");
		$("input[name='three']").val("");
		$("#l2").empty();
		$("#l3").empty();
		$("#l4").empty();
    }
</script>
 <div class="form-list">
        <form method="post" action="${pageContext.request.contextPath}/order/saleorder/getSaleJHGoodsListPage.do" id="myform">
            <ul>
                <li>
                    <div class="form-tips">
                        <lable>品牌名称</lable>
                    </div>
                    <div class="f_left">
                        <div class="form-blanks">
                            <input type="text" class="wid20" name="brandName" value="${goods.brandName }"/>
                        </div>
                    </div>
                </li>
                 <li>
                    <div class="form-tips">
                        <lable>制造商型号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="wid20" name="model" value="${goods.model }"/>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>产品名称</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" class="wid20" id="product-search" name="goodsName" value="${goods.goodsName}"/>
                        </div>
                    </div>
                </li>
                <li class="pos_rel">
                    <div class="form-tips">
                        <lable>产品分类目录</lable>
                    </div>
                    <div class="f_left">
                        <div class="form-blanks juhe-front-page control1">
                            <div class="mb10">
                                <input type="text" name="" class="wid20" placeholder="可输入分类名称进行搜索" id="type-search">
                                <span class="bt-small bt-bg-style bg-light-blue" style="line-height: 25px;"  id="front-page-search">搜索</span>
                                <span class="bt-small bt-bg-style bg-light-blue" style="line-height: 25px;"  id="front-page-clear" >清空</span>
                                <input type="hidden" name="one" value="${goods.one}">
                                <input type="hidden" name="two" value="${goods.two}">
                                <input type="hidden" name="three" value="${goods.three}">
                                <div class="clear"></div>
                            </div>
                            <div id="type-searchError"></div>
                            <div class="front-page-sanji">
                                <div class="f_left">
                                    <div class="please">
                                    	<input type="text" class="wid257" placeholder="请选择" readonly="readonly" id="one" name="name1" value="${name1}">
                                    </div>
                                     <ul>
                                        <c:forEach items="${list}" var="cat">
                                        	<li onclick="selectCate('${cat.categoryName}',${cat.categoryId},${fn:length(fn:split(cat.treenodes, ','))},1)">
                                        		<span>${cat.categoryName}</span>
                                        		<span><c:if test="${cat.treenodes ne null && fn:length(fn:split(cat.treenodes, ',')) gt 1}">></c:if></span>
                                        	</li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <div class="f_left">
                                  <div class="please">
	                                  <input type="text" class="wid257" placeholder="请选择" readonly="readonly" id="two" name="name2" value="${name2}">
                                  </div>
                                     <ul id="l2">
                                    </ul>   
                                 </div>
                                <div class="f_left">
                                  <div class="please">
                                  		<input type="text" class="wid257" placeholder="请选择" readonly="readonly" id="three" name="name3" value="${name3}">
                                  </div>
                                    <ul id="l3">
                                    </ul>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="input-research mt10 control1 none">
                                <div class="f_left">
                                     <ul style="border-top:1px solid #ccc;" id="l5">
                                    </ul>
                                </div>
                            </div>
                           
                        </div>
                    </div>
                    <c:if test="${sessionScope.curr_user.positType eq 310 && show eq 1}">
                     <!-- 悬浮 -->
	                    <div  class="front-page-fix" >
	                        <img class="fixbanner" src="${pageContext.request.contextPath}/static/images/banner.png" />
	                    </div>
                    </c:if>
                </li>
            </ul>
            <div class="text-center">
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" style="line-height: 25px;" id="sub">搜索</span>
                    <span class="bt-middle bg-light-blue bt-bg-style mr20" style="line-height: 25px;" onclick="searchReset();">重置</span>
            </div>
        </form>
       
    </div>
     <div class="front-page-result">
            <div class="front-page-part1 none">
                <ul id="l4">
                </ul>
            </div>
            <div class="list-page normal-list-page">
                    <table class="table">
                        <thead>
                            <tr>
                                <th >产品名称</th>
                                <th class="wid30">品牌</th>
                                <th class="wid30">型号</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${goodsList }" var="list" varStatus="status">
	                            <tr>
	                                <td class="text-left">
	                                   <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"${pageContext.request.contextPath}/order/saleorder/toSaleJHGoodsDetailPage.do?goodsId=${list.goodsId}","title":"商品信息"}'>${list.goodsName}</a><br/>
	                                    订货号：${list.sku}
	                                    </span>
	                                </td>
	                                <td>${list.brandName}</td>
	                                <td>${list.model}</td>
	                            </tr>
                            </c:forEach>
                            <c:if test="${empty goodsList}">
				      			<tr>
				      				<td colspan="3">查询无结果！请尝试使用其他搜索条件。</td>
				      			</tr>
					       	</c:if>
                        </tbody>
                    </table>
                    
                </div>
                <tags:page page="${page}"/>
    </div>
<%@ include file="../../common/footer.jsp"%>