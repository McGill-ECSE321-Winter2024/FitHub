<template>
  <div
    style="
      font-family: Figtree;
      height: 100vh;
      background-color: var(--color-black);
    "
  >
    <div style="background-color: var(--color-black)">
      <Toolbar />
    </div>
    <!-- Container for sidebar and main content -->
    <div class="content-container">
      <!-- Sidebar container with gray box -->
      <div
        class="sidebar-container"
        style="width: 500px; margin-right: 50px; overflow-y: auto; height: 100%"
      >
        <div
          class="sidebar"
          style="
            border-radius: 10px;
            background-color: #232323;
            margin-top: 20px;
            margin-left: 20px;
            width: 360px;
            height: 90%;
          "
        >
          <h2 style="color: #ffffff; font-size: 35px; padding: 30px">
            Settings
          </h2>
          <!-- Adjust font size here -->
          <ul
            style="
              list-style-type: none;
              padding: 0;
              text-align: left;
              color: #ffffff;
            "
          >
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
        class="main-content"
        style="flex-grow: 1; overflow: auto; padding: 60px; text-align: left"
      >
        <!-- Edit Profile -->
        <div v-if="currentTab === 'EditProfile'">
          <img class="mt-3" v-bind:src="this.$cookies.get()"
                                    @error="$event.target.src = defaultImage" 
                                    :style="{ 'width': '200px', 'height': 'auto' }" />

          <h2 style="color: #ffffff; font-size: 35px; margin-bottom: 50px">
            Edit profile
          </h2>
          <!-- Properties and text fields -->
          <div style="color: #ffffff">
            <div style="margin-top: 20px; margin-bottom: 10px">
              <div style="font-weight: bold">Name</div>
              <input type="text" v-model="profile.name" class="text-field" />
            </div>
            <div style="margin-top: 20px; margin-bottom: 10px">
              <div style="font-weight: bold">Email</div>
              <input type="email" v-model="profile.email" class="text-field" />
            </div>
            <div style="margin-top: 20px; margin-bottom: 50px">
              <div style="font-weight: bold">Password</div>
              <input
                type="password"
                v-model="profile.password"
                class="text-field"
              />
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
          <h2 style="color: #ffffff; font-size: 35px; margin-bottom: 50px">
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
        name: "",
        email: "",
        password: "",
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
                credentials: 'include'
            }).then((accountsResponse) => {
                if (accountsResponse.status === 204) {
                    console.log("No account with Id was found");
                }
                else {
                    accountsResponse.json().then(accounts => {
                        console.log(accounts.accounts);
                        this.instructors = accounts.accounts;

                        this.instructors.forEach(instructor => {
                            this.getAllCourses(instructor.id);
                        });

                        console.log(this.instructorCourses);
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
          name: this.profile.name,
          email: this.profile.email,
          password: this.profile.password
      };

      console.log(
        "username: ",
        decodeURIComponent(this.$cookies.get("username"))
      );
      console.log("password: ", this.$cookies.get("password"));
      console.log("role: ", this.$cookies.get("role"));
      console.log("id: ", this.$cookies.get("id"));

      const requestOptions = {
        method: "PUT",
        credentials: "include",
        body: requestBody,
        headers: {
          "Content-Type": "application/json",
          Authorization: 'Basic ' + btoa(this.username + ':' + this.password),
        },
      };
      const url = 'http://127.0.0.1:8080/customers/' + this.$cookies.get("id");
      fetch(
        url,
        requestOptions
      )
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then((data) => {
          console.log("Profile was approved:", data);
        })
        .catch((error) => {
          console.error("Failed to update profile:", error);
        });
      
    },
  },
};
</script>

<style>
.menu-item {
  padding: 20px;
  border-radius: 5px;
  transition: background-color 0.3s ease-in-out;
  margin-left: 15px;
  margin-right: 15px;
  margin-top: 10px;
  margin-bottom: 10px;
  cursor: pointer;
  font-size: 18px;
}

.menu-item-selected {
  background-color: #444;
}

.content-container {
  display: flex;
  height: calc(100% - 50px);
}

.text-field {
  width: 90%;
  padding: 10px;
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

.timepicker {
  width: 90%;
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
</style>
