package util;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public final class UiTheme {
    public static final Color BLACK = new Color(8, 8, 8);
    public static final Color DARK = new Color(18, 18, 18);
    public static final Color PANEL = new Color(28, 28, 28);
    public static final Color CARD = new Color(38, 38, 38);
    public static final Color RED = new Color(220, 20, 45);
    public static final Color RED_DARK = new Color(120, 0, 0);
    public static final Color RED_GLOW = new Color(255, 45, 55);
    public static final Color RED_LIGHT = new Color(255, 100, 100);
    public static final Color WHITE = Color.WHITE;
    public static final Color TEXT = new Color(245, 245, 245);
    public static final Color MUTED = new Color(160, 160, 160);

    private static final String FONT = resolveFontFamily();

    private UiTheme() {
    }

    private static String resolveFontFamily() {
        String[] preferred = { "Segoe UI", "Arial", "SansSerif" };
        String[] available = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String name : preferred) {
            for (String a : available) {
                if (a.equalsIgnoreCase(name)) {
                    return name;
                }
            }
        }
        return Font.SANS_SERIF;
    }

    public static void applyGlobal() {
        UIManager.put("Panel.background", DARK);
        UIManager.put("OptionPane.background", PANEL);
        UIManager.put("OptionPane.messageForeground", TEXT);
        UIManager.put("TabbedPane.background", BLACK);
        UIManager.put("TabbedPane.foreground", TEXT);
        UIManager.put("TabbedPane.selected", RED);
        UIManager.put("TabbedPane.highlight", RED_DARK);
        UIManager.put("TabbedPane.focus", RED_DARK);
        UIManager.put("TabbedPane.contentOpaque", Boolean.TRUE);
        UIManager.put("Table.background", CARD);
        UIManager.put("Table.foreground", TEXT);
        UIManager.put("Table.gridColor", RED_DARK);
        UIManager.put("Table.selectionBackground", RED);
        UIManager.put("Table.selectionForeground", WHITE);
        UIManager.put("TableHeader.background", BLACK);
        UIManager.put("TableHeader.foreground", RED);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("TextField.background", CARD);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.caretForeground", RED_GLOW);
        UIManager.put("PasswordField.background", CARD);
        UIManager.put("PasswordField.foreground", TEXT);
        UIManager.put("PasswordField.caretForeground", RED_GLOW);
        UIManager.put("ComboBox.background", CARD);
        UIManager.put("ComboBox.foreground", TEXT);
        UIManager.put("ComboBox.selectionBackground", RED);
        UIManager.put("ComboBox.selectionForeground", WHITE);
        UIManager.put("ScrollPane.background", DARK);
        UIManager.put("Viewport.background", DARK);
        UIManager.put("List.background", CARD);
        UIManager.put("List.foreground", TEXT);
        UIManager.put("List.selectionBackground", RED);
        UIManager.put("List.selectionForeground", WHITE);
        UIManager.put("Spinner.background", CARD);
        UIManager.put("Spinner.foreground", TEXT);
        UIManager.put("Menu.background", BLACK);
        UIManager.put("Menu.foreground", WHITE);
        UIManager.put("MenuItem.background", PANEL);
        UIManager.put("MenuItem.foreground", TEXT);
        UIManager.put("MenuItem.selectionBackground", RED);
        UIManager.put("MenuItem.selectionForeground", WHITE);
        UIManager.put("MenuBar.background", BLACK);
        UIManager.put("TitledBorder.titleColor", RED);
    }

    public static Font bold(int size) {
        return new Font(FONT, Font.BOLD, size);
    }

    public static Font plain(int size) {
        return new Font(FONT, Font.PLAIN, size);
    }

    public static String tabTitle(String title) {
        return "<html><div style='text-align:center;width:180px;font-family:" + FONT
                + ";font-size:15px;font-weight:900;color:#F5F5F5;padding:10px 0;'>"
                + title + "</div></html>";
    }

    public static String adminTabTitle(String title) {
        return "<html><span style='font-family:" + FONT
                + ";font-size:14px;font-weight:900;color:#F5F5F5;padding:6px 10px;'>"
                + title + "</span></html>";
    }

    public static void panel(JComponent c) {
        c.setBackground(DARK);
        c.setOpaque(true);
    }

    public static void card(JComponent c) {
        c.setBackground(CARD);
        c.setOpaque(true);
    }

    public static TitledBorder titledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(RED, 2),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                bold(14),
                RED);
    }

    public static void title(JLabel lbl, int size) {
        lbl.setFont(bold(size));
        lbl.setForeground(RED);
    }

    public static void accent(JLabel lbl, int size) {
        lbl.setFont(bold(size));
        lbl.setForeground(RED_GLOW);
    }

    public static void label(JLabel lbl) {
        lbl.setFont(bold(13));
        lbl.setForeground(TEXT);
    }

    public static void primaryButton(JButton btn) {
        btn.setBackground(RED);
        btn.setForeground(WHITE);
        btn.setFont(bold(13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void outlineButton(JButton btn) {
        btn.setBackground(BLACK);
        btn.setForeground(RED_GLOW);
        btn.setFont(bold(13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(RED, 2));
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void ghostButton(JButton btn) {
        btn.setBackground(PANEL);
        btn.setForeground(TEXT);
        btn.setFont(bold(12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(RED_DARK, 1));
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void menuBar(JMenuBar bar) {
        bar.setBackground(BLACK);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, RED));
    }

    public static void topMenu(JMenu menu) {
        menu.setForeground(WHITE);
        menu.setFont(bold(14));
    }

    public static void banButton(JButton btn, boolean trong) {
        btn.setFont(bold(14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(trong ? RED_DARK : RED, trong ? 2 : 3),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        if (trong) {
            btn.setBackground(PANEL);
            btn.setForeground(TEXT);
        } else {
            btn.setBackground(RED);
            btn.setForeground(WHITE);
        }
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void menuItemCard(JPanel panel) {
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(RED_DARK, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    public static void table(JTable table) {
        table.setBackground(CARD);
        table.setForeground(TEXT);
        table.setGridColor(RED_DARK);
        table.setSelectionBackground(RED);
        table.setSelectionForeground(WHITE);
        table.setFont(plain(13));
        table.getTableHeader().setBackground(BLACK);
        table.getTableHeader().setForeground(RED);
        table.getTableHeader().setFont(bold(13));
    }

    public static void scrollPane(JScrollPane sp) {
        sp.getViewport().setBackground(DARK);
        sp.setBorder(BorderFactory.createLineBorder(RED_DARK, 1));
    }

    public static void menuScrollPane(JScrollPane sp) {
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(DARK);
        sp.setBackground(DARK);
    }

    public static void sectionHeader(JLabel lbl, String title) {
        lbl.setText(title);
        lbl.setFont(bold(15));
        lbl.setForeground(RED);
        lbl.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        lbl.setOpaque(false);
    }

    public static void menuTabbedPane(JTabbedPane tabs) {
        tabs.setBackground(DARK);
        tabs.setForeground(TEXT);
        tabs.setFont(bold(12));
        tabs.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
    }

    public static void qtyButton(JButton btn) {
        btn.setFont(bold(14));
        btn.setBackground(CARD);
        btn.setForeground(TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(RED_DARK, 1));
        btn.setPreferredSize(new Dimension(28, 28));
        btn.setMinimumSize(new Dimension(28, 28));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }

    public static void addToOrderButton(JButton btn) {
        btn.setText("Thêm vào đơn");
        btn.setFont(bold(11));
        primaryButton(btn);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4));
    }

    public static void invoicePanel(JPanel panel) {
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(RED_DARK, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        panel.setOpaque(true);
    }

    public static JPanel roundedCard(int arc) {
        final int radius = arc;
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
                g2.setColor(RED_DARK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBackground(CARD);
        return p;
    }

    public static JLabel roundedImageLabel(ImageIcon icon, int maxW, int maxH, int arc) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        double scale = (w > 0 && h > 0) ? Math.min((double) maxW / w, (double) maxH / h) : 1;
        int nw = Math.max(1, (int) Math.round(w * scale));
        int nh = Math.max(1, (int) Math.round(h * scale));
        Image scaled = icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH);
        ImageIcon fitted = new ImageIcon(scaled);
        return new JLabel(fitted, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int x = (getWidth() - fitted.getIconWidth()) / 2;
                int y = (getHeight() - fitted.getIconHeight()) / 2;
                g2.setClip(new java.awt.geom.RoundRectangle2D.Float(
                        x, y, fitted.getIconWidth(), fitted.getIconHeight(), arc, arc));
                g2.drawImage(fitted.getImage(), x, y, null);
                g2.dispose();
            }
        };
    }
}
