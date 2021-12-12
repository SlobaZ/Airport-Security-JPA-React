import axios from 'axios';
import authHeader from './auth-header';

const FLIGHT_API_BASE_URL = "http://localhost:8080/api/flights";

const PLANE_API_BASE_URL = "http://localhost:8080/api/planes";

const RUNWAY_API_BASE_URL = "http://localhost:8080/api/runways";

class FlightService { 

    getFlights(config){
        return axios.get(FLIGHT_API_BASE_URL, config );
    }

    createFlight(flight){
        return axios.post(FLIGHT_API_BASE_URL, flight, { headers: authHeader() });
    }

    getFlightById(flightId){
        return axios.get(FLIGHT_API_BASE_URL + '/' + flightId, { headers: authHeader() });
    }

    updateFlight(flight, flightId){
        return axios.put(FLIGHT_API_BASE_URL + '/' + flightId, flight, { headers: authHeader() });
    }

    deleteFlight(flightId){
        return axios.delete(FLIGHT_API_BASE_URL + '/' + flightId, { headers: authHeader() });
    }

    getPlanes(){
        return axios.get(PLANE_API_BASE_URL );
    }

    getRunways(){
        return axios.get(RUNWAY_API_BASE_URL );
    }

}

export default new FlightService()