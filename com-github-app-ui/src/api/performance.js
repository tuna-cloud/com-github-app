import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/api/performance',
    method: 'get',
    params: params
  })
}
