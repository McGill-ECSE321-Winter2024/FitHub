<template>
    <div class="p-5">
        <h2>In the Limelight</h2>
        <h2 style="font-weight: 500">What Our Customers Say</h2>
        <div class="d-flex align-items-center justify-content-center">
            <hooper group="group1" class="pt-5">
                <slide class="slide-style whole-width testimonial">
                    "FitHub has been a game-changer for me! The vibrant atmosphere and top-notch facilities 
                    keep me motivated. From invigorating classes to state-of-the-art equipment, it's a fitness 
                    paradise!" - Anjali Singhal
                </slide>
                <slide class="slide-style whole-width testimonial">
                    "FitHub isn't just a gym; it's a lifestyle. The supportive staff and diverse 
                    range of activities make every workout session exciting. Whether I'm hitting the 
                    weights or joining a group class, I always leave feeling accomplished." - Émilia Gagné
                </slide>
                <slide class="slide-style whole-width testimonial">
                    "Finding peace amidst the hustle and bustle of city life can be tough, but 
                    FitHub provides the perfect escape. The tranquil yoga studio and serene meditation 
                    sessions help me unwind and reconnect with myself. It's a sanctuary for both body and mind." - James Luu
                </slide>
                <slide class="slide-style whole-width testimonial">
                    "FitHub isn't your average gym—it's a place where fitness meets fun! 
                    The energetic instructors and dynamic workouts make exercising enjoyable. 
                    Whether I'm sweating it out in a spin class or challenging myself on the climbing wall, 
                    there's never a dull moment." - Julia Grenier
                </slide>
                <slide class="slide-style whole-width testimonial">
                    "FitHub has become my second home. The welcoming community and sense of 
                    camaraderie keep me coming back for more. Whether I'm bonding with fellow members 
                    during group workouts or enjoying a post-exercise smoothie at the cafe, it's a place 
                    where friendships flourish." - Sahar Fathi
                </slide>
                <slide class="slide-style whole-width testimonial">
                    "FitHub isn't just about physical transformation—it's about personal growth. The supportive 
                    environment and encouragement from staff push me to surpass my limits. Whether I'm achieving 
                    new fitness milestones or setting ambitious goals, FitHub empowers me to be the best version 
                    of myself." - Tayba Jusab
                </slide>
            </hooper>
        </div>
        <div class="d-flex align-items-start justify-content-start px-5 pb-5">
            <hooper group="group1" :itemsToShow="3" :centerMode="true" style="height: 50vh">
                <slide class="slide-style third-width px-3">
                    <img src="../assets/customers/anjali.jpeg" alt="Anjali" class="hover-img"
                        @mouseenter="hoverBackgrounds[0] = true" @mouseleave="hoverBackgrounds[0] = false" />
                    <div v-if="hoverBackgrounds[0]" class="hover-background"></div>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img src="../assets/customers/emilia.jpeg" alt="Emilia" class="hover-img"
                        @mouseenter="hoverBackgrounds[1] = true" @mouseleave="hoverBackgrounds[1] = false" />
                    <div v-if="hoverBackgrounds[1]" class="hover-background"></div>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img src="../assets/customers/james.jpeg" alt="James" class="hover-img"
                        @mouseenter="hoverBackgrounds[2] = true" @mouseleave="hoverBackgrounds[2] = false" />
                    <div v-if="hoverBackgrounds[2]" class="hover-background"></div>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img src="../assets/customers/julia.jpeg" alt="Julia" class="hover-img"
                        @mouseenter="hoverBackgrounds[3] = true" @mouseleave="hoverBackgrounds[3] = false" />
                    <div v-if="hoverBackgrounds[3]" class="hover-background"></div>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img src="../assets/customers/sahar.jpeg" alt="Sahar" class="hover-img"
                        @mouseenter="hoverBackgrounds[4] = true" @mouseleave="hoverBackgrounds[4] = false" />
                    <div v-if="hoverBackgrounds[4]" class="hover-background"></div>
                </slide>
                <slide class="slide-style third-width px-3">
                    <img src="../assets/customers/tayba.jpeg" alt="Tayba" class="hover-img"
                        @mouseenter="hoverBackgrounds[5] = true" @mouseleave="hoverBackgrounds[5] = false" />
                    <div v-if="hoverBackgrounds[5]" class="hover-background"></div>
                </slide>
                <navigation slot="hooper-addons"></navigation>
            </hooper>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            hoverBackgrounds: [false, false, false, false, false, false] // Array to track hover background state for each slide
        };
    }
};
</script>

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

.testimonial {
    font-size: 24px;
    font-weight: 500;
}

.hover-img {
    width: 100%;
}

.hover-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(255, 0, 0, 0.5);
    border: 10px solid red;
    /* Adjust color and opacity as needed */
    z-index: -1;
    /* Ensure it's behind the image */
    opacity: 0;
    transition: opacity 0.3s ease;
    /* Adjust transition as needed */
}
</style>