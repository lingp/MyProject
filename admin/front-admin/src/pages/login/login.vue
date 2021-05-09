<template>
  <div class="main">
    <div class="login">
      <div class="header" v-if="this.currentFormModel === 'login' || this.currentFormModel === 'login_msg'">
        <el-row>
          <el-col :span="12" >
            <div class="header-item " :class="{active : this.currentFormModel === 'login'}" @click="returnLogin">账号登录</div>
          </el-col>
          <el-col :span="12">
            <div class="header-item" :class="{active : this.currentFormModel === 'login_msg'}" @click="toLoginByMsg">短信登录</div>
          </el-col>
        </el-row>
      </div>
      <div class="header" v-else>
        <div class="header-reg">
          <span v-if="this.currentFormModel === 'reg'">账号注册</span>
          <span v-if="this.currentFormModel === 'forget_pass'">找回密码</span>
          <a href="javascript:;" @click="returnLogin">返回登录</a>
        </div>
      </div>
      <div class="content">
        <el-form ref="form" :model="form" label-width="auto">
          <el-form-item label="账号">
            <el-input v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" v-if="this.currentFormModel !== 'login_msg' && this.currentFormModel !== 'forget_pass'">
            <el-input type="password" v-model="form.password"></el-input>
          </el-form-item>
          <el-form-item label="重复密码" v-if="this.currentFormModel === 'reg' ">
            <el-input type="password" v-model="form.checkPass"></el-input>
          </el-form-item>
          <el-form-item label="验证码" v-if="this.currentFormModel === 'reg' ||  this.currentFormModel === 'login_msg' || this.currentFormModel === 'forget_pass'">
            <el-row>
              <el-col :span="11" >
                <el-input v-model="form.captcha" ></el-input>
              </el-col>
              <el-col :span="11" class="captcha-send-btn">
                <el-button class="btn" type="success" @click="onSubmit" >发送</el-button>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item class="text-items" v-if="this.currentFormModel !== 'reg'">
            <el-row>
              <el-col :span="12" class="text-item" >
                <div @click="toReg">账号注册</div>
              </el-col>
              <el-col :span="12" class="text-item">
                <div @click="toForgetPass">忘记密码</div>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item>
            <el-button class="btn" type="primary" @click="onSubmit">{{this.submitTxt}}</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="footer" v-if="this.currentFormModel !== 'reg'">
        <el-row>
          <el-col :span="8" class="footer-item">
            <a href="javascript:;" @mouseenter="selectIcon('qq')" @mouseleave="selectIcon('')">
              <svg-icon icon-class="qq"   :class-name="this.selecedIcon === 'qq' ? 'icon active': 'icon'"></svg-icon>
            </a>
          </el-col>
          <el-col :span="8" class="footer-item">
            <a href="javascript:;" @mouseenter="selectIcon('weixin')" @mouseleave="selectIcon('')">
              <svg-icon icon-class="weixin" :class-name="this.selecedIcon === 'weixin' ? 'icon active': 'icon'"></svg-icon>
            </a>
          </el-col>
          <el-col :span="8" class="footer-item" >
            <a href="javascript:;" @mouseenter="selectIcon('weibo')" @mouseleave="selectIcon('')">
              <svg-icon icon-class="weibo"  :class-name="this.selecedIcon === 'weibo' ? 'icon active': 'icon'"></svg-icon>
            </a>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      form: {
        name: '',
        password: '',
        checkPass: '',
        captcha: ''
      },
      currentFormModel: 'login', // 当前是表单是什么状态 login:账号/密码登录, reg:注册, forget_pass:忘记密码, login_msg:短信登录
      selecedIcon: ''
    }
  },
  computed: {
    submitTxt: function () {
      let txt = '立即登录'
      if (this.currentFormModel === 'reg') {
        txt = '立即注册'
      } else if (this.currentFormModel === 'forget_pass') {
        txt = '立即发送'
      }
      return txt
    }
  },
  methods: {
    onSubmit () {
      console.log('submit!')
    },
    returnLogin () {
      this.currentFormModel = 'login'
    },
    toReg () {
      this.currentFormModel = 'reg'
    },
    toForgetPass () {
      this.currentFormModel = 'forget_pass'
    },
    toLoginByMsg () {
      this.currentFormModel = 'login_msg'
    },
    selectIcon (icon) {
      this.selecedIcon = icon
    }
  }
}
</script>

<style lang="less" scoped>
  .icon {
    color: #cdcdcd;
    font-size: 40px;
    &.active {
      color: @mainColor;
    }
  }
  .captcha-send-btn {
    margin-left: 20px;
  }
  .main {
    background-color: @mainColor;
    width: 100vw;
    height: 100vh;
    .login {
      margin: 0 auto;
      width: 460px;
      height: 400px;
      position: relative;
      top: 50%; /*偏移*/
      transform: translateY(-50%);
      background-color: #FFFFFF;
      border-radius: 5px;
      .header {
        border-bottom: 1px solid #DCDFE6;
        padding: 10px 0px;
        .header-reg {
          text-align: center;
          color: #cacaca;
          font-size: 16px;
          line-height: 16px;
          color: #76b9f7;
          a {
            margin-left: 5px;
            font-size: 12px;
            color: red;
            cursor: pointer;
          }
        }
        .header-item {
          text-align: center;
          line-height: 16px;
          cursor: pointer;
          color: #cacaca;
          font-size: 16px;
          &.active {
            color: #76b9f7;
          }
        }
      }
      .content {
        padding: 20px 50px 0px 50px;
        .btn {
          width: 100%;
        }
        .text-items {
          font-size: 12px;
          color: #8D8D8D;
          padding: 0 2px;
          .text-item {
            font-size: 12px;
            cursor: pointer;
            &:first-child {
              text-align: left;
            }
            &:last-child {
              text-align: right;
            }
          }
        }
      }
      .footer {
        .footer-item {
          text-align: center;
          cursor: pointer;
        }
      }
    }
  }
</style>
