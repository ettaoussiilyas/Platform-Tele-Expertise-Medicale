package com.example.platform_tele_expertise_medicale;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Medical Platform</title></head><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<p>Welcome to the Medical Expertise Platform!</p>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}