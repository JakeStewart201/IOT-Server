<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Recommendations</title>
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

<div class="input-section">
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
list.classList.add('notification');
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
