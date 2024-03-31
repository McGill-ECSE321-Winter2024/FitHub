<template>
  <div class="gradient-background">
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
}

.custom-card .card-body {
  color: #fff; 
}

.cards-container {
  display: flex; 
  flex-wrap: wrap; 
  margin-left: 100px;
  margin-right: 20px;
}

.text-search-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px; 
  margin-top: 50px;
}

.text-content {
  flex: 1; 
  text-align: left; 
  margin-left:100px;
  color: #FFFFFF;
}

.search-field {
  margin-left: 20px;
  margin-right:110px;
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
