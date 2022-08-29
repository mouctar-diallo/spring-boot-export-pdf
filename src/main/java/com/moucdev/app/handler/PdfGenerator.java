package com.moucdev.app.handler;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.moucdev.app.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);

    public static ByteArrayInputStream generatePdfReport(List<User> users) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            //Ajoutons le titre du document pdf
            addTitleToDocument(document,"Table des Utilisateurs");

            //Permet de definir le nombre de column de la table ici (4)
            PdfPTable table = new PdfPTable(4);
            Stream.of("ID", "FirstName", "LastName", "Email").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

                header.setBackgroundColor(BaseColor.ORANGE);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);

            });

            for (User user: users) {
                PdfPCell cellId = createCell(user.getId().toString());
                table.addCell(cellId);

                PdfPCell cellFirstName = createCell(user.getFirstName());
                table.addCell(cellFirstName);

                PdfPCell cellLastName = createCell(user.getLastName());
                table.addCell(cellLastName);

                PdfPCell cellEmail = createCell(user.getEmail());
                table.addCell(cellEmail);
            }

            document.add(table);

            //close document
            document.close();

        } catch (DocumentException ex) {
            logger.error(ex.toString());
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    //Permet de crée une cellule au tableau
    public static PdfPCell createCell(String cellName) {

        PdfPCell cell = new PdfPCell(new Phrase(cellName));
        cell.setPaddingLeft(4);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        return cell;
    }

    //Permet d'ajouter un titre au document pdf
    public static void addTitleToDocument(Document document, String titleDocument) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Paragraph para = new Paragraph(titleDocument , font);
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
        document.add(Chunk.NEWLINE);
    }
}