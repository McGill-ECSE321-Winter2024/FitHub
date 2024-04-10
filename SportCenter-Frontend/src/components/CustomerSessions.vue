<template>
  <div>
    <h2 style="color: #ffffff; font-size: 35px">My sessions</h2>
    <h4 style="color: #ffffff; font-size: 20px; margin-bottom: 50px">
      Manage the sessions to which you are registered.
    </h4>
    <!-- Properties and text fields -->
    <div style="color: #ffffff">
      <!-- Boxes -->
      <div v-for="session in this.sessions" :key="session.id">
        <div class="box">
          <div class="text-column">
            <div style="font-size: 25px; font-weight: bold; margin-top:15px;">
              {{ capitalize(session.course.name) }}
            </div>
            <div style="font-size: 18px; margin-bottom:15px;">{{ session.date }}, {{ convertTo12HourFormat(session.startTime) }} - {{
        convertTo12HourFormat(session.endTime) }} <br> Floor {{ session.location.floor}} Room {{ session.location.room}}</div>
          </div>
            <div style="font-size: 18px"> </div>
          <div class="button-column">
            <div class="button" @click="cancelRegistration(session.id)">Unregister</div>
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
      sessions: [],
      itemsPerPage: 3,
      currentPage: 1,
    };
  },
  mounted() {
    this.fetchSessions();
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
    fetchSessions() {
      const customerId = this.$cookies.get('id');
      fetch(`http://127.0.0.1:8080/customers/${customerId}/sessions/`, {
        method: 'GET',
        credentials: 'include',
        headers: {
          "Content-Type": "application/json",
          Authorization: 'Basic ' + btoa(this.$cookies.get('username') + ':' + this.$cookies.get('password')),
        }
      }).then((registrationResponse) => {
        if (registrationResponse.status === 204) {
          console.log("No registrations for this customer in database");
        }
        else {
          registrationResponse.json().then(sessions => {
            this.sessions = sessions.sessions;

            console.log(this.sessions);
          }).catch(error => {
            console.error('Error parsing JSON:', error);
          });
        }
      }).catch(error => {
        console.error('Error fetching sessions:', error);
      });
    },
    cancelRegistration(sessionId) {
      const username = decodeURIComponent(this.$cookies.get('username'));
      const password = this.$cookies.get('password');

      console.log('Username:', username);
      console.log('Password:', password);

      if (username && password) {
        console.log('Session ID: ', sessionId);
        console.log('Customer ID: ', this.$cookies.get('id'));
        const customerId = this.$cookies.get('id');

        fetch(`http://127.0.0.1:8080/registrations/${customerId}/${sessionId}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password'))
          },
          credentials: 'include',
        })
          .then(response => {
            console.log('Response Status:', response.status);
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
          })
          .then(data => {
            console.log('Registration deleted:');
            this.sessions = this.sessions.filter(session => session.id !== sessionId);
          })
      } else {
        console.error('User not authenticated');
      }
    },
    navigate(direction) {
      if (
        (direction === -1 && this.currentPage === 1) ||
        (direction === 1 && this.currentPage === this.totalPages)
      ) {
        return;
      }
      this.currentPage += direction;
    },
    capitalize(str) {
      return str.replace(/\b\w/g, (char) => char.toUpperCase());
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
