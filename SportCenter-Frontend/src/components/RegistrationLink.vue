<template>
    <div class="whole-screen p-5" id="registration-link">
        <div class="mb-5">
            <h1>Exclusive Offer: Get 30% Off Your First Course Package!</h1>
        </div>
        <div class="py-5">
            <p>Want to have access to all these incredible courses? Take advantage of our special offer
                today!
                <br>Click the button below to unlock your discount and start your fitness journey with us.
            </p>
        </div>
        <div id="container" class="pt-5">
            <button class="learn-more" @click="goToRegistration()">
                <span class="circle" aria-hidden="true">
                    <span class="icon arrow"></span>
                </span>
                <span class="button-text">Register Now</span>
            </button>
        </div>
    </div>
</template>

<script>
export default {
    methods: {
        goToRegistration() {
            this.$router.push('/registration');
        }
    }
};
</script>

<style>
.whole-screen {
    width: 100vw;
    height: 100vh;
    padding-top: 10% !important;
}

/* Transition mixin */
button {
    position: relative;
    display: inline-block;
    cursor: pointer;
    outline: none;
    border: 0;
    vertical-align: middle;
    text-decoration: none;
    background: transparent;
    padding: 0;
    font-size: inherit;
    font-family: inherit;
}

/* Learn more button */
button.learn-more {
    width: 12rem;
    height: auto;
}

/* Circle */
button.learn-more .circle {
    transition: all 0.45s cubic-bezier(0.65, 0, .076, 1);
    position: relative;
    display: block;
    margin: 0;
    width: 3rem;
    height: 3rem;
    background: var(--color-white);
    border-radius: 1.625rem;
}

/* Icon */
button.learn-more .circle .icon {
    transition: all 0.45s cubic-bezier(0.65, 0, .076, 1);
    position: absolute;
    top: 0;
    bottom: 0;
    margin: auto;
    background: var(--color-black);
}

/* Arrow icon */
button.learn-more .circle .icon.arrow {
    transition: all 0.45s cubic-bezier(0.65, 0, .076, 1);
    left: 0.625rem;
    width: 1.125rem;
    height: 0.125rem;
    background: none;
}

button.learn-more .circle .icon.arrow::before {
    position: absolute;
    content: '';
    top: -0.25rem;
    right: 0.0625rem;
    width: 0.625rem;
    height: 0.625rem;
    border-top: 0.125rem solid #171313;
    border-right: 0.125rem solid #171313;
    transform: rotate(45deg);
}

/* Button text */
button.learn-more .button-text {
    transition: all 0.45s cubic-bezier(0.65, 0, .076, 1);
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    padding: 0.75rem 0;
    margin: 0 0 0 1.85rem;
    color: var(--color-black);
    font-weight: 700;
    line-height: 1.6;
    text-align: center;
    text-transform: uppercase;
}

/* Hover effect */
button.learn-more:hover .circle {
    width: 100%;
}

button.learn-more:hover .circle .icon.arrow {
    background: var(--color-white);
    transform: translate(1rem, 0);
}

button.learn-more:hover .button-text {
    color: var(--color-black);
}

#container {
    float: right;
}

p {
    font-weight: 400;
    text-align: left;
    font-size: 120%;
}

#registration-link {
    background-color: var(--color-black);
    color: var(--color-white);
}
</style>