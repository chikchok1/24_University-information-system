    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package deu.UIS.Admin;
    import java.util.List;
    import javax.swing.table.DefaultTableModel;

    public class A_TableManager {
        private final DefaultTableModel tableModel;

        public A_TableManager(DefaultTableModel tableModel) {
            this.tableModel = tableModel;
        }

        public void loadData(List<String[]> records) {
            tableModel.setRowCount(0); // 기존 데이터 초기화
            for (String[] record : records) {
                tableModel.addRow(record);
            }
        }

        public void addRow(String[] record) {
            tableModel.addRow(record);
        }

        public void removeRow(int rowIndex) {
            tableModel.removeRow(rowIndex);
        }
    }
