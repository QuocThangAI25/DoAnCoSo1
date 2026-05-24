package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class QRCodeUtils {

    /**
     * Tạo mã QR từ nội dung text
     */
    public static BufferedImage generateQRCode(String text, int size) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Tạo JPanel chứa mã QR
     */
    public static JPanel createQRCodePanel(String text, int size) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        BufferedImage qrImage = generateQRCode(text, size);
        if (qrImage != null) {
            JLabel qrLabel = new JLabel(new ImageIcon(qrImage));
            qrLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(qrLabel, BorderLayout.CENTER);
        } else {
            JLabel errorLabel = new JLabel("Không thể tạo mã QR", SwingConstants.CENTER);
            panel.add(errorLabel, BorderLayout.CENTER);
        }
        
        return panel;
    }
}