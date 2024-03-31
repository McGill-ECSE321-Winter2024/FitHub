<template>
    <div id="instructor-page" class="container-fluid">
        <div class="row">
            <div>
                <h2>Meet our Instructors</h2>
            </div>
        </div>
        <div class="row">
            <div class="container">
                <div class="row">
                    <ul v-for="instructor in instructors"  :key="instructor.id">
                        <img class="instructor_pfp rounded" v-bind:src=instructor.imageURL">
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
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background-image: linear-gradient(#5078a8, #7394BC, #9bb0c9);
        font-family: "Rubik", sans-serif;
    }

    .instructor_pfp {
        width: 200px;
        height: auto;
    }

</style>