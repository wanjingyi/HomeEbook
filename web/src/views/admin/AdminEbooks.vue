<template>
    <a-layout>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <p>
                <a-button @click="add" type="primary" ghost :size="size">新增</a-button>
            </p>
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
                        </a>
                    </template>
                    <template v-else-if="column.key === 'cover'">
                        <a-avatar :src="record.cover" />
                    </template>
                    <template v-else-if="column.key === 'action'">
                        <a-space>
                            <a-button type="primary" ghost @click="edit(record)">编辑</a-button>
                            <!-- <a-button type="primary" danger ghost @click="handleDelete(record.id)">删除</a-button> -->
                            <a-popconfirm title="确定要删除吗?" ok-text="是" cancel-text="否"
                                @confirm="handleDelete(record.id)">
                                <a-button type="primary" danger ghost>删除</a-button>
                            </a-popconfirm>
                        </a-space>
                    </template>
                </template>
            </a-table>
        </a-layout-content>
    </a-layout>

    <a-modal v-model:open="open" :confirm-loading="confirmLoading" title="电子书表单" @ok="handleOk" okText="确定"
        cancelText="取消">
        <a-form :model="ebookOne" name="basic" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" autocomplete="off">
            <a-form-item label="封面" name="cover">
                <a-input v-model:value="ebookOne.cover" />
            </a-form-item>

            <a-form-item label="名称" name="name">
                <a-input v-model:value="ebookOne.name" />
            </a-form-item>

            <a-form-item label="分类一" name="category1Id">
                <a-input v-model:value="ebookOne.category1Id" />
            </a-form-item>

            <a-form-item label="分类二" name="category2Id">
                <a-input v-model:value="ebookOne.category2Id" />
            </a-form-item>

            <a-form-item label="描述" name="description">
                <a-input v-model:value="ebookOne.description" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import type { SizeType } from 'ant-design-vue/es/config-provider';
import { message } from 'ant-design-vue';

export default defineComponent({
    name: 'AdminEbooks',
    setup() {
        const size = ref<SizeType>('large');
        const ebooks = ref();
        const pagination = ref({
            current: 1,
            pageSize: 100,
            total: 0
        })
        const loading = ref(false);
        const open = ref<boolean>(false);
        const confirmLoading = ref<boolean>(false);
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
            },
        ]

        /**
         * 数据查询
         */
        const handleQuery = (params: any) => {
            loading.value = true
            axios.get('/ebook/lists', {
                params: {
                    page: params.page,
                    size: params.size
                }
            }).then((response) => {
                loading.value = false;
                const data = response.data
                if (data.success) {
                    ebooks.value = data.content.list

                    //重置分页
                    pagination.value.current = params.page;
                    pagination.value.total = data.content.total;
                }else {
                    message.error(data.message);
                }

            })
        }

        onMounted(() => {
            handleQuery({
                page: 1,
                size: pagination.value.pageSize
            });
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

        /**
         * 编辑
         */
        const ebookOne = ref();
        const edit = (record: any) => {
            open.value = true;
            ebookOne.value = record;
        }

        const handleOk = () => {
            confirmLoading.value = true;
            axios.post('/ebook/save', ebookOne.value).then((response) => {
                const data = response.data
                confirmLoading.value = false;
                if (data.success) {
                    open.value = false;
                    confirmLoading.value = false;

                    //重新加载列表
                    handleQuery({
                        //重新查询当前页面的所有数据
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    });
                }else {
                    message.error(data.message);
                }

            })
        }

        /**新增 */
        const add = () => {
            open.value = true;
            ebookOne.value = {};
        }

        /**删除 */
        const handleDelete = (id: number) => {
            axios.delete('/ebook/delete/' + id).then((response) => {
                const data = response.data
                if (data.success) {

                    //重新加载列表
                    handleQuery({
                        //重新查询当前页面的所有数据
                        page: pagination.value.current,
                        size: pagination.value.pageSize
                    });
                }

            })
        }

        return {
            size,
            ebooks,
            columns,
            pagination,
            loading,
            open,
            confirmLoading,
            ebookOne,
            handleTableChange,
            edit,
            handleOk,
            add,
            handleDelete,
        }
    }
})
</script>