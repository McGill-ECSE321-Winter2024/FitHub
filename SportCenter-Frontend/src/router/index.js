// router/index.js
import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import SignIn from '@/components/Login'
import Courses from '@/components/Courses'
import Instructors from '@/components/Instructors'
import Registration from '@/components/Registration'
import BillingAccount from '@/components/BillingAccount'

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
    },
    {
      path: '/registration',
      name: 'Registration',
      component: Registration
    }
    ,
    {
      path: '/billing-accounts',
      name: 'BillingAccount',
      component: BillingAccount
    }
  ]
})
