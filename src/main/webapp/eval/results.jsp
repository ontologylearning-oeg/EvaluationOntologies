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

<link rel="stylesheet" type="text/css" href="./css/tabs.css">


<div id="contenido" class="container section">


    <div class="row">
        <div class="col s12">
            <ul class="tabs z-depth-1" style="background-color: rgba(248, 187, 134, 0.2)!important;">
                <li class="tab col s6">
                    <a class="active" onclick="changePage('./eval/results.jsp')">Measures</a>
                </li>
                <li class="tab col s6">
                    <a onclick="changePage('./eval/fleiss.jsp')">Fleiss' Kappa</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="col s12 offset-s1 card-panel teal z-depth-1-half" style="background-color: rgba(248, 187, 134, 0.2)!important">
        <div class="col s12 center-align">
            <h3>Results for <c:out value="${sessionScope.ontology.name}"/> Ontology</h3>
        </div>
        <div class="row">
            <c:forEach var="m" items="${sessionScope.measure}">
             <div data-value="${m.measure}" id="${m.name}"></div>
            </c:forEach>
            <div class="col s12" id="chart"></div>

            <div class="col s8 offset-s2 card-panel teal z-depth-3" style="margin-top:30px;background-color: rgba(248, 187, 134, 0.2)!important">
                <div class="center-align col s10 offset-s1" style="margin-top: 20px">
                    <h5>Relevant learned terms</h5>
                </div>
                <div class="center-align col s10 offset-s1">
                    <form action="#">
                        <p class="range-field">
                            <input type="range" id="test" min="0" onchange="changeRelevantTerms()" max="<c:out value="${sessionScope.sterms}"/>" value="<c:out value="${sessionScope.value}"/>"/>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>