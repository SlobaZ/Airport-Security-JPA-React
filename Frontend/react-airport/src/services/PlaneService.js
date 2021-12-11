import axios from 'axios';
import authHeader from './auth-header';

const PLANE_API_BASE_URL = "http://localhost:8080/api/planes";

class PlaneService { 

    getPlanes(config){
        return axios.get(PLANE_API_BASE_URL, config );
    }

    createPlane(plane){
        return axios.post(PLANE_API_BASE_URL, plane, { headers: authHeader() });
    }

    getPlaneById(planeId){
        return axios.get(PLANE_API_BASE_URL + '/' + planeId, { headers: authHeader() });
    }

    updatePlane(plane, planeId){
        return axios.put(PLANE_API_BASE_URL + '/' + planeId, plane, { headers: authHeader() });
    }

    deletePlane(planeId){
        return axios.delete(PLANE_API_BASE_URL + '/' + planeId, { headers: authHeader() });
    }


}

export default new PlaneService()