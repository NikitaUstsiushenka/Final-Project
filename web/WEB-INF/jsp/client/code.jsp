<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:url var="style_url_1" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${style_url_1}"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <c:url var="style_url_2" value="/styles/code_style.css"/>
    <link rel="stylesheet" href="${style_url_2}">
    <title><c:out value="Enter code"/></title>
</head>
<body>
<form action="pharmacy" method="post">
    <div id="input_code">
        <input type="number" class="form-control" name="code" id="code_text" min="1000" max="9999"
               placeholder="Enter code" required>
    </div>
    <div id="button_enter">
        <input type="hidden" name="action" value="registration">
        <button type="submit" class="btn btn-info"><c:out value="Enter"/></button>
    </div>
</form>
<div id="button_back">
    <form action="pharmacy" method="post">
        <input type="hidden" name="action" value="code_back">
        <button type="submit" class="btn btn-info"><c:out value="Back"/></button>
    </form>
</div>
<c:url var="script_url_1" value="https://unpkg.com/sweetalert/dist/sweetalert.min.js"/>
<script src="${script_url_1}"></script>
<c:url var="javascriptURL" value="/js/main.js"/>
<script type="text/javascript" src="${javascriptURL}"></script>
<c:if test="${errorMessage != null}">
    <script>
        showMessage("Attention", "${errorMessage}");
    </script>
</c:if>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<c:url var="script_url_2" value="https://code.jquery.com/jquery-3.3.1.slim.min.js"/>
<script src="${script_url_2}" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<c:url var="script_url_3" value="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"/>
<script src="${script_url_3}" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<c:url var="script_url_4" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"/>
<script src="${script_url_4}" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
