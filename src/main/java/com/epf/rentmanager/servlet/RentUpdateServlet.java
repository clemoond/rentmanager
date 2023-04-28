package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/rents/update")
public class RentUpdateServlet extends HttpServlet {
    @Autowired
    ReservationService reservationService;
    ClientService clientService;
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            request.setAttribute("clients", clientService.findAll());
            request.setAttribute("voitures", vehicleService.findAll());
            request.setAttribute("reservation", reservationService.findById(id));
        } catch (ServiceException e) {
            throw new Error("Erreur, doGet n'a pas marché");
        }

        this.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/rents/update.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        int id = Integer.parseInt(request.getParameter("id"));
        Reservation reservation = new Reservation(id, Integer.parseInt(request.getParameter("client_id")), Integer.parseInt(request.getParameter("vehicle_id")),
                LocalDate.parse(request.getParameter("debut")), LocalDate.parse(request.getParameter("fin")));
        try {
            reservationService.update(reservation);
        } catch (ServiceException e) {
            throw new Error("Erreur, doPost n'a pas marché");
        }
        response.sendRedirect("/rentmanager/rents");
    }
}