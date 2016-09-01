/**
 * Created by dchavesf on 1/09/16.
 */

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


function sendToServer(text, filename){
    $.ajax({
        type: "POST",
        timeout: 50000,
        url: "/LoadFile",
        data: {"texto":text,"nombre":filename},
        cache: false,
        success: function (data) {
            Materialize.toast('File has being uploaded!', 2000, 'rounded');
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
