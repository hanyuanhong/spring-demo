package com.hanchen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.*;
import java.util.Base64;

public class FileTest {

    @Test
    public void testMultiplyFile() throws Exception {
        String inDirectoryPath = "C:\\Users\\hanchen\\Desktop\\转外网-韩源宏";
        String outFilePath = "C:\\Users\\hanchen\\Desktop\\转外网-韩源宏\\out\\out.txt";

        File directory = new File(inDirectoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File[] files = directory.listFiles(file -> !"out".equals(file.getName()));
        if (files == null || files.length == 0) {
            throw new Exception("empty");
        }

        JSONArray fileContentArray = new JSONArray();
        for (File file : files) {
            JSONObject json = new JSONObject();
            json.put("name", file.getName());
            json.put("content", readFileContentAsBase64(file));
            fileContentArray.add(json);
        }

        File outFile = new File(outFilePath);
        File outFileParent = outFile.getParentFile();
        if (!outFileParent.exists()) {
            outFileParent.mkdir();
        }
        if (!outFile.exists()) {
            outFile.createNewFile();
        }

        String fileContent = fileContentArray.toJSONString();

        PrintStream printStream = new PrintStream(outFilePath);
        System.setOut(printStream);
        System.out.println(fileContent);
    }

    @Test
    public void testParse() throws Exception {
        String outPath = "C:\\Users\\hanchen\\Desktop\\转外网-韩源宏\\out";
        String outFilePath = "C:\\Users\\hanchen\\Desktop\\转外网-韩源宏\\out\\out.txt";
        File file = new File(outFilePath);
        byte[] bytes;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            int size = inputStream.available();
            bytes = new byte[size];
            inputStream.read(bytes);
        }
        String content = new String(bytes);
        JSONArray array = JSON.parseArray(content);
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);
            writeFile(json.getString("content"), outPath + "\\" + json.getString("name"));
        }
    }

    private String readFileContentAsBase64 (File inFile) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inFile)) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return Base64.getEncoder().encodeToString(bytes);
        }
    }

    private void writeFile(String content, String outFilePath) throws IOException {
        File outFile = new File(outFilePath);
        try (FileOutputStream outputStream = new FileOutputStream(outFile)) {
            outputStream.write(Base64.getDecoder().decode(content));
        }
    }
}
