<template>
    <div style="font-family: Rubik">
        <nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary">
            <div class="container-fluid">
                <a class="navbar-brand mx-5" href="#">FitHub</a>
                <div class="collapse navbar-collapse justify-content-center" id="navbarCenteredExample">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" @click="goToCourses">Courses</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" @click="goToInstructors">Instructors</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Contact Us</a>
                        </li>
                    </ul>
                </div>
                <button type="button" class="btn btn-primary px-4 mx-4">
                    Join Now!
                </button>
                <button type="button" class="btn btn-secondary px-4" @click="goToSignIn">
                    Sign In
                </button>
            </div>
        </nav>
        <hr>
    </div>
</template>

<script>

export default {
    methods: {
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