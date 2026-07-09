    package  LogicaTorneo;

    import LogicaTorneo.Excepciones.*;

    import java.util.ArrayList;
    import java.util.List;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;

    public class Torneo implements ObservadorPartida{
        private String nombre;
        private Disciplina disciplina;
        private List<Participante> inscritos;
        private List<Partida> llaves;
        private Formato formato;
        private LocalDate proximaFechaDisponible;
        private LocalDate fechaInicio;

        public Torneo(String nombre, Disciplina disciplina, Formato formato) {
            if (nombre == null || nombre.isBlank()) {
                throw new NoInfoException("El nombre del torneo no puede estar vacío");
            }
            this.nombre = nombre;
            this.disciplina = disciplina;
            this.formato = formato;
            this.inscritos = new ArrayList<>();
            this.llaves = new ArrayList<>();
        }

        public void inscribir(Participante p) {

            int maxParticipantes = 32;

            if(p == null ){
                throw new NoInfoException("Falta informacion");
            }

            for(Participante inscrito : inscritos ){
                if (inscrito.getNombre().equalsIgnoreCase(p.getNombre())){
                    throw new DuplicadoException(inscrito.getNombre());
                }
            }

            if (inscritos.size() >= maxParticipantes) {
                throw new CantidadInvalidaParticipantesException("El torneo tiene un maximo de " + maxParticipantes + " participantes.");
            }

            this.inscritos.add(p);
            System.out.println(p.getNombre() + " se ha inscrito en " + this.nombre);
        }

        public void generarLlaves() {
            if (inscritos.size() < 2) {
                throw new CantidadInvalidaParticipantesException("Minimo de jugadores inscritos es 2 jugadores");
            }

            if(!formato.hayEmpates()){

                boolean siPot2 = false ;

                for (int i = 2; i<=32 ; i*=2){
                   if(inscritos.size() == i ){
                        siPot2 = true;
                        break;
                   }
                }

                if(!siPot2){
                    throw new CantidadInvalidaParticipantesException("Cantidad de participantes para el formato debe ser potencia de 2");
                }
            }

            for(Participante participante : inscritos){
                if(!participante.estaListoParaJugar()){
                    throw new EquipoNoListoException(participante.getNombre());
                }
            }

            llaves = formato.generarCalendario(this.inscritos);
            for (Partida partida : llaves) {
                partida.agregarObservador(this);
            }
            System.out.println("\nLLaves generadas para el torneo: " + this.nombre);
        }

        public void mostrarLlaves() {
            System.out.println("--- Lista de Partidas ---");
            for (Partida p : llaves) {
                System.out.println(p.getP1().getNombre() + " vs " + p.getP2().getNombre() +
                        "| Fecha: " + p.getFechaFormateada()+ " | Estado: " + p.getEstado());
            }
        }

        public void programarFechas(LocalDate fechaInicio){

            LocalTime horaPrimeraPartida = LocalTime.of(17,0);
            LocalTime horaSegundaPartida = LocalTime.of(20,0);

            LocalDate fechaActual = fechaInicio;

            for(int i = 0 ; i < llaves.size() ; i++){
                Partida partida = llaves.get(i);

                if(i%2 == 0){
                    partida.setFecha(LocalDateTime.of(fechaActual, horaPrimeraPartida));
                } else {
                    partida.setFecha(LocalDateTime.of(fechaActual, horaSegundaPartida));
                    fechaActual = fechaActual.plusDays(2);
                }

            }

            if (llaves.size() % 2 != 0) {
                fechaActual = fechaActual.plusDays(2);
            }

            proximaFechaDisponible = fechaActual;

        }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public Disciplina getDisciplina() { return disciplina; }
        public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

        public List<Participante> getInscritos() { return inscritos; }
        public List<Partida> getLlaves() { return llaves; }

        public LocalDate getFechaInicio() {
            return fechaInicio;
        }
        public void setFechaInicio(LocalDate fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public List<Partida> getPartidasPendientes(){
            List<Partida> partidasPendientes = new ArrayList<>();
            for(Partida partida : llaves){
                if(partida.getEstado() == EstadoPartida.PENDIENTE){
                    partidasPendientes.add(partida);
                }
            }
            return partidasPendientes;
        }

        public Participante getCampeon(){
            return formato.obtenerCampeon(llaves);
        }

        public boolean torneoFinalizado(){
            if (llaves.isEmpty()) {
                return false;
            }

            for (Partida p : llaves) {
                if (p.getEstado() != EstadoPartida.TERMINADA) {
                    return false; // Si faltan, aun no terminamos
                }
            }
            return formato.avanzarRonda(llaves).isEmpty();
        }
        public Formato getFormato() { return formato; }
        public void setFormato(Formato formato) { this.formato = formato; }

        @Override
        public void actualizar(Partida partida){
            System.out.println("\nFinalizo el encuentro entre :");
            System.out.println( partida.getP1().getNombre()  + " vs " + partida.getP2().getNombre());

            if (partida.getGanador() != null) {
                System.out.println("Ganador: " + partida.getGanador().getNombre());
            }
            else{
                System.out.println("Empate");
            }

            formato.actualizarResultado(partida);

            if (partidasCompletas()) {
                    continuarTorneo();
            } else {
                    System.out.println("Aun faltan partidas por terminar en esta ronda.");
            }


        }

        private boolean partidasCompletas(){
            for (Partida partida: llaves){
                if (partida.getEstado() != EstadoPartida.TERMINADA){
                    return false;
                }
            }
            return true;
        }

        public void registrarResultadosPartida(Partida partida, int puntaje1,int puntaje2 ){
            if (puntaje1 == puntaje2 && !formato.hayEmpates()){
                throw new EmpateException("formato no permite empates");
            }
            if (puntaje1 < 0 || puntaje2 < 0) {
                throw new PuntajeInvalidoException("Puntaje no puede ser negativo");
            }
            partida.registrarResultado(puntaje1,puntaje2);
        }

        private void continuarTorneo(){
            List<Partida> siguienteRonda= formato.avanzarRonda(llaves);
            if(!siguienteRonda.isEmpty() ){
                llaves = siguienteRonda;

                for (Partida partida : llaves){
                    partida.agregarObservador(this);
                }

                if (proximaFechaDisponible != null) {
                    programarFechas(proximaFechaDisponible);
                }

                System.out.println("\n--- Siguiente ronda ---");
                mostrarLlaves();
            }
            else{
               Participante campeon = formato.obtenerCampeon(llaves);

               if(campeon != null){
                   System.out.println("\n --- Fin Torneo --- ");

                   System.out.println("\n Campeon:" + campeon.getNombre() );
                }
            }
        }

    }
