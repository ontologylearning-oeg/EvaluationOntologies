/**
 * Created by dchavesf on 8/09/16.
 */
$( document ).ready(function(){
    var mes=[];
    var names=['Recall','Precision','F-Measure','FK','Taxonomic Precision','Taxonomic Recall','Taxonomic F-Measure', 'TFK'];
    for(var i=0; i<8;i++){
        mes[i] = parseFloat(document.getElementById(names[i]).getAttribute("data-value"))*100;
    }

    // Create the chart
    $('#chart').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            title: {
                text: 'Total percent of results'
            },
            min: 0,
            max: 100

        },
        legend: {
            enabled: false
        },
        credits:{
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}%'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },

        series: [{
            name: 'Percentage',
            colorByPoint: true,
            data: [{
                name: 'Recall',
                y: mes[0]
            }, {
                name: 'Precision',
                y: mes[1]
            }, {
                name: 'F-Measure',
                y: mes[2]
            }, {
                name: 'Fleiss Kappa',
                y: mes[3]
            }, {
                name: 'TPrecision',
                y: mes[4]
            }, {
                name: 'TRecall',
                y: mes[5]
            }, {
                name: 'TF-Measure',
                y: mes[6]
            },{
                name: 'TFleiss Kappa',
                y: mes[7]
            }]
        }],

    });



});
