<%-- 
    Document   : edit
    Created on : 12-Dec-2016, 22:59:49
    Author     : Maros Staurovsky
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="Person edit">
    <jsp:attribute name="scripts">
        <script>
            $(function () {
                $("#datepicker").datepicker({
                changeMonth: true,
                changeYear: true,
                yearRange: "-1000:+0",
                maxDate: "+0"});
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="body">
    <c:if test="${not empty Admin}">   
    <form:form method="post" action="${pageContext.request.contextPath}/person/edit/${data.id}"
           modelAttribute="person" cssClass="form-horizontal">
        
        <div class="form-group ${type_error?'has-error':''}">
                <form:label path="type" cssClass="col-sm-2 control-label">Type of Person</form:label>
                    <div class="col-sm-1 typeRadio">
                        <label for="radioEmployee">
                            Employee
                        </label>
                        <form:radiobutton path="type" id="radioEmployee" value="EMPLOYEE"/>
                    </div>
                    <div class="col-sm-1 typeRadio">
                        <label for="radioClient">
                            Client
                        </label>
                        <form:radiobutton path="type" id="radioClient" value="CLIENT"/>
                    </div>
                    <div class="col-sm-8">                        
                        <form:errors path="type" cssClass="help-block"/>
                    </div>
        </div>
        <div class="form-group ${firstname_error?'has-error':''}">
            <form:label path="firstname" cssClass="col-sm-2 control-label">First Name</form:label>
            <div class="col-sm-10">
                <form:input path="firstname" cssClass="form-control"/>
                <form:errors path="firstname" cssClass="help-block"/>
            </div>
        </div>
        
        <div class="form-group ${lastname_error?'has-error':''}">
            <form:label path="surname" cssClass="col-sm-2 control-label">Surname</form:label>
            <div class="col-sm-10">
                <form:input path="surname" cssClass="form-control"/>
                <form:errors path="surname" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${DateOfBirth_error?'has-error':''}">
            <form:label path="DateOfBirth" cssClass="col-sm-2 control-label">Date of Birth</form:label>
            <div class="col-sm-10">
            <fmt:formatDate value="${data.DateOfBirth}" pattern="dd/MM/yyyy"/>
                <form:input path="DateOfBirth" id="datepicker" class="date" cssClass="form-control"/>
                <form:errors path="DateOfBirth" cssClass="help-block"/>
            </div>
        </div>
            
            <div class="form-group ${login_error?'has-error':''}">
            <form:label path="login" cssClass="col-sm-2 control-label">Login</form:label>
            <div class="col-sm-10">
                <form:input path="login" cssClass="form-control"/>
                <form:errors path="login" cssClass="help-block"/>
            </div>
        </div>
            
            
        <div class="form-group ${passwordHash_error?'has-error':''}">
            <form:label path="passwordHash" cssClass="col-sm-2 control-label">Password Hash</form:label>
            <div class="col-sm-10">
                <form:input path="passwordHash" cssClass="form-control"/>
                <form:errors path="passwordHash" cssClass="help-block"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Edit person</button>
</form:form>
    </c:if>
    <c:if test="${not empty User}">   
        This page is off limits for you!
    </c:if>
    </jsp:attribute>
</my:pageTemplate>
