<template>
  <div class="login-container">
    <video style=" width: 100%; position: absolute;z-index: -1;" autoplay loop muted disablePictureInPicture
      controlslist="nodownload nofullscreen noremoteplayback">
      <source src="../../assets/keli.mp4" type="video/mp4" />
      <source src="../../assets/keli.mp4" type="video/webm" />
    </video>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
      label-position="left">

      <div class="title-container">
        <h3 class="title">晨曦影廊</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input ref="username" v-model="loginForm.username" placeholder="Username" name="username" type="text"
          tabindex="1" auto-complete="off" />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType"
          placeholder="Password" name="password" tabindex="2" auto-complete="off" @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <el-button :loading="loading" type="primary"
        style="float: left; background-color: #e3b175; width:100%;border: none;"
        @click.native.prevent="handleLogin">登录</el-button>

    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      // if (!validUsername(value)) {
      //   callback(new Error('Please enter the correct user name'))
      // } else {
      callback()
      // }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        console.log("router:",this.$router);
        
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {

            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #6b7e95;
$light_gray: #f86acd;
$cursor: #aed598;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: none;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;
      letter-spacing: 5px;
      caret-color: brown;

      &:-webkit-autofill {
        -webkit-text-fill-color: $light_gray !important;
        transition: background-color 5000s ease-in-out 0s;
      }
    }
  }


  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.3);
    background: rgb(253, 230, 202);
    border-radius: 2px;
    color: #f40c0c;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #fd6060;
$light_gray: #fcfbf7;

.login-container {
  min-height: 100%;
  width: 100%;
  // background-color: $bg;
  // background-image: url("../../assets/keli.gif");
  // background-repeat: no-repeat;
  // background-size: 100%;
  overflow: hidden;

  .login-form {
    z-index: 10;
    float: left;
    position: relative;
    width: 480px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 10%;
    overflow: hidden;
  }


  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
