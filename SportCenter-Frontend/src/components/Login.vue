<template>
    <div style="font-family: Rubik">
        <Toolbar /> 
        <div class="container-fluid">
            <div class="row align-items-center whole-screen-minus">
                <div class="col half-width">
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
                </form>
            </div>
        </div>
        <Footer />
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

            fetch('http://localhost:8080/login', {
                method: 'POST',
                body: params,
                credentials: 'include', // Ensure cookies are sent with the request,
            }).then((response) => {
                if (response.url === 'http://localhost:8080/login-success') {
                    console.log('Successful')
                    fetch('http://localhost:8080/role', {
                        method: 'GET',
                        credentials: 'include', // Ensure cookies are sent with the request,
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
                console.log(response);
            });

        },
        switchToHomePage() {
            this.$router.push('/'); // Navigate to the '/' route
        }
    }

};
</script>
<style>
.button-animation {
    background-color: transparent;
    border-color: rgba(255, 255, 255, 0.2);
    border-style: solid;
    border-width: 2px;
    color: white;
    transition: background-color 0.3s ease;
    /* Transition effect for background */
}


.button-animation:hover {
    background-color: rgba(255, 255, 255, 0.2);
    /* Increase opacity on hover */
}

.hidden {
    visibility: hidden;
}

.error {
    color: #2c3e50;
}

.bouncingball {
    width: 60px;
    height: 60px;
    border-radius: 100%;
    background-image: linear-gradient(#fea20d, #c27903);
    position: absolute;
    animation: bounce 1s infinite;
    transform: translateY(130px);
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