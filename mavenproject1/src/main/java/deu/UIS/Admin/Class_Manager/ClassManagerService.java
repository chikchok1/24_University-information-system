/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.Admin.Class_Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassManagerService {
    private final C_FileManager fileManager; // FileManager를 C_FileManager로 변경

    public ClassManagerService(String filePath) {
        this.fileManager = new C_FileManager(filePath); // FileManager를 C_FileManager로 변경
    }

    public List<ClassManager> getAllClassManagers() throws IOException {
        List<String[]> rawData = fileManager.readData(); // fileManager 호출 그대로 유지
        List<ClassManager> managers = new ArrayList<>();
        for (String[] data : rawData) {
            managers.add(new ClassManager(data[1], data[0], data[2], data[3], data[4]));
        }
        return managers;
    }

    public void addClassManager(ClassManager manager) throws IOException {
        List<ClassManager> managers = getAllClassManagers();
        List<String[]> rawData = new ArrayList<>();
        for (ClassManager m : managers) {
            rawData.add(new String[]{
                m.getClassNumber(), m.getName(), m.getBirthDate(), m.getPhone(), m.getPassword()
            });
        }
        rawData.add(new String[]{
            manager.getClassNumber(), manager.getName(), manager.getBirthDate(), manager.getPhone(), manager.getPassword()
        });
        fileManager.writeData(rawData); // fileManager 호출 그대로 유지
    }

    public void deleteClassManager(String classNumber) throws IOException {
        List<ClassManager> managers = getAllClassManagers();
        List<String[]> rawData = new ArrayList<>();
        for (ClassManager manager : managers) {
            if (!manager.getClassNumber().equals(classNumber)) {
                rawData.add(new String[]{
                    manager.getClassNumber(), manager.getName(), manager.getBirthDate(), manager.getPhone(), manager.getPassword()
                });
            }
        }
        fileManager.writeData(rawData); // fileManager 호출 그대로 유지
    }

    public void updateClassManager(ClassManager updatedManager) throws IOException {
        List<ClassManager> managers = getAllClassManagers();
        List<String[]> rawData = new ArrayList<>();
        for (ClassManager manager : managers) {
            if (manager.getClassNumber().equals(updatedManager.getClassNumber())) {
                rawData.add(new String[]{
                    updatedManager.getClassNumber(), updatedManager.getName(),
                    updatedManager.getBirthDate(), updatedManager.getPhone(), updatedManager.getPassword()
                });
            } else {
                rawData.add(new String[]{
                    manager.getClassNumber(), manager.getName(), manager.getBirthDate(), manager.getPhone(), manager.getPassword()
                });
            }
        }
        fileManager.writeData(rawData); // fileManager 호출 그대로 유지
    }
}

