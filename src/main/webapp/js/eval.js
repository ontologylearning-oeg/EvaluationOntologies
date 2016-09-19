/**
 * Created by dchavesf on 6/09/16.
 */

$(document).ajaxStop($.unblockUI);


$(document).ready(function(){
    $("#modal").leanModal();
});

var terms=[];
$('input[type="radio"]').on('change', function() {
    var answer="";
    if(this.value=="yes"){
        answer="no";
    }
    else {
        answer="yes";
    }
    var index=$.inArray(this.name+";"+answer, terms);
    if ( index == -1){
        terms.push(this.name+";"+this.value);
    }
    else{
        terms[index] = this.name+";"+this.value;
    }
});

function sendAnswers(servlet) {
    var text="";
    for(var i=0; i<terms.length; i++){
        text=text+terms[i]+"\n";
    }
    $.ajax({
        beforeSend: function(){
            $.blockUI({ message: null });
        },
        type: "POST",
        timeout: 50000,
        url: servlet,
        data: {"text":text},
        cache: false,
        success: function (data) {
            if(data=="./eval/index.jsp"){
                Materialize.toast("You've finished this part of the evaluation!", 2000, 'rounded');
            }
            $("#contenido").load(data);
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
}
