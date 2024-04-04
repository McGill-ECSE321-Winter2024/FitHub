<template>
    <nav class="navbar navbar-expand-lg navbar-light" :style="{ backgroundColor: bgColor , transition: 'background-color 1.5s'}">
        <a class="navbar-brand" href="#" @click="goToHome">
            <img width="40" height="40" src="https://img.icons8.com/ios-filled/100/acrobatics.png"
                alt="acrobatics" />itHub
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
            aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <transition name="toolbar-slide">
            <div v-if="mounted" class="collapse navbar-collapse" id="navbarToggler">
                <ul class="navbar-nav ml-auto">
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
                        <a class="nav-link" href="#">Register Here!</a>
                    </li>
                </ul>
            </div>
        </transition>
    </nav>
</template>

<script>
import { EventBus } from '@/EventBus';

export default {
    data() {
        return {
            bgColor: '#FFD0D5',
            mounted: false
        };
    },
    mounted() {
        setTimeout(() => {
            this.mounted = true;
        }, 100);
        EventBus.$on('beforeSlideOccurred', this.handleChangeBackgroundColor);
    },
    beforeDestroy() {
        EventBus.$off('beforeSlideOccurred', this.handleChangeBackgroundColor);
    },
    methods: {
        goToHome() {
            this.$router.push('/');
        },
        goToCourses() {
            this.$router.push('/courses');
        },
        goToInstructors() {
            this.$router.push('/instructors');
        },
        goToLogin() {
            this.$router.push('/login');
        },
        handleChangeBackgroundColor(payload) {
            const { currentSlide } = payload;
            if (currentSlide === 0 || currentSlide === 1) {
                this.updateBackgroundColor('#FFE818');
            } else if (currentSlide === 2 || currentSlide == 3) {
                this.updateBackgroundColor('#3E8EF1');
            } else {
                this.updateBackgroundColor('#FFD0D5');
            }
        },
        updateBackgroundColor(color) {
            this.bgColor = color;
        },
        goToRegistration() {
            this.$router.push('/registration');
        }
    }
};
</script>

<style scoped>
.toolbar-slide-enter-active,
.toolbar-slide-leave-active {
    transition: transform 1s ease;
}

.toolbar-slide-enter {
    transform: translateY(-100%);
}

.toolbar-slide-leave-to {
    transform: translateY(0);
}
</style>