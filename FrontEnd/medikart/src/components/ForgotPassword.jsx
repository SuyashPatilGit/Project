import React from "react";
import { Form, FormGroup, Label, Input, FormFeedback } from "reactstrap";
import Footer from "./Footer";
import Header from "./Header";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const ForgotPassword = () => {
  const USER_API_BASE_URL =
    "http://localhost:8080/medi/api/user/forgotpassword";
  let navigate = useNavigate();
  const [data, setData] = useState({
    email: "",
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setData({
      ...data,
      [e.target.name]: value,
    });
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    const userData = {
      email: data.email,
      //password: data.password
    };
    let sendEmail = userData.email;
    // axios.get(USER_API_BASE_URL+ email).then(
    //   (response) => {
    //     console.log(response);

    //     console.log(sessionStorage.getItem("userID"));
    //     navigate("/EnterOtp");
    //   },
    //   (error) => {
    //     console.log(error);
    //     toast.warning("Invalid Email Address");
    //   }
    // );

    const options = {
        method: 'GET',
        url: 'http://localhost:8080/medi/api/user/forgotpassword',
        params: {email: sendEmail}
      };
      
      axios.request(options).then(function (response) {
        console.log(response.data);
        sessionStorage.setItem("email",sendEmail);
        navigate('/EnterOtp');
      }).catch(function (error) {
        console.error(error);
      });


  };

  return (
    <div>
      <Header></Header>
      <div className="Login">
        <div className="Auth-form-container">
          <form className="Auth-form" onSubmit={handleSubmit}>
            <h3 className="Auth-form-title">Let's Reset Your Password</h3>
            <div className="form-group mt-3"></div>
            <label>Step 1: Enter Email address</label>
            <input
              type="email"
              name="email"
              value={data.email}
              onChange={handleChange}
              className="form-control mt-1"
              placeholder="Enter Email"
            />

            <div />

            <br></br>
            <button type="submit" className="btn btn-primary">
              Get OTP
            </button>
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
  );
};
export default ForgotPassword;
