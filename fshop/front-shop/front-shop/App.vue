<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
		/*每个页面公共css */
		@import url("common/css/animate.min.css");
		@import url("common/css/custom.css");
		@import url("common/css/common.css");
		page {
			height: 100%;
		}
		
		::-webkit-scrollbar {
		    display: none;
		    width: 0 !important;
		    height: 0 !important;
		    -webkit-appearance: none;
		    background: transparent;
		    color: transparent;
		}
		/* 解决小程序和app滚动条的问题 */
		/* #ifdef MP-WEIXIN || APP-PLUS */
			::-webkit-scrollbar {
			    display: none;
			    width: 0 !important;
			    height: 0 !important;
			    -webkit-appearance: none;
			    background: transparent;
			    color: transparent;
			  }
		/* #endif */
		
		/* 解决H5 的问题，ps:谷歌可以，火狐不行 */
		/* #ifdef H5 */
		    uni-scroll-view .uni-scroll-view::-webkit-scrollbar {
		    	/* 隐藏滚动条，但依旧具备可以滚动的功能 */
				display: none;
			    width: 0 !important;
			    height: 0 !important;
			    -webkit-appearance: none;
			    background: transparent;
			    color: transparent;
		    }
		/* #endif */
</style>
