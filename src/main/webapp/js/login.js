/**
 * Created by dchavesf on 1/09/16.
 */

$( document ).ready(function(){
    $(".button-collapse").sideNav();
    $(".download").click();
});

$(document).ajaxStop($.unblockUI);

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
            $.blockUI({ message: null });
        },
        type: "POST",
        timeout: 50000,
        url: "Login",
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

function loadOntos(a) {
    $.ajax({
        beforeSend: function(){
            $.blockUI({ message: null });
        },
        type: "POST",
        timeout: 50000,
        data: {"user": a},
        url: "LoadOntology",
        cache: false,
        success: function (data) {
            changePage(data)
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "There are not ontologies in the database",
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
        $("#nav-mobile").append('<li><a id="admin" onclick="loadOntos(\'name\')">Admin</a></li>');
        $("#slide-out").append('<li><a id="auxAdmin" onclick="loadOntos(\'name\')">Admin</a></li>');
        $("#nav-mobile").append('<li id="logout"><a onclick="changeToLogin(\'off\')">Log out</a></li>');
        $("#slide-out").append('<li id="logoutaux"><a onclick="changeToLogin(\'off\')">Log out</a></li>');
        $("#enlace").removeAttr('href');
        $("#enlace").attr('onClick', 'changePage("instructions.jsp")');
        $("#contenido").load("instructions.jsp");
    }
    else if (flag=="off"){
         $.ajax({
             beforeSend: function(){
                 $.blockUI({ message: null });
             },
            type: "POST",
            timeout: 50000,
            url: "Login",
            cache: false,
             success: function (data) {
                $("#logout").remove();
                $("#logoutaux").remove();
                $("#upload").remove();
                $("#auxUpload").remove();
                $("#admin").remove();
                $("#auxAdmin").remove();
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

