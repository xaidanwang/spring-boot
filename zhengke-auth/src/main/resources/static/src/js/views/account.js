!function(){
	 $(document).ready(function(){
		 
       //初始化表格
      	$("#jqGrid").jqGrid({
		    url: _common.rootPath + 'api/user/userInfoList.json',
		    datatype: "json",
		    mtype:"POST",
		    contentType : "application/json",
		    colModel: [			
				{ label: '账号id', name: 'ID', width: 20,hidden:true },
				{ label: '机器码', name: 'PHNON_ID', width: 40 },
				{ label: '秘钥', name: 'TOKEN', width: 40 },
				{ label: '到期时间', name: 'TIME', width: 40 },
				{ label: '登录时间', name: 'LAST_LOGIN_TIME', width: 50 },
				{ label: '登录状态', name: 'LOGIN_STATUS', width: 40, formatter: function(value, options, row){
					if(value === 0){
						return '<span class="label label-warning">离线</span>'
					}else{
						return '<span class="label label-success">在线</span>';
					}
				}},
				{ label: 'TOKEN', name: 'TOKEN', width: 100 }
		    ],
			viewrecords: true,//是否显示记录总数
		    height: 400,
		    rowNum: 10,
			rowList : [10,30,50],
		    rownumbers: true, 
		    rownumWidth: 70, 
		    autowidth:true,
		    multiselect: true,
		    pager: "#jqGridPager",
		    prmNames : {
		        page:"pageNumber", 
		        rows:"pageSize", 
		        order: "order",
		        sort:"sort"
		    },
		    postData:{
		    	cellNames:"ID,CUSTOMER_NAME,LOGIN_NAME,LOGIN_PWD,LAST_LOGIN_TIME,LOGIN_STATUS,TOKEN"
		    },
		    gridComplete:function(){
		    	//隐藏grid底部滚动条
		    	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
		    },
		    jsonReader : {  
				root:"result.rows",  
				page: "result.page",  
				total: "result.total",  
				records: "result.records",   
			},  
		    loadComplete: function(xhr){   
				if(xhr.success == false){
					_common.showInfoModal(xhr.messageCode);
				}
			}
    });

      	//登录
      	$("#login").click(function(){
      		var postData ={id:null};
      		var idArray = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
      		if (idArray && idArray.length && idArray.length == 1) {
      			var id = $("#jqGrid").jqGrid('getRowData', idArray[0]).ID;
      			postData.id = id;
      			 $.ajax({
	            		url:_common.rootPath+"api/user/userLogin",
	            		type:"post",
	            		dataType:"json",
	            		contentType:"application/json",
	            		data:JSON.stringify(postData),
	            		success:function(resData){
	            			if(resData.success){
	            				$("#jqGrid").jqGrid('setGridParam',{ 
	            	      	 		page:1, 
	            	            	postData:postData
	            	        	}).trigger("reloadGrid");
	            			}else{
	            				 _common.showInfoModal(resData.messageCode);
	            			}        			
	            		}
	            	}); 
      		}else{
      			 _common.showInfoModal("请选择一行");
      		}
      	});
      	
      //token查看
      	/*$("#tokenShow").click(function(){
      		var idArray = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
      		if (idArray && idArray.length && idArray.length == 1) {
      			var token = $("#jqGrid").jqGrid('getRowData', idArray[0]).TOKEN;
      			$("#bigToken").text(token);
      			$("#dataModel").modal("show");
      		}else{
      			 _common.showInfoModal("请选择一行");
      		}
      	});*/
      	
       	//新增
      	$("#addBtn").click(function(){
      	 	$("#addUserModal").modal("show");
      	 	$("#saveBtn").unbind().bind("click",function(){
      	 		var postData = getFormData();
	       		if(postData){
	       			//发送请求
	       			 $.ajax({
	            		url:_common.rootPath+"api/user/addUser.json",
	            		type:"post",
	            		dataType:"json",
	            		contentType:"application/json",
	            		data:JSON.stringify(postData),
	            		success:function(resData){
	            			if(resData.success){
	            				//重新查询
	            				$("#jqGrid").jqGrid('setGridParam',{ 
	            	      	 		page:1, 
	            	            	postData:postData
	            	        	}).trigger("reloadGrid");
	            				$("#addUserModal").modal("hide");
	            			}else{
	            				 _common.showInfoModal(resData.messageCode);
	            			}        			
	            		}
	            	}); 
	       		}     		
      	 	});
       	}); 
       	
       	
       	//模态框关闭事件，清空表单
       	$("#addUserModal").on('hide.bs.modal',function(){
       		$("#addUserForm")[0].reset();//表单重置
       		$("#addUserForm").data('bootstrapValidator').resetForm(true);//表单验证重置
       		$("#bigToken").text("");
       	});
       	
       	function getFormData(){
       		$("#addUserForm").bootstrapValidator('validate');
       		//获取表单验证对象
       		var bootstrapValidator = $("#addUserForm").data('bootstrapValidator');  
       		var isPass = bootstrapValidator.isValid();
       		if(!isPass){
       			return null;
       		}
       		//初始化向服务器请求参数
       		var postData ={customerName:null,loginName:null,loginPwd:null};
       		//获取参数
       	 	postData.customerName = $('#addUserForm input[name=customerName]').val();
            postData.loginName = $('#addUserForm input[name=loginName]').val();
            postData.loginPwd = $('#addUserForm input[name=loginPwd]').val();
       		return postData;
       	}
       	
       	/*******************表单验证功能*****************************/
       	 $('#addUserForm').bootstrapValidator({ 
       	 	message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	customerName: {
	                validators: {
	                	notEmpty: {
                        	message: '客户名称必填'
                    	}
	                }
	            },
	            loginName: {
	                validators: {
	                	notEmpty: {
                        	message: '登录帐号必填'
                    	}
	                }
	            },
	            loginPwd: {
	                validators: {
	                	notEmpty: {
                        	message: '登录密码必填'
                    	}
	                }
	            }
	        }
	     });
    });
	
}();



