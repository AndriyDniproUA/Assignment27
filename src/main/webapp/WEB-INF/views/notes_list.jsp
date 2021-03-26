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
    <title>Notes</title>
</head>

<body>
<h1>Note storage service</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>TITLE</th>
        <th>delete option</th>
    </tr>

    <c:forEach var="note" items="${notes}">

        <tr>
            <td>
                    ${note.id}
            </td>
            <td>

                <a href="<c:url value="/notes/${note.id}"/>">${note.title}</a>

            </td>

            <td>
                <form action="<c:url value="/delete"/>" method="post">
                    <input type="hidden" value="${note.id}" name="id">
                    <input type="submit" value="delete">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>

<br/>
<br/>


<form action="<c:url value="/addnote"/>" method="get" novalidate="novalidate" >
    <input type="hidden">
    <input type="submit" value="ADD NEW NOTE">
</form>

</body>
</html>
