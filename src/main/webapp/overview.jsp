<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Overview</title>
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

<div id="overview">
</div>

<script>

var list = document.createElement('ul');
list.classList.add('info-box');
/*ar statuss = [
[
  {'t': 'good', 'm': "All is good", 'i': 'water'},
  {'t': 'ok', 'm': "All is ok", 'i': 'temp'}
],
[
  {'t': 'bad', 'm': "All is bad", 'i': 'light'},
  {'t': 'good', 'm': "All is good2", 'i': 'hum'}
]];
var names = ["carrot", "tomato"];*/
var statuss = ${statuss};
var names = ${names};
var devices = ${devices};

for (var i = 0; i < names.length; i++) {
	if (names[i] == null) {
		continue;
	}
    var item = document.createElement('li');
	item.classList.add('plant-info');
	(function(i) {
		item.addEventListener("click", function() {location.href = '/data?fromDate=&id=' + devices[i];}, false);
	})(i);
	
	var pictureDiv = document.createElement('div');
	pictureDiv.classList.add('image-box');
	var image = document.createElement('img');
	image.src = 'images/' + names[i] + '.jpg';
	image.alt = names[i];
	pictureDiv.appendChild(image);
    item.appendChild(pictureDiv);
	
	var statusDiv = document.createElement('div');
	statusDiv.classList.add('status-box');
	var statusList = document.createElement('ul');
	for (var j = 0; j < statuss[i].length; j++) {
		var statusLi = document.createElement('li');
		statusLi.className = 'status ' + statuss[i][j]['t'];
		var span = document.createElement('span');
		span.classList.add('statustext');
		span.appendChild(document.createTextNode(statuss[i][j]['m']));
		var statusimage = document.createElement('img');
		statusimage.src = 'images/' + statuss[i][j]['i'] + '.png';
		statusimage.alt = "";
		statusLi.appendChild(statusimage);
		statusLi.appendChild(span);
		statusList.appendChild(statusLi);
	}
	statusDiv.appendChild(statusList);
	item.appendChild(statusDiv);
    list.appendChild(item);
}

document.getElementById('overview').appendChild(list);
</script>

</body>
</html>
