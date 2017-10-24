
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>		

<div class="tabbable">

	<jsp:include page="/common/adminTabs.jsp" />

	<div class="tab-content">
		<div class="tab-pane active">
			<div class="sm-ui-component">
				<c:choose>
					<c:when test="${filterDefinition != null}" >
						<h3><s:message code="label.filter.edit" text="Update filter definition" /></h3>
					</c:when>
					<c:otherwise>
						<h3><s:message code="label.filter.new" text="Create filter definition"/></h3>
					</c:otherwise>
				</c:choose>
				<br>
				
				<c:url var="saveFilterDefinitionUrl" value="/admin/filter_management/save.html" />
				
				<form:form action="${saveFilterDefinitionUrl}" method="POST" commandName="filterDefinition">
					<form:errors path="*" cssClass="alert alert-error" element="div" />
					<c:if test="${success}" >
						<div class="alert alert-success" id="success_message">
							<s:message code="message.success" text="Succeed! " />
						</div>
					</c:if>
					
					<div class="control-group">
						<label><s:message code="label.filter.default" text="Default" /></label>
						<div class="controls">
							<form:checkbox path="default" />
						</div>
					</div>
					
					<div class="control-group">
						<label><s:message code="label.filter.type" text="Value type"/></label>
						<div class="controls">
							<form:select path="type">
								<form:options items="${filterValueType}" />
							</form:select>
						</div>
						<span class="help-inline"><form:errors path="type" cssClass="error" /></span>
					</div>
					
					<div class="control-group">
						<label><s:message code="label.filter.type" text="Mode"/></label>
						<div class="controls">
							<form:select path="mode">
								<form:options items="${filterMode}" />
							</form:select>
						</div>
						<span class="help-inline"><form:errors path="mode" cssClass="error" /></span>
					</div>
					
					<div class="control-group">
                        <label><s:message code="label.filter.label" text="Label"/></label>
                        <div class="controls">
                        	<form:input cssClass="highlight" path="label"/>
                        	<span class="help-inline"><form:errors path="label" cssClass="error" /></span>
                        </div>
                  	</div>
                  	
                  	
					<%--<div class="control-group">
                        <label><s:message code="label.filter.label" text="Option Labels"/></label>
                        <form:errors path="optionLabels" cssClass="alert alert-error" element="div" />
                        <div class="controls">
                        	<c:choose>
                        		<c:when test="${filterDefinition.optionLabels != null}">
                        			<c:forEach items="${filterDefinition.optionLabels}" var="l" varStatus="i">
									    <form:input path="optionLabels[${i.index}]" />
									</c:forEach>
                        		</c:when>
                        		<c:otherwise>
                        			<s:message code="label.none" text="[None]"></s:message>
                        		</c:otherwise>
                        	</c:choose>
	                        
                        </div>
                  	</div> --%>
                  	
                  	<%-- <div class="control-group">
                        <label><s:message code="label.filter.label" text="Option Labels"/></label>
                        <form:errors path="optionLabels" cssClass="alert alert-error" element="div" />
                        <form:input path="optionLabels"/>
                        <div class="controls">
                       		<c:forEach items="${filterDefinition.optionLabels}" var="l" varStatus="i">
                       			<c:set var="labelsVar" value="${labelsVar}${i.first ? '' : ', '} ${l}" />
							</c:forEach>
                        	<input type="text" name="optionLabels" value="${labelsVar}">
                        </div>
                  	</div> --%>
                  	
                  	<div class="control-group">
                        <label><s:message code="label.filter.label" text="Option Labels"/></label>
                        <div class="controls">
	                        <form:errors path="optionLabels" cssClass="alert alert-error" element="div" />
	                        <form:input path="optionLabels"/>
                        </div>
                  	</div>
                  	
                  	<div class="control-group">
                        <label><s:message code="label.filter.value" text="Option Values"/></label>
                        <div class="controls">
	                        <form:errors path="optionValues" cssClass="alert alert-error" element="div" />
	                        <form:input path="optionValues"/>
                        </div>
                  	</div>
                  
                  <button type="submit" class="btn btn-success"><s:message code="button.label.submit" text="Submit"/></button>
				</form:form>
			</div>
		</div>
	</div>

</div>

