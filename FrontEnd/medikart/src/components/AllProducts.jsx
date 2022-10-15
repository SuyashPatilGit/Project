import React, { useEffect,useState } from 'react';
import axios from "axios";
import Products from "./Products";
import "../Layouts/Allcards.css";
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Container } from 'reactstrap';


const USER_API_BASE_URL = "http://localhost:8080/medi/api/product/view";

const AllProducts=()=>{

const [search,setSearch]=useState([]);
    const searchItemByNAme=(e)=>{

       setSearch(Product.filter((p)=>{
        if(p.productName.toUpperCase().includes(e.target.value.toUpperCase()))
        return p;
        
    })) 


    }
    

    useEffect(()=>{
        getAllproducts();
       },[]);


      const getAllproducts=()=>{
       axios.get(USER_API_BASE_URL).then(
           (response)=>{
               console.log(response);
               JSON.stringify(response);
               setProduct(response.data);
               setSearch(response.data);
           },
           (error)=>{
               console.log(error);
           }
       );
      };

    const [Product,setProduct]=useState([])
return(
    <div >
        <Container>
        

        <div  class="input-group rounded" style={{margin:"50px",}} dark >
                <input
                  type="search"
                  class="form-control rounded"
                  placeholder="Search your required medicine here"
                  aria-label="Search"
                  aria-describedby="search-addon"
                  onChange={searchItemByNAme}/>
                  
              </div>
              </Container>
        <div style={{marginTop:"50px",backgroundColor:"color: white "}}>
        {/* rgb(202, 233, 252) */}
        
        </div>
        {/* {Product.map((item) => {
            return (<Products prod={item}/>)
        })} */}
        {
            Product.length > 0
            ? search.map((item)=><div className='Allcards'><Products  prod={item}/></div> )
            :<div className='title'><h1>Sorry! Server is Down... Please try again later.</h1></div>
        }
    </div>
)
}
export default AllProducts;