<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>View Data</title>
</head>

<body>
<div>
    <h1>My Data</h1>
    View a graph of your measurements.<p/>

    <form action="data" method="get">
        <label for="datePicker">Choose date to show measurements from:</label>
        <input type="date" id="datePicker" name="fromDate">
        <p/>
        Choose measurement:
        <select name="type">
            <option value="Temperature">Temperature</option>
            <option value="Light">Light</option>
            <option value="Humidity">Humidity</option>
            <option value="Soil Moisture">Soil Moisture</option>
        </select>
        <p/>
        <label for="id">Choose a sensor id:</label><input type="number" id="id" name="id" min="0" max="100">
        <input type="submit" value="View">
    </form>

</div>

<%
        if (request.getParameter("type") != null) {
            // the following code uses the open source chart.js to produce a graph
%>

<script src="chart/Chart.bundle.js" type="text/javascript">
</script>

<div style="width:75%">
    <canvas id="myChart"></canvas>
</div>

<script>

    var ctx = document.getElementById('myChart');
    var myChart = new Chart(ctx, {
    	type: 'line',
        data: {
            datasets: [{
                label: '${label}',
                data: ${data},
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
</script>

<%}
else {%>

<%
    }
%>


</body>
</html>
