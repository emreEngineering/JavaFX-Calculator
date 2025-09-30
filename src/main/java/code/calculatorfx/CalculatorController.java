package code.calculatorfx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

public class CalculatorController {

    @FXML
    private TextField screen;

    private String currentInput = "";
    private List<Double> numbers = new ArrayList<>();
    private List<String> operators = new ArrayList<>();
    private boolean startNewNumber = true;
    private String displayExpression = "";

    @FXML
    public void initialize() {
        screen.setText("0");
    }

    // Sayı butonları
    @FXML private void handleZeroButton() { appendNumber("0"); }
    @FXML private void handleTwoZeroButton() { appendNumber("00"); }
    @FXML private void handleOneButton() { appendNumber("1"); }
    @FXML private void handleTwoButton() { appendNumber("2"); }
    @FXML private void handleThreeButton() { appendNumber("3"); }
    @FXML private void handleFourButton() { appendNumber("4"); }
    @FXML private void handleFiveButton() { appendNumber("5"); }
    @FXML private void handleSixButton() { appendNumber("6"); }
    @FXML private void handleSevenButton() { appendNumber("7"); }
    @FXML private void handleEightButton() { appendNumber("8"); }
    @FXML private void handleNineButton() { appendNumber("9"); }

    @FXML
    private void handleDotButton() {
        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty() || startNewNumber) {
                currentInput = "0.";
                startNewNumber = false;
            } else {
                currentInput += ".";
            }
            updateDisplay();
        }
    }

    // Operatör butonları
    @FXML
    private void handlePlusButton() {
        addOperator("+");
    }

    @FXML
    private void handleMinusButton() {
        addOperator("-");
    }

    @FXML
    private void handleMultiplyButton() {
        addOperator("×");
    }

    @FXML
    private void handleDividedButton() {
        addOperator("÷");
    }

    @FXML
    private void handleEqualsButton() {
        if (!currentInput.isEmpty() && !numbers.isEmpty()) {
            // Son sayıyı listeye ekle
            numbers.add(Double.parseDouble(currentInput));

            // İfadeyi ekranda göster
            displayExpression += " " + currentInput + " = ";

            // Hesaplama yap
            double result = calculateWithPriority();

            // Sonucu göster
            if (result == (long) result) {
                displayExpression += String.valueOf((long) result);
            } else {
                displayExpression += String.valueOf(result);
            }

            screen.setText(displayExpression);

            // Sonucu yeni işlemler için hazırla
            resetForNewCalculation(String.valueOf(result));
        }
    }

    @FXML
    private void handleClearButton() {
        clearAll();
        screen.setText("0");
    }

    // Yardımcı metodlar
    private void appendNumber(String number) {
        if (startNewNumber) {
            currentInput = number;
            startNewNumber = false;
        } else {
            currentInput += number;
        }
        updateDisplay();
    }

    private void addOperator(String operator) {
        if (!currentInput.isEmpty()) {
            // Mevcut sayıyı listeye ekle
            numbers.add(Double.parseDouble(currentInput));

            // Operatörü listeye ekle
            operators.add(operator);

            // Ekran ifadesini güncelle
            if (displayExpression.isEmpty()) {
                displayExpression = currentInput + " " + operator;
            } else {
                displayExpression += " " + currentInput + " " + operator;
            }

            screen.setText(displayExpression);

            // Yeni sayı için hazırla
            currentInput = "";
            startNewNumber = true;
        } else if (!numbers.isEmpty() && !operators.isEmpty()) {
            // Son operatörü değiştir
            operators.set(operators.size() - 1, operator);
            displayExpression = displayExpression.substring(0, displayExpression.lastIndexOf(" ")) + " " + operator;
            screen.setText(displayExpression);
        }
    }

    private void updateDisplay() {
        if (!displayExpression.isEmpty() && !currentInput.isEmpty()) {
            screen.setText(displayExpression + " " + currentInput);
        } else if (!displayExpression.isEmpty()) {
            screen.setText(displayExpression);
        } else {
            screen.setText(currentInput.isEmpty() ? "0" : currentInput);
        }
    }

    private double calculateWithPriority() {
        // Listeleri kopyala (çarpma ve bölme işlemleri için)
        List<Double> tempNumbers = new ArrayList<>(numbers);
        List<String> tempOperators = new ArrayList<>(operators);

        // Önce çarpma ve bölme işlemlerini yap
        for (int i = 0; i < tempOperators.size(); i++) {
            String operator = tempOperators.get(i);
            if (operator.equals("×") || operator.equals("÷")) {
                double num1 = tempNumbers.get(i);
                double num2 = tempNumbers.get(i + 1);
                double result = performOperation(num1, num2, operator);

                // Sonucu yerleştir ve kullanılan elemanları kaldır
                tempNumbers.set(i, result);
                tempNumbers.remove(i + 1);
                tempOperators.remove(i);
                i--; // İndex kaymasını engelle
            }
        }

        // Sonra toplama ve çıkarma işlemlerini yap
        double result = tempNumbers.get(0);
        for (int i = 0; i < tempOperators.size(); i++) {
            String operator = tempOperators.get(i);
            double nextNumber = tempNumbers.get(i + 1);
            result = performOperation(result, nextNumber, operator);
        }

        return result;
    }

    private double performOperation(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "×":
                return num1 * num2;
            case "÷":
                if (num2 == 0) {
                    throw new ArithmeticException("Sıfıra bölünemez");
                }
                return num1 / num2;
            default:
                return num2;
        }
    }

    private void resetForNewCalculation(String result) {
        // Sadece sonucu tut, diğer her şeyi temizle
        currentInput = result;
        numbers.clear();
        operators.clear();
        displayExpression = "";
        startNewNumber = true;
    }

    private void clearAll() {
        currentInput = "";
        numbers.clear();
        operators.clear();
        displayExpression = "";
        startNewNumber = true;
    }
}