<template>
<div class="container pt-5" id="app">
    <div class="row mb-3" @click="setNonSelected">
        <div class="col">
            <h1>Поиск места для отдыха</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="form-group">
                <div class="form-group">
                    <lable for="search">Куда поедем?</lable>
                    <input
                            type="text"
                            class="form-control"
                            id="search"
                            placeholder="Пример: кафе в Самаре"
                            v-model="search"
                            @keyup.enter="searchPlaces"
                    >
                </div>
                <hr>
                <button class="btn btn-primary mb-2" @click="showFilters = !showFilters">Фильтры</button>
                <div id="filters" v-if="showFilters">
                  <input type="checkbox" id="amusement" :value="amusementTypes" v-model="selectedFilters">
                  <label for="amusement">Развлечения</label><br>
                  <input type="checkbox" id="art" :value="artTypes" v-model="selectedFilters">
                  <label for="art">Искусство</label><br>
                  <input type="checkbox" id="stroll" :value="strollTypes" v-model="selectedFilters">
                  <label for="stroll">Прогулка</label><br>
                  <input type="checkbox" id="shopping" :value="shoppingTypes" v-model="selectedFilters">
                  <label for="shopping">Шоппинг</label><br>
                  <input type="checkbox" id="bar" :value="barTypes" v-model="selectedFilters">
                  <label for="bar">Бары, рестораны</label>
                </div>
                <hr>
                <ul class="list-group" v-if="places === null || places.length === 0">
                  <li
                    class="list-group-item list-group-item-action pointer"
                  >
                    Places was not found!
                  </li>
                </ul>
                <ul class="list-group" v-if="places != null">
                    <li
                            v-for="(place, index) in places"
                            class="list-group-item list-group-item-action pointer"
                            @click="selectPlace(index)"
                            :class="{'active': selectedPlaceIndex === index}"
                    >
                        {{ place.name }}
                    </li>
                </ul>
            </div>
            <div class="col-md-7 mb-5" v-if="place != null">
                <h2>Информация</h2>
                <ul>
                    <li><strong>Название: </strong>{{ place.name }}</li>
                    <li><strong>Адрес: </strong>{{ place.formatted_address }}</li>
                    <li><strong>Уровень цен: </strong>{{ place.price_level }}</li>
                    <li><strong>Рейтинг: </strong>{{ place.rating }}</li>
                    <li><strong>Теги: </strong>{{ place.types }}</li>
                </ul>
                <button class="btn btn-primary">Построить маршрут</button>
            </div>
        </div>
        <div class="col-md-8 pt-3">
            <div class="row">
                <div class="col-md-5">
                    <h2>Карта</h2>
                  <GmapMap
                    id="rest-map"
                    :center="this.center"
                    :zoom="this.zoom"
                    map-type-id="roadmap"
                    style="width: 700px; height: 400px"
                  >
                    <GmapMarker
                      :key="index"
                      v-for="(place, index) in places"
                      :position = "place.geometry.location"
                      :title = "place.name"
                      :clickable="true"
                      :draggable="true"
                      @click="selectPlace(index)"
                    />
                  </GmapMap>
                </div>
            </div>
        </div>
    </div>
</div>
</template>

