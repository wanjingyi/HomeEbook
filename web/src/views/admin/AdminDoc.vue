<template>
    <a-layout>
        <a-layout-content>
            <a-row>
                <a-col :span="6">
                    <a-tree v-if="level1.length > 0"  :tree-data="level1" :field-names="{ title: 'name', key: 'id', value: 'children' }" :defaultExpandAllRows="true">
                        <!-- <template #title="{ title, key }">
                            <span v-if="key === '0-0-1-0'" style="color: #1890ff">{{ title }}</span>
                            <template v-else>{{ title }}</template>
                        </template> -->
                    </a-tree>
                </a-col>
                <a-col :span="18">col-6 col-pull-18</a-col>
            </a-row>
        </a-layout-content>
    </a-layout>
</template>
<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import { Tool } from '@/util/tool'
import { message } from 'ant-design-vue';
import { useRoute } from 'vue-router';

export default defineComponent({
    name: 'AdminDoc',
    setup () {
        const route = useRoute();
        const docs = ref();
        const level1 = ref();
        level1.value = [];

        /**
         * 数据查询
         */

         const handleQuery = () => {
            axios.get('/document/all/' + route.query.ebookId).then((response) => {
                const data = response.data
                console.log(data,'data');
                if (data.success) {
                    docs.value = data.content
                    level1.value = [];
                    level1.value = Tool.array2Tree(docs.value,0);
                    console.log(level1.value,'level1');
                } else {
                    message.error(data.message);
                }

            })
        }

        onMounted(() => {
            handleQuery();
        }) 

        return {
            level1
        }
    }
})

</script>
<style scoped>
#components-layout-demo-top-side .logo {
    float: left;
    width: 120px;
    height: 31px;
    margin: 16px 24px 16px 0;
    background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side .logo {
    float: right;
    margin: 16px 0 16px 24px;
}

.site-layout-background {
    background: #fff;
}
</style>