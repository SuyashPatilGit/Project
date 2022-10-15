import React,{useState,useEffect} from "react";
import Footer from "./Footer";
import Header from "./Header";
import axios from "axios";
import { Navigate, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import{
    Form,
    Row,
    Col,
    FormGroup,
    Button,
    Label,
    Input,
    Container
}from "reactstrap";





//==========================================================================================================

const Address=()=>{

  const [data, setData] = useState({
    pinCode : "",
    city : "",
    state: "",
    landMark: "",
    house_no:""
  });
  const handleChange = (e) => {
    const value = e.target.value;
        setData({
        ...data,
        [e.target.name]: value
    });
  }
  let navigate=useNavigate();

  // load razor pay script
  function loadRazorpayScript(src) {
    return new Promise((resolve) => {
      const script = document.createElement("script");
      script.src = src;
      script.onload = () => {
        resolve(true);
      };
      script.onerror = () => {
        resolve(false);
      };
      document.body.appendChild(script);
    });
  }

  const placeOrder=()=>{



    displayRazorpayPaymentSdk();
  }

  

  async function displayRazorpayPaymentSdk() {
    //console.log(formValues);
    const res = await loadRazorpayScript(
      "https://checkout.razorpay.com/v1/checkout.js"
    );

    if (!res) {
      alert("Razorpay SDK failed to load. please check are you online?");
      return;
    }
    let token=sessionStorage.getItem("token");
    
    const Place_order = "http://localhost:8080/medi/api/order/place";

    let userOrder={
        id:sessionStorage.getItem("userID"),
        address: data,
        paymentType:"ONLINE"
        
    }

    //   creating a new order and sending details to backend
    const result = await axios
    .post(Place_order, userOrder, {
      headers: { Authorization: token },
    })
    

    //    let result;

          //if (sessionStorage.getItem("token")) {
        //     axios
        //       .post(Place_order, userOrder, {
        //         headers: { Authorization: token },
        //       })
        //       .then((response) => {
        //         console.log(response.data);
        //         toast.success("Lets Hyperloop to payment");
        //         result=(response.data);
        //       });
        //   } else {
        //     toast.danger("Unexpected error,kindly retry");
        //     navigate("/Home");
        //   }



    console.log(result.data);

    if (!result) {
      alert("Server error. please check are you onlin?");
      return;
    }

    //   Getting the order details back
    const { amount, currency, id } = result.data;
    console.log(amount);
    console.log(currency);
    console.log(id);

    const options = {
      key: "rzp_test_DIePDruxfVbZye",
      amount: amount,
      currency: currency,
      name: "MediKart.com",
      description: "Shopping",
      //image: require("../logo/home.PNG"),
      order_id: id,
      //   callback_url: "http://localhost:3000/",
      //   callback_method: "get",
      handler: function (response) {
        alert(" Your Payment ID: " + response.razorpay_payment_id);
        alert(" Your Order ID: " + response.razorpay_order_id);
        alert(" Your Signature ID: " + response.razorpay_signature);
        toast.success("payment recevied succesfully");
        navigate('/');
        //orderService.storePaymentDetails(response)
        // .then((res) => {
        //   console.log(res.data);
        //   swal(res.data, "Shop Again", "success");
        //   navigate("/");
        // }).catch(()=>{
        //   swal("Error occured while placing order","","error");
        // });
      },
      redirect: true,
      
      prefill: {
        name: JSON.parse(sessionStorage.getItem("user")).firstName,
        email: JSON.parse(sessionStorage.getItem("user")).email,
        contact: JSON.parse(sessionStorage.getItem("user")).mobNo,
      },
      notes: {
        address: "None",
      },
      theme: {
        color: "#61dafb",
      },
    };

    const paymentObject = new window.Razorpay(options);
    paymentObject.open();
  }
  


// const placeOrder=()=>{

//     let token=sessionStorage.getItem("token");
    
//     const Place_order = "http://localhost:8080/medi/api/order/place";

//     let userOrder={
//         id:sessionStorage.getItem("userID"),
//         address: data,
//         paymentType:"ONLINE"
        
//     }

       
//           if (sessionStorage.getItem("token")) {
//             axios
//               .post(Place_order, userOrder, {
//                 headers: { Authorization: token },
//               })
//               .then((response) => {
//                 console.log(response.data);
//                 toast.success("Lets Hyperloop to payment");
//                 navigate("/Cart");
//               });
//           } else {
//             toast.danger("Unexpected error,kindly retry");
//             navigate("/Home");
//           }
// }

    return(
        <div className="Home">
            <Header/>
            <Container >
                <Form className="Address" style={{margin:"50px"}}>
                        <Row>
                            <Col md={9}>
                        <FormGroup>
                            <Label for="exampleAddress">
                            Address
                            </Label>
                            <Input id="exampleAddress" name="addressLine" placeholder="1234 Main St" onChange={handleChange}/>
                        </FormGroup>
                        </Col>
                    </Row>
                    <Row>
                        <Col md={9}>
                    <FormGroup>
                        <Label for="exampleAddress"> LandMark</Label>
                        <Input id="exampleAddress"  name="landMark" placeholder="LandMark" onChange={handleChange}/>
                    </FormGroup>
                    </Col>
                    </Row>
                    
                    <Row>
                        <Col md={3}>
                        <FormGroup>
                            <Label for="exampleCity">City</Label>
                            <Input  id="exampleCity" name="city" onChange={handleChange}/>
                        </FormGroup>
                        </Col>
                        <Col md={3}>
                        <FormGroup>
                        <Label for="exampleSelect">
                        State
                        </Label>
                        <Input  id="exampleSelect" name="state" type="select" onChange={handleChange}>
                        <option>
                        Andhra Pradesh
                        </option>
                        <option>
                        Arunachal Pradesh
                        </option>
                        <option>
                        Assam
                        </option>
                        <option>
                        Bihar
                        </option>
                        <option>
                        Goa
                        </option>
                        <option>
                        Gujrat
                        </option>
                        <option>
                        Haryana
                        </option>
                        <option>
                        Himachal Pradesh
                        </option>
                        <option>
                        Jammu and Kashmir
                        </option>
                        <option>
                        Jharkhand
                        </option>
                        <option>
                        Karnataka
                        </option>
                        <option>
                        Kerala
                        </option>
                        <option>
                        MadhyaPradesh
                        </option>
                        <option>
                        Maharashtra
                        </option>
                        <option>
                        Manipur
                        </option>
                        <option>
                        Meghalaya
                        </option>
                        <option>
                        Mizoram
                        </option>
                        <option>
                        Nagaland
                        </option>
                        <option>
                        Odish
                        </option>
                        <option>
                        Punjab
                        </option>
                        <option>
                        Rajasthan
                        </option>
                        <option>
                        Sikkim
                        </option>
                        <option>
                        Tamilnadu
                        </option>
                        <option>
                        Telangan
                        </option>
                        <option>
                        Tripura
                        </option>
                        <option>
                        UttraPradesh
                        </option>
                        <option>
                        UttraKhand
                        </option>
                        <option>
                        West Bengal
                        </option>
                        <option>
                        Andaman and Nicobar Islands
                        </option>
                        <option>
                        Chandigarh
                        </option>
                        <option>
                        Dadra and Nagar Haveli
                        </option>
                        <option>
                        Daman and Diu
                        </option>
                        <option>
                        Delhi
                        </option>
                        <option>
                        Ladhak
                        </option>
                        <option>
                        Lakshadweep
                        </option>
                        <option>
                        Puducherry
                        </option>

                        </Input>
                            </FormGroup>
                        </Col>
                        <Col md={3}>
                        <FormGroup>
                            <Label for="exampleZip">Zip </Label>
                            <Input id="exampleZip"  name="pinCode" onChange={handleChange}/>
                        </FormGroup>
                        </Col>
                    </Row>
                    <Row>
                    <Col md={9}>
                    <div style={{alignItems:"center",margin:"50px"}}><Button color="success" onClick={placeOrder}>Place Order</Button></div>
                    </Col>
                    </Row>
                    </Form>
            </Container>
            <Footer/>
        </div>
    )
    }


export default Address;
