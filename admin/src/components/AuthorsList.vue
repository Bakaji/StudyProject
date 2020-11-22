<template>
  <div class="list-wrapper">
    <ul v-if="this.authors.length>0">
      <li v-for="a in this.authors" :key="a.author_id" v-on:click="selectAuthor(a)"
          :class="{'selected':authorSelected(a.author_id)}">
        <span>{{ a.last_name }}</span>
        <span>{{ a.first_name }}</span>
        <span>{{ a.birth_day }}</span>
      </li>
    </ul>
    <div v-else class="no-items">
      <span>pas des auteurs </span>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "AuthorsList",
  methods: {
    authorSelected: function (id) {
      return this.selectedAuthor != null && this.selectedAuthor.author_id === id;
    },
    selectAuthor: function (author) {
      this.$store.dispatch("selectAuthor", author);
    }
  },
  computed: {
    ...mapGetters(['authors', 'selectedAuthor']),
  }
}
</script>

<style lang="scss">


</style>
