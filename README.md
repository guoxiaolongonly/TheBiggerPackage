# TheBiggerPackage
饿了么，美团一键领取最大红包APP

## 需求分析

1.用户用的爽，所以只需要用户提供链接和手机号就可以直接领到大红包。
2.API获取，这个需要跟进。饿了么的领取好像不支持外部浏览器，技术难点需要突破，或者做成web页面。
3.从上面的需求可以扩展出，我们需要一堆小号来点红包。
4.由于饿了么验证，我们的小号都需要真实的手机号，所以需要一个获取号码的途径。
5.号码的途径（1.用户手动录入，2.通过短信接收平台获取 例：http://www.51ym.me/User/apidocs.html#errorlist）
6.然后就是本地缓存领取次数，防止单个帐号多次点击后续人数不够领大红包。
7.cookie过期处理，提醒用户录入

#Licence

本项目仅提供技术分享，作者不承担任何法律责任，不建议做任何商业用途使用。