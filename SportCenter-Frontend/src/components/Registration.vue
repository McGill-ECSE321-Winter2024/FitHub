<template>
    <div class="container-fluid p-0 m-0">
        <Toolbar />
        <div class="page mt-5 pt-5 row align-items-start justify-content-center">
            <div class="row m-0 p-0 align-items-center p-5 mt-5">
                <!-- Column 1 -->
                <div class="col">
                    <h1 class="mb-xl-5">Break the routine</h1>
                </div>
        
                <!-- Column 2 -->
                <div class="col p-0 m-0">
                <form class="form-group">
                    <div class="container justify-content-center">
                        <div class="row mt-xl-3 my-3 mr-xl-5">
                            <input v-model="imageURL" class="form-control" type="text" placeholder="Your Profile Picture...">
                        </div>
                        <div class="row mt-xl-3 my-3 mr-xl-5">
                            <input v-model="name" class="form-control" type="text" placeholder="Your Name...">
                        </div>
                        <div class="row my-xl-3 mr-xl-5">
                            <input v-model="email" class="form-control" type="text" placeholder="Your Email Address...">
                        </div>
                        <div class="row mt-xl-3 my-3 mr-xl-5">
                            <input v-model="password" class="form-control" type="password" placeholder="Your Password...">
                        </div>
                        <div class="row mt-xl-3 my-3 mr-xl-5">
                            <input v-model="pronouns" class="form-control" type="text" placeholder="Your Pronouns...">
                        </div>
                        <div class="row justify-content-center mr-xl-5">
                            <p class="error" :class="{ 'hidden': !showErrorMessage }">{{ errorMessage }}</p>
                        </div>
                        <div class="row justify-content-center mr-xl-5">
                            <button class="p-2 px-3 rounded justify-content-center btn btn-outline px-4" @click="register">Register</button>
                        </div>
                    </div>
                </form>
                </div>
            </div>
            
            <div class="area-blue higher" >
                    <ul class="circles">
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                    </ul>
            </div >
        </div>
    </div>
  </template>
  
<script>
import axios from 'axios';

export default {
    data() {
        return {
            email: '',
            password: '',
            name: '',
            imageURL: '',
            pronouns: '',
            errorMessage: 'Invalid email or password', // Initialize error message
            showErrorMessage: false,
        };
    },
    methods: {

    register() {
        // Create a JSON object with the data to be sent in the POST request
        const requestBody = {
            email: this.email,
            password: this.password,
            name: this.name,
            imageURL: this.imageURL,
            pronouns: this.pronouns,
        };

        fetch('http://localhost:8080/public/customers', {
            method: 'POST',
            body: JSON.stringify(requestBody),
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include', // Ensure cookies are sent with the request,
            mode: "cors",
        })  .then(response => response.text())
            .then(result => {
                console.log(result);

                result = JSON.parse(result);

                if (result.error == "") {
                    // Save cookies and change page
                    this.$cookies.set('username', result.email);
                    this.$cookies.set('password',  this.password);
                    this.$cookies.set('role',  result.type.toLowerCase());
                    this.$cookies.set('id',  result.id);
    
                    console.log('Created new cookies:');
                    console.log('username: ', decodeURIComponent(this.$cookies.get('username')));
                    console.log('password: ', this.$cookies.get('password'));
                    console.log('role: ', this.$cookies.get('role'));
                    console.log('id: ', this.$cookies.get('id'));
                    
                    this.$router.push('/');
                }
                else {
                    this.errorMessage = result.error;
                    this.showErrorMessage = true;
                }
            })
            .catch(error => console.log('error', error));

    }
}

};
</script>
<style scoped>

    .btn-outline {
        background-color: #fb7ea8 !important;
        border-color: #fb7ea8 !important;
        color: #fff !important;
    }

    .btn-outline:hover {
        background-color: #de4e7e !important;
        border-color: #de4e7e !important;
        color: #fff !important;
    }

    .page {
        min-height: 100vh;
        background-color: #3e8ef1;

        width: 100%;
        position: absolute;
        top:0;
        margin: 0px;
        padding: 0px;
        z-index: 0; /* Ensure that this element is behind other content */
    }

    .higher {
        z-index: 900; /* Ensure that this element is behind other content */
    }

    .form-control:valid {
        background: #3e8ef1;
        color: #cdf567;
        border-color: #cdf56746;
        border-style: solid;
        border-width: 2px;
        padding: 20px;
    }

    .form-control::placeholder {
        color: #cdf567;
        opacity: 1; /* Firefox */
    }

    .form-control::-ms-input-placeholder { /* Edge 12 -18 */
        color: #cdf567;
    }

    .form-control:focus {
        box-shadow: 0 0 8px #cdf5679d;
    }

    h1 {
        z-index:950;
        color: #cdf567;
    }

    .hidden {
        visibility: hidden;
    }

    /* https://codepen.io/mohaiman/pen/MQqMyo */
    .area-blue{
        width: 100%;
        height:100%;
        z-index: -1; /* Ensure that this element is behind other content */
    }

    .circles{
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }

    .circles li{
        position: absolute;
        display: block;
        list-style: none;
        width: 20px;
        height: 20px;
        background: rgba(255, 255, 255, 0.5);
        animation: animate-round 25s linear infinite;
        bottom: -150px;   
    }

    .circles li:nth-child(1){
        left: 25%;
        width: 80px;
        height: 80px;
        animation-delay: 0s;
    }


    .circles li:nth-child(2){
        left: 10%;
        width: 20px;
        height: 20px;
        animation-delay: 2s;
        animation-duration: 12s;
    }

    .circles li:nth-child(3){
        left: 70%;
        width: 20px;
        height: 20px;
        animation-delay: 4s;
    }

    .circles li:nth-child(4){
        left: 40%;
        width: 60px;
        height: 60px;
        animation-delay: 0s;
        animation-duration: 18s;
    }

    .circles li:nth-child(5){
        left: 65%;
        width: 20px;
        height: 20px;
        animation-delay: 0s;
    }

    .circles li:nth-child(6){
        left: 75%;
        width: 110px;
        height: 110px;
        animation-delay: 3s;
    }

    .circles li:nth-child(7){
        left: 35%;
        width: 150px;
        height: 150px;
        animation-delay: 7s;
    }

    .circles li:nth-child(8){
        left: 50%;
        width: 25px;
        height: 25px;
        animation-delay: 15s;
        animation-duration: 45s;
    }

    .circles li:nth-child(9){
        left: 20%;
        width: 15px;
        height: 15px;
        animation-delay: 2s;
        animation-duration: 35s;
    }

    .circles li:nth-child(10){
        left: 85%;
        width: 150px;
        height: 150px;
        animation-delay: 0s;
        animation-duration: 11s;
    }



    @keyframes animate-round {

        0%{
            transform: translateY(0) rotate(0deg);
            opacity: 1;
            border-radius: 100%;
        }

        100%{
            transform: translateY(-1000px) rotate(720deg);
            opacity: 0;
            border-radius: 5%;
        }

    }
</style>