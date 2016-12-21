<%--
  Created by IntelliJ IDEA.
  User: dchavesf
  Date: 9/09/16
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div id="contenido" class="container section">
    <div class="row">
        <div class="col s12 center-align">
            <h1>Login to evaluate</h1>
        </div>
    </div>
    <div class="row" style="margin-top: 50px;">
        <form class="col s10 offset-s1" style="margin-left: 165px">
            <div class="row">
                <div class="input-field col s5">
                    <i class="material-icons prefix">account_circle</i>
                    <input id="icon_prefix" type="text" class="validate">
                    <label for="icon_prefix">User</label>
                </div>
                <div class="input-field col s5">
                    <i class="material-icons prefix">person_pin</i>
                    <input id="icon_password" type="password" class="validate">
                    <label for="icon_password">Password</label>
                </div>
            </div>
        </form>
        <div class="row center-align">
            <div class="col s2 offset-s4">
                <a class="waves-effect waves-light btn" onclick="login();">Login</a>
            </div>
            <div class="col s2">
                <a class="waves-effect waves-light btn" onclick="login('true');">Sign up</a>
            </div>
        </div>
    </div>
</div>