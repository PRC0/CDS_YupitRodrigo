import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class SistemaReservas {
    public Map<Integer, Object> db = new HashMap<>();
    public int contador = 1;

    // TECNICA UTILIZADA: RENAME METHOD. REMOVE PARAMETER. ADD PARAMETER. PRESERVE
    // WHOLE OBJECT. INTRODUCE PARAMETER OBJECT. REPLACE ERROR CODE WITH EXCEPTION.
    // REPLACE EXCEPTION WITH TEST.
    // REFACTOR: Se cambio hacer a hacerReserva. Se elimino el parametro imprimir.
    // Se le dio un uso al parametro canal. El parametro cliente se pasa completo.
    // Se creo la clase SistemaReservas. Se reemplazan los codigos de error
    // numericos.
    public int hacerReserva(Cliente cliente, Funcion f,
            AsientosReserva reserva, String canal) {
        if (cliente.nombre == null || f == null)
            throw new IllegalArgumentException("Cliente o funcion invalidos");
        if (reserva.filas.length == 0 || reserva.filas.length != reserva.numeros.length)
            throw new IllegalArgumentException("Asientos invalida");
        List<String> lista = new ArrayList<>();
        try {
            for (int i = 0; i < reserva.filas.length; i++) {
                if (f.estaOcupado(reserva.filas[i], reserva.numeros[i])) {
                    for (String asiento : lista) {
                        f.liberar(asiento);
                    }
                    throw new IllegalStateException("Asiento ocupado");
                }
                f.ocupar(reserva.filas[i], reserva.numeros[i]);
                lista.add("" + reserva.filas[i] + reserva.numeros[i]);
            }
            Cliente c = new Cliente(cliente.nombre, cliente.email, cliente.socio);
            Reserva r = new Reserva(contador, c, f, lista, canal);
            if (r.cliente.socio) {
                r.precio = calcularPrecioSocio(r);
            } else {
                r.precio = calcularPrecioGeneral(r);
            }
            db.put(contador, r);
            contador++;
            return r.id;
        } catch (IllegalStateException e) {
            for (String asiento : lista) {
                f.liberar(asiento);
            }
            throw new IllegalStateException("Asiento ocupado");
        }
    }

    // TECNICA UTILIZADA: REPLACE PARAMETER WITH METHOD. REPLACE PARAMETER WITH
    // EXPLICIT METHODS.
    // REFACTOR: Se elimino el tipo de parametro ya que cliente puede
    // hacerse cargo. Se reemplazo el metodo calcularPrecio() por dos metodos
    // explicitos.
    public double calcularPrecioGeneral(Reserva r) {
        return r.funcion.precio * r.asientos.size();
    }

    public double calcularPrecioSocio(Reserva r) {
        double base = r.funcion.precio * r.asientos.size();
        return aplicarDescuento(base, 0.15);
    }

    // TECNICA UTILIZADA: PARAMETERIZE METHOD.
    // REFACTOR: Se unificaron los dos metodos.
    public double aplicarDescuento(double precio, double porcentaje) {
        return precio - precio * porcentaje;
    }

    // TECNICA UTILIZADA: ENCAPSULATE DOWNCAST
    // REFACTOR: Se encapsulo el casteo en buscar.
    public void cancelar(int id) {
        Reserva r = buscar(id);
        if (r == null)
            throw new NoSuchElementException("No existe una reserva");
        if (r.cancelada)
            throw new IllegalStateException("La reserva ya fue cancelada");
        for (String a : r.asientos) {
            r.funcion.liberar(a);
        }
        r.cancelar();
    }

    public Reserva buscar(int id) {
        return (Reserva) db.get(id);
    }
}
