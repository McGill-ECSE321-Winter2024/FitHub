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
                        <li v-if="!isLoggedIn" class="nav-item">
                            <router-link to="/login" class="nav-link">Already a Member?</router-link>
                        </li>
                        <li v-if="!isLoggedIn" class="nav-item">
                            <router-link to="/registration" class="nav-link">Register Here!</router-link>
                        </li>
                        <li v-if="isLoggedIn" class="nav-item">
                            <div v-if="isLoggedIn" class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    My Account
                                </a>
                                <div class="dropdown-menu" aria-labelledby="navbarDropdown" :style="{ transition: 'background-color 1.5s', zIndex: 1, backgroundColor: toolbarColor }">
                                    <router-link class="dropdown-item" to="/settings">Settings <b-icon icon="gear"></b-icon></router-link>
                                    <a class="dropdown-item" @click.prevent="signOut" style="color: var(--color-red)">Sign Out <b-icon icon="box-arrow-right" style="color: var(--color-red)"></b-icon></a>
                                </div>
                            </div>
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
            toolbarColor: '#FFD0D5',
            isLoggedIn: false,
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

        this.isLoggedIn = this.checkAuthenticationStatus();

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
                    this.toolbarColor = '#CDF567';
                    break;
                case '/instructors':
                    this.toolbarColor = '#FFE818';
                    break;
                case '/login':
                    this.toolbarColor = '#FFBC4B';
                    break;
                case '/registration':
                    this.toolbarColor = '#CDF567';
                    break;
                case '/settings':
                    this.toolbarColor = '#CDF567';
                    break;
                case '/sessions':
                    this.toolbarColor = '#FFFFFF';
                    break;
                default:
                    this.toolbarColor = '#FFD0D6';
            }
        },
        checkAuthenticationStatus() {
            // Check if user is authenticated based on your authentication logic
            const username = this.$cookies.get('username');
            const password = this.$cookies.get('password');

            console.log(`Username: ${username}`)
            console.log(`Password: ${password}`)
            return username && password;
        },
        signOut() {
            this.isLoggedIn = false;
            this.$cookies.keys().forEach(cookieName => {
                this.$cookies.remove(cookieName);
            });
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

.dropdown-item {
    font-weight: 600;
    align-items: center;
}

.dropdown-item:hover {
    background-color: var(--color-black);
    color: var(--color-white);
}
</style>