<template>
    <a-layout>
        <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
            <p>
                <a-button type="primary" ghost :size="size" @click="handleQuerydocumentName">查询</a-button>
                <a-button @click="add" type="primary" ghost :size="size">新增</a-button>
            </p>
            <a-table :columns="columns" :row-key="record => record.id" :data-source="level1" :pagination="false">
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

    <a-modal v-model:open="open" :confirm-loading="confirmLoading" title="文档表单" @ok="handleOk" okText="确定"
        cancelText="取消">
        <a-form :model="documentOne" name="basic" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }"
            autocomplete="off">

            <a-form-item label="名称" name="name">
                <a-input v-model:value="documentOne.name" />
            </a-form-item>

            <a-form-item label="父文档" name="name">
                <a-tree-select v-model:value="documentOne.parent" style="width: 100%"
                    :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }" :tree-data="treeSelectData"
                    placeholder="请选择父文档" tree-default-expand-all
                    :field-names="{ label: 'name', value: 'id', children: 'children' }">
                </a-tree-select>
            </a-form-item>

            <a-form-item label="顺序" name="sort">
                <a-input v-model:value="documentOne.sort" />
            </a-form-item>

            <a-form-item label="内容">
                <div id="content"></div>
            </a-form-item>

        </a-form>
    </a-modal>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import axios from 'axios';
import type { SizeType } from 'ant-design-vue/es/config-provider';
import { message, Modal } from 'ant-design-vue';
import { Tool } from '@/util/tool'
import { useRoute } from 'vue-router';
import E from 'wangeditor'

export default defineComponent({
    name: 'AdminDocuments',
    setup() {
        //接收路由的参数
        const route = useRoute();
        // console.log("route.path",route.path);
        // console.log("route.query",route.query);
        // console.log("route.params",route.params);
        // console.log("route.fullPath",route.fullPath);
        // console.log("route.name",route.name);
        // console.log("route.meta",route.meta);
        const size = ref<SizeType>('large');
        const documents = ref();
        const queryName = ref();
        const treeSelectData = ref();
        treeSelectData.value = [];

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
                title: '父文档',
                key: 'parent',
                dataIndex: 'parent',
            },
            {
                title: '按钮',
                key: 'action',
            },
        ]

        const editor = new E("#content")

        /**
         * 数据查询
         */
        const level1 = ref();

        const handleQuery = () => {
            loading.value = true
            level1.value = [];
            axios.get('/document/allData').then((response) => {

                loading.value = false;
                const data = response.data
                if (data.success) {
                    documents.value = data.content
                    console.log("原始数组", documents.value);
                    level1.value = [];
                    level1.value = Tool.array2Tree(documents.value, 0);
                    console.log("递归后数组", level1.value);
                } else {
                    message.error(data.message);
                }

            })
        }



        /**
         * 编辑
         */
        const documentOne = ref();
        const edit = (record: any) => {

            open.value = true;

            documentOne.value = Tool.copy(record);

            treeSelectData.value = Tool.copy(level1.value);
            setDisable(treeSelectData.value, record.id);

            //为选择树添加一个"无"
            treeSelectData.value.unshift({ id: 0, name: '无' });
            setTimeout(function () {
                editor.create()

            },100);

        }

        //因为树选择组件的属性状态，会随着当前编辑的节点而变化，所以单独声明一个响应式变量
        const handleOk = () => {
            confirmLoading.value = true;
            axios.post('/document/save', documentOne.value).then((response) => {
                const data = response.data
                confirmLoading.value = false;
                if (data.success) {
                    open.value = false;
                    confirmLoading.value = false;

                    //重新加载列表
                    handleQuery();
                } else {
                    message.error(data.message);
                }

            })
        }

        /**新增 */
        const add = () => {
            open.value = true;
            documentOne.value = {
                ebookId: route.query.ebookId
            };

            treeSelectData.value = Tool.copy(level1.value);

            //为选择树添加一个"无"
            treeSelectData.value.unshift({ id: 0, name: '无' });
            setTimeout(function () {
                editor.create()

            },100);

        }

        /**
        * 查找整根树枝
        */
        const ids: Array<string> = []
        const idsName: Array<string> = []
        const deleteAllChildren = (treeSelectData: any, id: any) => {
            // 遍历数组，即遍历某一层节点
            for (let i = 0; i < treeSelectData.length; i++) {
                const node = treeSelectData[i];
                if (node.id === id) {
                    // 如果当前节点就是目标节点
                    // console.log("disabled", node);
                    // 将目标节点设置为disabled
                    // node.disabled = true;
                    //将目标ID放入结果集
                    ids.push(node.id);
                    idsName.push(node.name);
                    // 遍历所有子节点
                    const children = node.children;
                    if (Tool.isNotEmpty(children)) {
                        for (let j = 0; j < children.length; j++) {
                            deleteAllChildren(children, children[j].id)
                        }
                    }
                } else {
                    // 如果当前节点不是目标节点，则到其子节点再找找看。
                    const children = node.children;
                    if (Tool.isNotEmpty(children)) {
                        deleteAllChildren(children, id);
                    }
                }
            }
        };


        /**删除 */
        const handleDelete = (id: number) => {
            ids.length = 0;
            idsName.length = 0;
            deleteAllChildren(level1.value, id);
            Modal.confirm({
                title: '重要提示',
                content: '将删除：【' + idsName.join(',') + '】删除后不可恢复，确认删除吗？',
                onOk() {
                    axios.delete('/document/delete/' + ids.join(',')).then((response) => {
                        const data = response.data
                        if (data.success) {

                            //重新加载列表
                            handleQuery();
                        } else {
                            message.error(data.message);
                        }

                    })
                }
            });

        }



        /**查询单文档 */
        const handleQuerydocumentName = () => {
            loading.value = true
            axios.get('/document/allData').then((response) => {
                loading.value = false;
                const data = response.data
                if (data.success) {
                    documents.value = data.content
                    console.log("原始数组", documents.value);
                    level1.value = [];
                    level1.value = Tool.array2Tree(documents.value, 0);
                    console.log("递归后数组", level1.value);
                } else {
                    message.error(data.message);
                }

            })
        }

        /**
 * 将某节点及其子孙节点全部置为disabled
 */
        const setDisable = (treeSelectData: any, id: any) => {
            // 遍历数组，即遍历某一层节点
            for (let i = 0; i < treeSelectData.length; i++) {
                const node = treeSelectData[i];
                if (node.id === id) {
                    // 如果当前节点就是目标节点
                    // console.log("disabled", node);
                    // 将目标节点设置为disabled
                    node.disabled = true;

                    // 遍历所有子节点，将所有子节点全部都加上disabled
                    const children = node.children;
                    if (Tool.isNotEmpty(children)) {
                        for (let j = 0; j < children.length; j++) {
                            setDisable(children, children[j].id)
                        }
                    }
                } else {
                    // 如果当前节点不是目标节点，则到其子节点再找找看。
                    const children = node.children;
                    if (Tool.isNotEmpty(children)) {
                        setDisable(children, id);
                    }
                }
            }
        };

        onMounted(() => {

            handleQuery();
        })


        return {
            size,
            // documents,
            columns,
            loading,
            open,
            confirmLoading,
            documentOne,
            edit,
            handleOk,
            add,
            handleDelete,
            queryName,
            handleQuerydocumentName,
            level1,
            treeSelectData
        }
    }
})
</script>