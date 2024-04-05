<template>
    <div class="footer p-5 d-flex">
            <a class="navbar-brand mr-auto p-2" href="#" @click="goToHome">
                <img width="100" height="100" src="https://img.icons8.com/ios-filled/100/acrobatics.png"
                    alt="acrobatics" />itHub
            </a>
        <nav class="navbar p-2">
            <div>
                <ul class="navbar-nav nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="#" @click="goToCourses">Courses</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" @click="goToInstructors">Instructors</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" @click="goToLogin">Already a Member?</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" @click="goToRegistration">Register Here!</a>
                    </li>
                </ul>
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
        }
    }
};
</script>

<style scoped>
.footer {
    background-color: var(--color-sunflower);
}

.nav-item {
    font-weight: 500;
    font-size: 24px;
}

.navbar-brand {
    color: var(--color-black);
    font-size: 75px;
}
</style>