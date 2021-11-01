<template>
  <div id="login">
    <h1>Login</h1>
    <input type="text" name="username" v-model="input.username" placeholder="Username" /><br>
    <input type="password" name="password" v-model="input.password" placeholder="Password" /><br>
    <button type="button" v-on:click="login()">Login</button>
  </div>
</template>

<script>

import xhr  from '../services/xhr.service'


export default {
  name: "Login",
  data() {
    return {
      input: {
        username: "",
        password: ""
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
        this.$router.push({ path: '/about' })
      })
    }
  }
}
</script>

<style scoped>

</style>