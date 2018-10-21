<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:url var="url_1" value="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${url_1}"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <c:url var="url_2" value="/styles/pharm_style.css"/>
    <link rel="stylesheet" href="${url_2}">
    <c:url var="image_url" value="/images/cross.png"/>
    <link rel="icon" href="${image_url}" type="images/x-icon">
    <title><c:out value="Pharmacist"/></title>
</head>
<body>
<div id="modal_create_drug" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><c:out value="Create Medicine"/></h4>
                <button type="button" class="close" data-dismiss="modal">×</button>
            </div>
            <div class="modal-body">
                <form method="post" action="pharmacy">
                    <div class="form-group">
                        <label for="name"><c:out value="Name:"/></label>
                        <input type="text" class="form-control" pattern="[A-Z]{1}[a-zA-Z-]*" name="name" id="name"
                               maxlength="35" placeholder="Enter medicine name" required>
                    </div>
                    <div class="form-group">
                        <label for="type"><c:out value="Type:"/></label>
                        <input type="text" class="form-control" pattern="[A-Z]{1}[a-z]*" name="type" id="type"
                               maxlength="8"
                               placeholder="Enter medicine type" required>
                    </div>
                    <div class="form-group">
                        <label for="dosage"><c:out value="Dosage(Mg):"/></label>
                        <input type="number" class="form-control" name="dosage" id="dosage" min="1" max="20000"
                               maxlength="5" placeholder="Enter medicine dosage" required>
                    </div>
                    <div class="form-group">
                        <label for="company"><c:out value="Company:"/></label>
                        <input type="text" class="form-control" pattern="[A-Z]{1}[a-z]*" name="company" id="company"
                               maxlength="35" placeholder="Enter medicine company" required>
                    </div>
                    <div class="form-group">
                        <label for="country"><c:out value="Country:"/></label>
                        <input type="text" class="form-control" pattern="[A-Z]{1}[a-z]*" name="country" id="country"
                               maxlength="25" placeholder="Enter company country" required>
                    </div>
                    <div class="form-group">
                        <label for="substance"><c:out value="Substance:"/></label>
                        <input type="text" class="form-control" pattern="[A-Z]{1}[a-z]*" name="substance" id="substance"
                               maxlength="35" placeholder="Enter substance name" required>
                    </div>
                    <div class="form-group">
                        <label for="recipe"><c:out value="Recipe(0-No, 1-Yes):"/></label>
                        <input type="number" class="form-control" name="recipe" id="recipe" max="1" min="0"
                               placeholder="Need a recipe" required>
                    </div>
                    <div class="form-group">
                        <label for="price"><c:out value="Price:"/></label>
                        <input type="text" class="form-control" pattern="[0-9.]*" name="price" id="price"
                               placeholder="Enter medicine price" required>
                    </div>
                    <div class="form-group">
                        <label for="count"><c:out value="Count:"/></label>
                        <input type="number" class="form-control" name="count" id="count" min="1"
                               placeholder="Enter medicine count" required>
                    </div>
                    <div>
                        <input type="hidden" name="action" value="create_drug">
                        <input type="submit" class="btn btn-info" value="Create">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="modal_add_drugs" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><c:out value="Add Medicines"/></h4>
                <button type="button" class="close" data-dismiss="modal">×</button>
            </div>
            <div class="modal-body">
                <form method="post" action="pharmacy">
                    <div class="form-group">
                        <label for="drug_name"><c:out value="Name:"/></label>
                        <input type="text" class="form-control" pattern="[A-Z]{1}[a-zA-Z-]*" name="name" id="drug_name"
                               maxlength="35" placeholder="Enter medicine name" required>
                    </div>
                    <div class="form-group">
                        <label for="drug_dosage"><c:out value="Dosage(Mg):"/></label>
                        <input type="number" class="form-control" name="dosage" id="drug_dosage" min="1" max="20000"
                               maxlength="5" placeholder="Enter medicine dosage" required>
                    </div>
                    <div class="form-group">
                        <label for="add_count"><c:out value="Count:"/></label>
                        <input type="number" class="form-control" name="count" id="add_count" min="1"
                               placeholder="Enter medicine count" required>
                    </div>
                    <div>
                        <input type="hidden" name="action" value="increase_amount">
                        <input type="submit" class="btn btn-info" value="Add">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="wrap">
    <header>
        <div class="title"><c:out value="Online-pharmacy"/></div>
    </header>
</div>
<main>
    <div id="menu_title"><h2>Pharmacist menu</h2></div>
    <div id="admins_button">
        <button class="btn btn-info menu" data-toggle="modal" data-target="#modal_create_drug"
                title="Add new medicine in database"><c:out value="Create Medicine"/></button>
    </div>
    <div id="clients_button">
        <form method="post" action="pharmacy">
            <input type="hidden" name="action" value="show_drugs">
            <button class="btn btn-info menu" title="Show medicines from database"><c:out
                    value="Show Medicines"/></button>
        </form>
    </div>
    <div id="drugs_button">
        <button class="btn btn-info menu" data-toggle="modal" data-target="#modal_add_drugs"
                title="Increase amount of medicines"><c:out value="Add Medicines"/></button>
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
