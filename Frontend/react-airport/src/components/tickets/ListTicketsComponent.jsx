import React, { Component } from 'react'
import TicketService from '../../services/TicketService'
import AuthService from "../../services/auth.service";

class ListTicketsComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                tickets: [],
                flights: [],
                users: [],
                searchFlightId: '',
                searchUserID: ''
                
        };
        
        this.addTicket = this.addTicket.bind(this);
        this.editTicket = this.editTicket.bind(this);
        this.deleteTicket = this.deleteTicket.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        
    }

    handleChange(event) {
       this.setState({[event.target.name]: event.target.value});  
    }
    
    handleSubmit(event) {
        event.preventDefault();  
       this.refreshTickets();
    }

    deleteTicket(id){
        TicketService.deleteTicket(id).then( response => {
            this.setState({tickets: this.state.tickets.filter(ticket => ticket.id !== id)});
        });
    }

    editTicket(id){
        this.props.history.push(`/addorupdate-Ticket/${id}`);
    }

    componentDidMount(){
        this.refreshTickets();
    }

    refreshTickets() {
        const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        showEmployee: user.roles.includes("ROLE_EMPLOYEE"),
        showAdmin: user.roles.includes("ROLE_ADMIN"),
        showEmployeeAndAdmin: user.roles.includes("ROLE_EMPLOYEE") || user.roles.includes("ROLE_ADMIN"),
      });
    }
        let config = { params: {} };
    
        if (this.state.searchFlightId  !== "") {
            config.params.flightId = this.state.searchFlightId;
          }
        if (this.state.searchUserID  !== "") {
            config.params.userId = this.state.searchUserID;
          }


        TicketService.getFlights().then((response) => {
            this.setState({ flights: response.data });
          });
          
        TicketService.getUsers().then((response) => {
            this.setState({ users: response.data });
          });

        TicketService.getTickets(config).then((response) => {
          this.setState({ tickets: response.data });
        });

      }

    addTicket(){
        this.props.history.push('/addorupdate-Ticket/_add');
    }

    render() {
        const { showAdmin } = this.state;
        const { showEmployee } = this.state;
        const {showEmployeeAndAdmin} = this.state;
        return (
            <div>
                <br/>
                
                 <div className="searchDiv">
                 <form onSubmit={this.handleSubmit}>
                   
                    <div className="form-group">
                    <label className="form-control">  Flight: 
                    <select name="searchFlightId" value={this.state.searchFlightId} onChange={this.handleChange}> 
                            <option value={''}> --- Odaberi ---</option>  
                            {this.state.flights.map(flight => (
                            <option value={flight.id}>{flight.destination}</option> ))}
                    </select>
                    </label>
                    </div>

                    <div className="form-group">
                    <label className="form-control">  User: 
                    <select name="searchUserID" value={this.state.searchUserID} onChange={this.handleChange}> 
                            <option value={''}> --- Odaberi ---</option>  
                            {this.state.users.map(user => (
                            <option value={user.id}>{user.username}</option> ))}
                    </select>
                    </label>
                    </div>

                    <div className="form-group">
                    <input type="submit" value="Search" />
                    </div>
                </form>
                </div>
                
                <br/>  
                 <h2 className="text-center">Tickets List</h2>

                 {showEmployee && (
                 <div className="addButtonDiv">
                    <button className="btn btn-primary btn-block" onClick={this.addTicket}> Add Ticket</button>
                 </div>
                  )}
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered table-hover">

                            <thead>
                                <tr>
                                    <th> Ime</th>
                                    <th> Prezime</th>
                                    <th> jmbg</th>
                                    <th> Destination</th>
                                    <th> Datetime</th>
                                    <th> Price</th>
                                    {showEmployeeAndAdmin && (    <th> Actions</th>  )}
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.tickets.map(
                                        ticket => 
                                        <tr key = {ticket.id}>                                            
                                             <td> {ticket.userFirstname}</td>
                                             <td> {ticket.userLastname}</td>
                                             <td> {ticket.userJmbg}</td>
                                             <td> {ticket.flightDestination} </td>   
                                             <td> {ticket.flightDatetime}</td>
                                             <td> {ticket.flightPrice}</td>
                                            
                                             <td>
                                                 <button onClick={ () => this.editTicket(ticket.id)} className="btn btn-info">Update </button>
                                                 {showAdmin && (
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteTicket(ticket.id)} className="btn btn-danger">Delete </button>
                                                 )}
                                             </td>
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

export default ListTicketsComponent
