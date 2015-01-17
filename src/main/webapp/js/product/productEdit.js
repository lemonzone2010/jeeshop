var	pageii;
function test() {
	pageii = $.layer({
		type: 1,
		title: false,
		area: ['auto', 'auto'],
		border: [0], //去掉默认边框
		closeBtn: [0, false], //去掉默认关闭按钮
		shift: 'left', //从左动画弹出
		page: {
			dom : '#uploadDiv'
		}
	});
}

function ajaxupload() {
	$.ajaxFileUpload({  
        url: ROOT + '/uploadify.do',  
        secureuri:false,  
        fileElementId: 'uploadimage',
        dataType:'json',
        success: function (data, status) {
        	$("#picture").val(data);
        	layer.close(pageii);
        },  
        error: function (data, status, e) {  
            alert("上传文件时发生错误：" + e.message);
        }  
    });  
}


$(document).ready(function() {

	ajaxLoadImgList();
	var url = '<%=request.getContextPath() %>/uploadify.do?id='+$("#id").val();
	//alert(url);
	$("#uploadify").uploadify({
		//'auto'           : false,
       'swf'       	 : '<%=request.getContextPath() %>/resource/uploadify/uploadify.swf',
       'uploader'       : url,//后台处理的请求
       'queueID'        : 'fileQueue',//与下面的id对应
       //'queueSizeLimit' :100,
       //'fileTypeDesc'   : 'rar文件或zip文件',
       //'fileTypeExts' 	 : '*.jpg;*.jpg', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
       //'fileTypeExts'   : '*.rar;*.zip', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc  
       //'fileTypeDesc' : '图片文件' , //出现在上传对话框中的文件类型描述
       //'fileTypeExts' : '*.jpg;*.bmp;*.png;*.gif', //控制可上传文件的扩展名，启用本项时需同时声明filedesc
       'multi'          : true,
       'buttonText'     : '上传',
       onUploadSuccess:function(file, data, response){
			//alert("上传成功,data="+data+",file="+file+",response="+response);      
			ajaxLoadImgList();
       },
       onUploadError:function(file, errorCode, errorMsg) {
    	   alert("上传失败,data="+data+",file="+file+",response="+response);   
       }
 	});
});

//ajax加载内容图片列表
function ajaxLoadImgList(){
	if($("#id").val()==''){
		 $("#fileListDiv").html("");
		 return;
	}
	
	 $("#fileListDiv").html("");
	var _url = "product!ajaxLoadImgList.action?id="+$("#id").val();
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
		  var _tableHtml = "<table class='table table-bordered' style='border:0px solid red;'>";
			  _tableHtml += "<tr style='background-color: #dff0'>";
			  _tableHtml += "<td>图片地址</td><td>设为默认图片</td><td>操作</td>";
			  _tableHtml += "</tr>";
		  $.each(data,function(i,row){
			  _tableHtml += "<tr>";
			  var str = "<a target='_blank' href='"+row+"'>"+row+"</a>";
			  _tableHtml += "<td>"+str+"</td><td><input type='radio' onclick='setProductImageToDefault(\""+row+"\")' name='abcdef123'/></td><td><input type='button' Class='btn btn-danger' value='删除' onclick='deleteImageByProductID(\""+row+"\")'/></td>";
			  _tableHtml += "</tr>";
			  //$("#fileListDiv").append("<a target='_blank' href='"+row+"'>"+row+"</a><br>");
		  });
		  _tableHtml += "</table>";
		  $("#fileListDiv").append(_tableHtml);
	  },
	  dataType: "json",
	  error:function(){
		alert("加载图片列表失败！");
	  }
	});
}

//产品图片设置为默认图片
function setProductImageToDefault(imageUrl){
	var _url = "product!setProductImageToDefault.action?id="+$("#id").val()+"&imageUrl="+imageUrl;
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
		  //alert("设置成功!");
		  $("#showMessage").append("设置成功！").fadeTo(2000, 1, function(){
			   //alert("Animation Done.");
			   $("#showMessage").html("").hide();
		  });
	  },
	  dataType: "text",
	  error:function(){
		alert("设置失败！");
	  }
	});
}

//产品图片设置为默认图片
function deleteImageByProductID(imageUrl){
	if(!confirm("确定删除选择的记录?")){
		return ;
	}
	var _url = "product!deleteImageByProductID.action?id="+$("#id").val()+"&imageUrl="+imageUrl;
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
			  	ajaxLoadImgList();
		  //$("#showMessage").append("删除成功！").fadeTo(2000, 1, function(){
			//   $("#showMessage").html("").hide();
		  //});
		  
	  },
	  dataType: "text",
	  error:function(){
		alert("删除失败！");
	  }
	});
}

