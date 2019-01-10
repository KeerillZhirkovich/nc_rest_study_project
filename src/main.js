import Vue from 'vue'
import App from './App.vue'
import * as VueGoogleMaps from 'vue2-google-maps'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios);

const apiKey = 'YOUR_KEY';

Vue.use(VueGoogleMaps, {
  load: {
    key: apiKey,
    libraries: 'places,drawing'
  }
});

new Vue({
  el: '#app',
  render: h => h(App),
});
