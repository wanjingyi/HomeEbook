import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icon from '@ant-design/icons-vue';

const app = createApp(App);
app.use(store).use(router).use(Antd).mount('#app')

//全局使用图标
const icons : any = Icon;
for(const i in icons) {
    app.component(i,icons)
}

console.log('环境',process.env.NODE_ENV);
console.log('服务端',process.env.VUE_APP_SERVER);