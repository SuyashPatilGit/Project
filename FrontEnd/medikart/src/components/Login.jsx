// import React from "react"
import "./Home.css";
import axios from "axios";
import Home from "./Home";
import React, { useState } from "react";
import ReCAPTCHA from "react-google-recaptcha";

import {navigate, useNavigate} from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";
import { toast } from "react-toastify";

const Login = () => {
 
const [isVerified, setIsVerified] = useState(false);
 const handleChangeRecaptcha = (value) => {
    console.log("Captcha value:", value);
    setIsVerified(true);
  };

  let navigate=useNavigate();
  const forgotPassword=()=>{

    navigate.apply("/ForgotPassword");

  }




  const [data, setData] = useState({
    email: "",
    password: ""
  });


const handleChange = (e) => {
  const value = e.target.value;
      setData({
      ...data,
      [e.target.name]: value
  });
};

const USER_API_BASE_URL = "http://localhost:8080/medi/api/auth/signin";
const handleSubmit = (e) =>
{
  e.preventDefault();
    const userData = {
      email: data.email,
      password: data.password
    };
  axios.post(USER_API_BASE_URL, userData).then(
    (response)=>{
        console.log(response);
        // JSON.stringify(response);
        sessionStorage.setItem("token","Bearer "+response.data.jwt);
        sessionStorage.setItem("user", JSON.stringify(response.data.user));
        
        let user=JSON.parse(sessionStorage.getItem("user"));
        sessionStorage.setItem("userRole",JSON.stringify(user.role));
        console.log(sessionStorage.getItem("userRole"))
        let uR=sessionStorage.getItem("userRole")
        console.log(uR)
        if(uR.includes('ROLE_ADMIN')){
          navigate('/Admin');
        }else{
          navigate("/");
        }
        sessionStorage.setItem("userID",user.id);
        console.log(sessionStorage.getItem("userID"));
        
        
    },
    (error)=>{
        console.log(error);
        toast.warning("Invalid credentials")
    }
);
}


return (
   
    <div>
      <Header></Header>
        <div className="Login" >
          <div className="Auth-form-container">
          <form className="Auth-form" onSubmit={handleSubmit}>
          <h3 className="Auth-form-title">Sign In</h3>
          <div className="form-group mt-3"></div>
          <label>Email address</label>
              <input
                type="email"
                name="email"
                value={data.email}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter Email"
              />
              <div className="form-group mt-3" >
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
                <div style={{marginLeft: "20px", marginTop:"10px"}}>
                <ReCAPTCHA
              sitekey="6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI"
              onChange={handleChangeRecaptcha}
            />
                </div>
                
                
                <div/>
                
            <br></br>
            <button type="submit" className="btn btn-primary">Login</button>
            <p>
                <a href="./ForgotPassword"> Forgot Password?</a>
            </p>
            {/* <div><button className="btn btn-primary" onClick={forgotPassword}>Forgot password?</button></div> */}
            
        
            <p className="forgot-password text-right mt-2">
            <a href="SignUp">Register here</a>
            </p>
            
          </form>
          </div>
        </div>
        <Footer></Footer>
        </div>
  );

}
export default Login;

  