<%--
    Document   : list
    Created on : 15-Dec-2016, 23:56:19
    Author     : Matej Sipka
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pageTemplate title="New tire">
    <jsp:attribute name="body">
        <c:if test="${not empty Admin}">   
            <form:form method="post" action="${pageContext.request.contextPath}/tires/create"
                       modelAttribute="tireCreate" cssClass="form-horizontal">
                <div class="form-group ${manufacturer_error?'has-error':''}">
                    <form:label path="manufacturer" cssClass="col-sm-2 control-label">Manufacturer</form:label>
                        <div class="col-sm-10">
                        <form:select path="manufacturer" cssClass="form-control">
                            <c:forEach items="${manufacturers}" var="manuf">
                                <option value="${manuf}">${manuf}</option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="manufacturer" cssClass="help-block"/>
                    </div>
                </div>
                <div class="form-group ${type_error?'has-error':''}">
                    <form:label path="type" cssClass="col-sm-2 control-label">Type</form:label>
                        <div class="col-sm-10">
                        <form:select path="type" cssClass="form-control">
                            <c:forEach items="${seasons}" var="type">
                                <option value="${type}">${type}</option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="type" cssClass="help-block"/>
                    </div>
                </div>
                <button class="btn btn-primary" type="submit">Create tire</button>
            </form:form>
        </c:if>

        <c:if test="${not empty User}">   
            You are not permitted to see this!
        </c:if>
    </jsp:attribute>
</my:pageTemplate>
