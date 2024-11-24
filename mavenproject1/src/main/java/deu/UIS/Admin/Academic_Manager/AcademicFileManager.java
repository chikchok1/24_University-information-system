/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Admin.Academic_Manager;

import java.io.*;
import java.util.*;

/**
 *
 * @author YangJinWon
 */

public class AcademicFileManager {

    private final String filePath;

    public AcademicFileManager(String filePath) {
        this.filePath = filePath;
    }

    public List<String[]> readData() throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] data = new String[5]; // 학사담당자번호, 이름, 생년월일, 휴대폰, 비밀번호
            int index = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    data[index++] = line.substring(line.indexOf(":") + 1).trim();
                }
                if (index == 5) { // 한 블록이 완성되면 추가
                    records.add(data.clone());
                    index = 0;
                }
            }
        }
        return records;
    }

    public void writeData(List<String[]> records) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] record : records) {
                writer.write("학사담당자번호: " + record[0] + "\n");
                writer.write("이름: " + record[1] + "\n");
                writer.write("생년월일: " + record[2] + "\n");
                writer.write("휴대폰: " + record[3] + "\n");

                // 비밀번호는 생년월일 뒷자리 7자리로 생성
                String password = record[2].contains("-") ? record[2].split("-")[1] : "0000000";
                writer.write("비밀번호: " + password + "\n\n");
            }
        }
    }
}
