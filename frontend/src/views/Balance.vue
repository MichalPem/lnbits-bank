<template>
  <div class="tc">
    <div class="transaction" v-for="(t) in user.payments" v-bind:key="t">
      <span class="p_item" style="text-align: left">{{ this.dateFormatter(t.date) }}</span>
      <span class="p_item" style="text-align: right;width: 86%;">{{ t.memo }}</span>
      <span class="p_item" style="text-align: right;min-width: 50px;">{{ t.amount/1000 }} <b>{{ unit}}</b></span>
    </div>
  </div>
</template>

<script>
import { isMobile } from 'mobile-device-detect';

export default {
  name: "Balance",
  data() {
    return {
      mobile:isMobile,
      user:this.$store.state.user,
      qrText:"",
      unit:"sat"
    }
  },
  methods: {
    dateFormatter(d) {
      if(this.mobile)
      {
        return this.timeSince(new Date(d))
      }
      var m = new Date(d);
      m = new Date(m.getTime()+ (3600000*4))
      return m.getUTCFullYear() + "/" +
          ("0" + (m.getUTCMonth() + 1)).slice(-2) + "/" +
          ("0" + m.getUTCDate()).slice(-2) + " " +
          ("0" + (m.getUTCHours())).slice(-2) + ":" +
          ("0" + m.getUTCMinutes()).slice(-2) + ":" +
          ("0" + m.getUTCSeconds()).slice(-2)
    },

    timeSince(date) {

      var seconds = Math.floor((new Date() - date) / 1000);

      var interval = seconds / 31536000;

      if (interval > 1) {
        return Math.floor(interval) + " years";
      }
      interval = seconds / 2592000;
      if (interval > 1) {
        return Math.floor(interval) + " months";
      }
      interval = seconds / 86400;
      if (interval > 1) {
        return Math.floor(interval) + " day";
      }
      interval = seconds / 3600;
      if (interval > 1) {
        return Math.floor(interval) + " hrs";
      }
      interval = seconds / 60;
      if (interval > 1) {
        return Math.floor(interval) + " min";
      }
      return Math.floor(seconds) + " sec";
    },
  }
}
</script>

<style scoped>
.tc {
  position: absolute;
  top: 140px;
  left: 10px;
  width: calc(100% - 20px);
}
.transaction {
  background-color: #e7e7e7;
  min-height: 50px;
  align-items: flex-start;
  display: flex;
  width: 100%;
  justify-items: stretch;
  flex-direction: row;
  justify-content: space-between;
}
.p_item {
  display: inline;
  margin-right: 10px;
  padding-left: 10px;
  width: 30%;
  padding-top: 15px;
}
</style>