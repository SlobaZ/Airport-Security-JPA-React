import React, { Component } from 'react'
import PlaneService from '../../services/PlaneService';

class CreatePlaneComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            name: '',
            brand: '',
            model: '',
            seats: ''
        }
        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.changeBrandHandler = this.changeBrandHandler.bind(this);
        this.changeModelHandler = this.changeModelHandler.bind(this);
        this.changeSeatslHandler = this.changeSeatslHandler.bind(this);
        this.saveOrUpdatePlane = this.saveOrUpdatePlane.bind(this);
    }


    componentDidMount(){

        if(this.state.id === '_add'){
            return
        }else{
            PlaneService.getPlaneById(this.state.id).then( (res) =>{
                let plane = res.data;
                this.setState({name: plane.name,
                    brand: plane.brand,
                    model: plane.model,
                    seats: plane.seats
                });
            });
        }        
    }
    saveOrUpdatePlane = (e) => {
        e.preventDefault();
        let plane = {name: this.state.name, brand: this.state.brand, model: this.state.model, seats: this.state.seats};                        
        console.log('plane => ' + JSON.stringify(plane));
        if(this.state.id === '_add'){
            PlaneService.createPlane(plane).then(res =>{
                this.props.history.push('/planes');
            });
        }else{
            PlaneService.updatePlane(plane, this.state.id).then( res => {
                this.props.history.push('/planes');
            });
        }
    }
    
    changeNameHandler= (event) => {
        this.setState({name: event.target.value});
    }

    changeBrandHandler= (event) => {
        this.setState({brand: event.target.value});
    }

    changeModelHandler= (event) => {
        this.setState({model: event.target.value});
    }

    changeSeatslHandler= (event) => {
        this.setState({seats: event.target.value});
    }


    cancel(){
        this.props.history.push('/planes');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Plane</h3>
        }else{
            return <h3 className="text-center">Update Plane</h3>
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
                                            <label> Name: </label>
                                            <input placeholder="Name" name="name" className="form-control" 
                                                value={this.state.name} onChange={this.changeNameHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Brand: </label>
                                            <input placeholder="Brand" name="brand" className="form-control" 
                                                value={this.state.brand} onChange={this.changeBrandHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Model: </label>
                                            <input placeholder="Model" name="model" className="form-control" 
                                                value={this.state.model} onChange={this.changeModelHandler}/>
                                        </div>    
                                        <div className = "form-group">
                                            <label> Seats: </label>
                                            <input placeholder="Seats" name="seats" className="form-control" 
                                                value={this.state.seats} onChange={this.changeSeatslHandler}/>
                                        </div>                                    

                                        <button className="btn btn-success" onClick={this.saveOrUpdatePlane}>Save</button>
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

export default CreatePlaneComponent
