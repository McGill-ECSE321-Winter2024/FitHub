<template>
  <div>
    <h2>Manage instructors</h2>
    <div class="container">
      <div class="row m-0">
        <div class="col" v-for="instructor in instructors" :key="instructor.id">
          <div class="col instructor p-2 m-2">
            <div class="image-container row align-items-center justify-elements-center m-0">
              <img
                class="circular-image m-auto"
                :src="instructor.imageURL"
                @error="$event.target.src = instructor.defaultImage"
                :style="{ width: '80px', height: 'auto' }"
              />
            </div>
            <div class="inst-name">{{ instructor.name }}</div>
            <div class="inst-info">{{ instructor.pronouns }}</div>
            <div class="inst-info">
              {{ instructor.email }}
            </div>   
            <div class="row justify-content-center">
              <button
                class="p-2 px-3 rounded justify-content-end btn btn-outline px-4"
                @click="showEditPopup(instructor)"
              >
                Edit
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="popup-edit" v-if="showEditConfirmation">
        <div class="popup-content">
          <div class="form-box">
            <h3>Edit Instructor</h3>
            <form>
              <div class="image-container">
                <img
                  class="row circular-image mx-auto"
                  :src="instructor.imageURL"
                  @error="$event.target.src = instructor.defaultImage"
                  :style="{ width: '80px', height: 'auto' }"
                />
              </div>
              <div class="row m-0 pb-2">
                <div>Image URL</div>
              </div>
              <div class="row m-0 pb-2">
                <input
                  type="text"
                  v-model="instructor.imageURL"
                  class="text-field"
                />
              </div>
              <div class="row m-0 pb-2">
                <div class="col p-0">Name</div>
                <div class="col p-0 ml-3">
                  Pronouns
                </div>
              </div>
              <div class="row m-0 pb-2">
                <input
                  type="text"
                  v-model="instructor.name"
                  class="col text-field"
                />
                <input
                  type="text"
                  v-model="instructor.pronouns"
                  class="col m-0 ml-3 text-field"
                />
              </div>
              <div class="row m-0 pb-2">
                <div class="col p-0">Email</div>
                <div class="col p-0 ml-3">
                  Password
                </div>
              </div>
              <div class="row m-0 pb-2">
                <input
                  type="email"
                  v-model="instructor.email"
                  class="col m-0 text-field"
                />
                <input
                  type="password"
                  v-model="instructor.password"
                  class="col m-0 ml-3 text-field"
                />
              </div>
              <div class="row m-0 popup-btn">
                <button @click="saveEdit" class="save-btn">Save</button>
                <button @click="cancelEdit" class="cancel-edit-btn">
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      instructors: [],
      showEditConfirmation: false,
      editedInstructor: null, // Hold the instructor being edited
      instructor: {
        imageURL: "",
        defaultImage: require("@/assets/pfp.png"),
        name: "",
        pronouns: "",
        email: "",
        password: "",
      },
      instructors: [],
    };
  },
  mounted() {
      // Fetch accounts data when the component is created
      this.fetchInstructors();
  },
  methods: {
      fetchInstructors() {
        fetch('http://localhost:8080/public/instructors', {
            method: 'GET',
            credentials: 'include'
        }).then((accountsResponse) => {
            if (accountsResponse.status === 204) {
                console.log("No instructors in the database");
            }
            else {
                accountsResponse.json().then(accounts => {
                    console.log(accounts.accounts);
                    this.instructors = accounts.accounts;

                    console.log(this.instructorCourses);
                }).catch(error => {
                    console.error('Error parsing JSON:', error);
                });
            }
        }).catch(error => {
            console.error('Error fetching accounts:', error);
        });
    },
    showEditPopup(instructor) {
      this.editedInstructor = { ...instructor }; // Copy account data
      this.showEditConfirmation = true; // Show edit popup
    },
    saveEdit() {
      this.showEditConfirmation = false;
    },
    cancelEdit() {
      this.editedInstructor = null; // Reset edited account
      this.showEditConfirmation = false; // Hide edit popup
    },
  },
};
</script>

<style scoped>

h2 {
  color: #ffffff; 
  font-size: 2rem;
  padding: 30px;
  padding-left: 0px;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: left;
}

