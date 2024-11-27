<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu  v-model:openKeys="openKeys" mode="inline"  @click="handleClick"
        :style="{ height: '100%', borderRight: 0 }">
        <a-menu-item key="welcome">
          <span>欢迎</span>
        </a-menu-item>
        <a-sub-menu v-for="item in level1" :key="item.id">
          <template v-slot:title>
            <span>
              <user-outlined />
              {{item.name}}
            </span>
          </template>
          <a-menu-item v-for="child in item.children" :key="child.id">{{child.name}}</a-menu-item>
        </a-sub-menu>
      </a-menu>

    </a-layout-sider>
    <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
      <div class="welcome" v-show="show_welcome">
        <h1>欢迎小狗水上漂</h1>
      </div>
      <a-list item-layout="vertical" size="large" :data-source="ebooks" :grid="{ gutter: 20, column: 3 }" v-show="!show_welcome">
        <template #renderItem="{ item }">
          <a-list-item key="item.name">
            <template #actions>
              <span v-for="{ icon, text } in actions" :key="icon">
                <component :is="icon" style="margin-right: 8px" />
                {{ text }}
              </span>
            </template>
            <a-list-item-meta :description="item.description">
              <template #title>
                <!-- <a :href="item.href">{{ item.name }}</a> -->
                 <router-link :to="'/admin/doc?ebookId=' + item.id">{{item.name}}</router-link>
              </template>
              <template #avatar><a-avatar :src="item.cover" /></template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-layout-content>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { StarOutlined, LikeOutlined, MessageOutlined, MailOutlined } from '@ant-design/icons-vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
import { Tool } from '@/util/tool';

const actions: any = [
  { icon: StarOutlined, text: '156' },
  { icon: LikeOutlined, text: '156' },
  { icon: MessageOutlined, text: '2' },
];



export default defineComponent({
  name: 'HomeView',
  setup() {
    const ebooks: any = ref();
    const openKeys = ref<string[]>([]);
    const show_welcome = ref(true);
    let categoryId2 = 0;



    const handleQueryEbook = () => {
      axios.get('/ebook/lists', {
        params: {
          page: 1,
          size: 1000,
          categoryId2: categoryId2
        }
      }).then((response) => {
        ebooks.value = response.data.content.list
      })
    }

    /**
     * 数据查询
     */
    const level1 = ref();
    let categorys: any;
    const handleQueryCategory = () => {
      axios.get('/category/allData').then((response) => {
        const data = response.data
        if (data.success) {
          categorys = data.content
          console.log("原始数组", categorys);
          level1.value = [];
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("递归后数组", level1.value);
        } else {
          message.error(data.message);
        }

      })
    }

    const handleClick = (value:any) => {
      console.log("menu click",value);
      if (value.key === 'welcome') {
        show_welcome.value = true
      }else {
        show_welcome.value = false
        categoryId2 = value.key
        handleQueryEbook();
      }
    }

    onMounted(() => {
      // handleQueryEbook();
      handleQueryCategory();
    })

    return {
      ebooks,
      actions,
      handleQueryCategory,
      level1,
      openKeys,
      handleClick,
      show_welcome
    }
  }
});
</script>

<style scoped>
.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;
}
</style>
