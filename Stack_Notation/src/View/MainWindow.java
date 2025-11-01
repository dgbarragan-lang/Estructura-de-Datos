package View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import Controller.NotationConverter;

public class MainWindow extends JFrame {

    private JTextField txtExpression;
    private JTextArea txtOutput;
    private JButton btnToPostfix, btnToPrefix, btnEvaluatePostfix, btnClear;
    private NotationConverter converter;
    private Map<Character, Double> variables;
    private String lastPostfix;

    public MainWindow() {
        converter = new NotationConverter();
        variables = new HashMap<>();
        assignDefaultValues();
        lastPostfix = "";

        setTitle("Expression Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== Panel superior: entrada + botones =====
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Campo de texto
        txtExpression = new JTextField();
        txtExpression.setFont(new Font("Consolas", Font.PLAIN, 16));
        txtExpression.setBorder(BorderFactory.createTitledBorder("Input Expression"));
        topPanel.add(txtExpression, BorderLayout.NORTH);

        // Botones pequeños centrados
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnToPostfix = createSmallButton("Postfix", new Color(52, 152, 219));
        btnToPrefix = createSmallButton("Prefix", new Color(230, 126, 34));
        btnEvaluatePostfix = createSmallButton("Evaluate", new Color(46, 204, 113));
        btnClear = createSmallButton("Clear", new Color(149, 165, 166));

        panelButtons.add(btnToPostfix);
        panelButtons.add(btnToPrefix);
        panelButtons.add(btnEvaluatePostfix);
        panelButtons.add(btnClear);

        topPanel.add(panelButtons, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // ===== Área de salida: ocupa todo el centro =====
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Consolas", Font.PLAIN, 16));
        txtOutput.setBackground(new Color(15, 15, 20));
        txtOutput.setForeground(new Color(0, 255, 128));
        txtOutput.setLineWrap(true);
        txtOutput.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtOutput);
        scroll.setBorder(BorderFactory.createTitledBorder("Conversion & Evaluation Steps"));
        add(scroll, BorderLayout.CENTER); // <<<<< CAMBIO AQUÍ

        // ===== Acciones =====
        btnToPostfix.addActionListener(e -> convertToPostfix());
        btnToPrefix.addActionListener(e -> convertToPrefix());
        btnEvaluatePostfix.addActionListener(e -> evaluatePostfix());
        btnClear.addActionListener(e -> clearAll());

        setVisible(true);
    }

    // Crear botones pequeños
    private JButton createSmallButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(90, 28)); // más pequeños aún
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        return btn;
    }

    private void assignDefaultValues() {
        variables.put('A',1.0);
        variables.put('B',2.0);
        variables.put('C',3.0);
        variables.put('D',4.0);
        variables.put('X',10.0);
        variables.put('Y',20.0);
    }

    private void convertToPostfix() {
        try {
            String infix = txtExpression.getText().trim();
            if(infix.isEmpty()) return;
            String steps = converter.convertToPostfix(infix);
            txtOutput.setText("=== CONVERT TO POSTFIX ===\n" + steps + "\n");
            lastPostfix = steps.substring(steps.lastIndexOf("Final postfix: ") + 15).trim();
        } catch(Exception ex) {
            txtOutput.setText("Error: " + ex.getMessage());
        }
    }

    private void convertToPrefix() {
        try {
            String infix = txtExpression.getText().trim();
            if(infix.isEmpty()) return;
            String steps = converter.infixToPrefixWithSteps(infix);
            txtOutput.setText("=== CONVERT TO PREFIX ===\n" + steps + "\n");
        } catch(Exception ex) {
            txtOutput.setText("Error: " + ex.getMessage());
        }
    }

    private void evaluatePostfix() {
        if(lastPostfix.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please convert to Postfix first");
            return;
        }
        try {
            String steps = converter.evaluatePostfixStepByStep(lastPostfix, variables);
            txtOutput.append("=== EVALUATE POSTFIX ===\n" + steps + "\n");
        } catch(Exception ex) {
            txtOutput.setText("Error: " + ex.getMessage());
        }
    }

    private void clearAll() {
        txtExpression.setText("");
        txtOutput.setText("");
        lastPostfix = "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
