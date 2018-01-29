<@override name="head"> </@override> <@override name="body">
<div class="row-fluid">
	<div class="block">
		<div class="navbar navbar-inner block-header">
			<div class="muted pull-left">地震信息列表</div>
		</div>
		<div class="block-content collapse in">
			<div class="span12">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>ID</th>
							<th>地点</th>
							<th>日期</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<#list page.list as jdata>
							<tr>
								<td>${jdata.eventid }</td>
								<td>${jdata.location }</td>
								<td>${jdata.time }</td>
								<td>
									<button class="btn btn-danger"
										onclick="qrCode('${jdata.id}')">详情二维码</button>
										<button class="btn btn-danger"
										onclick="detail('${jdata.id}')">详情</button>
										
										<button class="btn btn-danger"
										onclick="qrCodeContent('${jdata.id}')">内容二维码</button>
										<button class="btn btn-danger"
										onclick="content('${jdata.id}')">内容</button>
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
				共${page.total }条记录共${page.pages }页&nbsp;&nbsp;当前第${page.pageNum }页&nbsp;&nbsp;
			<a href="#" onclick="firstPage('${page.pageNum}','${page.pages }','${type }');">首页</a>&nbsp;&nbsp; 
			<a href="#" onclick="previousPage('${page.pageNum}','${page.pages }','${type }');">上一页</a>&nbsp;&nbsp;
			<a href="#" onclick="nextPage('${page.pageNum}','${page.pages }','${type }');">下一页</a>&nbsp;&nbsp; 
			<a href="#" onclick="lastPage('${page.pageNum}','${page.pages }','${type }');">尾页</a>
			</div>
		</div>
		<!-- /block -->
	</div>
</div>
<script>
function qrCode(jid){
	window.open("/manage/qrCode/"+jid);
	//window.open("/manage/detail?id="+jid);
}
function detail(jid){
	window.open("/manage/detail/"+jid);
	//window.open("/manage/detail?id="+jid);
}

function qrCodeContent(jid){
	window.open("/manage/qrCodeContent/"+jid);
	//window.open("/manage/detail?id="+jid);
}
function content(jid){
	window.open("/manage/content/"+jid);
	//window.open("/manage/detail?id="+jid);
}
//第一页
function firstPage(currentPage,totalPage,type){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		window.location.href="/manage/list?pageNo=1";
		return true;
	}
}
//下一页
function nextPage(currentPage,totalPage,type){
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		window.location.href="/manage/list?pageNo=" + (parseInt(currentPage)+1)+"&type="+type;
		return true;
	}
}
//上一页
function previousPage(currentPage,totalPage,type){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		window.location.href = "/manage/list?pageNo=" + (currentPage-1)+"&type="+type;
		return true;
	}
}

// 尾页
function lastPage(currentPage,totalPage,type){
	
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		window.location.href = "/manage/list?pageNo=" +totalPage+"&type="+type;
		return true;
	}
}
$(function() {
});
</script>
</@override> <@extends name="/common/base.ftl"/>
