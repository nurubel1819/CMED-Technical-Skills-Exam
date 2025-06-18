package com.example.prescription.management.system.service;

import com.example.prescription.management.system.model.dto.PrescriptionDto;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] generatePrescriptionPdf(PrescriptionDto dto) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer  = new PdfWriter(out);
        PdfDocument pdf   = new PdfDocument(writer);
        Document doc      = new Document(pdf);

        // Title
        doc.add(new Paragraph("Prescription Report").setBold().setFontSize(18).setUnderline());

        // Table two column
        Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();

        /* ↓ একটি হেল্পার মেথড বানালে কোড ছোট হয় */
        addRow(table, "Prescription ID",
                dto.getPrescriptionId() == null ? "N/A" : dto.getPrescriptionId().toString());

        addRow(table, "Date",           dto.getPrescriptionDate().toString());
        addRow(table, "Patient Name",   dto.getPatientName());
        addRow(table, "Age",            String.valueOf(dto.getPatientAge()));
        addRow(table, "Gender",         dto.getPatientGender());
        addRow(table, "Diagnosis",      dto.getPatientDiagnosis());
        addRow(table, "Medicines",      dto.getPatientMedicines());
        addRow(table, "Next Visit",
                dto.getNextVisitDate() == null ? "N/A" : dto.getNextVisitDate().toString());

        doc.add(table);
        doc.close();
        return out.toByteArray();
    }

    private void addRow(Table table, String label, String value) {
        table.addCell(new Cell()
                .add(new Paragraph(label).setBold()));
        table.addCell(new Cell()
                .add(new Paragraph(value)));
    }
}
