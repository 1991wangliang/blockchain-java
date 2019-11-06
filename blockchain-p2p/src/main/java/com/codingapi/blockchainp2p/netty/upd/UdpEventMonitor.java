package com.codingapi.blockchainp2p.netty.upd;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.beans.factory.DisposableBean;

import java.net.InetSocketAddress;

/**
 * @author lorne
 * @date 2019-11-06
 * @description
 */
public class UdpEventMonitor implements DisposableBean {


    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public UdpEventMonitor(InetSocketAddress address) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler( new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel)
                            throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new UdpEventHandler());
                    }
                } )
                .localAddress(address);
    }

    public Channel bind() {
        return bootstrap.bind().syncUninterruptibly().channel();
    }
    public void stop() {
        group.shutdownGracefully();
    }


    @Override
    public void destroy() throws Exception {
        stop();
    }
}
