<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% request.setAttribute("selectedPage", "register"); %>
<jsp:include page="header.jsp"/>
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Create a New Account</H1>
    <%-- handle error message --%>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
                ${errorMessage}
        </div>
    </c:if>
    <%-- handle message --%>
    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
                ${message}
        </div>
    </c:if>
    <form action="./register" method="POST">
        <input type="hidden" name="action" value="createNewAccount">
        <p>Username <input type="text" name="username"></input></p>
        <BR>
        <p>Password <input type="password" name="password" required></input></p>
        <p>Re Enter Password <input type="password" name="password2" required></input></p>
        <p>
            <button type="submit">Create New Account</button>
        </p>
    </form>
</main>
<jsp:include page="footer.jsp"/>