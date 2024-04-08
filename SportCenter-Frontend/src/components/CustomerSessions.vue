<template>
  <div>
    <h2 style="color: #ffffff; font-size: 35px">My sessions</h2>
    <h4 style="color: #ffffff; font-size: 20px; margin-bottom: 50px">
      Manage the sessions to which you are registered.
    </h4>
    <!-- Properties and text fields -->
    <div style="color: #ffffff">
      <div style="margin-top: 20px; margin-bottom: 10px">
        <div class="box">
          <div class="text-column">
            <div style="font-size: 25px; font-weight: bold">Goat yoga</div>
            <div style="font-size: 18px">04/06/24, 10:00-12:00</div>
          </div>
          <div class="button-column">
            <div class="button">Book Now</div>
          </div>
        </div>
        <div style="margin-top: 20px; margin-bottom: 10px">
          <div class="box">
            <div class="text-column">
              <div style="font-size: 25px; font-weight: bold">
                Jiu-jitsu 101
              </div>
              <div style="font-size: 18px">04/07/24, 12:00-14:00</div>
            </div>
            <div class="button-column">
              <div class="button">Book Now</div>
            </div>
          </div>
        </div>
        <div style="margin-top: 20px; margin-bottom: 50px">
          <div class="box">
            <div class="text-column">
              <div style="font-size: 25px; font-weight: bold">Badminton</div>
              <div style="font-size: 18px">04/08/24, 10:00-11:00</div>
            </div>
            <div class="button-column">
              <div class="button">Book Now</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return { profile: { name: "", email: "", password: "" } };
  },
};
</script>

<style>
.box {
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 90%;
  height: 100px;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #444;
  background-color: transparent;
  color: #ffffff;
  box-sizing: border-box;
  margin-top: 4px;
}

.text-column {
  flex: 1;
}

.button-column {
  flex-shrink: 0;
}

.button {
  background-color: transparent;
  color: red;
  font-weight: bold;
  font-size: 20px;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.button:hover {
  color: darkred;
}
</style>
