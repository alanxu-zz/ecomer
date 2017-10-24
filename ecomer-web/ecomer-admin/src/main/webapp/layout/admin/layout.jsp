<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page import="java.util.Calendar" %>
 
<%@page contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
  
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
 <c:set var="lang" scope="request" value="${requestScope.locale.language}"/> 
 
 
 <html xmlns="http://www.w3.org/1999/xhtml"> 
 
 
     <head>
     
     
        	 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        	 	<meta charset="utf-8">
    			<title><s:message code="label.storeadministration" text="Store administration" /></title>
    			<meta name="viewport" content="width=device-width, initial-scale=1.0">
    			<meta name="description" content="">
    			<meta name="author" content="">
    			
    			<script src="<c:url value="/resources/js/bootstrap/jquery.js" />"></script>
    			<script src="<c:url value="/resources/js/jquery.friendurl.min.js" />"></script>
    			<script src="<c:url value="/resources/js/ecomer.js" />"></script>
    			<script src="<c:url value="/resources/js/logout.js" />"></script>
    			<link rel="icon" href="<c:url value="/static/favicon.ico"/>"> 
 
  
                <jsp:include page="/common/adminLinks.jsp" />
                
                


 	
 	
 	</head>
 
 	<body class="body">

     <p>&nbsp;</p>

<div class="sm">
	<div class="container">

		<div class="row">

  			<div class="span4"><a class="brand" href="#"><img src="<c:url value="/resources/img/shopizer_small.jpg" />"/></a></div>

  			<div class="span4 offset4">

					<div class="btn-group pull-right">
						<div class="nav-collapse">
												
						
							<ul class="nav pull-right" style="z-index:500000;position:relative">
								<li class="dropdown">
									
									<a data-toggle="dropdown" class="dropdown-toggle" href="#">
										<i class="icon-user"></i> 
										<sec:authentication property="principal.username" />
										<b class="caret"></b>
									</a>
									
									<ul class="dropdown-menu">
										<li><a href="<c:url value="/admin/users/displayUser.html" />"><s:message code="label.my.profile" text="My profile" /></a></li>
										<li class="divider"></li>
										<li>
											<c:url value="/admin/logout" var="logoutUrl"/>
											<a href="javascript:logout()"><s:message code="button.label.logout" text="Logout" /></a>
										</li>
									</ul>
									
								</li>
						   </ul>
						   
						   <c:if test="${fn:length(requestScope.ADMIN_STORE.languages)>1}">
						   	<ul class="nav pull-right" style="z-index:500000;position:relative">
								<li class="dropdown">
									
									<a data-toggle="dropdown" class="dropdown-toggle" href="#">
										<i class="icon-globe"></i> 
											<s:message code="label.generic.language" text="Language"/>
										<b class="caret"></b>
									</a>
									
									<ul class="dropdown-menu">
										<c:forEach items="${requestScope.ADMIN_STORE.languages}" var="lang">
											<li><a href="<c:url value="/admin?locale=${lang.code}" />"><s:message code="${lang.code}" text="${lang.code}" /></a></li>
										</c:forEach>
									</ul>
									
								</li>
						   </ul>
						   </c:if>

				
				</div><!--/.nav-collapse -->	

			</div>

  			</div>
		
		</div>
		
	</div>

	
	<div class="row">&nbsp;</div>



	<div class="container"> 
		<div class="row">	
			
			<div class="span3">
				<ul class="nav nav-list">
					  <c:forEach items="${requestScope.MENULIST}" var="menu">
					  			<%-- <sec:authorize access="hasRole('${menu.role}') and fullyAuthenticated"> --%>
					  			<li <c:if test="${activeMenus[menu.code]!=null}"> class="active"</c:if>>
									<a href="<c:url value="${menu.url}" />">
										<i class="${menu.icon}"></i>
											<s:message code="menu.${menu.code}" text="${menu.code}"/>
									</a>
					  			</li>
					  			<%-- </sec:authorize> --%>
					  </c:forEach>
				</ul>
			</div><!-- end span 3 -->
			
			<div class="span9">

				<tiles:insertAttribute name="body"/>

			</div>

		</div>


  
		<hr> 
  
  
		<footer> 
 			<p>&copy; Shopizer 2010-<%=Calendar.getInstance().get(Calendar.YEAR)%></p> 
		</footer> 
  
  
	</div> <!-- /container --> 
	

  
</div>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-button.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-modal.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-tab.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-transition.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-alert.js" />"></script>
    
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-dropdown.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-scrollspy.js" />"></script>
    
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-tooltip.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-popover.js" />"></script>
    
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-collapse.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-carousel.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap-typeahead.js" />"></script>
     


	

     	
     	
     <script>
	
		$(document).ready(function(){ 
			
			
			<c:if test="${fn:length(currentMenu.menus)>0}">
				<c:forEach items="${currentMenu.menus}" var="menu">
					<sec:authorize access="hasRole('${menu.role}') and fullyAuthenticated">
						<c:forEach items="${menu.menus}" var="subMenu">
							<sec:authorize access="hasRole('${subMenu.role}') and fullyAuthenticated">
								$("#${subMenu.code}-link").click(function() {
					  				window.location='${subMenu.url}';
								});
							</sec:authorize>
						</c:forEach>
					</sec:authorize>
				</c:forEach>
			</c:if>

		}); 
		
		
		function checkCode(code, id, url) {
			

			
			$.ajax({
					type: 'POST',
					dataType: "json",
					url: url,
					data: "code="+ code + "&id=" + id,
					success: function(response) { 
						var msg = isc.XMLTools.selectObjects(response, "/response/statusMessage");
						var status = isc.XMLTools.selectObjects(response, "/response/status");
						
						callBackCheckCode(msg,status);

						
					},
					error: function(jqXHR,textStatus,errorThrown) { 
						alert(jqXHR + "-" + textStatus + "-" + errorThrown);
					}
					
			});
			
			
			
		}
	
</script>	
     	
         
 
 	</body>
 
 </html>
 
