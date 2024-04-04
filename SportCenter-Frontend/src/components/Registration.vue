<template>
    <div class="container-fluid p-0 m-0">
        <div class="page row align-items-start justify-content-center">
            <Toolbar />
            <div class="row align-items-center p-5">
                <!-- Column 1 -->
                <div class="col">
                    <h1 class="mb-xl-5">Break the routine</h1>
                </div>
        
                <!-- Column 2 -->
                <div class="col">
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
                        <button class="p-2 px-3 rounded justify-content-center btn btn-outline px-4" @click="login">Register</button>
                    </div>
                    </div>
                </form>
                </div>
            </div>
            
            <div class="area-blue" >
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
            errorMessage: 'Invalid email or password', // Initialize error message
            showErrorMessage: false
        };
    },
    methods: {

    login() {
        const params = new FormData();
        params.append('username', this.email);
        params.append('password', this.password);

        const basicAuth = 'Basic ' + btoa(this.email + ':' + this.password);

        fetch('http://localhost:8080/login', {
            method: 'POST',
            body: params,
            headers: {
                'Authorization': basicAuth,
            },
            credentials: 'include', // Ensure cookies are sent with the request,
            mode: "cors",
        })  .then(response => response.text())
            .then(result => {
                if (result === 'success') {
                    console.log('Successful')
                    fetch('http://localhost:8080/role', {
                        method: 'GET',
                        mode: "cors",
                        headers: {
                            'Authorization': basicAuth,
                        },
                        credentials: 'include' // Ensure cookies are sent with the request,
                    }).then((roleResponse) => {
                        roleResponse.text().then(role => {
                            console.log('Role:', role);
                        }).catch(error => {
                            console.error('Error reading role text:', error);
                        });
                    }).catch(error => {
                        console.error('Error fetching role:', error);
                    }); 
                }
                else {
                    this.showErrorMessage = true;
                }
            })
            .catch(error => console.log('error', error));

            

    },
    switchToHomePage() {
        this.$router.push('/'); // Navigate to the '/' route
    }
}

};
</script>
<style>
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

    body {
        background-image: linear-gradient(#3e8ef1);
    }

    .page {
        min-height: 100vh;
        font-family: "Rubik", sans-serif;

        width: 100%;
        position: absolute;
        top:0;
        margin: 0px;
        padding: 0px;
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