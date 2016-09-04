/**
 * Created by dchavesf on 2/09/16.
 */


function loadOntology(name) {
    $.ajax({
        type: "POST",
        timeout: 50000,
        url: "/LoadFeatures",
        data: {"name":name},
        cache: false,
        success: function (data) {
            $("#contenido").load("./eval/index.jsp");
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "",
                type: "error",
                confirmButtonColor: "#2bbbad",
                confirmButtonText: "OK" });
        }
    });
}