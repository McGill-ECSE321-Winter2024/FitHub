<template>
    <div>
        <Toolbar />
        <h4>Individual Sessions</h4>
        <div class="row">
            <div class="col-md-5 col-lg-3 col-sm-12 mb-5" v-for="session in this.sessions" :key="session.id">
                <img :src="session.course.url" :alt="session.course.name" width="300px" height="200px">
                <h6>Session ID: {{ session.id }}</h6>
                <h6>Capacity: {{ session.capacity }}</h6>
                <h6>Date: {{ session.date }}</h6>
                <h6>Start Time: {{ session.startTime }}</h6>
                <h6>End Time: {{ session.endTime }}</h6>
                <h6>Supervisor: {{ session.supervisor.name }}</h6>
                <h6>Location Floor: {{ session.location.floor }}</h6>
                <h6>Location Room: {{ session.location.room }}</h6>
            </div>
        </div>
        <h4>Session Packages</h4>
        <div class="row">
            <div class="col-md-5 col-lg-3 col-sm-12 mb-5" v-for="sessionPackage in this.sessionPackages"
                :key="sessionPackage.id">
                <h6>Session Package ID: {{ sessionPackage.id }}</h6>
                <h6>Starting Date: {{ sessionPackage.date }}</h6>
                <h6>Reduced Price: {{ sessionPackage.priceReduction }}</h6>
                <h6>Duration (in weeks): {{ sessionPackage.duration }}</h6>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: ['cId'],
    data() {
        return {
            sessions: [],
            sessionPackages: []
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
        }
    }
};
</script>