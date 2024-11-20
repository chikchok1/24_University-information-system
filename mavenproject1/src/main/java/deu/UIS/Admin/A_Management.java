/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.UIS.Admin;
import deu.UIS.AcademicManager.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author YangJinWon
 */
public class A_Management extends javax.swing.JFrame {

    /**
     * Creates new form Academic_Management
     */
    public A_Management() {
        initComponents();
        loadStudentInfo();  // 생성자에서 파일 데이터를 불러오는 메서드 호출
        addMouseListenerToSList(); // 테이블에 MouseListener 추가

    }
    // 학생 정보를 파일에서 불러와 테이블에 추가하는 메서드
private void loadStudentInfo() {
    DefaultTableModel model = (DefaultTableModel) S_list.getModel();
    model.setRowCount(0); // 기존 데이터 초기화

    String filePath = System.getProperty("user.home") + "\\data\\Academic_info.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String studentNumber = "", name = "", birthDate = "", phone = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

             if (line.startsWith("교수번호: ")) {
                studentNumber = line.substring(6);
            } else if (line.startsWith("이름: ")) {
                name = line.substring(4);
            }  else if (line.startsWith("생년월일: ")) {
                birthDate = line.substring(6);
            } else if (line.startsWith("휴대폰: ")) {
                phone = line.substring(5);
            } else if (line.isEmpty()) {
                // 데이터 유효성 검사: 모든 필드가 비어 있지 않은 경우에만 추가
                if (!studentNumber.isEmpty() && !name.isEmpty()
                        &&  !birthDate.isEmpty() && !phone.isEmpty()) {
                    model.addRow(new Object[]{name, studentNumber, birthDate, phone});
                }
                // 데이터 초기화
                studentNumber = name = birthDate = phone = "";
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "교수 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void addMouseListenerToSList() {
    S_list.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int selectedRow = S_list.getSelectedRow();
            if (selectedRow != -1) {
                // 클릭된 행의 데이터 가져오기
                String name = (String) S_list.getValueAt(selectedRow, 0);         // 이름
                String studentNumber = (String) S_list.getValueAt(selectedRow, 1); // 교수번호
                

                // 해당 학생의 정보를 S_info에 표시
                populateStudentDetails(studentNumber, name);
            }
        }
    });
}

