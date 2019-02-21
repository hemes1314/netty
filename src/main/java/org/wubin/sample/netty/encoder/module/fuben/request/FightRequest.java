 package org.wubin.sample.netty.encoder.module.fuben.request;

import org.wubin.sample.netty.serial.core.Serializer;

/**
 * @author wubin
 * @date 2019/02/21
 */
public class FightRequest extends Serializer {

    /**
     * 副本id
     */
    private int fubenId;
    
    /**
     * 次数
     */
    private int count;

    public int getFubenId() {
        return fubenId;
    }

    public void setFubenId(int fubenId) {
        this.fubenId = fubenId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /* (non-Javadoc)
     * @see org.wubin.sample.netty.serial.core.Serializer#read()
     */
    @Override
    protected void read() {
        this.fubenId = readInt();
        this.count = readInt();
    }

    /* (non-Javadoc)
     * @see org.wubin.sample.netty.serial.core.Serializer#write()
     */
    @Override
    protected void write() {
        writeInt(fubenId);
        writeInt(count);
    }
}
