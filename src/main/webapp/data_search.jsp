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
            labels: ${dates} ,
            datasets: [{
                label: '${label}',
                data: ${measurements},
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
                    }
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