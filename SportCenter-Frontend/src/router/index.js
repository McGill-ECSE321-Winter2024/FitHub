import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Instructor from '@/components/Instructor'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/login',
      name: 'SportCenter Login page',
      component: Login
    },
    {
      path: '/instructor',
      name: 'SportCenter Instructor page',
      component: Instructor
    }
  ]
})
