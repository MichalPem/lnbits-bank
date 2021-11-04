<template>
  <div class="pos" v-if="this.$store.state.user">
    POS
  </div>
</template>

<script>
import { isMobile } from 'mobile-device-detect';

export default {
  name: "Pos",
  components: {

  },
  data() {
    return {
      mobile:isMobile,
      user:this.$store.state.user,
      qrText:"",
      unit:"sat"
    }
  },
}
</script>

<style scoped>
.pos {
  z-index: 5;
  width: 800px;
  height: 100%;
  background-color: #c9c9c9;
  padding: 10px;
}
</style>