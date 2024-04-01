<template>
    <div id="instructor-page" class="container-fluid">
        <Toolbar />
        <div class="row">
            <div>
                <h2>Meet our Instructors</h2>
            </div>
        </div>
        <div class="row">
            <div class="container">
                <div class="row">
                    <ul v-for="instructor in instructors"  :key="instructor.id">
                        <img v-bind:src=instructor.imageURL @error="$event.target.src = defaultImage":style="{ 'border-radius': '50%', 'width': 'auto', 'height': '100px' }" />
                        <li>{{ instructor.name }}</li>
                        <li>{{ instructor.email }}</li>
                        <li>Pronouns</li>
                        <li>Courses list</li>
                    </ul>
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
    #instructor-page {
        min-height: 100vh;
    }

    ul {
        list-style-type: none;
    }
</style>