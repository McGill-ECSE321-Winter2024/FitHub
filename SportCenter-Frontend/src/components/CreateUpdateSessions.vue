<template>
  <div>
    <h2 style="color: #ffffff; font-size: 35px">My sessions</h2>
    <h4 style="color: #ffffff; font-size: 20px; margin-bottom: 20px">
      Manage the sessions which you are supervising as an instructor.
    </h4>
        <button id="save-btn">Create session</button>

    <!-- Properties and text fields -->
    <div style="color: #ffffff">
      <!-- Boxes -->
      <div v-for="session in paginatedSessions" :key="session.name">
        <div class="box">
          <div class="text-column">
            <div style="font-size: 25px; font-weight: bold">
              {{ session.name }}
            </div>
            <div style="font-size: 18px">{{ session.details }}</div>
          </div>
          <div class="button-column">
            <div class="button">Unregister</div>
          </div>
        </div>
      </div>
      <!-- Navigation arrows and page indicators -->
      <div class="navigation">
        <div class="arrows">
          <div class="arrow" style="margin-right: 10px" @click="navigate(-1)">
            &#10094;
          </div>
          <div class="page-indicator">{{ currentPage }} / {{ totalPages }}</div>
          <div class="arrow" @click="navigate(1)">&#10095;</div>
        </div>
      </div>
      <div class="button-column" style="width: 90%; margin-top: 30px">
        <a href="http://127.0.0.1:8087/#/courses" class="register-button">
          Register to more sessions
        </a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      profile: { name: "", email: "", password: "" },
      sessions: [
        { name: "Goat yoga", details: "04/06/24, 10:00-12:00", capacity: "40", attendance: "20", room: "3", floor: "5" },
        { name: "Jiu-jitsu 101", details: "04/07/24, 12:00-14:00" },
        { name: "Badminton", details: "04/08/24, 10:00-11:00" },
        // Add more sessions as needed
      ],
      itemsPerPage: 3,
      currentPage: 1,
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.sessions.length / this.itemsPerPage);
    },
    paginatedSessions() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.sessions.slice(start, end);
    },
  },
  methods: {
    navigate(direction) {
      if (
        (direction === -1 && this.currentPage === 1) ||
        (direction === 1 && this.currentPage === this.totalPages)
      ) {
        return;
      }
      this.currentPage += direction;
    },
  },
};
</script>

<style scoped>
.box {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 90%;
  height: 100px;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #ffffff;
  background-color: transparent;
  color: #ffffff;
  box-sizing: border-box;
  margin-top: 10px;
}

.text-column {
  flex: 1;
}

.button-column {
  flex-shrink: 0;
}

.button {
  background-color: transparent;
  color: var(--color-red);
  font-weight: bold;
  font-size: 20px;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

#save-btn {
  background-color: #CDF563;
  width: 160px; 
}

button {
  padding: 10px 10px;
  color: var(--color-black);
  border: none;
  border-radius: 30px;
  cursor: pointer;
  font-size: 18px;
  font-weight: 700;
  width: 100px; 
  height: 50px; 
  margin-bottom: 15px;
}


.button:hover {
  color: darkred;
}

.register-button {
  background-color: transparent;
  color: var(--color-green);
  font-size: 20px;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-button:hover {
  color: var(--color-green);
  font-weight: bold;
}

.navigation {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  width: 90%;
}

.page-indicator {
  font-size: 18px;
  margin-top: 5px;
}

.arrows {
  display: flex;
}

.arrow {
  font-size: 24px;
  cursor: pointer;
  margin: 0 10px;
  margin-right: 10px;
}
</style>
