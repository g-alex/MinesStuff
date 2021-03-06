/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.garnier;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bobi
 */
public class Index extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<p>Nombre d'objet(s) ServletContext : " + CountContext.getCount() + "</p>");
        out.println("<p>Nombre d'attribut(s) du contexte : " + CountContextAttribute.getCount() + "</p>");
        out.println("<p>Nombre d'objet(s) HttpServletRequest : " + CountRequest.getCount() + "</p>");
        out.println("<p>Attribut(s):<ul>");
        for (Map.Entry<String, Object> e : CountContextAttribute.getAttributes().entrySet()) {
            out.println("<li>" + e.getKey() + ":" + e.getValue().toString() + "</li>");
        }
        out.println("</ul></p>");
    }
}
