<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="Styles/site.css" />

<title>Bookstore</title>
</head>
<body>
   <div class="page">
      <div class="header" style="position:relative; width:100%; background-color: rgba(75, 108, 158, 0.8);" align="center">
         <h1>
                    <span style="font-family: Arial; font-size: 1.1em;" >Welcome to Online Bookstore</span>
         </h1>
<!--          <h5><a href="mailto:wwanglsu@gmail.com">by Weijie Wang</a></h5>     background-color:white;display:block  -->
      </div>
      
      <div class="main"> 
      <center>
	      <table width="98%" border="1" cellpadding="0" cellspacing="6" bgcolor="white">
		      <tr>
		         <td align="center" width="15%" height="10%" valign="top"><a href="showwishlist" target="_blank"><canvas id="myCanvas1" style="width:100%;height:100%;background-color:white;display:block"></canvas></a></td>
		         <td align="center" width="70%" valign="top" rowspan="2" bgcolor="white">
		            <iframe id="listbooks" src="listbooks" width="100%" frameborder="0" scrolling="no"></iframe>
		         </td>
		      </tr>
		      <tr>
		         <td height="100%" valign="top">
		            <div><canvas id="myCanvas2" width="200px" height="30px" ></canvas></div>
		            <iframe id="listbrowsebooks" src="listbrowsebooks" width="250" height="500" frameborder="0" scrolling="auto"></iframe>
		         </td>
		      </tr>
	      </table>
      </center>
	   <script type="text/javascript">	      
	         var c=document.getElementById("myCanvas1");
	         var ctx=c.getContext("2d");
	         var grd=ctx.createLinearGradient(0,0,250,50);
	         grd.addColorStop(0,"#AcD600");
	         grd.addColorStop(1,"white");	
	         // Fill with gradient
	         ctx.fillStyle=grd;
	         ctx.fillRect(0,0,400,400);	         
	         ctx.font="40px Arial";
	         // Create gradient
	         var gradient=ctx.createLinearGradient(0,0,c.width,0);
	         gradient.addColorStop("0","magenta");
	         gradient.addColorStop("0.5","blue");
	         gradient.addColorStop("1.0","red");
	         // Fill with gradient
	         ctx.fillStyle=gradient;
	         ctx.fillText("View My Wishlist",3,90,600);	         
	         var c=document.getElementById("myCanvas2");
	         var ctx=c.getContext("2d");
	         ctx.font="20px Arial";
	         ctx.fillText("Your Recent History:",3,20);	         
	   </script>
      </div>
   </div>
</body>
</html>