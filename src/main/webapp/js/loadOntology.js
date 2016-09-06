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
            $("#contenido").load(data);
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "Maybe you can not do anything at this moment",
                type: "error",
                confirmButtonColor: "#2bbbad",
                confirmButtonText: "OK" });
        }
    });
}