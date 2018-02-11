import request from '@/utils/request'

export function login(account, password) {
  return request({
    url: '/open/auth',
    method: 'post',
    data: {
      account,
      password
    }
  })
}

export function getInfo() {
  return request({
    url: '/api/account/current/login',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/api/account/logout',
    method: 'put'
  })
}
