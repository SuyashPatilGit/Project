// import React from "react"
import "./Home.css";
import axios from "axios";
import Home from "./Home";
import React, { useState } from "react";
import Login from "./Login";
import {navigate,useNavigate} from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";
import { toast } from "react-toastify";


const SignUp = () => {
  const [data, setData] = useState({
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    dob: "",
    mobNo: "",
    city: "",
    state:""
  });


const handleChange = (e) => {
  const value = e.target.value;
      setData({
      ...data,
      [e.target.name]: value
  });
};

let navigate=useNavigate();
const USER_API_BASE_URL = "http://localhost:8080/medi/api/auth/user/signup";
const handleSubmit = (e) =>
{
  e.preventDefault();
    const userData = {
      email: data.email,
      password: data.password,
      firstName: data.firstName,
      lastName: data.lastName,
      dob: data.dob,
      mobNo: data.mobNo,
      city:data.city,
      state:data.state,
      dob:data.dob,
      role:"ROLE_CUSTOMER"
    };
  axios.post(USER_API_BASE_URL, userData).then(
    (response)=>{
        console.log(response);
        JSON.stringify(response);
        toast.success("You have been Registered successfully");
        navigate("/Login");
        
    },
    
).catch((error)=>
console.log(error.response),
{
  "firstName":"Please check FirstName cannot be less than 4 letters"

}
)
}


  return (
    
<div>
<Header></Header>
    <div className="SignUp" >
      
      <div className="Auth-form-container"  >
      <div className=" col-md-4 col-lg-6 bg-image">
      <div className="col-md-8 col-lg-6"></div>
      <div className="login d-flex align-items-center py-5" >
      <form className="Auth-form" onSubmit={handleSubmit} style={{marginTop:"200px"}}>
      <h3 className="Auth-form-title">Register here</h3>
      <div className="form-group mt-2"></div>
      <label>Email address</label>
          <input
            type="email"
            name="email"
            value={data.email}
            onChange={handleChange}
            className="form-control mt-2"
            placeholder="Enter Email"
          />
          <div className="form-group mt-2" >
          <label style={{textAlign : 'left'}}>Password</label>
            <input
               type="password"
                name="password"
                value={data.password}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter Password"
              />
            </div>
            < div className="form-group mt-3" >
            <label style={{textAlign : 'left'}}>First Name</label>
            <input
               type="text"
                name="firstName"
                value={data.firstName}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter Firstname"
              />
            
            
            <label style={{textAlign : 'left'}}>Last Name</label>
            <input
               type="text"
                name="lastName"
                value={data.lastName}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter Lastname"
              />
            </div>
            <div className="form-group mt-2" >
            <label style={{textAlign : 'left'}}>Mobile Nuber</label>
            <input
               type="text"
                name="mobNo"
                value={data.mobNo}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter Mobile Number"
              />
            </div>
            <div className="form-group mt-2" >
            <label style={{textAlign : 'left'}}>Date of Birth</label>
            <input
               type="date"
                name="dob"
                value={data.dob}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter DOB"
              />
            </div>
            <div className="form-group mt-2" >
            <label style={{textAlign : 'left'}}>City</label>
            <input
               type="text"
                name="city"
                value={data.city}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter city"
              />
            </div>
            <div className="form-group mt-2" >
            <label style={{textAlign : 'left'}}>State</label>
            <input
               type="text"
                name="state"
                value={data.state}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter state"
              />
            </div>
            
            <div/>
            
        <br></br>
        <button type="submit" className="btn btn-success">Sign Up</button>
        
        <p className="forgot-password text-right mt-2">
        <p>Already Registered ?</p>
        <a href="Login">Login here</a>
        </p>
        
        
      </form>
      </div>
      </div>
      </div>
    </div>
    <Footer></Footer>
    
    </div>
  );

}
export default SignUp;

  