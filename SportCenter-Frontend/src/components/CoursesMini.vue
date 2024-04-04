<template>
    <div class="white-div rounded-div pt-5 mb-5 half-height">
        <h2 class="pl-5">Our Trending Courses</h2>
            <hooper :itemsToShow="3" class="pt-5">
                <slide class="slide-style ">
                    slide 1
                </slide>
                <slide class="slide-style">
                    slide 2
                </slide>
                <slide class="slide-style">
                    slide 3
                </slide>
                <slide class="slide-style">
                    slide 4
                </slide>
                <slide class="slide-style">
                    slide 5
                </slide>
                <slide class="slide-style">
                    slide 6
                </slide>
                <navigation slot="hooper-addons"></navigation>
            </hooper>
    </div>
</template>
