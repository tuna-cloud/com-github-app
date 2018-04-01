import { prefixInteger } from './numutils'
/**
 * 将时间戳格式化成日期对象
 * @param time 单位:微妙
 * @returns {string}
 */
export function formatUs2String(time) {
  return formatMs2String(time / 1000)
}

/**
 * 将时间戳格式化成时间字符串
 * @param time 单位:ms
 * @returns {string}
 */
export function formatMs2String(time) {
  var d = new Date(time)
  var date = (d.getFullYear()) + '-' +
    (prefixInteger(d.getMonth() + 1, 2)) + '-' +
    (prefixInteger(d.getDate(), 2)) + ' ' +
    (prefixInteger(d.getHours(), 2)) + ':' +
    (prefixInteger(d.getMinutes(), 2)) + ':' +
    (prefixInteger(d.getSeconds(), 2))
  return date
}

/**
 * 将时间戳格式化成时间字符串
 * @param time 单位:秒
 * @returns {string}
 */
export function formatSeconds2String(time) {
  return formatMs2String(time * 1000)
}
