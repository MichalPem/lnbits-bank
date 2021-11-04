<template>
  <div class="login_menu">
    <router-link to="/login"><div class="login_menu_item" >Login</div></router-link>
    <router-link to="/register"><div class="login_menu_item" >Register</div></router-link>
  </div>
</template>

<script>
export default {
  name: "LoginMenu",
  data() {
    return {
      loginSelected:true
    }
  },
}
</script>

<style scoped>
.login_menu {

  width: 400px;
  height: 40px;
  margin-bottom: 10px;
  margin-top: 1px;

}
.login_menu_item {
  display: inline-block;
  background-color: #c9c9c9;
  width: 196px;
  height: 40px;
  line-height: 40px;
  cursor: pointer;
  font-size: 20px;
  color: black;
  text-decoration: none;
  border-style: solid;
  border-width: 1px;
  border-color:  #e7e7e7;;
}
.router-link-active > .login_menu_item {
  background-color: #e7e7e7;

}
</style>