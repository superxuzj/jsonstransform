<!DOCTYPE html>
<html>
<head lang="en">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <meta charset="UTF-8">
    <title></title>
    <style>
        *{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font:14px microsoft yahei;
        }
        div.container{
            width: 100%;
        }
        div.container .section{
            width: 95%;
            margin: 40px auto 0;
        }
        div.container .section .title{
            font:900 36px 宋体;
            color: red;
            margin: 0 auto;
            text-align: center;
        }
        div.container .section section{
            text-align: center;
            margin: 10px auto;
            font-size: 20px;
        }
        div.container .section .top,div.container .section .bottom{
            padding: 5px 5px;
            border-bottom: 2px solid red;
            height: 34px;
            line-height: 32px;
            overflow: hidden;
            font-size: 18px;
        }
        div.container .section .top span, div.container .section .bottom span{
            font-size: 16px;
        }
        .fl{
            float: left;
            text-indent: 0em;
        }
        .fr{
            float: right;
            text-indent: 0em;
        }
       
        div.container .section h3{
            margin: 20px auto;
            text-align: center;
            font:700 24px microsoft yahei;
        }
         p{
         text-indent: 2em;
          font:18px Georgia;
          margin:5px auto 15px;
        }
        div.container .section .content{
            text-indent: 2em;
            font:18px Georgia;
            margin:5px auto 15px;
        }
        div.container .section .bottom{
            border:0 none;
            border-top: 2px solid red;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="section">
        <h1 class="title">地震局值班信息</h1>
        <section>第x期</section>
        <p class="top">
            <span class="left fl">地震局办公室</span>
            <span class="right fr">${jdata.time }</span>
        </p>
        <h3>${jdata.title }</h3>
        <p class="content">
${jdata.content }
        </p>
        <p class="bottom">
            <span class="left fl">编辑：薛晨钟</span>
            <span class="right fr">签发：康小林</span>
        </p>
    </div>
</div>
</body>
</html>