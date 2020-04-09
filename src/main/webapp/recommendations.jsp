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
<ul class = "info-box">

<li class="plant-info">
  <div class="image-box">
  <img src="images/carrot.jpg" alt="carrot">
  </div>
  <div class="status-box">
    <ul>
      <li class="status good"><span class="statustext">Good</span></li>
      <li class="status bad"><span class="statustext">Bad</span></li>
      <li class="status ok"><span class="statustext">Ok alot of text to try and break the screen</span></li>
    </ul>
  </div>
</li>

<li class="plant-info">
  <div class="image-box">
  <img src="images/cactus.jpg" alt="cactus">
  </div>
  <div class="status-box">
    <ul>
      <li class="status good"><span class="statustext">Good</span></li>
      <li class="status bad"><span class="statustext">Bad</span></li>
      <li class="status ok"><span class="statustext">Ok</span></li>
    </ul>
  </div>
</li>

<li class="plant-info">
  <div class="image-box">
  <img src="images/tomato.jpg" alt="tomato">
  </div>
  <div class="status-box">
    <ul>
      <li class="status good"><span class="statustext">Good</span></li>
      <li class="status bad"><span class="statustext">Bad</span></li>
      <li class="status ok"><span class="statustext">Ok</span></li>
    </ul>
  </div>
</li>
</ul>

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
