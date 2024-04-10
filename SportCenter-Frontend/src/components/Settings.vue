<template>
  <div class="content-container"
  >
    <div style="background-color: var(--color-black)">
      <Toolbar />
    </div>
    <!-- Container for sidebar and main content -->
    <div class="row p-0 m-0">
      <!-- Sidebar container with gray box -->
      <div
        class="sidebar-container col-auto p-0 m-0"
      >
        <div
          class="sidebar p-0 m-3"
        >
          <h2>
            Settings
          </h2>
          <!-- Adjust font size here -->
          <ul>
            <!-- Edit Profile Button -->
            <li
              @click="toggleMenu('EditProfile')"
              :class="{
                'menu-item-selected': currentTab === 'EditProfile',
              }"
              class="menu-item"
            >
              Edit profile
            </li>
            <!-- Edit Sport Center Button -->
            <li
              @click="toggleMenu('EditSportCenter')"
              :class="{
                'menu-item-selected': currentTab === 'EditSportCenter',
              }"
              class="menu-item"
            >
              Edit sport center
            </li>
            <!-- Manage Instructors Button -->
            <li
              @click="toggleMenu('ManageInstructors')"
              :class="{
                'menu-item-selected': currentTab === 'ManageInstructors',
              }"
              class="menu-item"
            >
              Manage instructors
            </li>
            <!-- Manage Courses Button -->
            <li
              @click="toggleMenu('ManageCourses')"
              :class="{
                'menu-item-selected': currentTab === 'ManageCourses',
              }"
              class="menu-item"
            >
              Approve/Disapprove Courses
            </li>
            <!-- Manage Locations Button -->
            <li
              @click="toggleMenu('ManageLocations')"
              :class="{
                'menu-item-selected': currentTab === 'ManageLocations',
              }"
              class="menu-item"
            >
              Manage locations
            </li>
            <!-- My Sessions Button -->
            <li
              @click="toggleMenu('MySessions')"
              :class="{
                'menu-item-selected': currentTab === 'MySessions',
              }"
              class="menu-item"
            >
              My sessions
            </li>
            <!-- Propose Courses Button -->
            <li
              @click="toggleMenu('ProposeCourse')"
              :class="{
                'menu-item-selected': currentTab === 'ProposeCourse',
              }"
              class="menu-item"
            >
              Propose courses
            </li>
            <!-- Billing Account Button -->
            <li
              @click="toggleMenu('BillingAccount')"
              :class="{
                'menu-item-selected': currentTab === 'BillingAccount',
              }"
              class="menu-item"
            >
              Billing account
            </li>
            <!-- Add more menu items as needed -->
          </ul>
        </div>
      </div>

      <!-- Main content -->
      <div
        class="main-content col m-0"
      >
        <!-- Edit Profile -->
        <div v-if="currentTab === 'EditProfile'">
          <h2>
            Edit profile
          </h2>

          <!-- Properties and text fields -->
          <div class="container content">

            <img class="row mt-3 mx-auto" :src="profile.imageURL"
                                      @error="$event.target.src = profile.defaultImage" 
                                      :style="{ 'width': '80px', 'height': 'auto'}" />
            <div class="row  pb-2">
              <div style="font-weight: bold">Image URL</div>
            </div>
            <div class="row  pb-2">
              <input type="text" v-model="profile.imageURL" class="text-field" />
            </div>
            <div class="row  pb-2">
              <div class="col p-0 " style="font-weight: bold">Name</div>
              <div class="col p-0 ml-3" style="font-weight: bold">Pronouns</div>
            </div>
            <div class="row  pb-2">
              <input type="text" v-model="profile.name" class="col text-field" />
              <input type="text" v-model="profile.pronouns" class="col m-0 ml-3 text-field" />
            </div>
            <div class="row  pb-2">
              <div class="col p-0 " style="font-weight: bold">Email</div>
              <div class="col p-0 ml-3" style="font-weight: bold">Password</div>
            </div>
            <div class="row  pb-2">
              <input type="email" v-model="profile.email" class="col text-field" />
              <input type="password" v-model="profile.password" class="col m-0 ml-3 text-field" />
            </div>
            <div class="row justify-content-center">
              <p class="error"
                :class="{ 
                  'hidden': !profile.showErrorMessage && !profile.showSuccessfulMessage, 
                  'red-text': profile.showErrorMessage && !profile.showSuccessfulMessage, 
                  'green-text': profile.showSuccessfulMessage && !profile.showErrorMessage 
                }">
                {{ profile.errorMessage }}
              </p>
            </div>
            <div class="row justify-content-center">
              <button class="p-2 px-3 rounded justify-content-end btn btn-outline px-4" @click="saveProfileInfo">Save</button>
            </div>
          </div>
        </div>

        <!-- Manage Courses Settings -->
        <div v-else-if="currentTab === 'ManageCourses'">
          <OwnerCourses />
        </div>

        <!-- Manage Instructors Settings -->
        <div v-else-if="currentTab === 'ManageInstructors'">
          <ManageInstructors />
        </div>

        <!-- Manage Locations Settings -->
        <div v-else-if="currentTab === 'ManageLocations'">
          <ManageLocations />
        </div>

        <!-- My Sessions Settings -->
        <div v-else-if="currentTab === 'MySessions'">
          <CustomerSessions />
        </div>

        <!-- Billing Account Settings -->
        <div v-else-if="currentTab === 'BillingAccount'">
          <BillingAccountOverview />
        </div>

        <div v-else-if="currentTab === 'ProposeCourse'">
          <ProposeCourse />
        </div>

        <!-- Sport Center Settings -->
        <div v-else-if="currentTab === 'EditSportCenter'">
          <h2>
            Edit sport center
          </h2>
          <!-- Properties and text fields -->
          <div class="container content">
            <div class="row  pb-2">
              <div class="col p-0 " style="font-weight: bold">Name</div>
              <div class="col p-0 ml-3" style="font-weight: bold">Email</div>
            </div>
            <div class="row  pb-2">
              <input type="name" v-model="sportCenter.name" class="col m-0 text-field" />
              <input type="name" v-model="sportCenter.email" class="col m-0 ml-3 text-field" />
            </div>
            <div class="row  pb-2">
              <div class="col p-0 " style="font-weight: bold">Phone number</div>
              <div class="col p-0 ml-3" style="font-weight: bold">Address</div>
            </div>
            <div class="row  pb-2">
              <input type="name" v-model="sportCenter.phoneNumber"  class="col m-0 text-field"/>
              <input type="name" v-model="sportCenter.address"  class="col m-0 ml-3 text-field" />
            </div>
            <div class="row  pb-2">
              <div class="col p-0 " style="font-weight: bold">Opening Time</div>
              <div class="col p-0 ml-3" style="font-weight: bold">Closing Time</div>
            </div>
            <div class="row  pb-2">
              <input class="timepicker text-field col p-0" type="time" v-model="sportCenter.openingTime"></input>
              <input class="timepicker text-field col p-0 ml-3" type="time" v-model="sportCenter.closingTime"></input>
            </div>
            <div class="row justify-content-center">
              <p class="error"
                :class="{ 
                  'hidden': !sportCenter.showErrorMessage && !sportCenter.showSuccessfulMessage, 
                  'red-text': sportCenter.showErrorMessage && !sportCenter.showSuccessfulMessage, 
                  'green-text': sportCenter.showSuccessfulMessage && !sportCenter.showErrorMessage 
                }">
                {{ sportCenter.errorMessage }}
              </p>
            </div>
            <div class="row justify-content-center">
              <button class="p-2 px-3 rounded justify-content-end btn btn-outline px-4" @click="updateSportCenterInfo">Save</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// Import OwnerProfileSettings and OwnerSportCenterSettings components
