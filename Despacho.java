import java.util.Scanner;

public class Despacho {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Datos del usuario
        System.out.print("Ingrese su nombre: ");
        String nombreUsuario = sc.nextLine();

        System.out.print("Ingrese su edad: ");
        int edadUsuario = sc.nextInt();
        sc.nextLine(); // Limpia el buffer después de nextInt

        // Datos del vehículo
        System.out.print("Ingrese la marca del vehículo: ");
        String marca = sc.nextLine();

        System.out.print("Ingrese el modelo del vehículo: ");
        String modelo = sc.nextLine();

        System.out.print("Ingrese la cilindrada del vehículo: ");
        String cilindrada = sc.nextLine();

        System.out.print("Ingrese el tipo de combustible: ");
        String combustible = sc.nextLine();

        System.out.print("Ingrese la capacidad de pasajeros: ");
        int capacidad = sc.nextInt();

        // Datos de la compra
        System.out.print("Ingrese el total de la compra: ");
        int totalCompra = sc.nextInt();

        System.out.print("Ingrese la distancia en kilómetros desde el centro a su casa: ");
        int km = sc.nextInt();

        int costoDespacho = 0;

        // Cálculo del costo de despacho
        if (totalCompra >= 50000 && km <= 20) {
            costoDespacho = 0;
        } else if (totalCompra >= 25000 && totalCompra < 50000) {
            costoDespacho = km * 150;
        } else {
            costoDespacho = km * 300;
        }

        int totalFinal = totalCompra + costoDespacho;

        // Salida de datos
        System.out.println("\n--- DATOS DEL USUARIO ---");
        System.out.println("Nombre: " + nombreUsuario);
        System.out.println("Edad: " + edadUsuario + " años");
       
        System.out.println("\n--- DATOS DEL VEHÍCULO ---");
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Cilindrada: " + cilindrada);
        System.out.println("Tipo de Combustible: " + combustible);
        System.out.println("Capacidad de Pasajeros: " + capacidad);

        System.out.println("\n--- DATOS DE LA COMPRA ---");
        System.out.println("Monto de la compra: $" + totalCompra);
        System.out.println("Distancia del despacho: " + km + " km");
        System.out.println("Costo del despacho: $" + costoDespacho);
        System.out.println("Total a pagar (compra + despacho): $" + totalFinal);
    }
}
