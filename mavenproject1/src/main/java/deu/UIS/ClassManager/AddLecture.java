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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author YangJinWon
 */
public class AddLecture extends javax.swing.JFrame {

    private static final String CLASS_INFO_PATH = Paths.get(System.getProperty("user.home"), "data", "CourseInfo.txt").toString();
    private static final String LECTURE_INFO_PATH = Paths.get(System.getProperty("user.home"), "data", "lecture.txt").toString();
    private Class_Management parent; // Class_Management 참조

    // 기본 생성자
    public AddLecture() {
        this(null); // null을 기본값으로 사용
    }

    public AddLecture(Class_Management parent) {
        this.parent = parent; // 참조 저장
        initComponents();
        loadCoursesToTable(); // 강좌 정보를 JTable에 로드
        loadLecturesToTable(); // 강의 정보를 jTable2에 로드
        addMouseListenerToTable(); // JTable 클릭 이벤트 추가

    }

    private void loadCoursesToTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // 기존 데이터 초기화

        try (BufferedReader reader = new BufferedReader(new FileReader(CLASS_INFO_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] courseData = line.split(",");
                if (courseData.length == 5) {
                    String courseNumber = courseData[0].replace("강좌 번호: ", "").trim();
                    String courseName = courseData[1].replace("강좌 이름: ", "").trim();
                    String courseDepartment = courseData[2].replace("담당 학과: ", "").trim();
                    String courseCredits = courseData[3].replace("학점 수: ", "").trim();
                    model.addRow(new Object[]{courseNumber, courseName, courseDepartment, courseCredits});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강좌 정보 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMouseListenerToTable() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    // 선택된 강좌 정보 확인
                    String courseNumber = jTable1.getValueAt(selectedRow, 0).toString();
                    String courseName = jTable1.getValueAt(selectedRow, 1).toString();
                    String courseDepartment = jTable1.getValueAt(selectedRow, 2).toString();
                    String courseCredits = jTable1.getValueAt(selectedRow, 3).toString();

                    /*디버깅 메시지 출력 (필요하면 삭제 가능)
                    System.out.println("선택된 강좌 번호: " + courseNumber);
                    System.out.println("선택된 강좌 이름: " + courseName);*/
                }
            }
        });
    }

    private boolean isLectureCreated(String courseNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LECTURE_INFO_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("강좌 번호: " + courseNumber)) {
                    return true; // 해당 강좌 번호로 개설된 강의가 있음
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강의 정보 파일 확인 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false; // 강의가 개설된 적이 없음
    }

    // 강좌 선택 여부 확인
    private String getSelectedCourseNumber() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "강좌를 선택하세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return jTable1.getValueAt(selectedRow, 0).toString(); // 선택된 강좌 번호 반환
    }

// 강좌 상태 확인 및 버튼 상태 변경
    private void checkLectureStatus() {
        String courseNumber = getSelectedCourseNumber();
        if (courseNumber == null) {
            return; // 강좌 선택되지 않으면 중단
        }
        if (isLectureCreated(courseNumber)) {
            JOptionPane.showMessageDialog(this, "이 강좌는 이미 강의가 개설된 적이 있어 수정 및 삭제가 불가능합니다.", "Info", JOptionPane.INFORMATION_MESSAGE);
            setEditingButtonsEnabled(false);
        } else {
            setEditingButtonsEnabled(true);
        }
    }

