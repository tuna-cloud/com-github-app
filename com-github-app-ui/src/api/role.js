import request from '@/utils/request'

export function getAllRoles(params) {
  return request({
    url: '/api/role',
    method: 'get',
    params: params
  })
}

export function deleteRoleById(id) {
  return request({
    url: '/api/role/' + id,
    method: 'delete'
  })
}

export function saveOrUpdate(role) {
  return request({
    url: '/api/role',
    method: 'post',
    data: role
  })
}

export function saveRolePopedoms(data) {
  return request({
    url: '/api/role/popedom',
    method: 'put',
    data: data
  })
}

export function getRolePopedomsByRoleId(roleId) {
  return request({
    url: '/api/role/popedom/' + roleId,
    method: 'get'
  })
}
