import axios from 'axios'
import {Message} from 'element-ui'

const service = axios.create({
  baseURL: process.env.BASE_API,
  timeout: 5000
})

// request拦截器
service.interceptors.request.use(config => {
  let token = ''
  if (!token) {
    config.headers['token'] = token
  }
  return config
}, error => {
  return Promise.reject(error)
})

// response拦截器
service.interceptors.response.use(resp => {
  let res = resp.data
  if (res.code !== 20000) {
    // TODO
    return Promise.reject(new Error(res.message || 'Error'))
  }
  return res
}, error => {
  console.log('err:' + error)
  Message({
    message: error.message,
    type: 'error',
    duration: 5 * 1000
  })
  return Promise.reject(error)
})

export default service