// 수정 및 삭제 버튼 활성화/비활성화
    private void setEditingButtonsEnabled(boolean isEnabled) {
        modify.setEnabled(isEnabled);
        delete.setEnabled(isEnabled);
    }

    private void editCourse() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "강좌를 선택하세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 선택된 강좌 번호 가져오기
        String courseNumber = jTable1.getValueAt(selectedRow, 0).toString();

        if (isLectureCreated(courseNumber)) {
            JOptionPane.showMessageDialog(this, "이 강좌는 이미 강의가 개설된 적이 있어 수정할 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 강좌 정보 수정
        String newCourseName = JOptionPane.showInputDialog(this, "새 강좌 이름을 입력하세요:", jTable1.getValueAt(selectedRow, 1));
        if (newCourseName == null || newCourseName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "강좌 이름을 입력해야 합니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 강좌 정보 파일에서 수정
        try {
            List<String> lines = Files.readAllLines(Paths.get(CLASS_INFO_PATH));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (line.contains("강좌 번호: " + courseNumber)) {
                    String[] parts = line.split(",");
                    parts[1] = " 강좌 이름: " + newCourseName;
                    updatedLines.add(String.join(",", parts)); // 수정된 내용으로 추가
                } else {
                    updatedLines.add(line); // 수정 대상이 아닌 경우 유지
                }
            }

            Files.write(Paths.get(CLASS_INFO_PATH), updatedLines);
            JOptionPane.showMessageDialog(this, "강좌 정보가 수정되었습니다.");
            loadCoursesToTable(); // 테이블 갱신
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강좌 수정 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadLecturesToTable() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel(); // jTable2의 모델 가져오기
        model.setRowCount(0); // 기존 데이터 초기화

        try (BufferedReader reader = new BufferedReader(new FileReader(LECTURE_INFO_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 쉼표로 데이터 분리
                String[] parts = line.split(",");
                if (parts.length >= 7) { // 최소 7개의 필드가 있어야 유효
                    String lectureName = parts[1].replace("강좌 이름: ", "").trim();
                    String professor = parts[4].replace("담당 교수: ", "").trim();
                    String minStudents = parts[5].replace("최소 학생 수: ", "").trim();
                    String maxStudents = parts[6].replace("최대 학생 수: ", "").trim();

                    // jTable2에 데이터 추가
                    model.addRow(new Object[]{lectureName, professor, minStudents, maxStudents});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강의 정보 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        add = new javax.swing.JButton();
        professor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        minStudent = new javax.swing.JTextField();
        maxStudent = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        before = new javax.swing.JButton();
        modify = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        jLabel1.setText("강의 추가 페이지");

        jLabel2.setText("담당 교수");

        jLabel4.setText("수강 가능 최소 학생 수 ");

        add.setText("추가");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        professor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                professorActionPerformed(evt);
            }
        });

        jLabel3.setText("수강 가능 최대 학생 수");

        minStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minStudentActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "강좌 번호", "강좌 이름", "담당 학과", "학점 수"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        before.setText("이전");
        before.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beforeActionPerformed(evt);
            }
        });

        modify.setText("수정");
        modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyActionPerformed(evt);
            }
        });

        delete.setText("삭제");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        jLabel5.setText("강좌 목록");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "강의 이름", "담당 교수", "최소 학생 수", "최대 학생 수"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel6.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        jLabel6.setText("강의 목록");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(modify)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(delete)))))
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(before)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(add))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(professor, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(minStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(maxStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(494, 494, 494))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(professor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(minStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(maxStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(before)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(add)
                        .addComponent(delete)
                        .addComponent(modify)))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "강좌를 선택하세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // JTable에서 선택된 강좌 정보 가져오기
        String courseNumber = jTable1.getValueAt(selectedRow, 0).toString();
        String courseName = jTable1.getValueAt(selectedRow, 1).toString();
        String courseDepartment = jTable1.getValueAt(selectedRow, 2).toString();
        String courseCredits = jTable1.getValueAt(selectedRow, 3).toString();

        // 추가 입력값 가져오기
        String professorName = professor.getText().trim();
        String minStudentCount = minStudent.getText().trim();
        String maxStudentCount = maxStudent.getText().trim();

        // 입력값 확인
        if (professorName.isEmpty() || minStudentCount.isEmpty() || maxStudentCount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 강의 정보 문자열 생성
        String lectureData = String.format("강좌 번호: %s, 강좌 이름: %s, 담당 학과: %s, 학점 수: %s, 담당 교수: %s, 최소 학생 수: %s, 최대 학생 수: %s",
                courseNumber, courseName, courseDepartment, courseCredits, professorName, minStudentCount, maxStudentCount);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LECTURE_INFO_PATH, true))) {
            // 파일에 저장
            writer.write(lectureData);
            writer.newLine();

            // JTable2에 즉시 반영
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.addRow(new Object[]{courseName, professorName, minStudentCount, maxStudentCount});

            JOptionPane.showMessageDialog(this, "강의 정보가 성공적으로 저장되고 테이블에 반영되었습니다!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // 입력 필드 초기화
        professor.setText("");
        minStudent.setText("");
        maxStudent.setText("");
    }//GEN-LAST:event_addActionPerformed

    private void professorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_professorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_professorActionPerformed

    private void minStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minStudentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minStudentActionPerformed

    private void beforeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beforeActionPerformed
        dispose();
        new C_Main().setVisible(true);
    }//GEN-LAST:event_beforeActionPerformed

    private void modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "수정할 강좌를 선택하세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String courseNumber = jTable1.getValueAt(selectedRow, 0).toString();

        // 강의 개설 여부 확인
        if (isLectureCreated(courseNumber)) {
            JOptionPane.showMessageDialog(this, "이 강좌는 이미 강의가 개설된 적이 있어 수정할 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 기존 값 가져오기
        String currentCourseName = jTable1.getValueAt(selectedRow, 1).toString();
        String currentDepartment = jTable1.getValueAt(selectedRow, 2).toString();
        String currentCredits = jTable1.getValueAt(selectedRow, 3).toString();

        // 새 값 입력받기
        String newCourseName = JOptionPane.showInputDialog(this, "새 강좌 이름을 입력하세요:", currentCourseName);
        if (newCourseName == null || newCourseName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "강좌 이름을 입력해야 합니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newDepartment = JOptionPane.showInputDialog(this, "새 담당 학과를 입력하세요:", currentDepartment);
        if (newDepartment == null || newDepartment.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "담당 학과를 입력해야 합니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newCredits = JOptionPane.showInputDialog(this, "새 학점 수를 입력하세요:", currentCredits);
        if (newCredits == null || newCredits.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "학점 수를 입력해야 합니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 강좌 정보 파일에서 수정
        try {
            List<String> lines = Files.readAllLines(Paths.get(CLASS_INFO_PATH));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (line.contains("강좌 번호: " + courseNumber)) {
                    String[] parts = line.split(",");
                    parts[1] = " 강좌 이름: " + newCourseName;  // 새 강좌 이름
                    parts[2] = " 담당 학과: " + newDepartment; // 새 담당 학과
                    parts[3] = " 학점 수: " + newCredits;     // 새 학점 수
                    updatedLines.add(String.join(",", parts)); // 수정된 내용으로 추가
                } else {
                    updatedLines.add(line); // 수정 대상이 아닌 경우 유지
                }
            }

            Files.write(Paths.get(CLASS_INFO_PATH), updatedLines);
            JOptionPane.showMessageDialog(this, "강좌 정보가 수정되었습니다.");
            loadCoursesToTable(); // 테이블 갱신
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강좌 수정 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_modifyActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 강좌를 선택하세요.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String courseNumber = jTable1.getValueAt(selectedRow, 0).toString();

        // 강의 개설 여부 확인
        if (isLectureCreated(courseNumber)) {
            JOptionPane.showMessageDialog(this, "이 강좌는 이미 강의가 개설된 적이 있어 삭제할 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 삭제 확인
        int confirm = JOptionPane.showConfirmDialog(this, "강좌를 삭제하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // 강좌 정보 파일에서 삭제
        try {
            List<String> lines = Files.readAllLines(Paths.get(CLASS_INFO_PATH));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (!line.contains("강좌 번호: " + courseNumber)) {
                    updatedLines.add(line);
                }
            }

            Files.write(Paths.get(CLASS_INFO_PATH), updatedLines);
            JOptionPane.showMessageDialog(this, "강좌가 삭제되었습니다.");
            loadCoursesToTable(); // 테이블 갱신
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강좌 삭제 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_deleteActionPerformed

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
            java.util.logging.Logger.getLogger(AddLecture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddLecture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddLecture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddLecture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddLecture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton before;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField maxStudent;
    private javax.swing.JTextField minStudent;
    private javax.swing.JButton modify;
    private javax.swing.JTextField professor;
    // End of variables declaration//GEN-END:variables
}
