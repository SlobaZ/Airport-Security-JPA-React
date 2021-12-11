import axios from 'axios';
import authHeader from './auth-header';

const TICKET_API_BASE_URL = "http://localhost:8080/api/tickets";

const FLIGHT_API_BASE_URL = "http://localhost:8080/api/flights";

const USER_API_BASE_URL = "http://localhost:8080/api/users";

class TicketService { 

    getTickets(config){
        return axios.get(TICKET_API_BASE_URL, config );
    }

    createTicket(ticket){
        return axios.post(TICKET_API_BASE_URL, ticket, { headers: authHeader() });
    }

    getTicketById(ticketId){
        return axios.get(TICKET_API_BASE_URL + '/' + ticketId, { headers: authHeader() });
    }

    updateTicket(ticket, ticketId){
        return axios.put(TICKET_API_BASE_URL + '/' + ticketId, ticket, { headers: authHeader() });
    }

    deleteTicket(ticketId){
        return axios.delete(TICKET_API_BASE_URL + '/' + ticketId, { headers: authHeader() });
    }
    getFlights(){
        return axios.get(FLIGHT_API_BASE_URL );
    }
    getUsers(){
        return axios.get(USER_API_BASE_URL + '/all' );
    }

    buyTicket(ticket){
        return axios.post(TICKET_API_BASE_URL + '/buyTicket', ticket, { headers: authHeader() });
    }

}

export default new TicketService()