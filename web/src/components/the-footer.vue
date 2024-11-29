<template>
    <a-layout-footer style="text-align: center">
       <span v-show="user.id">{{user.name}}的电子书</span> 
    </a-layout-footer>
</template>

<script lang="ts">
import { computed, defineComponent } from 'vue';
import store from '@/store';
export default defineComponent({
    name: 'TheFooter',
    setup () {
        const user = computed(() =>  store.state.user)
        return {
            user
        }
    }
});
</script>