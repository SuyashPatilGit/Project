import axios from "axios"
import { IP_ADDRS } from "./Constant"

const getCartItemsFromService=(id,token)=>{

    return axios.get(`${IP_ADDRS}/cart/${id}`,{ headers: {"Authorization" : `Bearer ${token}`}}) 
}
export default {getCartItemsFromService}