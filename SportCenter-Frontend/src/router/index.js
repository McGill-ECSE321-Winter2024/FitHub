// router/index.js
import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import SignIn from '@/components/Login'
import Courses from '@/components/Courses' // Import the About component

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
      component: SignIn
    },
    {
      path: '/courses', // Define the path for the About page
      name: 'Courses',
      component: Courses
    }
  ]
})
