// router/index.js
import Vue from 'vue'
import Router from 'vue-router'
import VueCookies from 'vue-cookies'
import Home from '@/components/Home'
import SignIn from '@/components/Login'
import Courses from '@/components/Courses'
import Instructors from '@/components/Instructors'
import Registration from '@/components/Registration'
import Settings from "@/components/Settings";
import BillingAccount from "@/components/BillingAccount";
import BillingAccountOverview from "@/components/BillingAccountOverview";
import SessionRegistration from "@/components/SessionRegistration"
import Location from '@/components/Location'

Vue.use(Router);

Vue.use(VueCookies);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home,
    },
    {
      path: "/login",
      name: "SportCenter Login page",
      component: SignIn,
    },
    {
      path: "/courses",
      name: "Courses",
      component: Courses,
    },
    {
      path: "/instructors",
      name: "Instructors",
      component: Instructors,
    },
    {
      path: "/registration",
      name: "Registration",
      component: Registration,
    },
    {
      path: "/settings",
      name: "Settings",
      component: Settings,
    },
    {
      path: "/billing-account",
      name: "BillingAccount",
      component: BillingAccount,
    },
    {
      path: "/billing-accounts",
      name: "BillingAccountOverview",
      component: BillingAccountOverview,
    },
    {
      path: '/location',
      name: 'Location',
      component: Location
    },
    {
      path: "/sessions/courses/:cId",
      name: "SessionRegistration",
      component: SessionRegistration,
      props: true
    }
  ]
})
