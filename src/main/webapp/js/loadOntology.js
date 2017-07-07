/**
 * Created by dchavesf on 2/09/16.
 */

$(document).ajaxStop($.unblockUI);


function loadOntology(name) {
    $.ajax({
        beforeSend: function(){
            $.blockUI({ message: null });
        },
        type: "POST",
        timeout: 50000,
        url: "LoadFeatures",
        data: {"name":name},
        cache: false,
        success: function (data) {
            if(data=="notUser"){
                swal({
                    title: "Oops...",
                    text: "You have to learn more about Knowledge Representation for answer these questions",
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



function loadAdmin(a){
    $.ajax({
        beforeSend: function(){
            $.blockUI({ message: null });
        },
        type: "POST",
        timeout: 50000,
        data:{"name":a},
        url: "LoadAdmin",
        cache: false,
        success: function (data) {
            $("#contenido").load("./eval/ontoAdmin.jsp");
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "There is a problem with the database",
                type: "error",
                confirmButtonColor: "#2bbbad",
                confirmButtonText: "OK" });
        }
    });
}

function removeOntology(a){
    $.ajax({
        beforeSend: function(){
            $.blockUI({ message: null });
        },
        type: "POST",
        timeout: 50000,
        data:{"name":a,"remove":true},
        url: "LoadAdmin",
        cache: false,
        success: function (data) {
            $("#contenido").load("./instructions.jsp");
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "There is a problem with the database",
                type: "error",
                confirmButtonColor: "#2bbbad",
                confirmButtonText: "OK" });
        }
    });
}