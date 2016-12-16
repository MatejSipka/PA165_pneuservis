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
    <%--<c:if test="${not empty Admin}">--%>   
    <form:form method="post" action="${pageContext.request.contextPath}/person/edit/${data.id}"
           modelAttribute="person" cssClass="form-horizontal">
        
        <div class="form-group ${personType_error?'has-error':''}">
                <form:label path="personType" cssClass="col-sm-2 control-label">Type of Person</form:label>
                    <div class="col-sm-1 typeRadio">
                        <label for="radioEmployee">
                            Employee
                        </label>
                    <c:if test="${data.personType == 'EMPLOYEE'}">
                        <form:radiobutton path="personType" id="radioEmployee" checked = "checked" value="EMPLOYEE"/>
                        </c:if>
                    <c:if test="${data.personType != 'EMPLOYEE'}"><form:radiobutton path="personType" id="radioEmployee" value="EMPLOYEE"/>
                    </c:if>
                    </div>
                    <div class="col-sm-1 typeRadio">
                        <label for="radioClient">
                            Client
                        </label>
                    <c:if test="${data.personType == 'CLIENT'}">
                        <form:radiobutton path="personType" id="radioClient" checked = "checked" value="CLIENT"/>
                    </c:if>
                    <c:if test="${data.personType != 'CLIENT'}">
                        <form:radiobutton path="personType" id="radioClient" value="CLIENT"/>
                    </c:if>
                    </div>
                    <div class="col-sm-8">                        
                        <form:errors path="personType" cssClass="help-block"/>
                    </div>
        </div>
        <div class="form-group ${firstname_error?'has-error':''}">
            <form:label path="firstname" cssClass="col-sm-2 control-label">First Name</form:label>
            <div class="col-sm-10">
                <form:input path="firstname" cssClass="form-control" value="${data.firstname}"/>
                <form:errors path="firstname" cssClass="help-block"/>
            </div>
        </div>
        
        <div class="form-group ${lastname_error?'has-error':''}">
            <form:label path="surname" cssClass="col-sm-2 control-label">Surname</form:label>
            <div class="col-sm-10">
                <form:input path="surname" cssClass="form-control" value="${data.surname}"/>
                <form:errors path="surname" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${dateOfBirth_error?'has-error':''}">
            <form:label path="dateOfBirth" cssClass="col-sm-2 control-label">Date of Birth (mm/dd/yyyy)</form:label>
            <div class="col-sm-10">
            <fmt:formatDate value="${data.dateOfBirth}" pattern="dd/MM/yyyy"/>
                <form:input path="dateOfBirth" id="datepicker" class="date" cssClass="form-control" value="${data.dateOfBirth}"/>
                <form:errors path="dateOfBirth" cssClass="help-block"/>
            </div>
        </div>
            
            <div class="form-group ${login_error?'has-error':''}">
            <form:label path="login" cssClass="col-sm-2 control-label">Login</form:label>
            <div class="col-sm-10">
                <form:input path="login" cssClass="form-control" value="${data.login}"/>
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
    <%--</c:if>--%>
    </jsp:attribute>
</my:pageTemplate>
