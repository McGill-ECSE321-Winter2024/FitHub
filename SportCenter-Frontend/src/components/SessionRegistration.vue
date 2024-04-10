<template>
    <div>
        <Toolbar />
        <div id="registration_page">
            <div class="row mt-5 pb-5">
                <div class="col ml-5 mr-5">
                    <img :src="this.sessions[0].course.url" :alt="this.sessions[0].course.name" width="300px"
                        class="course-image" />
                </div>
                <div class="col-7">
                    <h1>{{ capitalize(this.sessions[0].course.name) }}</h1>
                    <p class="description">{{ this.sessions[0].course.description }}</p>
                    <div class="row ml-2">
                        <h2 class="mr-5 green-text">{{ this.sessions[0].course.pricePerHour }}$/hour</h2>
                        <h2 class="ml-5 red-text">{{ this.sessions[0].course.difficulty }}</h2>
                    </div>
                </div>
                <div class="col"></div>
            </div>

            <div class="mt-5">
                <table>
                    <thead>
                        <tr>
                            <th class="table-headings">Date</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Instructor</th>
                            <th>Capacity</th>
                            <th>Location (Floor, Room)</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody v-for="session in this.sessions" :key="session.id">
                        <tr>
                            <td>{{ session.date }}</td>
                            <td>{{ convertTo12HourFormat(session.startTime) }}</td>
                            <td>{{ convertTo12HourFormat(session.endTime) }}</td>
                            <td>{{ session.supervisor.name }}</td>
                            <td>{{ session.capacity }}</td>
                            <td>{{ session.location.floor }}, {{ session.location.room }}</td>
                            <button class="register-btn" @click="register(session.id)">Register</button>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="popup" v-if="showSuccessConfirmation">
            <div class="popup-content">
                <p>You have succesfully been registered!</p>
                <button @click="closePopup" style="color: var(--color-azure)">OK</button>
            </div>
        </div>

        <div class="popup" v-if="showError">
            <div class="popup-content">
                <p>There was an error creating your registration. Please try again later.</p>
                <button @click="closePopup" style="color: var(--color-azure)">OK</button>
            </div>
        </div>

        <div class="popup" v-if="showNoCookies">
            <div class="popup-content">
                <p>You cannot register without an account!</p>
                <button @click="closePopup" style="color: var(--color-azure)">OK</button>
            </div>
        </div>
        <Footer />
    </div>
</template>

