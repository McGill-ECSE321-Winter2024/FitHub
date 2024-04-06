<template>
    <div class="p-5">
        <h2>In the Limelight</h2>
        <h2 style="font-weight: 500">What Our Customers Say</h2>
        <div class="d-flex align-items-center justify-content-center pr-5">
            <hooper group="group1" class="pt-5">
                <slide class="slide-style whole-width">
                    Anjali Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    Émilia Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    James Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    Julia Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    Sahar Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    Tayba Testimonial
                </slide>
            </hooper>
        </div>
        <div class="d-flex align-items-start justify-content-start p-5">
            <hooper group="group1" :itemsToShow="3" :centerMode="true" style="height: 50vh">
                <slide class="slide-style third-width px-3">
                    <img width="100%" src="../assets/customers/anjali.jpeg"/>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img width="100%" src="../assets/customers/emilia.jpeg"/>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img width="100%" src="../assets/customers/james.jpeg"/>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img width="100%" src="../assets/customers/julia.jpeg"/>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img width="100%" src="../assets/customers/sahar.jpeg"/>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img width="100%" src="../assets/customers/tayba.jpeg"/>
                </slide>

                <navigation slot="hooper-addons"></navigation>
            </hooper>
        </div>
    </div>
</template>

<style scoped>
.whole-screen {
    width: 100vw;
    height: 100vh;
}

.whole-width {
    width: 100vw;
}

.third-width {
    width: 33vw;
}

img {
    position: relative;
    z-index: 1;
    cursor: pointer;
}

img:hover {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: var(--color-salmon);
    z-index: -1;
    animation: splash 0.5s ease-out;
}

@keyframes splash {
    0% {
        transform: scale(0);
        opacity: 0;
    }
    100% {
        transform: scale(2);
        opacity: 0;
    }
}
</style>