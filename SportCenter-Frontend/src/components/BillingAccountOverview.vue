<template>

    <div class="solid-background">
    
    <div class="text-content">
        <h1 class="custom-h1">Billing account overview</h1>
        <h3>Manage your payment details for one-time purchase</h3>
    </div>
    

    <div class="card-box">
        <h5 class="card-display">**** 0000 | YYYY-MM</h5>
        <router-link to="/billing-account" class="add-link">Add new card</router-link>
    </div>

    <div class="cards">
        <h2>My cards</h2>
    
        <h6>Default card</h6>
        <div class="card-box">
          <h5 class="card-display">{{ defaultCard.cardNumber ? '**** ' + defaultCard.cardNumber.slice(-4) : '' }} | {{ defaultCard.expirationDate ? defaultCard.expirationDate.slice(0, 7) : '' }}</h5>
        <router-link to="/billing-account-edit" class="edit-link">Edit</router-link>
        <button @click="confirmDelete(defaultCard.id)" class="delete-link">Delete</button>
        </div>

        <div class="other-cards">
        <h6>Other cards</h6>
        <div v-for="account in billingAccounts.billingAccounts" :key="account.id" class="card-box">
        <h5 class="card-display">
          {{ account.cardNumber ? '**** ' + account.cardNumber.slice(-4) : '' }} | {{ account.expirationDate ? account.expirationDate.slice(0, 7) : '' }}
        </h5>
        <router-link to="/billing-account-edit" class="edit-link">Edit</router-link>
        <button @click="confirmDelete(account.id)" class="delete-link">Delete</button>
        </div>
        </div>
    </div>

    <div class="popup-dialog" v-if="showDeleteConfirmation">
      <div class="popup-content">
        <p>Are you sure you want to delete this card?</p>
        <button @click="cancelDelete" class="cancel-btn">Cancel</button>
        <button @click="deleteAccount" class="confirm-btn">Delete</button>
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
          showDeleteConfirmation: false 
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
        this.getOtherBillingAccounts();
      })
      .catch(error => {
        console.error('Error deleting account:', error);
      })
      .finally(() => {
        this.deleteAccountId = null;
        this.showDeleteConfirmation = false; // Hide the pop-up dialog
      });
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
      margin-left: 480px; 
      color: #ffffff;
      font-size: 45px;
    }

    .card-box {
      width: 700px;
      margin: 20px auto;
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
    }
    
    button:hover {
      background-color: #121a22;
    }
    
    h2 {
      text-align: left;
      margin-left: 400px;
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
      font-size: 24px;
      font-weight: 700;
      color: #FFFFFF;
    }
    
    h6 {
      margin-left: 20px;
      text-align: left;
      display: flex;
      margin-left: 400px;
      margin-top: 20px;
      margin-bottom: 5px;
      font-size: 20px;
      font-weight: 600;
      color: #FFFFFF;
    }

  .popup-dialog {
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

</style>