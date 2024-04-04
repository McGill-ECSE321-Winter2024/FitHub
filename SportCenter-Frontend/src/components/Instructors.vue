<template>
    <div id="instructor_page" class="container-fluid p-0 m-0">
        <div class="row align-items-start justify-content-start p-0 m-0">
            <Toolbar />
            <div class="container">
                <div class="row">
                    <h2>Meet our Instructors</h2>
                </div>
                <div class="row">
                    <div class="instructor m-2 p-5" v-for="instructor in instructors"  :key="instructor.id">
                        <img v-bind:src=instructor.imageURL @error="$event.target.src = defaultImage":style="{ 'width': 'auto', 'height': '100px' }" />
                        <ul class="m-0 p-0">
                            <li>{{ instructor.name }}</li>
                            <li>{{ instructor.email }}</li>
                            <li>Pronouns</li>
                            <li>Courses list</li>
                        </ul>
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
            instructors: [],
            defaultImage: require('@/assets/pfp.png')
        };
    },
    mounted() {
        // Fetch accounts data when the component is created
        this.fetchInstructors();
    },
    methods: {
        fetchInstructors() {
            fetch('http://localhost:8080/public/instructors', {
                method: 'GET',
                credentials: 'include', // Ensure cookies are sent with the request,
            }).then((accountsResponse) => {
                if(accountsResponse.status === 204) {
                    console.log("No instructors in the database");
                }
                else {
                    accountsResponse.json().then(accounts => {
                        console.log(accounts.accounts);
                        this.instructors = accounts.accounts;
                    }).catch(error => {
                        console.error('Error parsing JSON:', error);
                    });
                }
            }).catch(error => {
                console.error('Error fetching accounts:', error);
            });
        }
    }
};
</script>

<style>
    #instructor_page {
        min-height: 100vh;
        background-color: #a4c9d8;

        color: white;
    }

    .instructor {
        background: transparent;
        color: white;
        border-color: rgba(0, 0, 0, 0.2);
        border-style: solid;
        border-width: 2px;
    }

    ul {
        list-style-type: none;
    }

    h2 {
        color: #171313;
    }

</style>