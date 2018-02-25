import request from '@/utils/request'

export function getAllRoles() {
  return request({
    url: '/api/role',
    method: 'get'
  })
}
