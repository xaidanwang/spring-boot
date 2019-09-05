//jqGrid的配置信息
if($.jgrid){
	$.jgrid.defaults.width = 1000;
	$.jgrid.defaults.responsive = true;
	$.jgrid.defaults.styleUI = 'Bootstrap';
}

	
//错误代码信息
var message = {
	'140001':'非法操作',
	'240000':'操作成功',
	'240001':'操作失败',
	'240002':'操作出错',
	'240003':'没有参数',
	'240004':'缺少参数',
	'240005':'错误参数',
	'240006':'暂无数据',
	
	'250001':'客户名称已存在',
	'250002':'登录名已存在',
	'250003':'登录失败'
	
}

var  _common={
		//请求根路径
		rootPath: document.location.protocol + '//' + window.location.host,
		getMessage:function(messageCode){
			var msg = message[messageCode]?message[messageCode]:messageCode;
			return msg;
		},
		//模态框显示
		showInfoModal:function(msg,noColse){
			var message = this.getMessage(msg);
			window.parent.$('#messageInfo').show();
			window.parent.$('#message').html(message);
			var infoModal = window.parent.$('#infoModal');
			if(infoModal.is(':visible') === false){
				infoModal.each(function(i) {   
					var $clone = $(this).clone().css('display', 'block').appendTo('body'); 
					var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);   
					top = top > 0 ? top : 0;   
					$clone.remove();   
					$(this).find('.modal-content').css("margin-top", top);   
				}); 
				if(noColse){
					infoModal.modal({backdrop: 'static', keyboard: false});
				} else{
					infoModal.modal('show');
				}
			}
			//会话过期，点击确认跳转到登录页	
			window.parent.$("#confirmBtn").unbind();		
			if(msg == '240108'){
				window.parent.$("#confirmBtn").on("click",function(){
					window.parent.window.location.href = "../index.html";
				});
			}
			window.parent.$('#processInfo').hide();
		},
		showProcessModal:function(process,total){
			var infoModal = window.parent.$('#infoModal');
			if(infoModal.is(':visible') === false){
				infoModal.each(function(i) {   
					var $clone = $(this).clone().css('display', 'block').appendTo('body'); 
					var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);   
					top = top > 0 ? top : 0;   
					$clone.remove();   
					$(this).find('.modal-content').css("margin-top", top);   
				}); 
				window.parent.$('#processInfo').show();
				window.parent.$('#progressBar').css("width",process+"%"); 
				window.parent.$('#progressValue').text(process); 
				infoModal.modal({backdrop: 'static', keyboard: false});
			}else{
				window.parent.$('#progressBar').css("width",process+"%"); 
				window.parent.$('#progressValue').text(process); 
			}
			window.parent.$('#messageInfo').hide();
		},
		closeInfoModal:function(){
			window.parent.$('#messageInfo').hide();
			window.parent.$('#processInfo').hide();
			window.parent.$('#infoModal').modal('hide');
		}
};	

