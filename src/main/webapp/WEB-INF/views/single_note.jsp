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
    <title>"${note.title}"</title>
</head>

<body>
<h1>${note.title}</h1>
<p>
    ID:${note.id}   ${note.contents}
</p>
<br/>
<a href="<c:url value="/"></c:url>">RETURN</a>


</body>
</html>
