# 请求流程
SimpleChannelInboundHandler 主要方法顺序
1. handlerAdded
2. channelARegistered
3. channelActive
4. channelRead0
5. channelInactive
6. channelUnregistered
# 浏览器请求（Chrome）
连续请求 会发现其中两个请求并没有按照预期每次请求都执行
channel Inactive
channel Unregistered 
是因为http请求协议的问题
# 列出打开文件
list open files

 
lsof -i:8899
