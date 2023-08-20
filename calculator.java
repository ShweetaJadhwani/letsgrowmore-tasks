import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class calculator extends JFrame implements ActionListener {

    private JTextField displayTextField;
    private String currentInput;
    private double memory;

    public calculator() {
        
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        currentInput = "";

     
        displayTextField = new JTextField();
        displayTextField.setEditable(false);

     
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "√", "sin", "cos", "tan",
                "M+", "M-", "MR", "C"
        };
        JButton[] buttons = new JButton[buttonLabels.length];

        
        JButton memoryAddButton = new JButton("M+");
        JButton memorySubtractButton = new JButton("M-");
        JButton memoryRecallButton = new JButton("MR");

        
        JPanel buttonPanel = new JPanel(new GridLayout(6, 4));
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

      
        memoryAddButton.addActionListener(this);
        memorySubtractButton.addActionListener(this);
        memoryRecallButton.addActionListener(this);

        setLayout(new BorderLayout());
        add(displayTextField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(memoryAddButton, BorderLayout.SOUTH);
        add(memorySubtractButton, BorderLayout.SOUTH);
        add(memoryRecallButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
            case ".":
               
                currentInput += command;
                displayTextField.setText(currentInput);
                break;
            case "/": case "*": case "-": case "+":
                
                currentInput += " " + command + " ";
                displayTextField.setText(currentInput);
                break;
            case "=":
             
                try {
                    currentInput = String.valueOf(evaluateExpression(currentInput));
                    displayTextField.setText(currentInput);
                } catch (Exception ex) {
                    displayTextField.setText("Error");
                }
                break;
            case "sin": case "cos": case "tan":
               
                String functionName = command;
                double angle = Double.parseDouble(currentInput);
                if (functionName.equals("sin")) {
                    currentInput = String.valueOf(Math.sin(Math.toRadians(angle)));
                } else if (functionName.equals("cos")) {
                    currentInput = String.valueOf(Math.cos(Math.toRadians(angle)));
                } else if (functionName.equals("tan")) {
                    currentInput = String.valueOf(Math.tan(Math.toRadians(angle)));
                }
                displayTextField.setText(currentInput);
                break;
            case "√":
               
                double sqrtValue = Double.parseDouble(currentInput);
                if (sqrtValue >= 0) {
                    currentInput = String.valueOf(Math.sqrt(sqrtValue));
                } else {
                    currentInput = "Error";
                }
                displayTextField.setText(currentInput);
                break;
            case "M+":
               
                try {
                    memory += Double.parseDouble(currentInput);
                } catch (NumberFormatException ex) {
                    displayTextField.setText("Invalid Input");
                }
                currentInput = "";
                break;
            case "M-":
           
                try {
                    memory -= Double.parseDouble(currentInput);
                } catch (NumberFormatException ex) {
                    displayTextField.setText("Invalid Input");
                }
                currentInput = "";
                break;
            case "MR":
             
                currentInput = String.valueOf(memory);
                displayTextField.setText(currentInput);
                break;
            case "C":
              
                if (!currentInput.isEmpty()) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    displayTextField.setText(currentInput);
                }
                break;
        }
    }

    private double evaluateExpression(String expression) {
        String[] parts = expression.split(" ");
        double result = Double.parseDouble(parts[0]);
        for (int i = 1; i < parts.length - 1; i += 2) {
            String operator = parts[i];
            double operand = Double.parseDouble(parts[i + 1]);
            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    result /= operand;
                    break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
          calculator calculator = new calculator();
            calculator.setVisible(true);
        });
    }
}