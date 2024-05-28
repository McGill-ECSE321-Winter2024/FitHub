<template>
  <div class="solid-background">
    <!-- Toolbar and search bar -->
    <div class="text-search-bar" :class="{ 'blur-background': isPopupOpen }">
      <div class="text-content" style="text-align: left">
        <h2 class="custom-h2">Manage courses</h2>
        <h4 class="custom-h4">Edit or delete courses from the center.</h4>
      </div>
    </div>

    <!-- Display the UpdateCourseForm component when the icon is clicked -->
    <UpdateCourseForm
      v-if="showUpdateForm"
      :course="selectedCourse"
      @submit="updateCourse"
      @cancel="closeUpdateCourseForm"
      @close="closeUpdateCourseForm"
      style="
        z-index: 9999;
        position: absolute;
        top: 50px;
        left: 50%;
        transform: translateX(-50%);
      "
    />

    <div class="mt-5" :class="{ 'blur-background': isPopupOpen }">
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
                <div class="category-price">
                  <h3>
                    <span class="white-heading">{{ course.category.toUpperCase() }},  {{ course.pricePerHour }}$/hour</span>
                  </h3>
                </div>
                <div class="difficulty" :style="{ backgroundColor: getColorDifficulty(course.difficulty) }">
                  {{ course.difficulty }}
                </div>
            <h3>{{ capitalize(course.name) }}</h3>
            <p>{{ course.description }}</p>

            <div class="buttons">
              <!-- Display the pencil icon and bind the click event to openUpdateCourseForm method -->
              <b-icon
                icon="pencil-fill"
                @click="openUpdateCourseForm(course)"
                class="pencil-icon"
              ></b-icon>
              <b-icon
                icon="trash-fill"
                @click="deleteCourse(course.id)"
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
// Import the UpdateCourseForm component
import UpdateCourseForm from "./UpdateCourseForm.vue";

export default {
  name: "Courses",
  data() {
    return {
      list: [],
      hoveredCardColor: "",
      username: "",
      password: "",
      showUpdateForm: false, // Add a data property to track whether to show the UpdateCourseForm
      selectedCourse: null, // Add a data property to store the selected course
      isPopupOpen: false, // Add a data property to track whether the popup is open
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
        "http://127.0.0.1:8080/public/courses?status=Approved",
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
        getColorDifficulty(difficulty) {
      switch (difficulty) {
        case 'Advanced':
          return '#E3240C';
        case 'Intermediate':
          return '#FF5746';
        case 'Beginner':
          return '#CDF563';
        default:
          return '';
      }
    },

    capitalize(str) {
      return str.replace(/\b\w/g, (char) => char.toUpperCase());
    },
    deleteCourse(courseId) {
      const username = decodeURIComponent(this.$cookies.get("username"));
      const password = this.$cookies.get("password");

      const requestOptions = {
        method: "DELETE",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Basic " + btoa(username + ":" + password),
        },
      };

      fetch(`http://127.0.0.1:8080/courses/${courseId}`, requestOptions)
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          console.log("Course deleted successfully");
          this.getAllCourses(); // Refresh the courses list
        })
        .catch((error) => {
          console.error("Error deleting course:", error);
        });
    },
    openUpdateCourseForm(course) {
      console.log("Opening UpdateCourseForm for course:", course);
      this.selectedCourse = { ...course };
      this.showUpdateForm = true;
      this.isPopupOpen = true;
    },
    closeUpdateCourseForm() {
      this.showUpdateForm = false;
      this.isPopupOpen = false;
      this.getAllCourses(); 
    },
    updateCourse(formData) {
      console.log("Updating course with data:", formData);
      // Send the form data to update the course via API
      // Hide the update form after successful update
      this.showUpdateForm = false;
    },
  },
  components: {
    UpdateCourseForm,
  },
};
</script>

<style scoped>
.pencil-icon {
  color: #cdf563;
  font-size: 20px;
  cursor: pointer;
  margin-right: 25px;
}

.pencil-icon:hover {
  color: #fff;
}

/* Add styles for the UpdateCourseForm */
.solid-background {
  background-color: var(--color-black);
  height: 100vh;
  width: 70vw;
  overflow: auto;
  position: relative; /* Set the position to relative for proper positioning of the absolute element */
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

.search-input {
  width: 180px;
  height: 35px;
  border: 1px solid #ccc;
  border-radius: 20px;
  background-color: #bfd3f2;
  color: #ffffff;
}
.white-heading {
  padding: 5px;
  font-size: 14px;
  color: var(--color-black);
  background-color: #cdf563;
  font-weight: 700;
}

.difficulty {
  font-size: 14px;
  color: var(--color-black);
  font-weight: 700;
  padding: 1%;
  width: 95px;
  margin-bottom: 10px;
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
  color: #ec5545;
  font-size: 20px;
  cursor: pointer;
  margin-right: 25px;
}

.disapprove:hover {
  color: #fff;
}

.blur-background {
  filter: blur(2px);
}

.buttons {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
