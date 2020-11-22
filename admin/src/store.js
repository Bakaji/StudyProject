import Vue from 'vue'
import Vuex from 'vuex'
import AuthorBooks from './modules/booksAuthors'
import Navigation from './modules/navigation'

Vue.use(Vuex)



export default new Vuex.Store({
    modules:{
        AuthorBooks,
        Navigation
    }
});
