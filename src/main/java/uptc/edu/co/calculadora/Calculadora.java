package uptc.edu.co.calculadora;


import java.util.Stack;

public class Calculadora {

    public double evaluarExpresion(String expresion) {
        return evaluarPostfija(convertirAPostfija(expresion));
    }

    private String convertirAPostfija(String expresion) {
        StringBuilder resultado = new StringBuilder();
        Stack<Character> pila = new Stack<>();
        for (char c : expresion.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                resultado.append(c);
            } else if (c == '(') {
                pila.push(c);
            } else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    resultado.append(' ').append(pila.pop());
                }
                pila.pop();
            } else if (esOperador(c)) {
                while (!pila.isEmpty() && prioridad(c) <= prioridad(pila.peek())) {
                    resultado.append(' ').append(pila.pop());
                }
                resultado.append(' ');
                pila.push(c);
            }
        }
        while (!pila.isEmpty()) {
            resultado.append(' ').append(pila.pop());
        }
        return resultado.toString();
    }

    private double evaluarPostfija(String expresionPostfija) {
        Stack<Double> pila = new Stack<>();
        for (String token : expresionPostfija.split("\\s+")) {
            if (token.isEmpty()) continue;
            if (esNumero(token)) {
                pila.push(Double.parseDouble(token));
            } else if (esOperador(token.charAt(0))) {
                double b = pila.pop();
                double a = pila.pop();
                switch (token.charAt(0)) {
                    case '+': pila.push(a + b); break;
                    case '-': pila.push(a - b); break;
                    case '*': pila.push(a * b); break;
                    case '/': pila.push(a / b); break;
                    case '^': pila.push(Math.pow(a, b)); break;
                }
            } else if (token.equals("√")) {
                double a = pila.pop();
                pila.push(Math.sqrt(a));
            }
        }
        return pila.pop();
    }

    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private int prioridad(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    private boolean esNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Métodos para conversión entre bases numéricas
    public String convertirABase(String numero, String baseActual, String baseDestino) {
        int decimal = convertirADecimal(numero, baseActual);
        return convertirDesdeDecimal(decimal, baseDestino);
    }

    private int convertirADecimal(String numero, String base) {
        switch (base) {
            case "BIN":
                return Integer.parseInt(numero, 2);
            case "OCT":
                return Integer.parseInt(numero, 8);
            case "DEC":
                return Integer.parseInt(numero);
            case "HEX":
                return Integer.parseInt(numero, 16);
            default:
                throw new IllegalArgumentException("Base desconocida: " + base);
        }
    }

    private String convertirDesdeDecimal(int decimal, String base) {
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
