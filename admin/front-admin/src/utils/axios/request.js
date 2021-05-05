import axios from 'axios'
import { Message } from 'element-ui'

// 根据环境变量获取 - start
let baseUrl = ''
let timeout = 10000
// 根据环境变量获取 - end
const service = axios.create({
  baseURL: baseUrl,
  timeout: timeout
})

// request 拦截器
service.interceptors.request.use(function (config) {
  // Do something before request is sent
  config.headers['token'] = '' // TODO
  config.headers['timestamp '] = new Date().getTime()
  config.headers['Content-type'] = config.headers['Content-type'] || 'application/json; charset=UTF-8'
  return config
}, function (error) {
  // Do something with request error
  return Promise.reject(error)
})

// response 拦截器
service.interceptors.response.use(function (response) {
  // Do something with response data
  // TODO
  if (response.code === 200) {

  } else { // TODO，处理失败
    return Promise.reject(new Error('失败'))
  }
  return response
}, function (error) {
  // Do something with response error
  Message({
    message: error.message,
    type: 'error',
    duration: 5 * 1000
  })
  return Promise.reject(error)
})

export default service
