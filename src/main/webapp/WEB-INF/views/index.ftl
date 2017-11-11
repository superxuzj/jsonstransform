<@override name="head"> </@override> <@override name="body">
<div class="row-fluid">
	<div class="block">
		<div class="navbar navbar-inner block-header">
			<div class="muted pull-left">爬虫抓取列表</div>
		</div>
		<div class="block-content collapse in">
			<div class="span12">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>序号</th>
							<th>编码</th>
							<th>网站名称</th>
							<th>抓取板块</th>
							<th>抓取链接</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						
							<tr>
								<td>1</td>
								<td>1</td>
								<td>23</td>
								<td>23</td>
								<td><a href="${website.link}" title="${website.link}"
									target="_blank"> 123</a></td>
								<td>
									<button class="btn btn-danger"
										onclick="doSpider('${website.code}')">爬取</button>
								</td>
							</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- /block -->
	</div>


</div>
</@override> <@extends name="/common/base.ftl"/>
