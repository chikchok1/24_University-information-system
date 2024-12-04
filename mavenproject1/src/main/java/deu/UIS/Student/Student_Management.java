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
import java.util.LinkedHashMap;
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
        addMouseListenerToLectureList1(); // MouseListener 추가
    }

    private void loadLecturesToTable() {
        DefaultTableModel model = (DefaultTableModel) lectureList1.getModel();
        model.setRowCount(0); // 기존 데이터 초기화

        try (BufferedReader reader = new BufferedReader(new FileReader(LECTURE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 기본값 초기화
                String courseNumber = "미정";
                String lectureName = "미정";
                String credits = "0";
                String professor = "미정";
                String maxStudents = "0";
                String currentStudents = "0";
                String day = "미정"; // 요일 기본값
                String time = "미정"; // 시간 기본값

                // 데이터를 쉼표로 나누고 필드별로 처리
                String[] lectureData = line.split(", (?=[^:]+: )"); // 콜론과 공백을 기준으로 정확히 나눔
                for (String field : lectureData) {
                    if (field.startsWith("강좌 번호:")) {
                        courseNumber = field.replace("강좌 번호: ", "").trim();
                    } else if (field.startsWith("강좌 이름:")) {
                        lectureName = field.replace("강좌 이름: ", "").trim();
                    } else if (field.startsWith("학점 수:")) {
                        credits = field.replace("학점 수: ", "").trim();
                    } else if (field.startsWith("담당 교수:")) {
                        professor = field.replace("담당 교수: ", "").trim();
                    } else if (field.startsWith("최대 학생 수:")) {
                        maxStudents = field.replace("최대 학생 수: ", "").trim();
                    } else if (field.startsWith("현재 학생 수:")) {
                        currentStudents = field.replace("현재 학생 수: ", "").trim();
                    } else if (field.startsWith("요일:")) {
                        day = field.replace("요일: ", "").trim();
                    } else if (field.startsWith("시간:")) {
                        time = field.replace("시간: ", "").trim();
                    }
                }

                // JTable에 데이터 추가
                model.addRow(new Object[]{courseNumber, lectureName, credits, professor, maxStudents, currentStudents, day, time});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강의 정보 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudentCoursesToTable() {
        // 기존 JTable의 모델 가져오기
        DefaultTableModel model = (DefaultTableModel) lectureList.getModel();
        model.setRowCount(0); // 기존 데이터 초기화

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_COURSE_FILE_PATH))) {
            String line;
            String loggedInStudentId = UserSession.getInstance().getUserId(); // 현재 로그인한 학생 ID
            while ((line = reader.readLine()) != null) {
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
                    String courseNumber = dataMap.getOrDefault("강좌 번호", "미정");
                    String lectureName = dataMap.getOrDefault("강의 이름", "미정");
                    String credits = dataMap.getOrDefault("학점", "0");
                    String professor = dataMap.getOrDefault("담당 교수", "미정");
                    String maxStudents = dataMap.getOrDefault("최대 수강 인원", "0");
                    String currentStudents = dataMap.getOrDefault("현재 수강 인원", "0");
                    String day = "미정"; // 기본값 설정
                    String time = "미정"; // 기본값 설정

                    // lectureList1에서 요일과 시간을 가져옴
                    for (int i = 0; i < lectureList1.getRowCount(); i++) {
                        if (lectureList1.getValueAt(i, 0).toString().equals(courseNumber)) {
                            day = lectureList1.getValueAt(i, 6).toString(); // 요일
                            time = lectureList1.getValueAt(i, 7).toString(); // 시간
                            break;
                        }
                    }

                    // JTable에 데이터 추가
                    model.addRow(new Object[]{courseNumber, lectureName, credits, professor, maxStudents, currentStudents, day, time});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "학생 수강 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getLectureDescription(String courseNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LECTURE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lectureData = line.split(", (?=[^:]+: )");
                Map<String, String> dataMap = new LinkedHashMap<>();
                for (String field : lectureData) {
                    String[] keyValue = field.split(": ", 2);
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }

                // 강좌 번호가 일치하면 설명 반환
                if (courseNumber.equals(dataMap.get("강좌 번호"))) {
                    return dataMap.getOrDefault("강좌에 대한 설명", "설명이 없습니다.");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강좌 설명 로드 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return "설명을 찾을 수 없습니다.";
    }

    private void addMouseListenerToLectureList1() {
        lectureList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = lectureList1.getSelectedRow();
                if (selectedRow != -1) {
                    // 선택된 강좌 번호 가져오기
                    String courseNumber = lectureList1.getValueAt(selectedRow, 0).toString();

                    // 강좌에 대한 설명 가져오기
                    String description = getLectureDescription(courseNumber);

                    // jTable1에 설명만 추가
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0); // 기존 데이터 초기화
                    model.addRow(new Object[]{description}); // 설명만 추가
                }
            }
        });
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

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
                "강좌 번호", "강의 이름", "학점", "담당교수", "최대 수강 인원", "현재 수강 인원", "요일", "시간"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true
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
                "강좌 번호", "강의 이름", "학점", "담당교수", "최대 수강 인원", "현재 수강 인원", "요일", "시간"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lectureList1.setRowHeight(25);
        lectureList1.setShowHorizontalLines(true);
        lectureList1.setShowVerticalLines(true);
        S_Request1.setViewportView(lectureList1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "강좌에 대한 설명"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Title, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(S_RequestTitle)
                                            .addComponent(S_PreCourseTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Back)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(delete))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(add))
                            .addComponent(S_Request, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                            .addComponent(S_Request1))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(S_RequestTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(S_Request1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(add))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(S_PreCourseTitle)
                .addGap(9, 9, 9)
                .addComponent(S_Request, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete)
                    .addComponent(Back))
                .addGap(43, 43, 43))
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
        loadLecturesToTable(); // lectureList1을 새로 고침
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
    String professor = model.getValueAt(selectedRow, 3).toString(); // 담당 교수 추가
    String maxStudents = model.getValueAt(selectedRow, 4).toString();
    int currentStudents = Integer.parseInt(model.getValueAt(selectedRow, 5).toString()); // 현재 수강 인원
    String day = model.getValueAt(selectedRow, 6).toString(); // 요일
    String time = model.getValueAt(selectedRow, 7).toString(); // 시간

    // 동일한 요일과 시간이 중복되는지 확인
    if (isTimeSlotConflict(day, time)) {
        JOptionPane.showMessageDialog(this, "이미 동일한 시간대에 다른 강의가 등록되어 있습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
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
    updateLectureFile(courseNumber, currentStudents, professor); // professor 전달

    // 학생 수강 정보 추가
    addStudentCourse(courseNumber, lectureName, creditsStr, professor, maxStudents, String.valueOf(currentStudents));

    // 학생의 강좌 목록을 새로 로드
    loadStudentCoursesToTable();

    // 현재 JTable 즉시 업데이트
    refreshLectureList1();
}


    private boolean isTimeSlotConflict(String day, String time) {
        String loggedInStudentId = UserSession.getInstance().getUserId(); // 현재 로그인한 학생 ID

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_COURSE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(", ");
                Map<String, String> dataMap = new HashMap<>();
                for (String field : fields) {
                    String[] keyValue = field.split(": ");
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }

                // 현재 학생 ID와 요일이 동일한 경우 확인
                if (loggedInStudentId.equals(dataMap.get("아이디")) && day.equals(dataMap.get("요일"))) {
                    String existingTime = dataMap.get("시간");

                    if (existingTime != null && isTimeOverlap(existingTime, time)) {
                        return true; // 시간이 겹치는 경우
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "시간 중복 확인 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false; // 중복되지 않음
    }

    private boolean isTimeOverlap(String time1, String time2) {
        int[] timeRange1 = parseTimeRange(time1);
        int[] timeRange2 = parseTimeRange(time2);

        // 두 시간 범위가 겹치는지 확인
        return timeRange1[0] < timeRange2[1] && timeRange1[1] > timeRange2[0];
    }

    private int[] parseTimeRange(String time) {
        String[] parts = time.split("-");
        if (parts.length == 2) {
            int start = convertTimeToMinutes(parts[0].trim());
            int end = convertTimeToMinutes(parts[1].trim());
            return new int[]{start, end};
        }
        return new int[]{0, 0}; // 유효하지 않은 시간대
    }

    private int convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        if (parts.length == 2) {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours * 60 + minutes;
        }
        return 0;
    }

    private void refreshLectureList1() {
        DefaultTableModel model = (DefaultTableModel) lectureList1.getModel();
        model.setRowCount(0); // 기존 데이터 초기화

        try (BufferedReader reader = new BufferedReader(new FileReader(LECTURE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String courseNumber = "미정";
                String lectureName = "미정";
                String credits = "0";
                String professor = "미정";
                String maxStudents = "0";
                String currentStudents = "0";
                String day = "미정"; // 요일 기본값
                String time = "미정"; // 시간 기본값

                // 데이터를 쉼표로 나누고 필드별로 처리
                String[] lectureData = line.split(", (?=[^:]+: )");
                for (String field : lectureData) {
                    if (field.startsWith("강좌 번호:")) {
                        courseNumber = field.replace("강좌 번호: ", "").trim();
                    } else if (field.startsWith("강좌 이름:")) {
                        lectureName = field.replace("강좌 이름: ", "").trim();
                    } else if (field.startsWith("학점 수:")) {
                        credits = field.replace("학점 수: ", "").trim();
                    } else if (field.startsWith("담당 교수:")) {
                        professor = field.replace("담당 교수: ", "").trim();
                    } else if (field.startsWith("최대 학생 수:")) {
                        maxStudents = field.replace("최대 학생 수: ", "").trim();
                    } else if (field.startsWith("현재 학생 수:")) {
                        currentStudents = field.replace("현재 학생 수: ", "").trim();
                    } else if (field.startsWith("요일:")) {
                        day = field.replace("요일: ", "").trim();
                    } else if (field.startsWith("시간:")) {
                        time = field.replace("시간: ", "").trim();
                    }
                }

                // JTable에 데이터 추가
                model.addRow(new Object[]{courseNumber, lectureName, credits, professor, maxStudents, currentStudents, day, time});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "강의 정보 파일 읽기 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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

   private void updateLectureFile(String courseNumber, int currentStudents, String professor) {
    try {
        List<String> lines = Files.readAllLines(Paths.get(LECTURE_FILE_PATH));
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            if (line.contains("강좌 번호: " + courseNumber) && line.contains("담당 교수: " + professor)) {
                // 기존 데이터를 파싱
                String[] lectureData = line.split(", (?=[^:]+: )");
                Map<String, String> dataMap = new LinkedHashMap<>();
                for (String field : lectureData) {
                    String[] keyValue = field.split(": ", 2);
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }

                // "현재 학생 수" 업데이트
                dataMap.put("현재 학생 수", String.valueOf(currentStudents));

                // 데이터 조합
                StringBuilder updatedLine = new StringBuilder();
                for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                    updatedLine.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
                }

                // 마지막 쉼표와 공백 제거
                updatedLine.setLength(updatedLine.length() - 2);
                updatedLines.add(updatedLine.toString());
            } else {
                updatedLines.add(line); // 수정 대상이 아닌 줄은 그대로 추가
            }
        }

        // 파일 덮어쓰기
        Files.write(Paths.get(LECTURE_FILE_PATH), updatedLines);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일 업데이트 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void addStudentCourse(String courseNumber, String lectureName, String credits, String professor, String maxStudents, String currentStudents) {
        String loggedInStudentId = UserSession.getInstance().getUserId(); // 로그인한 학생 ID
        String loggedInStudentName = UserSession.getInstance().getUserName(); // 로그인한 학생 이름

        // lectureList1에서 선택된 강좌의 요일과 시간 가져오기
        String day = "미정";
        String time = "미정";
        for (int i = 0; i < lectureList1.getRowCount(); i++) {
            if (lectureList1.getValueAt(i, 0).toString().equals(courseNumber)) {
                day = lectureList1.getValueAt(i, 6).toString(); // 요일
                time = lectureList1.getValueAt(i, 7).toString(); // 시간
                break;
            }
        }

        // 새로 추가할 데이터 형식을 "키: 값" 형태로 설정
        String newEntry = String.format(
                "이름: %s, 아이디: %s, 강좌 번호: %s, 강의 이름: %s, 학점: %s, 담당 교수: %s, 최대 수강 인원: %s, 현재 수강 인원: %s, 요일: %s, 시간: %s",
                loggedInStudentName, loggedInStudentId, courseNumber, lectureName, credits, professor, maxStudents, currentStudents, day, time
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
        loadLecturesToTable(); // lectureList1을 새로 고침
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
    String professor = model.getValueAt(selectedRow, 3).toString(); // 담당 교수 추가
    String loggedInStudentId = UserSession.getInstance().getUserId();

    // 파일에서 삭제
    deleteStudentCourseFromFile(loggedInStudentId, courseNumber);

    // 강좌 정보에서 현재 수강 인원 감소
    decreaseCurrentStudents(courseNumber, professor); // professor 전달

    // JTable에서 데이터 삭제
    model.removeRow(selectedRow);

    // 현재 JTable 즉시 업데이트
    refreshLectureList1();
}

    private void decreaseCurrentStudents(String courseNumber, String professor) {
    try {
        List<String> lines = Files.readAllLines(Paths.get(LECTURE_FILE_PATH));
        List<String> updatedLines = new ArrayList<>();
        DefaultTableModel lectureModel = (DefaultTableModel) lectureList1.getModel();

        for (String line : lines) {
            if (line.contains("강좌 번호: " + courseNumber) && line.contains("담당 교수: " + professor)) {
                // 기존 데이터를 파싱
                String[] lectureData = line.split(", (?=[^:]+: )");
                Map<String, String> dataMap = new LinkedHashMap<>();
                for (String field : lectureData) {
                    String[] keyValue = field.split(": ", 2);
                    if (keyValue.length == 2) {
                        dataMap.put(keyValue[0].trim(), keyValue[1].trim());
                    }
                }

                // "현재 학생 수" 감소
                if (dataMap.containsKey("현재 학생 수")) {
                    int currentStudents = Integer.parseInt(dataMap.get("현재 학생 수"));
                    currentStudents = Math.max(0, currentStudents - 1); // 0 이하로 내려가지 않도록 설정
                    dataMap.put("현재 학생 수", String.valueOf(currentStudents));

                    // JTable의 해당 강좌의 현재 수강 인원 업데이트
                    for (int i = 0; i < lectureModel.getRowCount(); i++) {
                        if (lectureModel.getValueAt(i, 0).toString().equals(courseNumber) &&
                            lectureModel.getValueAt(i, 3).toString().equals(professor)) {
                            lectureModel.setValueAt(String.valueOf(currentStudents), i, 5); // 현재 수강 인원 업데이트
                            break;
                        }
                    }
                }

                // 데이터 조합
                StringBuilder updatedLine = new StringBuilder();
                for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                    updatedLine.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
                }

                // 마지막 쉼표와 공백 제거
                updatedLine.setLength(updatedLine.length() - 2);
                updatedLines.add(updatedLine.toString());
            } else {
                updatedLines.add(line);
            }
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable lectureList;
    private javax.swing.JTable lectureList1;
    // End of variables declaration//GEN-END:variables
}
