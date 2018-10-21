<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:url var="url_1" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${url_1}"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <c:url var="url_2" value="/styles/admin_style.css"/>
    <link rel="stylesheet" href="${url_2}">
    <c:url var="image_url" value="/images/cross.png"/>
    <link rel="icon" href="${image_url}" type="images/x-icon">
    <title><c:out value="Admin"/></title>
</head>
<body>
<div id="wrap">
    <header>
        <div class="title"><c:out value="Online-pharmacy"/></div>
    </header>
</div>
<main>
    <div id="menu_title"><h2>Admin menu</h2></div>
    <div id="admins_button">
        <form action="pharmacy" method="post">
            <input type="hidden" name="action" value="admins">
            <button class="btn btn-info menu"><c:out value="Admins"/></button>
        </form>
    </div>
    <div id="clients_button">
        <form method="post" action="pharmacy">
            <input type="hidden" name="action" value="clients">
            <button class="btn btn-info menu"><c:out value="Clients"/></button>
        </form>
    </div>
    <div id="drugs_button">
        <form method="post" action="pharmacy">
            <input type="hidden" name="action" value="pharms">
            <button class="btn btn-info menu"><c:out value="Pharmacists"/></button>
        </form>
    </div>
    <div id="logout">
        <form method="post" action="pharmacy">
            <input type="hidden" name="action" value="logout">
            <button class="btn btn-info menu"><c:out value="Logout"/></button>
        </form>
    </div>
</main>
<c:url var="script_url_1" value="https://unpkg.com/sweetalert/dist/sweetalert.min.js"/>
<script src="${script_url_1}"></script>
<c:url var="javascriptURL" value="/js/main.js"/>
<script type="text/javascript" src="${javascriptURL}"></script>
<c:if test="${message != null}">
    <script>
        showMessage("", "${message}");
    </script>
</c:if>
<c:if test="${errorMessage != null}">
    <script>
        showMessage("Attention", "${errorMessage}");
    </script>
</c:if>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<c:url var="script_1" value="https://code.jquery.com/jquery-3.3.1.slim.min.js"/>
<script src="${script_1}"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<c:url var="script_2" value="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"/>
<script src="${script_2}"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<c:url var="script_3" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"/>
<script src="${script_3}"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
