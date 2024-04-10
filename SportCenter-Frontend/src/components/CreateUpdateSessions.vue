<template>
  <div>
    <h2 style="color: #ffffff; font-size: 35px; margin-left: 140px; margin-top: 50px;">My sessions</h2>
    <h4 style="color: #ffffff; font-size: 20px; margin-bottom: 20px; margin-left: 140px;">
      Manage the sessions which you are supervising as an instructor.
    </h4>
<button id="save-btn" style="margin-left: 140px;" @click="openCreateSessionForm">Create session</button>

    <!-- Render UpdateSessionsForm only when selectedSession is not null -->
    <UpdateSessionsForm
      v-if="showUpdateForm && selectedSession"
      :session="selectedSession"
      @close="closeUpdateSessionForm"
      style="z-index: 9999; position: absolute; top: 50px; left: 50%; transform: translateX(-50%);"
    />

   <!-- Render CreateSessionForm only when showCreateForm is true -->
    <CreateSessionForm
      v-if="showCreateForm"
      @close="closeCreateSessionForm"
      style="z-index: 9999; position: absolute; top: 50px; left: 50%; transform: translateX(-50%);"
    />

    <!-- Properties and text fields -->
    <div style="color: #ffffff">
      <!-- Boxes -->
      <div v-for="session in sessions.sessions" :key="session.name">
        <div class="session-card">
          <div class="box">
            <div class="text-column">
              <div style="font-size: 25px; font-weight: bold">
                {{ session.course.name }}
              </div>
              <div style="font-size: 18px">{{ session.date }}, {{ session.startTime }} - {{ session.endTime }}</div>
              <div style="font-size: 18px">Capacity: {{ session.capacity }}, Floor {{ session.location.floor }} Room {{ session.location.room }} </div>
            </div>
            <div class="buttons">
              <b-icon
                icon="pencil-fill"
                class="pencil-icon"
                @click="openUpdateSessionForm(session)"
              ></b-icon>
              <b-icon
                icon="trash-fill"
                @click="deleteSession(session.id)"
                class="disapprove"
              ></b-icon>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import UpdateSessionsForm from "./UpdateSessionsForm.vue";
import CreateSessionForm from "./CreateSessionForm.vue";

export default {
  data() {
    return {
      profile: { name: "", email: "", password: "" },
      sessions: [],
      showUpdateForm: false,
      selectedSession: null,
      showCreateForm: false,
    };
  },
  methods: {
    getAllSessions() {
      const username = decodeURIComponent(this.$cookies.get('username'));
      const password = this.$cookies.get('password');

      fetch('http://127.0.0.1:8080/sessions', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password'))
        },
        credentials: 'include', 
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        this.sessions = data;
      })
      .catch(error => {
        console.error('Error fetching sessions:', error);
      });
    },
    openUpdateSessionForm(session) {
      this.selectedSession = session;
      console.log(session);
      this.showUpdateForm = true;
    },
    closeUpdateSessionForm() {
      this.showUpdateForm = false;
        this.getAllSessions();
    },
    openCreateSessionForm() {
      this.showCreateForm = true;
    },
    closeCreateSessionForm() {
      this.showCreateForm = false;
      this.getAllSessions();
    },

deleteSession(sessionID) {
  const username = decodeURIComponent(this.$cookies.get('username'));
  const password = this.$cookies.get('password');

  if (username && password) {
    fetch(`http://127.0.0.1:8080/sessions/${sessionID}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa(username + ':' + password)
      },
      credentials: 'include',
    })
      .then(response => {
        console.log('Session Delete Response Status:', response.status);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        // Remove the session from the sessions array on successful deletion
        this.sessions = this.sessions.filter(session => session.id !== sessionID);

        // Refresh sessions after deletion
        this.getAllSessions();
      })
      .catch(error => {
        console.error('Error deleting session:', error);
      });
  } else {
    console.error('User not authenticated');
  }
}
  },
  mounted() {
    this.getAllSessions();
  },
  components: {
    UpdateSessionsForm,
    CreateSessionForm
  },
};
</script>


<style scoped>
.box {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  color: #ffffff;
  box-sizing: border-box;
}

.session-card {
    border-radius: 10px;
    border: 1px solid #ffffff;
    background-color: transparent;
    padding: 30px;
    margin-top: 20px;
    margin-right: 150px;
    margin-left: 150px;
}

.text-column {
  width: 50%; /* Adjusted width to make two columns */
}

.buttons {
  display: flex;
  justify-content: center;
  align-items: center;
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

.disapprove {
    color: #EC5545; 
    font-size: 20px;
    cursor: pointer; 
    margin-right: 25px; 
}

.pencil-icon {
  color: #CDF563; 
  font-size: 20px;
  cursor: pointer; 
  margin-right: 25px; 
}

.pencil-icon:hover {
  color: #fff; 
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
</style>
