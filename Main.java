package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BlockingQueue<File> q = new ArrayBlockingQueue<File>(15);
        Boolean flag = true;
        File f;

        System.out.println("Directory path:");
        String dir = sc.nextLine();

        while (flag){
            f = new File(dir);
            if(f.exists() && f.isDirectory()){
                flag = false;
               new Thread(new Search(q, f)).start();
            }
            else{
                System.out.println("Wrong path!");
                System.out.println("Directory path: ");
                dir = sc.nextLine();
            }
        }
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Future<List<String>>> result = new ArrayList<>();


        for(int i = 0; i < 5; i++) {
            result.add(pool.submit(new Computing(q)));
        }

        FileWriter writer;
        try {
            writer = new FileWriter("D:\\Java\\LR_2 пример\\test2\\src\\com\\company\\result.txt");
            for(Future<List<String>> r: result) {
                List<String> l = r.get();
                for(String s: l) {
                    writer.write(s + "\n");
                    System.out.println(s + "\n");
                }
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

        sc.close();
        pool.shutdown();


    }
}
