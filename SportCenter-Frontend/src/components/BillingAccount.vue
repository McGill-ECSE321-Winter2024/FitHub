<template>

<div class="solid-background">

<div class="text-content">
    <h1 class="custom-h1">Billing account</h1>
    <h3>Manage your payment details for one-time purchase</h3>
</div>

<div class="form-box">
    <h3>Enter card details below</h3>
     <form>
      <div class="form-group">
        <label for="Card Number">Card Number</label>     
        <input type="text" id="cardNumber" v-model="cardNumber" >
      </div>
      <div class="form-group">
        <label for="expirationDate">Expiry Date</label>
        <input type="text" id="expirationDate" v-model="expirationDate"  placeholder=" YYYY-MM-DD">
      </div>
      <div class="form-group">
        <label for="cvv">Security Code (CVV)</label>
        <input type="text" id="cvv" v-model="cvv" required>
      </div>
      <div class="form-group">
        <label for="billingAddress">Billing Address</label>
        <input type="text" id="billingAddress" v-model="billingAddress" >
      </div>
      <div class="form-group">
        <label for="cardHolder">Card Holder</label>
        <input type="text" id="cardHolder" v-model="cardHolder" >
      </div>
      <div class="form-group-side">
        <input type="checkbox" id="isDefault" v-model="isDefault" style="transform: scale(1.5);">
        <label for="isDefault">Save as default </label>
      </div>
      <button id="save-btn" type="save" @click="create" >Save</button>
      <button id="cancel-btn" type="cancel" @click="cancel">Cancel</button>
    </form>
    </div>
    <!-- Display error message -->
    <p class="error" :class="{ 'hidden': !showErrorMessage }">{{ errorMessage }}</p>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  
  data() {
    return {
        cardNumber: '',
        expirationDate: '',
        cvv: '',
        billingAddress: '',
        cardHolder: '',
        isDefault: false,
        errorMessage: 'Invalid input(s)',
        showErrorMessage: false
    };
  },
  methods: {


    
    async create() {
    
        // Create a JSON object with the data to be sent in the POST request
        const requestBody = {
        cardNumber: this.cardNumber,
        expirationDate: this.expirationDate,
        cvv: this.cvv,
        billingAddress: this.billingAddress,
        cardHolder: this.cardHolder,
        isDefault: this.isDefault
        };

        console.log("The card is " + requestBody.cardNumber);
        console.log("The expiry is " + requestBody.expirationDate);
        console.log("The cvv is " + this.cvv);
        console.log("The billing address is " + requestBody.billingAddress);
        console.log("The card holder is " + this.cardHolder);
        console.log("The isDefault is " + requestBody.isDefault);
        

        fetch('http://localhost:8080/customers/' + this.$cookies.get('id') + '/billing-accounts', {
            method: 'POST',
            body: JSON.stringify(requestBody),
            //headers: headers,     
            headers: {
                'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password')),
                'Content-Type': 'application/json'
            },
            credentials: 'include', // Ensure cookies are sent with the request,
            mode: "cors",
        })  .then(response => response.text())
            .then(result => {
                console.log(result);
                result = JSON.parse(result);
                if (result.error == "") {     
                    
                }
                else {
                  this.errorMessage = result.error;
                  this.showErrorMessage = true;
                }
            })
            .catch(error => {
                console.error('Error creating billing account:', error);
                this.errorMessage = 'Error creating billing account: ' + error.message;
                this.showErrorMessage = true;
            });

    },
      // Here you can handle form submission, for example, sending data to a server
      //this.successMessage = 'Added card successfully';

  cancel(){
    // Clear all input fields
      this.cardNumber = '';
      this.expirationDate = '';
      this.cvv = '';
      this.billingAddress = '';
      this.cardHolder = '';
      this.isDefault = false;
      this.errorMessage = ''
  }
  }
};
</script>

<style scoped>

.solid-background {
  background-color: var(--color-black);
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

.form-group {
  margin-bottom: 15px;
}

input[type="text"] {
  background-color: var(--color-black);
  width: 100%;
  border: 1px solid #ccc;
  color: #ccc;
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

h3 {
  margin-bottom: 50px;
  font-size: 24px;
  font-weight: 700;
  color: #FFFFFF;
}

.error-message {
  color: #EC5545;
  font-size: 10px;
  font-weight: 100;
  border-color: #CDF563;
}

</style>