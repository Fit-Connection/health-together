<template>
<!--  <img alt="Vue logo" src="./assets/logo.png">-->
<!--  <HelloWorld msg="Welcome to Your Vue.js App"/>-->
  <div id="app">
    <router-view></router-view> <!-- 라우터에 따라 동적으로 컴포넌트 표시 -->
  </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'


export default {
  name: 'App',
  // components: {
  //   TeamSearch
  // }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
