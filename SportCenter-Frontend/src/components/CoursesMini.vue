<template>
    <div class="pl-5 pt-5 pr-5">
        <div class="d-flex align-items-center">
            <h2 class="mr-auto">Trending Courses</h2>
            <a class="pr-3" @mouseover="changeIcon" @mouseleave="resetIcon" @click="goToCourses" href="#">
                View All Courses
            </a>
            <div>
                <b-icon :icon="icon" font-scale="1.75"></b-icon>
            </div>
        </div>
        <div class="mt-4">
            <div class="row">
                <div class="col-md-6 col-lg-3 col-sm-12">
                    <div>
                        <img 
                            src="https://hellericksfarm.com/wp-content/uploads/2023/03/goat-yoga.jpeg" 
                            alt="goat-yoga"
                            class="w-100 h-100"
                        />
                    </div>
                    <h3><span class="black-heading">STRETCHING</span></h3>
                    <div>
                        <h3>Goat Yoga</h3>
                        <p>Discover serenity and strength with Goat Yoga, where yoga meets playful 
                            interaction with adorable goats, fostering relaxation and joy.</p>
                    </div>
                </div>
                <div class="col-md-6 col-lg-3 col-sm-12">
                    <div>
                        <img 
                            src="https://cdn.evolve-mma.com/wp-content/uploads/2015/05/why-bjj-is-the-perfect-martial-art.jpg" 
                            alt="jiu-jitsu"
                            class="w-100 h-100"
                        />
                    </div>
                    <h3><span class="black-heading">MARTIAL ARTS</span></h3>
                    <div>
                        <h3>Jiu Jitsu 101</h3>
                        <p>Master the art of self-defense and discipline in Jiu Jitsu 101, where you'll learn 
                            fundamental techniques and strategies to protect yourself while promoting physical 
                            and mental wellness.</p>
                    </div>
                </div>
                <div class="col-md-6 col-lg-3 col-sm-12">
                    <div>
                        <img 
                            src="https://jackcityfitness.com/wp-content/uploads/10-Essential-Weight-Lifting-Tips.jpg" 
                            alt="weightlifting"
                            class="w-100 h-100"
                        />
                    </div>
                    <h3><span class="black-heading">STRENGTH TRAINING</span></h3>
                    <div>
                        <h3>Weightlifting II</h3>
                        <p>Embark on a journey to amplify your strength and endurance in Weightlifting II, 
                            a course designed to elevate your lifting techniques and push your fitness boundaries.</p>
                    </div>
                </div>
                <div class="col-md-6 col-lg-3 col-sm-12">
                    <div>
                        <img 
                            src="https://www.verywellfit.com/thmb/WtaRzGOCbJdVYFlWr_7VOfwn_Ow=/3000x2002/filters:no_upscale():max_bytes(150000):strip_icc()/zumba-fatcamera-c9d4ee824a0f4fda883484f878abc8ae.jpg" 
                            alt="weightlifting"
                            class="w-100 h-100"
                        />
                    </div>
                    <h3><span class="black-heading">CARDIO</span></h3>
                    <div>
                        <h3>Zumba For All</h3>
                        <p>Unleash your energy and groove to the rhythm in Zumba For All, an exhilarating dance fitness 
                            program that blends Latin and international music for a fun and effective workout.</p>
                    </div>
                </div>
            </div>
        </div>


    </div>
</template>

<script>
export default {
    data() {
        return {
            icon: 'arrow-right-circle',
        };
    },
    methods: {
        changeIcon() {
            this.icon = 'arrow-right-circle-fill';
        },
        resetIcon() {
            this.icon = 'arrow-right-circle';
        },
        goToCourses() {
            this.$router.push('/courses');
        }
    }
};
</script>

<style scoped>
a {
    color: var(--color-black)
}

h3 {
    font-size: 24px;
    font-weight: 700;
}

p {
    font-size: 14px;
    font-weight: 400;
}

.black-heading {
    font-size: 14px;
    color: #FFF;
    background-color: var(--color-black);
    font-weight: 700;
    padding: 1%;
}

</style>
