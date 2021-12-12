import React, { Component } from 'react'
import TicketService from '../../services/TicketService';

class CreateTicketComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            userId: '',
            flightId: '',
            flights: [],
            users: []
        }
        this.changeFlightIdHandler = this.changeFlightIdHandler.bind(this);
        this.changeUserIdHandler = this.changeUserIdHandler.bind(this);
        this.saveOrUpdateTicket = this.saveOrUpdateTicket.bind(this);
    }


    componentDidMount(){
        TicketService.getUsers().then((response) => {
            this.setState({ users: response.data });
          });
        TicketService.getFlights().then((response) => {
            this.setState({ flights: response.data });
          });
        if(this.state.id === '_add'){
            return
        }else{
            TicketService.getTicketById(this.state.id).then( (res) =>{
                let ticket = res.data;
                this.setState({
                    userId: ticket.userId,
                    flightId: ticket.flightId
                });
            });
        }        
    }
    saveOrUpdateTicket = (e) => {
        e.preventDefault();
        let ticket = { userId: this.state.userId , flightId: this.state.flightId };                        
        console.log('ticket => ' + JSON.stringify(ticket));
        if(this.state.id === '_add'){
            TicketService.createTicket(ticket).then(res =>{
                this.props.history.push('/tickets');
            });
        }else{
            TicketService.updateTicket(ticket, this.state.id).then( res => {
                this.props.history.push('/tickets');
            });
        }
    }
    
    changeUserIdHandler= (event) => {
        this.setState({userId: event.target.value});
    }
    changeFlightIdHandler= (event) => {
        this.setState({flightId: event.target.value});
    }

    cancel(){
        this.props.history.push('/tickets');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add ticket</h3>
        }else{
            return <h3 className="text-center">Update ticket</h3>
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
                                       
                                        <div className="form-group">
                                        <label className="form-control">  User: 
                                        <select name="userId" value={this.state.userId} onChange={this.changeUserIdHandler}> 
                                            <option value={''}> --- Odaberi ---</option>  
                                            {this.state.users.map(user => (
                                            <option value={user.id}>{user.username}</option> ))}
                                        </select>
                                        </label>
                                        </div>  

                                        <div className="form-group">
                                        <label className="form-control">  Flight: 
                                        <select name="flightId" value={this.state.flightId} onChange={this.changeFlightIdHandler}> 
                                            <option value={''}> --- Odaberi ---</option>  
                                            {this.state.flights.map(flight => (
                                            <option value={flight.id}>{flight.destination}</option> ))}
                                        </select>
                                        </label>
                                        </div>                                     

                                        <button className="btn btn-success" onClick={this.saveOrUpdateTicket}>Save</button>
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

export default CreateTicketComponent
