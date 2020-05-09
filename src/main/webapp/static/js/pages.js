$(function(){
			$('.pages_num li').click(function(){
				if($('.firstpage').hasClass('canotclick')){
					$('.firstpage').removeClass('canotclick');
				}
				if($('.lastpage').hasClass('canotclick')){
					$('.lastpage').removeClass('canotclick');
				}
				$('.firstpage').addClass('canclick');
				$('.lastpage').addClass('canclick');
				$(this).siblings().removeClass('num_active');
				$(this).addClass('num_active');
			});			
			$('.pages_num li').hover(function(){
				$(this).siblings().removeClass('pageslihover');
				$(this).addClass('pageslihover');
				if($(this).hasClass('num_active')){
					$(this).addClass('num_active');
				}
			},function(){
			$('.pages_num li').removeClass('pageslihover')});
			$('.lastpage').click(function(){
				$('.pages_num li').removeClass('num_active');
				if($('.firstpage').hasClass('canotclick')){
					$('.firstpage').removeClass('canotclick');
				}
				$('.firstpage').addClass('canclick');
				$(this).addClass('canotclick');
			})
			$('.firstpage').click(function(){
				$('.pages_num li').removeClass('num_active');
				if($('.lastpage').hasClass('canotclick')){
					$('.lastpage').removeClass('canotclick');
				}
				$('.lastpage').addClass('canclick');
				$(this).addClass('canotclick');
			})
})