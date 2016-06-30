<!--
    Created on : 09-may-2016
    Author     : dchaves
-->
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>


<html>
<head>
    <title>DrOntoEval</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="js/libs/materialize.min.js"></script>


    <link rel="stylesheet" type="text/css" href="./css/onto.css">
    <link rel="stylesheet" type="text/css" href="css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>
<nav>   
    <div class="nav-wrapper indigo">
        <a href="index.jsp" class="brand-logo">DrOntoEval</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a onclick="changePage('goldstandard.jsp');">Taxonomic GS</a></li>
            <li><a onclick="changePage('terms.jsp');">Lexical GS</a></li>
            <li><a onclick="changePage('relations.jsp');">Results</a></li>
        </ul>
        <ul id="slide-out" class="side-nav">
            <li><a onclick="changePage('goldstandard.jsp');">Taxonomic GS</a></li>
            <li><a onclick="changePage('terms.jsp');">Lexical GS</a></li>
            <li><a onclick="changePage('relations.jsp');">Results</a></li>
        </ul>
        <a href="#" data-activates="slide-out" class="button-collapse"><i class="material-icons">reorder</i></a>
    </div>
</nav>


<div id="contenido" class="container section">
    <div class="row">
        <div class="col s12 center-align">
            <h1>Welcome to DrOntoEval</h1>
        </div>
        <div class="col s12 center-align" style="margin-top: -20px">
            <h5>The application for evaluating Ontologies</h5>
        </div>
    </div>
    <div class="row" style="margin-top: 50px;">
    <div class="col s4 center-align">
        <div class="card blue-grey darken-1">
            <div class="card-content white-text">
                <span class="card-title">Create a Lexical GS</span>
                <div class="cardsText">It is simply, answer a question about the pertinence of a term from
                    a domain</div>
            </div>
            <div class="card-action">
                <a onclick="changePage('terms.jsp')">Eval terms</a>
            </div>
        </div>
    </div>


        <div class="col s4 center-align">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">Create a Taxonomic GS</span>
                    <div class="cardsText">Answer a question about the relations between terms</div>
                </div>
                <div class="card-action">
                    <a onclick="changePage('goldstandard.jsp')">Begin to create a GS</a>
                </div>
            </div>
        </div>


        <div class="col s4 center-align">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">Results</span>
                    <div class="cardsText">See the results of the evaluation</div>
                </div>
                <div class="card-action">
                    <a onclick="changePage('relations.jsp')">Eval relations</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(".button-collapse").sideNav({closeOnClick: true});
    function changePage(a) {
           $("#contenido").load(a);
    }
</script>
</html>