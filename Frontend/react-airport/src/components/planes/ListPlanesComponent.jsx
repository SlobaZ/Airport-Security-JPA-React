import React, { Component } from 'react'
import PlaneService from '../../services/PlaneService'
import AuthService from "../../services/auth.service";

class ListPlanesComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                planes: [],
                searchName: '',
                searchBrand: ''
                
        };
        
        this.addPlane = this.addPlane.bind(this);
        this.editPlane = this.editPlane.bind(this);
        this.deletePlane = this.deletePlane.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        
    }

    handleChange(event) {
       this.setState({[event.target.name]: event.target.value});  
    }
    
    handleSubmit(event) {
        event.preventDefault();  
       this.refreshPlanes();
    }

    deletePlane(id){
        PlaneService.deletePlane(id).then( response => {
            this.setState({planes: this.state.planes.filter(plane => plane.id !== id)});
        });
    }

    editPlane(id){
        this.props.history.push(`/addorupdate-plane/${id}`);
    }

    componentDidMount(){
        this.refreshPlanes();
    }

    refreshPlanes() {
        const user = AuthService.getCurrentUser();
    if (user) {
      this.setState({
        currentUser: user,
        showEmployee: user.roles.includes("ROLE_EMPLOYEE"),
        showAdmin: user.roles.includes("ROLE_ADMIN"),
      });
    }
        let config = { params: {} };
    
        if (this.state.searchName !== "") {
          config.params.name = this.state.searchName;
        }
        if (this.state.searchBrand  !== "") {
          config.params.brand = this.state.searchBrand;
        }

        PlaneService.getPlanes(config).then((response) => {
          this.setState({ planes: response.data });
        });
      }

    addPlane(){
        this.props.history.push('/addorupdate-plane/_add');
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
                    <label className="form-control">  Brand: 
                    <input type="text" name="searchBrand" value={this.state.searchBrand} onChange={this.handleChange}/> 
                    </label>
                    </div>

                    <div className="form-group">
                    <input type="submit" value="Search" />
                    </div>
                </form>
                </div>
                
                <br/>  
                 <h2 className="text-center">Planes List</h2>

                 {showAdmin && (
                 <div className="addButtonDiv">
                    <button className="btn btn-primary btn-block" onClick={this.addPlane}> Add Plane</button>
                 </div>
                  )}
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered table-hover">

                            <thead>
                                <tr>
                                    <th> Name</th>
                                    <th> Brand</th>
                                    <th> Model</th>
                                    <th> Seats</th>
                                    {showAdmin && (    <th> Actions</th>  )}
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.planes.map(
                                        plane => 
                                        <tr key = {plane.id}>
                                             <td> {plane.name} </td>   
                                             <td> {plane.brand}</td>
                                             <td> {plane.model}</td>
                                             <td> {plane.seats}</td>
                                             {showAdmin && ( 
                                             <td>
                                                 <button onClick={ () => this.editPlane(plane.id)} className="btn btn-info">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deletePlane(plane.id)} className="btn btn-danger">Delete </button>
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

export default ListPlanesComponent
