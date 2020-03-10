<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recommendations</title>
</head>
<body>

<h1> Students Information </h1>
<% List<String> recommendations = (ArrayList<String>)request.getAttribute("students");

    int i = 1;
    for(String r : recommendations)
    {
        out.print("Tip " + i + ": "+ r);

        out.print("<br/>");
        out.print("<br/>");
        i+=1;
    }

%>

</body>
</html>
