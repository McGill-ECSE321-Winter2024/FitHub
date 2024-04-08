<template>
  <div class="solid-background">
    <div class="header-container">
      <h1 class="custom-h1">Propose a new course</h1>
      <h3 class="custom-h3">Propose a new course by inputting the details.</h3>
      <h3 class="custom-h3">Your request will be reviewed by the owner.</h3>
    </div>

    <div class="box">
      <div class="form-box">
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label>Image</label>     
            <input type="text" id="url" v-model="course.url" required>
          </div>
          <div class="form-group">
            <label>Name</label>     
            <input type="text" id="courseName" v-model="course.name" required>
          </div>
          <div class="form-group">
            <label>Category</label>
            <input type="text" id="category" v-model="course.category" required>
          </div>
          <div class="form-group">
            <label>Description</label>
            <textarea v-model="course.description" class="text-field-description"></textarea>
          </div>
          <div class="form-group">
            <label>Difficulty</label>
            <select v-model="course.difficulty" class="text-field">
              <option value="Beginner">Beginner</option>
              <option value="Intermediate">Intermediate</option>
              <option value="Advanced">Advanced</option>
            </select>
          </div>

          <button id="save-btn" type="submit">Send</button>
          <button id="cancel-btn" type="button" @click="cancelForm">Cancel</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      course: {
        name: "",
        category: "",
        description: "",
        difficulty: "",
        status: "",
        priceperhour: 0,
        url: ""
      },
    };
  },
  methods: {
    submitForm() {
      const username = decodeURIComponent(this.$cookies.get('username'));
      const password = this.$cookies.get('password');

      console.log('Username:', username);
      console.log('Password:', password);

      if (username && password) {
        const formData = {
          name: this.course.name,
          category: this.course.category,
          description: this.course.description,
          difficulty: this.course.difficulty,
          status: "Pending",
          pricePerHour: this.course.priceperhour,
          url: this.course.url
        };

        console.log('Form Data:', formData);

        fetch('http://127.0.0.1:8080/courses', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password'))
          },
          credentials: 'include', 
          body: JSON.stringify(formData)
        })
        .then(response => {
          console.log('Response Status:', response.status);
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          console.log('Course created:', data);
          this.resetForm();
        })
        .catch(error => {
          console.error('Error creating course:', error);
        });
      } else {
        console.error('User not authenticated');
      }
    },
    cancelForm() {
      this.resetForm();
    },
    resetForm() {
      this.course = {
        name: "",
        category: "",
        description: "",
        difficulty: "",
        priceperhour: 0,
        url: ""
      };
    },
  },
};
</script>




<style scoped>
.solid-background {
  background-color: var(--color-black);
  height: 100vh;
}

.box{
  display: flex;
  justify-content: center; /* Center content horizontally */
  align-items: center; /* Center content vertically */
}
.custom-h1 {
  color: var(--color-white);
  font-size: 55px;
}

.custom-h3 {
  font-size: 24px;
  font-weight: 700;
  color: var(--color-white);
  
}

.content {
  text-align: center;
}

.text-field-description,
.text-field,
input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 7px;
  background-color: var(--color-black);
  color: #ccc;
  font-family: inherit; /* Inherit font family */
}

.text-field option {
  background-color: var(--color-black);
  color: #ccc;
}

/* Disable default dropdown arrow */
.text-field::-ms-expand {
  display: none;
}

.text-field::-webkit-select-arrow {
  display: none;
}

.text-field::-webkit-scrollbar {
  width: 8px;
}

.text-field::-webkit-scrollbar-track {
  background: var(--color-black);
}

.text-field::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

.text-field:focus,
input[type="text"]:focus,
textarea:focus {
  outline: none;
  border-color: #fff;
}

.form-box {
  width: 600px;
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 10px;
  margin-top: 20px;
}

.form-group-side {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.form-group {
  margin-bottom: 15px;
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
  color: var(--color-black);
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

</style>
