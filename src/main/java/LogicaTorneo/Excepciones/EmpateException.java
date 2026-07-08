package LogicaTorneo.Excepciones;

public class EmpateException extends RuntimeException {
  public EmpateException(String message) {
    super("Este formato no permite Empates, debe haber un Ganador");
  }
}
