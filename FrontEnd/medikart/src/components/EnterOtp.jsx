import React from "react";
import { Form, FormGroup, Label,Input,FormFeedback } from "reactstrap";
import Footer from "./Footer";
import Header from "./Header";
import { useState, } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {toast} from "react-toastify";

const EnterOtp=()=>{
    const USER_API_BASE_URL = "http://localhost:8080/medi/api/user/verify";
    let navigate=useNavigate();

    const [data, setData] = useState({
        otp: "",
      });

    let otp1=data.otp;
    
    const handleChange = (e) => {
      const value = e.target.value;
          setData({
          ...data,
          [e.target.name]: value
      });
    };
const handleSubmit = (e) =>
{
    e.preventDefault();
    let email1=sessionStorage.getItem("email");
    console.log(email1)
    console.log(otp1)
    const options = {
      method: 'GET',
      url: 'http://localhost:8080/medi/api/user/verify',
      params: {otp: otp1, email: email1 }
    };
    
    axios.request(options).then(function (response) {
      let jwt=(response.data.jwt)
      let userr={
        role:"visitor"
      }
      let user=JSON.stringify(userr)
      sessionStorage.setItem("token", "Bearer "+jwt);
      sessionStorage.setItem("user",user)

      navigate("/NewPassword")
      

    }).catch(function (error) {
      console.error(error);
    });
}

    return(
        <div>
            <Header></Header>
            <div className="Login" >
          <div className="Auth-form-container">
          <form className="Auth-form" onSubmit={handleSubmit}>
          <h4 className="Auth-form-title">Let's Reset Your Password in few steps</h4>
          <div className="form-group mt-3"></div>
          <label>Step 2: Enter OTP</label>
              <input
                type="text"
                name="otp"
                value={data.otp}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder="Enter OTP"
              />
             
                
                <div/>
                
            <br></br>
            <button type="submit" className="btn btn-primary">Enter OTP</button>
            {/* <p>
                <a href="./ForgotPassword"> Forgot Password?</a>
            </p> */}
            {/* <div><button className="btn btn-primary" onClick={forgotPassword}>Forgot password?</button></div> */}
            
        
            <p className="forgot-password text-right mt-2">
            <a href="SignUp">Register here</a>
            </p>
            
          </form>
          </div>
        </div>

            <Footer></Footer>
        </div>
    )


    
}
export default EnterOtp;