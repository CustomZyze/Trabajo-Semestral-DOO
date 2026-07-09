package LogicaTorneo.Excepciones;

/**
 * Excepción que se lanza cuando se intenta registrar un empate
 * en un formato de torneo que no permite empates.
 */
public class EmpateException extends RuntimeException {

  /**
   * Usa un mensaje fijo para indicar
   * que el formato no permite empates.
   * @param message mensaje recibido al crear la excepción.
   */
  public EmpateException(String message) {
    super("Este formato no permite Empates, debe haber un Ganador");
  }
}
