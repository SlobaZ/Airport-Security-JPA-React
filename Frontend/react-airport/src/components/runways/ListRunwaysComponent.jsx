import React, { Component } from 'react'
import RunwayService from '../../services/RunwayService'
import AuthService from "../../services/auth.service";

class ListRunwaysComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                runways: [],
                searchName: ''
                
        };
        
        this.addRunway = this.addRunway.bind(this);
        this.editRunway = this.editRunway.bind(this);
        this.deleteRunway = this.deleteRunway.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        
    }

    handleChange(event) {
       this.setState({[event.target.name]: event.target.value});  
    }
    
    handleSubmit(event) {
        event.preventDefault();  
       this.refreshRunways();
    }

    deleteRunway(id){
        RunwayService.deleteRunway(id).then( response => {
            this.setState({runways: this.state.runways.filter(runway => runway.id !== id)});
        });
    }

    editRunway(id){
        this.props.history.push(`/addorupdate-Runway/${id}`);
    }

    componentDidMount(){
        this.refreshRunways();
    }

    refreshRunways() {
        const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        showEmployee: user.roles.includes("ROLE_EMPLOYEE"),
        showAdmin: user.roles.includes("ROLE_ADMIN"),
      });
    }
        let config = { params: {} };
    
        if (this.state.name !== "") {
          config.params.name = this.state.searchName;
        }
        
        RunwayService.getRunways(config).then((response) => {
          this.setState({ runways: response.data });
        });
      }

    addRunway(){
        this.props.history.push('/addorupdate-Runway/_add');
    }

    render() {
        const { showAdmin } = this.state;
        return (
            <div>
                <br/>
                
                 <div className="searchDiv">
                 <form onSubmit={this.handleSubmit}>
                   
                    <div className="form-group">
                    <label className="form-control">  Name: 
                    <input type="text" name="searchName" value={this.state.searchName} onChange={this.handleChange}/>
                    </label>
                    </div>

                    <div className="form-group">
                    <input type="submit" value="Search" />
                    </div>
                </form>
                </div>
                
                <br/>  
                 <h2 className="text-center">Runways List</h2>

                 {showAdmin && (
                 <div className="addButtonDiv">
                    <button className="btn btn-primary btn-block" onClick={this.addRunway}> Add Runway</button>
                 </div>
                  )}
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered table-hover">

                            <thead>
                                <tr>
                                    <th> Name</th>
                                    {showAdmin && (    <th> Actions</th>  )}
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.runways.map(
                                        runway => 
                                        <tr key = {runway.id}>
                                             <td> {runway.name} </td> 
                                             {showAdmin && ( 
                                             <td>
                                                 <button onClick={ () => this.editRunway(runway.id)} className="btn btn-info">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteRunway(runway.id)} className="btn btn-danger">Delete </button>
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

export default ListRunwaysComponent
