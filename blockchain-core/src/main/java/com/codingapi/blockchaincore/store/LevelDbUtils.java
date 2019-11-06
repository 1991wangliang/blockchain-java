package com.codingapi.blockchaincore.store;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.codingapi.blockchaincore.block.Block;
import com.codingapi.blockchaincore.transaction.TXOutput;
import com.codingapi.blockchaincore.util.SerializeUtils;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.util.Map;

/**
 * @author lorne
 * @date 2019-11-06
 * @description
 */
@Slf4j
public class LevelDbUtils {


    /**
     * 区块链数据文件
     */
    private static final String DB_FILE = "blockchain.db";

    /**
     * 区块桶Key
     */
    private static final String BLOCKS_BUCKET_KEY = "blocks";
    /**
     * 链状态桶Key
     */
    private static final String CHAINSTATE_BUCKET_KEY = "chainstate";

    /**
     * 最新一个区块
     */
    private static final String LAST_BLOCK_KEY = "l";

    private volatile static LevelDbUtils instance;

    public static LevelDbUtils getInstance() {
        if (instance == null) {
            synchronized (LevelDbUtils.class) {
                if (instance == null) {
                    instance = new LevelDbUtils();
                }
            }
        }
        return instance;
    }


    /**
     * block buckets
     */
    private Map<String, byte[]> blocksBucket;
    /**
     * chainstate buckets
     */
    @Getter
    private Map<String, byte[]> chainstateBucket;

    private DB db;

    private LevelDbUtils() {
        openDB();
        initBlockBucket();
        initChainStateBucket();
    }

    /**
     * 打开数据库
     */
    private void openDB() {
        try {
            Options options = new Options();
            DBFactory factory = new Iq80DBFactory();
            db = factory.open(new File(DB_FILE),options);
        } catch (Exception e) {
            log.error("Fail to open db ! ", e);
            throw new RuntimeException("Fail to open db ! ", e);
        }
    }


    /**
     * 初始化 blocks 数据桶
     */
    private void initBlockBucket() {
        byte[] blockBucketKey = SerializeUtils.serialize(BLOCKS_BUCKET_KEY);
        byte[] blockBucketBytes = db.get(blockBucketKey);
        if (blockBucketBytes != null) {
            blocksBucket = (Map) SerializeUtils.deserialize(blockBucketBytes);
        } else {
            blocksBucket = Maps.newHashMap();
            db.put(blockBucketKey, SerializeUtils.serialize(blocksBucket));
        }
    }


    /**
     * 初始化 blocks 数据桶
     */
    private void initChainStateBucket() {
        byte[] chainstateBucketKey = SerializeUtils.serialize(CHAINSTATE_BUCKET_KEY);
        byte[] chainstateBucketBytes = db.get(chainstateBucketKey);
        if (chainstateBucketBytes != null) {
            chainstateBucket = (Map) SerializeUtils.deserialize(chainstateBucketBytes);
        } else {
            chainstateBucket = Maps.newHashMap();
            db.put(chainstateBucketKey, SerializeUtils.serialize(chainstateBucket));
        }
    }



    /**
     * 保存最新一个区块的Hash值
     *
     * @param tipBlockHash
     */
    public void putLastBlockHash(String tipBlockHash) {
        blocksBucket.put(LAST_BLOCK_KEY, SerializeUtils.serialize(tipBlockHash));
        db.put(SerializeUtils.serialize(BLOCKS_BUCKET_KEY), SerializeUtils.serialize(blocksBucket));
    }

    /**
     * 查询最新一个区块的Hash值
     *
     * @return
     */
    public String getLastBlockHash() {
        byte[] lastBlockHashBytes = blocksBucket.get(LAST_BLOCK_KEY);
        if (lastBlockHashBytes != null) {
            return (String) SerializeUtils.deserialize(lastBlockHashBytes);
        }
        return "";
    }

    /**
     * 保存区块
     *
     * @param block
     */
    public void putBlock(Block block) {
        blocksBucket.put(block.getHash(), SerializeUtils.serialize(block));
        db.put(SerializeUtils.serialize(BLOCKS_BUCKET_KEY), SerializeUtils.serialize(blocksBucket));
    }

    /**
     * 查询区块
     *
     * @param blockHash
     * @return
     */
    public Block getBlock(String blockHash) {
        byte[] blockBytes = blocksBucket.get(blockHash);
        if (blockBytes != null) {
            return (Block) SerializeUtils.deserialize(blockBytes);
        }
        return null;
//        throw new RuntimeException("Fail to get block ! blockHash=" + blockHash);
    }


    /**
     * 清空chainstate bucket
     */
    public void cleanChainStateBucket() {
        try {
            chainstateBucket.clear();
        } catch (Exception e) {
            log.error("Fail to clear chainstate bucket ! ", e);
            throw new RuntimeException("Fail to clear chainstate bucket ! ", e);
        }
    }

    /**
     * 保存UTXO数据
     *
     * @param key   交易ID
     * @param utxos UTXOs
     */
    public void putUTXOs(String key, TXOutput[] utxos) {
        try {
            chainstateBucket.put(key, SerializeUtils.serialize(utxos));
            db.put(SerializeUtils.serialize(CHAINSTATE_BUCKET_KEY), SerializeUtils.serialize(chainstateBucket));
        } catch (Exception e) {
            log.error("Fail to put UTXOs into chainstate bucket ! key=" + key, e);
            throw new RuntimeException("Fail to put UTXOs into chainstate bucket ! key=" + key, e);
        }
    }


    /**
     * 查询UTXO数据
     *
     * @param key 交易ID
     */
    public TXOutput[] getUTXOs(String key) {
        byte[] utxosByte = chainstateBucket.get(key);
        if (utxosByte != null) {
            return (TXOutput[]) SerializeUtils.deserialize(utxosByte);
        }
        return null;
    }


    /**
     * 删除 UTXO 数据
     *
     * @param key 交易ID
     */
    public void deleteUTXOs(String key) {
        try {
            chainstateBucket.remove(key);
            db.put(SerializeUtils.serialize(CHAINSTATE_BUCKET_KEY), SerializeUtils.serialize(chainstateBucket));
        } catch (Exception e) {
            log.error("Fail to delete UTXOs by key ! key=" + key, e);
            throw new RuntimeException("Fail to delete UTXOs by key ! key=" + key, e);
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {
        try {
            db.close();
        } catch (Exception e) {
            log.error("Fail to close db ! ", e);
            throw new RuntimeException("Fail to close db ! ", e);
        }
    }

}
