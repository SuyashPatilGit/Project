import React from "react";
import Footer from "./Footer";
import Header from "./Header";
import {
  Jumbotron,
  Card,
  CardImg,
  CardText,
  CardImgOverlay,
  Container,
  Form,
  Row,
  Table,
  CardTitle,
} from "reactstrap";
const AboutUs = () => {
  return (
    <div style={{ backgroundColor: "rgb(202, 233, 252)" }}>
      <Header />
      <Container>
        <div className="fluid-container row" style={{ alignItems: "left" }}>
          {/* First Card */}
          <div style={{width: "1000px",height:"500px",alignContent:"center",marginBlock:"100px"}}>
            <Card color="transparent">
              <CardTitle>MediKart</CardTitle>
              <CardText>
                
              </CardText>
              
            </Card>
            
          </div>
          <Card
            style={{
              backgroundColor: "transparent",
              color: "light",
              width: "25rem",
              height: "40rem",
              padding: "20px",
              margin: "50px",
            }}
          >

            <img src={require("../images/Shubham.jfif")} style={{height:"350px"}}></img>

            <h5 className="mt-2">Shubham Mawle</h5>
            <CardText className="mb-2 text-muted">Business Partner</CardText>
            <CardText className="mb-2 text-muted">
              Phone: +91-8871579014
            </CardText>
            <CardText className="mb-2 text-muted">
              Email: shubhammawle@gmail.com
            </CardText>
          </Card>

          <Card
            style={{
              backgroundColor: "transparent",
              color: "light",
              width: "25rem",
              height: "40rem",
              padding: "20px",
              margin: "50px",
            }}
          >

            <img src={require("../images/Suyash.jpg")}></img>

            <h5 className="mt-2">Suyash Patil</h5>
            <CardText className="mb-2 text-muted">Business Partner</CardText>
            <CardText className="mb-2 text-muted">
              Phone: +91-9881020917
            </CardText>
            <CardText className="mb-2 text-muted">
              Email: psuyash4@gmail.com
            </CardText>
          </Card>
        </div>
      </Container>

      <Footer />
    </div>
  );
};
export default AboutUs
;
