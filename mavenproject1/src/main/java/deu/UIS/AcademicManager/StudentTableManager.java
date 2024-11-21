/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.AcademicManager;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentTableManager {
    private final DefaultTableModel tableModel;

    public StudentTableManager(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void loadStudentsToTable(List<Student> students) {
        tableModel.setRowCount(0); // 기존 데이터 초기화
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getName(),
                student.getStudentNumber(),
                student.getDepartment(),
                student.getGrade(),
                student.getBirthDate(),
                student.getPhone()
            });
        }
    }
}

