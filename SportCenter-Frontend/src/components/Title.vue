<template>
    <div class="d-flex flex-column pl-5 mb-5 rounded-div">
        <h1 class="fithub">FITHUB</h1>
        <p id="title-paragraph" class="mt-4">
            Level up your training with<br>
            certified instructors at Montreal's<br>
            #1 rated sport center.
        </p>
        <div class="d-flex flex-row mb-5">
            <div class="d-flex flex-column align-items-center stats-text px-3 pl-5 vl">
            <h2>15k+</h2>
            <p>Members Joined</p>
        </div>
        <div class="d-flex flex-column align-items-center stats-text px-3 vl">
            <h2>20+</h2>
            <p>Certified Instructors</p>
        </div>
        <div class="d-flex flex-column align-items-center stats-text px-3">
            <h2>10k+</h2>
            <p>Courses Offered</p>
        </div>
        </div>
    </div>
</template>

<style>
    #title-paragraph {
        color: #FFFFFF;
        text-align: justify;
        line-height: 2;
        font-weight: 400;
        font-size: 1.5em;
    }

    .stats-text {
        text-align: justify;
        margin-top: 7%;
        color: #FFFFFF;
        font-weight: 400;
        line-height: 0.8;
    }

    .vl {
        border-right: 1px solid white;
        height: 100%;
    }
</style>