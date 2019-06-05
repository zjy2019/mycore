package com.zjy.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zjy
 * @des
 * @date 2019/6/4
 */
public class FileNIO {
    public static void main(String[] args) {
        try {

            RandomAccessFile aFile = new RandomAccessFile("/opt2/test.txt", "rw");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);
            //int bytesWritten = inChannel.write(buf);
            while (bytesRead != -1) {
                System.out.println("Read " + bytesRead);
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
            aFile.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
