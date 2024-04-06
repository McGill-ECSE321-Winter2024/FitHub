<template>
    <div class="footer p-5 d-flex">
            <router-link to="/" class="navbar-brand mr-auto p-2">
                <img width="100" height="100" src="https://img.icons8.com/ios-filled/100/acrobatics.png"
                    alt="acrobatics" />itHub
            </router-link>
        <nav class="navbar p-2">
            <div>
                <ul class="navbar-nav nav flex-column">
                    <li class="nav-item">
                        <router-link to="/courses" class="nav-link">Courses</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/instructors" class="nav-link">Instructors</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/login" class="nav-link">Already a Member?</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/registration" class="nav-link">Register Here!</router-link>
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