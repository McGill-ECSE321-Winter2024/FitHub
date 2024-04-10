<template>
    <div class="p-5 d-flex" :style="{ backgroundColor: toolbarColor, transition: 'background-color 1.5s'}"">
        <router-link to=" /" class="navbar-brand mr-auto p-2">
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
import { EventBus } from '@/EventBus';
import router from '@/router/index';

export default {
    data() {
        return {
            mounted: false,
            toolbarColor: '#FFD0D5'
        };
    },
    mounted() {
        setTimeout(() => {
            this.mounted = true;
        }, 100);

        EventBus.$on('beforeSlideOccurred', this.handleChangeBackgroundColor);
        if (router.currentRoute.path != '/') {
            this.updateToolbarColor(router.currentRoute.path);
        }
    },
    beforeDestroy() {
        EventBus.$off('beforeSlideOccurred', this.handleChangeBackgroundColor);
    },
    methods: {
        handleChangeBackgroundColor(payload) {
            const { currentSlide } = payload;
            if (currentSlide === 0 || currentSlide === 1) {
                this.toolbarColor = '#FFE818';
            } else if (currentSlide === 2 || currentSlide == 3) {
                this.toolbarColor = '#3E8EF1';
            } else {
                this.toolbarColor = '#FFD0D5';
            }
        },
        updateToolbarColor(route) {
            switch (true) {
                case route.startsWith('/courses'):
                    this.toolbarColor = '#CDF567';
                    break;
                case route.startsWith('/instructors'):
                    this.toolbarColor = '#6900BA';
                    break;
                case route === '/login':
                    this.toolbarColor = '#FFBC4B';
                    break;
                case route === '/registration':
                    this.toolbarColor = '#3E8EF1';
                    break;
                case route === '/settings':
                    this.toolbarColor = '#CDF567';
                    break;
                case route.startsWith('/sessions/courses/'):
                    this.toolbarColor = '#3E8EF1';
                    break;
                default:
                    this.toolbarColor = '#FFD0D6';
            }
        },
    }
};
</script>

<style scoped>
.nav-item {
    font-weight: 500;
    font-size: 24px;
}

.navbar-brand {
    color: var(--color-black);
    font-size: 75px;
}

</style>