<template>

<div class="solid-background">
<div>
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
      <form-row>
      <div class="form-group">
        <label>Category</label>
        <input type="text" id="category" v-model="course.category" required>
      </div>
      <div class="form-group">
        <label>Description</label>
        <textarea v-model="course.description" class="text-field-description" ></textarea>
      </div>
      </form-row>

      <div class="form-group">
        <label>Difficulty</label>
            <select v-model="course.difficulty" class="text-field">
              <option value="Beginner">Beginner</option>
              <option value="Intermediate">Intermediate</option>
              <option value="Advanced">Advanced</option>
            </select>
      </div>

      <button id="save-btn" type="save">Send</button>
      <button id="cancel-btn" type="cancel">Cancel</button>
    </form>

</div>
</div>
<p v-if="successMessage">{{ successMessage }}</p>
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
        url: ""
      },
    };
  },
  methods: {
      // Here you can handle form submission, for example, sending data to a server
      //this.successMessage = 'Added card successfully';
      //console.log('Form submitted:', this.formData);
    submitForm() {
      fetch('http://127.0.0.1:8080/customers/{cId}/billing-accounts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.formData),
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then((data) => {
          this.successMessage = data.message; // Assuming your backend returns a success message
          console.log('Form submitted:', data);
        })
        .catch((error) => {
          console.error('Error submitting form:', error);
          // Handle error
        });

    },
    cancelForm() {

      return{
        formData: {
        cardNumber: '',
        expiryDate: '',
        cvv: '',
        billingAddress: '',
        cardHolder: '',
        isDefault: false,
        successMessage: '' 
        }
      };
    },
  }
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

.form-row {
  display: flex;
  justify-content: space-between;
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


</style>c