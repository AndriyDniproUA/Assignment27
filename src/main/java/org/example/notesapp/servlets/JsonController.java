package org.example.notesapp.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JsonController extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();

    protected void writeJson(Object o, HttpServletResponse response) throws IOException {
        try {
            response.setHeader("Content-type", "application/json");
            String strResponse = mapper.writeValueAsString(o);
            response.getWriter().print(strResponse);
            response.getWriter().flush();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected <T> T readJson(Class<T> clazz, HttpServletRequest request) throws IOException {
        return mapper.readValue(request.getInputStream(),clazz);
    }
}
