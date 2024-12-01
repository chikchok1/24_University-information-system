/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.UIS.ClassManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author YangJinWon
 */
public class ClassBill extends javax.swing.JFrame {

    private static final String STUDENT_INFO_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "student_info.txt").toString();
    private static final String STUDENT_COURSE_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "student_courses.txt").toString();
    private static final String BILL_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "courses_bill.txt").toString();

    /**
     * Creates new form ClassBill
     */
    public ClassBill() {
        initComponents(); // 기존 초기화 코드
        processAndLoadBillData(); // 프로그램 실행 시 데이터 처리 및 JTable에 로드
        setupEventListeners(); // 이벤트 리스너 설정
    }

    /**
     * bill JTable에 마우스 클릭 이벤트 리스너 등록
     */
    private void setupEventListeners() {
        bill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                billMouseClicked(evt);
            }
        });
    }

    /**
     * bill JTable에서 클릭된 학생 정보를 기반으로 totalbill JTable 업데이트
     */
    private void billMouseClicked(java.awt.event.MouseEvent evt) {
        // bill JTable에서 선택된 행 확인
        int selectedRow = bill.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "학생 정보를 선택하세요.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DefaultTableModel billModel = (DefaultTableModel) bill.getModel();
        DefaultTableModel totalBillModel = (DefaultTableModel) totalbill.getModel();

        // 선택된 학생 정보 가져오기
        String studentName = billModel.getValueAt(selectedRow, 0).toString(); // 이름
        String studentId = billModel.getValueAt(selectedRow, 1).toString();   // 학번

        int totalCredits = 0;
        int totalFee = 0;

        // 모든 행을 순회하며 선택된 학생과 같은 학번의 강의 정보를 합산
        for (int i = 0; i < billModel.getRowCount(); i++) {
            if (billModel.getValueAt(i, 1).toString().equals(studentId)) {
                totalCredits += Integer.parseInt(billModel.getValueAt(i, 5).toString()); // 학점 합산
                totalFee += Integer.parseInt(billModel.getValueAt(i, 7).toString());     // 수강료 합산
            }
        }

        // totalbill JTable 초기화 및 데이터 추가
        totalBillModel.setRowCount(0); // 기존 데이터 제거
        totalBillModel.addRow(new Object[]{studentName, studentId, totalCredits, totalFee});
    }

    /**
     * student_courses.txt 파일을 읽고 courses_bill.txt에 데이터를 저장 후 JTable에 표시
     */
    private void processAndLoadBillData() {
        Map<String, String> studentDepartmentMap = new HashMap<>();

        // student_info.txt에서 학생 정보 읽어오기
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_INFO_FILE_PATH))) {
            String line;
            String studentId = "";
            String department = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("학번:")) {
                    studentId = line.split(":")[1].trim();
                } else if (line.startsWith("학과:")) {
                    department = line.split(":")[1].trim();
                } else if (line.isEmpty()) {
                    // 학번과 학과가 모두 채워졌다면 맵에 추가
                    if (!studentId.isEmpty() && !department.isEmpty()) {
                        studentDepartmentMap.put(studentId, department);
                    }
                    // 초기화
                    studentId = "";
                    department = "";
                }
            }

            // 마지막 학생 정보 추가 (파일 끝날 때)
            if (!studentId.isEmpty() && !department.isEmpty()) {
                studentDepartmentMap.put(studentId, department);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "student_info.txt 파일 읽기 오류: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // courses_bill.txt 데이터 처리
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BILL_FILE_PATH))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_COURSE_FILE_PATH))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(", ");
                    if (parts.length >= 7) {
                        // 필요한 정보 추출
                        String name = getValue(parts, "이름");
                        String studentId = getValue(parts, "아이디");
                        String courseNumber = getValue(parts, "강좌 번호");
                        String courseName = getValue(parts, "강의 이름");
                        int credits = Integer.parseInt(getValue(parts, "학점").replaceAll("[^0-9]", ""));
                        String professor = getValue(parts, "담당 교수");
                        int fee = credits * 45000; // 학점당 수강료 계산

                        // 학과 정보 가져오기 (없으면 기본값 설정)
                        String department = studentDepartmentMap.getOrDefault(studentId, "컴퓨터소프트웨어공학과");

                        // courses_bill.txt에 저장
                        writer.write(String.format("%s, %s, %s, %s, %s, %d, %s, %d", name, studentId, department, courseNumber, courseName, credits, professor, fee));
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "courses_bill.txt 저장 중 오류가 발생했습니다: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // JTable에 데이터 로드
        loadBillData();
    }

    /**
     * courses_bill.txt에서 데이터를 읽어와 bill JTable에 표시
     */
    private void loadBillData() {
        DefaultTableModel tableModel = (DefaultTableModel) bill.getModel();
        tableModel.setRowCount(0); // 기존 데이터 초기화

        List<Object[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BILL_FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 8) {
                    rows.add(new Object[]{
                        parts[0], // 이름
                        parts[1], // 학번
                        parts[2], // 학과
                        parts[3], // 강좌 번호
                        parts[4], // 강의 이름
                        parts[5], // 학점
                        parts[6], // 담당 교수
                        parts[7] // 수강료
                    });
                }
            }
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "파일 읽기 오류: " + e.getMessage(),
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        // 학번 기준으로 정렬
        rows.sort(Comparator.comparing(row -> row[1].toString())); // 학번(인덱스 1) 기준 정렬

        // 정렬된 데이터 JTable에 추가
        for (Object[] row : rows) {
            tableModel.addRow(row);
        }
    }

    /**
     * 주어진 키에 해당하는 값을 parts 배열에서 가져옴
     */
    private String getValue(String[] parts, String key) {
        for (String part : parts) {
            if (part.startsWith(key + ":")) {
                return part.split(":")[1].trim();
            }
        }
        return "";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bill = new javax.swing.JTable();
        check = new javax.swing.JButton();
        select = new javax.swing.JComboBox<>();
        field = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        totalbill = new javax.swing.JTable();
        before = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        jLabel1.setText("수강료 청구서");

        bill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "이름", "학번", "학과", "강좌번호", "강의 이름", "학점", "담당 교수", "수강료"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(bill);

        check.setText("조회");
        check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActionPerformed(evt);
            }
        });

        select.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "학번", "이름" }));
        select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectActionPerformed(evt);
            }
        });

        field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldActionPerformed(evt);
            }
        });

        search.setText("검색");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        totalbill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "이름", "학번", "총 학점", "총 수강료"
            }
        ));
        jScrollPane2.setViewportView(totalbill);

        before.setText("이전");
        before.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beforeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(before)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(check))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 60, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(search)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(507, 507, 507))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(check)
                    .addComponent(before))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActionPerformed
        loadBillData(); // courses_bill.txt의 데이터를 JTable에 로드
    }//GEN-LAST:event_checkActionPerformed

    private void selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectActionPerformed
        // select JComboBox에서 선택된 값 가져오기
        String selectedCriteria = select.getSelectedItem().toString();
    }//GEN-LAST:event_selectActionPerformed

    private void fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldActionPerformed
        // field JTextField 입력값 확인 (검색 트리거로 활용 가능)
    }//GEN-LAST:event_fieldActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // JTable 초기화
        DefaultTableModel tableModel = (DefaultTableModel) bill.getModel();

        // select에서 선택된 검색 기준 (학번/이름)
        String selectedCriteria = select.getSelectedItem().toString();
        String searchValue = field.getText().trim(); // field에 입력된 검색어

        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색어를 입력하세요.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return; // 검색어가 없으면 작업 종료
        }

        tableModel.setRowCount(0); // 기존 데이터 제거 (검색 결과만 표시하기 위해 초기화)

        try (BufferedReader reader = new BufferedReader(new FileReader(BILL_FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 8) {
                    String name = parts[0];      // 이름
                    String studentId = parts[1]; // 학번

                    // 선택된 기준에 따라 필터링
                    if (selectedCriteria.equals("학번") && studentId.equals(searchValue)) {
                        tableModel.addRow(parts); // 검색 결과를 JTable에 추가
                    } else if (selectedCriteria.equals("이름") && name.equals(searchValue)) {
                        tableModel.addRow(parts); // 검색 결과를 JTable에 추가
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 읽기 오류: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // 검색 결과가 없을 경우 알림
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_searchActionPerformed

    private void beforeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beforeActionPerformed
        dispose();
        new C_Main().setVisible(true);
    }//GEN-LAST:event_beforeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClassBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClassBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClassBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClassBill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClassBill().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton before;
    private javax.swing.JTable bill;
    private javax.swing.JButton check;
    private javax.swing.JTextField field;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton search;
    private javax.swing.JComboBox<String> select;
    private javax.swing.JTable totalbill;
    // End of variables declaration//GEN-END:variables
}
