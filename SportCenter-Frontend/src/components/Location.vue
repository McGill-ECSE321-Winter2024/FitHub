<template>


<div class="solid-background">

<h2>Manage locations</h2>
<h4>Manage the rooms of your sport center</h4>

<div class="table">
    <table>
      <tr>
        <h3 class="custom-h3-Create">Create a location</h3>
      </tr>
      <tr>
          <td>
              <input type="number" placeholder="Room" class="text-field" v-model="newLocationRoom">
          </td>
          <td>
              <input type="number" placeholder="Floor" class="text-field" v-model="newLocationFloor">
          </td>
          <td>
              <button @click="createLocation()"  id="create-btn">Create</button>
          </td>
      </tr>
      <tr>
        <span v-if="createErrorLocation" class="error-message">Error: {{createErrorLocation}}</span>
      </tr>
      <tr>
        <h3 class="custom-h3-Delete">Delete a location</h3>
      </tr>
      <tr>
          <td>
              <input type="number" placeholder="Room" class="text-field" v-model="toDeleteLocationRoom">
          </td>
          <td>
              <input type="number" placeholder="Floor" class="text-field" v-model="toDeleteLocationFloor">
          </td>
          <td>
              <button  @click="deleteLocation()" id="delete-btn">Delete</button>
          </td>
      </tr>
      <tr>
        <span v-if="createErrorLocation" class="error-message">Error: {{createErrorLocation}}</span>
      </tr>
    </table>
  </div>
  <h3>Current Locations</h3>
        <table class="table">
            <tbody>
                <tr>
                    <th>Room</th>
                    <th>Floor</th>
                </tr>
                <tr v-for="l in locations.locations":key="l.id">
                    <td>{{ l.room }}</td>
                    <td>{{ l.floor }}</td>
                </tr>
            </tbody>
        </table>

</div>
</template>

<script>
import axios from 'axios'
import config from '../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const client = axios.create({
    // IMPORTANT: baseURL, not baseUrl
    baseURL: config.dev.backendBaseUrl
});

export default {
    name: "Locations",
    data() {
        return {
            locations: [],
            newLocationRoom: null,
            newLocationFloor: null,
            toDeleteLocationRoom: null,
            toDeleteLocationFloor: null,
            createErrorLocation: '',
            deleteErrorLocation: ''
        };
    },
    mounted() {
        // Fetch location data when the component is created
        this.getAllLocations();
    },
    methods: {
        getAllLocations() {
            const requestOptions = {
              method: 'GET',
              credentials: 'include'
            };
      
            fetch('http://127.0.0.1:8080/public/locations', requestOptions)
              .then(response => {
                if (!response.ok) {
                  throw new Error('Network response was not ok');
                }
                return response.json();
              })
              .then(data => {
                this.locations = data;
                
              })
              .catch(error => {
                console.error('Error fetching locations:', error);
              });
        },
        async createLocation() {
            
            const username = decodeURIComponent(this.$cookies.get('username'));
            const password = this.$cookies.get('password');

            const headers = new Headers();
            headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));
            headers.append('Content-Type', 'application/json');

            const newLocation = {
                floor: this.newLocationFloor,
                room: this.newLocationRoom
                }

            const requestOptions = {
              method: 'POST',
              credentials: 'include',
              body: JSON.stringify(newLocation),
              headers: headers
            };

            console.log("The floor is " + newLocation.floor);
            console.log("The room is " + this.newLocationRoom);
      
            fetch('http://127.0.0.1:8080/locations', requestOptions)
              .then(response => {
                if (!response.ok) {
                  console.log(response);
                  throw new Error('Network response was not ok');
                }
                return response.json();
              })
              .then(data => {
                // Automatically refresh the data after creating a new location
                this.getAllLocations();
                this.toDeleteLocationRoom =null;
                this.toDeleteLocationFloor = null;
                
              })
              .catch(error => {
                console.error('Error creating locations:', error);
              });
            
        },
        async deleteLocation() {

            const username = decodeURIComponent(this.$cookies.get('username'));
            const password = this.$cookies.get('password');

            const headers = new Headers();
            headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));
            headers.append('Content-Type', 'application/json');

            const toDeleteLocation = {
                floor: this.toDeleteLocationFloor,
                room: this.toDeleteLocationRoom
            }

            const requestOptions = {
            method: 'DELETE',
            credentials: 'include',
            body: JSON.stringify(toDeleteLocation),
            headers: headers
            };
        

            fetch('http://127.0.0.1:8080/locations', requestOptions)
            .then(response => {
                console.log("Attempting to delete")
                if (!response.ok) {
                console.log(response);
                throw new Error('Network response was not ok');
                }
                console.log(response);
            })
            .then(data => {
                // Automatically refresh the data after creating a new location
                this.getAllLocations();
                console.log(data);
                this.newLocationRoom = null; // Clear the input field
                this.newLocationFloor = null; // Clear the input field
            })
            .catch(error => {
                console.error('Error creating locations:', error);
            });

            }
    },
    computed: {
        isCreateBtnDisabled() {
            return (
                !this.newLocationFloor|| !this.newLocationRoom
            );
        }
    }
};
</script>

<style scoped>


.solid-background {
  background-color: #121212;
  overflow: auto;
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

h2 {
  color: #ffffff; 
  font-size: 2rem;
  padding: 30px;
  padding-left: 0px;
}

h3 {
  display: flex;
  justify-content: center;
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
#error-message {
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