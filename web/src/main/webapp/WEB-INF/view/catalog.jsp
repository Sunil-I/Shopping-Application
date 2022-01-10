<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%request.setAttribute("selectedPage", "admin");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Available Items</H1>
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
    <a href="./viewCreateItem"><p>Create Item</p></a>
    <table class="table">

        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th></th>
        </tr>

        <c:forEach var="item" items="${availableItems}">

        <tr>
            <td>${item.name}</td>
            <td>${item.price}</td>
            <td>${item.quantity}</td>
            <td></td>
            <td>
                <!-- post avoids url encoded parameters -->
                <form action="./viewModifyItem" method="get">
                    <input type="hidden" name="itemName" value="${item.name}">
                    <input type="hidden" name="action" value="view">
                    <button type="submit">View Item</button>
                </form>
                <form action="./viewModifyItem" method="get">
                    <input type="hidden" name="itemName" value="${item.name}">
                    <input type="hidden" name="action" value="update">
                    <button type="submit">Edit Item</button>
                </form>
                <form action="./viewModifyItem" method="get">
                    <input type="hidden" name="itemName" value="${item.name}">
                    <input type="hidden" name="action" value="delete">
                    <button type="submit">Delete Item</button>
                </form>
            </td>
        </tr>

        </c:forEach>


</main>


<jsp:include page="footer.jsp"/>
