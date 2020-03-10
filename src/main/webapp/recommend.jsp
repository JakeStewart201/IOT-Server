<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recommendations</title>
</head>
<body>

<h1> Recommendations</h1>
<form action="data" method="get">
<% List<String> recommendations = (ArrayList<String>)request.getAttribute("students");

%>

</body>
</html>
