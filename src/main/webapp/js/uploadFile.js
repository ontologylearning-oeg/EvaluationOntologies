/**
 * Created by dchavesf on 1/09/16.
 */
$(document).ajaxStop($.unblockUI);

function subirFichero(){
    if (!window.FileReader) {
        swal({
            title: "Oops...",
            text: " Your browser dont support this application",
            type: "error",
            confirmButtonColor: "#2bbbad",
            confirmButtonText: "OK" });
        return false;
    }
    var fileInputElement = document.getElementById("file");

    if (fileInputElement.files.length!==0) {
        var filename = fileInputElement.files[0].name;
        var reader = new FileReader();
        reader.readAsText(fileInputElement.files[0]);
        reader.onload = function(e) {
            sendToServer(reader.result, filename);
        };

    } else {
        swal({
            title: "Oops...",
            text: "We have to upload a file before continue",
            type: "error",
            confirmButtonColor: "#2bbbad",
            confirmButtonText: "OK" });
    }
}

function sendInstructions() {
    var relevant = $('#relevant').val();
    var norelevant = $('#noRelevant').val();
    var strictly = $('#noStrictly').val();
    var reason = $('#reason').val();
    if(relevant=="" || norelevant=="" || strictly=="" || reason==""){
        swal({
            title: "Oops...",
            text: "Please fill all the gaps",
            type: "error",
            confirmButtonColor: "#2bbbad",
            confirmButtonText: "OK"
        });
    }
    else {
        $.ajax({
            beforeSend: function () {
                $.blockUI({message: null});
            },
            type: "POST",
            timeout: 50000,
            url: "LoadInstructions",
            data: {"relevant": relevant, "norelevant": norelevant, "strictly":strictly,"reason":reason},
            cache: false,
            success: function (data) {
                Materialize.toast('File has being uploaded!', 2000, 'rounded');
                $('#modal1').closeModal();
            },
            error: function () {
                swal({
                    title: "Oops...",
                    text: "We cant send the instructions",
                    type: "error",
                    confirmButtonColor: "#2bbbad",
                    confirmButtonText: "OK"
                });
            }
        });
    }
}


function sendToServer(text){
    $.ajax({
        beforeSend: function(){
            $.blockUI({ message: null });
        },
        type: "POST",
        timeout: 50000,
        url: "LoadFile",
        data: {"texto":text},
        cache: false,
        success: function (data) {
            $('#modal1').openModal();
        },
        error: function (){
            swal({
                title: "Oops...",
                text: "We cant upload the file",
                type: "error",
                confirmButtonColor: "#2bbbad",
                confirmButtonText: "OK" });
        }
    });

    $("#contenido").load("./uploadfile.jsp");
}
