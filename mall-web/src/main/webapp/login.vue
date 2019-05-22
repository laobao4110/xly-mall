<template>
	<div class="login">
		<div class="welcome"><img src="../static/img/welcome.png"></div>
		<div class="login-form">
			<div class="login-inp"><label>输入手机号</label><input type="text" v-model='phone'></div>
			<div class="login-inp span" style='position:relative'>
				<label>输入验证码</label>
				<input type="password" v-model='code'>
				<span v-if='time'>{{time}}</span>
				<span @click='sendCode' v-else>发送验证码</span>
			</div>
			<div class="login-inp" @click='login'><a href="#" >立即登录</a></div>
		</div>
	</div>
</template>

<script>
	import {SEND_CODE,LOGIN,AJAX,_layer,_checkMobile,HREF} from '@/comm.js';
    export default {
    	data(){
    		return ({
    			phone:'',     //电话
    			time:0,       //时间
    			code:'',      //验证码
    		});
    	},
    	methods:{
    		sendCode(){
    			if(!_checkMobile(this.phone)){
    				this.phone='';
    				return ;
    			}
    			AJAX(SEND_CODE,'post',{phone:this.phone.trim()},(data)=>{
    				if(data.retult){
    					this.time=parseInt(data.data);
    					let time=setInterval(()=>{
    						if(!this.time--){
    							clearInterval(time);
    						}
    					},1000);
    				}else{
    					_layer(data.message);
    				}
    			});
    		},
    		login(){
    			if(String(this.code).trim()){
    				AJAX(LOGIN,'POST',{phone:this.phone.trim(),phoneCode:String(this.code).trim()},(data)=>{
    					if(data.retult){
    						localStorage.setItem('token',data.data);
    						this.openIndex();
    					}else{
    						_layer(data.message);
    					}
    				});
    			}else{
    				_layer('请输入验证码');
    			}
    		},
            openIndex(){
                window.open(HREF+'/index.html');
            }
    	}
    }
</script>

<style scoped>
	a, a.link{color:#666;text-decoration:none;font-weight:500;}
	a, a.link:hover{color:#666;}
	h1,h2,h3,h4,h5,h6{font-weight: normal;}
	.login{width:100%;height:100%;background:url(../static/img/login-bg.png) no-repeat;background-size:cover;position:fixed;z-index:-10;}
	.welcome{width:100%;margin:25% 0;}
	.welcome img{width:100%;}
	.login-inp{margin:0 30px 15px 30px;border:1px solid #fff;border-radius:25px;}
	.login-inp label{width:8em;text-align:center;display:inline-block;color:#fff;}

	.login-inp input{line-height:40px;color:#fff;background-color:transparent;border:none;outline: none;}
	.span span{
		position: absolute;
		width: 8em;
	    height: 100%;
		display:inline-block;
		text-align:center;
		color:#fff;
	    line-height: 40px;
	    right: 0px;
	}
	.login-inp a{display:block;width:100%;text-align:center;line-height:40px;color:#fff;font-size:16px;letter-spacing:5px;}
	.login-txt{text-align:center;color:#fff;}
	.login-txt a{color:#fff;padding:0 5px;}
</style>