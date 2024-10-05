import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
public class printswing extends JFrame {
    private JTable table;
    private JButton printButton;
    public printswing() {
        setTitle("MySQL Table Printer");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Password");
        fetchData(model);
        printButton = new JButton("Print Table");
        printButton.addActionListener(e -> printTable());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(printButton, BorderLayout.SOUTH);
    }
    private void fetchData(DefaultTableModel model) {
        String url = "jdbc:mysql://127.0.0.1:3306/sahayak";
        String user = "root";
        String password = "Luck0409@";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM user")) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("pass")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void printTable() {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                    if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }

                    graphics.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
                    table.printAll(graphics);
                    return PAGE_EXISTS;
                }
            });
            if (job.printDialog()) {
                job.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            printswing frame = new printswing();
            frame.setVisible(true);
        });
    }
}
