import request from '../utils/axios/request'

// 登录
export function login (data) {
  return request({
    url: '',
    data: data,
    method: 'post'
  })
}

// 注册
export function reg (data) {
  return request({
    url: '',
    data: data,
    method: 'post'
  })
}

// 找回密码
export function getForgetPass (data) {
  return request({
    url: '',
    data: data,
    method: 'post'
  })
}

// 发送验证码
export function sendCode (data) {
  return request({
    url: '',
    data: data,
    method: 'post'
  })
}
