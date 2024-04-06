<template>
    <div class="p-5 d-flex" :style="{ backgroundColor: bgColor, transition: 'background-color 1.5s'}"">
        <div>
            <ul class="blobs">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
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
        }
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

.blobs {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
}

.blobs li {
    position: absolute;
    display: block;
    list-style: none;
    width: 20px;
    height: 20px;
    background: rgba(255, 255, 255, 0.5);
    animation: animate-blob 25s linear infinite;
    bottom: -150px;

}

.blobs li:nth-child(1) {
    left: 25%;
    width: 80px;
    height: 80px;
    animation-delay: 0s;
}


.blobs li:nth-child(2) {
    left: 10%;
    width: 20px;
    height: 20px;
    animation-delay: 2s;
    animation-duration: 12s;
}

.blobs li:nth-child(3) {
    left: 70%;
    width: 20px;
    height: 20px;
    animation-delay: 4s;
}

.blobs li:nth-child(4) {
    left: 40%;
    width: 60px;
    height: 60px;
    animation-delay: 0s;
    animation-duration: 18s;
}

.blobs li:nth-child(5) {
    left: 65%;
    width: 20px;
    height: 20px;
    animation-delay: 0s;
}

.blobs li:nth-child(6) {
    left: 75%;
    width: 110px;
    height: 110px;
    animation-delay: 3s;
}

.blobs li:nth-child(7) {
    left: 35%;
    width: 150px;
    height: 150px;
    animation-delay: 7s;
}

.blobs li:nth-child(8) {
    left: 50%;
    width: 25px;
    height: 25px;
    animation-delay: 15s;
    animation-duration: 45s;
}

.blobs li:nth-child(9) {
    left: 20%;
    width: 15px;
    height: 15px;
    animation-delay: 2s;
    animation-duration: 35s;
}

.blobs li:nth-child(10) {
    left: 85%;
    width: 150px;
    height: 150px;
    animation-delay: 0s;
    animation-duration: 11s;
}

@keyframes animate-blob {

    0% {
        transform: translateY(0) rotate(0deg);
        opacity: 1;
        border-radius: 20%;
    }

    20% {
        transform: translateY(-250px) rotate(144deg);
        opacity: 0.8;
        border-radius: 41% 59% 41% 59% / 53% 51% 49% 47%;
    }

    40% {
        transform: translateY(-500px) rotate(288deg);
        opacity: 0.6;
        border-radius: 43% 57% 41% 59% / 53% 52% 48% 47%;
    }

    80% {
        transform: translateY(-750px) rotate(576deg);
        opacity: 0.2;
        border-radius: 54% 46% 60% 40% / 43% 55% 45% 57%;
    }

    100% {
        transform: translateY(-1000px) rotate(720deg);
        opacity: 0;
        border-radius: 10px 20px 30px 40px / 40px 30px 20px 10px;
    }

}
</style>