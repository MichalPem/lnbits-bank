<template>
  <div id="nav">
    <router-link v-if="this.$store.state.token !== null" to="/about">About</router-link>
  </div>
  <router-view/>
  <PulseLoader class="loader" v-if="this.$store.state.requestActive" :loading="true" :color="'black'"></PulseLoader>
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
html, body {
  margin: 0;
  height: 100%;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  width: 100%;
  height: 100%;
}
.loader {
  position: absolute;
  right: 0px;
  bottom: 0px;
}
</style>
