const menu = {
  state: {
    items: [
    ]
  },
  mutations: {
    SET_ITEMS: (state, items) => {
      state.items = items
    }
  },
  actions: {
    GetMenu({commit, state}) {
      return new Promise((resolve, reject) => {
      })
    }
  }
}

export default menu
