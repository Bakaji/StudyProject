<template>
  <div id="app">
    <nav>
      <section class="left">
        <a href="#"><h3>Bibliothèque</h3></a>
      </section>
      <section class="right">
        <a href="#" v-on:click="this.goHome">
          <button>Livres et auteurs</button>
        </a>
        <a href="#" v-on:click="this.goEditProfile">
          <button>Modifier Compte administrateur</button>
        </a>
        <a href="#">
          <button class="danger" v-on:click="logout">Se déconnecter</button>
        </a>
      </section>
    </nav>
    <main>
      <Home v-if="this.isHome"></Home>
      <EditAdmin v-if="this.isEditProfile"></EditAdmin>
      <EditBook v-if="this.isEditBook"></EditBook>
      <EditAuthor v-if="this.isEditAuthor"></EditAuthor>
    </main>
  </div>
</template>


<script>
import Home from "@/views/Home";
import {mapActions, mapGetters} from "vuex";
import {relativeLink} from "@/main";
import EditAdmin from "@/views/EditAdmin";
import EditBook from "@/views/EditBook";
import EditAuthor from "@/views/EditAuthor";

export default {
  components: {EditAuthor, EditBook, EditAdmin, Home},
  methods: {
    ...mapActions(['goHome', 'goEditProfile']),
    logout: function () {
      window.location.replace(relativeLink("/"))
    }
  },
  computed: {
    ...mapGetters(['isHome', 'isEditProfile', 'isEditBook', 'isEditAuthor'])
  }
}
</script>

<style lang="scss">
@import "../../src/main/webapp/assets/default";
#app{
  display: flex;
  flex-direction: column;
  height: 100vh;
}
nav {
  background-color: $secondColor;
  flex: 0;
  flex-basis: 50px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-left: 20px
}

.left {
  height: 100%;
  display: flex;
  align-items: center;

  h3 {
    font-family: $titleFont;
    color: white
  }
}

.right {
  display: flex;
  justify-content: space-between;
  align-items: stretch;
  height: 100%;
  width: 480px;

  a {
    display: block;
    height: 100%;

    button {
      height: 100%;
      cursor: pointer;
      background-color: transparent;
      padding: 0 15px;
      font-weight: bold;
      color: white;
      border: 0;
    }

    &:hover button {
      background-color: #0e283c;
    }

    &:hover button.danger {
      background-color: red;
    }
  }


}

main {
  flex: 1;
  overflow: auto;
}
</style>
