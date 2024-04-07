import axios from "axios";
import config from "../../config";


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
            errorLocation: ''
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
                console.log(this.locations)
              })
              .catch(error => {
                console.error('Error fetching locations:', error);
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

