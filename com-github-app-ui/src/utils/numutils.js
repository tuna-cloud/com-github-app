/**
 * 保留小数点的位数
 * @param value
 * @param precision
 */
export function roundNumber(value, precision) {
  var t = 1
  for (; precision > 0; precision--) {
    t *= 10
  }
  for (; precision < 0; precision++) {
    t /= 10
  }
  return Math.round(value * t) / t
}

/**
 * 将数字前边补充0，至长度为length
 * @param num
 * @param length
 * @returns {string}
 */
export function prefixInteger(num, length) {
  return (Array(length).join('0') + num).slice(-length)
}
