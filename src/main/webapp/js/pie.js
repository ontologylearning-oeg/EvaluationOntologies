/**
    Created on : 09-may-2016
    Author     : dchaves

$(function () {

    var gaugeOptions = {

        'chart': {
            'type': 'solidgauge'
        },

        'title': null,

        'tooltip': {
            'enabled': false
        },

        'pane': {
            'center': ['50%', '50%'],
            'size': '500px',
            'startAngle': 0,
            'endAngle': 360,
            'background': {
                'backgroundColor': '#EEE',
                'innerRadius': '90%',
                'outerRadius': '100%',
                'borderWidth': 0
            }
        },

        'yAxis': {
            'min': 0,
            'max': 100,
            'labels': {
                'enabled': false
            },

            'lineWidth': 0,
            'minorTickInterval': null,
            'tickPixelInterval': 400,
            'tickWidth': 0
        },

        'plotOptions': {
            'solidgauge': {
                'innerRadius': '90%'
            }
        },

        'series': [{
            'name': 'Speed',
            'data': [80],
            'dataLabels': {
                'enabled': false
            }
        }]
    };

    $('#my-chart').highcharts(gaugeOptions);

});
 **/