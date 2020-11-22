<template>
  <div class="container" style="flex-direction: column" :key="this.selectedBook.book_issn">
    <h1 id="EditingHeader">Modification du document : {{target.book_title}}</h1>
    <div class="form-container">
      <form action="#" v-on:submit="update">
        <label for="book_new_issn">ISSN</label>
        <input type="text" id="book_new_issn" v-model="target.book_new_issn"/>
        <label for="book_title">Titre</label>
        <input type="text" id="book_title" v-model="target.book_title"/>
        <label for="book_resume">Résumé</label>
        <textarea id="book_resume" v-model="target.book_resume"/>
        <label for="book_page_numbers">Nombre des pages</label>
        <input type="number" id="book_page_numbers" v-model="target.book_page_numbers"/>
        <label for="book_domain">Domaine</label>
        <input type="text" id="book_domain" v-model="target.book_domain"/>
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
  name: "EditBook",
  data: function () {
    return {
      "target": {}
    }
  },
  computed: {
    ...mapGetters(['selectedBook']),
  }
  , mounted() {
    if (this.selectedBook) {
      this.target = {
        ...this.selectedBook,
        "book_old_issn": this.selectedBook.book_issn,
        "book_new_issn": this.selectedBook.book_issn
      }
      delete this.target.book_issn;
    }
  },
  methods: {
    update(e) {
      e.preventDefault();
      this.$store.dispatch("editBook", this.target);
    },
    cancel () {
      this.$store.dispatch("goHome")
    }
  }
}
</script>

<style lang="scss">
form {
  textarea {
    flex-basis: 60%;
    height: 150px;
    margin-bottom: 15px;
  }

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
