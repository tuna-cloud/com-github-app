import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const AccountKey = 'User-Account'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setUserAccount(account) {
  return Cookies.set(AccountKey, account)
}

export function removeUserAccount() {
  return Cookies.remove(AccountKey)
}

export function getUserAccount() {
  return Cookies.get(AccountKey)
}
