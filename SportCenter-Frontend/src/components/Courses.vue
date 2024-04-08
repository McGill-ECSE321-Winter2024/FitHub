<template>
  <div class="solid-background">
    <Toolbar />
    <!-- Toolbar and search bar -->
    <div class="text-search-bar">
      <div class="text-content">
        <h1 class="custom-h1">
          <span class="explore-your">Explore your</span>
          <span class="interests">interests</span>
        </h1>
        <h3 class="custom-h3">Select a course to learn more</h3>
      </div>
    </div>

    <!-- Course list -->
    <div class="cont">
      <div class="mt-5">
        <div class="row">
          <div v-for="course in list.courses" :key="course.id" class="col-md-5 col-lg-3 col-sm-12 mb-5">
            <router-link to="/sessions">
              <div>
                <img :src="course.url" :alt="course.name" class="w-100 h-100">
              </div>
              <div class="content">
                <h3><span class="white-heading">{{ course.category }}</span></h3>
                <div>
                  <h3 class="custom-h3">{{ capitalize(course.name) }}</h3>
                  <p>{{ course.description }}</p>
                </div>
              </div>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import ProposeCourse from './ProposeCourse.vue'; 

export default {
  name: 'Courses',
  components: {
    ProposeCourse
  },
  data() {
    return {
      list: [],
      hoveredCardColor: '',
      showCreateCourse: false // Initially hide the CreateCourse component
    }
  },
  mounted() {
    this.getAllCourses();
  },
  methods: {
    getAllCourses() {
      const requestOptions = {
        method: 'GET',
        credentials: 'include'
      };

      fetch('http://127.0.0.1:8080/public/courses?status=Approved', requestOptions)
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          this.list = data;
          console.log(this.list)
        })
        .catch(error => {
          console.error('Error fetching courses:', error);
        });
    },
    getBorderColor(difficulty) {
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
    }
  }
}
</script>

<style scoped>
.solid-background {
  background-color: var(--color-black);
  height: 100vh;
  width: 100vw;
  overflow: auto;
}

.custom-h1 {
  color: var(--color-white);
  font-size: 55px;
}

.explore-your {
  -webkit-text-stroke: 1px white; /* Outline width and color */
  text-stroke: 1px white; /* Outline width and color */
  color: black; /* Fill color */
}


.interests {
  color: #CDF563;
  padding: 0 5px; /* Adjust padding as needed */
}

.content {
    text-align: left;
}

.custom-h3 {
  font-size: 24px;
  font-weight: 700;
  color: var(--color-white);
}

.left-content {
  margin-left: 50px;
  margin-right: 50px;
}

.text-search-bar {
  margin-top: 30px;
  margin-left: 50px;
  margin-right: 50px;
  text-align: left;
}

.cont {
  margin-top: 30px;
  width: 95vw;
  margin-left: 50px;
  margin-right: 50px;
}

.search-input {
  width: 180px;
  height: 35px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 20px;
  background-color: #bfd3f2;
  color: var(--color-white);
}

.white-heading {
  font-size: 14px;
  color: var(--color-black);
  background-color: #CDF563;
  font-weight: 700;
  padding: 1%;
}

p {
  font-size: 18px;
  font-weight: 400;
  color: var(--color-white);
}

body {
  margin: 0;
  padding: 0;
}

.hover-link {
  display: inline-block;
  position: relative;
}

.hover-link:hover {
  text-decoration: none; /* Remove default underline */
}

.hover-link:hover::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border: 3px solid #CDF563; /* Adjust the border width and color as needed */
  box-sizing: border-box;
}
</style>
