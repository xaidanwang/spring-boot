function modify(rows){
    console.log('modify',rows);
    rows=rows.split(',');
    var postData ={};
    postData['COMMUNICATION_ID']=rows[1];
    postData['BOARD_ID']=rows[2];
    console.log('postData',postData);
    if(postData){
        //发送请求
        $.ajax({
            url:_common.rootPath+"api/shipment/resetTcp",
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
                }else{
                    _common.showInfoModal(resData.messageCode);
                }
            }
        });
    }
}
!function(){
	 $(document).ready(function(){
         $("#jqGrid").jqGrid({
             url: _common.rootPath + 'api/shipment/list.json',
             datatype: "json",
             mtype:"POST",
             contentType : "application/json",
             colModel: [
                 { label: 'id', name: 'ID', width: 20,hidden:true },
                 { label: '通信板id', name: 'COMMUNICATION_ID', width: 40},
                 { label: '主板id', name: 'BOARD_ID', width: 40 },
                 { label: '通信板版本', name: 'COMMBOARDVERSION', width: 40 },
                 { label: '登录时间', name: 'LAST_LOGIN_TIME', width: 50 },
                 { label: '登录状态', name: 'LOGIN_STATUS', width: 40, formatter: function(value, options, row){
					if(value === 1){
						return '<span class="label label-success">在线</span>'
					}else{
						return '<span class="label label-warning">离线</span>';
					}
				}},
                 { label: '连接服务器', name: 'ADDRESS', width: 80 },
                 {name: '操作', index: 'operate',width: 40,
                     formatter: function (value, grid, rows, state) {
                         // return '<a href="javascript:void(0);" style="color:#f60" onclick="modify(\''+ rows.id+ '\');">OK</a>';
                         return '<button type="button" class="btn btn-success" onclick="modify(\''+ rows+ '\')";>OK</button>'
                     }
                 }

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
                 cellNames:"ID,COMMUNICATION_ID,BOARD_ID,COMMBOARDVERSION,LAST_LOGIN_TIME,LOGIN_STATUS,ADDRESS"
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
		 setInterval(function(){
		     console.log('刷新reloadGrid')
             $("#jqGrid").trigger("reloadGrid");
             //初始化表格
         },5000);


		 /*//ok发送请求
		 $("#okBtn").click(function(){
			 		var postData ={};
	                var id=$('#jqGrid').jqGrid('getGridParam',"selrow");
	                if(id==undefined||id==null){
	                    alert("请选择设备");
	                    return;
	                }
	                var rowData = $('#jqGrid').jqGrid('getRowData',id);
	                postData['COMMUNICATION_ID']=rowData['COMMUNICATION_ID'];
	                postData['BOARD_ID']=rowData['BOARD_ID'];
	                console.log('postData',postData,rowData);
		       		if(postData){
		       			//发送请求
		       			 $.ajax({
		            		url:_common.rootPath+"api/shipment/resetTcp",
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
		            			}else{
		            				 _common.showInfoModal(resData.messageCode);
		            			}        			
		            		}
		            	}); 
		       		}     		
	      	 	
	       	}); 
		 */
       	//更改地址后查询
      	$("#resetBtn").click(function(){
      	 	$("#resetModal").modal("show");
      	 	$("#saveBtn").unbind().bind("click",function(){
      	 		var postData = getFormData();
                var id=$('#jqGrid').jqGrid('getGridParam',"selrow");
                if(id==undefined||id==null){
                    alert("请选择设备");
                    return;
                }
                var rowData = $('#jqGrid').jqGrid('getRowData',id);
                postData['COMMUNICATION_ID']=rowData['COMMUNICATION_ID'];
                postData['BOARD_ID']=rowData['BOARD_ID'];
                console.log('postData',postData,rowData);
	       		if(postData){
	       			//发送请求
	       			 $.ajax({
	            		url:_common.rootPath+"api/shipment/resetTcp",
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
	            				$("#resetModal").modal("hide");
	            			}else{
	            				 _common.showInfoModal(resData.messageCode);
	            			}        			
	            		}
	            	}); 
	       		}     		
      	 	});
       	}); 
       	
       	
       /*	//模态框关闭事件，清空表单
       	$("#resetModal").on('hide.bs.modal',function(){
       		$("#resetAddressFrom")[0].reset();//表单重置
       		$("#resetAddressFrom").data('bootstrapValidator').resetForm(true);//表单验证重置
       		$("#bigToken").text("");
       	});*/
       	
       	function getFormData(){
       		$("#resetAddressFrom").bootstrapValidator('validate');
       		//获取表单验证对象
       		var bootstrapValidator = $("#resetAddressFrom").data('bootstrapValidator');  
       		var isPass = bootstrapValidator.isValid();
       		if(!isPass){
       			return null;
       		}
       		//初始化向服务器请求参数
       		var postData ={resetAddress:null};
       		//获取参数
            postData.resetAddress = $('#resetAddressFrom input[name=resetAddress]').val();
       		return postData;
       	}
       	
       	/*******************表单验证功能*****************************/
       	 /*$('#resetAddressFrom').bootstrapValidator({ 
       	 	message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	
	            resetAddress: {
	                validators: {
	                	notEmpty: {
                        	message: '地址必填'
                    	}
	                }
	            }
	        }
	     });*/
    });
	
}();



