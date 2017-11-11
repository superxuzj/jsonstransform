<script src="<@ps.s/>/vendors/jquery-1.9.1.js"></script>
<style>
p{ text-indent:2em; padding:0px; margin:0px; }
</style>
${hello}

 <table class="table table-hover">
  <tr>
   <th>名称</th>
      <th>电话</th>
      <th>部门</th>
     </tr>
  <tbody>
 <#list page.list as item>
<tr>
	<td>${item.name}</td>
	<td>${item.phone}</td>
	<td>${item.department}</td>
</tr>
</#list>
 </tbody>
</table>
共${page.total }条记录共${page.pages }页&nbsp;&nbsp;当前第${page.pageNum }页&nbsp;&nbsp;
			<a href="#" onclick="firstPage('${page.pageNum}','${page.pages }');">首页</a>&nbsp;&nbsp; 
			<a href="#" onclick="previousPage('${page.pageNum}','${page.pages }');">上一页</a>&nbsp;&nbsp;
			<a href="#" onclick="nextPage('${page.pageNum}','${page.pages }');">下一页</a>&nbsp;&nbsp; 
			<a href="#" onclick="lastPage('${page.pageNum}','${page.pages }');">尾页</a>

<script>

//第一页
function firstPage(currentPage,totalPage){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		window.location.href="/page?pageNo=1";
		return true;
	}
}
//下一页
function nextPage(currentPage,totalPage){
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		window.location.href="/page?pageNo=" + (parseInt(currentPage)+1);
		return true;
	}
}
//上一页
function previousPage(currentPage,totalPage){
	if(currentPage == 1){
		alert("已经是第一页数据");
		return false;
	}else{
		window.location.href = "/page?pageNo=" + (currentPage-1);
		return true;
	}
}

// 尾页
function lastPage(currentPage,totalPage){
	
	if(currentPage == totalPage){
		alert("已经是最后一页数据");
		return false;
	}else{
		window.location.href = "/page?pageNo=" +totalPage;
		return true;
	}
}
$(function() {
});
</script>