import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/api/account',
    method: 'get',
    params: params
  })
}
export function changeAccountStatus(params) {
  return request({
    url: '/api/account/enable',
    method: 'put',
    data: params
  })
}
export function deleteAccountById(id) {
  return request({
    url: '/api/account/' + id,
    method: 'delete'
  })
}
export function resetAccountPwdById(id) {
  return request({
    url: '/api/account/resetpwd/' + id,
    method: 'put'
  })
}
export function saveOrUpdate(account) {
  return request({
    url: '/api/account',
    method: 'post',
    data: account
  })
}
export function editAccountPwd(params) {
  return request({
    url: '/api/account/editpwd',
    method: 'put',
    data: params
  })
}
export function updateAccount(account) {
  return request({
    url: '/api/account',
    method: 'put',
    data: account
  })
}
