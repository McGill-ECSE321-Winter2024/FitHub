<template>
    <div class="whole-screen p-0 m-0" :style="{ backgroundColor: bgColor, transition: 'background-color 1.5s' }">
        <div>
            <ul class="squares">
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
        <div class="pl-5 pr-5 row align-items-center">
            <div class="col-lg-6 col-sm-12 slide-right">
                <h1 id="title-heading">Welcome to <br>FitHub</h1>
            </div>
            <div class="col-lg-6 col-sm-12">
                <hooper :infiniteScroll="true" style="height: 50vh" :autoPlay="true" :playSpeed="2500"
                    @beforeSlide="handleBeforeSlide">
                    <slide>
                        <img class="w-100 h-100"
                            src="https://images.adsttc.com/media/images/56d0/5850/e58e/ce94/0000/00bc/large_jpg/01_Sports_Center_Neumatt_Exterior.jpg?1456494662" />
                    </slide>
                    <slide style="height: 50vh;">
                        <img class="w-100 h-100"
                            src="https://images.adsttc.com/media/images/56d0/5843/e58e/ceb1/cf00/0135/newsletter/06_Sports_Center_Neumatt_Exterior.jpg?1456494647" />
                    </slide>
                    <slide>
                        <img class="w-100 h-100"
                            src="https://images.adsttc.com/media/images/56d0/5d91/e58e/ce94/0000/00e0/newsletter/041_Sports_Center_Neumatt_Hall.jpg?1456496006" />
                    </slide>
                    <slide>
                        <img class="w-100 h-100"
                            src="https://images.adsttc.com/media/images/56d0/5d62/e58e/ceb1/cf00/015c/medium_jpg/039_Sports_Center_Neumatt_Hall.jpg?1456495958" />
                    </slide>
                    <slide>
                        <img class="w-100 h-100"
                            src="https://image.architonic.com/imgArc/project-1/4/5201637/evolution-design-neumatt-sports-center-architonic036-sports-center-neumatt-hall-18.jpg" />
                    </slide>
                    <slide>
                        <img class="w-100 h-100"
                            src="https://images.adsttc.com/media/images/56d0/5e5a/e58e/ceb1/cf00/016a/newsletter/051_Sports_Center_Neumatt_Night_Shot.jpg?1456496206" />
                    </slide>
                </hooper>
            </div>
        </div>
    </div>
</template>

<script>
import { EventBus } from '@/EventBus';

export default {
    data() {
        return {
            bgColor: '#FFD0D5'
        };
    },
    mounted() {
        // Add a small delay to allow the element to be rendered before applying the animation class
        setTimeout(() => {
            this.$el.classList.add('slide-in');
        }, 100);
    },
    methods: {
        handleBeforeSlide(payload) {
            EventBus.$emit('beforeSlideOccurred', payload);

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
.whole-screen {
    background-color: var(--color-pink);
    height: 100vh;
}

div.slide-right {
    width: 100%;
    overflow: hidden;
}

div.slide-right h1 {
    animation: 2.5s slide-right;
}

@keyframes slide-right {
    from {
        margin-left: -100%;
        width: 300%;
    }

    to {
        margin-left: 0%;
        width: 100%;
    }
}

.squares {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
}

.squares li {
    position: absolute;
    display: block;
    list-style: none;
    width: 20px;
    height: 20px;
    background: rgba(255, 255, 255, 0.5);
    animation: animate-square 25s linear infinite;
    bottom: -150px;

}

.squares li:nth-child(1) {
    left: 25%;
    width: 80px;
    height: 80px;
    animation-delay: 0s;
}


.squares li:nth-child(2) {
    left: 10%;
    width: 20px;
    height: 20px;
    animation-delay: 2s;
    animation-duration: 12s;
}

.squares li:nth-child(3) {
    left: 70%;
    width: 20px;
    height: 20px;
    animation-delay: 4s;
}

.squares li:nth-child(4) {
    left: 40%;
    width: 60px;
    height: 60px;
    animation-delay: 0s;
    animation-duration: 18s;
}

.squares li:nth-child(5) {
    left: 65%;
    width: 20px;
    height: 20px;
    animation-delay: 0s;
}

.squares li:nth-child(6) {
    left: 75%;
    width: 110px;
    height: 110px;
    animation-delay: 3s;
}

.squares li:nth-child(7) {
    left: 35%;
    width: 150px;
    height: 150px;
    animation-delay: 7s;
}

.squares li:nth-child(8) {
    left: 50%;
    width: 25px;
    height: 25px;
    animation-delay: 15s;
    animation-duration: 45s;
}

.squares li:nth-child(9) {
    left: 20%;
    width: 15px;
    height: 15px;
    animation-delay: 2s;
    animation-duration: 35s;
}

.squares li:nth-child(10) {
    left: 85%;
    width: 150px;
    height: 150px;
    animation-delay: 0s;
    animation-duration: 11s;
}

@keyframes animate-square {

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

#title-heading {
    text-align: left;
    font-size: 64px;
    color: var(--color-black);
}
</style>