package uptc.edu.co.logic;

public class Calculator {

    /**
     * Evalúa la expresión matemática dada.
     * 
     * @param expression la expresión matemática en notación infija.
     * @return el resultado de la evaluación.
     */
    public double evaluateExpression(String expression) {
        String postfix = infixToPostfix(expression.replaceAll("\\s", ""));
        return evaluatePostfix(postfix);
    }

    /**
     * Convierte una expresión en notación infija a notación postfija.
     * 
     * @param expression la expresión en notación infija.
     * @return la expresión en notación postfija.
     */
    private static String infixToPostfix(String expression) {
        StackP<Character> stack = new StackP<>();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                while (i < expression.length()
                        && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    output.append(expression.charAt(i++));
                }
                output.append(' ');
                i--;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(' ');
                }
                stack.pop();
            } else if (c == '√') {
                stack.push(c);
            } else { // operator
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            output.append(stack.pop()).append(' ');
        }
        return output.toString();
    }

    /**
     * Evalúa una expresión en notación postfija.
     * 
     * @param expression la expresión en notación postfija.
     * @return el resultado de la evaluación.
     */
    private static double evaluatePostfix(String expression) {
        StackP<Double> stack = new StackP<>();
        String[] tokens = expression.split("\\s+");
        for (String token : tokens) {
            if (token.isEmpty())
                continue;
            if (Character.isDigit(token.charAt(0)) || token.charAt(0) == '.') {
                stack.push(Double.parseDouble(token));
            } else {
                if (token.equals("√")) {
                    double val = stack.pop();
                    stack.push(Math.sqrt(val));
                } else {
                    double val2 = stack.pop();
                    double val1 = stack.pop();
                    switch (token) {
                        case "+":
                            stack.push(val1 + val2);
                            break;
                        case "-":
                            stack.push(val1 - val2);
                            break;
                        case "*":
                            stack.push(val1 * val2);
                            break;
                        case "/":
                            stack.push(val1 / val2);
                            break;
                        case "^":
                            stack.push(Math.pow(val1, val2));
                            break;
                    }
                }
            }
        }
        return stack.pop();
    }

    public boolean validateParentheses(String expression) {
        int balance = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            }
            if (balance < 0) {
                return false;
            }
        }
        return balance == 0;
    }

    /**
     * Devuelve la precedencia del operador.
     * 
     * @param op el operador.
     * @return la precedencia del operador.
     */
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            case '√':
                return 4;
            default:
                return 0;
        }
    }

    /**
     * Convierte un número de una base a otra.
     * 
     * @param number      el número a convertir.
     * @param currentBase la base actual del número.
     * @param targetBase  la base destino del número.
     * @return el número en la base destino.
     */
    public String convertBase(String number, String currentBase, String targetBase) {
        int decimal = convertToDecimal(number, currentBase);
        return convertFromDecimal(decimal, targetBase);
    }

    /**
     * Convierte un número de una base dada a decimal.
     * 
     * @param number el número a convertir.
     * @param base   la base del número.
     * @return el número en decimal.
     */
    public int convertToDecimal(String number, String base) {
        switch (base) {
            case "BIN":
                if (!number.matches("[01]+")) {
                    throw new IllegalArgumentException("Número binario inválido: " + number);
                }
                return Integer.parseInt(number, 2);
            case "OCT":
                if (!number.matches("[0-7]+")) {
                    throw new IllegalArgumentException("Número octal inválido: " + number);
                }
                return Integer.parseInt(number, 8);
            case "DEC":
                if (!number.matches("\\d+")) {
                    throw new IllegalArgumentException("Número decimal inválido: " + number);
                }
                return Integer.parseInt(number);
            case "HEX":
                if (!number.matches("[0-9A-Fa-f]+")) {
                    throw new IllegalArgumentException("Número hexadecimal inválido: " + number);
                }
                return Integer.parseInt(number, 16);
            default:
                throw new IllegalArgumentException("Base desconocida: " + base);
        }
    }

    /**
     * Convierte un número decimal a una base dada.
     * 
     * @param decimal el número decimal.
     * @param base    la base destino.
     * @return el número en la base destino.
     */
    private String convertFromDecimal(int decimal, String base) {
        switch (base) {
            case "BIN":
                return Integer.toBinaryString(decimal);
            case "OCT":
                return Integer.toOctalString(decimal);
            case "DEC":
                return Integer.toString(decimal);
            case "HEX":
                return Integer.toHexString(decimal).toUpperCase();
            default:
                throw new IllegalArgumentException("Base desconocida: " + base);
        }
    }
}