<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
        <h1>Error!</h1>
        <p>An error has occurred.</p>
        <p>Error: ${error}</p>
        <p>Status: ${status}</p>
        <p>Failed URL: ${requestUrl}</p>
        <p>Exception:  ${exception.message}</p>
        <p>Stack trace:</p>
        <p>${strStackTrace}</p>
</main>

<jsp:include page="footer.jsp" />
