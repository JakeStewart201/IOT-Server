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
        Choose measurement:
        <select name="type">
            <option value="Temperature">Temperature</option>
            <option value="Light">Light</option>
            <option value="Humidity">Light</option>
            <option value="Soil Moisture">Light</option>
        </select>
        <p/>
        <input type="submit" value="View">
    </form>
</div>

<%
        if (request.getParameter("type") != null) {
%>

<script src="chart/Chart.bundle.js" type="text/javascript">
</script>

<canvas id="myChart" width="100" height="100"></canvas>
<script>

    var ctx = document.getElementById('myChart');
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ${dates} ,
            datasets: [{
                label: '${label}',
                data: ${measurements},
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 0.2)',
                fill : false,
            }]
        },
        options: {
            scales: {
                yAxes: [{
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