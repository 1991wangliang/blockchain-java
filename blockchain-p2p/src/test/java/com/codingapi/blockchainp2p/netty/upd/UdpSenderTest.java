package com.codingapi.blockchainp2p.netty.upd;

/**
 * @author lorne
 * @date 2019-11-06
 * @description
 */
class UdpSenderTest {

    public static void main(String[] args) {
        UdpSender udpSender = new UdpSender("255.255.255.255",9999);
        udpSender.bind();

        udpSender.sendPackage("123".getBytes());

        udpSender.sendPackage("234".getBytes());

        udpSender.sendPackage("345".getBytes());
    }
}