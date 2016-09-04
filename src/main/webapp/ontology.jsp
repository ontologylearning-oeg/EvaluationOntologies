<!--
    Created on : 09-may-2016
    Author     : dchaves
-->
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="js/loadOntology.js"></script>

<div id="contenido" class="container section">
        <div class="col s12 center-align">
            <h1>Choose an ontology</h1>
        </div>
        <div class="col s12 ">
            <div class="card-panel blue lighten-1">
                <h5>The available ontologies are:</h5>
                    <ul class="collection">
                        <c:forEach var="ontology" items="${applicationScope.ontos}">
                            <li class="collection-item"><div>${ontology.name}<a onclick="loadOntology('${ontology.name}')" class="secondary-content"><i class="material-icons">send</i></a></div></li>
                        </c:forEach>
                    </ul>
            </div>
        </div>
</div>
