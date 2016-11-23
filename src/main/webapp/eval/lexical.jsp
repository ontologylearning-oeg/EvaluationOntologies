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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="js/eval.js"></script>

<div id="contenido" class="container section">
    <div class="col s12 center-align">
        <h1>Which of these terms strictly belong to the <c:out value="${sessionScope.ontology.domain}"/> domain? </h1>
    </div>
    <div data-value="${sessionScope.flag}" id="flag"></div>
    <div class="row">
    <div class="col s5 offset-s4 card-panel teal z-depth-1" style="margin-top:30px;margin-bottom:40px;background-color: rgba(248, 187, 134, 0.2)!important">
        <div class="center-align col s10 offset-s1" style="margin-top: 20px;margin-bottom: 20px;">
            <h5>${fn:length(sessionScope.termsUser)} terms remain</h5>
        </div>
    </div>
    </div>
    <!-- Modal Structure -->
    <div id="modal1" class="modal">
        <div class="modal-content">
            <h4 class="center-align">How should I answer the questions?</h4>
            <p>Here you have some examples of questions you have to answer</p>
            <dl>
                <dt type="disc">Does <u><strong><c:out value="${sessionScope.instructions.noRelevantTerm}"/></strong></u> belong to the <c:out value="${sessionScope.ontology.domain}"/> domain? </dt>
                <dd>You have to answer <u><strong>NO</strong></u>, because <c:out value="${sessionScope.instructions.noRelevantTerm}"/> it is not a term from this domain.</dd>
                <dt type="disc">Does <u><strong><c:out value="${sessionScope.instructions.relevantTerm}"/></strong></u> belong to the <c:out value="${sessionScope.ontology.domain}"/> domain? </dt>
                <dd>You have to answer <u><strong>YES</strong></u>, because <c:out value="${sessionScope.instructions.relevantTerm}"/> references to <c:out value="${sessionScope.instructions.reason}"/>.</dd>
                <dt type="disc">Does <u><strong><c:out value="${sessionScope.instructions.noStrictlyRelevantTerm}"/></strong></u> belong to the <c:out value="${sessionScope.ontology.domain}"/> domain? </dt>
                <dd>You have to answer <u><strong>NO</strong></u>, because <c:out value="${sessionScope.instructions.noStrictlyRelevantTerm}"/> is not strictly a term from this domain.</dd>

            </dl>
            <p>Remember, answer <u><strong>YES</strong></u> only if strictly the term belongs to the <c:out value="${sessionScope.ontology.domain}"/> domain.</p>
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
            <input  class="waves-effect waves-light btn" onClick="sendAnswers('EvaluatedTerms')" type="button" value="Send Terms">
        </div>
        <div class="col s3" style="margin-top: 20px;margin-left: -50px;">
            <a class="waves-effect waves-light btn modal-trigger" id="modal" href="#modal1">How should I answer?</a>
        </div>
    </div>

</div>