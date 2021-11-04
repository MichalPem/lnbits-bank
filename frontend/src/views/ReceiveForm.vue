<template>
  <div class="recieve_form">
    <input v-if="lninvoice.length === 0" placeholder="amount" type="number" name="amount" v-model="amount" required :disabled="formDisabled"/><br>
    <input v-if="lninvoice.length === 0" placeholder="memo" type="text" name="memo" v-model="memo" :disabled="formDisabled"/><br>
    <qrcode-vue v-if="lninvoice && lninvoice.length > 0" :value="lninvoice"  :size="200" level="H" :render-as="'svg'"/><br>
    <div v-if="lninvoice && lninvoice.length > 0" class="lnurl">{{ lninvoice }}</div>
    <div class="recieve_buttons">
      <button class="ok_button" type="button" v-on:click="cancel()" style="margin-right: 4px">{{lninvoice && lninvoice.length > 0 ? "OK":"Cancel"}}</button>
      <button v-if="lninvoice.length === 0" class="ok_button" type="button" v-on:click="send()">Send</button>
    </div>

  </div>
</template>

<script>
import xhr  from '../services/xhr.service'
import QrcodeVue from 'qrcode.vue'
export default {
  name: "ReceiveForm",
  components: {
    QrcodeVue,
  },
  data() {
    return {
      amount:"",
      memo:"",
      lninvoice:"",
      formDisabled:false
    }
  },
  methods: {
    send() {
      if(this.formDisabled) return;
      this.formDisabled = true
      xhr.getInvoice(this.$store.state.token,{amount:this.amount,memo:this.memo},(r)=>{
        this.lninvoice = r.data.payment_request
      })
    },
    cancel() {
      this.amount = "";
      this.memo = "";
      this.formDisabled = false;
      this.lninvoice = "";
      this.$router.push({ path: '/account' })
    }
  }
}
</script>

<style scoped>
.recieve_form {
  text-align: center;
  position: absolute;
  top: 140px;
  left: 10px;
  width: calc(100% - 20px);
}
.recieve_buttons {
  display: block;
}
.ok_button
{
  cursor: pointer;
  width: 100px;
  height: 37px;
  background-color: #e7e7e7;
  border-style: none;
  font-size: 20px;
}
.lnurl {
  width: 400px;
  overflow-wrap: break-word;
  display: inline-block;
}
</style>