<template>
    <div class="p-5 d-flex" :style="{ backgroundColor: toolbarColor, transition: 'background-color 1.5s' }"">
        <router-link to=" /" class="navbar-brand mr-auto p-2">
        <img width="100" height="100" src="https://img.icons8.com/ios-filled/100/acrobatics.png"
            alt="acrobatics" />itHub
        </router-link>
        <nav class="navbar p-2">
            <div>
                <ul class="navbar-nav nav flex-column">
                    <li class="nav-item">
                        {{ sportCenter.email }}
                    </li>
                    <li class="nav-item">
                        {{ sportCenter.phoneNumber }}
                    </li>
                    <li class="nav-item">
                        {{ sportCenter.address }}
                    </li>
                    <li class="nav-item">
                        {{ convertTo12HourFormat(sportCenter.openingTime) }} - {{ convertTo12HourFormat(sportCenter.closingTime) }}
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
            toolbarColor: '#FFD0D5',
            sportCenter: {
                name: "",
                email: "",
                phoneNumber: "",
                address: "",
                openingTime: "",
                closingTime: "",
                errorMessage: 'Empty fields for name, address, email or phone number are not valid',
                showErrorMessage: false,
                showSuccessfulMessage: false,
            },
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
        this.getSportCenterInfo();
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
        getSportCenterInfo() {
            const url = 'http://127.0.0.1:8080/public/sport-center';
            fetch(url, {
                method: 'GET',
                credentiZals: 'include',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: 'Basic ' + btoa(this.$cookies.get('username') + ':' + this.$cookies.get('password')),
                },
            }).then((sportCenterResponse) => {
                sportCenterResponse.json().then(sportCenter => {
                    console.log(sportCenter);
                    this.sportCenter.name = sportCenter.name;
                    this.sportCenter.email = sportCenter.email;
                    this.sportCenter.address = sportCenter.address;
                    this.sportCenter.phoneNumber = sportCenter.phoneNumber;
                    this.sportCenter.openingTime = sportCenter.openingTime;
                    this.sportCenter.closingTime = sportCenter.closingTime;
                }).catch(error => {
                    console.error('Error parsing JSON:', error);
                });
            }).catch(error => {
                console.error('Error fetching accounts:', error);
            });
        },
        convertTo12HourFormat(timeString) {
            const [hours, minutes] = timeString.split(':');
            let hour = parseInt(hours, 10);
            const period = hour >= 12 ? 'PM' : 'AM';
            hour = hour % 12 || 12;
            const paddedMinutes = minutes.padStart(2, '0');
            const twelveHourTime = `${hour}:${paddedMinutes} ${period}`;
            return twelveHourTime;
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