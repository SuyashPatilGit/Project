import React from "react"
import "./Home.css";
const Footer=()=>{
const current= new Date;
const year= `${current.getFullYear()}`;
  return(
    <div className="footer"> 
    <h4 style={{color:"whitesmoke"}}>Copyright@Medikart : {year}</h4>
    </div>
)
}
export default Footer;
