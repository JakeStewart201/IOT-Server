<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Recommendations</title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
</head>

<body>

<div id="nav-placeholder">
</div>

<script>
$(function(){
    $("#nav-placeholder").load("nav.html");
});
</script>

<div>
    <h1>Recommendations</h1>
    <form action="recommendations" method="get">
        <label for="deviceID">Enter device id:</label><input type="number" id="deviceID" name="deviceID" min="0" max="100">
        <input type="submit" value="View">
    </form>

</div>
<div id="tips">
</div>

<%
if (request.getParameter("deviceID") != null) {
%>

<script>

var list = document.createElement('ul');
var tips = ${tips};

for (var i = 0; i < tips.length; i++) {
    var item = document.createElement('li');
    item.appendChild(document.createTextNode(tips[i]));
    list.appendChild(item);
}

document.getElementById('tips').appendChild(list);
</script>

<%
} else {
%>

<%
}
%>


</body>
</html>