import OwnerCourses from "./OwnerCourses.vue";
import BillingAccountOverview from "./BillingAccountOverview.vue";
import CustomerSessions from "./CustomerSessions.vue";
import ProposeCourse from "./ProposeCourse.vue";
import ManageInstructors from "./ManageInstructors.vue";
import ManageLocations from "./Location.vue";

export default {
  name: "ProfileSettings",
  components: {
    OwnerCourses,
    BillingAccountOverview,
    CustomerSessions,
    ProposeCourse,
    ManageInstructors,
    ManageLocations
    // Add more components as needed
  },
  mounted() {
    // Call your function to retrieve JSON data and autofill the input field
    this.getCurrentAccountInfo();
    this.getSportCenterInfo();
  },
  data() {
    return {
      currentTab: "EditProfile", // Default tab
      profile: {
        imageURL: "",
        name: "",
        email: "",
        pronouns: "",
        password: "",
        defaultImage: require('@/assets/pfp.png'),
        errorMessage: 'Empty fields for email, password or name are not valid',
        showErrorMessage: false,
        showSuccessfulMessage: false,
      },
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
  methods: {
    toggleMenu(tabName) {
      // Hide error messages when switching tabs
      this.profile.showErrorMessage = false;
      this.profile.showSuccessfulMessage = false;
      this.sportCenter.showErrorMessage = false;
      this.sportCenter.showSuccessfulMessage = false;

      this.currentTab = tabName;
    },
    getCurrentAccountInfo() {
      const url = 'http://127.0.0.1:8080/accounts/' + this.$cookies.get("id");
      fetch(url, {
                method: 'GET',
                credentials: 'include',
                headers: {
                  "Content-Type": "application/json",
                  Authorization: 'Basic ' + btoa(this.$cookies.get('username') + ':' + this.$cookies.get('password')),
                },
            }).then((accountsResponse) => {
                if (accountsResponse.status === 204) {
                    console.log("No account with Id was found");
                }
                else {
                    accountsResponse.json().then(accounts => {
                        console.log(accounts);
                        this.profile.imageURL = accounts.imageURL;
                        this.profile.name = accounts.name;
                        this.profile.email = accounts.email;
                        this.profile.pronouns = accounts.pronouns;
                    }).catch(error => {
                        console.error('Error parsing JSON:', error);
                    });
                }
            }).catch(error => {
                console.error('Error fetching accounts:', error);
            });
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
    saveProfileInfo() {

      // Create a JSON object with the data to be sent in the POST request
      const requestBody = {
          email: this.profile.email,
          password: this.profile.password,
          name: this.profile.name,
          imageURL: this.profile.imageURL,
          pronouns: this.profile.pronouns
      };

      console.log(
        "username: ",
        decodeURIComponent(this.$cookies.get("username"))
      );
      console.log("password: ", this.$cookies.get("password"));
      console.log("role: ", this.$cookies.get("role"));
      console.log("id: ", this.$cookies.get("id"));

      console.log(requestBody);

      const requestOptions = {
        method: "PUT",
        credentials: "include",
        body: JSON.stringify(requestBody),
        headers: {
          "Content-Type": "application/json",
          Authorization: 'Basic ' + btoa(this.$cookies.get("username") + ':' + this.$cookies.get("password")),
        },
      };

      let url = 'http://127.0.0.1:8080/owners/' + this.$cookies.get("id");
      if (this.$cookies.get("role") == "customer") {
        url = 'http://127.0.0.1:8080/customers/' + this.$cookies.get("id");
      }
      else if (this.$cookies.get("role") == "instructors") {
        url = 'http://127.0.0.1:8080/instructors/' + this.$cookies.get("id");
      }
      fetch(
        url,
        requestOptions
      )
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          if (data.error === "") {
            console.log("Profile was approved:", data);
            
            // Update cookies if it worked
            this.$cookies.set('username', this.profile.email);
            this.$cookies.set('password', this.profile.password);
            this.profile.errorMessage = "Your profile was updated";
            this.profile.showSuccessfulMessage = true;
            this.profile.showErrorMessage = false;
          }
          else {
            this.profile.errorMessage = data.error;
            this.profile.showErrorMessage = true;
            this.profile.showSuccessfulMessage = false;
            console.log(data.error);
          }
        });
    },
    updateSportCenterInfo() {
      // Function to format the time string with seconds, (Since when we edit the text field and hide the second field, it will put nothing but we want 00 to do the request)
      const formatTimeWithSeconds = (timeString) => {
        const timeParts = timeString.split(':');
        // Ensure that all time parts (hours, minutes, seconds) are present
        const formattedTime = `${timeParts[0]}:${timeParts[1]}:${timeParts[2] || '00'}`;
        return formattedTime;
      };

      // Create a JSON object with the data to be sent in the POST request
      const requestBody = {
          name: this.sportCenter.name,
          email: this.sportCenter.email,
          phoneNumber: this.sportCenter.phoneNumber,
          address: this.sportCenter.address,
          openingTime: formatTimeWithSeconds(this.sportCenter.openingTime),
          closingTime: formatTimeWithSeconds(this.sportCenter.closingTime),
      };

      console.log(requestBody);

      const requestOptions = {
        method: "PUT",
        credentials: "include",
        body: JSON.stringify(requestBody),
        headers: {
          "Content-Type": "application/json",
          Authorization: 'Basic ' + btoa(this.$cookies.get("username") + ':' + this.$cookies.get("password")),
        },
      };

      let url = 'http://127.0.0.1:8080/sport-center';
      fetch(
        url,
        requestOptions
      )
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          if (data.error === "") {
            console.log("SportCenter was updated", data);
            this.sportCenter.errorMessage = "The sport center was updated";
            this.sportCenter.showSuccessfulMessage = true;
            this.sportCenter.showErrorMessage = false;
          }
          else {
            this.sportCenter.errorMessage = data.error;
            this.sportCenter.showErrorMessage = true;
            this.sportCenter.showSuccessfulMessage = false;
            console.log(data.error);
          }
        });
    },
  },
};
</script>

