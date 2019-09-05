function connect(rows){
    console.log('connect',rows);
    rows=rows.split(',');
    var postData ={};
    postData.protocolId=$("#hiddenId").val();
    postData.commBoardId=rows[2];
    postData.primaryBoardId=rows[3];
    postData.commHardCode=rows[4];
    postData.commHardType=rows[5];
    postData.primaryBoardVersion=rows[6];
    postData.commBoardVersion=rows[7];
    postData.simNum=rows[8];
    postData.latitude="1.1";
    postData.longitude="1.2";
    console.log('postData',postData);
    if(postData){
        //发送请求
        $.ajax({
            url:_common.rootPath+"api/pro/connectServer",
            type:"post",
            contentType:"application/json",
            data:JSON.stringify(postData),
            success:function(resData){
                _common.showInfoModal(resData);
                // if(resData.success){
                //     //重新查询
                //     $("#jqGrid").jqGrid('setGridParam',{
                //         page:1,
                //         postData:postData
                //     }).trigger("reloadGrid");
                // }else{
                //     _common.showInfoModal(resData.messageCode);
                // }
            }
        });
    }
}

function set(rows){
	var idArray = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
		if (idArray && idArray.length && idArray.length == 1) {
			var id = $("#jqGrid").jqGrid('getRowData',idArray[0]).ID;
			var deviceId = $("#jqGrid").jqGrid('getRowData', idArray[0]).COMMUNICATION_ID;
			var boradId = $("#jqGrid").jqGrid('getRowData', idArray[0]).BOARD_ID;
			$("#proModal").show();
			$("#hiddenId").val(id);
			$("#hiddenDeviceId").val(deviceId);
			$("#hiddenBoradId").val(boradId);
		}else{
			 _common.showInfoModal("请选择一行");
		}
}

