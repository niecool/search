package com.nie.interviews.ThreadConmunication;

import com.nie.interviews.ThreadConmunication.threadPool.ThreadPoolTest;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 专门用于多线程管道通信
 * @author zhaochengye
 * @date 2019-04-29 20:14
 */
public class Piped {
    private static Logger LOGGER = Logger.getLogger(Piped.class);
    public static void piped() throws IOException {
        //面向于字符 PipedInputStream 面向于字节
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();
        //输入输出流建立连接
        writer.connect(reader);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running");
                try {
                    for (int i = 0; i < 10; i++) {
                        writer.write(i+"");
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                } finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("running2");
                int msg = 0;
                try {
                    while ((msg = reader.read()) != -1) {
                        LOGGER.info("msg={}"+(char) msg);
                    }
                } catch (Exception e) {
                }
            }
        });
        t1.start();
        t2.start();
    }


}
