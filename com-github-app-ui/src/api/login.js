import fetch from '@/utils/fetch'

export function login(account, password) {
  return fetch({
    url: '/open/auth',
    method: 'post',
    data: {
      account,
      password
    }
  })
}

export function getInfo() {
  return fetch({
    url: '/api/users/current/login',
    method: 'get'
  })
}

export function logout() {
  return fetch({
    url: '/user/logout',
    method: 'post'
  })
}