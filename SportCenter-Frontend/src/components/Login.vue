<template>
    <div>
        <div class="container-fluid">
            <div  id="eventregistration" class="row align-items-center p-5">
                <div class="col">
                    <h2 class="col-md-auto">Sign <span style="color:white">In</span></h2>
                </div>
                
                <form class="col form-group">
                <div class="container justify-content-center">
                    <div class="row m-5">
                        <input v-model="email" class="form-control" type="text" placeholder="Your Email Address...">
                    </div>
                    <div class="row mx-5">
                        <input v-model="password" class="form-control" type="password" placeholder="Your Password...">
                    </div>
                    <div class="row mt-2 justify-content-center">
                        <p class="error">error</p>
                    </div>
                    <div class="row mx-5 justify-content-center">
                        <button class="mx-5 p-2 px-3 rounded justify-content-center button-animation" @click="login">Login</button>
                    </div>
                </div>
                </form>
            </div>
            
        </div>
    </div>
    
</template>
<script>

import axios from "axios";
import config from "../../config";

const client = axios.create({
    // IMPORTANT: baseURL, not baseUrl
    baseURL: config.dev.backendBaseUrl
});

export default {
    data() {
        return {
        email: '',
        password: ''
        };
    },
    methods: {
        async login() {
            
            /* const formData = new FormData();
            formData.append('username', this.email);
            formData.append('password', this.password);
 */
            const params = new URLSearchParams();
            params.append('username', this.email);
            params.append('password', this.password);

            axios({
                method: 'post',
                url: 'http://localhost:8080/login',
                data: params,
                headers: { 'content-type': 'application/x-www-form-urlencoded' },
            })
            .then(function (response) {
                //handle success
                console.log(response);
            })
            .catch(function (response) {
                //handle error
                console.log(response);
            });
        }
    }
};
</script>
<style>
    #eventregistration {
        min-height: 100vh;
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background-image: linear-gradient(#5078a8, #7394BC, #9bb0c9);
    }

    .form-control:valid {
        background: transparent;
        border: white;
        color: white;
        border-color: rgba(255, 255, 255, 0.2);
        border-style: solid;
        border-width: 2px;
        padding: 20px;
    }

    .form-control::placeholder {
        color: white;
        opacity: 1; /* Firefox */
    }

    .form-control::-ms-input-placeholder { /* Edge 12 -18 */
        color: white;
    }

    .form-control:focus {
        box-shadow: inset 0 1px 1px rgba(255, 255, 255, 0.075), 0 0 8px rgba(255, 255, 255, 0.6);
    }

    h2 {
        font-size: 6em;
    }

    
    .button-animation {
        background-color: transparent;
        border-color: rgba(255, 255, 255, 0.2);
        border-style: solid;
        border-width: 2px;
        color: white;
        transition: background-color 0.3s ease; /* Transition effect for background */
    }


    .button-animation:hover {
        background-color: rgba(255, 255, 255, 0.2); /* Increase opacity on hover */
    }

</style>