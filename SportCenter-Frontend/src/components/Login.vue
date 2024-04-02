<template>
    <div style="font-family: Rubik">
        <Toolbar /> 
        <div class="container-fluid">
            <div  id="login-page" class="row align-items-center p-5">
                <div class="col">
                    <div class="container justify-content-center">
                        <div class="justify-content-center pl-5">
                            <h1>
                                Own Your <span style="color: #F9F9F9">Journey,</span><br>
                                Keep <span style="color: #F9F9F9"><br>Slaying</span>
                            </h1>
                        </div>
                        <div class="row justify-content-center">
                            <div class="ball-row bouncingball"></div>
                        </div>
                    </div>
                </div>

                <form class="col form-group half-width d-flex flex-column justify-content-center">
                    <div class="container">
                        <div class="row my-xl-5 mr-xl-5">
                            <input v-model="email" class="form-control" type="text" placeholder="Your Email Address...">
                        </div>
                        <div class="row mt-xl-5 my-3 mr-xl-5">
                            <input v-model="password" class="form-control" type="password"
                                placeholder="Your Password...">
                        </div>
                        <div class="row justify-content-center mr-xl-5">
                            <p class="error" :class="{ 'hidden': !showErrorMessage }">{{ errorMessage }}</p>
                        </div>
                        <div class="row justify-content-center mr-xl-5">
                            <button class="p-2 px-3 rounded justify-content-center btn btn-outline px-4"
                                @click="login">Sign In</button>
                        </div>
                    </div>
                    <div class="row mt-xl-5 my-3 mr-xl-5">
                        <input v-model="password" class="form-control" type="password" placeholder="Your Password...">
                    </div>
                    <div class="row justify-content-center mr-xl-5">
                        <p class="error" :class="{ 'hidden': !showErrorMessage }">{{ errorMessage }}</p>
                    </div>
                    <div class="row justify-content-center mr-xl-5">
                        <button class="p-2 px-3 rounded justify-content-center btn btn-animation" @click="login">Login</button>
                    </div>
                </form>
            </div>
        </div>
        <Footer />
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
    #login-page {
        min-height: 100vh;
        font-family: "Rubik", sans-serif;
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
        font-size: 4em;
        z-index:950;
        text-align: center;
    }

.hidden {
    visibility: hidden;
}

    .bouncingball {
        width:60px;
        height:60px;
        border-radius:100%;
        background-image: linear-gradient(#fea20d, #c27903);
        animation: bounce 1s;
        transform: translateY(130px);
        animation-iteration-count: infinite;
        position:absolute;
        z-index:900;
    }

.ball-row {
    padding: 5%;
    margin: 35%;
}


@keyframes bounce {
    0% {
        top: 0;
        -webkit-animation-timing-function: ease-in;
    }

    40% {}

    50% {
        top: 140px;
        height: 60px;
        -webkit-animation-timing-function: ease-out;
    }

    55% {
        top: 160px;
        height: 50px;
        -webkit-animation-timing-function: ease-in;
    }

    65% {
        top: 120px;
        height: 60px;
        -webkit-animation-timing-function: ease-out;
    }

    95% {
        top: 0;
        -webkit-animation-timing-function: ease-in;
    }

    100% {
        top: 0;
        -webkit-animation-timing-function: ease-in;
    }
}
</style>