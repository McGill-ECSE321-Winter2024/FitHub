<template>
    <div :style="{ backgroundColor: bgColor, transition: 'background-color 1.5s' }"
        class="whole-screen pl-5 pr-5 row align-items-center">
        <div class="col-lg-6 col-sm-12 slide-right">
                <h1 id=" title-heading">Welcome to <br>FitHub</h1>
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
  width:100%;
  overflow:hidden;
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

#title-heading {
    text-align: left;
    font-size: 64px;
    color: var(--color-black);
}
</style>