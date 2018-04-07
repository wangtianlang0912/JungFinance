
        

        	
        	$("#article_right a.a2,#article_media .a2").bind("click",function(){
        		
        		el = $(this);
        		id= el.attr('tag');
        		if(!id){
        			return ;
        		}
				rel = el.attr('rel');
				if(rel){
					alert("请先登录");
					window.location.href = rel;
					return;
					}
        		 $.ajax({
                  type: "post",
                  url: '/api/me/article/fav/create',
                   data: {id:id},
                  dataType: 'JSON',
                  success: function (data) {
                      if (data && data.error) {
                          alert(data.error);
                      }else {
                    	 
                    	  alert("已收藏");
                    	  el.addClass('a2_d');
        				
                      }
                  }
              });
        	});
        	
    
          	
          	var support = $("#support");
            var oppose = $("#oppose");
        	//支持
        	window.per = function(article){
        		s = article.support;
        		o = article.oppose;
        		support_per = parseInt(s/(s+o)*100);
        		oppose_per = parseInt(o/(s+o)*100);
        		$("i",support).text(s);
        		$("em",support).text(support_per);
        		
        		
        		$("i",oppose).text(o);
        		$("em",oppose).text(oppose_per);
        		
        		
        		};
        	$(".support",support).click(function(){

              if(!$(this).attr('uid')){
                alert("请先登录");
                window.location.href = $("#url").val();
                return;
              }

        		$.ajax({
                          type: "post",
                          url: '/api/vote/support',
                           data: {id:$("#id").val()},
                           async: false, 
                          dataType: 'JSON',
                          success: function (data) {
                              if (data && data.error) {
                                  alert(data.error);
                              }else {
        						  article = data.article;
                            	 per(article);
        						  
        						
                                 
                              }
                          }
                      });
        		
        		});
        	//反对
        	$(".oppose",oppose).click(function(){
            if(!$(this).attr('uid')){
                alert("请先登录");
                window.location.href = $("#url").val();
                return;
              }
        		$.ajax({
                          type: "post",
                          url: '/api/vote/oppose',
                           data: {id:$("#id").val()},
                           async: false, 
                          dataType: 'JSON',
                          success: function (data) {
                              if (data && data.error) {
                                  alert(data.error);
                              }else {
        						  
                            	 
                            	  article = data.article;
                            	 per(article);
                                 
                              }
                          }
                      });
        		
        		});
          //打赏
    $(".dashang_btn").bind("click",function(){
      if(!$(this).attr('uid')){
        alert("请先登录");
        window.location.href = $("#url").val();
        return;
      }
      $("#dashang_ok").show();
      /* $.ajax({
        type: "post",
        url: '/api/me/score/pay',
         data: {id:id},
        dataType: 'JSON',
        success: function (data) {
          if (data && data.error) {
            alert(data.error);
          }else {
            alert("关注成功");
            el.text("已关注");
           
          }
        }
      });*/
    });
     $('#article_right a.a3').bind("click",function(){
      $("#article_right .bdsharebuttonbox").show();
      });
      $('.bdsharebuttonbox').bind("click",function(){
      $("#article_right .bdsharebuttonbox").hide();
      });
    var dashang  = $("#dashang_ok");
    $('.mask_title em',dashang).bind("click",function(){
      dashang.hide();
      });
    
    $('.publish_btn',dashang).bind("click",function(){
      id = $('input[name=id]',dashang).val();
      touid = $('input[name=touid]',dashang).val();
      score = parseInt($('input[name=score]',dashang).val());
      if(score>99 || score<1 || isNaN(score)){
			 alert("请输入大于0小于100的赏金币");
			 return;
	}
      $.ajax({
        type: "post",
        url: '/api/me/score/pay',
         data: {id:id,touid:touid,score:score},
        dataType: 'JSON',
        success: function (data) {
          if (data && data.error) {
            alert(data.error);
          }else {
            $(".dashang_mask_input",dashang).hide();
             $(".dashang_mask_ok",dashang).show();
           
          }
        }
      });
      
      });

  //
          	
          	
        });