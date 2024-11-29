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
            <div class="unque" @click="showLoginModal" v-show="user.id" >
                <a class="login-menu">
                    <span>您好：{{user.name}}</span>
                </a>
            </div>
            <div class="unque" @click="showLoginModal" v-show="!user.id">
                <a class="login-menu">
                    <span>登录</span>
                </a>
            </div>
        </a-menu>

        <a-modal title="登录" v-model:visible="loginModalVisible" :confirm-loading="loginModalLoading" @ok="login">
            <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
                <a-form-item label="登录名">
                    <a-input v-model:value="loginUser.loginName" />
                </a-form-item>
                <a-form-item label="密码">
                    <a-input v-model:value="loginUser.password" type="password" />
                </a-form-item>
            </a-form>
        </a-modal>
    </a-layout-header>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref,computed } from 'vue';
import axios from 'axios';
import { message } from 'ant-design-vue';
declare let hexMd5: any;
declare let KEY: any;
import store from '@/store';

export default defineComponent({
    name: 'TheHeader',

    setup() {
        const loginModalVisible = ref(false);
        const loginModalLoading = ref(false);
        // 用来登录
        const loginUser = ref({
            loginName: "admin",
            password: "123456"
        });
        //登陆后保存
        const user = computed(() =>  store.state.user)

        const showLoginModal = () => {
            loginModalVisible.value = true;
        };


        // 登录
        const login = () => {
            console.log("开始登录");
            loginModalLoading.value = true;
            loginUser.value.password = hexMd5(loginUser.value.password + KEY);
            axios.post('/user/login', loginUser.value).then((response) => {
                loginModalLoading.value = false;
                const data = response.data;
                if (data.success) {
                    loginModalVisible.value = false;
                    message.success("登录成功！");
                    // user.value = data.content
                    // store.commit("setUser",user.value);
                    store.commit("setUser",data.content);
                } else {
                    message.error(data.message);
                }
            });
        };

        onMounted(() => {
            let elements = document.getElementsByClassName("unque")
            elements[0].remove();
        });

        return {
            showLoginModal,
            loginModalVisible,
            loginModalLoading,
            loginUser,
            login,
            user
        }
    }
});

</script>

<style scoped>
.unque {
    width: 700px !important
}

.unque .login-menu {
    float: right !important;
    color: white !important;
    padding-right: 10px;

}
</style>