!function(){
	 $(document).ready(function(){
		 
       //初始化表格
      	$("#jqGrid").jqGrid({
		    url: _common.rootPath + 'api/pro/list.json',
		    datatype: "json",
		    mtype:"POST",
		    contentType : "application/json",
		    colModel: [			
				{ label: 'id', name: 'ID', width: 20, hidden:true},
				{ label: '设备id', name: 'EQUMENT_ID', width: 20, hidden:true},
				{ label: '通讯id', name: 'COMMUNICATION_ID', width: 50 },
				{ label: '主板id', name: 'BOARD_ID', width: 40 },
				{ label: 'IMEI/MAC', name: 'IMEI', width: 50 },
				{ label: '编码类型', name: 'COMMTYPE', width: 30 },
				{ label: '主板版本', name: 'BOARD_VERSION', width: 30 },
				{ label: '通信板版本', name: 'COMM_VERSION', width: 30 },
				{ label: 'sim卡号', name: 'SIM_NUM', width: 60 },
				{ name: '操作', index: 'operate',width: 30,
                     formatter: function (value, grid, rows, state) {
                         return '<button type="button" class="btn btn-success" onclick="connect(\''+ rows+ '\')";>连接</button>'
                     }
                 },
                 { name: '设置', index: 'operate',width: 30,
                     formatter: function (value, grid, rows, state) {
                         return '<button type="button" class="btn btn-success" onclick="set(\''+ rows+ '\')";>回复</button>'
                     }
                 }
				
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
		    	cellNames:"ID,EQUMENT_ID,COMMUNICATION_ID,BOARD_ID,IMEI,COMMTYPE,BOARD_VERSION,COMM_VERSION,SIM_NUM"
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

      	//设置
      	$("#setBtn").click(function(){
      		var idArray = $("#jqGrid").jqGrid('getGridParam', 'selarrrow');
      		if (idArray && idArray.length && idArray.length == 1) {
//      			var id = $("#jqGrid").jqGrid('getRowData',idArray[0]).ID();
      			var deviceId = $("#jqGrid").jqGrid('getRowData', idArray[0]).COMMUNICATION_ID;
      			var boradId = $("#jqGrid").jqGrid('getRowData', idArray[0]).BOARD_ID;
      			$("#proModal").show();
//      			$("#hiddenId").val(id);
      			$("#hiddenDeviceId").val(deviceId);
      			$("#hiddenBoradId").val(boradId);
      			$("#inSign").val("");
      		}else{
      			 _common.showInfoModal("请选择一行");
      		}
      	});
      	
      	//加载设备列表中的通信id
        $("#kcui-combobox-equ-modal").kccombo({
            fields: {
                key: "id", 
                value: "commId"
            },
            source: function(request, response){
               $.ajax({
            		url:_common.rootPath+"api/equ/commList.json",
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
        
        
      
      	
       	//新增
      	$("#addBtn").click(function(){
      	 	$("#addProModal").modal("show");
      	 	$("#saveBtn").unbind().bind("click",function(){
      	 		var postData = getFormData();
      	 		console.log("----------------",postData);
	       		if(postData){
	       			//发送请求
	       			 $.ajax({
	            		url:_common.rootPath+"api/pro/addPro.json",
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
	            				$("#addProModal").modal("hide");
	            			}else{
	            				 _common.showInfoModal(resData.messageCode);
	            			}        			
	            		}
	            	}); 
	       		}     		
      	 	});
       	}); 
      //proModal 保存
        $("#installBtn").click(function(){
        	
        	var postData = {};
        	postData.protocol_id = $("#hiddenId").val();
        	// postData.deviceId = $("#hiddenDeviceId").val();
            postData.msgStatus = $("#msgStatus").val();
            postData.equStatus = $("#equStatus").val();
            postData.upStatus = $("#upStatus").val();
            postData.msgType = $("#msgType").val();
            postData.upMinute = $("#upMinute").val();
            postData.upSecond = $("#upSecond").val();
            postData.downStatus = $("#downStatus").val();
            postData.money = $("#money").val();
            postData.sumMoney = $("#sumMoney").val();
            postData.traData = $("#traData").val();
            postData.reportData = $("#reportData").val();

        	console.log('postData',postData);
 			 $.ajax({
          		url:_common.rootPath+"api/reply/addReply.json",
          		type:"post",
          		dataType:"json",
          		contentType:"application/json",
          		data:JSON.stringify(postData),
                 success:function(resData){
          		    console.log('resData',resData);
                     if(resData.success){
                         $("#proModal").hide();
                     }else{
                         _common.showInfoModal(resData.messageCode);
                     }
                 }
          	});
        });
      	
       	//投币上报
        $("#coinBtn").click(function(){
        	var postData = {};
        	postData.protocolId = $("#hiddenId").val();
 			 $.ajax({
          		url:_common.rootPath+"api/pro/coinReport",
          		type:"post",
          		contentType:"application/json",
          		data:JSON.stringify(postData),
                 success:function(resData){
                     _common.showInfoModal(resData);
                 }
          	});
        });
        //透明数据上报
         $("#dataBtn").click(function(){
             var postData = {};
             postData.protocolId = $("#hiddenId").val();
             $.ajax({
                 url:_common.rootPath+"api/pro/transReport",
                 type:"post",
                 contentType:"application/json",
                 data:JSON.stringify(postData),
                 success:function(resData){
                     _common.showInfoModal(resData);
                 }
             });
         });

       		//透明数据上报
        $("#reportData").click(function(){
        	
        	var postData = {customerId:null,deviceId:null};
        	postData.customerId = $("#hiddenId").val();
        	postData.deviceId = $("#hiddenDeviceId").val();
 			 $.ajax({
          		url:_common.rootPath+"api/equ/startOp.json",
          		type:"post",
          		dataType:"json",
          		contentType:"application/json",
          		data:JSON.stringify(postData),
          	});
        });
       	
       	//模态框关闭事件，清空表单
       	$("#addProModal").on('hide.bs.modal',function(){
       		$("#addProForm")[0].reset();//表单重置
       		$("#addProForm").data('bootstrapValidator').resetForm(true);//表单验证重置
       		$("#kcui-combobox-equ-modal").kccombo("clearComboBox");//combobox重置
       	});
       	
       	function getFormData(){
       		$("#addProForm").bootstrapValidator('validate');
       		//获取表单验证对象
       		var bootstrapValidator = $("#addProForm").data('bootstrapValidator');  
       		var isPass = bootstrapValidator.isValid();
       		if(!isPass){
       			return null;
       		}
       		//初始化向服务器请求参数
       		var postData ={boardId:null,imei:null,commType:null,boardVersion:null,commVersion:null,simNum:null,equmentId:null};
       		//获取参数
            postData.boardId = $('#addProForm input[name=boardId]').val();
            postData.imei = $('#addProForm input[name=imei]').val();
            postData.commType = $('#addProForm input[name=commType]').val();
            postData.boardVersion = $('#addProForm input[name=boardVersion]').val();
            postData.commVersion = $('#addProForm input[name=commVersion]').val();
            postData.simNum = $('#addProForm input[name=simNum]').val();
            
            var equItem = $("#kcui-combobox-equ-modal").kccombo("getSelectedItem");
        	var equInputVal = $("#kcui-combobox-equ-modal").val();
        	if(equItem && equItem.id && equInputVal){
        		postData.equmentId = equItem.id;
       	 	}
       		return postData;
       	}
       	
       	/*******************表单验证功能*****************************/
       	 $('#addProForm').bootstrapValidator({ 
       	 	message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	boardId: {
	                validators: {
	                	notEmpty: {
                        	message: '主板id必填'
                    	}
	                }
	            },
	            imei: {
	                validators: {
	                	notEmpty: {
                        	message: 'imei必填'
                    	}
	                }
	            },
	            commType: {
	                validators: {
	                	notEmpty: {
                        	message: 'type必填'
                    	}
	                }
	            },
	            boardVersion: {
	                validators: {
	                	notEmpty: {
                        	message: '主板版本号必填'
                    	}
	                }
	            },
	            commVersion: {
	                validators: {
	                	notEmpty: {
                        	message: '通信板版本号必填'
                    	}
	                }
	            },
	            simNum: {
	                validators: {
	                	notEmpty: {
                        	message: 'sim卡号必填'
                    	}
	                }
	            }
	        }
	     });
    });
	
}();



