package org.example;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.lang.StringBuilder;

public class Ls {


    public static void main (String[] args) {
        new Ls().ls(args);
    }

    private void ls(String[]args) {
        String fileOrDir = "something";
        char flag = 'n';
        boolean flagReverse = false;
        boolean flagOutput = false;
        String outputFile = "something";

        for(int i = 0; i < args.length; i++){
            switch (args[i]) {
                case "-l" -> {
                    if(flag == 'h') {
                        System.out.println("Command conflict");
                        return;
                    }
                    flag = 'l';
                }
                case "-h" -> {
                    if(flag == 'l') {
                        System.out.println("Command conflict");
                        return;
                    }
                    flag = 'h';
                }
                case "-r" -> flagReverse = true;
                case "-o" -> {
                    flagOutput = true;
                    if(args.length == ++i) {
                        System.out.println("Output file not specified");
                        return;
                    }
                    outputFile = args[i];
                }
                default -> {
                    if(!fileOrDir.equals("something")) {
                        System.out.println("Unknown commands: " + args[i] + "   " + fileOrDir);
                        return;
                    } else fileOrDir = args[i];
                }
            }
        }
        if(fileOrDir.equals("something")){
            System.out.println("Directory not specified");
            return;
        }

        File dir = new File(fileOrDir);
        if (!dir.exists()) {
            System.out.println("Directory not exist");
            return;
        }

        File[] files;
        if(dir.isFile()) files = new File[]{dir};
        else files = dir.listFiles();

        if (flagReverse) Arrays.sort(files, Collections.reverseOrder());
        else Arrays.sort(files);

        String s = switch (flag) {
            case 'l' -> l(files);
            case 'h' -> h(files);
            default -> none(files);
        };

        if (flagOutput) {
            try {
                Writer writer  = new FileWriter(outputFile);
                writer.write(s);
                writer.close();
            } catch (IOException e){
                e.printStackTrace();
            }

        } else
            System.out.println(s);
    }

    public static String l(File[] file) {
        StringBuilder s = new StringBuilder();
        for (File f: file) {
            if (f.isDirectory()) continue;
            s.append(String.format("%-15s      %d%d%d      %s      %s\n", f.getName(), f.canExecute() ? 1 : 0,
                    f.canRead() ? 1 : 0, f.canWrite() ? 1 : 0, f.lastModified(), f.length()));
        }
        return s.toString();
    }

    public static String h(File[] file) {
        StringBuilder s = new StringBuilder();
        for (File f: file) {
            if (f.isDirectory()) continue;
            long i = f.length();
            int index = 0;
            while (i>0) {
                i/=1024;
                index++;
            }
            String v = switch (index) {
                case 2 -> "Kb";
                case 3 -> "Mb";
                case 4 -> "Gb";
                case 0, 1 -> "b";
                default -> "10^" + (index-1) + "b";
            };
            s.append(String.format("%-15s   %c%c%c   %.3f %s\n", f.getName(), f.canExecute() ? 'r' : '-',
                    f.canRead() ? 'w' : '-', f.canWrite() ? 'x' : '-', f.length() / Math.pow(1024.0, index-1), v));
        }
        return s.toString();
    }

    public static String none(File[] file) {
        StringBuilder s = new StringBuilder();
        for (File f: file) {
            if (f.isDirectory()) continue;
            s.append(f.getName()).append("\n");
        }
        return s.toString();
    }
}