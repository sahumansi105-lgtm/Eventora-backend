package com.pronight.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pronight.entity.BookingEntity;

@Service
public class TicketPDFService {

    public byte[] generateTicket(BookingEntity booking) {

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4, 20, 20, 20, 20);

            PdfWriter.getInstance(document, out);

            document.open();

            // =========================
            // FONTS
            // =========================
            Font titleFont =
                    new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, BaseColor.BLUE);

            Font headingFont =
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

            Font successFont =
                    new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.GREEN);

            // =========================
            // TITLE
            // =========================
            Paragraph title =
                    new Paragraph("PRO NIGHT EVENT TICKET", titleFont);

            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            Paragraph status =
                    new Paragraph("PAYMENT SUCCESSFUL ✓", successFont);

            status.setAlignment(Element.ALIGN_CENTER);
            document.add(status);

            document.add(new Paragraph(" "));

            // =========================
            // TABLE
            // =========================
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            addRow(table, "Event Name", booking.getEvent().getTitle());
            addRow(table, "Venue", booking.getEvent().getVenue());
            addRow(table, "Artist", booking.getEvent().getArtistName());
            addRow(table, "Event Date", String.valueOf(booking.getEvent().getEventDate()));
            addRow(table, "Event Time", String.valueOf(booking.getEvent().getEventTime()));

            addRow(table, "Customer Name", booking.getUser().getFullName());
            addRow(table, "Email", booking.getUser().getEmail());

            addRow(table, "Pass", booking.getPass().getPassName());
            addRow(table, "Quantity", String.valueOf(booking.getQuantity()));
            addRow(table, "Total Amount", "₹" + booking.getTotalAmount());

            addRow(table, "Booking Status", String.valueOf(booking.getBookingStatus()));

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // =========================
            // QR CODE
            // =========================
            if (booking.getQrCode() != null) {

                byte[] qrBytes =
                        Base64.getDecoder().decode(booking.getQrCode());

                Image qrImage = Image.getInstance(qrBytes);

                qrImage.scaleAbsolute(150, 150);
                qrImage.setAlignment(Element.ALIGN_CENTER);

                document.add(new Paragraph("ENTRY QR CODE", headingFont));
                document.add(new Paragraph(" "));
                document.add(qrImage);
            }

            // =========================
            // FOOTER
            // =========================
            document.add(new Paragraph(" "));

            Paragraph footer =
                    new Paragraph(
                            "Please show this ticket (QR Code) at the entry gate.\n" +
                            "This ticket is non-transferable.\n" +
                            "Powered by Pro Night",
                            new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)
                    );

            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // ADD ROW HELPER METHOD
    // =========================
    private void addRow(PdfPTable table, String key, String value) {

        Font keyFont =
                new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);

        Font valueFont =
                new Font(Font.FontFamily.HELVETICA, 11);

        PdfPCell keyCell =
                new PdfPCell(new Paragraph(key, keyFont));

        PdfPCell valueCell =
                new PdfPCell(new Paragraph(value, valueFont));

        keyCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        keyCell.setPadding(6);

        valueCell.setPadding(6);

        table.addCell(keyCell);
        table.addCell(valueCell);
    }
}