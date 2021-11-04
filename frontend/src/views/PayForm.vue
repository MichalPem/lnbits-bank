<template>
  <div class="pay_form">
    <div v-if="lninvoice.length === 0" id="reader" ></div>
    <input placeholder="Paste invoice" type="text" name="lninvoice" v-model="lninvoice" required :disabled="formDisabled"/><br>
    {{lninv&&lninv.satoshis ? 'Pay '+lninv.satoshis+' sats':"" }}
    <div class="recieve_buttons">
      <button class="ok_button" type="button" v-on:click="cancel()" style="margin-right: 4px">Cancel</button>
      <button class="ok_button" type="button" v-on:click="send()">Send</button>
    </div>
  </div>
</template>

<script>
import {Html5QrcodeScanner} from "html5-qrcode"
import bolt11 from 'bolt11'

let scanner;
export default {
  name: "PayForm",
  data() {
    return {
      lninvoice:"",
      lninv:{},
      formDisabled:false
    }
  },
  methods : {
    onScanSuccess(decodedText, decodedResult) {
      // handle the scanned code as you like, for example:
      if(decodedText.indexOf('ln')===0)
      {
        this.lninvoice = decodedText;
        this.formDisabled = true;
        this.lninv = bolt11.decode(this.lninvoice)

        scanner.clear()
      }
      console.log(`Code matched = ${decodedText}`, decodedResult);
    },

    onScanFailure() {
      // handle scan failure, usually better to ignore and keep scanning.
      // for example:
      // console.warn(`Code scan error = ${error}`);
    },
    createScanner()
    {
      scanner = new Html5QrcodeScanner(
          "reader",
          { fps: 10, qrbox: {width: 230, height:230} },
          /* verbose= */ false);
      scanner.render(this.onScanSuccess, this.onScanFailure);
    },
    cancel() {
      scanner.clear()
      this.formDisabled = false;
      this.lninvoice = "";
      this.$router.push({ path: '/account' })
    },
    send(){

    }
  },
  mounted() {
    this.createScanner()
  },
  unmounted() {
    scanner.clear()
  }
}
</script>

<style scoped>
.pay_form {
  display: block;
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
#reader {
  width: 350px;
  height: 350px;
  left: calc(50% - 175px)
}
@media only screen and (max-width: 440px) {
  #reader {
    width: 100%;
    height: 310px;
  }
}
</style>