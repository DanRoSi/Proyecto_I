package uptc.edu.co.test;

import java.util.Scanner;

import uptc.edu.co.model.ExpressionEvaluator;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Evaluar expresión aritmética");
            System.out.println("2. Convertir base numérica");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (option) {
                case 1:
                    System.out.print("Ingrese una expresión aritmética: ");
                    String expression = scanner.nextLine();
                    try {
                        int result = ExpressionEvaluator.evaluateExpression(expression);
                        System.out.println("Resultado: " + result);
                    } catch (Exception e) {
                        System.out.println("Error al evaluar la expresión: " + e.getMessage());
                    }
                    break;
                /**
                 * case 2:
                 * System.out.print("Ingrese el número: ");
                 * String number = scanner.nextLine();
                 * System.out.print("Ingrese la base de origen: ");
                 * int fromBase = scanner.nextInt();
                 * System.out.print("Ingrese la base de destino: ");
                 * int toBase = scanner.nextInt();
                 * scanner.nextLine(); // Consumir nueva línea
                 * 
                 * try {
                 * String converted = BaseConverter.convertBase(number, fromBase, toBase);
                 * System.out.println("Número convertido: " + converted);
                 * } catch (NumberFormatException e) {
                 * System.out.println("Error en la conversión: " + e.getMessage());
                 * }
                 * break;
                 */
                case 3:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
}
