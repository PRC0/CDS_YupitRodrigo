public class Main {
    public static void main(String[] args) {
        SistemaReservas s = new SistemaReservas();
        Funcion f = new Funcion("Interestelar", 3, "20:30", 12.0);

        Cliente cliente1 = new Cliente("Ana", "ana@mail.com", true);
        AsientosReserva reserva1 = new AsientosReserva(new char[] { 'F', 'F' }, new int[] { 5, 6 });
        try {
            int r1 = s.hacerReserva(cliente1, f, reserva1, "App");
            System.out.println("Reserva Ana: " + r1);
        } catch (Exception e) {
            System.out.println("Error al reservar");
        }

        Cliente cliente2 = new Cliente("Luis", "luis@mail.com", false);
        AsientosReserva reserva2 = new AsientosReserva(new char[] { 'F', 'F' }, new int[] { 7, 6 });
        try {
            int r2 = s.hacerReserva(cliente2, f, reserva2, "Taquilla");
            System.out.println("Reserva Luis: " + r2);
        } catch (Exception e) {
            System.out.println("Error al reservar");
        }

        System.out.println("Ocupados: " + f.obtenerOcupados());
        Reserva ana = (Reserva) s.buscar(1);
        System.out.println("Precio Ana (socio): " + ana.precio);

        try {
            s.cancelar(1);
            System.out.println("Cancelar 1: Reserva cancelada correctamente");
        } catch (Exception e) {
            System.out.println("Error al cancelar");
        }

        try {
            s.cancelar(99);
            System.out.println("Cancelar 99: Reserva cancelada correctamente");
        } catch (Exception e) {
            System.out.println("Error al cancelar");
        }
    }
}