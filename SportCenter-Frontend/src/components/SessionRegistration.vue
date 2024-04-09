<template>
    <div>
        <Toolbar />

        <div id="registration_page">

        <div class="course-section">
            <img src="https://wehco.media.clients.ellingtoncms.com/imports/adg/photos/201312922_MOMS-SKATING-CLASSES-2-TB_t800.jpg?90232451fbcadccc64a17de7521d859a8f88077d" alt="Rollerskating" class="course-image">
            <div class="content">
                <h1>Rollerskating</h1>
                <h2>Glide into the world of rollerskating and improve your balance with this beginner-friendly course that promises excitement and fun.</h2>
                <div class="container">
                    <h3>10$/hr</h3>
                    <h4>Beginner</h4>
                </div>
            </div>
        </div>

        <div class="buttons">
            <button id="session-btn" type="button" >Individual sessions</button>
            <button id="package-btn" type="button" > Packages</button>
        </div>

        <div class="sessions">
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
                <tbody>
                <tr>
                    <td>2024-04-18</td>
                    <td>11:00 am</td>
                    <td>12:00 pm</td>
                    <td>Mary</td>
                    <td>50</td>
                    <td>3, 303</td>
                    <button class="register-btn">Register</button>
                </tr>
                <tr>
                    <td>2024-04-20</td>
                    <td>1:00 pm</td>
                    <td>2:00 pm</td>
                    <td>Lory</td>
                    <td>40</td>
                    <td>4, 410</td>
                    <button class="register-btn">Register</button>
                </tr>
                </tbody>

            </table>

        </div>

        </div>

        <Footer />
    </div>
</template>

<script>

</script>

<style scoped>

#registration_page {
  background-color: var(--color-black);
  height: 100vh;
  width: 100vw;
  overflow: auto;
}

.course-section {
    display: flex;
    align-items: flex-start;
}

.course-image {
    margin-top: 110px;
    margin-left: 120px;
    width: 450px; 
    height: 250px;
    border-radius: 5px;
}

.content {
    flex-grow: 1;
    padding-left: 20px; 
}

h1 {
    margin-top: 100px;
    font-size: 50px;
    font-weight: 900;
    color: var(--color-white);
    margin-left: 30px;
}

h2 {
    margin-top: 20px;
    margin-left: 30px;
    margin-right: 200px;
    font-size: 25px;
    font-weight: 300;
    color: var(--color-white);
}

.container {
    margin-left: 30px;
    margin-top: 25px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

h3 {
    font-size: 30px;
    font-weight: 650;
    color: #8EE797;
}

h4 {
    font-size: 30px;
    font-weight: 650;
    color: #F56363;
    margin-right: 650px;
}

.buttons {
    margin-top: 100px;
    margin-left: 60px; 
    display: flex;
}

button {
    border-radius: 20px;
    background-color: #509BF4;
    border: none;
    cursor: pointer;
    font-size: 25px;
    font-weight: 650;
    width: 250px; 
    height: 50px; 
    margin-right: 20px;
}
table {
    margin-top: 50px;
    margin-left: 60px;
    background-color: var(--color-black);
    border: white;
}
thead {
    background-color: #509BF4;
    border-radius: 20px;
    color: var(--color-black);
    font-size: 20px;
    font-weight: 600;
}
th {
    margin-left: 50px;
    padding: 25px
}
tr{
    border-radius: 20px;
    margin-top: 50px;
}
td{
    margin-top: 30px;
    color: var(--color-white);
    font-size: 15px;
    font-weight: 400;
    align-content: center;
}
.register-btn{
    border-radius: 20px;
    background-color: #F8FB76;
    border: none;
    cursor: pointer;
    font-size: 17px;
    font-weight: 500;
    width: 110px; 
    height: 30px; 
    margin-right: 20px;
    align-content: center;
}

</style>
