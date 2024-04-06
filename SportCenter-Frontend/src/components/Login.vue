<template>
    <div class="container-fluid p-0 m-0">
        <div class="page row align-items-start justify-content-center">
            <Toolbar />
            <div class="row p-5">
                <!-- Column 1 -->
                <div class="col">
                    <div class="container justify-content-center-lg">
                        <div class="row px-xl-4 mb-xl-5 justify-content-center">
                            <h1 class="mb-xl-5">Own Your Journey</h1>
                        </div>
                    </div>
                </div>
        
                <!-- Column 2 -->
                <div class="col">
                    <form class="form-group">
                        <div class="container justify-content-center">
                        <div class="row my-xl-5 mr-xl-5">
                            <input v-model="email" class="form-control" type="text" placeholder="Your Email Address...">
                        </div>
                        <div class="row mt-xl-5 my-3 mr-xl-5">
                            <input v-model="password" class="form-control" type="password" placeholder="Your Password...">
                        </div>
                        <div class="row justify-content-center mr-xl-5">
                            <p class="error" :class="{ 'hidden': !showErrorMessage }">{{ errorMessage }}</p>
                        </div>
                        <div class="row justify-content-center mr-xl-5">
                            <button class="p-2 px-3 rounded justify-content-center btn btn-outline px-4" @click="login">Sign In</button>
                        </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="area-orange higher" >
                <ul class="squares">
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
<style scoped>
    
    .btn-outline {
        background-color: #3e8ef1 !important;
        border-color: #3e8ef1 !important;
        color: #fff !important;
    }

    .btn-outline:hover {
        background-color: #2469bd !important;
        border-color: #2469bd !important;
        color: #fff !important;
    }

    .page {
        min-height: 100vh;
        font-family: "Rubik", sans-serif;
        background-color: #ffbc4b;

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
        background-color: #ffbc4b;
        color: #171313;
        border-color: rgba(0, 0, 0, 0.2);
        border-style: solid;
        border-width: 2px;
        padding: 20px;
    }

    .form-control::placeholder {
        color: #171313;
        opacity: 1; /* Firefox */
    }

    .form-control::-ms-input-placeholder { /* Edge 12 -18 */
        color: #171313;
    }

    .form-control:focus {
        box-shadow: 0 0 8px #1713139d;
    }

    h1 {
        z-index:950;
        color: #171313;
    }

    .hidden {
        visibility: hidden;
    }


    /* https://codepen.io/mohaiman/pen/MQqMyo */
    .area-orange{
        width: 100%;
        height:100%;
        z-index: -1; /* Ensure that this element is behind other content */
    }

    .squares{
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }

    .squares li{
        position: absolute;
        display: block;
        list-style: none;
        width: 20px;
        height: 20px;
        background: rgba(255, 255, 255, 0.5);
        animation: animate-square 25s linear infinite;
        bottom: -150px;
        
    }

    .squares li:nth-child(1){
        left: 25%;
        width: 80px;
        height: 80px;
        animation-delay: 0s;
    }


    .squares li:nth-child(2){
        left: 10%;
        width: 20px;
        height: 20px;
        animation-delay: 2s;
        animation-duration: 12s;
    }

    .squares li:nth-child(3){
        left: 70%;
        width: 20px;
        height: 20px;
        animation-delay: 4s;
    }

    .squares li:nth-child(4){
        left: 40%;
        width: 60px;
        height: 60px;
        animation-delay: 0s;
        animation-duration: 18s;
    }

    .squares li:nth-child(5){
        left: 65%;
        width: 20px;
        height: 20px;
        animation-delay: 0s;
    }

    .squares li:nth-child(6){
        left: 75%;
        width: 110px;
        height: 110px;
        animation-delay: 3s;
    }

    .squares li:nth-child(7){
        left: 35%;
        width: 150px;
        height: 150px;
        animation-delay: 7s;
    }

    .squares li:nth-child(8){
        left: 50%;
        width: 25px;
        height: 25px;
        animation-delay: 15s;
        animation-duration: 45s;
    }

    .squares li:nth-child(9){
        left: 20%;
        width: 15px;
        height: 15px;
        animation-delay: 2s;
        animation-duration: 35s;
    }

    .squares li:nth-child(10){
        left: 85%;
        width: 150px;
        height: 150px;
        animation-delay: 0s;
        animation-duration: 11s;
    }



    @keyframes animate-square {

        0%{
            transform: translateY(0) rotate(0deg);
            opacity: 1;
            border-radius: 0;
        }

        100%{
            transform: translateY(-1000px) rotate(720deg);
            opacity: 0;
            border-radius: 50%;
        }

    }
</style>