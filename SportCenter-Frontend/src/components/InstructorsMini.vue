<template>
    <div class="rounded-div pt-5 mb-5 whole-screen">
        <h2 class="pl-5" style="color: #F9F9F9">Our Instructors</h2>
        <div class="d-flex align-items-center justify-content-center p-5">
            <hooper group="group1" class="pt-5">
                <slide class="slide-style whole-width">
                    Anjali Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    Émilia Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    James Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    Julia Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    Sahar Testimonial
                </slide>
                <slide class="slide-style whole-width">
                    Tayba Testimonial
                </slide>
            </hooper>
        </div>
        <div class="d-flex align-items-start justify-content-start p-5">
            <hooper group="group1" :itemsToShow="3" :centerMode="true" class="pt-5">
                <slide class="slide-style third-width">
                    Anjali
                </slide>
                <slide class="slide-style third-width">
                    Émilia
                </slide>
                <slide class="slide-style third-width">
                    James
                </slide>
                <slide class="slide-style third-width">
                    Julia
                </slide>
                <slide class="slide-style third-width">
                    Sahar
                </slide>
                <slide class="slide-style third-width">
                    Tayba
                </slide>

                <navigation slot="hooper-addons"></navigation>
            </hooper>
        </div>
    </div>
</template>

<style>
.whole-width {
    width: 100vw;
}

.third-width {
    width: 33vw;
}
</style>