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
