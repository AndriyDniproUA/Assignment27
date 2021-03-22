<%@ page import="org.example.notesapp.services.NotesRepository" %>
<%@ page import="org.example.notesapp.services.UberFactory" %>
<%@ page import="org.example.notesapp.entities.Note" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.notesapp.dto.NotesResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Add new note</title>
</head>

<body>
<h1>Add a new note</h1>

<form action="<c:url value="/addnote"/>" method="post">
    Title: <input type="text" name="title"> <br/>
    Note: <input type="text" name="contents"> <br/>
    <input type="submit" value="submit">
</form>

</body>
</html>
