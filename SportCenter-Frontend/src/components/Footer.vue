<template>
    <div style="font-family: Rubik; background-color: #524E5E;">
        <footer class="py-3 my-auto">
            <ul class="nav justify-content-center pb-3 mb-3">
                <li class="nav-item">
                    <a href="#" class="nav-link px-2" @click="goToHome">Home</a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link px-2" @click="goToCourses">Courses</a>
                </li>
                <li class="nav-item">
                    <a href="#" class="nav-link px-2" @click="goToInstructors">Instructors</a>
                </li>
                <li class="nav-item">
                    <a href="#contact-us" class="nav-link px-2">Contact Us</a>
                </li>
            </ul>
            <p class="text-center text-muted">© 2024 FitHub, P12.Inc</p>
        </footer>
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
        }
    }
};
</script>