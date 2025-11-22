package com.example.utils;

import com.example.models.Certificate;
import com.example.models.Student;
import com.example.models.Course;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class CertificateGenerator {

    public static void generatePDF(Certificate cert, Student student, Course course) {
        try {
            String fileName = "Certificate_" + student.getUsername() + "_" + course.getTitle() + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 16);

            Paragraph title = new Paragraph("Certificate of Completion", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("This is to certify that", textFont));
            document.add(new Paragraph(student.getUsername(), titleFont));
            document.add(new Paragraph("has successfully completed the course:", textFont));
            document.add(new Paragraph(course.getTitle(), titleFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Issued on: " + cert.getIssueDate(), textFont));

            document.close();
            System.out.println("Certificate PDF generated: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