<style scoped>

.btn-outline {
    background-color: #cdf567 !important;
    border-color: #cdf567 !important;
    color: var(--color-black) !important;
}

.btn-outline:hover {
    background-color: #a0ca35 !important;
    border-color: #a0ca35 !important;
    color: var(--color-black) !important;
}

h2 {
  color: #ffffff; 
  font-size: 2em;
  padding: 30px;
}

.toolbar-container {
  background-color: var(--color-black);
}

.content-container {
  background-color: var(--color-black);
  min-height: 100vh;
}

.content {
  width: 80%;
  color: #ffffff;
}

.sidebar-container {
  height: 100%;
}

.sidebar {
  border-radius: 10px;
  background-color: #343434;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
  text-align: left;
  color: #ffffff;
  font-size: .8em;
}

.sidebar-title {
  color: #ffffff;
  font-size: .8em;
}

.menu {
  list-style-type: none;
  padding: 0;
  text-align: left;
  color: #ffffff;
}

.menu-item {
  padding: 10px;
  margin: 10px;
  border-radius: 5px;
  transition: background-color 0.3s ease-in-out;
  cursor: pointer;
  font-size: 1.2em;
}

.menu-item-selected {
  background-color: #505050;
}

.main-content {
  text-align: left;
  overflow-x: hidden; /* Add this line */
  padding: 20px;
  min-width: 50%;
}

.main-title {
  color: #ffffff;
  font-size: 35px;
}

.container {
  color: #ffffff;
}

.text-field {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #7d7d7d; /* Set initial border color to gray */
  background-color: transparent;
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

/* Target the clock icon */
input[type="time"]::-webkit-calendar-picker-indicator {
  display: none;
}

.error {
  font-size: .8em;
}

.red-text {
  color: #F56363; 
}

.green-text {
  color: #cdf567; 
}

.hidden {
    visibility: hidden;
}
</style>
