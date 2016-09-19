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
        <h1>Which of these terms strictly belong to the <c:out value="${sessionScope.ontology.domain}"/> domain?</h1>

    </div>
    <!-- Modal Structure -->
    <div id="modal1" class="modal">
        <div class="modal-content">
            <h4 class="center-align">How should I answer the questions?</h4>
            <p>Here you have some examples of questions you have to answer</p>
            <dl>
                <dt type="disc">Does <u><strong>WATER</strong></u> belong to the Computer Graphics domain? </dt>
                <dd>You have to answer <u><strong>NO</strong></u>, because WATER it is not a term from this domain.</dd>
                <dt type="disc">Does <u><strong>CARD</strong></u> belong to the Computer Graphics domain? </dt>
                <dd>You have to answer <u><strong>YES</strong></u>, because CARD references to the graphics cards of the computers.</dd>
                <dt type="disc">Does <u><strong>DATA</strong></u> belong to the Computer Graphics domain? </dt>
                <dd>You have to answer <u><strong>NO</strong></u>, because DATA is not strictly a term from this domain.</dd>

            </dl>
            <p>Remember, answer <u><strong>YES</strong></u> only if strictly the term belongs to the Computer Graphics domain.</p>
        </div>
    </div>
    <div class="col s12" style="margin-left: -20px">
            <form action="#">
                <div class="row center-align">
                    <c:forEach var="term" items="${sessionScope.termsUser}">

                        <div class="col s3 offset-s1 card-panel teal" style="background-color: rgba(248, 187, 134, 0.2)!important">
                        <div class="row" style="margin-left: 20px; margin-top: 8px">
                                   <div class="col s2 center-align" style="margin-right: 42px; margin-top: 35px" > ${term.word}</div>
                                       <div class="col s1 offset-s1 center-align">
                                        <p class="center-align">
                                        <input type="radio" value="yes" name="${term.word}" id="${term.word}+yes"/>
                                        <label for="${term.word}+yes" style="color: #000000"><span>Yes</span></label>
                                       </p>
                                       <p class="center-align">
                                        <input type="radio" value="no" name="${term.word}" id="${term.word}+no"/>
                                        <label for="${term.word}+no" style="color: #000000"><span>No</span></label>
                                        </p>
                                       </div>
                        </div>
                        </div>

                    </c:forEach>
                </div>
            </form>
    </div>
    <div class="row">
        <div id="boton" class="col s3 offset-s4">
            <input  class="waves-effect waves-light btn" onClick="sendAnswers('/EvaluatedTerms')" type="button" value="Send Terms">
        </div>
        <div class="col s3" style="margin-top: 20px;margin-left: -50px;">
            <a class="waves-effect waves-light btn modal-trigger" id="modal" href="#modal1">How should I answer?</a>
        </div>
    </div>

</div>