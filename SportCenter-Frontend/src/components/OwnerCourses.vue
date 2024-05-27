<template>
  <div class="solid-background">
    <!-- Toolbar and search bar -->
    <div class="text-search-bar">
      <div class="text-content" style="text-align: left">
        <h2 class="custom-h2">Approve or disapprove course</h2>
        <h4 class="custom-h4">
          Approve or disapprove courses which instructors have proposed.
        </h4>
      </div>
    </div>

    <div class="popup-delete" v-if="showPopup">
      <div class="popup-content">
        <p>
          Enter the price per hour for {{ capitalize(selectedCourse.name) }}:
        </p>
        <input
          v-model="pricePerHour"
          placeholder="Price per hour"
          class="text-field"
        />
        <div class="popup-buttons">
          <button @click="submitPrice" class="approve">Approve</button>
          <button @click="cancelPopup" class="disapprove">Cancel</button>
        </div>
      </div>
    </div>

    <div class="mt-5">
      <div class="row">
        <div
          class="col-md-5 col-lg-3 col-sm-12 mb-5"
          v-for="course in list.courses"
          :key="course.id"
        >
          <div>
            <img
              :src="course.url"
              :alt="course.name"
              class="w-100 h-100"
              style="margin-bottom: 10px"
            />
          </div>
          <h3>
            <h3>
              <span class="white-heading"
                >{{ course.category }}, {{ course.pricePerHour }}$/hour</span
              >
            </h3>
          </h3>
          <div>
            <h3>{{ capitalize(course.name) }}</h3>
            <p>{{ course.description }}</p>

            <div class="buttons">
              <button @click="approveCourse(course)" class="approve">
                Approve
              </button>
              <button @click="disapproveCourse(course.id)" class="disapprove">
                Disapprove
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Courses",
  data() {
    return {
      list: [],
      hoveredCardColor: "",
      username: "",
      password: "",
      showPopup: false,
      selectedCourse: null,
      pricePerHour: null,
    };
  },
  mounted() {
    this.username = decodeURIComponent(this.$cookies.get("username"));
    this.password = this.$cookies.get("password");
    this.getAllCourses();
  },
  methods: {
    getAllCourses() {
      const requestOptions = {
        method: "GET",
        credentials: "include",
      };

      fetch(
        "http://127.0.0.1:8080/public/courses?status=Pending",
        requestOptions
      )
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then((data) => {
          this.list = data;
          console.log(this.list);
        })
        .catch((error) => {
          console.error("Error fetching courses:", error);
        });
    },
    capitalize(str) {
      return str.replace(/\b\w/g, (char) => char.toUpperCase());
    },
    approveCourse(course) {
      // Set the selected course before showing the popup
      this.selectedCourse = course;
      // Show the popup for entering the price per hour
      this.showPopup = true;
    },
    disapproveCourse(courseId) {
      const requestOptions = {
        method: "PUT",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Basic " + btoa(this.username + ":" + this.password),
        },
      };

      fetch(
        `http://127.0.0.1:8080/course-disapproval/${courseId}`,
        requestOptions
      )
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then((data) => {
          console.log("Course disapproved:", data);
          this.getAllCourses(); // Refresh the courses list
        })
        .catch((error) => {
          console.error("Error disapproving course:", error);
        });
    },
    submitPrice() {
      // Check if pricePerHour is valid
      if (!this.pricePerHour || this.pricePerHour <= 0) {
        alert("Please enter a valid price per hour.");
        return;
      }

      // Call the API to approve the course with the entered price per hour
      const courseId = this.selectedCourse.id;
      const requestOptions = {
        method: "PUT",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Basic " + btoa(this.username + ":" + this.password),
        },
      };

      fetch(
        `http://127.0.0.1:8080/course-approval/${courseId}?value=${this.pricePerHour}`,
        requestOptions
      )
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          return response.json();
        })
        .then((data) => {
          console.log("Course approved:", data);
          this.getAllCourses(); // Refresh the courses list
          // Hide the popup after approval
          this.showPopup = false;
        })
        .catch((error) => {
          console.error("Error approving course:", error);
        });
    },
    cancelPopup() {
      // Hide the popup when cancel is clicked
      this.showPopup = false;
      this.pricePerHour = "";
    },
  },
};
</script>

<style scoped>
.solid-background {
  background-color: var(--color-black);
  height: 100vh;
  width: 70vw;
  overflow: auto;
  margin-left: 100px;
  margin-top: 50px;
}

.custom-h2 {
  color: #ffffff;
  font-size: 2rem;
}

.custom-h4 {
  font-size: 20px;
  font-weight: 600;
  color: var(--color-white);
}

.white-heading {
  font-size: 14px;
  color: var(--color-black);
  background-color: #cdf563;
  font-weight: 700;
  padding: 1%;
}

.search-input {
  width: 180px;
  height: 35px;
  border: 1px solid #ccc;
  border-radius: 20px;
  background-color: #bfd3f2;
  color: #ffffff;
}

h3 {
  font-size: 20px;
  font-weight: 700;
  color: #ffffff;
}

p {
  font-size: 18px;
  font-weight: 400;
  color: #ffffff;
}

body {
  margin: 0;
  padding: 0;
}

.approve {
  border: 0px;
  background-color: #cdf563;
  color: var(--color-black);
  font-weight: bold;
  border-radius: 20px;
  height: 40px;
  width: 100px;
}

.disapprove {
  margin-left: 10px;
  border: 0px;
  background-color: #ec5545;
  color: var(--color-black);
  font-weight: bold;
  border-radius: 20px;
  height: 40px;
  width: 100px;
}

.text-field {
  width: 50%;
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #444;
  background-color: transparent;
  color: #ffffff;
  height: 50px;
  box-sizing: border-box;
  margin-top: 4px;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out,
    border-width 0.1s ease-in-out; /* Add transition effect */
  font-weight: normal;
  border-width: 1px;
}

.text-field:hover {
  outline: none;
  border-color: #fff;
}

.text-field:focus {
  border-width: 2px;
}

.buttons {
  display: flex;
  justify-content: center;
}

.popup-delete {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: var(--color-black);
  border: 2px solid #ccc;
  border-radius: 40px;
  padding: 20px;
  z-index: 9999; /* Ensure it appears on top of other elements */
  width: 300px; /* Adjust the width as needed */
}

.popup-content {
  text-align: center;
  color: var(--color-white);
}

.popup-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.cancel-btn {
  margin-right: 10px;
}

.confirm-btn {
  margin-left: 10px;
}
</style>
