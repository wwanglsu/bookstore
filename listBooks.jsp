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
<title>Bookstore</title>
<link type="text/css" rel="stylesheet" href="Styles/site.css" />
<script type="text/javascript">
      function autosize(){
         var tag=parent.document.getElementById("listbooks");
         tag.height=document.body.scrollHeight; 
      }
      function addbrowsegoods(){
         window.setTimeout("showbrowsegoods()",500);
      }
      function showbrowsegoods(){
         var tag=parent.document.getElementById("listbrowsebooks");
         tag.src="listbrowsebooks";
      }
</script>
</head>
<body style="background-color:white" onload="autosize()">
<div >   
   <div>
   <center> 
   <table border="0" width="100%" style="word-break:break-all">
      <tr height="30">
         <td align="center" valign="top">
            <c:set var="allgoodslist" value="${requestScope.goodslist}"/>
            <c:if test="${empty allgoodslist}">No books.</c:if>
            <c:if test="${!empty allgoodslist}">
            <table width="99%" border="0" cellspacing="5" cellpadding="5">
               <c:forEach var="onegoodslist" items="${allgoodslist}">
               <tr>
               <c:forEach var="onegoods" items="${onegoodslist}">
               <td>
                  <c:if test="${!empty onegoods}">
                  <div>                  
                  <a href="viewbooks?goodId=${onegoods.id}" target="_blank"><img src="images/goods/${onegoods.goodsViewpic}" width="90" height="75" style="border:0" title="Click for detail" onclick="addbrowsegoods()"></a><br>
                  </div>   
                  Book name:&nbsp${onegoods.goodsName}<br>
                  Book price:&nbsp$${onegoods.goodsPrice}<br>  
                  <a href="viewbooks?goodId=${onegoods.id}" target="_blank">More detail</a>
                  </c:if>  
               </td>
               </c:forEach>
               </tr>
               <tr ><td colspan="4"><hr color="black"></td></tr>
               </c:forEach>
               <tr><td colspan="4">${requestScope.pageBar.pageLink}</td></tr>
            </table>
            </c:if>        
         </td>
      </tr>    
   </table>
   </center>
   </div>
</div>
</body>
</html>