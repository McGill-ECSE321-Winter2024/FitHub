<template>
    <div>
        <nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary mx-2">
            <div class="container-fluid border-bottom pb-2">
                <a class="navbar-brand mx-5" href="#" @click="goToHome">FitHub</a>
                <div class="collapse navbar-collapse justify-content-center" id="navbarCenteredExample">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#" @click="goToHome">Home</a>
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
                </div>
                <button type="button" class="btn btn-primary px-4 mx-4" @click="goToRegistration">
                    Join Now!
                </button>
                <button type="button" class="btn btn-secondary px-4" @click="goToSignIn">
                    Sign In
                </button>
            </div>
        </nav>
    </div>
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