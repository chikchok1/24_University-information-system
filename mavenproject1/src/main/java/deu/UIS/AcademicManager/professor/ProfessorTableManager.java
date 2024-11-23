/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.UIS.AcademicManager.professor;

import deu.UIS.AcademicManager.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProfessorTableManager {
    private final DefaultTableModel tableModel;

    public ProfessorTableManager(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void loadProfessorsToTable(List<Professor> professors) {
        tableModel.setRowCount(0);
        for (Professor professor : professors) {
            tableModel.addRow(new Object[]{
                professor.getName(),
                professor.getProfessorNumber(),
                professor.getDepartment(),
                professor.getBirthDate(),
                professor.getPhone()
            });
        }
    }

    public Professor getSelectedProfessor(int selectedRow) {
        if (selectedRow < 0) return null;
        return new Professor(
            (String) tableModel.getValueAt(selectedRow, 0),
            (String) tableModel.getValueAt(selectedRow, 1),
            (String) tableModel.getValueAt(selectedRow, 2),
            (String) tableModel.getValueAt(selectedRow, 3),
            (String) tableModel.getValueAt(selectedRow, 4)
        );
    }
}
