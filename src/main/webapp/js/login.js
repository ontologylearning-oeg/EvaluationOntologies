/**
 * Created by dchavesf on 1/09/16.
 */


$(".button-collapse").sideNav({closeOnClick: true});
function changePage(a) {
    $("#contenido").load(a);
}

function signup() {

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
        return
    }

    $.ajax({
        type: "POST",
        timeout: 50000,
        url: "/Login",
        data: {"email":email,"pass":pass,"signup":signup},
        cache: false,
        success: function (data) {
            $.ajax({
                type: "POST",
                timeout: 50000,
                url: "/LoadOntology",
                cache: false,
                success: function (data) {
                   changeToLogin("on");
                },
                error: function (){
                    swal({
                        title: "Oops...",
                        text: "Something is wrong with the database",
                        type: "error",
                        confirmButtonColor: "#2bbbad",
                        confirmButtonText: "OK" });
                }
            });
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



function  changeToLogin(flag) {
    console.log(flag);
    if(flag=="on"){
        $("#nav-mobile").append('<li id="logout"><a onclick="changeToLogin(\'off\')">Log out</a></li>');
        $("#slide-out").append('<li id="logoutaux"><a onclick="changeToLogin(\'off\')">Log out</a></li>');
        $("#enlace").removeAttr('href');
        $("#enlace").attr('onClick', 'changePage("instructions.jsp");');
        $("#contenido").load("instructions.jsp");
    }
    else if (flag=="off"){
         $.ajax({
            type: "POST",
            timeout: 50000,
            url: "/Login",
            cache: false,
            success: function (data) {
                $("#logout").remove();
                $("#logoutaux").remove();
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

