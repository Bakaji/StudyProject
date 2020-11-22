<template>
  <div class="container">
    <section>
      <h3>Liste des auteurs</h3>
      <div class="header">
        <span v-on:click="sortAuthorsBy('last_name')">Nom</span>
        <span v-on:click="sortAuthorsBy('first_name')">Prénom</span>
        <span v-on:click="sortAuthorsBy('birth_day')">Date de naissance</span>
      </div>
      <AuthorsList></AuthorsList>
      <div class="control-buttons">
        <a :href=addAuthorLink>
          <button>Ajouter auteur</button>
        </a>
        <a href="#" v-if="selectedAuthor">
          <button v-on:click="modifyAuthor">Modifier</button>
        </a>
        <a href="#" v-if="selectedAuthor" v-on:click="promptDeletingAuthor">
          <button class="danger">Supprimer</button>
        </a>
      </div>
    </section>
    <section v-if="selectedAuthor">
      <h3>Liste des livres</h3>
      <div class="header">
        <span v-on:click="sortBooksBy('book_title')">Titre</span>
        <span v-on:click="sortBooksBy('book_page_numbers')">Nombre des pages</span>
      </div>
      <BooksList :key="author_id"></BooksList>
      <div class="control-buttons">
        <a :href=addBookLink>
          <button>Ajouter livre</button>
        </a>
        <a href="#" v-if="selectedBook">
          <button v-on:click="modifyBook">Modifier</button>
        </a>
        <a href="#" v-if="selectedBook" v-on:click="promptDeleting">
          <button class="danger">Supprimer</button>
        </a>
      </div>
    </section>
  </div>
</template>

<script>

import {relativeLink} from "@/main";
import AuthorsList from "@/components/AuthorsList";
import BooksList from "@/components/BooksList";
import {mapGetters} from "vuex";
import {showDialog} from "@/assets/ConfirmationDialog";

export default {
  name: 'Home',
  components: {BooksList, AuthorsList},
  computed: {
    ...mapGetters(['selectedAuthor', 'selectedBook']),
    author_id: function () {
      return this.selectedAuthor !== null ? this.selectedAuthor.author_id : -1;
    },
    addAuthorLink: function () {
      return relativeLink("/admin/add-author/");
    },
    addBookLink: function () {
      const question = "?authorId=" + (this.selectedAuthor !== null ? this.selectedAuthor.author_id : -1);
      return relativeLink("/admin/authors/add-book" + question)
    }
  },
  methods: {
    modifyBook: function () {
      this.$store.dispatch("goEditBook");
    },
    sortAuthorsBy: function (key) {
      this.$store.commit("sortAuthorsByKey", key)
    },
    sortBooksBy: function (key) {
      this.$store.commit("sortBooksByKey", key)
    },
    modifyAuthor: function () {
      this.$store.dispatch("goEditAuthor");
    },
    promptDeleting: function () {
      showDialog("est-ce que vous etez sure de supprimer le livre " + this.selectedBook.book_title + " ?",
          () => {
            this.$store.dispatch("deleteBook", this.selectedBook.book_issn);
          }
      )
    },
    promptDeletingAuthor: function () {
      showDialog("est-ce que vous etez sure de supprimer l'auteur' " + this.selectedAuthor.first_name + " " + this.selectedAuthor.last_name + " ?",
          () => {
            this.$store.dispatch("deleteAuthor", this.selectedAuthor.author_id);
          }
      )
    }
  }
  ,
  mounted() {
    this.$store.dispatch("loadAuthors");
  }
}
</script>
<style lang="scss">
@import "../../../src/main/webapp/assets/default";

.container {
  height: 100%;
  width: 100%;
  box-sizing: border-box;
  padding: 30px;
  display: flex;

  section {
    flex: 1;
    display: flex;
    flex-direction: column;
    max-width: 50%;

    h3 {
      flex-basis: 50px;
    }

    ul, a, .header {
      margin: 5px 15px 0;
    }

    .list-wrapper {
      flex: 1;
      overflow: auto;
      display: flex;
      position: relative;

      .no-items {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -5%);

        span {
          color: red;
          font-weight: bold;
        }
      }
    }

    ul {
      flex: 1;
      overflow: auto;
      background-color: #fafafa;
      transition: background-color 0.5s;
      margin-top: 0;
    }

    .control-buttons {
      flex-basis: 50px;
      display: flex;
      justify-content: space-between;
      align-items: center;

      a {
        display: block;

        button {
          height: 40px;
          width: 100px;
          padding: 0;

          &.danger {
            background-color: red;
          }
        }
      }
    }

    li, .header {
      height: 40px;
      display: flex;
      justify-content: space-between;
      padding: 0 20px;
      align-items: center;
      cursor: pointer;

      span {
        position: relative;
        display: block;
        flex: 1;
        padding-left: 15px;

        &:last-child {
          flex: 0;
          flex-basis: 170px;
          text-align: end;
        }
      }
    }

    .header {
      background-color: $secondColor;
      span {
        color: white;
        font-weight: bold;

        &:last-child {
          padding-right: 15px;
        }
      }
    }
    .selected {
      background-color: #e0e0e0;
    }
    li:hover {
      background-color: $mainColor;

      span {
        color: white;
      }
    }
  }
}

.header {
  span {
    &:hover {
      &::after {
        content: '';
        display: block;
        box-sizing: content-box;
        width: 0;
        height: 0;
        position: absolute;
        right: 0;
        top: 25%;
        border-left: 5px solid transparent;
        border-right: 5px solid transparent;
        border-bottom: 5px solid white;
      }

      &::before {
        content: '';
        display: block;
        box-sizing: content-box;
        width: 0;
        height: 0;
        position: absolute;
        right: 0;
        top: 65%;
        border-left: 5px solid transparent;
        border-right: 5px solid transparent;
        border-top: 5px solid white;
      }
    }
  }
}
</style>
