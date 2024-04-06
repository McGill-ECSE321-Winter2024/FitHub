<template>

<div class="solid-background">

<div class="text-content">
    <h1 class="custom-h1">Billing account</h1>
    <h3>Manage your payment details for one-time purchase</h3>
</div>

<div class="form-box">
    <h3>Enter card details below</h3>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="Card Number">Card Number</label>     
        <input type="text" id="cardNumber" v-model="formData.cardNumber" required>
      </div>
      <form-row>
      <div class="form-group">
        <label for="expiryDate">Expiry Date</label>
        <input type="text" id="expiryDate" v-model="formData.expiryDate" required placeholder=" YYYY-MM-DD">
      </div>
      <div class="form-group">
        <label for="cvv">Security Code (CVV)</label>
        <input type="text" id="cvv" v-model="formData.cvv" required>
      </div>
      </form-row>
      <div class="form-group">
        <label for="billingAddress">Billing Address</label>
        <input type="text" id="billingAddress" v-model="formData.billingAddress" required>
      </div>
      <div class="form-group">
        <label for="cardHolder">Card Holder</label>
        <input type="text" id="cardHolder" v-model="formData.cardHolder" required>
      </div>
      <div class="form-group-side">
        <input type="checkbox" id="isDefault" v-model="formData.isDefault" style="transform: scale(1.5);">
        <label for="isDefault">Save as default </label>
      </div>
      <button id="save-btn" type="save">Save</button>
      <button id="cancel-btn" type="cancel">Cancel</button>
    </form>

</div>
</div>
</template>

<script>

export default {
  data() {
    return {
      formData: {
        cardNumber: '',
        expiryDate: '',
        cvv: '',
        billingAddress: '',
        cardHolder: '',
        isDefault: false
      }
    };
  },
  methods: {
    submitForm() {
      // Here you can handle form submission, for example, sending data to a server
      console.log('Form submitted:', this.formData);
      // Reset the form after submission
      this.resetForm();
    },
    resetForm() {
      // Reset form data
      this.formData = {
        cardNumber: '',
        expiryDate: '',
        cvv: '',
        billingAddress: '',
        cardHolder: ''
      };
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
  border-radius: 10px;
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
  border-radius: 7px;
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

h3 {
  margin-bottom: 50px;
  font-size: 24px;
  font-weight: 700;
  color: #FFFFFF;
}

</style>