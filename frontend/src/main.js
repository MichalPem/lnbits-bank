import { createApp } from 'vue'
import App from './App.vue'
import router from "@/router";
import { createStore } from 'vuex'


// 5. Create and mount the root instance.
// const app = Vue.createApp({})
export const store = createStore({
    state () {
        return {
            token:null,
            user:null,
            requestActive:false
        }
    }
})
export const app = createApp(App)
    .use(store)
    .use(router)

app.mount('#app')
