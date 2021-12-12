import React, { Component } from 'react'
import FlightService from '../../services/FlightService';

class CreateFlightComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            destination: '',
            datetime: '',
            price: '',
            freeSeats: '',
            planeId: '',
            runwayId: '',
            planes: [],
            runways: []
        }
        this.changeDestinationHandler = this.changeDestinationHandler.bind(this);
        this.changeDatetimeHandler = this.changeDatetimeHandler.bind(this);
        this.changePriceHandler = this.changePriceHandler.bind(this);
        this.changeFreeSeatsHandler = this.changeFreeSeatsHandler.bind(this);
        this.changePlaneIdHandler = this.changePlaneIdHandler.bind(this);
        this.changeRunwayIdHandler = this.changeRunwayIdHandler.bind(this);
        this.saveOrUpdateFlight = this.saveOrUpdateFlight.bind(this);
    }


    componentDidMount(){
        FlightService.getPlanes().then((response) => {
            this.setState({ planes: response.data });
          });
        FlightService.getRunways().then((response) => {
            this.setState({ runways: response.data });
          });
        if(this.state.id === '_add'){
            return
        }else{
            FlightService.getFlightById(this.state.id).then( (res) =>{
                let flight = res.data;
                this.setState({destination: flight.destination,
                    datetime: flight.datetime,
                    price: flight.price,
                    freeSeats: flight.freeSeats,
                    planeId: flight.planeId,
                    runwayId: flight.runwayId
                });
            });
        }        
    }
    saveOrUpdateFlight = (e) => {
        e.preventDefault();
        let flight = {destination: this.state.destination, datetime: this.state.datetime, price: this.state.price,
            freeSeats: this.state.freeSeats, planeId: this.state.planeId, runwayId: this.state.runwayId};                        
        console.log('flight => ' + JSON.stringify(flight));
        if(this.state.id === '_add'){
            FlightService.createFlight(flight).then(res =>{
                this.props.history.push('/flights');
            });
        }else{
            FlightService.updateFlight(flight, this.state.id).then( res => {
                this.props.history.push('/flights');
            });
        }
    }
    
    changeDestinationHandler= (event) => {
        this.setState({destination: event.target.value});
    }

    changeDatetimeHandler= (event) => {
        this.setState({datetime: event.target.value});
    }

    changePriceHandler= (event) => {
        this.setState({price: event.target.value});
    }

    changeFreeSeatsHandler= (event) => {
        this.setState({freeSeats: event.target.value});
    }

    changePlaneIdHandler= (event) => {
        this.setState({planeId: event.target.value});
    }

    changeRunwayIdHandler= (event) => {
        this.setState({runwayId: event.target.value});
    }


    cancel(){
        this.props.history.push('/flights');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add flight</h3>
        }else{
            return <h3 className="text-center">Update flight</h3>
        }
    }
    render() {
        return (
            <div>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Destination: </label>
                                            <input placeholder="Destination" name="destination" className="form-control" 
                                                value={this.state.destination} onChange={this.changeDestinationHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Date and time: </label>
                                            <input placeholder="Datetime" name="datetime" className="form-control" 
                                                value={this.state.datetime} onChange={this.changeDatetimeHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Price: </label>
                                            <input placeholder="Price" name="price" className="form-control" 
                                                value={this.state.price} onChange={this.changePriceHandler}/>
                                        </div>  
                                        <div className = "form-group">
                                            <label> Free Seats: </label>
                                            <input placeholder="FreeSeats" name="freeSeats" className="form-control" 
                                                value={this.state.freeSeats} onChange={this.changeFreeSeatsHandler}/>
                                        </div>  

                                        <div className="form-group">
                                        <label className="form-control">  Plane: 
                                        <select name="planeId" value={this.state.planeId} onChange={this.changePlaneIdHandler}> 
                                            <option value={''}> --- Odaberi ---</option>  
                                            {this.state.planes.map(plane => (
                                            <option value={plane.id}>{plane.name}</option> ))}
                                        </select>
                                        </label>
                                        </div>    

                                        <div className="form-group">
                                        <label className="form-control">  Runway: 
                                        <select name="runwayId" value={this.state.runwayId} onChange={this.changeRunwayIdHandler}> 
                                            <option value={''}> --- Odaberi ---</option>  
                                            {this.state.runways.map(runway => (
                                            <option value={runway.id}>{runway.name}</option> ))}
                                        </select>
                                        </label>
                                        </div>                                   

                                        <button className="btn btn-success" onClick={this.saveOrUpdateFlight}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default CreateFlightComponent
