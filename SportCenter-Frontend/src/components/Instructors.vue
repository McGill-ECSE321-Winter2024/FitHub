<template>
    <div id="instructor_page" class="container-fluid p-0 m-0">
        <div class="row align-items-start justify-content-start p-0 m-0">
            <Toolbar />
            <div class="container pt-5">
                <div class="row">
                    <h2>Meet our Instructors</h2>
                </div>
                <div class="row">
                    <h3>Discover our dedicated team that will guide you with their passion and expertise.</h3>
                </div>
                
                <div class="row align-items-start justify-content-start">
                    <hooper group="group1" :loop="false" :itemsToShow="3" :initialSlide="0" class="h-100 pt-5">
                        <slide class="instructor third-width my-auto" v-for="instructor in instructors"  :key="instructor.id">
                            <div class="">
                                <img class="mt-3" v-bind:src=instructor.imageURL @error="$event.target.src = defaultImage":style="{ 'width': '200px', 'height': 'auto' }" />
                                <ul class="m-0 p-0">
                                    <li class="heading my-2 px-2">{{ instructor.name }}</li>
                                    <li>{{ instructor.email }}</li>
                                    <li>{{ instructor.pronouns }}</li>
                                    <li>Courses list</li>
                                </ul>
                            </div>
                        </slide>
                        <navigation slot="hooper-addons"></navigation>
                    </hooper>
                </div>
            </div>
            
        </div>
    </div>
</template>

<script setup>
defineEmits(['mouseover', 'mouseleave']);
</script>

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

<style scoped>
    #instructor_page {
        min-height: 100vh;
        background-color: #6900BA;
        color: #cdf567;
    }

    .instructor {
        background: transparent;
        color: white;
    }

    ul {
        list-style-type: none;
        font-size: .9em;
    }

    h3 {
        text-align: start;
    }


    .heading {
        font-size: 1.5em;
        color: #171313;
        background-color: #cdf567;
        font-weight: 700;
        display: inline-block;
        width: 200px;
    }

    img {
        filter: grayscale(100%);
    }


    .third-width {
        width: 33vw;
    }
</style>