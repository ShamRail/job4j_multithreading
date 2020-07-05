package ru.job4j.sync;

import java.io.*;

public class ParseFile {

    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            for (int data = -2; data != -1; data = i.read()) {
                output.append(data);
            }
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            for (int data = -2; data != -1; data = i.read()) {
                if (data < 0x80) {
                    output.append(data);
                }
            }
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        try (OutputStream o = new FileOutputStream(file)) {
            o.write(content.getBytes());
        }
    }

}
