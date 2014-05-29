<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<!-- JQuery CSS -->
<link rel="stylesheet" href="${ctx }/jquery-ui/css/redmond/jquery-ui.css" type="text/css" />
<!-- jqGrid CSS -->
<link rel="stylesheet" href="${ctx }/jqgrid/css/ui.jqgrid.css" type="text/css" />
 
<!-- The actual JQuery code -->
<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.min.js" ></script>
<!-- The jqGrid language file code-->
<script type="text/javascript" src="${ctx }/jqgrid/js/i18n/grid.locale-cn.js" ></script>
<!-- The JQuery UI code -->
<script type="text/javascript" src="${ctx }/jquery-ui/js/jquery-ui-1.10.4.min.js" ></script>
<!-- The jqGrid language file code-->
<script type="text/javascript" src="${ctx }/jqgrid/js/i18n/grid.locale-cn.js"></script>
<!-- The atual jqGrid code -->
<script type="text/javascript" src="${ctx }/jqgrid/js/jquery.jqGrid.src.js" ></script>
</head>
<body>
<h1>view index</h1>
<table id="list"></table>
<div id="pager"></div>
<script type="text/javascript">
$('#list').jqGrid({
	url:'data',
	datatype:'json',
	colNames:['pk_user','user name'],
	colModel:[
	          {name:'pk_user',index:'pk_user',width:200},
	          {name:'vusername',index:'vusername',width:200}
	],
	rowNum:5,
	rowList:[5,10,15],
	pager:'#pager',
	sortname:'pk_user',
	viewrecords:true,
	sortorder:'desc',
	caption:'json example',
	multiselect:true,
	recordpos:'left',
	multiboxonly:true,
	autowidth:true,
	shrinkToFit:false
});
$('#list').jqGrid('navGrid','#pager',{edit:false,add:false,del:false});
</script>
</body>
</html>