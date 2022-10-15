import React from "react";
import { Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import {
  Card,
  CardBody,
  //CardTitle,
  CardSubtitle,
  CardText,
  //CardFooter,
  Button,
  CardImg,
  //Container,
} from "reactstrap";

const USER_API_BASE_URL = "http://localhost:8080/medi/api/cart/add";
const Products = ({ prod }) => {
  const sendId = (id) => {
    navigate("/ProductDetails/" + id);
  };

  const navigate = useNavigate();
  let userRole = JSON.parse(sessionStorage.getItem("user"));
  const addToCartHandler = () => {
    // if (userRole.role === "ROLE_ADMIN") {
    //   navigate("/Admin");
    // } 
    if (sessionStorage.getItem("token")) {
      console.log("user is logged in");
      if(userRole==='ROLE_ADMIN'){
        navigate('/Admin')
      }
      let addTOCart = {
        productId: prod.id,
        customerId: sessionStorage.getItem("userID"),
        quantity: 1,
      };
      console.log(addTOCart);
      toast.success("Product added successfully");

      axios.post(USER_API_BASE_URL, addTOCart).then((res) => {
        console.log(res.data);
      });
    } else {
      console.log("user is not logged in");
      // alert("Kindly Login First");
      toast.warning("Kindly Login First");
      navigate("/Login");
    }
  };
  return (
    <div style={{ backgroundColor: "transparent" }}>
      <Card
        onClick={() => sendId(prod.id)}
        style={{
          backgroundColor: "white",
          color: "light",
          width: "18rem",
          height: "22rem",
          margin: "-5px",
          // padding: "-2px"
        }}
      >
        <CardSubtitle className="title">
          <a href="./ProductDetails"></a> {prod.productName}
        </CardSubtitle>
        <img alt="Sample" className="cardImage" src={prod.imageUrl} />
        <CardText className="body">GenericName:{prod.genericName} </CardText>
        <CardText className="body">CompanyName:{prod.companyName}</CardText>
        <CardText className="body">Price: {prod.unitPrice}₹/unit</CardText>
      </Card>
       
       {(sessionStorage.getItem("token") && sessionStorage.getItem("userRole").includes("ADMIN")) 
       ?
       <></>
      
      :<Button
      style={{ margin: "5px" }}
      color="primary"
      onClick={addToCartHandler}
    >
      Add to Cart
    </Button>
      }
      
       
    </div>
  );
};
export default Products;
