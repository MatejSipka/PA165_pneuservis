<%-- 
    Document   : logout
    Created on : 15-Dec-2016, 17:12:21
    Author     : Maros Staurovsky
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<% response.setHeader("Refresh", "5;url=login.jsp"); %>

<my:loginTemplate title = "Logout">
<jsp:attribute name="body">
    
    <h1>LOGOUT</h1>
    You have been successfully logged out

</jsp:attribute>
</my:loginTemplate>