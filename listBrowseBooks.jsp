<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recent History</title>
</head>
<body style="background-color:white; margin-top:0">
   <center> 
	   <table border="0" cellspacing="0" cellpadding="0">
	      <tr><td colspan="2"></td></tr>
	      <c:set var="allbrowsegoods" value="${requestScope.browsegoodslist}"/>
	      <c:if test="${!empty allbrowsegoods}">
	      <c:forEach var="onebrowsegoods" items="${allbrowsegoods}">
	      <tr height="25"><td colspan="2" style="padding-left:15">${onebrowsegoods["browsename"]}</td></tr>
	      <tr height="60">
	         <td style="padding-left:15" align="center" width="20"><a href="viewbooks?goodId=${onebrowsegoods['browseid']}" target="_blank"><img src="images/goods/${onebrowsegoods['browseviewpic']}" width="50" height="45" style="border:1 solid;border-color:black" title="Click for detail"></a></td>
	         <td style="padding-left:15;color:gray" valign="bottom">Browse time:<br>${onebrowsegoods["browsetime"]}</td>
	      </tr>
	      <tr><td colspan="2"><hr color="black"></td></tr>
	      </c:forEach>
	      <tr><td align="right" colspan="2"><a href="clearbrowse">Clear history</a>&nbsp;&nbsp;</td></tr>
	      </c:if>
	   </table>       
   </center>
</body>
</html>