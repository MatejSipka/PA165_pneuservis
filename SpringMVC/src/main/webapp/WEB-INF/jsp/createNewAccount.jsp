<%--
    Document   : create
    Created on : 16-Dec-2016, 18:22:17
    Author     : Ivan Moscovic
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="New person">
    <jsp:attribute name="body">
        <c:if test="${not empty NewUser}">
        <form:form method="post" action="${pageContext.request.contextPath}/createNewAccount"
                   modelAttribute="personCreate" cssClass="form-horizontal">
            <div class="form-group ${personType_error?'has-error':''}">
                <form:input type="hidden" path="personType" value="CLIENT"/>
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
            <div class="form-group ${dateOfBirth_error?'has-error':''}">
                <form:label path="dateOfBirth" cssClass="col-sm-2 control-label">Date of birth(mm/dd/yyyy)</form:label>
                <div class="col-sm-10">
                    <fmt:formatDate value="${person.dateOfBirth}" pattern="dd/MM/yyyy"/>
                    <form:input path="dateOfBirth" id="datepicker" class="date" cssClass="form-control"/>
                    <form:errors path="dateOfBirth" cssClass="help-block"/>
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
            <button class="btn btn-primary" type="submit">Create person</button>
         </form:form>
        </c:if>
    </jsp:attribute>
</my:pageTemplate>