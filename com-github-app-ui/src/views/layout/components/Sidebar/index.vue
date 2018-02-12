<template>
  <scroll-bar>
    <el-menu mode="vertical" unique-opened :default-active="$route.path" :collapse="isCollapse" background-color="#304156" text-color="#fff" active-text-color="#409EFF">
      <sidebar-item :routes="routes"></sidebar-item>
    </el-menu>
  </scroll-bar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import ScrollBar from '@/components/ScrollBar'
import { isItemInArray } from '@/utils/index'

export default {
  components: { SidebarItem, ScrollBar },
  computed: {
    ...mapGetters([
      'sidebar',
      'codes'
    ]),
    routes() {
      var myArray = []
      var idx = 0
      for (idx = 0; idx < this.$router.options.routes.length; idx++) {
        if (this.$router.options.routes[idx].hasOwnProperty('code')) {
          if (isItemInArray(this.codes, this.$router.options.routes[idx].code)) {
            var menu = {}
            menu.path = this.$router.options.routes[idx].path
            menu.component = this.$router.options.routes[idx].component
            menu.redirect = this.$router.options.routes[idx].redirect
            menu.name = this.$router.options.routes[idx].name
            menu.code = this.$router.options.routes[idx].code
            menu.icon = this.$router.options.routes[idx].icon
            if (this.$router.options.routes[idx].children.length <= 1) {
              menu.children = this.$router.options.routes[idx].children
              myArray.push(menu)
            } else {
              var childrens = []
              var cdx = 0
              for (cdx = 0; cdx < this.$router.options.routes[idx].children.length; cdx++) {
                if (isItemInArray(this.codes, this.$router.options.routes[idx].children[cdx].code)) {
                  childrens.push(this.$router.options.routes[idx].children[cdx])
                }
              }
              menu.children = childrens
              myArray.push(menu)
            }
          }
        }
      }
      return myArray
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
