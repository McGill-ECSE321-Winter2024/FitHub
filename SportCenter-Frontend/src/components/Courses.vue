<template>
  <div class="gradient-background">
  <Toolbar />
    <div class="text-search-bar">
      <div class="text-content">
        <h1>Explore your interests</h1>
        <h3>Select a course to learn more</h3>
      </div>
      <div class="search-field">
        <input type="text" class="search-input" placeholder="Search courses">
      </div>
    </div>

    <div class="cards-container">

      <b-card class="custom-card">

        <div class="card-img-top">
          <img src="@/assets/goat-64.png" alt="Picture 1">
          <img src="@/assets/yoga-50.png" alt="Picture 2">
        </div>

        <div class="card-body">
          <h5 class="card-title">GOAT YOGA</h5>

        </div>
      </b-card>

      <b-card class="custom-card">

        <div class="card-img-top">
          <img src="@/assets/dog-64.png" alt="Picture 1">
          <img src="@/assets/yoga-50.png" alt="Picture 2">
        </div>

        <div class="card-body">
          <h5 class="card-title">DOG YOGA</h5>

        </div>
      </b-card>

      <b-card class="custom-card">

        <div class="card-img-top">
          <img src="@/assets/goat-64.png" alt="Picture 1">
          <img src="@/assets/yoga-50.png" alt="Picture 2">
        </div>

        <div class="card-body">
          <h5 class="card-title">GOAT YOGA</h5>

        </div>
      </b-card>

      <b-card class="custom-card">

        <div class="card-img-top">
          <img src="@/assets/dog-64.png" alt="Picture 1">
          <img src="@/assets/yoga-50.png" alt="Picture 2">
        </div>

        <div class="card-body">
          <h5 class="card-title">DOG YOGA</h5>

        </div>
      </b-card>

      <b-card class="custom-card">

        <div class="card-img-top">
          <img src="@/assets/goat-64.png" alt="Picture 1">
          <img src="@/assets/yoga-50.png" alt="Picture 2">
        </div>

        <div class="card-body">
          <h5 class="card-title">GOAT YOGA</h5>

        </div>
      </b-card>

      <b-card class="custom-card">

        <div class="card-img-top">
          <img src="@/assets/dog-64.png" alt="Picture 1">
          <img src="@/assets/yoga-50.png" alt="Picture 2">
        </div>

        <div class="card-body">
          <h5 class="card-title">DOG YOGA</h5>

        </div>
      </b-card>

      <b-card class="custom-card">

        <div class="card-img-top">
          <img src="@/assets/goat-64.png" alt="Picture 1">
          <img src="@/assets/yoga-50.png" alt="Picture 2">
        </div>

        <div class="card-body">
          <h5 class="card-title">GOAT YOGA</h5>

        </div>
      </b-card>

      <b-card class="custom-card">

        <div class="card-img-top">
          <img src="@/assets/dog-64.png" alt="Picture 1">
          <img src="@/assets/yoga-50.png" alt="Picture 2">
        </div>

        <div class="card-body">
          <h5 class="card-title">DOG YOGA</h5>

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
      primary: true,
      secondary: false,
    }
  }
}

</script>
<style scoped>
.gradient-background {
  background: linear-gradient(to bottom, #5F8DC4, #82B2EA, #91B2DA);
  height: 100vh; 
  overflow: auto; 
}

.custom-card {
  background: transparent;
  border: 1px solid #fff;
  flex: 0 0 224px; 
  height: 153px;
  margin: 20px;
  border-radius: 32px;
  position: relative;
  transition: transform 0.3s ease, box-shadow 0.3s ease; 
}

.custom-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 0 20px rgba(255, 255, 255, 0.5);
}

.custom-card .card-img-top img {
  transition: transform 0.3s ease; 
}

.custom-card:hover .card-img-top img:nth-child(odd) {
  transform: translateY(-10px); 
}

.custom-card:hover .card-img-top img:nth-child(even) {
  transform: translateY(10px);
}

.custom-card .card-body {
  color: #fff; 
}

.cards-container {
  display: flex; 
  flex-wrap: wrap; 
  margin-right: 20px;
  margin-left: 60px;
}

img {
  transform: scale(1.4); 
  margin-top: 10px;
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
  color: #FFFFFF;
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
  background-color: #BFD3F2;
  color: #FFFFFF;
}

</style>
