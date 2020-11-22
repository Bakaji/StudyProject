import {relativeLink} from "@/main";
import axios from "axios";

const cpr = function (a, b, key) {
    if (typeof a[key] === 'string') {
        if (a[key] && b[key])
            return a[key].localeCompare(b[key])
        else return 0;
    }
    if (typeof a[key] === "number")
        if (a[key] === b[key])
            return 0;
        else
            return a[key] < b[key] ? -1 : 1;
    return 0;
}

const store = {
    state: {
        state_authorsList: [],
        state_selectedAuthorBooks: [],
        state_selectedAuthor: null,
        state_selectedBook: null
    },
    getters: {
        authors: (state) => state.state_authorsList,
        selectedAuthor: (state) => state.state_selectedAuthor,
        authorBooks: (state) => state.state_selectedAuthorBooks,
        selectedBook: (state) => state.state_selectedBook,
    },
    actions: {
        loadAuthors: async ({commit}) => {
            let url = relativeLink("/api/authors/all/");
            const result = await axios.get(url);
            if (result.status === 200) {
                commit("setAuthors", result.data);
            }
        },
        loadAuthorBooks: async ({commit, state}) => {
            if (state.state_selectedAuthor !== null) {
                let url = relativeLink("/api/books?authorId=" + state.state_selectedAuthor.author_id);
                const result = await axios.get(url);
                if (result.status === 200) {
                    commit("setSelectedAuthorBooks", result.data);
                }
            }

        },
        selectAuthor: function ({commit, dispatch}, author) {
            commit("setSelectedAuthor", author);
            dispatch("loadAuthorBooks");
        },
        selectBook: function ({commit}, book) {
            commit("setSelectedBook", book);
        },
        editBook: async function ({dispatch}, book) {
            let url = relativeLink("/api/books/edit/");
            const result = await axios.put(url, book);
            if (result.status === 200) {
                console.log("book edited successfully");
                dispatch("loadAuthorBooks");
                // eslint-disable-next-line no-undef
                showMessage("livre a été modifié")
            } else {
                // eslint-disable-next-line no-undef
                showError("Une erreur s'est produite, Veuillez réessayer")
            }
            dispatch("goHome");

        },
        deleteBook: async function ({dispatch}, book_issn) {
            const url = relativeLink("/api/books/delete/" + book_issn + "/");
            const result = await axios.delete(url);
            if (result.status === 200) {
                console.log("book deleted successfully");
                dispatch("loadAuthorBooks");
                // eslint-disable-next-line no-undef
                showMessage("livre a été supprimé")
            }
        },
        editAuthor: async function ({dispatch}, author) {
            let url = relativeLink("/api/authors/edit/");
            const result = await axios.put(url, author);
            if (result.status === 200) {
                console.log("author edited successfully");
                dispatch("loadAuthors");
                // eslint-disable-next-line no-undef
                showMessage("l'auteur a été modifié")
            }
            dispatch("goHome");
        },
        deleteAuthor: async function ({dispatch}, authorId) {
            const url = relativeLink("/api/authors/delete/" + authorId + "/");
            const result = await axios.delete(url);
            if (result.status === 200) {
                console.log("author deleted successfully");
                dispatch("loadAuthors");
                // eslint-disable-next-line no-undef
                showMessage("l'auteur a été supprimé")
            }
        },
    },
    mutations: {
        "setAuthors": (state, authors) => {
            state.state_authorsList = authors;
            state.state_selectedAuthor = null;
            state.state_selectedBook = null;
            state.state_selectedAuthorBooks.length = 0;
        },
        "setSelectedAuthor": (state, author) => {
            state.state_selectedAuthor = author;
            state.state_selectedBook = null;
            state.state_selectedAuthorBooks.length = 0;

        },
        "setSelectedBook": (state, book) => {
            state.state_selectedBook = book;

        },
        "setSelectedAuthorBooks": (state, books) => {
            state.state_selectedBook = null;
            state.state_selectedAuthorBooks = books;

        },
        "sortAuthorsByKey": (state, key) => {
            state.state_authorsList.sort((a, b) => cpr(a, b, key));
        },
        "sortBooksByKey": (state, key) => {
            state.state_selectedAuthorBooks.sort((a, b) => cpr(a, b, key));
        }
    },
}
export default store;
