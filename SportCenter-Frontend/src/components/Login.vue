<template>
    <div>
        <div class="container-fluid">
            <div  id="eventregistration" class="row align-items-center p-5">
                <div class="col">
                    <div class="container justify-content-center">
                        <div class="row px-xl-4 mb-xl-5 justify-content-center">
                            <h2 class="mb-xl-5">
                                BREAK THE <span style="color:white">ROUTINE</span>
                            </h2>
                        </div>
                        <div class="row justify-content-center ball-row">
                            <div class="bouncingball"></div>
                        </div>
                    </div>
                </div>
                
                <form class="col form-group">
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
                        <button class="p-2 px-3 rounded justify-content-center button-animation" @click="login">Login</button>
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
            password: '',
            errorMessage: 'Invalid email or password', // Initialize error message
            showErrorMessage: false
        };
    },
    methods: {
    async login() {
        const params = new URLSearchParams();
        params.append('username', this.email);
        params.append('password', this.password);

        try {
            const response = await axios.post('http://localhost:8080/login', params, {
                headers: { 'content-type': 'application/x-www-form-urlencoded' }
            });

            // Handle success
            console.log(response);
            if (response.data === 'success') {
                console.log('Login successful');
                this.switchToHomePage(); // Call another function to switch page
            } else {
                console.log('Login failed');
                this.showErrorMessage = true; // Show error message
            }
        } catch (error) {
            console.log('Login failed');
            this.showErrorMessage = true; // Show error message

        }
    },
    switchToHomePage() {
        this.$router.push('/'); // Navigate to the '/' route
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

    .hidden {
        visibility: hidden;
    }

    .error {
        color: #2c3e50;
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
        min-height: 100px;
    }


@keyframes bounce {
	0% {top: 0;
		-webkit-animation-timing-function: ease-in;
	}
	40% {}
	50% {top: 140px;
		height: 60px;
		-webkit-animation-timing-function: ease-out;
	}
	55% {top: 160px; height: 50px; 
		-webkit-animation-timing-function: ease-in;}
	65% {top: 120px; height: 60px; 
		-webkit-animation-timing-function: ease-out;}
	95% {
		top: 0;		
		-webkit-animation-timing-function: ease-in;
	}
	100% {top: 0;
		-webkit-animation-timing-function: ease-in;
	}
}

</style>