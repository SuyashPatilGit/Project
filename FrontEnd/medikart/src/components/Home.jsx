import React, { useEffect, useState } from "react";
import AllProducts from "./AllProducts";
import Products from "./Products";
import Header from "./Header";
import "./Home.css";
import Footer from "./Footer";
import Login from "./Login";
import SignUp from "./SignUp";
import Example from "./Header";
import Crousel from "./Crousel";
import { useNavigate } from "react-router-dom";
import Admin from './Admin';

const Home = () => {
  let userRole = JSON.parse(sessionStorage.getItem("user"));
  
  let navigate = useNavigate();

//   useEffect(() => {
//     if (userRole.role === null) {
//       navigate("/Home");
//     } else if(userRole.role === 'ROLE_ADMIN') {
//       navigate("/Admin");
//     }
//   }, []);

  return (
    <div className="Home">
      {
        <div>
          <Header />
          <div className="title" style={{ margin: "50px" }}>
            <h2>Welcome to MediKart </h2>
          </div>
          <Crousel></Crousel>
          <AllProducts />
          {/* <Login/> 
                <SignUp/>  */}
          <Footer />
        </div>
      }
    </div>
  );
};
export default Home;

//
