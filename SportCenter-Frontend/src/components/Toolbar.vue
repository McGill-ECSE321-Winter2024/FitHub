<template>
    <div id="app">
        <nav class="navbar navbar-expand-lg navbar-light"
            :style="{ transition: 'background-color 1.5s', zIndex: 1, backgroundColor: toolbarColor }">
            <router-link to="/" class="navbar-brand"">
              <img width=" 40" height="40" src="https://img.icons8.com/ios-filled/100/acrobatics.png"
                alt="acrobatics" />itHub
            </router-link>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
                aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <transition name="toolbar-slide">
                <div v-if="mounted" class="collapse navbar-collapse" id="navbarToggler">
                    <ul class="navbar-nav ml-auto">
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
            </transition>
        </nav>
        <router-view />
    </div>
</template>

<script>
import { EventBus } from '@/EventBus';
import router from '@/router/index';

export default {
    data() {
        return {
            mounted: false,
            toolbarColor: '' // Initialize toolbar color
        };
    },
    mounted() {
        setTimeout(() => {
            this.mounted = true;
        }, 100);

        if (router.currentRoute.path === '/') {
            this.handleChangeBackgroundColor();
        } else {
            this.updateToolbarColor(router.currentRoute.path);
        }

        // this.$watch(() => router.currentRoute.path, (to, from) => {
        //     if (to === '/') {
        //         this.handleChangeBackgroundColor();
        //     } else {
        //         this.updateToolbarColor(to);
        //     }
        // });

        EventBus.$on('beforeSlideOccurred', this.handleChangeBackgroundColor);
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
            switch (route) {
                case '/courses':
                    this.toolbarColor = '#121212';
                    break;
                case '/instructors':
                    this.toolbarColor = '#CDF567';
                    break;
                case '/login':
                    this.toolbarColor = '#FFBC4B';
                    break;
                case '/registration':
                    this.toolbarColor = '#CDF567';
                    break;
                default:
                    this.toolbarColor = '#FFD0D6';
            }
        },
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