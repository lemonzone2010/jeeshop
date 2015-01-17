<%@page import="net.jeeshop.services.manage.catalog.bean.Catalog"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/resource/common_html_meat.jsp"%>
	<%@ include file="/manage/system/common.jsp"%>
	<%@ include file="/resource/common_html_validator.jsp"%>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/jquery-jquery-ui/themes/base/jquery.ui.all.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/uploadify/uploadify.css"  type="text/css">
	<script src="<%=request.getContextPath() %>/resource/layer/layer.min.js"></script>
	<script src="<%=request.getContextPath() %>/resource/js/ajaxfileupload.js"></script>	 
	<script type="text/javascript" src="<%=request.getContextPath() %>/resource/uploadify/jquery.uploadify.min.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/kindeditor-min.js"></script>
	<script charset="utf-8" src="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/lang/zh_CN.js"></script>
	<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.core.js"></script>
	<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.widget.js"></script>
	<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.tabs.js"></script>
	<style>
		#insertOrUpdateMsg{
			border: 0px solid #aaa;margin: 0px;position: fixed;top: 0;width: 100%;
			background-color: #d1d1d1;display: none;height: 30px;z-index: 9999;font-size: 18px;color: red;
		}
	</style>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/product/productEdit.js"></script>
</head>
<body>
	<div id="uploadDiv" style="width:420px; height:260px; padding:20px; border:1px solid #ccc; background-color:#eee; display: none;">
		<input type="file" id="uploadimage" name="uploadimage" onchange="ajaxupload();" />
		<input type="hidden" id="hideUpload" />
		<input type="button" value="确定" onclick="selectImage();" />
	</div>
	<s:form action="product" id="form" name="form" namespace="/manage" theme="simple" enctype="multipart/form-data" method="post">
		<div class="navbar navbar-inverse" >
			<div id="insertOrUpdateMsg">
				<s:property value="#session.insertOrUpdateMsg"/>
				<%request.getSession().setAttribute("insertOrUpdateMsg", "");//列表页面进行编辑文章的时候,需要清空信息 %>
			</div>
		</div>
		<span id="pifeSpan" class="input-group-addon" style="display:none"><%=SystemManager.systemSetting.getImageRootPath()%></span>
		<input type="hidden" value="<s:property value="e.id"/>" id="productID"/>
		<input type="hidden" value="<s:property value="e.catalogID"/>" id="catalogID"/>
		<div style="text-align: center;">
			<div id="updateMsg"><font color='red'><s:property value="updateMsg" /></font></div>
			<s:if test="e.id=='' or e.id==null">
				<button method="product!insert.action" class="btn btn-success">
					<i class="icon-ok icon-white"></i> 新增
				</button>
			</s:if> 
			<s:else>
				商品ID：<span class="badge badge-success"><s:property value="e.id"/></span>
				<s:if test="e.activityID!=null">
					活动ID：<span class="badge badge-success"><s:property value="e.activityID"/></span>
				</s:if>
				<button method="product!update.action" class="btn btn-success">
					<i class="icon-ok icon-white"></i> 保存
				</button>
				<s:if test="e.status!=2">
					<s:a method="updateUpProduct" cssClass="btn btn-warning" onclick="return confirm(\"确定上架商品吗?\");">
						<s:param name="e.id" value="e.id"/>
						<i class="icon-arrow-up icon-white"></i> 上架
					</s:a>
				</s:if>
				<s:else>
					<s:a method="updateDownProduct" cssClass="btn btn-warning" onclick="return confirm(\"确定下架商品吗?\");">
						<s:param name="e.id" value="e.id"/>
						<i class="icon-arrow-down icon-white"></i> 下架
					</s:a>
				</s:else>
				
				<a class="btn btn-info" target="_blank" href="<%=SystemManager.systemSetting.getWww()%>/product/<s:property value="e.id"/>.html">
				<i class="icon-eye-open icon-white"></i> 查看</a>
				<a target="_blank" href="<%=SystemManager.systemSetting.getWww()%>/freemarker!create.action?method=staticProductByID&id=<s:property value="e.id"/>" class="btn btn-warning">
				<i class="icon-refresh icon-white"></i> 静态化</a>
				
			</s:else>
		</div>
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">基本信息</a></li>
				<li><a href="#tabs-2">商品介绍</a></li>
				<li><a href="#tabs-3">商品图片</a></li>
				<li><a href="#tabs-4">商品属性</a></li>
				<li><a href="#tabs-5">商品参数</a></li>
				<li><a href="#tabs-6">商品规格</a></li>
				<li><a href="#tabs-7">绑定商品赠品</a></li>
			</ul>
			<div id="tabs-1">
				<table class="table table-condensed">
							<tr style="display: none;">
								<td>id</td>
								<td><s:hidden name="e.id" label="id" id="id"/></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td style="text-align: right;">名称</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield name="e.name" data-rule="商品名称;required;name;length[0~44];" size="44" maxlength="44" style="width: 80%;"
										id="name" /></td>
							</tr>
							<tr>
								<td style="text-align: right;">类别</td>
								<td colspan="1">
									<%
									application.setAttribute("catalogs", SystemManager.catalogs);
									%>
									<select onchange="catalogChange(this)" name="e.catalogID" id="catalogSelect">
										<option></option>
										<s:iterator value="#application.catalogs">
											<option pid="0" value="<s:property value="id"/>"><font color='red'><s:property value="name"/></font></option>
											<s:iterator value="children">
												<option value="<s:property value="id"/>">&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="name"/></option>
											</s:iterator>
										</s:iterator>
									</select>(请选择子类别)
								</td>
								<td style="text-align: right;">单位</td>
								<td colspan="1">
									<s:select list="#{'item':'件'}" id="unit" name="e.unit" 
										listKey="key" listValue="value"  />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">简介</td>   
								<td style="text-align: left;" colspan="3">
									<s:textarea name="e.introduce" rows="3" cols="600" cssStyle="width:800px;" id="introduce" 
									data-rule="商品简介;required;introduce;length[4~500];"/>
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">主图</td>   
								<td style="text-align: left;" colspan="3">
									<input type="button" value="选择图片" onclick="test();" class="btn btn-success"/>
									<s:textfield type="text" id="picture" name="e.picture" ccc="imagesInput" style="width: 600px;" data-rule="小图;required;maxPicture;"/>
									<s:if test="e.picture!=null">
										<a target="_blank" href="<%=SystemManager.systemSetting.getImageRootPath()%><s:property value="e.picture" />">
											<img style="max-width: 50px;max-height: 50px;" alt="" src="<%=SystemManager.systemSetting.getImageRootPath()%><s:property value="e.picture" />">
										</a>
									</s:if>
								</td>
							</tr>
							<tr>
								<td style="text-align: right;">定价</td>
								<td style="text-align: left;"><s:textfield name="e.price" data-rule="定价;required;price;" size="10" maxlength="10"
										id="price" /></td>
								<td style="text-align: right;">现价</td>
								<td style="text-align: left;"><s:textfield name="e.nowPrice" data-rule="现价;required;nowPrice;" size="10" maxlength="10" 
										id="nowPrice" /></td>
							</tr>
							<tr>
								<td style="text-align: right;">库存</td>
								<td style="text-align: left;"><s:textfield name="e.stock" data-rule="库存;required;integer;stock;" 
										id="stock" /></td>
								<td style="text-align: right;">销量</td>
								<td style="text-align: left;"><s:textfield name="e.sellcount" data-rule="销量;required;integer;sellcount;" 
										id="sellcount" /></td>
							</tr>
							<tr>
								<td style="text-align: right;">是否新品</td>
								<td style="text-align: left;">
									<s:select list="#{'n':'否','y':'是'}" id="isnew" name="e.isnew" 
										listKey="key" listValue="value"  />
								</td>
								<td style="text-align: right;">是否特价</td>
								<td style="text-align: left;">
									<s:select list="#{'n':'否','y':'是'}" id="pid" name="e.sale" 
										listKey="key" listValue="value"  />
								</td>
							</tr>
							
							<tr>
								<td style="text-align: right;" nowrap="nowrap">送积分</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.score" id="score" maxlength="20" data-rule="销量;required;integer;score;"/>
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" nowrap="nowrap">页面标题</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.title" maxlength="300" size="300" style="width: 80%;" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" nowrap="nowrap">页面描述</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.description" maxlength="300" size="300" style="width: 80%;" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" nowrap="nowrap">页面关键字</td>
								<td style="text-align: left;" colspan="3">
									<s:textfield type="text" name="e.keywords" maxlength="300" size="300" style="width: 80%;" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" nowrap="nowrap">其他信息</td>
								<td style="text-align: left;" colspan="3">
									录入人：<a style="text-decoration: underline;" target="_blank" href="user!show?account=<s:property value="e.createAccount"/>"><s:property value="e.createAccount"/></a>
									录入时间：<s:property value="e.createtime"/><br>
									最后修改人：<a style="text-decoration: underline;" target="_blank" href="user!show?account=<s:property value="e.createAccount"/>"><s:property value="e.updateAccount"/></a>
									最后修改时间：<s:property value="e.updatetime"/>
								</td>
							</tr>
						</table>
			</div>
			<div id="tabs-2">
				<s:textarea data-rule="商品介绍;required;productHTML;" id="productHTML" name="e.productHTML" style="width:100%;height:500px;visibility:hidden;"></s:textarea>
			</div>
			<div id="tabs-3">
				<div>
					<h4><div class="alert alert-info">图片列表</div></h4>
					<table class="table table-bordered">
						<tr>
							<td colspan="11">
								<input style="display: none;" onclick="addTrFunc();" value="添加" class="btn btn-warning" type="button"/>
								<s:submit method="deleteImageByImgPaths" onclick="return deleteImageByImgPaths();"
											value="删除" cssClass="btn btn-primary"/>
							</td>
						</tr>
						<tr style="background-color: #dff0d8">
							<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
							<th>图片地址</th>
						</tr>
						<s:iterator value="e.imagesList" var="img">
							<tr>
								<td><input type="checkbox" name="imagePaths"
										value="<s:property value="img"/>" /></td>
								<td>
									<a href="<%=SystemManager.systemSetting.getImageRootPath()%><s:property value="img"/>" target="_blank">
										<img style="max-width: 100px;max-height: 100px;" alt="" src="<%=SystemManager.systemSetting.getImageRootPath()%><s:property value="img"/>">
									</a>
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
				<br>
				<table class="table table-bordered">
					<tr style="background-color: #dff0d8">
						<td>文件</td>
					</tr>
					<tr id="firstTr">
						<td>
							<%for(int i=0;i<10;i++){ %>
							<div>
								<input type="button" id="browseImage" value="浏览图片" class="btn btn-warning"/>
								<input type="text" ccc="imagesInput" name="e.images" style="width: 80%;" />
							</div>
							<%} %>
						</td>
					</tr>
				</table>
			</div>
			<!-- 商品属性 -->
			<div id="tabs-4">
				<table class="table table-bordered">
					<s:iterator value="e.attrList" var="item">
						<tr>
							<td nowrap="nowrap" style="text-align: right;"><s:property value="name"/></td>
							<td>
								<s:select list="attrList" id="attrSelectIds" name="e.attrSelectIds"  value="selectedID"
						headerKey="" headerValue="--请选择--"
											listKey="id" listValue="name"  />
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<!-- 商品参数 -->
			<div id="tabs-5">
				<table class="table">
					<s:iterator value="e.parameterList" var="item">
						<tr>
							<th style="display: none;"><s:hidden name="id"/></th>
							<th style="text-align: right;"><s:property value="name"/></th>
							<th><s:textfield name="parameterValue"/></th>
						</tr>
					</s:iterator>
				</table>
			</div>
			<!-- 商品规格 -->
			<div id="tabs-6">
				<table class="table">
					<tr>
						<th style="display: none;">id</th>
						<th>尺寸</th>
						<th>颜色</th>
						<th>规格库存数</th>
						<th>价格</th>
						<th>是否显示</th>
					</tr>
					<s:if test="e.specList!=null and 1==1">
						<s:iterator value="e.specList" var="item" status="stat">
							<tr>
								<th style="display: none;"><s:hidden name="e.specList[%{#stat.index}].id"/></th>
								<th><s:textfield name="e.specList[%{#stat.index}].specSize" cssClass="search-query input-small"/></th>
								<th><s:textfield name="e.specList[%{#stat.index}].specColor" cssClass="search-query input-small"/></th>
								<th><s:textfield name="e.specList[%{#stat.index}].specStock" cssClass="search-query input-small"/></th>
								<th><s:textfield name="e.specList[%{#stat.index}].specPrice" cssClass="search-query input-small"/></th>
								<th><s:select name="e.specList[%{#stat.index}].specStatus" list="#{'n':'不显示','y':'显示'}" cssClass="search-query input-small"/></th>
							</tr>
						</s:iterator>
					</s:if>
					<s:else>
						<%for(int i=0;i<10;i++){ %>
							<tr>
								<th style="display: none;"><s:hidden name="id"/></th>
								<th><s:textfield name="e.specArray.specColor" cssClass="search-query input-small"/></th>
								<th><s:textfield name="e.specArray.specSize" cssClass="search-query input-small"/></th>
								<th><s:textfield name="e.specArray.specStock" cssClass="search-query input-small"/></th>
								<th><s:textfield name="e.specArray.specPrice" cssClass="search-query input-small"/></th>
								<th><s:select name="e.specArray.specStatus" list="#{'n':'不显示','y':'显示'}" cssClass="search-query input-small"/></th>
							</tr>
						<%} %>
					</s:else>
				</table>
			</div>
			<div id="tabs-7">
				商品赠品:
				<s:select list="giftList" headerKey="" headerValue="" listKey="id" listValue="giftName" name="e.giftID"></s:select>
			</div>
		</div>
	</s:form>
</body>
</html>
