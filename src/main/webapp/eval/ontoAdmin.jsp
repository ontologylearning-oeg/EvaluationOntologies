<%--
  Created by IntelliJ IDEA.
  User: dchavesf
  Date: 16/09/16
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>

<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="contenido" class="container section">
    <div class="row">
        <div class="col s12 center-align">
            <h1>Admin your Ontology</h1>
        </div>
    </div>
    <div class="row" style="margin-left: 70px;">
        <div class="col s3 card-panel teal" style="background-color: rgba(248, 187, 134, 0.2)!important">
            <div class="odata">
                <p class="center-align"><c:out value="${sessionScope.admin.nterms}"/></p>
                <p class="card-title center-align">Terms</p>
            </div>
        </div>
        <div class="col s3 offset-s1 card-panel teal" style="background-color: rgba(248, 187, 134, 0.2)!important">
            <div class="odata">
                <p class="center-align"><c:out value="${sessionScope.admin.ncontrol}"/></p>
                <p class="card-title center-align">Control questions</p>
            </div>
        </div>
        <div class="col s3 offset-s1 card-panel teal" style="background-color: rgba(248, 187, 134, 0.2)!important">
            <div class="odata">
                <p class="center-align"><c:out value="${sessionScope.admin.nevaluators}"/></p>
                <p class="card-title center-align">Distinct experts</p>
            </div>
        </div>
        <div class="col s3 card-panel teal" style="background-color: rgba(248, 187, 134, 0.2)!important">
            <div class="odata">
                <p class="center-align"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${sessionScope.admin.complete}"/></p>
                <p class="card-title center-align">Complete</p>
            </div>
        </div>
        <div class="col s3 offset-s1 card-panel teal" style="background-color: rgba(248, 187, 134, 0.2)!important">
            <div class="odata">
                <p class="center-align"><c:out value="${sessionScope.admin.nevaluations}"/></p>
                <p class="card-title center-align">Total evaluations</p>
            </div>
        </div>
        <div class="col s3 offset-s1 card-panel teal" style="background-color: rgba(248, 187, 134, 0.2)!important">
            <div class="odata">
                <p class="center-align"><c:out value="${sessionScope.admin.tevaluators}"/></p>
                <p class="card-title center-align">Untrusted evaluators</p>
            </div>
        </div>
    </div>

    <div class="row" style="margin-top: 50px; margin-bottom: -20px;" >
        <div class="col s12 center-align">
            <h3>The terms of your ontology</h3>
        </div>
    </div>
    <table class="centered striped" style="margin-top: 50px">
        <thead>
        <tr>
            <th data-field="id">Term</th>
            <th data-field="name">Relevant (Learned)</th>
            <th data-field="price">Control Term</th>
            <th data-field="price">Positive Answers</th>
            <th data-field="price">Negative Answers</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="term" items="${sessionScope.admin.terms}">
            <tr>
                <td>${term.word}</td>
                <td>${term.relevant}</td>
                <td>${term.control}</td>
                <td>${term.yes}</td>
                <td>${term.no}</td>
            </tr>

        </c:forEach>
        </tbody>
    </table>

    <div class="col s2 offset-s5 center-align" style="margin-top: 50px; visibility: hidden">
        <a class="waves-effect waves-light btn" onclick="removeOntology('<c:out value="${sessionScope.admin.ontology}"/>')">Remove ontology</a>
    </div>

</div>