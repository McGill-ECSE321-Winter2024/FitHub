<template>
  <div> 
    <transition name="fade">
      <div v-if="showSplash" class="splash" @click="hideSplash">
        <h1 class="splash-title">
          <span class="fit">Fit</span>
          <span class="hub">Hub.</span>
        </h1>
      </div>
    </transition>

    <div v-if="!showSplash">
      <Toolbar />
      <Title />
      <CoursesMini />
      <WhatOurCustomersSay />
      <RegistrationLink />
      <Footer />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showSplash: true
    };
  },
  methods: {
    hideSplash() {
      setTimeout(() => {
        this.showSplash = false;
      }, 300); // Adjust the delay to match your animation duration
    }
  }
};
</script>

<style scoped>
.splash {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: var(--color-pink);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999; /* ensure splash screen is above other content */
}

.splash-title {
  color: var(--color-black);
  font-size: 500%;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

.fit,
.hub {
  display: inline-block;
  overflow: hidden;
}

.fit {
  animation: roll-up 3s ease-out forwards;
}

.hub {
  animation: roll-down 3s ease-out forwards;

}

@keyframes roll-up {
  from {
    transform: translateY(1000px);
  }
  to {
    transform: translateY(0);
  }
}

@keyframes roll-down {
  from {
    transform: translateY(-1000px);
  }
  to {
    transform: translateY(0);
  }
}
</style>
