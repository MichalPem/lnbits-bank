import axios from "axios";

class XHR {
    state = null
    baseURL = "http://localhost:8080"
    setState(s)
    {
        this.state = s;
    }
    login(username,password,callback)
    {
        this.state.requestActive = true;
        axios.post(this.baseURL+"/api/login",{
            username:username,
            password:password
        }).then((response)=> {
            callback(response)
            this.state.requestActive = false;
        })
    }
    getUser(token,callback)
    {
        this.state.requestActive = true;
        axios.get(this.baseURL+"/api/user", { headers: { Authorization: 'Bearer ' + token } })
            .then((r)=>{ this.state.requestActive = false;callback(r)})
    }

    getInvoice(token,data,callback)
    {
        this.state.requestActive = true;
        axios.post(this.baseURL+"/api/invoice", data,{ headers: { Authorization: 'Bearer ' + token } })
            .then((r)=>{ this.state.requestActive = false;callback(r)})
    }
}
export default new XHR();