KindEditor.ready(function(K) {
	var editor = K.editor({
		fileManagerJson : '<%=request.getContextPath() %>/resource/kindeditor-4.1.7/jsp/file_manager_json.jsp'
	});
	K('input[name=filemanager]').click(function() {
		var imagesInputObj = $(this).parent().children("input[ccc=imagesInput]");
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'image',
				clickFn : function(url, title) {
					//K('#picture').val(url);
					//alert(url);
					imagesInputObj.val(url);
					editor.hideDialog();
					clearRootImagePath(imagesInputObj);//$("#picture"));
				}
			});
		});
	});
	
});


var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="e.productHTML"]', {
		allowFileManager : true
	});
	K('input[name=getHtml]').click(function(e) {
		alert(editor.html());
	});
	K('input[name=isEmpty]').click(function(e) {
		alert(editor.isEmpty());
	});
	K('input[name=getText]').click(function(e) {
		alert(editor.text());
	});
	K('input[name=selectedHtml]').click(function(e) {
		alert(editor.selectedHtml());
	});
	K('input[name=setHtml]').click(function(e) {
		editor.html('<h3>Hello KindEditor</h3>');
	});
	K('input[name=setText]').click(function(e) {
		editor.text('<h3>Hello KindEditor</h3>');
	});
	K('input[name=insertHtml]').click(function(e) {
		editor.insertHtml('<strong>插入HTML</strong>');
	});
	K('input[name=appendHtml]').click(function(e) {
		editor.appendHtml('<strong>添加HTML</strong>');
	});
	K('input[name=clear]').click(function(e) {
		editor.html('');
	});
});

function addTrFunc(){
	var cc = $("#firstTr").clone();
	$("#firstTr").after(cc);
	
	cc.find("a").show();
}

function removeThis(t){
	$(t).parent().parent().remove();
	return false;
}

$(function() {
	$( "#tabs" ).tabs({
		//event: "mouseover"
	});
	if($("#insertOrUpdateMsg").html()!='' && $("#insertOrUpdateMsg").html().trim().length>0){
		$("#insertOrUpdateMsg").slideDown(1000).delay(1500).slideUp(1000);
	}
	
	selectDefaultCatalog();
	
	$("#removePife").click(function(){
		clearRootImagePath();
	});
});
//删除图片主路径
function clearRootImagePath(picInput){
	var _pifeSpan = $("#pifeSpan").text();
	var _imgVal = picInput.val();
	console.log("1===>_imgVal = "+_imgVal);
	//if(_imgVal && _imgVal.length>0 && _imgVal.indexOf(_pifeSpan)==0){
		//picInput.val(_imgVal.substring(_pifeSpan.length));
		console.log("2===>"+_imgVal.indexOf("/attached/"));
		picInput.val(_imgVal.substring(_imgVal.indexOf("/attached/")));
		
	//}
}
function deleteImageByImgPaths(){
	if ($("input:checked").size() == 0) {
		alert("请选择要删除的图片！");
		return false;
	}
	return confirm("确定删除选择的图片吗?");
}

function selectDefaultCatalog(){
	var _catalogID = $("#catalogID").val();
	if(_catalogID!='' && _catalogID>0){
		//$("#catalogSelect").attr("value",_catalogID);
		$("#catalogSelect").val(_catalogID);
	}
}

function catalogChange(obj){
	var _pid = $(obj).find("option:selected").attr("pid");
	if(_pid==0){
		alert("不能选择大类!");
		selectDefaultCatalog();
		return false;
	}
	var _productID = $("#productID").val();
	
	if(confirm("修改商品类别会清空该商品的属性和参数，确认要这样做吗？")){
		$.blockUI({ message: "正在切换商品目录，请稍候...",css: { 
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
        }});
		
		//alert($(obj).val());
		if(_productID==''){
			//alert(3);
			document.form.action = "product!toAdd.action?chanageCatalog=true&catalog="+$(obj).val();
		}else{
			document.form.action = "product!updateProductCatalog.action?e.id="+_productID+"&chanageCatalog=true&catalog="+$(obj).val();
		}
		document.form.submit();
	}else{
		selectDefaultCatalog();
	}
}