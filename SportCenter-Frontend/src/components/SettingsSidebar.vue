<template>
  <div class="sidebar-container">
    <div class="sidebar">
      <h2 style="color: #ffffff; font-size: 45px; padding: 30px">Settings</h2>
      <div
        style="
          list-style-type: none;
          padding: 0;
          text-align: left;
          color: #ffffff;
        "
      >
        <b-row
          @click="toggleMenu('EditProfile')"
          :class="{
            'menu-item-selected': currentTab === 'EditProfile',
          }"
          class="menu-item"
          style="
            cursor: pointer;
            padding: 20px;
            font-size: 20px;
            color: #ffffff;
          "
        >
          Edit profile
        </b-row>
        <b-row
          @click="toggleMenu('EditSportCenter')"
          :class="{
            'menu-item-selected': currentTab === 'EditSportCenter',
          }"
          class="menu-item"
          style="
            cursor: pointer;
            padding: 20px;
            font-size: 20px;
            color: #ffffff;
          "
        >
          Edit sport center
        </b-row>
        <b-row
          @click="toggleMenu('ManageCourses')"
          :class="{
            'menu-item-selected': currentTab === 'ManageCourses',
          }"
          class="menu-item"
          style="
            cursor: pointer;
            padding: 20px;
            font-size: 20px;
            color: #ffffff;
          "
        >
          Manage courses
        </b-row>
        <b-row
          @click="toggleMenu('MySessions')"
          :class="{
            'menu-item-selected': currentTab === 'MySessions',
          }"
          class="menu-item"
          style="
            cursor: pointer;
            padding: 20px;
            font-size: 20px;
            color: #ffffff;
          "
        >
          My sessions
        </b-row>
        <b-row
          @click="toggleMenu('Locations')"
          :class="{
            'menu-item-selected': currentTab === 'Locations',
          }"
          class="menu-item"
          style="
            cursor: pointer;
            padding: 20px;
            font-size: 20px;
            color: #ffffff;
          "
        >
          Locations
        </b-row>
        <b-row
          @click="toggleMenu('BillingAccount')"
          :class="{
            'menu-item-selected': currentTab === 'BillingAccount',
          }"
          class="menu-item"
          style="
            cursor: pointer;
            padding: 20px;
            font-size: 20px;
            color: #ffffff;
          "
        >
          Billing account
        </b-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    currentTab: String,
  },
  methods: {
    toggleMenu(tabName) {
      this.$emit("toggle-menu", tabName);
    },
  },
};
</script>

<style scoped>
.sidebar-container {
  width: 350px;
  margin-right: 50px;
  overflow-y: auto;
  height: 100%;
}

.sidebar {
  border-radius: 10px;
  background-color: #232323;
  margin-top: 20px;
  margin-left: 20px;
  width: 330px;
  height: 90%;
}

.menu-item {
  margin-top: 10px;
  margin-bottom: 10px;
  list-style-type: none;
  color: #ffffff;
}

.menu-item-selected {
  background-color: #444;
}
</style>
