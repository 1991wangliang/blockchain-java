## 基于Java语言构建区块链
[![Build Status](https://travis-ci.org/wangweiX/blockchain-java.svg?branch=master)](https://travis-ci.org/wangweiX/blockchain-java)

> 本系列文章的思路均来自：https://github.com/Jeiwan/blockchain_go

### 环境

- Maven 3.2.5
- JDK 1.7 +
- IDEA 安装插件 Lombok. [详情](https://wangwei.one/posts/917fb1e0.html)


### 文章

- [基本原型](https://wangwei.one/posts/build-blockchain-in-java-base-prototype.html)
- [工作量证明](https://wangwei.one/posts/build-blockchain-in-java-proof-of-work.html)
- [持久化存储](https://wangwei.one/posts/build-blockchain-in-java-data-persistence.html)
- [交易 - UTXO](https://wangwei.one/posts/build-blockchain-in-java-transaction-utxo.html)
- [地址 - 钱包](https://wangwei.one/posts/build-blockchain-in-java-wallet-address.html)
- [交易 - Merkle Tree](https://wangwei.one/posts/build-blockchain-in-java-transaction-merkle-tree.html)


### 反馈&提示
- 由于本人水平有限，若代码或文章存在错误或不足，还望指正；
- 如若[博客](https://wangwei.one/)网站无法打开，请开启翻墙代理全局模式；


### 目前尚未实现
- P2P网络的数据同步功能.
- 矿工挖矿的功能
- 智能合约功能

### 计划实现目标
完成比特币的完整网络结构，目前先将所有的功能放在一个节点上来实现。
P2P网络，由于运营商的限制，先提出接口实现现已内网跑通为目的。