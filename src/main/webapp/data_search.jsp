<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>View Data</title>
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
        <input type="date" id="datePicker" name="fromDate" onChange="updateDate(this);">
        <select name="type" onChange="updateType(this);">
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
	var epoch = 0;

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

function updateType(sel) {
	jLabel = sel.options[sel.selectedIndex].text;
	changeData();
}

function updateDate(dat) {
	var date = dat.value;
	if (date.match(/\d{4}-\d{2}-\d{2}/)) {
		var temp = new Date(date);
		epoch = temp.getTime();
		changeData();
	}
}

function trimData(oldData, epoch) {
	var newData = [];
	oldData.forEach((reading) => {
		if (reading.x >= epoch) {
			newData.push(reading);
		}
	});
	return newData;
}

function changeData() {
	var data = trimData(jData[jLabel], epoch);
    removeData(myChart);
	addData(myChart, jLabel, data);
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
