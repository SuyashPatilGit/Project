// import React, { useState } from 'react';
// import { Link } from 'react-router-dom';
// import {
//   Collapse,
//   Navbar,
//   NavbarToggler,
//   NavbarBrand,
//   Nav,
//   NavItem,
//   NavLink,
//   UncontrolledDropdown,
//   DropdownToggle,
//   DropdownMenu,
//   DropdownItem,
//   NavbarText,
// } from 'reactstrap';
// // import React, { useState } from 'react';
// // import { Navigate } from "react-router-dom";
// // import {
// //   Collapse,
// //   Navbar,
// //   NavbarToggler,
// //   NavbarBrand,
// //   Nav,
// //   NavItem,
// //   NavLink,
// // } from 'reactstrap';

// // function Header(props) {
// //   const [collapsed, setCollapsed] = useState(true);

// //   const toggleNavbar = () => setCollapsed(!collapsed);

// //   return (
// //     <div>
// //       <Navbar color="dark" >
// //         <NavbarBrand href="/" className="me-auto">
// //           <h3>MediKart</h3>
// //         </NavbarBrand>
// //         <NavbarToggler onClick={toggleNavbar} className="me-2" />
// //         <Collapse isOpen={!collapsed} navbar>
// //           <Nav navbar>
// //             <NavLink href="http://localhost:8080/medi/api/product/allProducts">
// //               Register
// //               </NavLink>
// //             <NavItem>
// //               <NavLink href="http://localhost:3000/Login">
// //                 Login
// //               </NavLink>
// //             </NavItem>
// //             <NavItem>
// //               <NavLink href="http://localhost:8080/medi/api/product/allProducts">
// //                 Contact us
// //               </NavLink>
// //             </NavItem>
// //           </Nav>
// //         </Collapse>
// //       </Navbar>
// //     </div>
// //   );
// // }
// // // const Header=()=>{
// // //   return(
// // // <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
// // //   <div class="container-fluid">
// // //     <a class="navbar-brand" href="#">MediKart</a>
// // //     <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
// // //       <span class="navbar-toggler-icon"></span>
// // //     </button>
// // //     <div class="collapse navbar-collapse" id="navbarColor01">
// // //       <ul class="navbar-nav me-auto">
// // //         <li class="nav-item">
// // //           <a class="nav-link active" href="#">Home
// // //             <span class="visually-hidden">(current)</span>
// // //           </a>
// // //         </li>
// // //         <li class="nav-item">
// // //           <a class="nav-link" href="#">Features</a>
// // //         </li>
// // //         <li class="nav-item">
// // //           <a class="nav-link" href="#">Pricing</a>
// // //         </li>
// // //         <li class="nav-item">
// // //           <a class="nav-link" href="#">About</a>
// // //         </li>
// // //         <li class="nav-item dropdown">
// // //           <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Dropdown</a>
// // //           <div class="dropdown-menu">
// // //             <a class="dropdown-item" href="#">Action</a>
// // //             <a class="dropdown-item" href="#">Another action</a>
// // //             <a class="dropdown-item" href="#">Something else here</a>
// // //             <div class="dropdown-divider"></div>
// // //             <a class="dropdown-item" href="#">Separated link</a>
// // //           </div>
// // //         </li>
// // //       </ul>
// // //       <form class="d-flex">
// // //         {/* <input class="form-control me-sm-2" type="text" placeholder="Search"> */}
// // //         <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
// // //       </form>
// // //     </div>
// // //   </div>
// // // </nav>);
// // // }
// function Header(args) {
//   const [isOpen, setIsOpen] = useState(false);

//   const toggle = () => setIsOpen(!isOpen);

// return(
// // const Header= () => {
//   <div>
//   {/* <Navbar
//       className="my-2"
//       color="dark"
//       dark
//     >
//       <NavbarBrand href="/">
//         <img
//           alt="logo"
//           src="/logo-white.svg"
//           style={{
//             height: 40,
//             width: 40
//           }}
//         />
//         MediKart
//       </NavbarBrand>
//     </Navbar> */}
//     <Navbar {...args} color="dark">
//         <NavbarBrand href="/">MediKart</NavbarBrand>
//         <NavbarToggler onClick={toggle} />
//         <Collapse isOpen={isOpen} navbar>
//           <Nav className="me-auto" navbar>
//             <NavItem>
//               <NavLink href="Login">Login</NavLink>

//             </NavItem>
//             <NavItem>
//               <NavLink href="SignUp">
//                 Register
//               </NavLink>
//             </NavItem>
//             <UncontrolledDropdown nav inNavbar>
//               <DropdownToggle nav caret>
//                 Options
//               </DropdownToggle>
//               <DropdownMenu right>
//                 <DropdownItem>Sign Up</DropdownItem>
//                 <DropdownItem>Login</DropdownItem>
//                 <DropdownItem divider />
//                 <DropdownItem>Contact Us</DropdownItem>
//               </DropdownMenu>
//             </UncontrolledDropdown>
//           </Nav>
//           <NavbarText>Simple Text</NavbarText>
//         </Collapse>
//       </Navbar>
//     </div>
//   );
//   }

