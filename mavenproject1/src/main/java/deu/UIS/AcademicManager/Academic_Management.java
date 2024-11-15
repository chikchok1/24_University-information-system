/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.UIS.AcademicManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author YangJinWon
 */
public class Academic_Management extends javax.swing.JFrame {

    /**
     * Creates new form Academic_Management
     */
    public Academic_Management() {
        initComponents();
        loadStudentInfo();  // 생성자에서 파일 데이터를 불러오는 메서드 호출
        addMouseListenerToSList(); // 테이블에 MouseListener 추가

    }
    // 학생 정보를 파일에서 불러와 테이블에 추가하는 메서드
private void loadStudentInfo() {
    String filePath = System.getProperty("user.home") + "/student_info.txt";
    DefaultTableModel model = (DefaultTableModel) S_list.getModel();
    model.setRowCount(0); // 테이블 초기화

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String department = "", studentNumber = "", name = "", birthDate = "", phone = "", grade = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("학과: ")) {
                department = line.substring(4);
            } else if (line.startsWith("학번: ")) {
                studentNumber = line.substring(4);
            } else if (line.startsWith("이름: ")) {
                name = line.substring(4);
            } else if (line.startsWith("생년월일: ")) {
                birthDate = line.substring(6);
            } else if (line.startsWith("휴대폰: ")) {
                phone = line.substring(5);
            } else if (line.startsWith("학년: ")) {
                grade = line.substring(4);
            } else if (line.isEmpty() && !department.isEmpty() && !studentNumber.isEmpty() && !name.isEmpty()) {
                model.addRow(new Object[]{name, studentNumber, department}); // 순서: 이름, 학번, 학과
                department = studentNumber = name = birthDate = phone = grade = ""; 
            }
        }

        // 마지막 학생 정보가 테이블에 추가되지 않은 경우 추가
        if (!department.isEmpty() && !studentNumber.isEmpty() && !name.isEmpty()) {
            model.addRow(new Object[]{name, studentNumber, department}); // 순서: 이름, 학번, 학과
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "학생 정보를 불러오는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void addMouseListenerToSList() {
    S_list.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int selectedRow = S_list.getSelectedRow();
            if (selectedRow != -1) {
                // 클릭된 행의 데이터 가져오기
                String name = (String) S_list.getValueAt(selectedRow, 0);         // 이름
                String studentNumber = (String) S_list.getValueAt(selectedRow, 1); // 학번
                String department = (String) S_list.getValueAt(selectedRow, 2);    // 학과

                // 해당 학생의 정보를 S_info에 표시
                populateStudentDetails(department, studentNumber, name);
            }
        }
    });
}


