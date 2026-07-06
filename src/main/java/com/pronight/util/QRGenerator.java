package com.pronight.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class QRGenerator {

    public static String generateQR(String text) {

        try {
            QRCodeWriter writer = new QRCodeWriter();

            var bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 200, 200);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            return Base64.getEncoder()
                    .encodeToString(outputStream.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}