<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <head>
        <title>地震局值班信息管理系统</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <!-- Bootstrap -->
        <link href="<@ps.s/>/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <link href="<@ps.s/>/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
        <link href="<@ps.s/>/assets/styles.css" rel="stylesheet" media="screen">
        <link href="<@ps.s/>/assets/DT_bootstrap.css" rel="stylesheet" media="screen">
<!--[if lt IE 9]>
<script src="<@ps.s/>/vendors/html5shiv.min.js"></script> 
<script src="<@ps.s/>/vendors/respond.min.js"></script> 
<![endif]-->
<style type="text/css">
 .wid{
 background-color: #ffffff;
 	width:280px;
 }
.activeli{
        background-color: #e0e0d1;
    }

</style>
<@block name="head"></@block>
    </head>
    <body>
    	<div class="navbar navbar-fixed-top">
    		<#include "/include/top.ftl"/>
    	</div>
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span3" id="sidebar">
                   <#include "/include/left_menu.ftl"/>
                </div>
                
                <!--/span-->
                <div class="span9" id="content">
                    <@block name="body"></@block>
            	</div>
            <hr>
        </div>
        <!--/.fluid-container-->
<script src="<@ps.s/>/vendors/jquery-1.9.1.js"></script>
<script src="<@ps.s/>/bootstrap/js/bootstrap.min.js"></script>
<script src="<@ps.s/>/assets/scripts.js"></script>
<script language="javascript">
var t = null;
t = setTimeout(time,1000);//开始执行
function time()
{
  clearTimeout(t);//清除定时器
  dt = new Date();
  var year = dt.getFullYear();
  var month = dt.getMonth()+1;
  var day = dt.getDate();
  var h=dt.getHours();
  var m=dt.getMinutes();
  var s=dt.getSeconds();
  $("#timeshow").text(""+year+"年"+month+"月"+day+"日"+h+"时"+m+"分"+s+"秒");
  t = setTimeout(time,1000); //设定定时器，循环执行             
} 
</script>
</script>
</body>
</html>