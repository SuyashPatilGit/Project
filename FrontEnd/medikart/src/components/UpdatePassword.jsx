import React from "react";
import Button from "react-bootstrap/esm/Button";
import {
  Container,
  Form,
  Row,
  Col,
  FormGroup,
  Label,
  Input,
  handleChange,
} from "reactstrap";
import Footer from "./Footer";
import Header from "./Header";
import "./Home.css";
import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const USER_API_BASE_URL =
  "http://localhost:8080/medi/api/customer/updatepassword";

const UpdatePassword = () => {
  let navigate = useNavigate();

  let getUserEmail = JSON.parse(sessionStorage.getItem("user")).email;
  const [data, setData] = useState({
    email: getUserEmail,
    oldPassword: "",
    newPassword: "",
  });
  const handleChange = (e) => {
    const value = e.target.value;
    setData({
      ...data,
      [e.target.name]: value,
    });
  };
  console.log(data);

  //=============================================================================================================
  //   let getUserEmail=(JSON.parse(sessionStorage.getItem("user")).email)
  console.log(getUserEmail);
  let token1 = sessionStorage.getItem("token");
  console.log(data);
  const handleSubmit = (e) => {
    e.preventDefault();
    axios
      .put(USER_API_BASE_URL, data, {
        headers: {
          Authorization: token1,
        },
      })
      .then((Response) => {
        toast.success("Your Password has been updated successfully ! ");
        navigate("/");
      }).catch = (error) => {
      console.log(error);
      toast.danger("Invalid current password ! ");
    };
  };

  return (
    <div >
      <Header />
      
        {/* <Form className="Address" style={{alignItems:"baseline"}}>
                <Row>
                    <Col md={6}>
                        <FormGroup>
                        <Label for="exampleAddress"> Email</Label>
                        <Input id="exampleAddress" name="oldPassword" placeholder={getUserEmail} type="email" disabled/>
                        </FormGroup>
                    </Col>
                </Row>
                <Row>
                    <Col md={6}>
                        <FormGroup>
                        <Label for="exampleAddress"> Old Password</Label>
                        <Input id="exampleAddress" name="oldPassword" placeholder="Enter old password" type="password"
                        onChange={handleChange} />
                        </FormGroup>
                    </Col>
                </Row>
                <Row>
                    <Col md={6}>
                        <FormGroup>
                        <Label for="exampleAddress"> New Password</Label>
                        <Input id="exampleAddress" name="newPassword" placeholder="Enter new password" type="password" onChange={handleChange} />
                        </FormGroup>
                    </Col>
                </Row>
                <Row>
                    <Col md={6}>
                        <FormGroup>
                        <Label for="exampleAddress"> Confirm New Password</Label>
                        <Input id="exampleAddress" name="confirmNewPassword" placeholder="Confirm new password" type="password" onChange={handleChange} />
                        </FormGroup>
                    </Col>
                </Row>
                <Row>
                    <Col md={3}>
                    <Button color="success" onClick={updatePassword}>Confirm</Button>
                    </Col>
                </Row>
                </Form> */}
        <div className="Login">
          <div className="Auth-form-container">
            <form className="Auth-form" onSubmit={handleSubmit}>
              <h3 className="Auth-form-title">Update Password</h3>
              <div className="form-group mt-3"></div>
              <label>Email address</label>
              <input
                type="email"
                name="email"
                value={data.email}
                onChange={handleChange}
                className="form-control mt-1"
                placeholder={(JSON.parse(sessionStorage.getItem("user"))).email}
                disabled
              />
              <div className="form-group mt-3">
                <label style={{ textAlign: "left" }}>Old Password</label>
                <input
                  type="password"
                  name="oldPassword"
                  value={data.oldPassword}
                  onChange={handleChange}
                  className="form-control mt-1"
                  placeholder="Enter Password"
                />
              </div>
              <div className="form-group mt-3">
                <label style={{ textAlign: "left" }}>New Password</label>
                <input
                  type="password"
                  name="newPassword"
                  value={data.newPassword}
                  onChange={handleChange}
                  className="form-control mt-1"
                  placeholder="Enter new Password"
                />
              </div>
              <div className="form-group mt-3">
                <label style={{ textAlign: "left" }}>Confirm New Password</label>
                <input
                  type="password"
                  name="confirmPassword"
                  //value={data.password}
                  onChange={handleChange}
                  className="form-control mt-1"
                  placeholder="Confirm new Password"
                />
              </div>

              <div />

              <br></br>
              <button style={{margin:"30px"}} type="submit" className="btn btn-primary">
                Change Password
              </button>
              
            </form>
          </div>
        </div>
      
      <Footer />
    </div>
  );
};
export default UpdatePassword;
