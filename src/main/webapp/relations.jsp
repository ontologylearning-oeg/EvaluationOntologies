<!--
    Created on : 09-may-2016
    Author     : dchaves
-->

<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>

<script src="js/pie.js"></script>



<div id="contenido" class="container section">
    <div class="col s12 center-align">
        <h1>Evaluate relations</h1>
    </div>
    <div class="col s12 center-align" style="margin-top: -20px">
        <h5>Select the gold standard and the ontology</h5>
    </div>

    <div class="row" style="margin-top: 30px">
        <div class="input-field col s4 offset-s4">
            <select class="center-align" onchange="">
                <option value="" disabled selected>Choose your option</option>
                <option value="1">Option 1</option>
                <option value="2">Option 2</option>
                <option value="3">Option 3</option>
            </select>
            <label>Ontology</label>
        </div>
    </div>

    <div class="row">
        <div id="TF" class="col s4 center-align">
            <img class="responsive-img" src="pie.png">
        </div>
        <div id="TF2" class="col s4 center-align">
            <img class="responsive-img" src="pie.png">
        </div>
        <div id="LP" class="col s4 center-align">
            <img class="responsive-img" src="pie.png">
        </div>
    </div>

    <div class="row">
        <div id="TP" class="col s4 center-align">
            <img class="responsive-img" src="pie.png">
        </div>
        <div id="TR" class="col s4 center-align">
            <img class="responsive-img" src="pie.png">
        </div>
        <div id="LR" class="col s4 center-align">
            <img class="responsive-img" src="pie.png">
        </div>
    </div>

</div>

<script>
    $(document).ready(function() {
        $('select').material_select();
    });

</script>