// export default Header;

import axios from "axios";
import { useState } from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { toast } from "react-toastify";
import Admin from "./Admin";
import {
  Dropdown,
  NavItem,
  DropdownMenu,
  DropdownToggle,
  DropdownItem,
  UncontrolledDropdown,
  Form,
  Button,
} from "reactstrap";

const Header = () => {
  const [Interval, setInterval] = useState([]);
  let userRole = JSON.parse(sessionStorage.getItem("user"));
  console.log(userRole);
  const clearSession = () => {
    setInterval(() => {
      toast.success("You Have been Succesfully Signed Out");
    }, 3000);

    sessionStorage.clear();
  };

  let user = JSON.parse(sessionStorage.getItem("user"));
  let userName;
  if (user) {
    userName = user.firstName;
  }
  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" dark="false">
      <Navbar.Brand href="/" style={{ marginLeft: "20px" }}>
        MediKart
      </Navbar.Brand>

      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav">
        <Nav>
          {sessionStorage.getItem("token") && userRole.role === "ROLE_ADMIN" && (
            <NavItem
              className="userNav"
              style={{ alignContent: "right", marginLeft: "1300px" }}
            >
              <UncontrolledDropdown nav inNavbar>
                <DropdownToggle nav caret>
                  Welcome Admin
                </DropdownToggle>
                <DropdownMenu right>
                  <DropdownItem href="/AddShipment">
                    Add new Shipment
                  </DropdownItem>
                  <DropdownItem href="/SupplierList">
                    Add new Supplier
                  </DropdownItem>
                  <DropdownItem href="/AddnewDB">
                    Add new Dilevery Boy
                  </DropdownItem>
                  <DropdownItem divider />
                  <DropdownItem>Get list of Customers</DropdownItem>
                  <DropdownItem href="./" onClick={clearSession}>
                    Sign Out
                  </DropdownItem>
                </DropdownMenu>
              </UncontrolledDropdown>
            </NavItem>
          )}
        </Nav>

        <Nav>
          {sessionStorage.getItem("token") && userRole.role === "ROLE_ADMIN" && (
            <NavItem>
              <Nav.Link href="./Admin">Dashboard</Nav.Link>
            </NavItem>
          )}
        </Nav>

        {!sessionStorage.getItem("token") && (
          <Nav className="me-auto">
            <NavItem style={{ marginLeft: "50px" }}>
              <Nav.Link href="./SignUp">Register</Nav.Link>
            </NavItem>
            <NavItem style={{ marginLeft: "50px" }}>
              <Nav.Link href="./Login">Login</Nav.Link>
            </NavItem>
          </Nav>
        )}

        <Nav>
          {sessionStorage.getItem("token") &&
            !(userRole.role === "ROLE_ADMIN") && (
              <NavItem style={{ marginLeft: "50px" }}>
                <Nav.Link href="/Cart"> Cart</Nav.Link>
              </NavItem>
            )}
        </Nav>

        <Nav>
          {sessionStorage.getItem("token") &&
            !(userRole.role === "ROLE_ADMIN") && (
              <NavItem style={{ marginLeft: "100px", marginRight: "10px" }}>
                <Nav.Link href="/AboutUs">AboutUs</Nav.Link>
              </NavItem>
            )}
        </Nav>
        {/* <Nav>
          <NavItem>
          {sessionStorage.getItem("token")&&
            <Nav.Link href="./" onClick={clearSession}>
              Sign Out
            </Nav.Link>
            }
          </NavItem>
          </Nav> */}
        {sessionStorage.getItem("token") && (
          <Nav>
            <NavItem
              className="userNav"
              style={{ alignContent: "right", marginLeft: "1000px" }}
            >
              <UncontrolledDropdown nav inNavbar>
                <DropdownToggle nav caret>
                  Welcome {userName}
                </DropdownToggle>
                <DropdownMenu right>
                  <DropdownItem href="/updatePassword">
                    Update Password
                  </DropdownItem>
                  <DropdownItem href="/viewOrders">View Orders</DropdownItem>
                  <DropdownItem divider />
                  <DropdownItem>update profile</DropdownItem>
                  <DropdownItem href="./" onClick={clearSession}>
                    Sign Out
                  </DropdownItem>
                </DropdownMenu>
              </UncontrolledDropdown>
            </NavItem>
          </Nav>
        )}
      </Navbar.Collapse>
    </Navbar>
  );
};

export default Header;
