import axios from "axios";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Card, CardTitle, Container } from "reactstrap";
import Footer from "./Footer";
import Header from "./Header";
import './Home.css';



const ProductDetails=()=>{
    let navigate=useNavigate();
    const sendBack=()=>{
        navigate('/');
    }

    let token= sessionStorage.getItem("token");
    let [product,setProduct]=useState([]);
    let {id} = useParams();
    console.log("suyash")
    console.log({id});
     let URL="http://localhost:8080/medi/api/product/"+id;
    useEffect(()=>{
     axios.get(URL,{
        headers:{
            Authorization:token
        }
     }).then(
        (response)=>{
        console.log(response.data)
        setProduct(response.data)
        }
     )
    },[])
    return(
        <div className="Home">
            <Header/>
                <h5 className="title" style={{margin:"30px"}}>This is product details page</h5>
                <Container>
                    <Card>
                    <div className="productDetails" >
                    <img src={product.imageUrl}/>
                    </div>
                    <div className="title">
                        <h1>{product.prodDetails}</h1>
                    </div>
                    </Card>
                    <div style={{margin:"30px"}}>
                    <Button size="lg" color="link" onClick={sendBack}> Back</Button>
                    </div>
                    
                </Container>
            <Footer/>
        </div>
    )
}
export default ProductDetails;

//{product.productName}