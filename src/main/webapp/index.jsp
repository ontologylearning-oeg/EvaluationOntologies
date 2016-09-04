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
        <a id="enlace" href="index.jsp" class="brand-logo">DrOntoEval</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a onclick="changePage('uploadfile.jsp');">Upload File</a></li>
        </ul>
        <ul id="slide-out" class="side-nav">
            <li><a onclick="changePage('uploadfile.jsp');">Upload File</a></li>
        </ul>
        <a href="#" data-activates="slide-out" class="button-collapse"><i class="material-icons">reorder</i></a>
    </div>
</nav>


<div id="contenido" class="container section">
    <div class="row">
        <div class="col s12 center-align">
            <h1>DrOntoEval</h1>
        </div>
    </div>
    <div class="row" style="margin-top: 50px;">
        <form class="col s10 offset-s2 ">
            <div class="row">
                <div class="input-field col s5">
                    <i class="material-icons prefix">account_circle</i>
                    <input id="icon_prefix" type="email" class="validate">
                    <label for="icon_prefix">Email</label>
                </div>
                <div class="input-field col s5">
                    <i class="material-icons prefix">person_pin</i>
                    <input id="icon_password" type="password" class="validate">
                    <label for="icon_password">Password</label>
                </div>
            </div>
        </form>
        <div class="col s2 offset-s5 center-align">
            <a class="waves-effect waves-light btn" onclick="login();">LOGIN</a>
        </div>
    </div>
</div>
</body>
<script>

</script>
</html>