<template>
<!--  <div id="nav">-->
<!--    <router-link v-if="this.$store.state.token !== null" to="/about">About</router-link>-->
<!--  </div>-->
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="in-out">
      <component :is="Component"/>
    </transition>
  </router-view>
  <PulseLoader class="loader" v-if="this.$store.state.requestActive" :loading="true" :color="'white'"></PulseLoader>
  <div style="display: none" class="fade-enter-from fade-enter-active fade-leave-to fade-leave-active" ></div>
  <img src="./assets/pngfind.com-orange-lightning-png-5703769.png" class="bitcoin_log">
</template>

<script>
import PulseLoader from 'vue-spinner/src/PulseLoader'
import xhr  from './services/xhr.service'

export default {
  name: 'App',
  components: {
    PulseLoader
  },
  mounted() {
    if(this.$store.state.token === null)
    {
      this.$router.push({ path: '/login' })
    }
    xhr.setState(this.$store.state);
  }
}
</script>

<style>
html,body {
  width: 100%;
  height: 100%;
  margin: 0px;
}
a {
  text-decoration: none !important;
}

#login {
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  background-color: #c9c9c9;
  min-height: 200px;

  padding: 1px 1px;
  /*background-color: #c9c9c9;;*/
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #e7e7e7;
  width: 100%;
  height: 100%;
  background-image: url("./assets/wp4452848-artistic-shapes-wallpapers.jpg");
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;
  overflow: scroll;
}
.loader {
  position: absolute;
  right: 0px;
  bottom: 5px;
}
input {
  width: 200px;
  height: 30px;
  text-align: center;
  font-size: 20px;
  margin-bottom: 5px;
  border-style: none;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease-out;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.bitcoin_log {
  position: absolute;
  bottom: 5px;
  right: 50px;
  height: 30px;
}

</style>
