<template>
  <div v-if="this.$store.state.user">
    ID: {{this.$store.state.user.id}}<br>
    Name: {{this.$store.state.user.name}}
  </div>
</template>

<script>
export default {
  name: "About"
}
</script>

<style scoped>

</style>