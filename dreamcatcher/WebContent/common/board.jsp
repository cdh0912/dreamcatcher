<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.dreamcatcher.util.*"%>
<%@include file="/common/common.jsp"%>
<form id="boardForm" name="boardForm" method="get" action="">
	<input type="hidden" id="act" name="act" value="">
	<input type="hidden" id="page" name="page" value="">
	<input type="hidden" id="viewMode" name="viewMode" value="">
	<input type="hidden" id="searchMode" name="searchMode" value="">
	<input type="hidden" id="searchWord" name="searchWord" value="">
	<input type="hidden" id="categoryMode" name="categoryMode" value="">
</form>    
<c:set var="page" value='<%=NumberCheck.nullToOne(request.getParameter("page")) %>'/>
<c:set var="viewMode" value='<%=StringCheck.nullToBlank(request.getParameter("viewMode")) %>'/>
<c:set var="searchMode" value='<%=StringCheck.nullToBlank(request.getParameter("searchMode")) %>'/>
<c:set var="searchWord" value='<%=Encoder.serverCharToDefaultChar(StringCheck.nullToBlank(request.getParameter("searchWord"))) %>'/>
<c:set var="categoryMode" value='<%=StringCheck.nullToBlank(request.getParameter("categoryMode")) %>'/>

<script>
var page = ${page};
var viewMode = '${viewMode}';
var searchMode = '${searchMode}';
var searchWord = '${searchWord}';
var categoryMode = '${categoryMode}';
</script>    