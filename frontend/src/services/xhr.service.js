import axios from "axios";

class XHR {
    state = null
    setState(s)
    {
        this.state = s;
    }
    login(username,password,callback)
    {
        this.state.requestActive = true;
        axios.post("/api/login",{
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
        axios({
            method: 'get',
            url: "/api/user",
            headers: {
                Authorization: 'Bearer ' + token
            }
        }).then((r)=>{ this.state.requestActive = false;callback(r)})

    }
}
export default new XHR();