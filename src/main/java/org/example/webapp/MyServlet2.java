package org.example.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.webapp.dto.User;
import org.example.webapp.dto.UserResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyServlet2 extends JsonServlet {

    int i = 2;

    Map<String, User> users = new HashMap<>() {{
        put("1", new User().setName("Vasia2").setRole("admin"));
        put("2", new User().setName("Petia2").setRole("user"));
    }};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null && users.containsKey((String) id)) {
            User user = users.get(id);
            UserResponse response = new UserResponse()
                    .setUser(user)
                    .setStatus("ok");
            writeJson(response, resp);
        } else {
            UserResponse response = new UserResponse()
                    .setError("USER NOT FOUND in TEST")
                    .setStatus("error");
            writeJson(response, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = readJson(User.class,req);
        String id = String.valueOf(++i);
        users.put(id,user);
    }
}
