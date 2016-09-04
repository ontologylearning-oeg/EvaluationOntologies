<%--
  Created by IntelliJ IDEA.
  User: dchavesf
  Date: 2/09/16
  Time: 11:16
  To change this template use File | Settings | File Templates.
--%>

<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="contenido" class="container section">
    <div class="col s12 center-align">
        <h1>Select the evaluation you want to do</h1>
    </div>
    <div class="row">
    <div class="card col s3 offset-s1">
        <div class="card-image waves-effect waves-block waves-light">
            <img class="activator" src="images/eval.png">
        </div>
        <div class="card-content">
            <span class="card-title activator grey-text text-darken-4">Lexical Layer<i class="material-icons right">more_vert</i></span>
            <p><a onclick="">Go to the lexical evaluation of <c:out value="${applicationScope.ontology.name}"/></a></p>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Lexical Layer<i class="material-icons right">close</i></span>
            <p>Resumen de lo que se hace aquí</p>
        </div>
    </div>
    <div class="card col s3 offset-s1">
        <div class="card-image waves-effect waves-block waves-light">
            <img class="activator" src="images/eval.png">
        </div>
        <div class="card-content">
            <span class="card-title activator grey-text text-darken-4">Taxonomic Layer<i class="material-icons right">more_vert</i></span>
            <p><a onclick="">Go to evaluation of <c:out value="${applicationScope.ontology.name}"/></a></p>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Taxonomic Layer<i class="material-icons right">close</i></span>
            <p>Resumen de lo que se hace aquí</p>
        </div>
    </div>

    <div class="card col s3 offset-s1">
        <div class="card-image waves-effect waves-block waves-light">
            <img class="activator" src="images/eval.png">
        </div>
        <div class="card-content">
            <span class="card-title activator grey-text text-darken-4">Results<i class="material-icons right">more_vert</i></span>
            <p><a onclick="">Go to evaluation of <c:out value="${applicationScope.ontology.name}"/></a></p>
        </div>
        <div class="card-reveal">
            <span class="card-title grey-text text-darken-4">Results<i class="material-icons right">close</i></span>
            <p>Resumen de lo que se hace aquí</p>
        </div>
    </div>

    </div>

</div>
