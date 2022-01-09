<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>

<!-- Begin page content -->
<main role="main" class="container">
    <%-- handle error message --%>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span>
            </button>
                ${errorMessage}
        </div>
    </c:if>
    <%-- handle message --%>

    <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span>
            </button>
                ${message}
        </div>
    </c:if>
    <div>
        <H1>Create Item </H1>

        <form action="./viewCreateItem" method="POST" enctype="multipart/form-data">
            <table class="table">
                <thead>
                </thead>
                <tbody>
                <tr>
                    <td>Name</td>
                    <td><input type="text" name="name" value="" required/></td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td><input type="text" name="price" value="" required/></td>
                </tr>
                <tr>
                    <td>Quantity</td>
                    <td><input type="text" name="quantity" value="" required/></td>
                </tr>
                </tbody>

            </table>

            <input type="hidden" name="action" value="create"/>
            <button class="btn" type="submit">Create Item</button>
        </form>

        <form action="./catalog">
            <button class="btn" type="submit">Return To Catalog</button>
        </form>

    </div>

</main>

<jsp:include page="footer.jsp"/>