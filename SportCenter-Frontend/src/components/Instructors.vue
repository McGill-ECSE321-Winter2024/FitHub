<template>
    <div>  
    <div id="instructor_page" class="p-0 m-0">
        <Toolbar />
        <div class="row align-items-start justify-content-start p-0 m-0">
            <div class="container pt-5">
                <div class="row">
                    <h2>Meet our Instructors</h2>
                </div>
                <div class="row">
                    <h3>Discover our dedicated team that will guide you with their passion and expertise.</h3>
                </div>

                <div class="row align-items-start justify-content-start over">
                    <hooper group="group1" :loop="false" :itemsToShow="3" :initialSlide="0" class="over h-100 pt-5">
                        <slide class="instructor third-width my-auto" v-for="instructor in instructors"
                            :key="instructor.id">
                            <div class="list">
                                <img class="mt-3" v-bind:src=instructor.imageURL
                                    @error="$event.target.src = defaultImage"
                                    :style="{ 'width': '200px', 'height': 'auto' }" />
                                <ul class="m-0 p-0">
                                    <li class="heading">{{ instructor.name }}</li>
                                    <li>{{ instructor.email }}</li>
                                    <li>{{ instructor.pronouns }}</li>
                                    <ul>
                                        <li v-for="course in instructorCourses[instructor.id]" :key="course.id">{{ course.name }}</li>
                                    </ul>
                                </ul>
                            </div>
                        </slide>
                        <navigation slot="hooper-addons"></navigation>
                    </hooper>
                </div>
                <div class="area higher">
                    <ul class="squares">
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    
    <Footer />
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
            instructorCourses: {}, // Object to store courses for each instructor
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
                credentials: 'include'
            }).then((accountsResponse) => {
                if (accountsResponse.status === 204) {
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
        },
        getAllCourses(instructorId) {
            // Fetch courses for the given instructorId
            const requestOptions = {
                method: 'GET',
                credentials: 'include'
            };

            fetch(`http://127.0.0.1:8080/public/courses?instructor-id=${instructorId}`, requestOptions)
                .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
                })
                .then(data => {
                // Assign courses to the instructorCourses object using instructorId as the key
                this.$set(this.instructorCourses, instructorId, data);
                })
                .catch(error => {
                console.error('Error fetching courses:', error);
                });
        },
    }
};
</script>

<style scoped>
#instructor_page {
    min-height: 100vh;
    background-color: var(--color-purple);
    width: 100%;
    color: var(--color-yellow);
    z-index: 0;
    /* Ensure that this element is behind other content */
}

.over {
    z-index: 1000;
}

.instructor {
    background: transparent;
}

.list {
    width: 200px;
    margin: auto;
}

ul {
    list-style-type: none;
    font-size: .9em;
    text-align: start;
}

h3 {
    text-align: start;
}


.heading {
    font-size: 1.5em;
    font-weight: 700;
    width: 200px;
}

img {
    filter: grayscale(100%);
}

.third-width {
    width: 33vw;
}


.higher {
    z-index: 900;
    /* Ensure that this element is behind other content */
}

/* https://codepen.io/mohaiman/pen/MQqMyo */
.area {
    width: 100%;
    height: 100%;
    z-index: -1;
    /* Ensure that this element is behind other content */
}

.squares {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
}

.squares li {
    position: absolute;
    display: block;
    list-style: none;
    width: 20px;
    height: 20px;
    background: rgba(255, 255, 255, 0.5);
    animation: animate-square 15s linear infinite;
    bottom: -150px;

}

.squares li:nth-child(1) {
    left: 25%;
    width: 10px;
    height: 10px;
    animation-delay: 0s;
}


.squares li:nth-child(2) {
    left: 10%;
    width: 20px;
    height: 20px;
    animation-delay: 2s;
    animation-duration: 12s;
}

.squares li:nth-child(3) {
    left: 70%;
    width: 20px;
    height: 20px;
    animation-delay: 4s;
}

.squares li:nth-child(4) {
    left: 40%;
    width: 60px;
    height: 60px;
    animation-delay: 0s;
    animation-duration: 18s;
}

.squares li:nth-child(5) {
    left: 65%;
    width: 20px;
    height: 20px;
    animation-delay: 0s;
}

.squares li:nth-child(6) {
    left: 75%;
    width: 60px;
    height: 60px;
    animation-delay: 3s;
}

.squares li:nth-child(7) {
    left: 35%;
    width: 80px;
    height: 80px;
    animation-delay: 7s;
}

.squares li:nth-child(8) {
    left: 50%;
    width: 25px;
    height: 25px;
    animation-delay: 15s;
    animation-duration: 45s;
}

.squares li:nth-child(9) {
    left: 20%;
    width: 15px;
    height: 15px;
    animation-delay: 2s;
    animation-duration: 35s;
}

.squares li:nth-child(10) {
    left: 85%;
    width: 10px;
    height: 10px;
    animation-delay: 0s;
    animation-duration: 11s;
}



@keyframes animate-square {

    0% {
        transform: translateY(0) rotate(0deg);
        opacity: 1;
        border-radius: 0;
    }

    100% {
        transform: translateY(-1000px) rotate(720deg);
        opacity: 0;
        border-radius: 50%;
    }

}
</style>