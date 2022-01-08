<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // request set in controller
//    request.setAttribute("selectedPage","about");
%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <H1>Available Items</H1>
    <table class="table">

        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th></th>
        </tr>

        <c:forEach var="item" items="${availableItems}">

        <tr>
            <td>${item.name}</td>
            <td>${item.price}</td>
            <td></td>
            <td>
                <!-- post avoids url encoded parameters -->
                <form action="./home" method="get">
                    <input type="hidden" name="itemName" value="${item.name}">
                    <input type="hidden" name="action" value="deleteItem">
                    <button type="submit" >Delete Item</button>
                </form>
            </td>
        </tr>

        </c:forEach>


</main>




<jsp:include page="footer.jsp" />
