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
                <div class="cardsText">It is simply, answer questions about the pertinence of terms from
                    a domain</div>
            </div>
            <div class="card-action">
                <a onclick="changePage('terms.jsp')">Lexical GS</a>
            </div>
        </div>
    </div>


        <div class="col s4 center-align">
            <div class="card blue-grey darken-1">
                <div class="card-content white-text">
                    <span class="card-title">Create a Taxonomic GS</span>
                    <div class="cardsText">Answer questions about the relations between terms</div>
                </div>
                <div class="card-action">
                    <a onclick="changePage('goldstandard.jsp')">Taxonomic GS</a>
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
                    <a onclick="changePage('relations.jsp')">Results</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(".button-collapse").sideNav({closeOnClick: true});
    function changePage(a) {
            var term=[];
            var termhood=[];
            $.get("http://localhost:8084/DrOntoEval/terms.txt", function(data) {
            var lines = data.split('\n');

            for(var t=0; t<lines.length;t++){
                    var d=lines[t].split(';');
                    term.push(d[0]);
                    termhood.push(d[1]);
                }
            });

            $.ajax({
                type: "POST",
                timeout: 50000,
                url: "./LoadOntology",
                data: {"term":term,"termhood":termhood},
                cache: false,
                success: function (data) {
                    Materialize.toast('File has being uploaded!', 2000, 'rounded');
                },
                error: function (){
                    Materialize.toast('Error!', 2000, 'rounded');
                }
            });
           $("#contenido").load(a);
    }
</script>
</html>