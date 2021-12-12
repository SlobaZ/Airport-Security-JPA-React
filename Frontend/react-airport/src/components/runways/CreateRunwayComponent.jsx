import React, { Component } from 'react'
import RunwayService from '../../services/RunwayService';

class CreateRunwayComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            name: ''
        }
        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.saveOrUpdateRunway = this.saveOrUpdateRunway.bind(this);
    }


    componentDidMount(){

        if(this.state.id === '_add'){
            return
        }else{
            RunwayService.getRunwayById(this.state.id).then( (res) =>{
                let runway = res.data;
                this.setState({name: runway.name
                });
            });
        }        
    }
    saveOrUpdateRunway = (e) => {
        e.preventDefault();
        let runway = {name: this.state.name};                        
        console.log('runway => ' + JSON.stringify(runway));
        if(this.state.id === '_add'){
            RunwayService.createRunway(runway).then(res =>{
                this.props.history.push('/runways');
            });
        }else{
            RunwayService.updateRunway(runway, this.state.id).then( res => {
                this.props.history.push('/runways');
            });
        }
    }
    
    changeNameHandler= (event) => {
        this.setState({name: event.target.value});
    }


    cancel(){
        this.props.history.push('/runways');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Runway</h3>
        }else{
            return <h3 className="text-center">Update Runway</h3>
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

                                        <button className="btn btn-success" onClick={this.saveOrUpdateRunway}>Save</button>
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

export default CreateRunwayComponent
