<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%request.setAttribute("selectedPage", "about");%>
<jsp:include page="header.jsp"/>
<!-- Begin page content -->
<main class="container">
    <H1>Introduction</H1>
    <p>This is a simple web-based catalogue and shopping cart system.</p>
    <h1>Contributor(s)</h1>
    <ul>
        <a href="https://github.com/Sunil-I">
            <li>Sunil Islam</li>
        </a>
    </ul>
    <h1>Warning</h1>
    <p>DO NOT attempt to use any real credit card data with this application.</p>
</main>

<jsp:include page="footer.jsp"/>
