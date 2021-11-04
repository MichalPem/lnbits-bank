import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Account from "@/views/Account";
import Register from "@/views/Register";
import Pos from "@/views/Pos";
import MainMenu from "@/views/MainMenu";
import ReceiveForm from "@/views/ReceiveForm";
import Balance from "@/views/Balance";
import PayForm from "@/views/PayForm";
const routes = [
    {
        path: '/',
        name: 'MainMenu',
        component: MainMenu,
        children: [
            // UserHome will be rendered inside User's <router-view>
            // when /user/:id is matched
            {
                path: '/account',
                component: Account,
                children: [
                    {
                        path: '',
                        component: Balance
                    },
                    {
                        path: 'receive',
                        component: ReceiveForm,
                    },
                    {
                        path: 'pay',
                        component: PayForm,
                    }
                ]
            },
            { path: '/pos', component: Pos },

            // ...other sub routes
        ],
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    }

]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
export default router