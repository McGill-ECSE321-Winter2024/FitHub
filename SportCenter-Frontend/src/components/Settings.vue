<template>
  <div style="font-family: Figtree; height: 100vh; background-color: #121212">
    <div style="background-color: #121212">
      <Toolbar />
    </div>
    <!-- Container for sidebar and main content -->
    <div class="content-container">
      <!-- Sidebar container with gray box -->
      <div
        class="sidebar-container"
        style="width: 370px; margin-right: 50px; overflow-y: auto; height: 100%"
      >
        <div
          class="sidebar"
          style="
            border-radius: 10px;
            background-color: #232323;
            margin-top: 20px;
            margin-left: 20px;
            width: 330px;
            height: 90%;
          "
        >
          <h2 style="color: #ffffff; font-size: 45px; padding: 30px">
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
              style="cursor: pointer; padding: 20px; font-size: 20px"
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
              style="cursor: pointer; padding: 20px; font-size: 20px"
            >
              Edit sport center
            </li>
            <!-- Manage Courses Button -->
            <li
              @click="toggleMenu('ManageCourses')"
              :class="{
                'menu-item-selected': currentTab === 'ManageCourses',
              }"
              class="menu-item"
              style="cursor: pointer; padding: 20px; font-size: 20px"
            >
              Manage courses
            </li>
            <!-- Manage Locations Button -->
            <li
              @click="toggleMenu('ManageLocations')"
              :class="{
                'menu-item-selected': currentTab === 'ManageLocations',
              }"
              class="menu-item"
              style="cursor: pointer; padding: 20px; font-size: 20px"
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
              style="cursor: pointer; padding: 20px; font-size: 20px"
            >
              My sessions
            </li>
            <!-- Billing Account Button -->
            <li
              @click="toggleMenu('BillingAccount')"
              :class="{
                'menu-item-selected': currentTab === 'BillingAccount',
              }"
              class="menu-item"
              style="cursor: pointer; padding: 20px; font-size: 20px"
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

        <!-- Billing Account Settings -->
        <div v-else-if="currentTab === 'BillingAccount'">
          <BillingAccount />
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

export default {
  name: "ProfileSettings",
  components: {
    OwnerCourses,
    BillingAccount,
    // Add more components as needed
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
  },
};
</script>

<style>
.menu-item {
  padding: 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease-in-out;
  margin-left: 15px;
  margin-right: 15px;
  margin-top: 10px;
  margin-bottom: 10px;
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
