import React, { useState, useEffect } from "react";
import axios from "axios";
import { Navigate, useNavigate } from "react-router-dom";

import Footer from "./Footer";
import Header from "./Header";
import { Button, Container, Nav, Table } from "reactstrap";
import "./Home.css";
import { toast } from "react-toastify";
import getCartItemsFromSerive from "../service/CartService"


const USER_API_BASE_URL = "http://localhost:8080/medi/api/cart/";

const Remove_Cart = "http://localhost:8080/medi/api/cart/remove/";
const Remove_Cart_Items = "http://localhost:8080/medi/api/cart/remove";

const AllCartItems = () => {

  let navigate = useNavigate();

  const [Count, setCount] = useState(0);
  const [Product, setProduct] = useState([]);
  useEffect(() => {
    getCartItems() 
  },[Count])
//================================================================================================================
  let reduceItemQty=(id)=>{

    let productID = id;
    let customerID = sessionStorage.getItem("userID");
    let token=sessionStorage.getItem("token");
    setCount(Count+1);
    const userDto = {
      productId: productID,
      customerId: customerID,
      quantity:-1
    };

    axios.post(USER_API_BASE_URL+"add",userDto,{
      headers :
      {
        Authorization: token,
      }
    }).then(

      (response)=>console.log(response.data),
      setCount=(Count+1),
      toast.success(' Quantity Updated', {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        }),
        window.location.reload(),
        navigate("/Cart")

      ).catch=(error)=>{
      console.log(error)
    }
  };
  
//=============================================================================================================
  let increaseItemQty=(id)=>{
    
    let productID = id;
    let customerID = sessionStorage.getItem("userID");
    let token=sessionStorage.getItem("token");
    setCount(Count+1);

    const userDto = {
      productId: productID,
      customerId: customerID,
      quantity:1
    };

    axios.post(USER_API_BASE_URL+"add",userDto,{
      headers :
      {
        Authorization: token,
      }
    }).then(
      (response)=>console.log(response.data),
      setCount=(Count+1),
      toast.success(' Quantity Updated', {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        }),
        // navigate("/cart"),
        window.location.reload(),
      navigate("/Cart")

    ).catch=(error)=>{
      console.log(error)
    }
  };
  

//============================================================================================================
  
    const removeCartItem = (id) => {
    const token = sessionStorage.getItem("token");
    let cid = sessionStorage.getItem("userID");
    let navigate = useNavigate;
    let productID = id;
    let customerID = cid;

    const userDto = {
      productId: productID,
      customerId: customerID,
    };
    // if(sessionStorage.getItem("token")){
    // axios.delete(Remove_Cart_Items,userDto,{
    // headers:{"Authorization": `$(token)`},}).then(
    //     (response)=>{
    //         navigate("/Cart");
    //     }
    //     )
    // // }

    const options = {
      method: "DELETE",
      url: "http://localhost:8080/medi/api/cart/remove",
      headers: {
        Authorization: token,
      },
      data: { productId: productID, customerId: customerID },
    };

    axios
      .request(options)
      .then(function (response) {
        console.log(response.data);
        toast.success("Product has been removed from cart");
        // getCartItems();
      
           window.location.reload();
           
        
      })
      .catch(function (error) {
        console.error(error);
      });
  };
  
//=============================================================================================================
  let id = sessionStorage.getItem("userID");
  const emptyCart = () => {
    if (sessionStorage.getItem("token")) {
      axios
        .delete(Remove_Cart + id)
        .then(
          (response) => console.log(response.data),
          // setCount=(Count+1),
          toast.warning("Cart is empty"),
          window.location.reload(),
          
          
          
        );
    }
  };

 
  useEffect(() => {
    getCartItems();
  }, []);
//==================================================================================================================
 
  //===========================================================================================================
  const getCartItems = () => {
    let id = sessionStorage.getItem("userID");
    console.log(id);
    let token = sessionStorage.getItem("token");
    console.log(token);
    if (sessionStorage.getItem("token")) {
      axios
        .get(USER_API_BASE_URL + id, {
          headers: { Authorization: token },
        })
        .then((response) => {
          console.log(response);
          JSON.stringify(response);
          setProduct(response.data);
          let product = response.data.product;
          sessionStorage.setItem("product");
        });
    } else {
      console.error("unexpected error");
    }
  };
  
//==================================================================================================================
  return (
    <div className="Home">
      <Header />
       
        {
          Product.length==0 && <div className="title" style={{margin:"50px"}}><h4>Your Cart is Empty</h4> <br/>
          <a href="/">
          <Button color="success">Shop Again</Button>
          </a>
          </div>
        }
        
        {Product.length > 0 && <>
        <div>
        <Container>
        <div className="title" style={{margin:"50px"}}><h4>Let's place the order</h4></div>
          <Table striped style={{marginTop:"50px"}}>
            <thead className="thead dark">
              <tr>
                {/* <th>#</th> */}
                <th>Product Name</th>
                <th>Product Quantity</th>
                <th>price per item</th>
                <th>Best Price</th>
              </tr>
            </thead>

            
              { Product.map((item) => (
                  <tbody>
                    <tr>
                      <td>{item.product.productName}</td>
                      <td>
                        <Button color="primary" size="sm" onClick={()=>reduceItemQty(item.product.id)}>
                          -
                        </Button>
                        &nbsp;&nbsp;{item.quantity}&nbsp;&nbsp;
                        <Button color="primary" size="sm" onClick={()=>increaseItemQty(item.product.id)}>
                          +
                        </Button>
                      </td>
                      <td>{item.product.unitPrice} ₹</td>
                      <td>{item.subtotal} ₹</td>
                      <td>
                        <Button
                          color="danger"
                          onClick={() => removeCartItem(item.product.id)}
                        >
                          {" "}
                          Remove{" "}
                        </Button>
                      </td>
                    </tr>
                  </tbody>
                ))
                  }
          </Table>
          
          
        </Container>
      </div>
      <div>
        <a href="/Address" >
          <Button color="success">Check Out</Button>
        </a>{" "}
        <Button color="primary" onClick={emptyCart}>
          Empty Cart
        </Button>
      </div>
      </>}
      <Footer />
    </div>

  );
};
export default AllCartItems;
