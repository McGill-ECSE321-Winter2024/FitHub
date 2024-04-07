<template>
  <div class="solid-background">
    <div>
      <h1 class="custom-h1">Propose a new course</h1>
      <h3 class="custom-h3">Input details for this course request to the owner</h3>
      <!-- Container for sidebar and main content -->
      <div class="content-container">
        <!-- Properties and text fields -->
        <div class="text-fields">
          <div class="text-field-item">
            <label>Image</label>
            <input type="url" v-model="course.image" class="text-field" @change="loadImage" />
            <img v-if="course.image" :src="course.image" alt="Course Image" style="max-width: 100%; margin-top: 10px;" />
          </div>
          <div class="text-field-item">
            <label>Name</label>
            <input type="name" v-model="course.name" class="text-field" />
          </div>
          <div class="text-field-item">
            <label>Category</label>
            <input type="category" v-model="course.category" class="text-field" />
          </div>
          <div class="text-field-item">
            <label>Description</label>
            <textarea v-model="course.description" class="text-field-description" ></textarea>
          </div>
          <div class="text-field-item">
            <label>Difficulty</label>
            <select v-model="course.difficulty" class="text-field">
              <option value="Beginner">Beginner</option>
              <option value="Intermediate">Intermediate</option>
              <option value="Advanced">Advanced</option>
            </select>
          </div>
        </div>
        <div class="buttons">
          <button @click="create(course)" class="approve">Create</button>
          <button @click="cancel()" class="disapprove">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// Import OwnerSportCenterSettings component
import OwnerSportCenterSettings from "./OwnerSportCenterSettings.vue";

export default {
  name: "ProfileSettings",
  components: {
    OwnerSportCenterSettings,
    // Add more components as needed
  },
  data() {
    return {
      course: {
        name: "",
        category: "",
        description: "",
        difficulty: "",
        image: ""
      },
    };
  },
  methods: {
    create(course) {
      // Implement create method
    },
    cancel() {
      // Implement cancel method
    },
    loadImage(event) {
      const input = event.target;
      if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.course.image = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
      }
    }
  }
};
</script>

<style scoped>
.solid-background {
  font-family: Figtree;
  height: 100vh;
  width: 100vw;
  background-color: #121212;
}

.content-container {
  display: flex;
  flex-direction: column;
  justify-content: left;
  align-items: center;
  width: 100%;
}

.text-fields {
  color: #ffffff;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: left;
}

.text-field-item {
  margin-bottom: 10px;
  height: 1000%;
}

.text-field {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #444;
  background-color: transparent;
  color: #ffffff;
  box-sizing: border-box;
  margin-top: 4px;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out, border-width 0.1s ease-in-out;
  font-weight: normal;
  border-width: 1px;
}

.text-field-description {
    width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #444;
  background-color: transparent;
  color: #ffffff;
  box-sizing: border-box;
  margin-top: 4px;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out, border-width 0.1s ease-in-out;
  font-weight: normal;
  border-width: 1px;
}

textarea.text-field {
  height: auto; /* Remove fixed height for textarea */
}

.custom-h3 {
  font-size: 24px;
  font-weight: 700;
  color: #FFFFFF;
  text-align: left;
  margin-left: 60px;
}

.custom-h1 {
  color: #ffffff;
  font-size: 55px;
  text-align: left;
  margin-left: 60px;
}

.approve {
  border: 0px;
  background-color: #CDF563;
  color: #121212;
  font-weight: bold;
  border-radius: 20px;
  height: 40px;
  width: 100px;
}

.disapprove {
  margin-left: 10px;
  border: 0px;
  background-color: #E3240C;
  color: #121212;
  font-weight: bold;
  border-radius: 20px;
  height: 40px;
  width: 100px;
}

.buttons {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
