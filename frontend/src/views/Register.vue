<template>
  <div id="login">
    <LoginMenu />
    <input type="text" name="username" v-model="input.username" placeholder="Username" required/><br>
    <input type="password" name="password" v-model="input.password" placeholder="Password" required/><br>
    <input type="email" name="email" v-model="input.email" placeholder="Email" /><br>
    <button class="login_button" type="button" v-on:click="login()">Send</button>
  </div>
</template>

<script>

import xhr  from '../services/xhr.service'
import LoginMenu from "@/components/LoginMenu";

export default {
  name: "Login",
  components: {
    LoginMenu
  },
  data() {
    return {
      input: {
        username: "",
        password: "",
        email: "",
      },
      username: ""
    }
  },
  methods: {
    login() {
      if(this.input.username !== "" && this.input.password !== "") {
        xhr.login(
            this.input.username,this.input.password,this._onLoginComplete
        )

      } else {
        console.log("A username and password must be present");
      }
    },
    _onLoginComplete(r) {
      this.$store.state.token = r.data.jwttoken
      this.getUser();
    },
    getUser()
    {
      xhr.getUser(this.$store.state.token,(r)=>{
        this.$store.state.user = r.data
        this.$router.push({ path: '/account' })
      })
    }
  }
}
</script>

<style scoped>


.login_button
{
  cursor: pointer;
  width: 204px;
  height: 37px;
  background-color: #e7e7e7;
  border-style: none;
  font-size: 20px;
}
</style>