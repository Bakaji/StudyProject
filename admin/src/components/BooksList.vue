<template>
  <div class="list-wrapper">
    <ul v-if="this.authorBooks.length>0">
      <li v-for="book in this.authorBooks" :key="book.book_issn" v-on:click="selectBook(book)"
          :class="{'selected':bookSelected(book.book_issn)}">
        <span>{{ book.book_title }}</span>
        <span>{{ book.book_page_numbers }}</span>
      </li>
    </ul>
    <div v-else class="no-items">
      <span>cet auteur n'a pas des livres</span>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "BooksList",
  computed: {
    ...mapGetters(['authorBooks', 'selectedBook'])
  },
  methods: {
    bookSelected: function (issn) {
      return this.selectedBook != null && this.selectedBook.book_issn === issn;
    },
    selectBook: function (author) {
      this.$store.dispatch("selectBook", author);
    }
  },
}
</script>
