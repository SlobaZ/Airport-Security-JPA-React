import axios from 'axios';
import authHeader from './auth-header';

const RUNWAY_API_BASE_URL = "http://localhost:8080/api/runways";

class RunwayService { 

    getRunways(config){
        return axios.get(RUNWAY_API_BASE_URL, config );
    }

    createRunway(runway){
        return axios.post(RUNWAY_API_BASE_URL, runway, { headers: authHeader() });
    }

    getRunwayById(runwayId){
        return axios.get(RUNWAY_API_BASE_URL + '/' + runwayId, { headers: authHeader() });
    }

    updateRunway(runway, runwayId){
        return axios.put(RUNWAY_API_BASE_URL + '/' + runwayId, runway, { headers: authHeader() });
    }

    deleteRunway(runwayId){
        return axios.delete(RUNWAY_API_BASE_URL + '/' + runwayId, { headers: authHeader() });
    }


}

export default new RunwayService()