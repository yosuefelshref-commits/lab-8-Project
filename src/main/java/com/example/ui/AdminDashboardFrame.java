package com.example.ui;

import com.example.models.Course;
import com.example.models.CourseStatus;
import com.example.services.CourseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class AdminDashboardFrame extends JFrame {
    private CourseService courseService;
    private JTable pendingTable;
    private DefaultTableModel pendingModel;

    public AdminDashboardFrame() {
        this.courseService = CourseService.getInstance();

        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Welcome, Admin");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        // ================= Pending Courses Table =================
        String[] columns = {"ID", "Title", "Instructor", "Description", "Status", "Action"};
        pendingModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return column == 5; }
        };
        pendingTable = new JTable(pendingModel);
        pendingTable.getColumn("Action").setCellRenderer(new ButtonRenderer());

        JScrollPane scrollPane = new JScrollPane(pendingTable);
        add(scrollPane, BorderLayout.CENTER);

        refreshPendingCourses();
        setVisible(true);
    }

    private void refreshPendingCourses() {
        pendingModel.setRowCount(0);
        List<Course> courses = courseService.getPendingCourses(); // النسخة بدون argument
        if(courses == null || courses.isEmpty()) return;

        for(Course c : courses) {
            pendingModel.addRow(new Object[]{
                    c.getCourseId(),
                    c.getTitle(),
                    (c.getInstructor() != null) ? c.getInstructor().getUsername() : "Unknown",
                    c.getDescription(),
                    c.getStatus(),
                    "Approve / Decline"
            });
        }

        if(pendingTable.getRowCount() > 0) {
            pendingTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), courseId -> {
                showApproveDeclineDialog(courseId);
            }));
        }
    }

    private void showApproveDeclineDialog(int courseId) {
        Course c = courseService.getCourseById(courseId);
        if(c == null) return;

        int choice = JOptionPane.showOptionDialog(this,
                "Approve or Decline course: " + c.getTitle(),
                "Course Review",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Approve", "Decline"},
                "Approve");

        if(choice == JOptionPane.YES_OPTION) {
            c.setStatus(CourseStatus.APPROVED); // استخدم Enum مباشرة
        } else if(choice == JOptionPane.NO_OPTION) {
            c.setStatus(CourseStatus.REJECTED);
        } else {
            return;
        }

        courseService.saveCourses();
        refreshPendingCourses();
    }

    // ================= Renderers & Editors =================
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    interface ButtonAction { void action(int courseId); }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private int courseId;
        private boolean clicked;
        private ButtonAction action;

        public ButtonEditor(JCheckBox checkBox, ButtonAction action){
            super(checkBox);
            this.button = new JButton("Action");
            this.button.setOpaque(true);
            this.action = action;

            button.addActionListener(e -> {
                if(clicked && courseId != -1){
                    action.action(courseId);
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
            courseId = -1;
            if(table.getRowCount() > 0 && row >= 0 && row < table.getRowCount()){
                Object idObj = table.getValueAt(row,0);
                if(idObj instanceof Integer) courseId = (Integer) idObj;
            }
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue(){
            clicked = false;
            return "";
        }
    }
}
