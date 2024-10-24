
import java.sql.*;
import java.util.Scanner;

public class EmpleadoCRUD {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conexion = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("Selecciona una opción:");
                System.out.println("1. Insertar empleado");
                System.out.println("2. Consultar empleados");
                System.out.println("3. Actualizar empleado");
                System.out.println("4. Eliminar empleado");
                System.out.println("5. Salir");
                opcion = scanner.nextInt();
                scanner.nextLine();  // Limpiar el buffer

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Cargo: ");
                        String cargo = scanner.nextLine();
                        System.out.print("Salario: ");
                        double salario = scanner.nextDouble();
                        insertarEmpleado(conexion, nombre, cargo, salario);
                        break;
                    case 2:
                        consultarEmpleados(conexion);
                        break;
                    case 3:
                        System.out.print("ID del empleado a actualizar: ");
                        int idActualizar = scanner.nextInt();
                        scanner.nextLine();  // Limpiar el buffer
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nuevo cargo: ");
                        String nuevoCargo = scanner.nextLine();
                        System.out.print("Nuevo salario: ");
                        double nuevoSalario = scanner.nextDouble();
                        actualizarEmpleado(conexion, idActualizar, nuevoNombre, nuevoCargo, nuevoSalario);
                        break;
                    case 4:
                        System.out.print("ID del empleado a eliminar: ");
                        int idEliminar = scanner.nextInt();
                        eliminarEmpleado(conexion, idEliminar);
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intenta de nuevo.");
                }
            } while (opcion != 5);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la conexión o consulta a la base de datos.");
        }
    }

    private static void insertarEmpleado(Connection conexion, String nombre, String cargo, double salario) throws SQLException {
        String sql = "INSERT INTO empleados (nombre, cargo, salario) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }

    private static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM empleados";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Cargo: %s, Salario: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"));
            }
        }
    }

    private static void actualizarEmpleado(Connection conexion, int id, String nombre, String cargo, double salario) throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, cargo = ?, salario = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    private static void eliminarEmpleado(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }
}
