<template>
    <div class="pl-5 pt-5 pr-5">
        <div class="d-flex align-items-center">
            <h2 class="mr-auto">Trending Courses</h2>
            <router-link to="/courses" class="pr-3" @mouseover="changeIcon" @mouseleave="resetIcon">
                View All Courses
            </router-link>
            <div>
                <b-icon :icon="icon" font-scale="1.75"></b-icon>
            </div>
        </div>
        <div class="mt-4">
            <div class="row">
                <div class="col-md-6 col-lg-3 col-sm-12">
                    <CoursesCol black-heading="STRETCHING" title="Goat Yoga"
                        description="Discover serenity and strength with Goat Yoga, where yoga meets playful interaction with adorable goats, fostering relaxation and joy."
                        image-url="https://hellericksfarm.com/wp-content/uploads/2023/03/goat-yoga.jpeg"
                        alt-text="goat-yoga" />
                </div>
                <div class="col-md-6 col-lg-3 col-sm-12">
                    <CoursesCol black-heading="MARTIAL ARTS" title="Jiu Jitsu 101" description="Master the art of self-defense and discipline in Jiu Jitsu 101, where you'll learn
                            fundamental techniques and strategies to protect yourself while promoting physical
                            and mental wellness."
                        image-url="https://cdn.evolve-mma.com/wp-content/uploads/2015/05/why-bjj-is-the-perfect-martial-art.jpg"
                        alt-text="jiu-jitsu" />
                </div>
                <div class="col-md-6 col-lg-3 col-sm-12">
                    <CoursesCol black-heading="STRENGTH TRAINING" title="Weightlifting II" description="Embark on a journey to amplify your strength and endurance in Weightlifting II,
                            a course designed to elevate your lifting techniques and push your fitness boundaries."
                        image-url="https://jackcityfitness.com/wp-content/uploads/10-Essential-Weight-Lifting-Tips.jpg"
                        alt-text="weightlifting" />
                </div>
                <div class="col-md-6 col-lg-3 col-sm-12">
                    <CoursesCol black-heading="CARDIO" title="Zumba For All" description="Unleash your energy and groove to the rhythm in Zumba For All, an exhilarating dance fitness
                            program that blends Latin and international music for a fun and effective workout."
                        image-url="https://www.verywellfit.com/thmb/WtaRzGOCbJdVYFlWr_7VOfwn_Ow=/3000x2002/filters:no_upscale():max_bytes(150000):strip_icc()/zumba-fatcamera-c9d4ee824a0f4fda883484f878abc8ae.jpg"
                        alt-text="zumba" />

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
</style>
