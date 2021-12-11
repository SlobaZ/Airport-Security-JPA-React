import React, { Component } from 'react'
import FlightService from '../../services/FlightService'
import AuthService from "../../services/auth.service";

class ListFlightsComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                flights: [],
                planes: [],
                searchDestination: '',
                searchDatetime: '',
                searchPlaneId: ''
                
        };
        
        this.addFlight = this.addFlight.bind(this);
        this.editFlight = this.editFlight.bind(this);
        this.deleteFlight = this.deleteFlight.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        
    }

    handleChange(event) {
       this.setState({[event.target.name]: event.target.value});  
    }
    
    handleSubmit(event) {
        event.preventDefault();  
       this.refreshFlights();
    }

    deleteFlight(id){
        FlightService.deleteFlight(id).then( response => {
            this.setState({flights: this.state.flights.filter(flight => flight.id !== id)});
        });
    }

    editFlight(id){
        this.props.history.push(`/addorupdate-Flight/${id}`);
    }

    componentDidMount(){
        this.refreshFlights();
    }

    refreshFlights() {
        const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        showEmployee: user.roles.includes("ROLE_EMPLOYEE"),
        showAdmin: user.roles.includes("ROLE_ADMIN"),
      });
    }
        let config = { params: {} };
    
        if (this.state.searchDestination !== "") {
          config.params.destination = this.state.searchDestination;
        }
        if (this.state.searchDatetime  !== "") {
          config.params.datetime = this.state.searchDatetime;
        }
        if (this.state.searchPlaneId  !== "") {
            config.params.planeId = this.state.searchPlaneId;
          }

        FlightService.getPlanes().then((response) => {
            this.setState({ planes: response.data });
          });

        FlightService.getFlights(config).then((response) => {
          this.setState({ flights: response.data });
        });

      }

    addFlight(){
        this.props.history.push('/addorupdate-Flight/_add');
    }

    render() {
        const { showAdmin } = this.state;
        return (
            <div>
                <br/>
                
                 <div className="searchDiv">
                 <form onSubmit={this.handleSubmit}>
                   
                    <div className="form-group">
                    <label className="form-control">  Destination: 
                    <input type="text" name="searchDestination" value={this.state.searchDestination} onChange={this.handleChange}/>
                    </label>
                    </div>

                    <div className="form-group">
                    <label className="form-control">  Date and time: 
                    <input type="text" name="searchDatetime" value={this.state.searchDatetime} onChange={this.handleChange}/> 
                    </label>
                    </div>

                    <div className="form-group">
                    <label className="form-control">  Plane: 
                    <select name="searchPlaneId" value={this.state.searchPlaneId} onChange={this.handleChange}> 
                            <option value={''}> --- Odaberi ---</option>  
                            {this.state.planes.map(plane => (
                            <option value={plane.id}>{plane.name}</option> ))}
                    </select>
                    </label>
                    </div>

                    <div className="form-group">
                    <input type="submit" value="Search" />
                    </div>
                </form>
                </div>
                
                 <br/>             
                 <h2 className="text-center">Flights List</h2>

                 {showAdmin && (
                 <div className="addButtonDiv">
                    <button className="btn btn-primary btn-block" onClick={this.addFlight}> Add Flight</button>
                 </div>
                  )}
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered table-hover">

                            <thead>
                                <tr>
                                    <th> Destination</th>
                                    <th> Datetime</th>
                                    <th> Price</th>
                                    <th> Free Seats</th>
                                    <th> Plane</th>
                                    <th> Runway</th>
                                    {showAdmin && (    <th> Actions</th>  )}
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.flights.map(
                                        flight => 
                                        <tr key = {flight.id}>
                                             <td> {flight.destination} </td>   
                                             <td> {flight.datetime}</td>
                                             <td> {flight.price}</td>
                                             <td> {flight.freeSeats}</td>
                                             <td> {flight.planeName}</td>
                                             <td> {flight.runwayName}</td>
                                             {showAdmin && ( 
                                             <td>
                                                 <button onClick={ () => this.editFlight(flight.id)} className="btn btn-info">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteFlight(flight.id)} className="btn btn-danger">Delete </button>
                                             </td>
                                             )}
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>
                 
            </div>
        )
    }
}

export default ListFlightsComponent
