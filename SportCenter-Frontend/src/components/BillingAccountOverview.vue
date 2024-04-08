<template>

    <div class="solid-background">
    
    <div class="text-content">
        <h1 class="custom-h1">Billing account overview</h1>
        <h3>Manage your payment details for one-time purchase</h3>
    </div>
    

    <div class="card-box">
        <h5 class="card-display">****0000 | YYYY-MM</h5>
        <router-link to="/billing-account" class="add-link">Add new card</router-link>
        <!--button id="add-btn" type="add" @click="add">Add new card</button-->
    </div>

    <div class="cards">
        <h2>My cards</h2>
    
        <h6>Default card</h6>
        <div class="card-box">
        <h5 class="card-display">****8888 | 2026-10</h5>
        <button id="edit-btn" type="edit">Edit</button>
        <button id="remove-btn" type="remove">Delete</button>
        </div>

        <div class="other-cards" v-for="account in billingAccounts":key="account.id">
        <h6>Other cards</h6>
        <div class="card-box">
        <h5 class="card-display">{{ account.cardNumber }} | {{ account.expirationDate }} </h5>
        <button id="edit-btn" type="edit">Edit</button>
        <button id="remove-btn" type="remove">Delete</button>
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
            billingAccount: {}
        };
      },

      mounted() {
        // Fetch location data when the component is created
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
          this.list = data;
          console.log(this.list)
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
          this.list = data;
          console.log(this.list)
        })
        .catch(error => {
          console.error('Error fetching billing-accounts:', error);
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
    
    button {
      padding: 10px 10px;
      background-color: var(--color-black);
      border-radius: 30px;
      border: none;
      cursor: pointer;
      font-size: 20px;
      font-weight: 700; 
      margin-bottom: 15px;
    }
    
    #edit-btn {
        margin-left: 260px;
        color: #CDF563;
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

    #remove-btn {
        margin-left: 0px; 
        margin-right: 40px;
        color: #EC5545;
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
      text-align: left;
      margin-left: 400px;
      margin-top: 20px;
      margin-bottom: 5px;
      font-size: 20px;
      font-weight: 600;
      color: #FFFFFF;
    }
    </style>