package com.codingapi.blockchaincore;

import com.codingapi.blockchaincore.cli.CLI;

/**
 * 测试
 *
 * @author wangwei
 * @date 2018/02/05
 */
public class BlockchainTest {

    public static void main(String[] args) {
        try {
//            String[] argss = {"createwallet"};
//            String[] argss = {"createblockchain", "-address", "1PST8Hw7UoCa8s9Fr8J6KLErJDWmvHzig9"};
            // 1CNU3b3W8SEhmZKgB8u7MLdv3DoR2dTwhn
            // 1PST8Hw7UoCa8s9Fr8J6KLErJDWmvHzig9
            // 1Dk1SBDGhVVaDNS5mJLDUfk9txo7LD6uan
//            String[] argss = {"printaddresses"};
//            String[] argss = {"printchain"};
            String[] argss = {"getbalance", "-address", "1PST8Hw7UoCa8s9Fr8J6KLErJDWmvHzig9"};
//            String[] argss = {"send", "-from", "1CNU3b3W8SEhmZKgB8u7MLdv3DoR2dTwhn", "-to", "1PST8Hw7UoCa8s9Fr8J6KLErJDWmvHzig9", "-amount", "5"};
            CLI cli = new CLI(argss);
            cli.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
