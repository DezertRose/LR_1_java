package com.company;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Search implements Runnable{
    private File begin;
    private BlockingQueue<File> q;
    public static final File fin = new File("");

    public Search(BlockingQueue<File> tempQ, File tempBegin){
        this.q = tempQ;
        this.begin = tempBegin;
    }

    @Override
    public void run() {
        try{
            takeFile(begin);
            q.put(fin);
        }
        catch (InterruptedException iEx){
            iEx.printStackTrace();
        }

    }

    private void takeFile(File dir) throws InterruptedException{
        File[] fileArr = dir.listFiles();

        assert fileArr != null;
        for (File file : fileArr) {
            if (file.isDirectory()) {
                new Thread(new Search(q, file)).start();
            } else {
                q.put(file);
            }
        }
    }
}
