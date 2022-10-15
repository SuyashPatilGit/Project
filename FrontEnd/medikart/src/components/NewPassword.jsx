import React from "react";
import { Form, FormGroup, Label, Input, FormFeedback } from "reactstrap";
import Footer from "./Footer";
import Header from "./Header";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const NewPassword = () => {

  const USER_API_BASE_URL = "http://localhost:8080/medi/api/user/newpassword";
  let navigate = useNavigate();
  let email1 = sessionStorage.getItem("email");

  const [data, setData] = useState({
    newPassword: "",
    email: email1,
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setData({
      ...data,
      [e.target.name]: value,
    });
  };
  const handleSubmit = (e) => {
    let token=sessionStorage.getItem("token")
    e.preventDefault();
    const options = {
      method: "POST",
      url: "http://localhost:8080/medi/api/user/newpassword",
      data: { email: data.email, newPassword: data.newPassword },
      headers:{
        Authorization: token
      }
    };

    axios
      .request(options)
      .then(function (response) {
        console.log(response.data);
        toast.success("Your Password has been changed successfully");
        navigate("/Login")
        sessionStorage.clear();
      })
      .catch(function (error) {
        console.error(error);
        toast.warning("unexpected error");
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
            <label>Step no.3 Enter new Password</label>
            <input
              type="text"
              name="newPassword"
              value={data.newPassword}
              onChange={handleChange}
              className="form-control mt-1"
              placeholder="Enter new Password"
            />

            <div />

            <br></br>
            <button type="submit" className="btn btn-primary">
              change password
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
export default NewPassword;
