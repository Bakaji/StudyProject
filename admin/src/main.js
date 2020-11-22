import Vue from 'vue'
import App from './App.vue'
import store from './store'

Vue.config.productionTip = false

let rLink = null;

if (process.env.NODE_ENV === 'production') {
    rLink = function (re_path) {
        return `${localStorage.getItem("base_url")}${re_path}`;
    }
} else {
    rLink = function (re_path) {
        return re_path;
    }
}
export const relativeLink = rLink;

new Vue({
    store,
    render: h => h(App)
}).$mount('#app')
