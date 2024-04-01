import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import SignIn from '@/components/Login'
import Courses from '@/components/Courses'
import Instructors from '@/components/Instructors'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/login',
      name: 'SportCenter Login page',
      component: SignIn
    },
    {
      path: '/courses',
      name: 'Courses',
      component: Courses
    },
    {
      path: '/instructors',
      name: 'Instructors',
      component: Instructors
    }
  ]
})
