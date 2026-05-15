
/*Dannia María Pérez Rivera
 * Grupo 1MS-2
 */
import java.util.Scanner;
import java.io.IOException;
import static java.lang.Math.*;

public class EquipoBaloncesto {
    static Scanner lector = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        // Iniciar el menú principal
        mostrarMenu();
    }

    static void mostrarMenu() throws IOException, InterruptedException {
        // Datos de los jugadores
        int[] Estatura = { 185, 192, 188, 196, 205 }, Edad = { 21, 25, 28, 26, 31 },
                Salto = { 102, 110, 100, 98, 40 }, Peso = { 86, 90, 85, 100, 120 },
                Envergadura = { 192, 192, 195, 210, 211 };
        double[] Velocidad_max = { 31, 28, 30, 33, 15 };
        String[] Nombre = { "Josu\u00E9 Mojica", "Hollman Vargas", "Fernando Mendoza", "Juan Mart\u00EDnez",
                "Neffer Cano" };

        boolean salir = false;
        do {
            // Limpiar la pantalla
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            // Mostrar opciones del menú
            System.out.println("\t\tEQUIPO DE BALONCESTO UNI \n");
            System.out.println("\t\tElija una opci\u00F3n:" + "\n");
            System.out.println(
                    "\t1. Ver listado de jugadores (ordenados del m\u00E1s r\u00E1pido al m\u00E1s lento)\n\t2. Ver promedio de la l\u00EDnea titular"
                            + "\n\t3. Calcular el jugador m\u00E1s completo\n\t4. Ver jugadores capacitados para clavar el bal\u00F3n\n"
                            + " \t5. Listado de jugadores por IMC (de menor a mayor)\n\t6. Salir");
            System.out.print("\n");
            System.out.print("\t---> ");

            // Validar entrada del usuario
            while (!lector.hasNextInt()) {
                System.out.println("\tPor favor, ingrese un n\u00FAmero v\u00E1lido.");
                System.out.print("\tSeleccione una opci\u00F3n: ");
                lector.next(); // Limpiar el búfer de entrada
            }

            int opcion = lector.nextInt();
            switch (opcion) {
                case 1:
                    ordenarJugadoresPorVelocidad(Nombre, Estatura, Edad, Velocidad_max, Salto, Peso, Envergadura);
                    MostrarJugadores(Nombre, Estatura, Edad, Velocidad_max, Salto, Peso, Envergadura);
                    break;

                case 2:
                    double AlturaPromedio = calcularAlturaPromedio(Estatura);
                    System.out.println("\tLa altura promedio de la l\u00EDnea titular es: " + "\n\t" + "--->  "
                            + AlturaPromedio + " cm");
                    break;

                case 3:
                    String jugadorMasCompleto = CalcularJugadorMasCompleto(Nombre, Estatura, Edad, Velocidad_max, Salto,
                            Peso, Envergadura);
                    System.out.println("\t El jugador m\u00E1s completo es: " + jugadorMasCompleto + "\n"
                            + "\n\t--------------------------");
                    mostrarEstadisticasJugador(Nombre, Estatura, Edad, Velocidad_max, Salto, Peso, Envergadura,
                            jugadorMasCompleto);
                    System.out.println("\t--------------------------");
                    break;

                case 4:
                    MostrarJugadoresCapacitadosParaClavar(Nombre, Estatura, Edad, Velocidad_max, Salto, Peso,
                            Envergadura);
                    break;

                case 5:
                    OrdenarPorIMC(Nombre, Estatura, Peso);
                    MostrarJugadoresPorIMC(Nombre, Estatura, Edad, Velocidad_max, Salto, Peso, Envergadura);
                    break;

                case 6:
                    System.out.println("\tSaliendo...");
                    return;

                default:
                    System.out.println("\tOpci\u00F3n inv\u00E1lida. Por favor, elija una opci\u00F3n v\u00E1lida.");
                    break;
            }
            Thread.sleep(9000);
        } while (!salir);

    }

    // Metodo para limpiar la pantalla
    public static void limpiarpantalla() {
        // Limpia la pantalla
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println("Error al limpiar la pantalla: " + ex.getMessage());
        }
    }

    // Metodo para mostrar el listado de jugadores
    static void MostrarJugadores(String[] Nombre, int[] Estatura, int[] Edad, double[] Velocidad_max, int[] Salto,
            int[] Peso, int[] Envergadura) {
        limpiarpantalla();
        System.out.println("\tListado de jugadores:");
        System.out.println(
                "\t---------------------------------------------------------------------------------------------------------"
                        + "\n\t| No. |     Nombre del\t   |   Estatura    | Edad |  Velocidad M\u00E1x. | Alcance en |  Peso |  Envergadura |"
                        + "\n\t|     |      Jugador \t   |     (cm)      |      |     (km/h)      | Salto (cm) |  (kg) |     (cm)     |"
                        + "\n\t---------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < Nombre.length; i++) {
            System.out.printf("\t| %-3d | %-18s | %-13d | %-4d | %-15.1f | %-10d | %-5d | %-12d |\n",
                    (i + 1), Nombre[i], Estatura[i], Edad[i], Velocidad_max[i], Salto[i], Peso[i], Envergadura[i]);
        }
        System.out.println(
                "\t---------------------------------------------------------------------------------------------------------");
    }

    // Metodo para ordenar los jugadores por velocidad
    static void ordenarJugadoresPorVelocidad(String[] Nombre, int[] Estatura, int[] Edad, double[] Velocidad_max,
            int[] Salto,
            int[] Peso, int[] Envergadura) {
        int n = Velocidad_max.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (Velocidad_max[j] < Velocidad_max[j + 1]) {
                    double tVelocidad = Velocidad_max[j];
                    String tNombre = Nombre[j];
                    int tEstatura = Estatura[j], tEdad = Edad[j], tSalto = Salto[j], tempPeso = Peso[j],
                            tEnvergadura = Envergadura[j];

                    Velocidad_max[j] = Velocidad_max[j + 1];
                    Velocidad_max[j + 1] = tVelocidad;

                    Nombre[j] = Nombre[j + 1];
                    Nombre[j + 1] = tNombre;

                    Estatura[j] = Estatura[j + 1];
                    Estatura[j + 1] = tEstatura;

                    Edad[j] = Edad[j + 1];
                    Edad[j + 1] = tEdad;

                    Salto[j] = Salto[j + 1];
                    Salto[j + 1] = tSalto;

                    Peso[j] = Peso[j + 1];
                    Peso[j + 1] = tempPeso;

                    Envergadura[j] = Envergadura[j + 1];
                    Envergadura[j + 1] = tEnvergadura;
                }
            }
        }
    }

    // Metodo paar calcular la altura promedio de la línea titular
    static double calcularAlturaPromedio(int[] Estatura) {
        limpiarpantalla();
        int sumaEstatura = 0, nTitular = 5; // nTitutlar es el Número de jugadores en la línea titular
        for (int i = 0; i < nTitular; i++) {
            sumaEstatura += Estatura[i];
        }
        double promedio = (double) sumaEstatura / nTitular;
        return promedio;
    }

    // Metodo para encontrar el jugador mas completo
    static String CalcularJugadorMasCompleto(String[] Nombre, int[] Estatura, int[] Edad, double[] Velocidad_max,
            int[] Salto, int[] Peso, int[] Envergadura) {
        limpiarpantalla();
        double Puntuacion_max = 0;
        String jugadorMasCompleto = "";
        for (int i = 0; i < Nombre.length; i++) {
            double Puntuacion = (0.25 * Estatura[i]) + (0.25 * Velocidad_max[i]) + (0.20 * Salto[i]) + (0.10 * Peso[i])
                    + (0.20 * Envergadura[i]);
            if (Puntuacion_max < Puntuacion) {
                Puntuacion_max = Puntuacion;
                jugadorMasCompleto = Nombre[i];
            }
        }
        return jugadorMasCompleto;
    }

    // Metodo para mostrar las estadisticas de los jugadores
    static void mostrarEstadisticasJugador(String[] Nombre, int[] Estatura, int[] Edad, double[] Velocidad_max,
            int[] Salto, int[] Peso, int[] Envergadura, String jugador) {
        for (int i = 0; i < Nombre.length; i++) {
            if (Nombre[i].equals(jugador)) {
                System.out.println("\tNombre: " + Nombre[i] + "\n\tEstatura: " + Estatura[i] + " cm" + "\n\tEdad: "
                        + Edad[i] + "\n\tVelocidad: " + Velocidad_max[i] + " km/h" + "\n\tSalto: " + Salto[i] + " cm"
                        + "\n\tPeso: " + Peso[i] + " kg" + "\n\tEnvergadura: " + Envergadura[i] + " cm");
                break;
            }
        }
    }

    // Metodo para encontrar los jugadores aptos para clavar el balón
    static void MostrarJugadoresCapacitadosParaClavar(String[] Nombre, int[] Estatura, int[] Edad,
            double[] Velocidad_max, int[] Salto, int[] Peso, int[] Envergadura) {
        limpiarpantalla();

        System.out.println("\tJugadores capacitados para clavar el bal\u00F3n:");
        System.out.println(
                "\t---------------------------------------------------------------------------------------------------------------------"
                        + "\n\t| No. |     Nombre del\t   |   Estatura    | Edad |  Velocidad M\u00E1x. | Alcance en |  Peso |  Envergadura | Apto para |"
                        + "\n\t|     |      Jugador \t   |     (cm)      |      |     (km/h)      | Salto (cm) |  (kg) |     (cm)     |  clavar   |"
                        + "\n\t---------------------------------------------------------------------------------------------------------------------");
        boolean[] AptoParaClavar = new boolean[Nombre.length];
        for (int i = 0; i < Nombre.length; i++) {
            double alcanceTotal = Estatura[i] + (Envergadura[i] / 2 * 0.7) + Salto[i];
            if (alcanceTotal >= 305) {
                AptoParaClavar[i] = true;
            } else {
                AptoParaClavar[i] = false;
            }
        }
        for (int i = 0; i < Nombre.length; i++) {
            String apto = AptoParaClavar[i] ? "Sí" : "No";
            System.out.printf("\t| %-3d | %-18s | %-13d | %-4d | %-15.1f | %-10d | %-5d | %-12d | %-9s |\n",
                    (i + 1), Nombre[i], Estatura[i], Edad[i], Velocidad_max[i], Salto[i], Peso[i], Envergadura[i],
                    apto);
        }
        System.out.println(
                "\t---------------------------------------------------------------------------------------------------------------------");
    }

    // Metodo para ordenar los jugadores por su indice de masa corporal(IMC)
    static void OrdenarPorIMC(String[] Nombre, int[] Estatura, int[] Peso) {
        int n = Nombre.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String tNombre = Nombre[j];
                int tEstatura = Estatura[j], tPeso = Peso[j];

                Nombre[j] = Nombre[j + 1];
                Nombre[j + 1] = tNombre;

                Estatura[j] = Estatura[j + 1];
                Estatura[j + 1] = tEstatura;

                Peso[j] = Peso[j + 1];
                Peso[j + 1] = tPeso;
            }
        }
    }

    // Metodo para mostrar el orden de los jugadores por su indice de masa
    // corporal(IMC)
    static void MostrarJugadoresPorIMC(String[] Nombre, int[] Estatura, int[] Edad, double[] Velocidad_max,
            int[] Salto, int[] Peso, int[] Envergadura) {
        limpiarpantalla();
        System.out.println("\tJugadores ordenados por IMC:");
        System.out.println(
                "\t----------------------------------------------------------------------------------------------------------------------"
                        + "\n\t| No. |     Nombre del\t   |   Estatura    | Edad |  Velocidad M\u00E1x. | Alcance en |  Peso |  Envergadura |    IMC     |"
                        + "\n\t|     |      Jugador \t   |     (cm)      |      |     (km/h)      | Salto (cm) |  (kg) |     (cm)     |            |"
                        + "\n\t----------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < Nombre.length; i++) {
            double IMC = Peso[i] / pow(Estatura[i] / 100.0, 2);
            System.out.printf("\t| %-3d | %-18s | %-13d | %-4d | %-15.1f | %-10d | %-5d | %-12d | %-10f |\n",
                    (i + 1), Nombre[i], Estatura[i], Edad[i], Velocidad_max[i], Salto[i], Peso[i], Envergadura[i], IMC);
        }
        System.out.println(
                "\t----------------------------------------------------------------------------------------------------------------------");
    }
}