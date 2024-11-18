<template>
    <a-layout>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <a-table :columns="columns" :row-key="record => record.id" :data-source="ebooks" :pagination="pagination"
                @change="handleTableChange">
                <template #headerCell="{ column }">
                    <template v-if="column.key === 'name'">
                        <span>
                            <smile-outlined />
                            名称
                        </span>
                    </template>
                </template>

                <template #bodyCell="{ column, record }">
                    <template v-if="column.key === 'name'">
                        <a>
                            {{ record.name }}
                            {{ record.cover }}
                        </a>
                    </template>
                    <template v-else-if="column.key === 'cover'">
                        <!-- <span>
                            <a-tag v-for="tag in record.tags" :key="tag"
                                :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'">
                                {{ tag.toUpperCase() }}
                            </a-tag>
                        </span> -->
                        <!-- <a-image
                            src={{record.cover}} /> -->
                        <a-avatar :src="record.cover" />
                    </template>
                    <template v-else-if="column.key === 'action'">
                        <a-space>
                            <a-button type="primary" ghost>Primary</a-button>
                            <a-button type="primary" danger ghost>Danger</a-button>
                        </a-space>
                    </template>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';

export default defineComponent({
    name: 'AdminEbooks',
    setup() {
        const ebooks = ref();
        const pagination = ref({
            current: 1,
            pageSize: 3,
            total: 0
        })
        const loading = ref(false);
        const columns = [
            {
                name: '名称',
                key: 'name',
                dataIndex: 'name',
            },
            {
                title: '分类一',
                key: 'category1Id',
                dataIndex: 'category1Id',
                // slots: { customRender: 'category1Id' }
            },
            {
                title: '分类二',
                key: 'category2Id',
                dataIndex: 'category2Id',
                // slots: { customRender: 'category2Id' }
            },
            {
                title: '封面',
                key: 'cover',
                dataIndex: 'cover'
            },
            {
                title: '描述',
                key: 'description',
                dataIndex: 'description',
            },
            {
                title: '文档数',
                key: 'docCount',
                dataIndex: 'docCount',
            },
            {
                title: '阅读数',
                key: 'view_count',
                dataIndex: 'viewCount',
            },
            {
                title: '点赞数',
                key: 'voteCount',
                dataIndex: 'voteCount',
            },
            {
                title: 'Action',
                key: 'action',
                // slots: { customRender: 'action' }
            },
        ]

        /**
         * 数据查询
         */
        const handleQuery = (params: any) => {
            loading.value = true
            axios.get('/ebook/lists').then((response) => {
                ebooks.value = response.data.content

                //重置分页
                pagination.value.current = params.page;
            })
        }

        onMounted(() => {
            // axios.get('/ebook/lists').then((response) => {
            //     ebooks.value = response.data.content
            // })
            handleQuery({});
        })

        /**
         * 表格低级页码时触发
         */
        const handleTableChange = (pagination: any) => {
            console.log('看看自带的分页参数都有啥', pagination);
            handleQuery({
                page: pagination.current,
                size: pagination.pageSize
            });
        }

        return {
            ebooks,
            columns,
            pagination,
            loading,
            handleTableChange
        }
    }
})
</script>