import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
import { createPinia } from 'pinia'

const app = createApp(App)

app.provide('$axios', axios);
app.provide('$serverUrl', 'http://localhost:8082/')

app.use(createPinia())
app.use(router)

app.mount('#app')
