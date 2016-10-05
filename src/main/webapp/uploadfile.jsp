<!--
    Created on : 09-may-2016
    Author     : dchaves
-->


<script src="js/uploadFile.js"></script>
<script src="js/libs/materialize.min.js"></script>

<h1 class="row center-align">Upload a file</h1>
<div class="row" style="margin-top: 50px">
     <form action="#">
        <div class="file-field input-field col s8 offset-s2">
            <div class="btn">
                <span>File</span>
                <input type="file" name="file" id="file" multiple>
            </div>
            <div class="file-path-wrapper">
                <input class="file-path validate" type="text">
            </div>
        </div>
     </form>
    <div id="boton" class="col s2">
        <input  class="waves-effect waves-light btn" onClick="subirFichero()" type="button" value="Send">
    </div>


    <div id="modal1" class="modal">
        <div class="modal-content">
            <h4 class="center-align">Please, fill the gaps for the evaluation instructions</h4>
            <div class="row">
                <div class="input-field col s4">
                    <input id="relevant" type="text" class="validate">
                    <label for="relevant">One relevant term</label>
                </div>
                <div class="input-field col s4">
                    <input id="noStrictly" type="text" class="validate">
                    <label for="noStrictly">One no strictly relevant term</label>
                </div>
                <div class="input-field col s4">
                    <input id="noRelevant" type="text" class="validate">
                    <label for="noRelevant">One no relevant term</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input placeholder="because references to..." id="reason" type="text" class="validate">
                    <label for="reason">Reason why the relevant term belongs to the domain</label>
                </div>
            </div>
        </div>
        <div class="col s2 offset-s5 center-align" style="margin-bottom: 30px; margin-top: -10px">
            <a class="waves-effect waves-light btn" onclick="sendInstructions()">Send</a>
        </div>

    </div>

</div>

