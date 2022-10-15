import axios from "axios";
import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Table, Container,Form, Row,Col,FormGroup,Label,Input,handleChange, Button} from "reactstrap";
import Footer from "./Footer";
import Header from "./Header";
import ProductDetails from "./ProductDetails";

const USER_API_BASE_URL = "http://localhost:8080/medi/api/order/userOrder/";
const GET_INVOICE=  "http://localhost:8080/medi/report/invoice/";
const ViewOrders=()=>{

  let navigate=useNavigate();
  useEffect(()=>{
    getOrdersById();
   },[]);
   let[orderNumber,setOrderNumber]=useState(0);
    const [Order,setOrder]=useState([]);
    let [Count,setCount]=useState(0);


    const getInvoice=(id)=>{

          axios.get(GET_INVOICE+id).then(
            (response)=>{
              console.log(response.data);
              navigate('/ViewOrders');
            },
          (error)=>{
            console.log(error)
          }
          )
          }
    


    const productDetails=(id)=>{
        console.log(id);
        //  let id1=JSON.stringify(id)
      navigate('/OrderDetails/'+id)
      
    }

    
    let id=sessionStorage.getItem("userID")
    let token=sessionStorage.getItem("token")
    const getOrdersById=()=>{
      
        axios.get(USER_API_BASE_URL+id,{
            headers :
            {
              Authorization: token,
            }}).then(
               (response)=>{
                    console.log(response);
                    JSON.stringify(response);
                    setOrder(response.data);
                },
            (error)=>{
                console.log(error);
            }
        )
      
          }  
    return(
        <div className="Home" >
            <Header></Header>
            <div className="title" style={{margin:"20px"}}> Your Order List</div>
            {Order.length > 0 && <>
        <div>
        <Container>
          <Table striped style={{marginTop:"30px"}}
           bordered
           hover
           dark
           responsive
           size="Lg">
            <thead className="thead dark">
              <tr>
                
                {/* <th>#</th> */}
                <th>Sr.no</th>
                <th>Order ID</th>
                <th>Order Date</th>
                <th>Order Status</th>
                <th>Dilevery Date</th>
                <th>Total Amount</th>
                <th>Details</th>
                <th>Invoice</th>
                
              </tr>
            </thead>

            
              { Order.map((item) => (
                  <tbody>
                    <tr>
                      <td >{++orderNumber}</td>
                      <td>
                      {item.id}
                      </td>
                      <td>
                      {item.orderDate}
                      </td>
                      <td>{item.orderStatus}</td>
                      <td>{item.deliveryDate}</td>
                      <td>
                      {item.totalAmount} ₹
                      </td>
                      <td>
                        <Button color="primary" onClick={()=>productDetails(item.id)}> Details</Button>
                      </td>
                      <td>
                        <Button color="success" onClick={()=>getInvoice(item.id)}> Invoice </Button>
                      </td>
                    </tr>
                  </tbody>
                ))
                  }
          </Table>
          
          
        </Container>
      </div>
      
      </>}
      <Footer></Footer>
        </div>

    )
}
export default ViewOrders;