package com.example.market1.Service;

import com.example.market1.DAO.TicketDAO;
import com.example.market1.Model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    TicketDAO ticketDAO;

    public int addTicketLogin(Ticket ticket){
        return ticketDAO.addTicket(ticket);
    }

    public int updateStatus(String ticket){
        return ticketDAO.expireTicket(ticket);
    }
}