private void populateStudentDetails(String department, String studentNumber, String name) {
    String filePath = System.getProperty("user.home") + "/student_info.txt";

    // S_info 테이블 모델을 새로 만들기
    DefaultTableModel infoModel = new DefaultTableModel(new String[]{"이름", "학번", "학과", "학년", "생년월일", "휴대폰"}, 0);
    S_info.setModel(infoModel);  // 테이블 모델 설정

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String departmentFile = "", studentNumberFile = "", nameFile = "", birthDateFile = "", phoneFile = "", gradeFile = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            // 파일에서 학생 정보를 읽어서 매칭
            if (line.startsWith("학과: ")) {
                departmentFile = line.substring(4);
            } else if (line.startsWith("학번: ")) {
                studentNumberFile = line.substring(4);
            } else if (line.startsWith("이름: ")) {
                nameFile = line.substring(4);
            } else if (line.startsWith("생년월일: ")) {
                birthDateFile = line.substring(6);
            } else if (line.startsWith("휴대폰: ")) {
                phoneFile = line.substring(5);
            } else if (line.startsWith("학년: ")) {
                gradeFile = line.substring(4);
            }

            // 모든 정보가 채워졌을 때 입력된 학생 정보와 매칭
            if (!departmentFile.isEmpty() && !studentNumberFile.isEmpty() && !nameFile.isEmpty() &&
                !birthDateFile.isEmpty() && !phoneFile.isEmpty() && !gradeFile.isEmpty()) {

                if (departmentFile.equals(department) && studentNumberFile.equals(studentNumber) && nameFile.equals(name)) {
                    // S_info 테이블에 학생 정보 추가
                    infoModel.addRow(new Object[]{
                        nameFile, studentNumberFile, departmentFile, gradeFile, birthDateFile, phoneFile
                    });
                    break; // 필요한 학생 정보를 찾았으므로 루프 종료
                }

                // 정보 초기화
                departmentFile = studentNumberFile = nameFile = birthDateFile = phoneFile = gradeFile = "";
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "학생 정보 검색 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
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

        Title = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        S_number = new javax.swing.JLabel();
        department = new javax.swing.JLabel();
        grade = new javax.swing.JLabel();
        birthdate = new javax.swing.JLabel();
        phone = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        numberBox = new javax.swing.JComboBox<>();
        Name = new javax.swing.JTextField();
        S_Number = new javax.swing.JTextField();
        Department = new javax.swing.JTextField();
        gradeBox = new javax.swing.JComboBox<>();
        birth = new javax.swing.JTextField();
        Phone = new javax.swing.JTextField();
        S_search = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        search_button = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        Add = new javax.swing.JButton();
        Before = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        S_list = new javax.swing.JTable();
        Delete = new javax.swing.JButton();
        modify = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        S_info = new javax.swing.JTable();
        refresh = new javax.swing.JButton();
        save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Title.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        Title.setText("학생 학사 관리 페이지");

        title.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        title.setText("학생 정보 입력");

        name.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        name.setText("이름");

        S_number.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        S_number.setText("학번");

        department.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        department.setText("학과");

        grade.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        grade.setText("학년");

        birthdate.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        birthdate.setText("생년월일");

        phone.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        phone.setText("휴대폰");

        jLabel1.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        jLabel1.setText("학생 정보 검색");

        numberBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "학번", "이름" }));
        numberBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberBoxActionPerformed(evt);
            }
        });

        Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameActionPerformed(evt);
            }
        });

        S_Number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                S_NumberActionPerformed(evt);
            }
        });

        Department.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepartmentActionPerformed(evt);
            }
        });

        gradeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1학년", "2학년", "3학년", "4학년" }));
        gradeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeBoxActionPerformed(evt);
            }
        });

        birth.setText("2000.01.01");
        birth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthActionPerformed(evt);
            }
        });

        Phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhoneActionPerformed(evt);
            }
        });

        S_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                S_searchActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        jLabel2.setText("학생 정보");

        search_button.setText("검색");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        jLabel3.setText("학생 목록");

        Add.setText("추가");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        Before.setText("이전");
        Before.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeforeActionPerformed(evt);
            }
        });

        S_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "이름", "학번", "학과"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(S_list);

        Delete.setText("삭제");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        modify.setText("수정");
        modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyActionPerformed(evt);
            }
        });

        S_info.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "이름", "학번", "학과", "학년", "생년월일", "휴대폰"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(S_info);

        refresh.setText("새로고침");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        save.setText("저장");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(Title))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(Add)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Before))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(phone)
                                        .addComponent(birthdate))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(gradeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(Department, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(S_Number, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(grade)
                            .addComponent(title)
                            .addComponent(department)
                            .addComponent(S_number)
                            .addComponent(name))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(numberBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(S_search, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(modify)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(save)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Delete))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(refresh))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Title)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(name)
                            .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(S_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search_button)
                            .addComponent(numberBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(S_number)
                                .addComponent(S_Number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(refresh)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(department)
                                    .addComponent(Department, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(grade)
                                    .addComponent(gradeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(birthdate)
                                    .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(phone)
                                    .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Add)
                            .addComponent(Before)
                            .addComponent(modify)
                            .addComponent(save)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Delete)))
                .addContainerGap(176, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameActionPerformed

    private void PhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhoneActionPerformed

    private void numberBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberBoxActionPerformed

    private void gradeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gradeBoxActionPerformed

    private void S_NumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_S_NumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_S_NumberActionPerformed

    private void DepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepartmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DepartmentActionPerformed

    private void S_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_S_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_S_searchActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
    String keyword = S_search.getText().trim(); // 검색 필드에서 키워드를 가져옵니다.
    if (keyword.isEmpty()) {
        JOptionPane.showMessageDialog(this, "검색어를 입력해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String filePath = System.getProperty("user.home") + "/student_info.txt";
    DefaultTableModel model = (DefaultTableModel) S_list.getModel();
    model.setRowCount(0); // 테이블 초기화

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String department = "", studentNumber = "", name = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("학과: ")) {
                department = line.substring(4);
            } else if (line.startsWith("학번: ")) {
                studentNumber = line.substring(4);
            } else if (line.startsWith("이름: ")) {
                name = line.substring(4);
            } else if (line.isEmpty() && !department.isEmpty() && !studentNumber.isEmpty() && !name.isEmpty()) {
                // 키워드로 검색 (이름, 학번, 학과에 대해)
                if (name.contains(keyword) || studentNumber.contains(keyword) || department.contains(keyword)) {
                    model.addRow(new Object[]{name, studentNumber, department});
                }
                department = studentNumber = name = "";
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "학생 정보 검색 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }
        
    }//GEN-LAST:event_search_buttonActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
// 입력된 값을 가져옵니다.
    String name = Name.getText();
    String studentNumber = S_Number.getText();
    String department = Department.getText();
    String grade = (String) gradeBox.getSelectedItem();
    String phone = Phone.getText();
    String birthDate = birth.getText();

    // 메모장에 저장할 문자열을 구성합니다.
    String studentInfo = "이름: " + name + "\n" +
                     "학번: " + studentNumber + "\n" +
                     "학과: " + department + "\n" +
                     "학년: " + grade + "\n" +
                     "생년월일: " + birthDate + "\n" +
                     "휴대폰: " + phone + "\n\n";

    // "student_info.txt" 파일에 저장합니다.
    String filePath = System.getProperty("user.home") + "/student_info.txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        writer.write(studentInfo);
        writer.newLine();
        JOptionPane.showMessageDialog(this, "학생 정보가 저장되었습니다.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // 테이블에 데이터 추가
    DefaultTableModel model = (DefaultTableModel) S_list.getModel();
    model.addRow(new Object[]{name, studentNumber, department});  // 이름, 학번, 학과 순으로 추가

    // 새로운 학생 정보가 추가되었으면, S_info 테이블도 새로 갱신
    loadStudentInfo();  // S_list 테이블을 갱신
    }//GEN-LAST:event_AddActionPerformed

    private void BeforeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeforeActionPerformed
        // TODO add your handling code here:
        dispose();
        
         new A_Main().setVisible(true);
    }//GEN-LAST:event_BeforeActionPerformed

    private void birthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed

// S_list에서 선택된 행의 인덱스를 가져옵니다
    int selectedRow = S_list.getSelectedRow();
    
    if (selectedRow != -1) { // 선택된 행이 있을 경우
        // 테이블 모델을 가져옵니다
        DefaultTableModel model = (DefaultTableModel) S_list.getModel();
        
        // 선택된 행을 모델에서 삭제합니다
        model.removeRow(selectedRow);
        
        // 삭제 완료 메시지
        JOptionPane.showMessageDialog(this, "선택된 학생 정보가 삭제되었습니다.");
    } else {
        // 선택된 행이 없을 경우 경고 메시지
        JOptionPane.showMessageDialog(this, "삭제할 학생을 선택해 주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_DeleteActionPerformed

    private void modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyActionPerformed
         // 선택된 학생 정보가 없으면 경고 메시지
    int selectedRow = S_list.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "수정할 학생을 선택해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 선택된 행에서 학생 정보를 가져옵니다.
    String department = (String) S_list.getValueAt(selectedRow, 0);
    String studentNumber = (String) S_list.getValueAt(selectedRow, 1);
    String name = (String) S_list.getValueAt(selectedRow, 2);

    // 해당 학생 정보를 입력 필드에 채워넣습니다.
    Department.setText(department);
    S_Number.setText(studentNumber);
    Name.setText(name);
    
    // 수정 버튼을 눌렀을 때 필드가 수정 가능하도록 설정합니다.
    Department.setEnabled(true);
    S_Number.setEnabled(true);
    Name.setEnabled(true);
    gradeBox.setEnabled(true);
    Phone.setEnabled(true);
    birth.setEnabled(true);
    
    // 수정 후 저장할 수 있는 버튼을 추가로 활성화 (예: 저장 버튼)
    // 예: Save 버튼을 활성화하거나 다른 로직을 추가할 수 있습니다.
    }//GEN-LAST:event_modifyActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
          // 학생 정보를 다시 불러와서 S_list 테이블을 새로 갱신합니다.
            loadStudentInfo();

    }//GEN-LAST:event_refreshActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // 선택된 학생 정보가 없으면 경고 메시지
    int selectedRow = S_list.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "수정할 학생을 선택해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 수정된 값들을 가져옵니다.
    String updatedDepartment = Department.getText();
    String updatedStudentNumber = S_Number.getText();
    String updatedName = Name.getText();
    String updatedGrade = (String) gradeBox.getSelectedItem();
    String updatedPhone = Phone.getText();
    String updatedBirthDate = birth.getText();

    // 테이블에서 선택된 행을 업데이트합니다.
    DefaultTableModel model = (DefaultTableModel) S_list.getModel();
    model.setValueAt(updatedDepartment, selectedRow, 0);  // 학과
    model.setValueAt(updatedStudentNumber, selectedRow, 1);  // 학번
    model.setValueAt(updatedName, selectedRow, 2);  // 이름

    // 파일에 수정된 정보 저장 (기존의 정보를 덮어쓰기 위해서는 파일을 다시 읽고 업데이트해야 합니다)
    updateStudentInfoInFile(updatedDepartment, updatedStudentNumber, updatedName, updatedGrade, updatedPhone, updatedBirthDate);
    
    JOptionPane.showMessageDialog(this, "학생 정보가 수정되었습니다.");
    // 입력 칸을 초기화 (비우기)
    clearInputFields();

    }//GEN-LAST:event_saveActionPerformed
