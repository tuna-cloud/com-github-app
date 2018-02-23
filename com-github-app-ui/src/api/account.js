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
