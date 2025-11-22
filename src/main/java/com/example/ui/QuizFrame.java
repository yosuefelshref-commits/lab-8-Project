package com.example.ui;

import com.example.models.*;
import com.example.services.CourseService;
import com.example.database.JsonDatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizFrame extends JFrame {
    private Quiz quiz;
    private Student student;
    private Lesson lesson;
    private Course course;
    private StudentDashboardFrame dashboard;

    private JPanel mainPanel;
    private JButton submitBtn;
    private JScrollPane scrollPane;
    private ButtonGroup[] buttonGroups; // لكل سؤال مجموعة أزرار راديو

    public QuizFrame(Quiz quiz, Student student, Lesson lesson, Course course, StudentDashboardFrame dashboard) {
        this.quiz = quiz;
        this.student = student;
        this.lesson = lesson;
        this.course = course;
        this.dashboard = dashboard;

        setTitle("Quiz: " + lesson.getTitle());
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        List<Question> questions = quiz.getQuestions();
        buttonGroups = new ButtonGroup[questions.size()];

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            JPanel qPanel = new JPanel();
            qPanel.setLayout(new BoxLayout(qPanel, BoxLayout.Y_AXIS));
            qPanel.setBorder(BorderFactory.createTitledBorder("Question " + (i+1)));

            JLabel qLabel = new JLabel(q.getQuestionText());
            qPanel.add(qLabel);

            ButtonGroup group = new ButtonGroup();
            buttonGroups[i] = group;

            List<String> options = q.getOptions();
            for (int j = 0; j < options.size(); j++) {
                JRadioButton rb = new JRadioButton(options.get(j));
                rb.setActionCommand(String.valueOf(j));
                group.add(rb);
                qPanel.add(rb);
            }

            mainPanel.add(qPanel);
        }

        submitBtn = new JButton("Submit Quiz");
        submitBtn.addActionListener(e -> submitQuiz());
        add(submitBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void submitQuiz() {
        List<Question> questions = quiz.getQuestions();
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            ButtonGroup group = buttonGroups[i];
            if (group.getSelection() != null) {
                int selected = Integer.parseInt(group.getSelection().getActionCommand());
                StudentAnswer sa = new StudentAnswer(student.getId(), q, selected);
                quiz.getStudentAnswers().add(sa);
                if (selected == q.getCorrectAnswer()) score += 1;
            }
        }

        // حساب النتيجة النهائية بالنسبة المئوية
        int percentage = (int) ((score * 100.0) / questions.size());

        // حفظ النتيجة في Student
        student.saveQuizScore(lesson.getLessonId(), percentage);
        JsonDatabaseManager.getInstance().saveUsers();
        CourseService.getInstance().saveCourses();

        // عرض النتائج والإجابات الصحيحة
        StringBuilder resultMsg = new StringBuilder();
        resultMsg.append("Your Score: ").append(percentage).append("%\n\nCorrect Answers:\n");

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            resultMsg.append("Q").append(i+1).append(": ").append(q.getOptions().get(q.getCorrectAnswer())).append("\n");
        }

        JOptionPane.showMessageDialog(this, resultMsg.toString(), "Quiz Results", JOptionPane.INFORMATION_MESSAGE);

        // تحديث التقدم في Dashboard
        dashboard.refreshEnrolledCourses();
        dispose();
    }
}
