package com.example.domaci4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private List<String> days = new ArrayList<>();
    private ArrayList<Meal> saveMeal = new ArrayList<>();
    private Map<String, List<Meal>> map = new HashMap<>();

    public void init() {
        days.add("Ponedeljak");
        days.add("Utorak");
        days.add("Sreda");
        days.add("Cetvrtak");
        days.add("Petak");
        getServletContext().setAttribute("days", days);
        for (String day: days) {
            try {
                Scanner scanner = new Scanner(new File("C:/Users/Lav/Desktop/Domaci4/src/data/" + day + ".txt"));
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    Meal meal = new Meal(day, data);
                    saveMeal.add(meal);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }}
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service method");
        super.service(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (getServletContext().getAttribute(request.getSession().getId()) != null) {
            out.println("<h3>" + "Done" + "</h3>");
            List<Meal> meals = map.get(request.getSession().getId());
            for (Meal meal: meals) {
                out.println(meal.getDay());
                out.println("<br>");
                out.println(meal.getFood());
                out.println("<br>");
                out.println("<br>");
            }
            return;
        }

        out.println("<html><body><form method=\"POST\" action = \"hello-servlet\">");
        out.println("<h1>" + "Choose your lunch" + "</h1>");
        for (String day: days) {
            out.println("<h3>" + day + "</h3>");
            out.println("<select name = \""+ day + "\" id=\"" + day + "\">");
//            System.out.println("<select name = \" "+ day + "\" id=\"" + day+ "\">" );
            for (Meal meal: saveMeal) {
                if (meal.getDay().equals(day)) {
                    out.println("<option value = \"" + meal.getFood() + "\" selected>" + meal.getFood() + "</option>");
                }
            }
            out.println("</select><br>");
            out.println("--------------------------------");
        }
        out.println("<br><input type=\"submit\" name\"submit\" value\"Sacuvaj\"/></form>");
        out.println("</body></html>");
        getServletContext().setAttribute("allMeals", saveMeal);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/response.html");
        getServletContext().setAttribute(request.getSession().getId(),true);
        List<Meal> list = new ArrayList<>();
        for (String day: days) {
            String meal = request.getParameter(day); // vraca izabrani obrok za odredjeni dan
//            System.out.println(meal);
            Meal m = returnMeal(day, meal);
            m.setOrderNumber(m.getOrderNumber()  + 1);
            synchronized (this) {
                list.add(m);
            }
        }
        map.put(request.getSession().getId(), list);
        getServletContext().setAttribute("map", map);
    }

    private Meal returnMeal(String day, String meal){
        for (Meal m : saveMeal) {
            if (m.getDay().equals(day) && m.getFood().equals(meal))
                return m;
        }
//        System.out.println(day + " " + meal);
        return null;
    }

    public void destroy() {
    }
}
