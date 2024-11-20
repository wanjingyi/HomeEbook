<template>
    <a-layout>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <p>
                <a-input-search v-model:value="queryName" placeholder="分类名称" style="width: 200px;padding-right:10px;" size="large" type="primary" ghost
                    @search="handleQuerycategoryName(queryName)" />
                <a-button @click="add" type="primary" ghost :size="size">新增</a-button>
            </p>
            <a-table :columns="columns" :row-key="record => record.id" :data-source="categorys" :pagination="pagination"
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

    <a-modal v-model:open="open" :confirm-loading="confirmLoading" title="分类表单" @ok="handleOk" okText="确定"
        cancelText="取消">
        <a-form :model="categoryOne" name="basic" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" autocomplete="off">

            <a-form-item label="名称" name="name">
                <a-input v-model:value="categoryOne.name" />
            </a-form-item>

            <a-form-item label="顺序" name="sort">
                <a-input v-model:value="categoryOne.sort" />
            </a-form-item>

            <a-form-item label="父分类" name="parent">
                <a-input v-model:value="categoryOne.parent" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import type { SizeType } from 'ant-design-vue/es/config-provider';
import { message } from 'ant-design-vue';
import {Tool} from '@/util/tool'

export default defineComponent({
    name: 'AdminCategorys',
    setup() {
        const size = ref<SizeType>('large');
        const categorys = ref();
        const queryName = ref();
        const pagination = ref({
            current: 1,
            pageSize: 10,
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
                title: '排序',
                key: 'sort',
                dataIndex: 'sort'
            },
            {
                title: '父分类',
                key: 'parent',
                dataIndex: 'parent',
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
            axios.get('/category/lists', {
                params: {
                    page: params.page,
                    size: params.size
                }
            }).then((response) => {
                loading.value = false;
                const data = response.data
                if (data.success) {
                    categorys.value = data.content.list

                    //重置分页
                    pagination.value.current = params.page;
                    pagination.value.total = data.content.total;
                } else {
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
        const categoryOne = ref();
        const edit = (record: any) => {
            open.value = true;
            categoryOne.value = Tool.copy(record);
        }

        const handleOk = () => {
            confirmLoading.value = true;
            axios.post('/category/save', categoryOne.value).then((response) => {
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
                } else {
                    message.error(data.message);
                }

            })
        }

        /**新增 */
        const add = () => {
            open.value = true;
            categoryOne.value = {};
        }

        /**删除 */
        const handleDelete = (id: number) => {
            axios.delete('/category/delete/' + id).then((response) => {
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

        /**查询单本分类 */
        const handleQuerycategoryName = (queryName:any) => {
            loading.value = true
            axios.get('/category/lists', {
               params: {
                page:1,
                size: pagination.value.pageSize,
                name:queryName
               }
            }).then((response) => {
                loading.value = false;
                const data = response.data
                if (data.success) {
                    categorys.value = data.content.list

                    //重置分页
                    // pagination.value.current = params.page;
                    // pagination.value.total = data.content.total;
                } else {
                    message.error(data.message);
                }

            })
        }

        return {
            size,
            categorys,
            columns,
            pagination,
            loading,
            open,
            confirmLoading,
            categoryOne,
            handleTableChange,
            edit,
            handleOk,
            add,
            handleDelete,
            queryName,
            handleQuerycategoryName
        }
    }
})
</script>