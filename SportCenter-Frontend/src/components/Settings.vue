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

        <!-- My Sessions Settings -->
        <div v-else-if="currentTab === 'MySessions'">
          <CustomerSessions />
        </div>

        <!-- Billing Account Settings -->
        <div v-else-if="currentTab === 'BillingAccount'">
          <BillingAccount />
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
          <div style="color: #ffffff">
            <div style="margin-top: 20px; margin-bottom: 10px">
              <div style="font-weight: bold">Name</div>
              <input
                type="name"
                v-model="sportCenter.name"
                class="text-field"
              />
            </div>
            <div style="margin-top: 20px; margin-bottom: 10px">
              <div style="font-weight: bold">Email</div>
              <input
                type="email"
                v-model="sportCenter.email"
                class="text-field"
              />
            </div>
            <div style="margin-top: 20px; margin-bottom: 10px">
              <div style="font-weight: bold">Phone number</div>
              <input
                type="phone number"
                v-model="sportCenter.phoneNumber"
                class="text-field"
              />
            </div>
            <div style="margin-top: 20px; margin-bottom: 10px">
              <div style="font-weight: bold">Address</div>
              <input
                type="text"
                v-model="sportCenter.address"
                class="text-field"
              />
            </div>
            <div>
              <label for="opening-time">Opening time</label>
              <b-form-timepicker
                id="opening-time"
                class="timepicker"
                :state="true"
              ></b-form-timepicker>
            </div>
            <div>
              <label for="closing-time">Closing time</label>
              <b-form-timepicker
                id="closing-time"
                class="timepicker"
                :state="true"
              ></b-form-timepicker>
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
import BillingAccount from "./BillingAccount.vue";
import BillingAccountOverview from "./BillingAccountOverview.vue";
import CustomerSessions from "./CustomerSessions.vue";
import ProposeCourse from "./ProposeCourse.vue";

export default {
  name: "ProfileSettings",
  components: {
    OwnerCourses,
    BillingAccount,
    CustomerSessions,
    ProposeCourse,
    // Add more components as needed
  },
  mounted() {
    // Call your function to retrieve JSON data and autofill the input field
    this.getCurrentAccountInfo();
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
        email: "",
        address: "",
        phoneNumber: "",
        openingTime: "",
        closingTime: "",
      },
    };
  },
  methods: {
    toggleMenu(tabName) {
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
          console.log(response);
          return response.json();
        })
        .then((data) => {
          if (data.error === "") {
            console.log("Profile was approved:", data);
            
            // Update cookies if it worked
            this.$cookies.set('username', this.profile.email);
            this.$cookies.set('password', this.profile.password);
            this.profile.errorMessage = "Profile was updated";
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
  },
};
</script>

<style scoped>

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
  background-color: #232323;
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
  background-color: #444;
}

.main-content {
  text-align: left;
  overflow-x: hidden; /* Add this line */
  padding: 20px;
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
  border: 1px solid #444; /* Set initial border color to gray */
  background-color: transparent;
  color: #ffffff;
  height: 50px;
  box-sizing: border-box;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out,
    border-width 0.1s ease-in-out; /* Add transition effect */
  font-weight: normal; /* Set default font weight */
  border-width: 1px; /* Set initial border width */
}

.timepicker {
  width: 100%;
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #444; /* Set initial border color to gray */
  background-color: transparent;
  color: #ffffff;
  height: 50px;
  box-sizing: border-box;
  margin-top: 4px;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out,
    border-width 0.1s ease-in-out; /* Add transition effect */
  font-weight: normal; /* Set default font weight */
  border-width: 1px; /* Set initial border width */
}

.text-field:focus,
.text-field:hover {
  outline: none;
  border-color: #fff; /* Change border color to white on hover or focus */
}

.text-field:focus {
  border-width: 2px; /* Increase border width on hover or focus */
}

.text-field[type="time"]::-webkit-datetime-edit-second-field {
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
