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
    <script src="js/libs/highcharts.js"></script>
    <script src="js/libs/blockUI.js"></script>
    <script src="js/login.js"></script>
    <script src="js/libs/sweetalert-master/dist/sweetalert.min.js"></script>

    <link rel="stylesheet" type="text/css" href="js/libs/sweetalert-master/dist/sweetalert.css">
    <link rel="stylesheet" type="text/css" href="./css/onto.css">
    <link rel="stylesheet" type="text/css" href="css/materialize.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>
<nav>
    <div class="nav-wrapper indigo">
        <a id="enlace" href="index.jsp" class="brand-logo">DrOntoEval: Evaluation of Ontology Learning</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a href="files/demo.csv" download class="download">Download dataset template</a></li>
            <li><a id="login" onclick="changePage('login.jsp');">Login/Signup</a></li>
        </ul>
        <ul id="slide-out" class="side-nav">
            <li><a href="files/demo.csv" download class="download">Download dataset template</a></li>
            <li><a id="loginaux" onclick="changePage('login.jsp');">Login/Signup</a></li>
        </ul>
        <a href="#" data-activates="slide-out" class="button-collapse"><i class="material-icons">reorder</i></a>
    </div>
</nav>


<div id="contenido" class="container section">
    <div class="row" style="background-color: white">
        <div class="col s12 center-align" style="margin-bottom: 50px">
            <img src="files/doctor.png" alt="doctor" height="400">
        </div>
        <div class="row">
            <div class="col s4 offset-s2 center-align">
                <a href="http://www.upm.es">
                    <img src="files/upm.gif" alt="upm" height="70">
                </a>
            </div>
            <div class="col s4 center-align">
                <a href="http://www.oeg-upm.net">
                    <img src="files/oeg.png" alt="oeg" height="70">
                </a>
            </div>
        </div>
    </div>

</div>

</body>

</html>