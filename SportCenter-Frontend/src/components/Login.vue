<template>
    <div class="container-fluid p-0 m-0">
        <Toolbar />
        <div class="page pt-5 mt-5 row align-items-center justify-content-center">
            <div class="row m-0 p-5 align-items-center mt-5">
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
                    fetch('http://localhost:8080/role-id', {
                        method: 'GET',
                        mode: "cors",
                        headers: {
                            'Authorization': basicAuth,
                        },
                        credentials: 'include' // Ensure cookies are sent with the request,
                    }).then((roleResponse) => {
                        roleResponse.text().then(role => {
                            const role_and_id = role.split(',');
                            role = role_and_id[0].toLowerCase();
                            const id = role_and_id[1];
                            
                            // Save cookies and change page
                            this.$cookies.set('username', this.email);
                            this.$cookies.set('password', this.password);
                            this.$cookies.set('role', role);
                            this.$cookies.set('id', id);

                            console.log('Created new cookies:');
                            console.log('username: ', decodeURIComponent(this.$cookies.get('username')));
                            console.log('password: ', this.$cookies.get('password'));
                            console.log('role: ', this.$cookies.get('role'));
                            console.log('id: ', this.$cookies.get('id'));
                            
                            this.$router.push('/');

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
            .catch(error => {
                this.showErrorMessage = true;
                console.error('Error fetching role:', error);
            }); 

    }
}

};
</script>
<style scoped>
    .btn-outline {
        background-color: var(--color-azure) !important;
        border-color: var(--color-azure) !important;
        color: var(--color-white) !important;
    }

    .btn-outline:hover {
        background-color: var(--color-storm) !important;
        border-color: var(--color-storm) !important;
        color: var(--color-white) !important;
    }

    .page {
        min-height: 100vh;
        background-color: var(--color-sunflower);
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
        background-color: var(--color-sunflower);
        color: var(--color-black);
        border-color: rgba(0, 0, 0, 0.2);
        border-style: solid;
        border-width: 2px;
        padding: 20px;
    }

    .form-control::placeholder {
        color: var(--color-black);
        opacity: 1; /* Firefox */
    }

    .form-control::-ms-input-placeholder { /* Edge 12 -18 */
        color: var(--color-black);
    }

    .form-control:focus {
        box-shadow: 0 0 8px #1713139d;
    }

    h1 {
        z-index:950;
        color: var(--color-black);
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