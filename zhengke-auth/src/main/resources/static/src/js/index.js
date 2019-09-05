!function(){
	$(function(){
		//回车键
		$("body").keyup(function(event){
			if(event.keyCode ==13){
    			$("#loginBtn").click();
  			}
		})
		
		//登录按钮
		$("#loginBtn").click(function(){
			var userName = $("#loginName").val();
			var pwd = $("#pwd").val();
			alert(userName + pwd);
			if(userName && pwd){
				$.ajax({
            		url:"http://localhost:8000"+"/login?username="+userName+"&password="+pwd,
            		type:"post",
            		dataType:"json",
            		contentType:"application/json",
            		data:JSON.stringify({userName:userName,pwd:pwd}),
            		success:function(resData){
            			if(resData.success){
            				window.location.href = "src/system.html";
            			}else{
            				$("#span-msg").text(_common.getMessage(resData.messageCode));
            			}        			
            		}
            	}); 
			}else{
				$("#span-msg").text("请输入账号或密码");
			}
		});
		
		$("#loginName").on("change",function(){
			$("#span-msg").text("");
		});
		$("#pwd").on("change",function(){
			$("#span-msg").text("");
		});
		
	});
}();