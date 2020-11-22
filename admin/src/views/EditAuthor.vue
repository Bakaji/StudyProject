<template>
  <div class="container" style="flex-direction: column" :key="this.selectedAuthor.author_id">
    <h1 id="EditingHeader">Modification d'auteur : {{ target.last_name }} {{target.first_name}}</h1>
    <div class="form-container">
      <form action="#" v-on:submit="update">
        <label for="author_last_name">Nom</label>
        <input type="text" id="author_last_name" v-model="target.last_name"/>
        <label for="author_first_name">Prénom</label>
        <input type="text" id="author_first_name" v-model="target.first_name"/>
        <label for="book_page_numbers">Date de naissance</label>
        <input type="date" id="book_page_numbers" v-model="target.birth_day"/>
        <div class="actions-container">
          <input type="button" v-on:click="cancel" value="Annuler"/>
          <input type="submit" value="Modifier"/>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "EditAuthor",
  data: function () {
    return {
      "target": {}
    }
  },
  computed: {
    ...mapGetters(['selectedAuthor']),
  }
  , mounted() {
    if (this.selectedAuthor) {
      this.target = {
        ...this.selectedAuthor
      }
    }
  },
  methods: {
    update(e) {
      e.preventDefault();
      this.$store.dispatch("editAuthor", this.target);
    },
    cancel() {
      this.$store.dispatch("goHome")
    }
  }
}
</script>

<style lang="scss">
#EditingHeader{
  margin-left: 40px;
  margin-top: 15px;
}
form {
  .actions-container {
    display: flex;
    align-items: center;
    flex-basis: 100%;

    input {
      max-width: 150px;
      margin-left: 15px;
    }
  }
}
</style>
