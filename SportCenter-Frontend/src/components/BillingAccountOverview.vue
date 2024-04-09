<template>

<div class="solid-background" :class="{ 'blur-background': showDeleteConfirmation || showEditConfirmation || showAddConfirmation }">
    
    <div class="text-content">
        <h1 class="custom-h1">Billing account overview</h1>
        <h3>Manage your payment details for one-time purchase</h3>
    </div>
    

    <div class="card-box">
        <h5 class="card-display">**** 0000 | YYYY-MM</h5>
        <button @click="showAddPopup()" class="add-link">Add new card</button>
    </div>

    <div class="cards">
        <h2>My cards</h2>
    
        <h6>Default card</h6>
        <div class="default-cards"v-if="defaultCard && Object.keys(defaultCard).length > 0">
        <div class="card-box">
          <h5 class="card-display">{{ defaultCard.cardNumber ? '**** ' + defaultCard.cardNumber.slice(-4) : '' }} | {{ defaultCard.expirationDate ? defaultCard.expirationDate.slice(0, 7) : '' }}</h5>
          <button @click="showEditPopup(defaultCard)" class="edit-link">Edit</button>
        <button @click="confirmDelete(defaultCard.id)" class="delete-link">Delete</button>
        </div>
        </div>
        <div v-else>
          <p class="no-data">No card saved as default</p>
        </div>
        

        <h6>Other cards</h6>
        <div class="other-cards" v-if="billingAccounts && billingAccounts.billingAccounts && billingAccounts.billingAccounts.length > 0">
        <div v-for="account in billingAccounts.billingAccounts" :key="account.id" class="card-box">
        <h5 class="card-display">
          {{ account.cardNumber ? '**** ' + account.cardNumber.slice(-4) : '' }} | {{ account.expirationDate ? account.expirationDate.slice(0, 7) : '' }}
        </h5>
        <button @click="showEditPopup(account)" class="edit-link">Edit</button>
        <button @click="confirmDelete(account.id)" class="delete-link">Delete</button>
        </div>
        </div>
        <div v-else>
        <p class="no-data">No other cards added</p>
        </div>
    </div>

    <div class="popup-delete" v-if="showDeleteConfirmation">
      <div class="popup-content">
        <p>Are you sure you want to delete this card?</p>
        <button @click="cancelDelete" class="cancel-btn">Cancel</button>
        <button @click="deleteAccount" class="confirm-btn">Delete</button>
      </div>
    </div>

    <div class="popup-edit" v-if="showEditConfirmation">
      <div class="popup-content">
        <div class="form-box">
        <h7>Edit card details below</h7>
         <form>
          <div class="form-group">
            <label for="Card Number">Card Number</label>     
            <input type="text" id="cardNumber" v-model="editedAccount.cardNumber" >
          </div>
          <div class="form-group">
            <label for="expirationDate">Expiry Date</label>
            <input type="text" id="expirationDate" v-model="editedAccount.expirationDate">
          </div>
          <div class="form-group">
            <label for="cvv">Security Code (CVV)</label>
            <input type="text" id="cvv" v-model="editedAccount.cvv">
          </div>
          <div class="form-group">
            <label for="billingAddress">Billing Address</label>
            <input type="text" id="billingAddress" v-model="editedAccount.billingAddress" >
          </div>
          <div class="form-group">
            <label for="cardHolder">Card Holder</label>
            <input type="text" id="cardHolder" v-model="editedAccount.cardHolder" >
          </div>
          <div class="form-group-side">
            <input type="checkbox" id="isDefault" v-model="editedAccount.isDefault" style="transform: scale(1.5);">
            <label for="isDefault" style="margin-left: 10px;">Save as default </label>
          </div>
          <button @click="cancelEdit" class="cancel-edit-btn">Cancel</button>
          <button @click="saveEdit" class="save-btn">Save</button>
        </form>
        </div>
      </div>
    </div>

    <div class="popup-edit" v-if="showAddConfirmation">
      <div class="popup-content">
        <div class="form-box">
        <h7>Enter card details below</h7>
         <form>
          <div class="form-group">
            <label for="Card Number">Card Number</label>     
            <input type="text" id="cardNumber" v-model="newAccount.cardNumber" >
          </div>
          <div class="form-group">
            <label for="expirationDate">Expiry Date</label>
            <input type="text" id="expirationDate" placeholder=" YYYY-MM-DD" v-model="newAccount.expirationDate">
          </div>
          <div class="form-group">
            <label for="cvv">Security Code (CVV)</label>
            <input type="text" id="cvv" v-model="newAccount.cvv">
          </div>
          <div class="form-group">
            <label for="billingAddress">Billing Address</label>
            <input type="text" id="billingAddress" v-model="newAccount.billingAddress" autocomplete="off" >
          </div>
          <div class="form-group">
            <label for="cardHolder">Card Holder</label>
            <input type="text" id="cardHolder" v-model="newAccount.cardHolder" autocomplete="off">
          </div>
          <div class="form-group-side">
            <input type="checkbox" id="isDefault" v-model="newAccount.isDefault" style="transform: scale(1.5);">
            <label for="isDefault" style="margin-left: 10px;">Save as default </label>
          </div>
          <button @click="cancelAdd" class="cancel-edit-btn">Cancel</button>
          <button @click="saveAdd" class="save-btn">Save</button>
        </form>
        </div>
      </div>
    </div>

    </div>
    </template>
    
    <script>

    import axios from 'axios';

    export default { 

      data() {
        return {
          billingAccounts: [],
          defaultCard: {},
          deleteAccountId: null,
          showDeleteConfirmation: false,
          showEditConfirmation: false,
          editedAccount: null, // Hold the account being edited 
          newAccount: {
            cardNumber: '',
            expirationDate: '',
            cvv: '',
            billingAddress: '',
            cardHolder: '',
            isDefault: false
          },
          showAddConfirmation: false
        };
      },
      mounted() {
       this.getDefaultBillingAccounts();
       this.getOtherBillingAccounts();
    },

    methods : {
    getDefaultBillingAccounts() {
        const requestOptions = {
        method: 'GET',
        credentials: 'include',
        headers: {
                'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password')),
                'Content-Type': 'application/json'
            },
        };

        fetch('http://localhost:8080/customers/' + this.$cookies.get('id') + '/billing-account', requestOptions)
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          this.defaultCard = data;
          console.log(this.defaultCard)
        })
        .catch(error => {
          console.error('Error fetching billing-accounts:', error);
        });
    },

    getOtherBillingAccounts() {
        const requestOptions = {
        method: 'GET',
        credentials: 'include',
        headers: {
                'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password')),
                'Content-Type': 'application/json'
            },
        };

        fetch('http://localhost:8080/customers/' + this.$cookies.get('id') + '/billing-accounts', requestOptions)
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          this.billingAccounts = data;
          console.log(this.billingAccounts)
        })
        .catch(error => {
          console.error('Error fetching billing-accounts:', error);
        });
    },
    confirmDelete(accountId) {
      this.deleteAccountId = accountId;
      this.showDeleteConfirmation = true;
    },
    cancelDelete() {
      this.showDeleteConfirmation = false; // Hide the pop-up dialog
    },
    deleteAccount() {
      // Call the API to delete the account
      axios.delete(`http://localhost:8080/customers/${this.$cookies.get('id')}/billing-accounts/${this.deleteAccountId}`, {
        headers: {
          'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password')),
          'Content-Type': 'application/json'
        }
      })
      .then(response => {
        // Handle success
        console.log('Account deleted successfully');
        this.getDefaultBillingAccounts();
        this.getOtherBillingAccounts();
      })
      .catch(error => {
        console.error('Error deleting account:', error);
      })
      .finally(() => {
        this.deleteAccountId = null;
        this.showDeleteConfirmation = false; // Hide the pop-up dialog
      });
    },
    showEditPopup(account) {
      this.editedAccount = { ...account }; // Copy account data
      this.showEditConfirmation = true; // Show edit popup
    },
    cancelEdit() {
      this.showEditConfirmation = false; // Hide edit popup
      this.editedAccount = null; // Reset edited account
    },
    saveEdit() {

      const editedData = {
      cardNumber: this.editedAccount.cardNumber,
      cardHolder: this.editedAccount.cardHolder,
      billingAddress: this.editedAccount.billingAddress,
      cvv: this.editedAccount.cvv,
      isDefault: this.editedAccount.isDefault,
      expirationDate: this.editedAccount.expirationDate
    };
    console.log("The card is " + cardNumber);
    console.log("The expiry is " + expirationDate);
    console.log("The cvv is " + cvv);
    console.log("The billing address is " + billingAddress);
    console.log("The card holder is " + cardHolder);
    console.log("The isDefault is " + isDefault);
    console.log("The id is " + this.editedAccount.id);
        
    // body: JSON.stringify(requestBody),   

    axios.put(`http://localhost:8080/customers/${this.$cookies.get('id')}/billing-accounts/${this.editedAccount.id}`, editedData, {
      headers: {
        'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password')),
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      // Handle success
      console.log('Account updated successfully');
      this.getDefaultBillingAccounts();
      this.getOtherBillingAccounts();
    })
    .catch(error => {
      console.error('Error updating account:', error);
    })
    .finally(() => {
      this.showEditConfirmation = false; // Hide the edit popup
      this.editedAccount = null; // Reset edited account
    });
    },
    showAddPopup() {
      this.newAccount = {
        cardNumber: '',
        expirationDate: '',
        cvv: '',
        billingAddress: '',
        cardHolder: '',
        isDefault: false
      };

      // Show the add card pop-up
      this.showAddConfirmation = true;
    },
    cancelAdd() {
      this.showAddConfirmation = false; // Hide the add card pop-up
      this.newAccount = {
        cardNumber: '',
        expirationDate: '',
        cvv: '',
        billingAddress: '',
        cardHolder: '',
        isDefault: false
      };
      // Reset edited account
    },
    saveAdd(){
      const requestBody = {
        cardNumber: this.newAccount.cardNumber,
        expirationDate: this.newAccount.expirationDate,
        cvv: this.newAccount.cvv,
        billingAddress: this.newAccount.billingAddress,
        cardHolder: this.newAccount.cardHolder,
        isDefault: this.newAccount.isDefault
      };

      console.log("The card is " + this.newAccount.cardNumber);
      console.log("The expiry is " + this.newAccount.expirationDate);
      console.log("The cvv is " + this.newAccount.cvv);
      console.log("The billing address is " + this.newAccount.billingAddress);
      console.log("The card holder is " + this.newAccount.cardHolder);
      console.log("The isDefault is " + this.newAccount.isDefault);

        

        fetch('http://localhost:8080/customers/' + this.$cookies.get('id') + '/billing-accounts', {
            method: 'POST',
            body: JSON.stringify(requestBody),    
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
                this.successMessage = 'Added card successfully';
                this.getDefaultBillingAccounts();
                this.getOtherBillingAccounts();
                this.showAddConfirmation = false; // Hide the add card pop-up
            })
            .catch(error => {
                console.error('Error creating billing account:', error);
                this.errorMessage = 'Error creating billing account: ' + error.message;
                this.showErrorMessage = true;
            })
            .finally(() => {
                this.showAddConfirmation = false; // Hide the add card pop-up
           });
    },
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
    .blur-background {
    filter: blur(10px); /* Adjust the blur amount as needed */
    pointer-events: none; /* Ensure that the blurred background is not clickable */
  }
    .custom-h1 {
      margin-top: 20px;
      margin-left: 230px; 
      color: #ffffff;
      font-size: 45px;
    }

    .card-box {
      width: 700px;
      margin-left: 130px;
      margin-top: 20px;
      margin-bottom: 20px;
      /*margin: 20px auto;*/
      padding: 20px;
      border: 2px solid #ccc;
      border-radius: 5px;
      display: flex;
      justify-content: space-between;
    }
    
    .cards {
      align-items: center;
    }
    
    label {
      text-align: left;
      display: block;
      margin-bottom: 5px;
      color: #ffffff;
      font-size: 18px;
      font-weight: 500;
    }
    
    .edit-link{
      margin-left: 260px;
      color: #CDF563;
      padding: 10px 10px;
      border-radius: 30px;
      border: none;
      cursor: pointer;
      font-size: 20px;
      font-weight: 700; 
      margin-bottom: 15px;
      padding: 10px 10px;
      background-color: var(--color-black);
    }
    .delete-link{
      padding: 10px 10px;
      border-radius: 30px;
      border: none;
      cursor: pointer;
      font-size: 20px;
      font-weight: 700; 
      margin-bottom: 15px;
        margin-left: 0px; 
        margin-right: 40px;
        color: #EC5545;
        padding: 10px 10px;
      background-color: var(--color-black);
    }
    
    .add-link {
        color: #CDF563;
        margin-right: 40px;
        padding: 10px 10px;
      border-radius: 30px;
      border: none;
      cursor: pointer;
      font-size: 20px;
      font-weight: 700; 
      margin-bottom: 15px;
      background-color: var(--color-black);
    }
    
    h2 {
      text-align: left;
      margin-left: 130px; 
      margin-top: 50px;
      margin-bottom: 20px;
      font-size: 30px;
      font-weight: 700;
      color: #FFFFFF;
    }

    h5 {
      margin-left: 20px;
      text-align: left;
      display: flex;
      align-items: center;
      font-size: 20px;
      font-weight: 600;
      color: #FFFFFF;
    }

    h3 {
      margin-bottom: 50px;
      margin-left: 190px;
      font-size: 24px;
      font-weight: 700;
      color: #FFFFFF;
    }
    
    h6 {
      margin-left: 130px;
      text-align: left;
      display: flex;
      margin-top: 20px;
      margin-bottom: 5px;
      font-size: 20px;
      font-weight: 600;
      color: #FFFFFF;
    }
    h7 {
      margin-bottom: 70px;
      margin-left: 50px;
      font-size: 24px;
      font-weight: 700;
      color: #FFFFFF;
    }

  .popup-delete {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: var(--color-black);
  border: 2px solid #ccc;
  border-radius: 40px;
  padding: 20px;
  z-index: 9999; /* Ensure it appears on top of other elements */
  width: 400px; /* Adjust the width as needed */
  height: 150px; /* Adjust the height as needed */
}

