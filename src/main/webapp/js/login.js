/**
 * Created by dchavesf on 1/09/16.
 */

$( document ).ready(function(){
    $(".button-collapse").sideNav();
    $(".download").click();

});
function changePage(a) {
    $("#contenido").load(a);
}

function login(signup) {
    var email = $("#icon_prefix").val();
    var pass = $("#icon_password").val();
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(email) || pass==""){
        swal({
            title: "Oops...",
            text: "Fill the gaps correctly",
            type: "error",
            confirmButtonColor: "#2bbbad",
            confirmButtonText: "OK" });
        return;
    }

    $.ajax({
        beforeSend: function(){
            swal({
                title: "Login...",
                timer: 500,
                showConfirmButton: false });
        },
        type: "POST",
        timeout: 50000,
        url: "/Login",
        data: {"email":email,"pass":pass,"signup":signup},
        cache: false,
        success: function (data) {
            changeToLogin("on");
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "You are not sign up or the user already exits",
                type: "error",
                confirmButtonColor: "#2bbbad",
                confirmButtonText: "OK" });
        }
    });

}

function loadOntos() {
    $.ajax({
        beforeSend: function(){
            swal({
                title: "Load ontologies...",
                timer: 500,
                showConfirmButton: false });
        },
        type: "POST",
        timeout: 50000,
        url: "/LoadOntology",
        cache: false,
        success: function (data) {
            changePage("./eval/index.jsp")
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "There is not ontologies for evaluating",
                type: "error",
                confirmButtonColor: "#2bbbad",
                confirmButtonText: "OK" });
        }
    });
}

function  changeToLogin(flag) {
    if(flag=="on"){
        $("#login").remove();
        $("#loginaux").remove();
        $("#nav-mobile").append('<li><a id="upload" onclick="changePage(\'uploadfile.jsp\');">Upload File</a></li>');
        $("#slide-out").append('<li><a id="auxUpload" onclick="changePage(\'uploadfile.jsp\');">Upload File</a></li>');
        $("#nav-mobile").append('<li><a id="ontos" onclick="loadOntos();">Ontologies</a></li>');
        $("#slide-out").append('<li><a id="auxontos" onclick="loadOntos();">Ontologies</a></li>');
        $("#nav-mobile").append('<li id="logout"><a onclick="changeToLogin(\'off\')">Log out</a></li>');
        $("#slide-out").append('<li id="logoutaux"><a onclick="changeToLogin(\'off\')">Log out</a></li>');
        $("#enlace").removeAttr('href');
        $("#enlace").attr('href', 'instructions.jsp');
        $("#contenido").load("instructions.jsp");
    }
    else if (flag=="off"){
         $.ajax({
             beforeSend: function(){
                 swal({title: "Logout...",
                     timer: 500,
                     showConfirmButton: false });
             },
            type: "POST",
            timeout: 50000,
            url: "/Login",
            cache: false,
             success: function (data) {
                $("#logout").remove();
                $("#logoutaux").remove();
                $("#upload").remove();
                $("#auxUpload").remove();
                $("#ontos").remove();
                $("#auxontos").remove();
                $("#nav-mobile").append('<li><a id="login" onclick="changePage(\'login.jsp\');">Login</a></li>');
                $("#slide-out").append('<li><a id="loginaux" onclick="changePage(\'login.jsp\');">Login</a></li>');
                $("#enlace").removeAttr('onClick');
                $("#enlace").attr('href', 'index.jsp');
                window.location.replace("index.jsp");
            },
            error: function (){
                swal({
                    title: "Oops...",
                    text: "There is a problem with your logout",
                    type: "error",
                    confirmButtonColor: "#2bbbad",
                    confirmButtonText: "OK" });
            }
        });
    }


}

