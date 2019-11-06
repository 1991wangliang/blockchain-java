package com.codingapi.blockchainp2p.netty.upd;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lorne
 * @date 2019-11-06
 * @description
 */
@Slf4j
@ChannelHandler.Sharable
public class UdpEventHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf data = packet.content();
        int length = data.readableBytes();
        byte[] bytes = new byte[length];
        data.readBytes(bytes);

        String content = new String(bytes);
        log.info("read->{}",content);
        ByteBuf out = ctx.alloc().buffer();
        out.writeBytes(("hi:"+content).getBytes());
        DatagramPacket response = new DatagramPacket(out,packet.sender());
        ctx.channel().writeAndFlush(response);
    }
}
