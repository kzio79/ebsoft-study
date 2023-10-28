import { createRouter, createWebHistory } from 'vue-router'

import index from "@/index.vue";
import content from "@/content.vue";
import modify from "@/modify.vue";
import write from "@/write.vue";
import deleteContent from "@/delete.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: index
    },
    {
      path: '/api/v1/index',
      name: 'index',
      component: index
    },
    {
      path: '/api/v1/content',
      name: 'content',
      props: true,
      component: content
    },
    {
      path: '/api/v1/modify',
      name: 'modify',
      props: true,
      component: modify
    },
    {
      path: '/api/v1/write',
      name: 'write',
      component: write
    },
    {
      path: '/api/v1/delete',
      name: 'delete',
      props: true,
      component: deleteContent
    }
  ]
})

export default router
