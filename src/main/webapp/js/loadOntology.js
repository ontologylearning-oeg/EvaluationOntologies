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
            if(data=="notUser"){
                swal({
                    title: "Oops...",
                    text: "You have to learn more about Knowledge Representation for answer this questions",
                    type: "error",
                    confirmButtonColor: "#2bbbad",
                    confirmButtonText: "OK" });
            }
            else{
                $("#contenido").load(data);
            }
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