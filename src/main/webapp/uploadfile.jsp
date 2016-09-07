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
</div>

