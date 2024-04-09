<template>
  <div>
    <h2 style="color: #ffffff; font-size: 35px">Manage instructors</h2>
    <div class="container">
      <div class="row" v-for="(boxRow, index) in boxRows" :key="index">
        <div class="col" v-for="(box, boxIndex) in boxRow" :key="boxIndex">
          <div class="box">
            <div style="justify-items: center">
              <div style="font-weight: bold; color: #ffffff">Image</div>
              <input
                type="text"
                v-model="instructor.imageURL"
                class="setting-instructor-field"
              />
            </div>
            <div style="">
              <div style="font-weight: bold; color: #ffffff">Name</div>
              <input
                type="text"
                v-model="instructor.name"
                class="setting-instructor-field"
              />
            </div>
            <div style="">
              <div style="font-weight: bold; color: #ffffff">Email</div>
              <input
                type="text"
                v-model="instructor.email"
                class="setting-instructor-field"
              />
            </div>
            <div style="">
              <div style="font-weight: bold; color: #ffffff">Password</div>
              <input
                type="text"
                v-model="instructor.password"
                class="setting-instructor-field"
              />
            </div>
            <div style="">
              <div style="font-weight: bold; color: #ffffff">Pronouns</div>
              <input
                type="text"
                v-model="instructor.pronouns"
                class="setting-instructor-field"
              />
            </div>
            <button class="save-button" @click="saveBox(index, boxIndex)">
              Save
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      instructor: {
        imageURL: "",
        name: "",
        email: "",
        password: "",
        pronouns: "",
      },
      boxRows: [
        [
          { field1: "", field2: "", field3: "", field4: "", field5: "" },
          { field1: "", field2: "", field3: "", field4: "", field5: "" },
          { field1: "", field2: "", field3: "", field4: "", field5: "" },
        ],
        [{ field1: "", field2: "", field3: "", field4: "", field5: "" }],
      ],
    };
  },
  methods: {
    saveBox(rowIndex, boxIndex) {
      const box = this.boxRows[rowIndex][boxIndex];
      // Here you can perform save operation, for now just log the data
      console.log("Saving box:", box);
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: left;
}

.row {
  display: flex;
}

.col {
  flex: 1;
}

.box {
  border: 1px solid #ccc;
  height: 400px;
  width: 320px;
  border-radius: 8px;
  padding: 10px;
  margin-top: 50px;
  justify-content: center;
  align-items: center;
}

.save-button {
  margin-top: 10px;
  background-color: var(--color-green);
  color: var(--color-black);
  font-weight: bold;
  border: none;
  border-radius: 20px;
  padding: 5px 10px;
  cursor: pointer;
}

.setting-instructor-field {
  width: 90%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #444; /* Set initial border color to gray */
  background-color: transparent;
  color: #ffffff;
  height: 35px;
  box-sizing: border-box;
  margin-top: 2px;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out,
    border-width 0.1s ease-in-out; /* Add transition effect */
  font-weight: normal; /* Set default font weight */
  border-width: 1px; /* Set initial border width */
}

.setting-instructor-field:focus,
.setting-instructor-field:hover {
  outline: none;
  border-color: #fff; /* Change border color to white on hover or focus */
}

.setting-instructor-field:focus {
  border-width: 2px; /* Increase border width on hover or focus */
}

.setting-instructor-field[type="time"]::-webkit-datetime-edit-second-field {
  display: none;
}
</style>