private void populateStudentDetails(String studentNumber, String name) {
    String filePath = System.getProperty("user.home") + "\\data\\Academic_info.txt";

    // S_info 테이블 모델을 새로 만들기
    DefaultTableModel infoModel = new DefaultTableModel(new String[]{"이름", "교수번호", "생년월일", "휴대폰"}, 0);
    S_info.setModel(infoModel);  // 테이블 모델 설정

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String studentNumberFile = "", nameFile = "", birthDateFile = "", phoneFile = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            // 파일에서 학생 정보를 읽어서 매칭
            if (line.startsWith("교수번호: ")) {
                studentNumberFile = line.substring(6);
            } else if (line.startsWith("이름: ")) {
                nameFile = line.substring(4);
            } else if (line.startsWith("생년월일: ")) {
                birthDateFile = line.substring(6);
            } else if (line.startsWith("휴대폰: ")) {
                phoneFile = line.substring(5);
            } 

            // 모든 정보가 채워졌을 때 입력된 학생 정보와 매칭
            if (!studentNumberFile.isEmpty() && !nameFile.isEmpty() &&
                !birthDateFile.isEmpty() && !phoneFile.isEmpty()) {

                if ( studentNumberFile.equals(studentNumber) && nameFile.equals(name)) {
                    // S_info 테이블에 학생 정보 추가
                    infoModel.addRow(new Object[]{
                        nameFile, studentNumberFile,birthDateFile, phoneFile
                    });
                    break; // 필요한 교수 정보를 찾았으므로 루프 종료
                }

                // 정보 초기화
                studentNumberFile = nameFile = birthDateFile = phoneFile ="";
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "교수 정보 검색 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
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
        birthdate = new javax.swing.JLabel();
        phone = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        numberBox = new javax.swing.JComboBox<>();
        Name = new javax.swing.JTextField();
        birth = new javax.swing.JTextField();
        birthlast = new javax.swing.JTextField();
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
        info_refresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Title.setFont(new java.awt.Font("맑은 고딕", 0, 18)); // NOI18N
        Title.setText("학사담당자 관리 페이지");

        title.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        title.setText("학사담당자 정보 입력");

        name.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        name.setText("이름");

        birthdate.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        birthdate.setText("생년월일");

        phone.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        phone.setText("휴대폰");

        jLabel1.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        jLabel1.setText("학사담당자 정보 검색");

        numberBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "교수번호", "이름" }));
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

        birth.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        birth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthActionPerformed(evt);
            }
        });

        birthlast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthlastActionPerformed(evt);
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
        jLabel2.setText("학사담당자 정보");

        search_button.setText("검색");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("맑은 고딕", 0, 16)); // NOI18N
        jLabel3.setText("학사담당자 목록");

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
                "이름", "교수번호"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
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
                "이름", "교수번호", "생년월일", "휴대폰"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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

        info_refresh.setText("새로고침");
        info_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                info_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(birthdate, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(phone))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(birthlast))
                                    .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(title)
                            .addComponent(name)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Add)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Before)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(numberBox, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(S_search, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel3))
                                .addGap(67, 67, 67))
                            .addComponent(refresh, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(modify)
                                    .addGap(18, 18, 18)
                                    .addComponent(save)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Delete))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(search_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(info_refresh, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(361, 361, 361)
                        .addComponent(Title)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(title)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(name)
                            .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(99, 99, 99)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(birthdate)
                            .addComponent(birthlast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phone))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Add)
                            .addComponent(Before)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(S_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(search_button)
                            .addComponent(numberBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(refresh, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(modify)
                            .addComponent(save)
                            .addComponent(Delete)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(info_refresh)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameActionPerformed

    private void numberBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberBoxActionPerformed

    private void S_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_S_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_S_searchActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
    String keyword = S_search.getText().trim(); // 검색 필드에서 키워드를 가져옵니다.
    String searchType = (String) numberBox.getSelectedItem(); // numberBox에서 선택된 값을 가져옵니다.

    if (keyword.isEmpty()) {
        JOptionPane.showMessageDialog(this, "검색어를 입력해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String filePath = System.getProperty("user.home") +"\\data\\Academic_info.txt";
    DefaultTableModel model = (DefaultTableModel) S_list.getModel();
    model.setRowCount(0); // 테이블 초기화

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String studentNumber = "", name = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("교수번호: ")) {
                studentNumber = line.substring(6);
            } else if (line.startsWith("이름: ")) {
                name = line.substring(4);
            } else if (line.isEmpty() && !studentNumber.isEmpty() && !name.isEmpty()) {
                // 검색 타입에 따라 검색
                boolean matches = false;
                if ("교수번호".equals(searchType)) {
                    matches = keyword.equals(studentNumber); // 학번 검색
                } else if ("이름".equals(searchType)) {
                    matches = keyword.equalsIgnoreCase(name); // 이름 검색 (대소문자 무시)
                }

                if (matches) {
                    model.addRow(new Object[]{name, studentNumber});
                }
                // 초기화
                studentNumber = name = "";
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "교수 정보 검색 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    if (model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.", "검색", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_search_buttonActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
  // 입력된 값을 가져옵니다.
    String name = Name.getText();
    String phone = Phone.getText();
    String birthFirst = birth.getText(); // 생년월일의 앞 6자리
    String birthLast = birthlast.getText(); // 생년월일의 뒤 7자리

    // 생년월일 결합
    String birthDate = birthFirst + "-" + birthLast; // "YYYYMM-DDDDDDD" 형식

    // 입력값 유효성 검증
    if (birthFirst.length() != 6 || birthLast.length() != 7) {
        JOptionPane.showMessageDialog(this, "생년월일은 앞 6자리와 뒤 7자리를 정확히 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // "professor_info.txt" 파일에 저장할 문자열을 구성합니다.
    String filePath = System.getProperty("user.home") + "\\data\\Academic_info.txt";
    
    // 현재 파일에서 가장 큰 교수번호를 찾습니다.
    int maxProfessorNumber = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("교수번호: ")) {
                try {
                    String professorNumberStr = line.substring(6).trim(); // "P001" 같은 형식
                    if (professorNumberStr.matches("H\\d{3}")) { // 형식 검증
                        int professorNumber = Integer.parseInt(professorNumberStr.substring(1)); // 숫자 부분 추출
                        maxProfessorNumber = Math.max(maxProfessorNumber, professorNumber);
                    }
                } catch (NumberFormatException e) {
                    // 잘못된 번호 형식이 있으면 무시하고 진행
                    JOptionPane.showMessageDialog(this, "잘못된 교수번호가 파일에 포함되어 있습니다. 파일을 확인해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    } catch (IOException e) {
        maxProfessorNumber = 0; // 파일이 없거나 읽을 수 없는 경우 초기값 유지
    }

    // 새로운 교수번호 생성
    String studentNumber = String.format("H%03d", maxProfessorNumber + 1);

    // 메모장에 저장할 문자열을 구성합니다.
    String professorInfo = "이름: " + name + "\n" +
                           "교수번호: " + studentNumber + "\n" +
                           "생년월일: " + birthDate + "\n" +
                           "휴대폰: " + phone + "\n\n";

    // 파일에 저장
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        writer.write(professorInfo);
        writer.newLine();
        JOptionPane.showMessageDialog(this, "교수 정보가 저장되었습니다.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // 테이블에 데이터 추가
    DefaultTableModel model = (DefaultTableModel) S_list.getModel();
    model.addRow(new Object[]{name, studentNumber});  // 이름, 교수번호, 학과 순으로 추가

    // 입력 필드 초기화
    Name.setText("");
    Phone.setText("");
    birth.setText("");
    birthlast.setText("");
    }//GEN-LAST:event_AddActionPerformed

    private void BeforeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeforeActionPerformed
        // TODO add your handling code here:
        dispose();
         new Admin_Main().setVisible(true);
    }//GEN-LAST:event_BeforeActionPerformed

    private void birthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
// S_list에서 선택된 행의 인덱스를 가져옵니다.
    int selectedRow = S_list.getSelectedRow();

    if (selectedRow != -1) { // 선택된 행이 있을 경우
        // 테이블 모델을 가져옵니다.
        DefaultTableModel model = (DefaultTableModel) S_list.getModel();

        // 선택된 행의 데이터를 가져옵니다.
        String selectedStudentNumber = (String) model.getValueAt(selectedRow, 1); // 학번

        // 파일 경로 설정
        String filePath = System.getProperty("user.home") + "\\data\\Academic_info.txt";
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String studentNumber = "", name = "", birthDate = "", phone = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

               if (line.startsWith("교수번호: ")) {
                    studentNumber = line.substring(6);
                } else if (line.startsWith("이름: ")) {
                    name = line.substring(4);
                } else if (line.startsWith("생년월일: ")) {
                    birthDate = line.substring(6);
                } else if (line.startsWith("휴대폰: ")) {
                    phone = line.substring(5);
                } else if (line.isEmpty()) {
                    // 선택된 학번과 일치하지 않는 경우만 파일에 저장
                    if (!studentNumber.equals(selectedStudentNumber)) {
                        updatedContent.append("교수번호: ").append(studentNumber).append("\n");
                        updatedContent.append("이름: ").append(name).append("\n");
                        updatedContent.append("생년월일: ").append(birthDate).append("\n");
                        updatedContent.append("휴대폰: ").append(phone).append("\n\n");
                    }
                    // 데이터 초기화
                     studentNumber = name = birthDate = phone = "";
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 읽기 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 수정된 데이터를 파일에 저장
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(updatedContent.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 테이블에서 선택된 행 삭제
        model.removeRow(selectedRow);

        // 삭제 완료 메시지
        JOptionPane.showMessageDialog(this, "선택된 교수 정보가 삭제되었습니다.");
    } else {
        // 선택된 행이 없을 경우 경고 메시지
        JOptionPane.showMessageDialog(this, "삭제할 교수를 선택해 주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_DeleteActionPerformed

    private void modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyActionPerformed
       // 선택된 학생 정보가 없으면 경고 메시지
    int selectedRow = S_list.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "수정할 교수를 선택해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 선택된 행에서 기본 정보를 가져옵니다.
    String selectedName = (String) S_list.getValueAt(selectedRow, 0);         // 이름
    String selectedStudentNumber = (String) S_list.getValueAt(selectedRow, 1); // 학번
    
    // 파일에서 선택된 학생의 세부 정보를 가져와서 입력 필드에 채웁니다.
    String filePath = System.getProperty("user.home") + "\\data\\Academic_info.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String studentNumber = "", name = "", birthDate = "", phone = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

             if (line.startsWith("교수번호: ")) {
                studentNumber = line.substring(6);
            } else if (line.startsWith("이름: ")) {
                name = line.substring(4);
            } else if (line.startsWith("생년월일: ")) {
                birthDate = line.substring(6);
            } else if (line.startsWith("휴대폰: ")) {
                phone = line.substring(5);
            } else if (line.isEmpty() && name.equals(selectedName) && studentNumber.equals(selectedStudentNumber)) {
                // 선택된 학생과 일치하는 정보를 찾았을 때 입력 필드에 채우기
                Name.setText(name);

                // 주민등록번호를 앞 6자리와 뒤 7자리로 나눔
                if (birthDate.length() == 14) { // 주민등록번호 형식 확인 (앞 6 + "-" + 뒤 7)
                    String[] parts = birthDate.split("-");
                    if (parts.length == 2) {
                        birth.setText(parts[0]);     // 앞 6자리
                        birthlast.setText(parts[1]); // 뒤 7자리
                    }
                } else {
                    birth.setText(birthDate); // 형식이 맞지 않을 경우 전체 입력
                }

                Phone.setText(phone);
                break;
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "교수 정보를 불러오는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_modifyActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
          // 학생 정보를 다시 불러와서 S_list 테이블을 새로 갱신합니다.
            loadStudentInfo();
    }//GEN-LAST:event_refreshActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
    int selectedRow = S_list.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "수정할 교수를 선택해주세요.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 입력 필드에서 수정된 데이터 가져오기
    String updatedName = Name.getText().trim();
    String updatedBirthDatePart1 = birth.getText().trim(); // 주민등록번호 앞 6자리
    String updatedBirthDatePart2 = birthlast.getText().trim(); // 주민등록번호 뒤 7자리
    String updatedPhone = Phone.getText().trim();
    String existingProfessorNumber = (String) S_list.getValueAt(selectedRow, 1); // 선택된 교수번호

    // 주민등록번호를 다시 합침
    String updatedBirthDate = updatedBirthDatePart1 + "-" + updatedBirthDatePart2;

    if (updatedName.isEmpty() || updatedBirthDatePart1.isEmpty() || 
        updatedBirthDatePart2.isEmpty() || updatedPhone.isEmpty()) {
        JOptionPane.showMessageDialog(this, "모든 필드를 입력해야 합니다.", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String filePath = System.getProperty("user.home") + "\\data\\Academic_info.txt";
    StringBuilder updatedContent = new StringBuilder();
    boolean isProfessorFound = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String studentNumber = "", name = "", birthDate = "", phone = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

             if (line.startsWith("교수번호: ")) {
                studentNumber = line.substring(6);
            } else if (line.startsWith("이름: ")) {
                name = line.substring(4);
            } else if (line.startsWith("생년월일: ")) {
                birthDate = line.substring(6);
            } else if (line.startsWith("휴대폰: ")) {
                phone = line.substring(5);
            } else if (line.isEmpty()) {
                if (studentNumber.equals(existingProfessorNumber)) {
                    isProfessorFound = true;
                    // 수정된 값으로 갱신
                    updatedContent.append("교수번호: ").append(studentNumber).append("\n");
                    updatedContent.append("이름: ").append(updatedName).append("\n");
                    updatedContent.append("생년월일: ").append(updatedBirthDate).append("\n");
                    updatedContent.append("휴대폰: ").append(updatedPhone).append("\n\n");
                } else {
                    // 기존 데이터 유지
                    updatedContent.append("교수번호: ").append(studentNumber).append("\n");
                    updatedContent.append("이름: ").append(name).append("\n");
                    updatedContent.append("생년월일: ").append(birthDate).append("\n");
                    updatedContent.append("휴대폰: ").append(phone).append("\n\n");
                }
                studentNumber = name = birthDate = phone = ""; // 데이터 초기화
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일을 읽는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!isProfessorFound) {
        JOptionPane.showMessageDialog(this, "선택된 교수 정보를 찾을 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 수정된 내용 파일에 덮어쓰기
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write(updatedContent.toString());
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일을 저장하는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JOptionPane.showMessageDialog(this, "교수 정보가 성공적으로 수정되었습니다.");

    // 테이블 갱신
    loadStudentInfo();
    S_list.repaint(); // 강제 UI 갱신
    clearInputFields(); // 입력 필드 초기화
    }//GEN-LAST:event_saveActionPerformed

    private void info_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_info_refreshActionPerformed
   // S_info 초기화
    DefaultTableModel infoModel = (DefaultTableModel) S_info.getModel();
    infoModel.setRowCount(0);

    String filePath = System.getProperty("user.home") +"\\data\\Academic_info.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String studentNumber = "", name = "", birthDate = "", phone = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("교수번호: ")) {
                studentNumber = line.substring(6);
            } else if (line.startsWith("이름: ")) {
                name = line.substring(4);
            } else if (line.startsWith("생년월일: ")) {
                birthDate = line.substring(6);
            } else if (line.startsWith("휴대폰: ")) {
                phone = line.substring(5);
            } else if (line.isEmpty()) {
                // 모든 필드가 채워진 경우 S_info에 추가
                if (!studentNumber.isEmpty() && !name.isEmpty() && 
                    !birthDate.isEmpty() && !phone.isEmpty()) {
                    infoModel.addRow(new Object[]{name, studentNumber, birthDate, phone});
                }
                // 데이터 초기화
                studentNumber = name = birthDate = phone = "";
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일을 읽는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_info_refreshActionPerformed

    private void birthlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthlastActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthlastActionPerformed

    private void PhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhoneActionPerformed
// 입력 필드를 초기화하는 메서드
private void clearInputFields() {
    Name.setText("");           // 이름
    Phone.setText("");          // 전화번호
    birth.setText("");          // 주민등록번호 앞자리
    birthlast.setText(""); //주민등록번호 뒷자리
}

private void updateStudentInfoInFile(String updatedName, String updatedStudentNumber, String updatedDepartment, 
                                     String updatedGrade, String updatedBirthDate, String updatedPhone) {
    String filePath = System.getProperty("user.home") + "\\data\\Academic_info.txt";
    StringBuilder updatedContent = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        String studentNumber = "", name = "", birthDate = "", phone = "";
        boolean isStudentFound = false;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

           if (line.startsWith("교수번호: ")) {
                studentNumber = line.substring(6);
            } else if (line.startsWith("이름: ")) {
                name = line.substring(4);
            }  else if (line.startsWith("생년월일: ")) {
                birthDate = line.substring(6);
            } else if (line.startsWith("휴대폰: ")) {
                phone = line.substring(5);
            } else if (line.isEmpty()) {
                // 기존 데이터와 입력된 데이터를 비교해 일치하는 학생 찾기
                if (name.equals(updatedName) && studentNumber.equals(updatedStudentNumber)) {
                    isStudentFound = true;

                    // 수정된 값으로 덮어쓰기 (수정하지 않은 값은 기존 값을 유지)
                    updatedContent.append("교수번호: ").append(updatedStudentNumber).append("\n");
                    updatedContent.append("이름: ").append(updatedName).append("\n");
                    updatedContent.append("생년월일: ").append(updatedBirthDate.isEmpty() ? birthDate : updatedBirthDate).append("\n");
                    updatedContent.append("휴대폰: ").append(updatedPhone.isEmpty() ? phone : updatedPhone).append("\n\n");
                } else {
                    // 기존 데이터 유지
                    updatedContent.append("교수번호: ").append(studentNumber).append("\n");
                    updatedContent.append("이름: ").append(name).append("\n");
                    updatedContent.append("생년월일: ").append(birthDate).append("\n");
                    updatedContent.append("휴대폰: ").append(phone).append("\n\n");
                }
            }
        }

        if (!isStudentFound) {
            JOptionPane.showMessageDialog(this, "수정할 교수 정보를 찾을 수 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일을 읽는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // 파일에 수정된 내용 덮어쓰기
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write(updatedContent.toString());
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일을 저장하는 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(A_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(A_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(A_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(A_Management.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold> 
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new A_Management().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton Before;
    private javax.swing.JButton Delete;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField Phone;
    private javax.swing.JTable S_info;
    private javax.swing.JTable S_list;
    private javax.swing.JTextField S_search;
    private javax.swing.JLabel Title;
    private javax.swing.JTextField birth;
    private javax.swing.JLabel birthdate;
    private javax.swing.JTextField birthlast;
    private javax.swing.JButton info_refresh;
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
