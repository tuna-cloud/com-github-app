import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading

/* layout */
import Layout from '../views/layout/Layout'

Vue.use(Router)

/**
 * icon : the icon show in the sidebar
 * redirect : if `redirect:noredirect` will not redirct in the levelbar
 * name : the title show in menu
 * code : if no code value set, this item will not show in sidebar
**/
export const constantRouterMap = [
  {
    path: '/login',
    component: _import('login/index')
  },
  {
    path: '/404',
    component: _import('404')
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '主面板',
    icon: 'home',
    code: 'vuedashboard',
    children: [{ path: 'dashboard', component: _import('dashboard/index') }]
  },
  {
    path: '/api',
    component: Layout,
    redirect: 'noredirect',
    name: '系统管理',
    icon: 'table',
    code: 'vuesystemmgr',
    children: [
      { path: 'account/list', name: '账号管理', code: 'vueaccountmgr', component: _import('system/account') },
      { path: 'account/pwd', name: '密码修改', code: 'vueaccountpwd', component: _import('system/account') },
      { path: 'account/edit', name: '账号信息', code: 'vueaccountinfo', component: _import('system/account') },
      { path: 'role/list', name: '角色管理', code: 'vuerolemgr', component: _import('system/role') },
      { path: 'sysBackup', name: '数据备份', code: 'vuedbback', component: _import('system/role') },
      { path: 'log/list', name: '日志管理', code: 'vuelogmgr', component: _import('system/log') }
    ]
  },
  {
    path: '*',
    redirect: '/404'
  }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

