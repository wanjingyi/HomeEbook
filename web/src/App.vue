<template>
  <a-layout class="content_ebook">
    <the-header></the-header>

    <router-view />

    <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';
const ignoreErrors = ["ResizeObserver loop completed with undelivered notifications", "ResizeObserver loop limit exceeded"]
 
window.addEventListener('error', e => {
  let errorMsg = e.message;
  ignoreErrors.forEach(m => {
    if (errorMsg.startsWith(m)) {
      console.error(errorMsg);
      if (e.error) {
        console.error(e.error.stack);
      }
      const resizeObserverErrDiv = document.getElementById(
        'webpack-dev-server-client-overlay-div'
      );
      const resizeObserverErr = document.getElementById(
        'webpack-dev-server-client-overlay'
      );
      if (resizeObserverErr) {
        resizeObserverErr.setAttribute('style', 'display: none');
      }
      if (resizeObserverErrDiv) {
        resizeObserverErrDiv.setAttribute('style', 'display: none');
      }
    }
  })
});


export default defineComponent({
  name: 'app',
  components: {
    TheHeader,
    TheFooter
  },

  setup() {
    // const debounce = (fn, delay) => {
    //   let timer = null;
    //   return function () {
    //     let context = this;
    //     let args = arguments;
    //     clearTimeout(timer);
    //     timer = setTimeout(function () {
    //       fn.apply(context, args);
    //     }, delay);
    //   }
    // }

    // const _ResizeObserver = window.ResizeObserver;
    // window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
    //   constructor(callback) {
    //     callback = debounce(callback, 16);
    //     super(callback);
    //   }
    // }
  }
})
</script>

<style>
.content_ebook {
  height: auto;
}

#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>
