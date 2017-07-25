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
<script src="js/eval.js"></script>

<div id="contenido" class="container section">
    <div class="col s12 center-align">
        <h1>In the context of the <c:out value="${sessionScope.ontology.domain}"/> domain, is the following relations between terms relevant?</h1>
    </div>
    <div data-value="false" id="flag"></div>
    <div class="col s12" style="margin-left: -20px">
        <form action="#">
            <div class="row center-align" style="margin-left: 70px">
                <c:forEach var="relation" items="${sessionScope.relations}">
                    <div class="col s4 offset-s1 card-panel teal center-align" style="background-color: rgba(248, 187, 134, 0.2)!important">
                       <div style="margin-top: 20px; margin-bottom: 20px">
                            ${relation.term1} and ${relation.term2}
                            <p class="center-align">
                                <input type="radio" value="yes" name="${relation.term1};${relation.term2}" id="${relation.term1};${relation.term2}+yes"/>
                                <label for="${relation.term1};${relation.term2}+yes" style="color: #000000"><span>Yes</span></label>
                                <input type="radio" value="no" name="${relation.term1};${relation.term2}" id="${relation.term1};${relation.term2}+no"/>
                                <label for="${relation.term1};${relation.term2}+no" style="color: #000000"><span>No</span></label>
                            </p>
                        </div>
                    </div>

                </c:forEach>
            </div>
        </form>
        <div id="boton" class="col s2 center-align">
            <input  class="waves-effect waves-light btn" onClick="sendAnswers('EvaluatedRelations')" type="button" value="Send Relations">
        </div>
    </div>
</div>
