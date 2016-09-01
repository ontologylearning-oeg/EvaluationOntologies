/**
 * Created by dchavesf on 1/09/16.
 */


$(".button-collapse").sideNav({closeOnClick: true});
function changePage(a) {
    $("#contenido").load(a);
}

function login() {
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
        data: {"email":email,"pass":pass},
        cache: false,
        success: function (data) {
            $("#contenido").load("./ontology.jsp");
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "The user already exists",
                type: "error",
                confirmButtonColor: "#2bbbad",
                confirmButtonText: "OK" });
        }
    });

}