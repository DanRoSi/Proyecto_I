package uptc.edu.co.model;

public class ExpressionEvaluator {

    public static int evaluateExpression(String expression) {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    private static String infixToPostfix(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                output.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                stack.pop();
            } else { // operator
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    output.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }
        return output.toString();
    }

    private static int evaluatePostfix(String expression) {
        Stack<Integer> stack = new Stack<>();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else {
                if (c == '√') {
                    int val = stack.pop();
                    stack.push((int) Math.sqrt(val));
                } else {
                    int val2 = stack.pop();
                    int val1 = stack.pop();
                    switch (c) {
                        case '+':
                            stack.push(val1 + val2);
                            break;
                        case '-':
                            stack.push(val1 - val2);
                            break;
                        case '*':
                            stack.push(val1 * val2);
                            break;
                        case '/':
                            stack.push(val1 / val2);
                            break;
                        case '^':
                            stack.push((int) Math.pow(val1, val2));
                            break;
                    }
                }
            }
        }
        return stack.pop();
    }

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
}

