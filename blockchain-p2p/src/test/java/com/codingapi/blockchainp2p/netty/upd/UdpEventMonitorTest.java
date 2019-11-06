package com.codingapi.blockchainp2p.netty.upd;

import java.net.InetSocketAddress;

/**
 *  * @author lorne
 *  * @date 2019-11-06
 *  * @description
 */
class UdpEventMonitorTest {

    public static void main(String[] args) {
        UdpEventMonitor udpEventMonitor = new UdpEventMonitor(new InetSocketAddress(9999));
        udpEventMonitor.bind();
    }
}