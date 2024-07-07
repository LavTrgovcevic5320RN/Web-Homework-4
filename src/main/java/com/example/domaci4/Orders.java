package com.example.domaci4;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@WebServlet(name = "Orders", value = "/orders")
public class Orders extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String password = request.getParameter("password");
        Scanner scanner = new Scanner(new File("C:/Users/Lav/Desktop/Domaci4/src/data/password.txt"));
        if (password.equals(scanner.nextLine())) {
            List<Meal> listOfMeals = (List<Meal>) getServletContext().getAttribute("allMeals");
            List<String> days = (List<String>) getServletContext().getAttribute("days");

            out.println("<html><head><style></style></head><body>");
            out.println("<form method=\"POST\" action = \"orders?password=sifra\">");
            out.println("<h1>" + "Odabrana Jela" + "</h1>");


            for(String day: days) {
                out.println("<h1>" + day + "</h1>");
                out.println("<h1></h1>");
                out.println("<style>");
                out.println("h1 {");
                out.println("  width: 50%;");
                out.println("  margin: auto;");
                out.println("}");
                out.println("table {");
                out.println("  border-collapse: collapse;");
                out.println("  width: 50%;");
                out.println("  margin: auto;");
                out.println("}");
                out.println("th, td {");
                out.println("  text-align: left;");
                out.println("  padding: 8px;");
                out.println("}");
                out.println("tr:nth-child(even) {");
                out.println("  background-color: #f2f2f2;");
                out.println("}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");

                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Jelo</th>");
                out.println("<th>Kolicina</th>");
                out.println("</tr>");

                for (int i = 0; i < listOfMeals.size(); i++) {
                    if (listOfMeals.get(i).getDay().equals(day)) {
                        String backgroundColor = i % 2 == 0 ? "#ffffff" : "#f2f2f2";
                        out.println("<tr style='background-color: " + backgroundColor + ";'>");
                        out.println("<td>" + listOfMeals.get(i).getFood() + "</td>");
                        out.println("<td>" + listOfMeals.get(i).getOrderNumber() + "</td>");
                        out.println("</tr>");
                    }
                }
                out.println("</table>");
            }

            out.println("<br><input type=\"submit\" text-align=\"center\" name=\"submit\" value=\"Obrisi\"/></form>");
            out.println("</body></html>");
        }
        else {
            out.println("<h3>" +"Wrong password" + "</h3>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        List<Meal> listOfMeals = (List<Meal>) getServletContext().getAttribute("allMeals");
        for (Meal o: listOfMeals) {
            o.setOrderNumber(0);
        }
        Map<String, List<Meal>> map = (Map<String, List<Meal>>) getServletContext().getAttribute("map");
        for (Map.Entry<String, List<Meal>> m: map.entrySet()){
            getServletContext().setAttribute(m.getKey(), null);
        }
        getServletContext().setAttribute("map", map);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
