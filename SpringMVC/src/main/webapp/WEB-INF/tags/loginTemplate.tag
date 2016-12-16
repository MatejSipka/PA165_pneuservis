<%-- 
    Document   : loginTemplate
    Created on : 12-Dec-2016, 23:00:20
    Author     : Maros Staurovsky
--%>
<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${title}"/></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"  crossorigin="anonymous">
    <jsp:invoke fragment="head"/>
</head>
<body>
<!-- navigation bar -->
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}"><f:message key="navigation.project"/></a>
        </div>
    </div>
</nav>

<div class="container">
    <!-- page title -->
    <c:if test="${not empty title}">
        <div class="page-header">
            <img class="centerImage" src="${pageContext.request.contextPath}/logo.png" height="100" width="200" border="0"/><br>
        </div>
    </c:if>
    
    <!-- page body -->
    <jsp:invoke fragment="body"/>

    <!-- footer -->
    <footer class="footer">
        <p>&copy;&nbsp;<%=java.time.Year.now().toString()%>&nbsp;Group #2</p>
    </footer>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>

<style type="text/css">
    .centerImage
    {
        display:block;
    }
</style>