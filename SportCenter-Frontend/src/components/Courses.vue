<template>
  <div class="solid-background">
    <Toolbar />
      <!-- Toolbar and search bar -->
      <div class="text-search-bar">
        <div class="text-content">
          <h1 class="custom-h1">Explore your interests</h1>
          <h3>Select a course to learn more</h3>
        </div>
        <div class="search-field">
          <input type="text" class="search-input" placeholder="Search courses">
        </div>
      </div>

    <div class="cont">
      <div class="mt-5">
        <div class="row">
          <div class="col-md-5 col-lg-3 col-sm-12 mb-5" v-for="course in list.courses" :key="course.id">
            <div>
              <img :src="course.url" :alt="course.name" class="w-100 h-100">
            </div>
            <h3><span class="white-heading">{{ course.category }}</span></h3>
            <div>
              <h3>{{ capitalize(course.name) }}</h3>
              <p>{{ course.description }}</p>
            </div>
          </div>
        </div>
      </div>
      </div>
  </div>
</template>
<script>
export default {
  name: 'Courses',
  data() {
    return {
      list: [],
      hoveredCardColor: ''
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

      fetch('http://127.0.0.1:8080/public/courses', requestOptions)
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
  background-color: #121212;
  height: 100vh;
  width: 100vw;
  overflow: auto;
}

.custom-h1 {
  color: #ffffff;
  font-size: 55px;
}

.text-search-bar{
    margin-top: 30px;
    margin-left: 50px;
    margin-right:50px;
}

.cont {
  margin-top: 30px;
  width: 95vw;
  height: 100vh;
  margin-left: 50px;
  margin-right:50px;
}


.search-input {
  width: 180px;
  height: 35px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 20px;
  background-color: #bfd3f2;
  color: #ffffff;
}

.white-heading {
  font-size: 14px;
  color: #121212;
  background-color: #FFF;
  font-weight: 700;
  padding: 1%;
}

h3 {
  font-size: 24px;
  font-weight: 700;
  color: #FFFFFF;
}

p {
  font-size: 18px;
  font-weight: 400;
  color: #FFFFFF;
}

body {
  margin: 0;
  padding: 0;
}
</style>
