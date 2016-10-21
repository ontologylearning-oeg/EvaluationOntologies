<!--
    Created on : 09-may-2016
    Author     : dchaves
-->
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="./css/onto.css">


<div id="contenido" class="container section">
    <div class="col s12 center-align">
        <h1>Instructions for the evaluation</h1>
    </div>
    <div class="col s8 card-panel teal" style="background-color: rgba(248, 187, 134, 0.2)!important">
        <p class="center-align" style="font-size: 120%;">
            Welcome to the web application for evaluating the taxonomic and the lexical layer of an ontology learning process.
            <br>
            The main instructions are:
        </p>
            <ol>
                <li class="liI">When you click on the button "Continue" you are going to see the uploaded ontologies in the database</li>
                <li class="liI">Once you are login into the application you can <strong>upload your learned ontology</strong></li>
                <li class="liI">You can <strong>download the template of the ontology</strong> at the top of this page</li>
                <li class="liI">Each uploaded ontology has a state:</li>
                <ol>
                    <li class="liI"><strong>Lexical evaluation:</strong> you are going to evaluate terms from a domain</li>
                    <li class="liI"><strong>Taxonomic evaluation:</strong> you are going to evaluate taxonomic relations from a domain</li>
                    <li class="liI"><strong>See results:</strong> you can see the final results of the evaluation (recall, precision, Fleiss' Kappa...)</li>
                </ol>
                <li class="liI">Remember to answer correctly the <strong>control questions</strong></li>
                <li class="liI">All of the terms and relations are going to be evaluated for <strong>five different</strong> experts</li>
                <li class="liI">You can not do the taxonomic evaluation if the lexical evaluation has not finished yet </li>
                <li class="liI">You can not see the results if the taxonomic evaluation has not finished yet </li>
                <li class="liI">You can <strong>read our research</strong> about this topic <a href="https://www.researchgate.net/publication/308749599_Methods_and_techniques_for_the_Evaluation_of_Ontology_Learning">at this link</a> (Spanish) or <a href="https://www.dropbox.com/s/a4izlwp0tob9fg2/ontoLearnEval.pdf?dl=0">at this one</a> (English)</li>
            </ol>

    </div>
    <div class="col s2 offset-s5 center-align">
        <a class="waves-effect waves-light btn" onclick="loadOntos()">Continue</a>
    </div>


</div>