<script>
  import {gmapApi} from 'vue2-google-maps'

  const artTypes = [
    'art_gallery',
    'museum'
  ];

  const strollTypes = [
    'park',
    'cemetery',
    'campground'
  ];

  const amusementTypes = [
    'amusement_park',
    'bowling_alley',
    'casino',
    'movie_theater',
    'zoo'
  ];

  const shoppingTypes = [
    'clothing_store',
    'jewelry_store',
    'shoe_store',
    'shopping_mall',
    'electronics_store',
    'supermarket'
  ];

  const barTypes = [
    'bar',
    'night_club',
    'restaurant',
    'liquor_store'
  ];

  const location = (lat, lng) => ({lat, lng});

  const geometry = (location) => ({location});

  const place = (formatted_address, name, price_level, rating, types, geometry) =>
    ({formatted_address, name, price_level, rating, types, geometry});

  const marker = (position, name) => ({position, name});

  const locationsStub = [
    location(53.2034071, 50.1472741),
    location(53.21634299999999, 50.146395),
    location(53.2065457, 50.126171)
  ];

  const geometriesStub = [
    geometry(locationsStub[0]),
    geometry(locationsStub[1]),
    geometry(locationsStub[2])
  ];

  const placesStub = [
    place('Московское ш., 4к15, Самара, Самарская обл., Россия, 443013', 'Дорогая, я перезвоню ..,', 2, 4.3, 'бар, клуб', geometriesStub[0]),
    place('ул. Ново-Садовая, 106, лит. Г, Самара, Самарская обл., Россия, 443068', 'Ван Гог', 2, 4.2, 'бар, ресторан', geometriesStub[1]),
    place('ул. Ново-Садовая, 6, Самара, Самарская обл., Россия, 443100', 'Just Wood', 1, 4.8, 'бар', geometriesStub[2])
  ];

  const markersStub = [
    marker(placesStub[0].geometry.location, placesStub[0].name),
    marker(placesStub[1].geometry.location, placesStub[1].name),
    marker(placesStub[2].geometry.location, placesStub[2].name)
  ];

  const backUrl = 'http://localhost:8888';
  const apiPath = '/api';
  const v1Path = "/v1";
  const searchPath = "/search";

export default {
  name: 'app',
  data() {
    return {
      //places
      places: placesStub,
      place: null,
      selectedPlaceIndex: null,
      search: '',

      //map properties
      lat: 53.203358,
      lng: 50.146048,
      map: null,
      markers: markersStub,
      zoom: 11,
      center: location(53.203358, 50.146048),
      position: location(53.203358, 50.146048),

      //filters
      showFilters: false,
      selectedFilters: [],
      artTypes: artTypes,
      strollTypes: strollTypes,
      amusementTypes: amusementTypes,
      shoppingTypes: shoppingTypes,
      barTypes: barTypes
    }
  },

  methods: {
    selectPlace(index) {
      this.place = this.places[index];
      this.center = this.places[index].geometry.location;
      this.zoom = 15;
      this.selectedPlaceIndex = index;
    },

    searchPlaces() {
      const params = new URLSearchParams();
      params.append('query', this.search);
      params.append('types', this.selectedFilters);
      this.axios.post(backUrl + apiPath + v1Path + searchPath, params)
        .then((response) => {
         this.places = response.data;
      }).catch((error) => {
        console.log(error)
      })
    },

    setNonSelected() {
      this.zoom = 11;
      this.place = null;
      this.selectedPlaceIndex = null;
    },

    //TODO Routes не подключены на используемом api key. Маршруты реализуем при подключении routes
/*    calculateAndDisplayRoute(directionsService, directionsDisplay, pointA, pointB) {
      directionsService.route({
        origin: pointA,
        destination: pointB,
        avoidTolls: true,
        avoidHighways: false,
        travelMode: google.maps.TravelMode.DRIVING
      }, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
          directionsDisplay.setDirections(response);
        } else {
          window.alert('Directions request failed due to ' + status);
        }
      });
    },

    initMap() {
      const pointA = new google.maps.LatLng(51.7519, -1.2578);
      const pointB = new google.maps.LatLng(50.8429, -0.1313);
      const myOptions = {
            zoom: 7,
            center: pointA
          };
      const map = new google.maps.Map(document.getElementById('rest-map'), myOptions),
          // Instantiate a directions service.
          directionsService = new google.maps.DirectionsService,
          directionsDisplay = new google.maps.DirectionsRenderer({
            map: map
          });
      const markerA = new google.maps.Marker({
            position: pointA,
            title: "point A",
            label: "A",
            map: map
          });
      const markerB = new google.maps.Marker({
            position: pointB,
            title: "point B",
            label: "B",
            map: map
          });
        // get route from A to B
      this.calculateAndDisplayRoute(directionsService, directionsDisplay, pointA, pointB);
    }*/
  },

  //Определение местоположения
  mounted: function() {
    if(navigator.geolocation){
      navigator.geolocation.getCurrentPosition(position => {
        this.position = position.coords;
        console.log(position.coords);
        this.center = location(position.coords.latitude, position.coords.longitude);
      })
    }
  },
  computed: {
    google: gmapApi
  }
}
</script>
