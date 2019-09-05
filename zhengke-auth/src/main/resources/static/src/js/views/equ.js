!function(){
	 $(document).ready(function(){
		 
       //初始化表格
      	$("#jqGrid").jqGrid({
		    url: _common.rootPath + 'api/equ/list.json',
		    datatype: "json",
		    mtype:"POST",
		    contentType : "application/json",
		    colModel: [			
				{ label: 'id', name: 'ID', width: 20, hidden:true},
				{ label: '客户id', name: 'CUSTOMER_ID', width: 20, hidden:true},
				{ label: '通讯id', name: 'COMMUNICATION_ID', width: 40 },
				{ label: '主板id', name: 'BOARD_ID', width: 40 },
				{ label: '客户名称', name: 'CUSTOMER_NAME', width: 40 },
				{ label: '状态', name: 'STATUS', width: 50 },
				{ label: '二维码', name: 'QR_CODE', width: 100 }
				
		    ],
			viewrecords: true,//是否显示记录总数
		    height: 200,
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
		    	cellNames:"ID,CUSTOMER_ID,COMMUNICATION_ID,BOARD_ID,CUSTOMER_NAME,STATUS,QR_CODE"
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

      	//测试
      	$("#testBtn").click(function(){
      		var idArray = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
      		if (idArray && idArray.length && idArray.length == 1) {
      			var id = $("#jqGrid").jqGrid('getRowData', idArray[0]).CUSTOMER_ID;
      			var deviceId = $("#jqGrid").jqGrid('getRowData', idArray[0]).COMMUNICATION_ID;
      			var boradId = $("#jqGrid").jqGrid('getRowData', idArray[0]).BOARD_ID;
      			$("#equModal").show();
      			$("#hiddenId").val(id);
      			$("#hiddenDeviceId").val(deviceId);
      			$("#hiddenBoradId").val(boradId);
      			$("#inSign").val("");
      		}else{
      			 _common.showInfoModal("请选择一行");
      		}
      	});
      	
      	//加载客户
        $("#kcui-combobox-equ-modal").kccombo({
            fields: {
                key: "id", 
                value: "customerName"
            },
            source: function(request, response){
               $.ajax({
            		url:_common.rootPath+"api/user/simpleList.json",
            		type:"post",
            		dataType:"json",
            		contentType:"application/json",
            		data:JSON.stringify(request),
            		success:function(resData){
            			if(resData.success){
            				var data = {
		        				dataList:resData.result,
		        				pageSize:resData.pageSize,
		        				pageNumber:resData.pageNumber,
		        				total:resData.total
            				};
            				response(data);
            			}else{
            				
            			}        			
            		},
            		error:function(){
            			
            		}
            	}); 
            },
            pageConfig: {
                isShow: true,
                pageSize:10,
                pageNumber:1
            }
        });	
        
        //查询指令结果
        $("#opBtn").click(function(){
        	var inSign = $("#inSign").val();
        	if(inSign == null
        			|| inSign == ''){
        		_common.showInfoModal("未获取inSign");
        		return;
        	}
        	var postData = {customerId:null,deviceId:null,inSign:null};
        	postData.customerId = $("#hiddenId").val();
        	postData.deviceId = $("#hiddenDeviceId").val();
        	postData.inSign = inSign;
 			 $.ajax({
          		url:_common.rootPath+"api/equ/startOp.json",
          		type:"post",
          		dataType:"json",
          		contentType:"application/json",
          		data:JSON.stringify(postData),
          		success:function(resData){
          			if(resData.success){
          				$("#timestamp").val(resData.timestamp);
          				$("#sign").val(resData.sign)
          				$("#opStr").val(JSON.stringify(JSON.parse(resData.str), null, 4));
          			}else{
          				 _common.showInfoModal(resData.messageCode);
          			}        			
          		}
          	});
        });
        
        //start
        $("#startBtn").click(function(){
        	var postData = {customerId:null,inCode:null,deviceId:null,boradId:null,info:null};
        	postData.customerId = $("#hiddenId").val();
        	console.log(postData.customerId);
        	postData.inCode = $("#inCode").val();
        	postData.deviceId = $("#hiddenDeviceId").val();
        	postData.boradId = $("#hiddenBoradId").val();
        	postData.info = $("#info").val();
  			 $.ajax({
         		url:_common.rootPath+"api/equ/startEqu.json",
         		type:"post",
         		dataType:"json",
         		contentType:"application/json",
         		data:JSON.stringify(postData),
         		success:function(resData){
         			if(resData.success){
         				$("#timestamp").val(resData.timestamp);
         				$("#sign").val(resData.sign)
         				$("#startStr").val(JSON.stringify(JSON.parse(resData.str), null, 4));
         				$("#inSign").val(resData.inSign);
         			}else{
         				 _common.showInfoModal(resData.messageCode);
         			}        			
         		}
         	}); 
        });
      	
       	//新增
      	$("#addBtn").click(function(){
      	 	$("#addEquModal").modal("show");
      	 	$("#saveBtn").unbind().bind("click",function(){
      	 		var postData = getFormData();
	       		if(postData){
	       			//发送请求
	       			 $.ajax({
	            		url:_common.rootPath+"api/equ/addEqu.json",
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
	            				$("#addEquModal").modal("hide");
	            			}else{
	            				 _common.showInfoModal(resData.messageCode);
	            			}        			
	            		}
	            	}); 
	       		}     		
      	 	});
       	}); 
       	
       	
       	//模态框关闭事件，清空表单
       	$("#addEquModal").on('hide.bs.modal',function(){
       		$("#addUserForm")[0].reset();//表单重置
       		$("#addUserForm").data('bootstrapValidator').resetForm(true);//表单验证重置
       		$("#kcui-combobox-equ-modal").kccombo("clearComboBox");//combobox重置
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
       		var postData ={communicationId:null,boardId:null,customerId:null};
       		//获取参数
       	 	postData.communicationId = $('#addUserForm input[name=communicationId]').val();
            postData.boardId = $('#addUserForm input[name=boardId]').val();
            
            var equItem = $("#kcui-combobox-equ-modal").kccombo("getSelectedItem");
        	var equInputVal = $("#kcui-combobox-equ-modal").val();
        	if(equItem && equItem.id && equInputVal){
        		postData.customerId = equItem.id;
       	 	}
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
	        	communicationId: {
	                validators: {
	                	notEmpty: {
                        	message: '通讯id必填'
                    	}
	                }
	            },
	            boardId: {
	                validators: {
	                	notEmpty: {
                        	message: '主板id必填'
                    	}
	                }
	            }
	        }
	     });
    });
	
}();