<script>
export default {
    props: ['cId'],
    data() {
        return {
            sessions: [],
            sessionPackages: [],
            showSuccessConfirmation: false,
            showError: false,
            showNoCookies: false
        };
    },
    mounted() {
        this.fetchSessions();
        this.fetchSessionsPackage();
    },
    methods: {
        fetchSessions() {
            fetch(`http://127.0.0.1:8080/public/sessions/courses/${this.cId}`, {
                method: 'GET',
                credentials: 'include'
            }).then((sessionsResponse) => {
                if (sessionsResponse.status === 204) {
                    console.log("No sessions for this course in database");
                }
                else {
                    sessionsResponse.json().then(sessions => {
                        this.sessions = sessions.sessions;

                        console.log(this.sessions);
                    }).catch(error => {
                        console.error('Error parsing JSON:', error);
                    });
                }
            }).catch(error => {
                console.error('Error fetching sessions:', error);
            });
        },
        fetchSessionsPackage() {
            fetch(`http://127.0.0.1:8080/public/session-packages/course/${this.cId}`, {
                method: 'GET',
                credentials: 'include'
            }).then((sessionPackagesResponse) => {
                if (sessionPackagesResponse.status === 204) {
                    console.log("No session packages for this course in database")
                } else {
                    sessionPackagesResponse.json().then(sessionPackages => {
                        this.sessionPackages = sessionPackages.sessionPackages;
                        console.log(this.sessionPackages)
                    }).catch(error => {
                        console.error('Error parsing JSON:', error);
                    });
                }
            }).catch(error => {
                console.error('Error fetching session packages:', error);
            });
        },
        register(sessionId) {
            const username = decodeURIComponent(this.$cookies.get('username'));
            const password = this.$cookies.get('password');

            console.log('Username:', username);
            console.log('Password:', password);

            if (username && password) {
                console.log('Session ID: ', sessionId);
                console.log('Customer ID: ', this.$cookies.get('id'));
                const customerId = this.$cookies.get('id');

                fetch(`http://127.0.0.1:8080/registrations?customerId=${customerId}&sessionId=${sessionId}`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password'))
                    },
                    credentials: 'include',
                })
                    .then(response => {
                        console.log('Response Status:', response.status);
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => {
                        console.log('Registration created:', data);
                        this.showSuccessConfirmation = true;
                    })
                    .catch(error => {
                        console.error('Error creating registration:', error);
                        this.showError = true;
                    });
            } else {
                console.error('User not authenticated');
                this.showNoCookies = true;
            }
        },
        cancelRegistration(sessionId) {
            const username = decodeURIComponent(this.$cookies.get('username'));
            const password = this.$cookies.get('password');

            console.log('Username:', username);
            console.log('Password:', password);

            if (username && password) {
                console.log('Session ID: ', sessionId);
                console.log('Customer ID: ', this.$cookies.get('id'));
                const customerId = this.$cookies.get('id');

                fetch(`http://127.0.0.1:8080/registrations/${customerId}/${sessionId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Basic ' + btoa(decodeURIComponent(this.$cookies.get('username')) + ':' + this.$cookies.get('password'))
                    },
                    credentials: 'include',
                })
                    .then(response => {
                        console.log('Response Status:', response.status);
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                    })
                    .then(data => {
                        console.log('Registration deleted:');
                    })
            } else {
                console.error('User not authenticated');
            }
        },
        capitalize(str) {
            return str.replace(/\b\w/g, (char) => char.toUpperCase());
        },
        convertTo12HourFormat(timeString) {
            const [hours, minutes] = timeString.split(':');
            let hour = parseInt(hours, 10);
            const period = hour >= 12 ? 'PM' : 'AM';
            hour = hour % 12 || 12;
            const paddedMinutes = minutes.padStart(2, '0');
            const twelveHourTime = `${hour}:${paddedMinutes} ${period}`;
            return twelveHourTime;
        },
        closePopup() {
            this.showSuccessConfirmation = false;
            this.showError = false;
            this.showNoCookies = false;
        }
    }
};
</script>

<style scoped>
#registration_page {
    background-color: var(--color-black);
    height: 100vh;
    width: 100vw;
    overflow: auto;
}

.course-image {
    border-radius: 5px;
}

h1 {
    font-weight: 800;
    font-size: 4em;
    color: var(--color-white);
}

.green-text {
    font-weight: 600;
    color: var(--color-green);
}

.red-text {
    font-weight: 600;
    color: var(--color-red);
}

.description {
    color: var(--color-white);
    font-weight: 500;
    font-size: 1em;
    text-align: left;
}

table {
    border-collapse: separate;
    border-spacing: 0;
    min-width: 350px;
    margin: auto;
    width: 100vw;
    padding-left: 5%;
    padding-right: 5%;
}

table tr th,
table tr td {
    border-right: 1px solid #FFF;
    border-bottom: 1px solid #FFF;
    padding: 10px;
}

table tr th:first-child,
table tr td:first-child {
    border-left: 1px solid #FFF;
}

table tr th {
    background: #3E8EF1;
    text-align: center;
    border-top: solid 1px #3E8EF1;
}

table td {
    color: var(--color-white);
}

/* top-left border-radius */
table tr:first-child th:first-child {
    border-top-left-radius: 6px;
}

/* top-right border-radius */
table tr:first-child th:last-child {
    border-top-right-radius: 6px;
}

/* bottom-left border-radius */
table tr:last-child td:first-child {
    border-bottom-left-radius: 6px;
}

/* bottom-right border-radius */
table tr:last-child td:last-child {
    border-bottom-right-radius: 6px;
}

.register-btn {
    border-radius: 20px;
    background-color: var(--color-yellow);
    border: none;
    font-size: 1em;
    font-weight: 700;
    width: 110px;
    height: 30px;
    margin-top: 5%;
}

.popup {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: var(--color-black);
    border: 2px solid #ccc;
    border-radius: 40px;
    padding: 20px;
    z-index: 9999;
    /* Ensure it appears on top of other elements */
    width: 400px;
    /* Adjust the width as needed */
    height: 150px;
    /* Adjust the height as needed */
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
</style>
