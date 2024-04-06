<template>


<div class="solid-background">

<div class="text-content">
    <h1 class="custom-h1">My Locations</h1>
    <h4>Manage the rooms of your sport center</h4>
</div>

<div class="table">
    <table>
      <tr>
        <h3 class="custom-h3-Create">Create a location</h3>
      </tr>
      <tr>
          <td>
              <input type="text" placeholder="Room" class="text-field">
          </td>
          <td>
              <input type="text" placeholder="Floor" class="text-field">
          </td>
          <td>
              <button id="create-btn">Create</button>
          </td>
      </tr>
      <tr>
        <span style="color:red">Error: Message text comes here</span>
      </tr>
      <tr>
        <h3 class="custom-h3-Delete">Delete a location</h3>
      </tr>
      <tr>
          <td>
              <input type="text" placeholder="Room" class="text-field">
          </td>
          <td>
              <input type="text" placeholder="Floor" class="text-field">
          </td>
          <td>
              <button id="delete-btn">Delete</button>
          </td>
      </tr>
      <tr>
        <span style="color:red">Error: Message text comes here</span>
      </tr>
    </table>
    <p>
      
    </p>
  </div>

</div>
</template>

<script>

</script>

<style scoped>


.solid-background {
  background-color: #121212;
  height: 100vh;
  width: 100vw;
  overflow: auto;
}

.custom-h1 {
  display: flex;
  justify-content: center;
  margin-top: 200px; 
  color: #ffffff;
  font-size: 45px;
}


.custom-h3-Create {
  margin-bottom: 10px;
  font-size: 24px;
  font-weight: 700;
  color: #CDF563;
}

.custom-h3-Delete {
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 24px;
  font-weight: 700;
  color: #F56363;
}

.table {
  display: flex;
  justify-content: center;
}
h3 {
  margin-bottom: 50px;
  font-size: 24px;
  font-weight: 700;
  color: #FFFFFF;
}

h4 {
  margin-bottom: 50px;
  font-size: 20px;
  font-weight: 500;
  color: #FFFFFF;
}

.text-field {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #444; /* Set initial border color to gray */
  background-color: transparent;
  color: #ffffff;
  box-sizing: border-box;
  transition: border-color 0.1s ease-in-out, font-weight 0.1s ease-in-out,
    border-width 0.1s ease-in-out; /* Add transition effect */
  font-weight: normal; /* Set default font weight */
  border-width: 1px; /* Set initial border width */
}

#create-btn {
    background-color: #CDF563;
}
#delete-btn {
    background-color: #F56363;
}




button {
  padding: 10px 10px;
  color: #121212;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 18px;
  font-weight: 500;
  width: 80px; 
  height: 40px; 
  margin-bottom: 20px;
}

</style>