<template>
  <div class="account" v-if="this.$store.state.user">
    <div class="account_d">
      <div style="float: left;padding-left: 10px"><h3>{{user.user.name}}</h3></div>
      <div class="pay_menu">
        <div @click="this.$router.push({ path: '/account/pay' })" style="margin-right: 5px" >Pay</div>
        <div @click="this.$router.push({ path: '/account/receive' })">Receive</div>
      </div>
    </div>

    <div class="wallet">
      <div class="balance"><div style="padding-left: 10px;float: left;"><b>Balance</b></div><span>{{user.wallet.balance/1000}} <b>{{ unit}}</b></span></div>
    </div>
    <hr style="border-style: double;color: #b9b9b9;">
    <router-view v-slot="{ Component }">
      <transition name="fade" mode="in-out">
        <component :is="Component"/>
      </transition>
    </router-view>
    <div style="display: none" class="fade-enter-from fade-enter-active fade-leave-to fade-leave-active" ></div>

    <!--    ID: {{this.$store.state.user.id}}<br>-->
<!--    Name: {{this.$store.state.user.name}}-->

  </div>
</template>

<script>
import { isMobile } from 'mobile-device-detect';
export default {
  name: "Account",
  components: {

  },
  compocomponents: {

  },
  data() {
    return {
      mobile:isMobile,
      user:this.$store.state.user,
      qrText:"",
      unit:"sat"
    }
  },
  methods : {


  },
  mounted() {


  }
}
</script>

<style scoped>
#reader {
  width: 260px;
  height: 260px;
  display: none;
  /*background-color: aquamarine;*/
}
.balance {
  width: 100%;
  background-color: #e7e7e7;
  min-height: 50px;
  text-align: right;
  align-items: center;
  display: flex;
  justify-content: space-between;
}
.balance > span {
  margin-right: 10px;
}
.account {
  width: 800px;
  height: 100%;
  background-color: #c9c9c9;
  padding: 10px;
  z-index: 10;
}
.account_d {
  width: 100%;
  height: 50px;
  display: block;
}


.pay_menu {
  display: flex;
  float: right;
  padding-top: 15px;
  padding-right: 10px;
}

.pay_menu > div {
  width: 100px;
  height: 30px;
  background-color: #898989;
  line-height: 30px;
  cursor: pointer;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease-out;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}


@media only screen and (max-width: 440px) {
  .account {
    width: calc(100% - 22px);
    margin-top: 0px;
    height: 100%;
    background-color: #c9c9c9;
    display: inline-block;
    padding: 10px;
  }

  .pay_menu {

    position: fixed;
    bottom: 10px;
    right: -5px;
    display: block;
    z-index: 10;
  }

  .pay_menu > div {
    width: 70px;
    height: 70px;
    background-color: #898989;
    line-height: 70px;
    cursor: pointer;
    margin-bottom: 10px;
  }


}
</style>