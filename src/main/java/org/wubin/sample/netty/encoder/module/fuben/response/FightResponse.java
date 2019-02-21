 package org.wubin.sample.netty.encoder.module.fuben.response;

import org.wubin.sample.netty.serial.core.Serializer;

/**
 * @author wubin
 * @date 2019/02/21
 */
public class FightResponse extends Serializer {

    /**
     * 金币
     */
    private int gold;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    /* (non-Javadoc)
     * @see org.wubin.sample.netty.serial.core.Serializer#read()
     */
    @Override
    protected void read() {
        this.gold = readInt();
    }

    /* (non-Javadoc)
     * @see org.wubin.sample.netty.serial.core.Serializer#write()
     */
    @Override
    protected void write() {
        writeInt(gold);
    }
    
    
}
