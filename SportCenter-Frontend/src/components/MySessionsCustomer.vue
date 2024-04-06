<template>

    <div class="solid-background">
    
        <div class="text-content">
            <h1 class="custom-h1">My Sessions</h1>
            <h3>Manage your payment details for one-time purchase</h3>
        </div>

    <div class="cont">
      <div class="mt-5">
        <div class="row">
         
        </div>
      </div>
    </div>
        
    </div>
   
</template>

<script>
//import Datepicker from 'vue-datepicker';
export default {
    names:sessions,
    data() {
    return {
      list: [],
    }
  },
  mounted() {
    this.getAllSessions();
  },
  methods: {
    getAllSessions() {
      const requestOptions = {
        method: 'GET',
        credentials: 'include'
      };

      fetch('http://127.0.0.1:8080/public/sessions', requestOptions)
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
          console.error('Error fetching sessions:', error);
        });
    },
    submitForm() {
      // Here you can handle form submission, for example, sending data to a server
      console.log('Form submitted:', this.formData);
      // Reset the form after submission
      this.resetForm();
    },
    resetForm() {

    }
  }
};
</script>

<style scoped>
.solid-background {
  background-color: #121212;
  height: 100vh;
  width: 100vw;
  overflow: auto;
}
.custom-h1 {
  margin-top: 300px;
  margin-left: 580px; 
  color: #ffffff;
  font-size: 45px;
}
.form-box {
  margin-top: 50px;
  width: 700px;
  margin: auto;
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 5px;
}
.form-group-side {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}
.form-row {
  display: flex;
  justify-content: space-between;
}
.form-group {
  margin-bottom: 15px;
}
input[type="text"] {
  background-color: #121212;
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 5px;
}
input[type="checkbox"] {
    align-self: left;
    align-items: left;
    margin-right: 5px;
}
label {
  text-align: left;
  display: block;
  margin-bottom: 5px;
  color: #ffffff;
  font-size: 18px;
  font-weight: 500;
}
button {
  padding: 10px 10px;
  color: #121212;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 18px;
  font-weight: 500;
  width: 100px; 
  height: 50px; 
  margin-bottom: 15px;
}
#save-btn {
    background-color: #CDF563;
}
#cancel-btn {
    margin-left: 75px; 
    background-color: #EC5545;
}
button:hover {
  background-color: #0056b3;
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