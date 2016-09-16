<%--
  Created by IntelliJ IDEA.
  User: dchavesf
  Date: 16/09/16
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="js/loadOntology.js"></script>

<div id="contenido" class="container section">
    <div class="col s12 center-align">
        <h1>Choose the ontology you want admin</h1>
    </div>
    <div class="col s12 ">
        <div class="card-panel blue lighten-1">
            <h5>The available ontologies are:</h5>
            <ul class="collection">
                <c:forEach var="ontology" items="${sessionScope.userOnts}">
                    <li class="collection-item"><div>${ontology.name} in the domain of ${ontology.domain}<a onclick="loadAdmin('${ontology.name}');" class="secondary-content"><i class="material-icons">send</i></a></div></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>