<template>
  <div class="solid-background">
      <!-- Toolbar and search bar -->
      <div class="text-search-bar">
        <div class="text-content">
          <h1 class="custom-h1">Manage courses</h1>
          <h3>Approve or disapprove courses which instructors have proposed</h3>
        </div>
      </div>

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

              <div class="buttons">
                <button @click="approveCourse(course.id)" class="approve">Approve</button>
                <button @click="disapproveCourse(course.id)" class="disapprove">Disapprove</button>
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

       fetch('http://127.0.0.1:8080/public/courses?status=Pending',requestOptions)
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
    },
    approveCourse(courseId) {

    },
    disapproveCourse(courseId) {

    }
  }
}
</script>

<style scoped>
.solid-background {
  background-color: var(--color-black);
  height: 100vh;
  width: 70vw;
  overflow: auto;
  margin-left: -30px;
}

.custom-h1 {
  color: #ffffff;
  font-size: 55px;
}

.text-search-bar{
    margin-top: 15px;
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
  color: var(--color-black);
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

.approve{
    border: 0px;
    background-color: #CDF563;
    color: var(--color-black);
    font-weight: bold;
    border-radius: 20px;
    height: 40px;
    width: 100px;
}

.disapprove {
    margin-left: 10px;
    border: 0px;
    background-color: #E3240C;
    color: var(--color-black);
    font-weight: bold;
    border-radius: 20px;
     height: 40px;
    width: 100px;
}

.buttons {
  display: flex;
  justify-content: center;
}
</style>
