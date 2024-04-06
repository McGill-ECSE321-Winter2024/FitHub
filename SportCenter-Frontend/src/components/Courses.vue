<template>
  <div class="solid-background">

    <Toolbar />
    <div class="text-search-bar">
      <div class="text-content">
        <h1 class="custom-h1">Explore your interests</h1>
        <h3>Select a course to learn more</h3>
      </div>
      <div class="search-field">
        <input type="text" class="search-input" placeholder="Search courses">
      </div>
    </div>

    <div class="cards-container">
      <b-card
        v-for="course in list.courses"
        :key="course.id"
        class="custom-card"
        :style="{ borderColor: getBorderColor(course.difficulty) }">
        <div class="card-img-top">
          <img :src="require(`@/assets/${course.icon1}.png`)" alt="Course Image">
        </div>
        <div class="card-body">
          <h5 class="card-title">{{ course.name.toUpperCase() }}</h5>
        </div>
      </b-card>
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
    }
  }
}


</script>
<style scoped>

.custom-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 0 20px rgba(255, 255, 255, 0.5);
  background-color: var(--hovered-card-color);
}

.solid-background {
  background-color: #121212;
  height: 100vh;
  overflow: auto;
}

.custom-h1 {
  color: #ffffff;
  font-size: 55px;
}

.cards-container {
  display: flex;
  flex-wrap: wrap;
  margin-right: 20px;
  margin-left: 25px;
  margin-bottom: 60px;
  position: relative;
  overflow: hidden; /* Ensure spike doesn't overflow */
}

img {
  transform: scale(1.8);
  margin-top: 30px;
  margin-bottom: 20px;
}

.text-search-bar {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: left;
  text-align: left;
  padding: 20px;
  margin-top: 20px;
  margin-left: 60px;
  margin-right: 60px;
}

.text-content {
  flex: 1;
  text-align: left;
  color: #ffffff;
}

.search-field {
  margin-top: 20px;
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

.custom-card {
  background: #121212;
  border: 4px solid #fff;
  flex: 0 0 290px;
  height: 198px;
  margin: 20px;
  border-radius: 42px;
  position: relative;
  z-index: 1;
}

.custom-card .card-body {
  color: #fff;
}

</style>
