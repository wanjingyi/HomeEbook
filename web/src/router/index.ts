import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AboutView from '@/views/AboutView.vue'
import AdminEbooks from '@/views/admin/AdminEbooks.vue'
import AdminCategory from '@/views/admin/AdminCategory.vue'
import AdminDocument from '@/views/admin/AdminDocument.vue'
import AdminDoc from '@/views/admin/AdminDoc.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: AboutView
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    //懒加载
    // component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },{
    path: '/admin/ebooks',
    name: 'adminEbooks',
    component: AdminEbooks
  },{
    path: '/admin/category',
    name: 'adminCategory',
    component: AdminCategory
  },{
    path: '/admin/document',
    name: 'AdminDocument',
    component: AdminDocument
  },{
    path: '/admin/doc',
    name: 'AdminDoc',
    component: AdminDoc
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
