<template>
    <nav class="navbar navbar-expand-lg navbar-light">
        <a class="navbar-brand" href="#" @click="goToHome">
            <img width="25" height="25" src="https://img.icons8.com/ios-glyphs/90/FFFFFF/acrobatics.png"
                alt="acrobatics" />itHub</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
            aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse border-bottom" id="navbarToggler">
            <ul class="navbar-nav justify-content-center mx-auto mt-2 mt-lg-0 py-2">
                <li class="nav-item">
                    <a class="nav-link active" href="#" @click="goToHome">Home <span
                            class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" @click="goToCourses">Courses</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" @click="goToInstructors">Instructors</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#contact-us">Contact Us</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <button type="button" class="btn btn-primary px-4 mx-2 mb-3" @click="goToRegistration">
                        Join Now!
                    </button>
                </li>
                <li class="nav-item">
                    <button type="button" class="btn btn-secondary px-4" @click="goToSignIn">
                        Sign In
                    </button>
                </li>
            </ul>
        </div>
    </nav>
</template>


<script>
export default {
    mounted() {
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();

                document.querySelector(this.getAttribute('href')).scrollIntoView({
                    behavior: 'smooth'
                });
            });
        });
    },
    methods: {
        goToHome() {
            this.$router.push('/');
        },

        goToCourses() {
            this.$router.push('/courses');
        },
        goToInstructors() {
            this.$router.push('/instructors')
        },
        goToSignIn() {
            this.$router.push('/login');
        },
        goToRegistration() {
            const registrationComponent = document.getElementById('join-now');
            if (registrationComponent) {
                registrationComponent.scrollIntoView({ behavior: 'smooth' });
            }
        }
    }
};
</script>

<style scoped>
@media (max-width: 991.98px) {

    /* Adjusted to target when navbar is collapsed */
    .navbar-collapse {
        padding-top: 20px;
        /* Increase the top padding */
        padding-bottom: 20px;
        /* Increase the bottom padding */
    }
}
</style>