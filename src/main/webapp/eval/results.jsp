<%--
  Created by IntelliJ IDEA.
  User: dchavesf
  Date: 1/09/16
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="js/loadGraphics.js"></script>

<div id="contenido" class="container section">
    <div class="col s12 center-align">
        <h1>Results for <c:out value="${sessionScope.ontology.name}"/> Ontology</h1>
    </div>
    <div class="row">
        <c:forEach var="m" items="${sessionScope.measure}">
         <div data-value="${m.measure}" id="${m.name}"></div>
        </c:forEach>
        <div id="chart"></div>

        <div class="center-align col s3 offset-s4">
            Relevant learned terms
        </div>
        <div class="center-align col s8 offset-s1">
            <form action="#">
                <p class="range-field">
                    <input type="range" id="test" min="0" onchange="changeRelevantTerms()" max="<c:out value="${sessionScope.sterms}"/>" value="<c:out value="${sessionScope.value}"/>"/>
                </p>
            </form>
        </div>

        <div id="chart2" style="margin-top: 40px"></div>
    </div>


</div>