.popup-content {
  text-align: center;
  color: var(--color-white);
}

.popup-content p {
  margin-bottom: 20px;
  margin-top: 10px;
}

.popup-content button {
  padding: 10px 20px;
  margin: 0 10px;
  border: none;
  border-radius: 45px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 400px;
}
.cancel-btn {
  background-color: #CDF563 !important;
}

.confirm-btn {
  background-color: #EC5545 !important;
}

.popup-edit {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: var(--color-black);
  border: 2px solid #ccc;
  border-radius: 40px;
  padding: 20px;
  z-index: 9999; /* Ensure it appears on top of other elements */
  width: 850px; /* Adjust the width as needed */
  height: 600px; /* Adjust the height as needed */
}
.form-box {
  width: 800px;
  height: 600px;
  padding: 20px;
  margin:0px;
}

.form-group-side {
  margin-bottom: 15px;
  margin-left: 10px;
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

label {
  text-align: left;
  display: block;
  margin-bottom: 5px;
  color: #ffffff;
  font-size: 18px;
  font-weight: 500;
}

.save-btn {
  padding: 10px 10px;
  color: var(--color-black);
  border: none;
  border-radius: 20px;
  margin-left: 50px; /* Add margin-left to create space between buttons */
  cursor: pointer;
  font-size: 18px;
  font-weight: 500;
  width: 100px; 
  height: 50px; 
  margin-bottom: 15px;
    background-color: #CDF563;
}

.cancel-edit-btn {
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
    margin-left: 150px; 
    background-color: #EC5545;
}

.popup-edit .cancel-edit-btn {
  margin-right: 20px; /* Add margin-right to create space between buttons */
}

.popup-edit .save-btn {
  margin-left: 20px; /* Add margin-left to create space between buttons */
}

.no-data {
      margin-left: 150px;
      text-align: left;
      display: flex;
      margin-top: 20px;
      margin-bottom: 5px;
      font-size: 15px;
      font-weight: 400;
      color: #FFFFFF;
}

</style>