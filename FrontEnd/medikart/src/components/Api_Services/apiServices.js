import axios from "axios";
const USER_API_BASE_URL = "http://localhost:8080/medi";

class ApiService{
    
    getAllProducts(){
        return axios.get(USER_API_BASE_URL +"/product/products",{
            headers: {
                Authorization: token,
              },
        }).then(
            (response)=>
        );
    }
}

