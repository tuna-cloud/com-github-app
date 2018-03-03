import request from '@/utils/request'

export function netGet(url, params) {
  return request({
    url: url,
    method: 'get',
    params: params
  })
}

export function netPut(url, params, data) {
  return request({
    url: url,
    method: 'put',
    params: params,
    data: data
  })
}

export function netDelete(url, params, data) {
  return request({
    url: url,
    method: 'put',
    params: params,
    data: data
  })
}

export function netPost(url, params, data) {
  return request({
    url: url,
    method: 'post',
    params: params,
    data: data
  })
}
