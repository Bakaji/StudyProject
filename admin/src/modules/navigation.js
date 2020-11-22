const store = {
    state: {
        location: 'home'
    },
    getters: {
        isHome: (state) => state.location === 'home',
        isEditProfile: (state) => state.location === 'editProfile',
        isEditBook: (state) => state.location === 'editBook',
        isEditAuthor: (state) => state.location === 'editAuthor',
    },
    actions: {
        goEditProfile: ({commit}) => {
            commit("setLocation", 'editProfile');
        },
        goHome: ({commit}) => {
            commit("setLocation", 'home');
        },
        goEditBook: ({commit}) => {
            commit("setLocation", 'editBook');
        }, goEditAuthor: ({commit}) => {
            commit("setLocation", 'editAuthor');
        }
    },
    mutations: {
        "setLocation": (state, place) => {
            state.location = place;
            return state;
        }
    }
}

export default store;