.row {
  display: flex;
}

.col {
  flex: 1;
}

.btn-outline {
  margin-top: 10px;
  background-color: #cdf567 !important;
  border-color: #cdf567 !important;
  color: var(--color-black) !important;
}

.btn-outline:hover {
  background-color: #a0ca35 !important;
  border-color: #a0ca35 !important;
  color: var(--color-black) !important;
}

.circular-image {
  border-radius: 50%; /* Set border-radius to 50% for circular shape */
}

.image-container {
  text-align: center; /* Center the image */
  height: 110px;
  overflow-y: hidden;
}

.instructor {
  border: 1px solid #ccc;
  color: #ffffff;
  border-radius: 8px;
  justify-content: center;
  align-items: center;
  width: 240px;
  font-weight: normal;
}

.inst-name {
  justify-content: center;
  text-align: center;
  font-size: 1.5rem;
  margin-top: 10px;
  font-weight: bold;
}

.inst-info {
  text-align: center;
  font-size: 1rem;
}

.save-button {
  margin-top: 10px;
  background-color: var(--color-green);
  color: var(--color-black);
  font-weight: bold;
  border: none;
  border-radius: 20px;
  padding: 5px 10px;
  cursor: pointer;
}

.popup-btn {
  justify-content: center;
}

.setting-instructor-field {
  width: 90%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #444; /* Set initial border color to gray */
  background-color: transparent;
  color: #ffffff;
  height: 35px;
  box-sizing: border-box;
  margin-top: 2px;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out,
    border-width 0.1s ease-in-out; /* Add transition effect */
  font-weight: normal; /* Set default font weight */
  border-width: 1px; /* Set initial border width */
}

.setting-instructor-field:focus,
.setting-instructor-field:hover {
  outline: none;
  border-color: #fff; /* Change border color to white on hover or focus */
}

.setting-instructor-field:focus {
  border-width: 2px; /* Increase border width on hover or focus */
}

.setting-instructor-field[type="time"]::-webkit-datetime-edit-second-field {
  display: none;
}

.popup-content button {
  padding: 10px 20px;
  margin: 0 10px;
  border: none;
  border-radius: 45px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 400px;
}

.popup-content {
  width: 90%; /* Adjust the width as needed */
  height: 95%; /* Adjust the width as needed */
  margin: auto;
  margin-top: 20px;
  overflow-y: scroll;
}

.popup-edit {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: var(--color-black);
  border: 2px solid #ccc;
  border-radius: 40px;
  z-index: 9999; /* Ensure it appears on top of other elements */
  max-width: 600px;
  width: 80%; /* Adjust the width as needed */
  height: 80%; /* Adjust the width as needed */
}
.form-box {
  padding: 20px;
  color: #ffffff;
}

.save-btn {
  padding: 10px 10px;
  color: var(--color-black);
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 18px;
  font-weight: 500;
  width: 100px;
  height: 50px;
  margin-bottom: 15px;
  background-color: #cdf563;
}

.save-btn:hover {
  background-color: #a0ca35 !important;
  border-color: #a0ca35 !important;
  color: var(--color-black) !important;
}

.cancel-edit-btn {
  padding: 10px 10px;
  color: var(--color-black);
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 18px;
  font-weight: 500;
  width: 100px;
  height: 50px;
  margin-bottom: 15px;
  margin-left: 150px;
  background-color: #ec5545;
}

.cancel-edit-btn:hover {
  background-color: #cd4c3e !important;
  border-color: #cd4c3e !important;
  color: var(--color-black) !important;
}

.text-field {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 2px solid #444; /* Set initial border color to gray */
  background-color: var(--color-black);
  color: #ffffff;
  height: 50px;
  box-sizing: border-box;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out,
  border-width 0.1s ease-in-out; /* Add transition effect */
  font-weight: normal; /* Set default font weight */
  border-width: 2px; /* Set initial border width */
}

.text-field:focus,
.text-field:hover {
  outline: none;
  border-color: #fff; /* Change border color to white on hover or focus */
}

.text-field:focus {
  border-width: 4px; /* Increase border width on hover or focus */
}

.text-field[type="time"]::-webkit-datetime-edit-second-field {
  display: none;
}
</style>
