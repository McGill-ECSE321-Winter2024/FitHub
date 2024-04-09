// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'jquery';
import 'popper.js';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import Vue from 'vue'
import { BootstrapVue, IconsPlugin } from "bootstrap-vue"
import App from './App'
import router from './router'
import Toolbar from './components/Toolbar'
import Title from './components/Title'
import CoursesMini from './components/CoursesMini'
import WhatOurCustomersSay from './components/WhatOurCustomersSay'
import ContactUs from './components/ContactUs'
import RegistrationLink from './components/RegistrationLink'
import Footer from './components/Footer'
import CoursesCol from'./components/CoursesCol'
import { Hooper, Slide, Navigation} from 'hooper';
import 'hooper/dist/hooper.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap/dist/css/bootstrap.css'
import './assets/css/global.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.config.productionTip = false
Vue.component('Toolbar', Toolbar)
Vue.component('Title', Title)
Vue.component('CoursesMini', CoursesMini)
Vue.component('WhatOurCustomersSay', WhatOurCustomersSay)
Vue.component('ContactUs', ContactUs)
Vue.component('RegistrationLink', RegistrationLink)
Vue.component('Footer', Footer)
Vue.component('CoursesCol', CoursesCol)
Vue.component('Hooper', Hooper)
Vue.component('Slide', Slide)
Vue.component('Navigation', Navigation)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
