import java.util.ArrayList;
import java.util.List;

public class Funcion {
    public String titulo;
    public int sala;
    public String hora;
    public double precio;
    // TECNICA UTILIZADA: HIDE METHOD.
    // REFACTOR: Se cambio la visibilidad de atributo acupados de public a private
    // ahora se deben modificar o revisar con los metodos proporcionados por
    // Funcion.
    private List<String> ocupados = new ArrayList<>();

    public void liberar(String codigo) {
        ocupados.remove(codigo);
    }

    public List<String> obtenerOcupados() {
        return new ArrayList<>(ocupados);
    }

    public Funcion(String titulo, int sala, String hora, double precio) {
        this.titulo = titulo;
        this.sala = sala;
        this.hora = hora;
        this.precio = precio;
    }

    // TECNICA UTILIZADA: SEPARATE QUERY FROM MODIFIER
    // REFACTOR: Se separo la consulta de la modificacion de la lista de asientos.
    public boolean estaOcupado(char fila, int numero) {
        String codigo = "" + fila + numero;
        return ocupados.contains(codigo);
    }

    public void ocupar(char fila, int numero) {
        String codigo = "" + fila + numero;
        if (estaOcupado(fila, numero)) {
            throw new IllegalStateException("Asiento ocupado: " + codigo);
        }
        ocupados.add(codigo);
    }

    // TECNICA UTILIZADA: REMOVE SETTING METHOD
    // REFACTOR: Se elimino el setter para mejorar la seguridad.
}
