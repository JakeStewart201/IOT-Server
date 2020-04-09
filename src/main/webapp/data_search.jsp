<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>View Data</title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body>

<div id="nav-placeholder">
</div>

<script>
$(function(){
    $("#nav-placeholder").load("nav.html");
});
</script>

<div class="center">
<div class="bar">

    <form action="data" method="get">
        <label for="datePicker">From:</label>
        <input type="date" id="datePicker" name="fromDate">
        <select name="type" onChange="changeData(this);">
          <option value="Temperature" selected="selected">Temperature</option>
          <option value="Light">Light</option>
          <option value="Humidity">Humidity</option>
          <option value="Soil Moisture">Soil Moisture</option>
        </select>
    </form>

</div>

<%
        if (request.getParameter("id") != null) {
            // the following code uses the open source chart.js to produce a graph
%>

<script src="chart/Chart.bundle.js" type="text/javascript">
</script>

<div class="center" style="width:75%">
    <canvas id="myChart"></canvas>
</div>

<script>
	var jLabel = "Temperature";
    var jData = ${data};

    var ctx = document.getElementById('myChart');
    var myChart = new Chart(ctx, {
    	type: 'line',
        data: {
            datasets: [{
                label: jLabel,
                data: jData[jLabel],
                backgroundColor: 'rgba(0, 230, 64, 1)',
                borderColor: 'rgba(0, 230, 64, 1)',
                fill : false,
            }]
        },
        options: {
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Date'
                    },
                    type: 'time',
                    distribution: 'linear'
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: '${label}'
                    },
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
		
    });
	
function addData(chart, label, data) {
    chart.data.datasets.forEach((dataset) => {
        dataset.data = data;
		dataset.label = label;
    });
    chart.update();
}

function removeData(chart) {
    chart.data.datasets.forEach((dataset) => {
        dataset.data = [];
		dataset.label = "";
    });
}

function changeData(sel) {
	var type = sel.options[sel.selectedIndex].text;
    removeData(myChart);
	addData(myChart, type, jData[type]);
}
</script>

<%}
else {%>

<%
    }
%>
</div>

</body>
</html>
