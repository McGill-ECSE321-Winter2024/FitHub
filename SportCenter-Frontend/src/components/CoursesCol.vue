<template>
    <div>
        <div>
            <img :src="imageUrl" :alt="altText" class="w-100 h-100" />
        </div>
        <h3><span class="black-heading">{{ blackHeading }}</span></h3>
        <div>
            <h3>{{ title }}</h3>
            <p>{{ description }}</p>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        blackHeading: {
            type: String,
            required: true
        },
        title: {
            type: String,
            required: true
        },
        description: {
            type: String,
            required: true
        },
        imageUrl: {
            type: String,
            required: true
        },
        altText: {
            type: String,
            required: true
        }
    }
};
</script>

<style scoped>
h3 {
    font-size: 24px;
    font-weight: 700;
    text-align: left;
}

p {
    font-size: 14px;
    font-weight: 400;
}

.black-heading {
    font-size: 14px;
    color: #FFF;
    background-color: var(--color-black);
    font-weight: 700;
    padding: 1%;
    text-align: left;
}
</style>