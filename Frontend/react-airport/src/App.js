import React, { Component } from "react";
import { HashRouter, Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import logo from './logo.jpg';

import AuthService from "./services/auth.service";

import Login from "./components/login.component";
import Register from "./components/register.component";
import Home from "./components/home.component";
import Profile from "./components/profile.component";
import ListPlanesComponent from "./components/planes/ListPlanesComponent.jsx";
import CreatePlaneComponent from "./components/planes/CreatePlaneComponent.jsx";
import ListUsersComponent from "./components/users/ListUsersComponent.jsx";
import CreateUserComponent from "./components/users/CreateUserComponent.jsx";
import ListRunwaysComponent from "./components/runways/ListRunwaysComponent.jsx";
import CreateRunwayComponent from "./components/runways/CreateRunwayComponent.jsx";
import ListFlightsComponent from "./components/flights/ListFlightsComponent";
import CreateFlightComponent from "./components/flights/CreateFlightComponent.jsx";
import ListTicketsComponent from "./components/tickets/ListTicketsComponent";
import CreateTicketComponent from "./components/tickets/CreateTicketComponent.jsx";
import BuyTicketComponent from "./components/buy/BuyTicketComponent.jsx";


class App extends Component {

  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showEmployee: false,
      showPassenger: false,
      showAdmin: false,
      showEmployeeAndAdmin: false,
      currentUser: false,
    };
  }

  componentDidMount() {
  
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
        showPassenger: user.roles.includes("ROLE_PASSENGER"),
        showEmployeeAndAdmin: user.roles.includes("ROLE_EMPLOYEE") || user.roles.includes("ROLE_ADMIN"),

      });
    }
  }

  logOut() {
    AuthService.logout();
    this.setState({
      showPassenger: false,
      showEmployeeAndAdmin: false,
      currentUser: false,
    });
  }

  render() {
    const { currentUser, showPassenger, showEmployeeAndAdmin} = this.state;

    return (
      <div>      
      
      <nav className="navbar navbar-expand-md navbar-dark bg-dark">
        
        <div className="logo">
        <img src={logo} width="50" height="50" alt="Logo" />
        </div>
        
          <Link to={"/"} className="navbar-brand">
            Airport
          </Link>
          <div className="navbar-nav mr-auto ml-4">
           
            <li className="nav-item ml-3">
              <Link to={"/planes"} className="nav-link">
                Planes
              </Link>
            </li>

            <li className="nav-item ml-3">
              <Link to={"/runways"} className="nav-link">
                Runways
              </Link>
            </li>

            <li className="nav-item ml-3">
              <Link to={"/flights"} className="nav-link">
                Flights
              </Link>
            </li>
            
            
            
            { showEmployeeAndAdmin &&  (
              <li className="nav-item ml-3">
                <Link to={"/users"} className="nav-link">
                  Users
                </Link>
              </li>
            )}

            { showEmployeeAndAdmin &&  (
            <li className="nav-item ml-3">
              <Link to={"/tickets"} className="nav-link">
                Tickets
              </Link>
            </li>
            )}

          { showPassenger &&  (
            <li className="nav-item ml-3">
              <Link to={"/buy"} className="nav-link">
                Buy
              </Link>
            </li>
            )}


          </div>
          {currentUser ? (
            <div className="navbar-nav ml-auto mr-4">
             
              <li className="nav-item mr-4">
                <Link to={"/profile"} className="nav-link">
                  {currentUser.username}
                </Link>
              </li>
              <li className="nav-item mr-1">
                <a href="/" className="nav-link" onClick={this.logOut}>
                  LogOut
                </a>
              </li>
            </div>
          ) : (
            <div className="navbar-nav ml-auto mr-4" >
              <li className="nav-item mr-4">
                <Link to={"/login"} className="nav-link">
                  Login
                </Link>
              </li>

              <li className="nav-item mr-1">
                <Link to={"/register"} className="nav-link">
                  Sign Up
                </Link>
              </li>
            </div>
          )}
        </nav>

       <div className="container mt-3" >
       <HashRouter>
          <Switch>
            <Route exact path="/" component={Home} />
            <Route path="/planes" component={ListPlanesComponent} />
            <Route path="/addorupdate-plane/:id" component={CreatePlaneComponent} /> 
            <Route path="/users" component={ListUsersComponent} />
	        <Route path="/update-user/:id" component={CreateUserComponent} />
            <Route path="/runways" component={ListRunwaysComponent} />
            <Route path="/addorupdate-runway/:id" component={CreateRunwayComponent} /> 
            <Route path="/flights" component={ListFlightsComponent} />
            <Route path="/addorupdate-flight/:id" component={CreateFlightComponent} /> 
            <Route path="/tickets" component={ListTicketsComponent} />
            <Route path="/addorupdate-ticket/:id" component={CreateTicketComponent} /> 
            <Route path="/buy" component={BuyTicketComponent} />
            <Route path="/login" component={Login} />
            <Route path="/register" component={Register} />
            <Route path="/profile" component={Profile} />
          </Switch>
         </HashRouter>
        </div>
      </div>
    );
  }
}

export default App;
