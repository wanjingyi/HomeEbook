<template>
    <a-layout-footer style="text-align: center">
        {{user.name}}的电子书
    </a-layout-footer>
</template>

<script lang="ts">
import { computed, defineComponent } from 'vue';
import store from '@/store';
export default defineComponent({
    name: 'TheFooter',
    setup () {
        const user = computed(() =>  store.state.user)
        console.log(user,'9090909090');
        return {
            user
        }
    }
});
</script>