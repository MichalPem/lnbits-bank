<template>
  <div>
    <div class="main_menu">
      <router-link to="/account"><div class="main_menu_button" >Account</div></router-link>
      <router-link to="/pos"><div class="main_menu_button">Point of sale</div></router-link>
    </div>
    <div class="bb">
      <router-view v-slot="{ Component }">
        <transition name="fade">
          <component class="cc" :is="Component"/>
        </transition>
      </router-view>
    </div>
  </div>
</template>

<script>
import Pos from "@/views/Pos";
import Account from "@/views/Account";
export default {
  name: "MainMenu",
  components: {Pos, Account},

}
</script>

<style scoped>
.main_menu {
  background-color: #898989;
  height: 40px;
  width: 820px;
  display: flex;
  position: absolute;
  left: 50%;
  -ms-transform: translateX(-50%);
  transform: translateX(-50%);
}
.cc {
  display: block;
  position: absolute;
  top: 50px;
  left: 50%;
  -ms-transform: translateX(-50%);
  transform: translateX(-50%);
  overflow: scroll;
  height: calc(100vh - 100px);
}
.bb {
  display: block;
  height: 100%;
}
.main_menu_button {
  line-height: 40px;
  height: 40px;
  text-decoration: none !important;
  color: black;
  width: 200px;
}

.main_menu_button > a {
  width: 100%;
  height: 100%;
}

.router-link-active > .main_menu_button    {
  background-color: #e7e7e7;
}

@media only screen and (max-width: 440px) {
  .main_menu {
    background-color: #898989;
    height: 40px;
    width: 100%;
    display: flex;
    position: absolute;
    left: 50%;
    -ms-transform: translateX(-50%);
    transform: translateX(-50%);
  }
  .main_menu_button {
    line-height: 40px;
    height: 40px;
    text-decoration: none !important;
    color: black;
    width: calc(100vw / 2);
  }
  .cc {
    display: block;
    position: absolute;
    top: 40px;
    left: 50%;
    -ms-transform: translateX(-50%);
    transform: translateX(-50%);
    overflow: scroll;
    height: calc(100vh - 58px);
  }
}
</style>