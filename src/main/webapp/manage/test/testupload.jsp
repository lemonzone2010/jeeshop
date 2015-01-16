<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/manage/system/common.jsp"%>
<script src="<%=request.getContextPath() %>/resource/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	function ajaxupload111() {
		$.ajaxFileUpload({  
	        url: ROOT + '/uploadify.do',  
	        secureuri:false,  
	        fileElementId: 'uploadimage',
	        dataType:'json',
	        success: function (data, status) {
	        	alert(data);
	        },  
	        error: function (data, status, e) {  
	            alert(e.message);
	        }  
	    });  
	}
</script>
<form method="post" enctype="multipart/form-data" action="/uploadify.do">
	<input type="file" style="width:200px;" name="localUrl" />
	<input type="submit" />
</form>

	<input type="file" name="uploadimage" id="uploadimage" onchange="ajaxupload111();" />