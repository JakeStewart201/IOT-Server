<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Add Plant</title>
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

<div>
    <h1>Add Plant</h1>
    <form action="add_plant" method="get">
        <label for="deviceID">Enter device id:</label><input type="number" id="deviceID" name="deviceID" min="0" max="100">
        </p>
        <select name="name">
            <option value="Tomato">Tomato</option>
            <option value="Cactus">Cactus</option>
            <option value="Carrot">Carrot</option>
        </select>
        <input type="submit" value="Create">
    </form>

</div>
