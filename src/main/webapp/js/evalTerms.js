/**
 * Created by dchavesf on 6/09/16.
 */


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

function sendTerms() {
    var text="";
    for(var i=0; i<terms.length; i++){
        text=text+terms[i]+"\n";
        console.log(text);
    }
    $.ajax({
        type: "POST",
        timeout: 50000,
        url: "/EvaluatedTerms",
        data: {"text":text},
        cache: false,
        success: function (data) {
            if(data=="./eval/index.jsp"){
                Materialize.toast("You've finished this part of the evaluation!");
            }
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
