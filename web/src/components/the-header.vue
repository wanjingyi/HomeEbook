<template>
    <a-layout-header class="header">
        <div class="logo" />
        <a-menu theme="dark" mode="horizontal" :style="{ lineHeight: '64px' }">
            <div>
                <a-menu-item key="/">
                    <router-link to="/">首页</router-link>
                </a-menu-item>
                <a-menu-item key="/admin/user">
                    <router-link to="/admin/user">用户管理</router-link>
                </a-menu-item>
                <a-menu-item key="/admin/ebooks">
                    <router-link to="/admin/ebooks">电子书管理</router-link>
                </a-menu-item>
                <a-menu-item key="/admin/category">
                    <router-link to="/admin/category">分类管理</router-link>
                </a-menu-item>
                <a-menu-item key="/about">
                    <router-link to="/about">关于我们</router-link>
                </a-menu-item>
            </div>

            <!-- <a class="login-menu" @click="showLoginModal">
                登陆
            </a> -->
            <!-- <a-button name="登录"></a-button> -->
            <div class="unque" @click="showLoginModal">
                <a class="login-menu" >
                    <span>登录</span>
                </a>
            </div>

        </a-menu>
    </a-layout-header>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';

export default defineComponent({
    name: 'TheHeader',

    setup() {
        const loginModalVisible = ref(false);
        const showLoginModal = () => {
            loginModalVisible.value = true;
        };

        onMounted (() => {
            let elements=document.getElementsByClassName("unque") 
            elements[0].remove(); 
        });

        return {
            showLoginModal,
            loginModalVisible

        }
    }
});

</script>

<style scoped>
.unque{
    width:700px !important
  }
 .unque .login-menu {
    float: right !important;
    color: white !important;
    padding-right: 10px;
   
  }
</style>