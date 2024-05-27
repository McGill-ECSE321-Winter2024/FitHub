<template>
  <div class="solid-background">
    <div class="box">
      <div class="form-box">
        <div class="title-container">
          <h3 class="custom-h3">Edit course details below</h3>
        </div>
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label>Image</label>     
            <input class="text-field" id="url" v-model="course.url" required>
          </div>
          <div class="form-group flex-container">
            <div class="name">
              <label>Name</label>     
              <input class="text-field" id="courseName" v-model="course.name" required>
            </div>
            <div class="category">
              <label>Category</label>
              <input class="text-field" id="category" v-model="course.category" required>
            </div>
          </div>
          <div class="form-group">
            <label>Description</label>
            <textarea v-model="course.description" class="text-field-description"></textarea>
          </div>
          <div class="form-group flex-container">
            <div class="difficulty">
              <label>Difficulty</label>
              <select v-model="course.difficulty" class="text-field">
                <option value="Beginner">Beginner</option>
                <option value="Intermediate">Intermediate</option>
                <option value="Advanced">Advanced</option>
              </select>
            </div>
            <div class="price">
              <label>Price per hour</label>
              <input class="text-field" id="priceperhour" v-model="course.pricePerHour" required>
            </div>
          </div>
          <div class="buttons">
            <button id="save-btn" type="submit">Save</button>
            <button id="cancel-btn" type="button" @click="cancelForm">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    course: Object 
  },
  methods: {
    submitForm() {
      const username = decodeURIComponent(this.$cookies.get('username'));
      const password = this.$cookies.get('password');

      console.log('Username:', username);
      console.log('Password:', password);

      if (username && password) {
        const courseId = this.course.id; // Assuming you have access to the course ID
        const formData = {
          id: courseId,
          name: this.course.name,
          category: this.course.category,
          description: this.course.description,
          difficulty: this.course.difficulty,
          status: this.course.status,
          pricePerHour: this.course.pricePerHour,
          url: this.course.url,
          center: this.course.center
        };

        console.log('Form Data:', formData);
        console.log(this.course.pricePerHour);

        fetch(`http://127.0.0.1:8080/courses/${courseId}`, { // Use PUT method and include the course ID in the URL
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(username + ':' + password)
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
          console.log('Course updated:', data);
          this.$emit('close'); // Emit a close event
          
        })
        .catch(error => {
          console.error('Error updating course:', error);
        });
      } else {
        console.error('User not authenticated');
      }
    },
    cancelForm() {
      this.$emit('close'); // Emit a close event
    }
  }
};
</script>


<style scoped>
.solid-background {
  background-color: transparent;
  height: 100vh;
}

.box {
  display: flex;
  justify-content: center; /* Center content horizontally */
  align-items: center; /* Center content vertically */
}

.custom-h3 {
  font-size: 24px;
  font-weight: 700;
  color: #FFFFFF;
}

.form-box {
  width: 700px;
  padding: 50px;
  border: 2px solid var(--color-muted-muted-black);
  border-radius: 20px;
  margin-top: 20px;
  background-color: var(--color-black);
}

.form-group {
  margin-bottom: 15px;
}

.title-container {
  text-align: center;
  margin-bottom: 20px;
}


label {
  text-align: left;
  display: block;
  margin-bottom: 5px;
  color: #ffffff;
  font-size: 18px;
  font-weight: 500;
}

.text-field,
.text-field-description {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
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

.text-field-description {
  height: 100px;
}

.text-field:focus,
.text-field:hover,
.text-field-description:focus,
.text-field-description:hover {
  outline: none;
  border-color: #fff;
}

.text-field:focus,
.text-field-description:focus {
  border-width: 2px;
}

.buttons {
  display: flex;
  justify-content: center; /* Center content horizontally */
}

#save-btn {
  background-color: #CDF563;
}

#cancel-btn {
  margin-left: 75px; 
  background-color: #EC5545;
}

button {
  padding: 10px 10px;
  color: var(--color-black);
  border: none;
  border-radius: 30px;
  cursor: pointer;
  font-size: 18px;
  font-weight: 700;
  width: 100px; 
  height: 50px; 
  margin-bottom: 15px;
}

button:hover {
  background-color: #0056b3;
}

.flex-container {
  display: flex;
  justify-content: space-between;
  align-items: center; /* Center content vertically */
}

.name,
.category,
.difficulty,
.price {
  width: 48%; /* Adjust width as needed */
}
</style>