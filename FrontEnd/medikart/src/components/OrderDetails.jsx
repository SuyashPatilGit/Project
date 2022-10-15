import axios from "axios";
import React from "react";
//import { useEffect } from "react";
import { useState,useEffect } from "react";
import { useParams } from "react-router-dom";
import { Card, CardHeader, Container, Table } from "reactstrap";
import Footer from "./Footer";
import Header from "./Header";



const OrderDetails=()=>{

  let token=sessionStorage.getItem("token");
    //console.log(token)
    
    
    //console.log({id});
    // let URL_Product="http://localhost:8080/medi/api/order/8";

  const {id}= useParams();
  useEffect(()=>{


    
     
     const options = {
        method: 'GET',
        url: "http://localhost:8080/medi/api/order/"+id,
        headers: {
          Authorization: token
        }
      };
      
      axios.request(options).then(function (response) {
        console.log(response.data.orderDetails)
        setProdDet(response.data.orderDetails)
        console.log(prodDet)
      }).catch(function (error) {

        console.error(error);

      });
    
   },[]);

    //const id = useParams();

    let [prodDet,setProdDet]=useState([]);

    let [order,setOrder]=useState({});

    let [count,setCount]=useState(0); 
    

    
   
      
    
          


    
    
    
    return(
      
        <div className="Home" >
            <Header/>
                <h5 className="title" style={{margin:"30px"}}>This is Order details page</h5>
                <Container>
            <Table style={{marginTop:"30px"}}bordered hover dark responsive size="Lg">
                  <thead className="thead dark">
              <tr>
                

                <th>Sr.no</th>
                <th>Order ID</th>
                <th>Product Name</th>
                <th>Order Qty</th>
                <th>Subtotal</th>
                <th>Unit Price</th>
                
              </tr>
              </thead>
            
              {prodDet && prodDet.map((item)=>(

                <tbody>
                  <tr>
                    <td>{++count}</td>
        
                    <td>{item.id}</td>
                  
                    <td>{item.product.productName}</td>
                  
                    <td>{item.quantity}</td>
                  
                    <td>{item.subtotal}</td>
                  
                    <td>{item.unitPrice}</td>
                  </tr>
                  
                </tbody>
              ))
}
                  </Table>
                  <a href="/ViewOrders"><h5>Back</h5></a>
                    
                    
                </Container>
            <Footer/>
        </div>
        
 
    )
}
export default OrderDetails;