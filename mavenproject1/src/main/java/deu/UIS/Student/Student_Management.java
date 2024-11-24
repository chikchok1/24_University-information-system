/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.UIS.Student;

//dsada
import deu.UIS.Login.UserSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author YangJinWon
 */
public class Student_Management extends javax.swing.JFrame {

    private static final String LECTURE_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "lecture.txt").toString();
    private static final String STUDENT_COURSE_FILE_PATH = Paths.get(System.getProperty("user.home"), "data", "student_courses.txt").toString(); // 학생 수강 파일

    /**
     * Creates new form Main
     */
    public Student_Management() {
        initComponents();
        loadLecturesToTable(); // 강의 정보를 테이블에 로드
        loadStudentCoursesToTable(); // 학생 수강 강좌를 테이블에 로드
    }

    private void loadLecturesToTable() {
        DefaultTableModel model = (DefaultTableModel) lectureList1.getModel();
        model.setRowCount(0); // 기존 데이터 초기화

        try (BufferedReader reader = new BufferedReader(new FileReader(LECTURE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lectureData = line.split(", ");
                if (lectureData.length >= 7) {
                    String courseNumber = lectureData[0].replace("강좌 번호: ", "").trim();
                    String lectureName = lectureData[1].replace("강좌 이름: ", "").trim();
                    String credits = lectureData[3].replace("학점 수: ", "").trim();
                    String professor = lectureData[4].replace("담당 교수: ", "").trim();
                    String maxStudents = lectureData[6].replace("최대 학생 수: ", "").trim();

                    String currentStudents = "0"; // 기본값 설정
                    if (lectureData.length > 7 && lectureData[7].contains("현재 학생 수: ")) {
                        currentStudents = lectureData[7].replace("현재 학생 수: ", "").trim();
                    }

                    model.addRow(new Object[]{courseNumber, lectureName, credits, professor, maxStudents, currentStudents});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강의 정보 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudentCoursesToTable() {
        DefaultTableModel model = (DefaultTableModel) lectureList.getModel();
        model.setRowCount(0); // 기존 데이터 초기화

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_COURSE_FILE_PATH))) {
            String line;
            String loggedInStudentId = UserSession.getInstance().getUserId(); // 현재 로그인한 학생 ID
            while ((line = reader.readLine()) != null) {
                // 각 줄을 파싱하여 키-값 구조로 읽기
                String[] fields = line.split(", ");
                Map<String, String> dataMap = new HashMap<>();
                for (String field : fields) {
                    String[] keyValue = field.split(": ");
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }

                // 현재 학생 ID와 일치하는 정보만 로드
                if (loggedInStudentId.equals(dataMap.get("아이디"))) {
                    String courseNumber = dataMap.get("강좌 번호");
                    String lectureName = dataMap.get("강의 이름");
                    String credits = dataMap.get("학점");
                    String professor = dataMap.get("담당 교수");
                    String maxStudents = dataMap.get("최대 수강 인원");
                    String currentStudents = dataMap.get("현재 수강 인원");

                    // JTable에 추가
                    model.addRow(new Object[]{courseNumber, lectureName, credits, professor, maxStudents, currentStudents});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "학생 수강 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getTotalCredits() {
        int totalCredits = 0;
        String loggedInStudentId = UserSession.getInstance().getUserId(); // 현재 로그인한 학생 ID

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_COURSE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 각 줄을 파싱하여 키-값 구조로 읽기
                String[] fields = line.split(", ");
                Map<String, String> dataMap = new HashMap<>();
                for (String field : fields) {
                    String[] keyValue = field.split(": ");
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }

                // 현재 학생 ID와 일치하는 정보만 처리
                if (loggedInStudentId.equals(dataMap.get("아이디"))) {
                    String creditsStr = dataMap.get("학점");
                    int credits = parseCredits(creditsStr);
                    totalCredits += credits;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "학생 수강 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return totalCredits;
    }

    private int parseCredits(String creditStr) {
        // 숫자만 추출
        creditStr = creditStr.replaceAll("[^0-9]", "");
        if (creditStr.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(creditStr);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Title = new javax.swing.JLabel();
        Back = new javax.swing.JButton();
        S_PreCourseTitle = new javax.swing.JLabel();
        S_RequestTitle = new javax.swing.JLabel();
        S_Request = new javax.swing.JScrollPane();
        lectureList = new javax.swing.JTable();
        add = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        S_Request1 = new javax.swing.JScrollPane();
        lectureList1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Title.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        Title.setText("학생 수강신청 페이지");

        Back.setText("뒤로가기");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        S_PreCourseTitle.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        S_PreCourseTitle.setText("수강 신청 현황");

        S_RequestTitle.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        S_RequestTitle.setText("개설된 강의 목록");

        lectureList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "강좌 번호", "강의 이름", "학점", "담당교수", "최대 수강 인원", "현재 수강 인원"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lectureList.setRowHeight(25);
        lectureList.setShowHorizontalLines(true);
        lectureList.setShowVerticalLines(true);
        S_Request.setViewportView(lectureList);

        add.setText("추가");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        delete.setText("삭제");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        lectureList1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "강좌 번호", "강의 이름", "학점", "담당교수", "최대 수강 인원", "현재 수강 인원"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lectureList1.setRowHeight(25);
        lectureList1.setShowHorizontalLines(true);
        lectureList1.setShowVerticalLines(true);
        S_Request1.setViewportView(lectureList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(add))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(S_RequestTitle)
                                    .addComponent(S_PreCourseTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 130, Short.MAX_VALUE))
                            .addComponent(S_Request)
                            .addComponent(S_Request1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Back)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(delete)))))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22)
                .addComponent(S_RequestTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(S_Request1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(add)
                .addGap(5, 5, 5)
                .addComponent(S_PreCourseTitle)
                .addGap(9, 9, 9)
                .addComponent(S_Request, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete)
                    .addComponent(Back))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        // TODO add your handling code here:
        dispose();
        new S_Main().setVisible(true); //뒤로가기
    }//GEN-LAST:event_BackActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        addStudentToLecture(); // 추가 버튼 동작
    }//GEN-LAST:event_addActionPerformed

    private void addStudentToLecture() {
        int selectedRow = lectureList1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "수강할 강좌를 선택하세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) lectureList1.getModel();
        String courseNumber = model.getValueAt(selectedRow, 0).toString(); // 강좌 번호
        String lectureName = model.getValueAt(selectedRow, 1).toString();
        String creditsStr = model.getValueAt(selectedRow, 2).toString(); // 학점
        int courseCredits = parseCredits(creditsStr); // 학점 정수 변환
        String professor = model.getValueAt(selectedRow, 3).toString();
        String maxStudents = model.getValueAt(selectedRow, 4).toString();
        int currentStudents = Integer.parseInt(model.getValueAt(selectedRow, 5).toString()); // 현재 수강 인원

        // 이미 수강 신청되었는지 확인
        if (isLectureAlreadyAdded(courseNumber)) {
            JOptionPane.showMessageDialog(this, "이미 수강 신청된 강좌입니다.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return; // 이미 신청된 경우 메서드를 종료
        }

        if (currentStudents >= Integer.parseInt(maxStudents)) {
            JOptionPane.showMessageDialog(this, "최대 수강 인원을 초과할 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 총 학점 계산 및 18학점 초과 여부 확인
        int totalCredits = getTotalCredits();
        if (totalCredits + courseCredits > 18) {
            JOptionPane.showMessageDialog(this, "총 수강 학점이 18학점을 초과할 수 없습니다.\n현재 총 학점: " + totalCredits + "학점", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // "현재 수강 인원" 증가
        currentStudents++;
        model.setValueAt(String.valueOf(currentStudents), selectedRow, 5); // JTable 업데이트

        // 파일에 반영
        updateLectureFile(courseNumber, currentStudents);

        // 학생 수강 정보 추가
        addStudentCourse(courseNumber, lectureName, creditsStr, professor, maxStudents, String.valueOf(currentStudents));

        // 학생의 강좌 목록을 새로 로드
        loadStudentCoursesToTable();
    }

    private boolean isLectureAlreadyAdded(String courseNumber) {
        String loggedInStudentId = UserSession.getInstance().getUserId(); // 로그인한 학생 ID

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_COURSE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 각 줄을 파싱하여 키-값 구조로 읽기
                String[] fields = line.split(", ");
                Map<String, String> dataMap = new HashMap<>();
                for (String field : fields) {
                    String[] keyValue = field.split(": ");
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }

                // 현재 학생 ID와 강좌 번호가 모두 일치하면 이미 신청된 강좌임
                if (loggedInStudentId.equals(dataMap.get("아이디")) && courseNumber.equals(dataMap.get("강좌 번호"))) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "학생 수강 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false; // 중복되지 않은 경우
    }

    private void updateLectureFile(String courseNumber, int currentStudents) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(LECTURE_FILE_PATH));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (line.contains("강좌 번호: " + courseNumber)) {
                    String[] lectureData = line.split(", ");
                    if (lectureData.length <= 7) {
                        line += ", 현재 학생 수: " + currentStudents;
                    } else {
                        lectureData[7] = "현재 학생 수: " + currentStudents;
                        line = String.join(", ", lectureData);
                    }
                }
                updatedLines.add(line);
            }

            Files.write(Paths.get(LECTURE_FILE_PATH), updatedLines);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 업데이트 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudentCourse(String courseNumber, String lectureName, String credits, String professor, String maxStudents, String currentStudents) {
        String loggedInStudentId = UserSession.getInstance().getUserId(); // 로그인한 학생 ID
        String loggedInStudentName = UserSession.getInstance().getUserName(); // 로그인한 학생 이름

        // 새로 추가할 데이터 형식을 "키: 값" 형태로 설정
        String newEntry = String.format(
                "이름: %s, 아이디: %s, 강좌 번호: %s, 강의 이름: %s, 학점: %s, 담당 교수: %s, 최대 수강 인원: %s, 현재 수강 인원: %s",
                loggedInStudentName, loggedInStudentId, courseNumber, lectureName, credits, professor, maxStudents, currentStudents
        );

        try {
            // 파일을 읽어 이미 등록된 데이터인지 확인
            List<String> lines = Files.readAllLines(Paths.get(STUDENT_COURSE_FILE_PATH));
            for (String line : lines) {
                if (line.contains("아이디: " + loggedInStudentId) && line.contains("강좌 번호: " + courseNumber)) {
                    JOptionPane.showMessageDialog(this, "이미 수강 신청된 강좌입니다.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return; // 이미 존재하면 추가하지 않음
                }
            }

            // 중복되지 않은 경우에만 파일에 추가
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_COURSE_FILE_PATH, true))) {
                writer.write(newEntry);
                writer.newLine();
            }

            JOptionPane.showMessageDialog(this, "수강 신청이 완료되었습니다.");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "학생 수강 파일 업데이트 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        removeStudentFromLecture(); // 삭제 버튼 동작
    }//GEN-LAST:event_deleteActionPerformed
    private void removeStudentFromLecture() {
        int selectedRow = lectureList.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 강좌를 선택하세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) lectureList.getModel();

        // JTable에서 선택한 데이터 가져오기
        String courseNumber = model.getValueAt(selectedRow, 0).toString(); // 강좌 번호
        String loggedInStudentId = UserSession.getInstance().getUserId();

        // 파일에서 삭제
        deleteStudentCourseFromFile(loggedInStudentId, courseNumber);

        // 강좌 정보에서 현재 수강 인원 감소
        decreaseCurrentStudents(courseNumber);

        // JTable에서 데이터 삭제
        model.removeRow(selectedRow);
    }

    private void decreaseCurrentStudents(String courseNumber) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(LECTURE_FILE_PATH));
            List<String> updatedLines = new ArrayList<>();
            DefaultTableModel lectureModel = (DefaultTableModel) lectureList1.getModel(); // JTable 모델 가져오기

            for (String line : lines) {
                if (line.contains("강좌 번호: " + courseNumber)) {
                    String[] lectureData = line.split(", ");
                    if (lectureData.length > 7 && lectureData[7].contains("현재 학생 수: ")) {
                        // 현재 학생 수 감소
                        int currentStudents = Integer.parseInt(lectureData[7].replace("현재 학생 수: ", "").trim());
                        currentStudents = Math.max(0, currentStudents - 1); // 0 이하로 내려가지 않도록 설정
                        lectureData[7] = "현재 학생 수: " + currentStudents;
                        line = String.join(", ", lectureData);

                        // JTable의 해당 강좌의 현재 수강 인원 업데이트
                        for (int i = 0; i < lectureModel.getRowCount(); i++) {
                            if (lectureModel.getValueAt(i, 0).toString().equals(courseNumber)) {
                                lectureModel.setValueAt(String.valueOf(currentStudents), i, 5); // 현재 수강 인원 업데이트
                                break;
                            }
                        }
                    }
                }
                updatedLines.add(line);
            }

            // 파일 덮어쓰기
            Files.write(Paths.get(LECTURE_FILE_PATH), updatedLines);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강좌 정보 파일 업데이트 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudentCourseFromFile(String studentId, String courseNumber) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(STUDENT_COURSE_FILE_PATH));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                // 각 줄을 파싱하여 키-값 구조로 읽기
                String[] fields = line.split(", ");
                Map<String, String> dataMap = new HashMap<>();
                for (String field : fields) {
                    String[] keyValue = field.split(": ");
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }

                // 조건: 현재 학생 ID와 강좌 번호가 모두 일치하면 삭제
                if (studentId.equals(dataMap.get("아이디")) && courseNumber.equals(dataMap.get("강좌 번호"))) {
                    continue; // 해당 줄은 삭제
                }

                // 삭제하지 않는 줄은 업데이트 목록에 추가
                updatedLines.add(line);
            }

            // 파일 덮어쓰기
            Files.write(Paths.get(STUDENT_COURSE_FILE_PATH), updatedLines);

            JOptionPane.showMessageDialog(this, "선택한 강좌가 삭제되었습니다.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "학생 수강 파일 업데이트 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(Student_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Student_Management().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JLabel S_PreCourseTitle;
    private javax.swing.JScrollPane S_Request;
    private javax.swing.JScrollPane S_Request1;
    private javax.swing.JLabel S_RequestTitle;
    private javax.swing.JLabel Title;
    private javax.swing.JButton add;
    private javax.swing.JButton delete;
    private javax.swing.JTable lectureList;
    private javax.swing.JTable lectureList1;
    // End of variables declaration//GEN-END:variables
}
