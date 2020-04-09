window.app = {
	
	websocketUrl: 'ws://192.168.0.154:8889/ws',
	
	/**
	 * 后端地址
	 */
	serverUrl: 'http://192.168.0.154:8080',
	
	/**
	 * 图片地址
	 */
	imgServerUrl: 'http://192.168.80.143/',
	
	/**
	 * 判断字符串是否为空
	 * @param {Object} str
	 */
	isNotNull: function(str) {
		if (str != null && str != '' && str != undefined) {
			return true;
		}
		return false
	},
	
	/**
	 * 消息提示框，使用H5+, 默认mui不支持icon等属性
	 * @param {Object} msg
	 * @param {Object} type
	 */
	showToast: function(msg, type) {
		plus.nativeUI.toast(msg, {icon: 'image/' + type + ".png", verticalAlign: 'center'})
	},
	
	/**
	 * 保存用户的全局对象
	 * @param {Object} user
	 */
	setUserGlobalInfo: function(user) {
		var userInfoStr = JSON.stringify(user);
		plus.storage.setItem("userInfo", userInfoStr)
	},
	
	/**
	 * 获取用户的全局对象
	 */
	getUserGlobalInfo: function() {
		var userInfoStr = plus.storage.getItem("userInfo")
		return JSON.parse(userInfoStr)
	},
	
	userLogout: function() {
		plus.storage.removeItem("userInfo")
	},
	
	getContactList: function() {
		var contractList = plus.storage.getItem('contractList')
		if(!this.isNotNull(contractList)) {
			return []
		}
		return JSON.parse(contractList)
	},
	
	setContactList: function(contractList) {
		var contractList = JSON.stringify(contractList);
		plus.storage.setItem('contractList', contractList)
	},
	
	
	/**
	 * 保存用户的聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} flag 判断本条消息是我/朋友发送的， 1：我， 2：朋友
	 */
	saveUserChatHistory: function(myId, friendId, msg, flag)  {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		var chatHistoryListStr = plus.storage.getItem(chatKey)
		var chatHistoryList;
		if (me.isNotNull(chatHistoryListStr)) {
			chatHistoryList = JSON.parse(chatHistoryListStr)
		} else {
			chatHistoryList = []
		}
		var chatHistory = new me.ChatHistory(myId, friendId, msg, flag)
		chatHistoryList.push(chatHistory)
		
		plus.storage.setItem(chatKey, JSON.stringify(chatHistoryList))
	},
	
	/**
	 * 删除用户的聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 */
	deleteUserChatHistory: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		plus.storage.removeItem(chatKey)
	},
	/**
	 * 获取用户聊天记录
	 * @param {Object} myId
	 * @param {Object} friendId
	 */
	getUserChatHistory: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-" + myId + "-" + friendId;
		var chatHistoryListStr = plus.storage.getItem(chatKey);
		var chatHistoryList;
		if (me.isNotNull(chatHistoryListStr)) {
			chatHistoryList = JSON.parse(chatHistoryListStr);
		} else {
			chatHistoryList = [];
		}
		return chatHistoryList;
	},
	
	/**
	 * 保存用户最新的快照
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} isRead
	 */
	saveUserChatSnapshot: function(myId, friendId, msg, isRead) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		
		var chatSnapshotListStr = plus.storage.getItem(chatKey)
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			chatSnapshotList = JSON.parse(chatSnapshotListStr)
			// 循环快照list，把已经存在的friendId的元素删了，后续插入最新的
			for(var i = 0; i < chatSnapshotList.length; i++) {
				if (chatSnapshotList[i].friendId == friendId) {
					chatSnapshotList.splice(i, 1);
					break;
				}
			}
		} else {
			chatSnapshotList = []
		}
		
		var snapshotObj = new me.ChatSnapshot(myId, friendId, msg, isRead)
		chatSnapshotList.unshift(snapshotObj)
		
		plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList))
		
	},
	/**
	 * 删除聊天快照
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} isRead
	 */
	deleteUserChatSnapshot: function(myId, friendId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		
		var chatSnapshotListStr = plus.storage.getItem(chatKey)
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			chatSnapshotList = JSON.parse(chatSnapshotListStr)
			// 循环快照list，把已经存在的friendId的元素删了
			for(var i = 0; i < chatSnapshotList.length; i++) {
				if (chatSnapshotList[i].friendId == friendId) {
					chatSnapshotList.splice(i, 1);
					break;
				}
			}
		} else {
			chatSnapshotList = []
		}		
		plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList))
	},
	/**
	 * 获取用户快照列表
	 * @param {Object} myId
	 */
	getUserChatSnapshot: function(myId) {
		var me = this;
		var chatKey = "chat-snapshot" + myId;
		var chatSnapshotListStr = plus.storage.getItem(chatKey)
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			chatSnapshotList = JSON.parse(chatSnapshotListStr)
		} else {
			chatSnapshotList = []
		}
		return chatSnapshotList
	},
	
	/**
	 * 根据通讯录缓存获取朋友的信息
	 * @param {Object} friendId
	 */
	getFriendFromContractList: function(friendId) {
		var contractListStr = plus.storage.getItem("contractList")
		
		if (this.isNotNull(contractListStr)) {
			var contractList = JSON.parse(contractListStr)
			for (var i = 0; i < contractList.length; i++) {
				var friend = contractList[i]
				if (friend.friendUserId == friendId) {
					return friend
				}
			}
			
		} else {
			return null
		}
	},
	/**
	 * 将消息改成已读
	 * @param {Object} myId
	 * @param {Object} friendId
	 */
	readUserChatSnapshot: function(myId, friendId) {
		var me = this
		var chatKey = "chat-snapshot" + myId;
		var chatSnapshotListStr = plus.storage.getItem(chatKey)
		var chatSnapshotList;
		if (me.isNotNull(chatSnapshotListStr)) {
			chatSnapshotList = JSON.parse(chatSnapshotListStr)
			// 循环快照list，把已经存在的friendId的元素删了，后续插入最新的
			for(var i = 0; i < chatSnapshotList.length; i++) {
				var item = chatSnapshotList[i]
				if (item.friendId == friendId) {
					item.isRead = true
					chatSnapshotList.splice(i, 1, item); // 替换原有的快照
					break;
				}
			}
		} else {
			chatSnapshotList = []
		}
		plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList))// 替换原有快照列表
		return chatSnapshotList
	},
	CONNECT: 1, // 第一次(或重连)初始化连接
	CHAT: 2, // 聊天消息
	SIGNED: 3, // 消息签收
	KEEPALIVE: 4, // 客户端保持心跳
	PULL_FRIEND: 5, // 重新拉取好友
	
	CHAT_MSG_ME: 1, // 标识"我的消息"
	CHAT_MSG_FRIENDS: 2, //  标识"朋友的消息"
	
	/**
	 * 跟后端的ChatMsg聊天模型对象保持一致
	 * @param {Object} sendId
	 * @param {Object} receiveId
	 * @param {Object} msg
	 * @param {Object} msgId
	 */
	ChatMsg: function(sendId, receiveId, msg, msgId) {
		this.sendId = sendId;
		this.receiveId = receiveId;
		this.msg = msg;
		this.msgId = msgId;
	},
	
	/**
	 * 构建消息模型对象
	 * @param {Object} action
	 * @param {Object} chatMsg
	 * @param {Object} extandD
	 */
	DataContent: function(action, chatMsg, extand) {
			this.action = action;
			this.chatMsg = chatMsg;
			this.extand = extand;
	},
	
	/**
	 * 单个聊天记录的对象
	 * @param {Object} action
	 * @param {Object} chatMsg
	 * @param {Object} extandD
	 */
	ChatHistory: function(myId, friendId, msg, flag) {
			this.myId = myId;
			this.friendId = friendId;
			this.msg = msg;
			this.flag = flag;
	},
	
	/**
	 * 快照对象
	 * @param {Object} myId
	 * @param {Object} friendId
	 * @param {Object} msg
	 * @param {Object} isRead 判断消息是否已读/未读
	 */
	ChatSnapshot: function(myId, friendId, msg, isRead) {
		this.myId = myId;
		this.friendId = friendId;
		this.msg = msg;
		this.isRead = isRead;
	},
	
}