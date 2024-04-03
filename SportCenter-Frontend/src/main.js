// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import App from './App'
import router from './router'
import Toolbar from './components/Toolbar'
import Title from './components/Title'
import CoursesMini from './components/CoursesMini'
import InstructorsMini from './components/InstructorsMini'
import ContactUs from './components/ContactUs'
import RegistrationForm from './components/RegistrationForm'
import Footer from './components/Footer'
import { Hooper, Slide, Navigation} from 'hooper';
import 'hooper/dist/hooper.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap/dist/css/bootstrap.css'
import './assets/css/global.css'

Vue.use(BootstrapVue)
Vue.config.productionTip = false
Vue.component('Toolbar', Toolbar)
Vue.component('Title', Title)
Vue.component('CoursesMini', CoursesMini)
Vue.component('InstructorsMini', InstructorsMini)
Vue.component('ContactUs', ContactUs)
Vue.component('RegistrationForm', RegistrationForm)
Vue.component('Footer', Footer)
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
