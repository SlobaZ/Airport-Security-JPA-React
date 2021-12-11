import React, { Component } from 'react'
import TicketService from '../../services/TicketService';
import AuthService from "../../services/auth.service";

class BuyTicketComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            userId: '',
            currentUser: { id: "" },
            flightId: '',
            flights: []
        }
        this.buyTicket = this.buyTicket.bind(this);
    }


    componentDidMount(){
        const currentUser = AuthService.getCurrentUser();
        this.setState({ userId: currentUser.id});
        TicketService.getFlights().then((response) => {
            this.setState({ flights: response.data });
          });       
    }

    buyTicket(id) {
        let ticket = { userId: this.state.userId , flightId: id };                        
        console.log('ticket => ' + JSON.stringify(ticket));
            TicketService.buyTicket(ticket).then(res =>{
                this.props.history.push('/');
            });
    }

 

    render() {
        
        return (
            <div>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-9 offset-md-6 offset-md-6">
                                 <br/>  
                                <h2>Flights and Destinations: </h2>
                                <div className = "card-body">
                                <table className = "table table-striped table-bordered">

                                <thead>
                                <tr>
                                    <th> Destination</th>
                                    <th> Datetime</th>
                                    <th> Price</th>
                                    <th> Free Seats</th>
                                    <th> Actions</th>
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
                                            <td>
                                 <button onClick={ () => this.buyTicket(flight.id)} className="btn btn-danger btn-block">BUY </button>
                                            </td>
                                        </tr>
                                    )
                                    }
                                </tbody>
                                </table>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default BuyTicketComponent
