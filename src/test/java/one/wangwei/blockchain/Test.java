package one.wangwei.blockchain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

public class Test {

    public static final int expected_time = 2016*10;

    public static void main(String[] args) {
//        difficulty();

        BigDecimal bigDecimal = new BigDecimal(Utils.decodeCompactBits(486604799));
        bits(bigDecimal);
    }


    /**
     * 计算区块链的难度系数 当前区块的bits/创世块的bits
     * https://www.blockchain.com/btc/block-height/401592
     * http://suanbing.com/1853.html
     */
    public static void difficulty(){

        //602,784 区块的bits
        BigInteger bits = Utils.decodeCompactBits(0x171620d1);
        System.out.println(bits.toString(10));

        //创世块 bits 486604799
        BigInteger hash = Utils.decodeCompactBits(486604799);
        System.out.println(hash.toString(16));

        BigInteger difficulty = hash.divide(bits);
        System.out.println(difficulty);
    }

    /**
     * 计算目标值
     * http://suanbing.com/1850.html
     * https://www.blockchain.com/btc/block-height/32255
     * https://www.blockchain.com/btc/block-height/30240
     * 32255-2016+1= 30240
     * @param current_target
     */
    public static void bits(BigDecimal current_target){
        //actual_time = 32255 create_time  - 30240 create_time 转分钟
        double actual_time = ((dateParser("2009-12-30 05:58:59") - dateParser("2009-12-18 09:56:01"))/1000/60 );
        //v = actual_time / expected_time
        BigDecimal v = new BigDecimal(actual_time).divide(new BigDecimal(expected_time),20,ROUND_HALF_DOWN);
        // new_target = new_target * v
        BigDecimal new_target = current_target.multiply(v,MathContext.DECIMAL64);
        //to bits 实际值:486594663  区块链值:486594666
        //不确定误差是因为时间戳还是运算丢失精确度导致的.
        System.out.println(Utils.encodeCompactBits(new_target.toBigInteger()));

    }

    public static long dateParser(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return simpleDateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
