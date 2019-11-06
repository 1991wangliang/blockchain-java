package com.codingapi.blockchainp2p.netty.upd;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.net.InetSocketAddress;

/**
 * @author lorne
 * @date 2019-11-06
 * @description
 */
@Slf4j
public class UdpSender implements DisposableBean {

    private String host;
    private int scanPort;
    private EventLoopGroup group;
    private Bootstrap b;
    private Channel channel;


    public UdpSender(String host,int scanPort) {
        this.host = host;
        this.scanPort = scanPort;
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new UdpSenderHandler());

    }


    @Override
    public void destroy() throws Exception {
        group.shutdownGracefully();
    }

    public void bind(){
        try {
            channel = b.bind(0).sync().channel();
        } catch (InterruptedException e) {
            log.error(e.getLocalizedMessage(),e);
        }
    }


    public void sendPackage(byte[] data) {
        if(channel==null){
            throw new RuntimeException("not bind.");
        }
        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(data),
                new InetSocketAddress(host, scanPort))).syncUninterruptibly();
    }

}
