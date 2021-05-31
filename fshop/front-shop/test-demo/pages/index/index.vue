<template>
	<view class="content">
		<div style="width: 100%;height: 50upx;border: 1px solid red;"></div>
		<div style="width: 100%;border: 1upx solid blue;flex: 1;"></div>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello'
			}
		},
		onLoad() {

		},
		methods: {

		}
	}
</script>

<style>
	/* 1,2,3，才能保证flex:1生效，铺满剩下的div */
	/*可以放到公共css */
	page {
		height: 100%; /* 需要1 */ 
		display: flex; /* 需要2.1 */
	}
	.content {
		flex: 1; /* 需要2.1 */ 
		/*height: 100%; /* 需要2.2 设置高度，nvue无效 */ 
		display: flex;
		flex-direction: column;
		border: 1px solid red;
		box-sizing: border-box; /* 需要3 */ 
	}
</style>