// 입력 필드를 초기화하는 메서드
private void clearInputFields() {
    Name.setText("");           // 이름
    S_Number.setText("");       // 학번
    Department.setText("");     // 학과
    gradeBox.setSelectedIndex(0);  // 학년 (초기값으로 리셋)
    Phone.setText("");          // 전화번호
    birth.setText("");          // 생년월일
}
    private void updateStudentInfoInFile(String department, String studentNumber, String name, String grade, String phone, String birthDate) {
    String filePath = System.getProperty("user.home") + "/student_info.txt";
    File file = new File(filePath);
    StringBuilder fileContent = new StringBuilder();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        boolean studentFound = false;
        
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            // 기존 정보를 찾아서 수정된 정보로 교체
            if (line.startsWith("학번: ") && line.substring(4).equals(studentNumber)) {
                studentFound = true;
                fileContent.append("학과: ").append(department).append("\n");
                fileContent.append("학번: ").append(studentNumber).append("\n");
                fileContent.append("이름: ").append(name).append("\n");
                fileContent.append("학년: ").append(grade).append("\n");
                fileContent.append("생년월일: ").append(birthDate).append("\n");
                fileContent.append("휴대폰: ").append(phone).append("\n\n");
            } else {
                fileContent.append(line).append("\n");
            }
        }
        
        // 수정된 내용을 파일에 다시 씁니다.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(fileContent.toString());
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "학생 정보를 파일에 저장하는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(Academic_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Academic_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Academic_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Academic_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Academic_Management().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton Before;
    private javax.swing.JButton Delete;
    private javax.swing.JTextField Department;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField Phone;
    private javax.swing.JTextField S_Number;
    private javax.swing.JTable S_info;
    private javax.swing.JTable S_list;
    private javax.swing.JLabel S_number;
    private javax.swing.JTextField S_search;
    private javax.swing.JLabel Title;
    private javax.swing.JTextField birth;
    private javax.swing.JLabel birthdate;
    private javax.swing.JLabel department;
    private javax.swing.JLabel grade;
    private javax.swing.JComboBox<String> gradeBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton modify;
    private javax.swing.JLabel name;
    private javax.swing.JComboBox<String> numberBox;
    private javax.swing.JLabel phone;
    private javax.swing.JButton refresh;
    private javax.swing.JButton save;
    private javax.swing.JButton search_button;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
