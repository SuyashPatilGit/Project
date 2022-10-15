import { useNavigate } from "react-router-dom";
import { Button, Card, Container } from "reactstrap";
import Footer from "./Footer";
import Header from "./Header";
//import SideBar from "../components/sidebar/Sidebar"
import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    ResponsiveContainer,
    AreaChart,
    Area,
    BarChart,
    Bar,
  } from "recharts";

const Admin = () => {
  let navigate = useNavigate();
  const data = [
    {
      "name": "JAN",
      "ES": 4000,
      "AS": 2400,
      "amt": 2400
    },
    {
      "name": "FEB",
      "ES": 3000,
      "AS": 1398,
      "amt": 2210
    },
    {
      "name": "MAR",
      "ES": 2000,
      "AS": 9800,
      "amt": 2290
    },
    {
      "name": "APR",
      "ES": 2780,
      "AS": 3908,
      "amt": 2000
    },
    {
      "name": "MAY",
      "ES": 1890,
      "AS": 4800,
      "amt": 2181
    },
    {
      "name": "JUN",
      "ES": 2390,
      "AS": 3800,
      "amt": 2500
    },
    {
      "name": "JUL",
      "ES": 3490,
      "AS": 4300,
      "amt": 2100
    },
    {
        "name": "AUG",
        "ES": 3900,
        "AS": 4000,
        "amt": 3300
      }
  ]
  const addShipment = () => {};
  return (
    <div style={{backgroundColor:"rgb(195, 234, 249)",backgroundRepeat:"no-repeat",backgroundSize:" 1000px"}} >

      <Header />

      <div style={{ margin: "50px" }}>
        <Container>
          <Card style={{height:"1500px",background:"transparent"}}>
        <div>
            <h1>Yearly Sales review</h1>
        </div>
        <AreaChart
          width={1000}
          height={250}  
          data={data}
          margin={{ top: 10, right: 30, left: 50, bottom: 0 }}
        >
          <defs>
            <linearGradient id="colorUv" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="#8884d8" stopOpacity={0.8} />
              <stop offset="95%" stopColor="#8884d8" stopOpacity={0} />
            </linearGradient>
            <linearGradient id="colorPv" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="#82ca9d" stopOpacity={0.8} />
              <stop offset="95%" stopColor="#82ca9d" stopOpacity={0} />
            </linearGradient>
          </defs>
          <XAxis dataKey="name" />
          <YAxis />
          <CartesianGrid strokeDasharray="3 3" />
          <Tooltip />
          <Area
            type="monotone"
            dataKey="ES"
            stroke="#8884d8"
            fillOpacity={1}
            fill="url(#colorUv)"
          />
          <Area
            type="monotone"
            dataKey="AS"
            stroke="#82ca9d"
            fillOpacity={1}
            fill="url(#colorPv)"
          />
        </AreaChart>
        </Card>
        </Container>
      </div>
      <Footer />
    </div>
  );
};
export default Admin;
