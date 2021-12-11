import React, { Component } from 'react'
import UserService from '../../services/UserService';
import AuthService from "../../services/auth.service";

class CreateUserComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            username: '',
            password: '',
            firstname: '',
            lastname: '',
            jmbg: '',
            city: ''
        }
        this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.changeFirstnameHandler = this.changeFirstnameHandler.bind(this);
        this.changeLastnamelHandler = this.changeLastnamelHandler.bind(this);
        this.changeJmbgHandler = this.changeJmbgHandler.bind(this);
        this.changeCityHandler = this.changeCityHandler.bind(this);
        this.updateUser = this.updateUser.bind(this);
    }


    componentDidMount(){
        const user = AuthService.getCurrentUser();
        if (user) {
          this.setState({
            currentUser: user,
            showEmployee: user.roles.includes("ROLE_EMPLOYEE"),
            showAdmin: user.roles.includes("ROLE_ADMIN"),
          });
        }
            UserService.getUserById(this.state.id).then( (res) =>{
                let user = res.data;
                this.setState({username: user.username,
                    password : user.password,
					firstname: user.firstname,
					lastname: user.lastname,
					jmbg: user.jmbg,
					city: user.city
                });
            });
        }        
   
    updateUser = (e) => {
        e.preventDefault();
        let user = {username: this.state.username, password: this.state.password,firstname: this.state.firstname,lastname: this.state.lastname,jmbg: this.state.jmbg,city: this.state.city};
        console.log('user => ' + JSON.stringify(user));

            UserService.updateUser(user, this.state.id).then( res => {
                this.props.history.push('/users');
            });
       
    }
    
    changeUsernameHandler= (event) => {
        this.setState({username: event.target.value});
    }

    changePasswordHandler= (event) => {
        this.setState({password: event.target.value});
    }

    changeFirstnameHandler= (event) => {
        this.setState({firstname: event.target.firstname});
    }
    changeLastnamelHandler= (event) => {
        this.setState({lastname: event.target.value});
    }
    changeJmbgHandler= (event) => {
        this.setState({jmbg: event.target.value});
    }
    changeCityHandler= (event) => {
        this.setState({city: event.target.value});
    }
    

    cancel(){
        this.props.history.push('/users');
    }


    render() {
        const { showEmployee , showAdmin } = this.state;
        return (
            <div>
                <br></br>
                {showEmployee && (
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                    <h3 className="text-center">Update User</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Username: </label>
                                            <input placeholder="Username" name="username" className="form-control" 
                                                value={this.state.username} onChange={this.changeUsernameHandler}/>
                                        </div>
                                        {showAdmin && ( 
                                        <div className = "form-group">
                                            <label> Password: </label>
                                            <input placeholder="Password" name="password" className="form-control" 
                                                value={this.state.password} onChange={this.changePasswordHandler}/>
                                        </div>
                                        )}
                                        <div className = "form-group">
                                            <label> First name: </label>
                                            <input placeholder="Firstname" name="firstname" className="form-control" 
                                                value={this.state.firstname} onChange={this.changeFirstnameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Last name: </label>
                                            <input placeholder="Lastname" name="lastname" className="form-control" 
                                                value={this.state.lastname} onChange={this.changeLastnamelHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> JMBG: </label>
                                            <input placeholder="Jmbg" name="jmbg" className="form-control" 
                                                value={this.state.jmbg} onChange={this.changeJmbgHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> City: </label>
                                            <input placeholder="City" name="city" className="form-control" 
                                                value={this.state.city} onChange={this.changeCityHandler}/>
                                        </div>

                                        <button className="btn btn-success" onClick={this.updateUser}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
                )}
            </div>
        )
    }
}

export default CreateUserComponent
