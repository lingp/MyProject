<template>
	<view class="content">
		<scroll-view 
		scroll-x="true" 
		:scroll-into-view="scrollItemId"
		class="index-tab">
			<view class="index-tab-item" 
			:class="{selected: tabSelectedIndex === index}" 
			@click="selectTab(index)"
			v-for="(item, index) in tabItems" 
			:key="index" 
			:id="'test'+index"
			>
				{{item.name}}
			</view>
		</scroll-view>
		
		<swiper class="main-swiper" :style="{height:mainSwiperHeight+'px'}"
			:current="tabSelectedIndex"
			@animationfinish="mainSwiperChange"
		>
			<swiper-item class="main-swiper-item" v-for="(item, index) in tabItems" :key="index">
				<scroll-view scroll-y="true" class="list-scroll" :style="{height:listScrollHeight+'px'}">
					<!-- 轮播图 -->
					<swiper 
					class="index-swiper"
					indicator-dots="true"
					indicator-color="rgba(254, 254, 254, .3)"
					indicator-active-color="#7F8D99"
					circular="true"
					autoplay="true"
					>
						<swiper-item>
							<view>
								<image src="../../static/image/demo/demo4.jpg" mode="widthFix"></image>
							</view>
						</swiper-item>
						<swiper-item>
							<view>
								<image src="../../static/image/demo/demo8.jpg" mode="widthFix"></image>
							</view>
						</swiper-item>
					</swiper>
					
					<!-- 分类 -->
					<view class="list-nav">
						<view class="list-nav-item" v-for="(item, index) in listNav">
							<view>
								<image :src="item.img_url"
								mode="aspectFill"></image>
							</view>
							<view class="list-nav-item-text" style="margin-top: -10upx;">
								{{item.name}}
							</view>
						</view>
					</view>
					
					<!-- 三图广告 -->
					<view class="three-ad">
						<view class="three-ad-left">
							<image src="../../static/image/demo/demo1.jpg"></image>
						</view>
						<view class="three-ad-right">
							<image src="../../static/image/demo/demo2.jpg" mode="widthFix"></image>
							<image src="../../static/image/demo/demo2.jpg" mode="widthFix"></image>
						</view>
					</view>
					<!-- 大图广告 -->
					<view class="big-ad">
						<view class="big-ad-title">
							<text>每日推荐</text>
						</view>
						<view class="big-ad-body">
							<image src="../../static/image/demo/demo4.jpg" mode="widthFix"></image>
						</view>
					</view>
					<!-- 列表 -->
					<view class="list">
						<view class="list-items" v-for="(item, index) in 10" :key="index">
							<view class="list-item">
								<image 
								src="../../static/image/demo/list/5.jpg"
								mode="widthFix"
								></image>
								<view>
									<view class="title">米家空调</view>
									<view class="desc">1.5匹配支流变频</view>
									<view class="prices">
										<view class="price-unit">¥</view> 2199
										<view class="origin-price">
											¥2699
										</view>
										
									</view>
								</view>
							</view>
							<view class="list-item">
								<image 
								src="../../static/image/demo/list/5.jpg"
								mode="widthFix"
								></image>
								<view>
									<view class="title">米家空调</view>
									<view class="desc">1.5匹配支流变频</view>
									<view class="prices">
										<view class="price-unit">¥</view> 2199
										<view class="origin-price">
											¥2699
										</view>
										
									</view>
								</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				listScrollHeight: '100',
				mainSwiperHeight: '100',
				scrollItemId: '',
				tabItems: [
					{
						"name": "推荐",
					},
					{
						"name": "水果",
					},
					{
						"name": "生鲜",
					},
					{
						"name": "体育",
					},
					{
						"name": "热点",
					},
					{
						"name": "财经",
					},
					{
						"name": "娱乐",
					},
				],
				tabSelectedIndex: 0,
				listNav: [
					{
						"img_url": "../../static/image/indexnav/1.png",
						"name": "新品分类"
					},
					{
						"img_url": "../../static/image/indexnav/2.gif",
						"name": "小米众筹"
					},
					{
						"img_url": "../../static/image/indexnav/3.gif",
						"name": "以旧换新"
					},
					{
						"img_url": "../../static/image/indexnav/4.gif",
						"name": "1分拼图"
					},
					{
						"img_url": "../../static/image/indexnav/5.gif",
						"name": "超值特卖"
					},
					{
						"img_url": "../../static/image/indexnav/6.gif",
						"name": "小米秒杀"
					},
					{
						"img_url": "../../static/image/indexnav/7.gif",
						"name": "真心想要"
					},
					{
						"img_url": "../../static/image/indexnav/8.gif",
						"name": "电视热卖"
					},
					{
						"img_url": "../../static/image/indexnav/9.gif",
						"name": "家电热卖"
					},
					{
						"img_url": "../../static/image/indexnav/10.gif",
						"name": "米粉卡"
					},
				]
			}
		},
		onLoad() {

		},
		onReady() {
			let that=this
			uni.getSystemInfo({
				success(res) {
					let winHeight = res.windowHeight // 获取窗口高度

					let listScroll = uni.createSelectorQuery().select(".list-scroll");
					listScroll.boundingClientRect(data => {
						that._data.listScrollHeight = winHeight-data.top
					}).exec()
					
					let mainSwiper = uni.createSelectorQuery().select(".main-swiper");
					listScroll.boundingClientRect(data => {
						that._data.mainSwiperHeight = winHeight-data.top
					}).exec()
					
				}
			})
		},
		methods: {
			selectTab(index) {
				this.tabSelectedIndex = index
			},
			mainSwiperChange(e) {
				this.tabSelectedIndex = e.target.current
				// TODO，需要优化
				let that = this
				that.$nextTick(()=> {
					// test 后面用ID,替换
					that.scrollItemId = "test" + that.tabSelectedIndex
					console.log(that.scrollItemId)
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		display: flex;
		overflow: hidden;
		flex-direction: column;
		box-sizing: border-box;
		
		// padding-bottom: calc(var(--window-bottom) + 20upx);
		.index-tab {
			width: 100%;
			display: inline-block;
			white-space: nowrap;
			box-sizing: border-box;
			.index-tab-item {
				text-align: center;
				display: inline-block;
				padding: 15upx;
				width: 120upx;
				color: #5D5D5D;
				&.selected {
					color: #FD6801;
					border-bottom: 4upx solid #FD6801;
				}
			}
		}
		
		.main-swiper {
		}
		
		.list-scroll {
			box-sizing: border-box;
		}
		
		.list-nav {
			box-sizing: border-box;
			width: 100%;
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			padding-top: 20upx;
			.list-nav-item {
				flex: 1;
				min-width: 20%;
				max-width: 20%;
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				font-size: 20upx;
				margin-bottom: 20upx;
				image {
					width: 60upx;
					height: 60upx;
				}
				.list-nav-item-text {
					margin-top: -10upx;
				}
			}
		}
		.three-ad {
			display: flex;
			flex-direction: row;
			box-sizing: border-box;
			.three-ad-left {
				width: 375upx;
				display: flex;
				align-items: stretch;
				image {
					height: 530upx;
				}
			}
			.three-ad-right {
				width: 375upx;
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				border-left: 10upx solid #FFFFFF;
				image {
					height: 260upx;
				}
			}
			
		}
		.big-ad {
			// display: flex;
			// flex-direction: column;
			.big-ad-title {
				height: 80upx;
				display: flex;
				// justify-content: center;
				align-items: center;
				padding-left: 20upx;
				text {
					font-size: 25upx;
					font-weight: bold;
				}
			}
			.big-ad-body {
				height: 300upx;
			}
		}
		.list {
			display: flex;
			flex-direction: column;
			.list-items {
				display: flex;
				padding: 0 2upx;
				flex-direction: row;
				justify-content: space-between;
				margin: 1upx 0;
				.list-item {
					width: 370upx;
					border: 1px solid #F1F1F1;
					image {
						height: 370upx;
					}
					.title {
						font-size: 30upx;
						padding: 10upx;
					}
					.desc {
						font-size: 25upx;
						padding-left: 10upx;
						color: #C4C4C4;
					}
					.prices {
						display: flex;
						flex-direction: row;
						padding-left: 10upx;
						color: #D95502;
						.price-unit {
							font-size:22upx;
							align-self: flex-start;
							line-height: 1.5!important;
						}
						.origin-price {
							margin-left: 20upx;
							align-self: flex-end;
							text-decoration: line-through;
							font-size: 20upx;
							color: #C4C4C4;
						}
					} 
				}
				
			}
		}
	}
</style>
