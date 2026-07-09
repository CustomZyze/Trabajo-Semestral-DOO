    package  LogicaTorneo;

    import LogicaTorneo.Excepciones.*;

    import java.util.ArrayList;
    import java.util.List;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;

    /**
     * Representa un torneo dentro del sistema.
     * Un torneo tiene un nombre, una disciplina, una lista de participantes inscritos,
     * una lista de partidas o llaves, y un formato de competencia.
     * Esta clase implementa ObservadorPartida, por lo que puede reaccionar
     * automaticamente cuando una partida termina. Gracias a eso, el torneo puede
     * actualizar resultados, revisar si todas las partidas terminaron y avanzar
     * de ronda si corresponde.
     */
    public class Torneo implements ObservadorPartida{
        private String nombre;
        private Disciplina disciplina;
        private List<Participante> inscritos;
        private List<Partida> llaves;
        private Formato formato;
        private LocalDate proximaFechaDisponible;
        private LocalDate fechaInicio;

        /**
         * Constructor de la clase Torneo.
         * Crea un torneo con nombre, disciplina y formato. Además, inicializa
         * las listas de inscritos y llaves.
         * @param nombre nombre del torneo.
         * @param disciplina disciplina asociada al torneo.
         * @param formato formato de competencia del torneo.
         * @throws NoInfoException si el nombre del torneo está vacío o es nulo.
         */
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

        /**
         * Inscribe un participante en el torneo.
         * Valida que el participante no sea nulo, que no exista otro participante
         * con el mismo nombre y que el torneo no supere el maximo permitido de
         * participantes.
         * @param p participante que se desea inscribir.
         * @throws NoInfoException si el participante es nulo.
         * @throws DuplicadoException si ya existe un participante con el mismo nombre.
         * @throws CantidadInvalidaParticipantesException si se supera el máximo de participantes.
         */
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

        /**
         * Genera las llaves o partidas iniciales del torneo.
         * Luego genera el calendario usando el formato del torneo y agrega al torneo
         * como observador de cada partida.
         * @throws CantidadInvalidaParticipantesException si no se cumple la cantidad requerida.
         * @throws EquipoNoListoException si algún participante no está listo para jugar.
         */
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

        /**
         * Muestra por consola las partidas actuales del torneo.
         * Imprime el nombre de ambos participantes, la fecha formateada
         * y el estado actual de cada partida.
         */
        public void mostrarLlaves() {
            System.out.println("--- Lista de Partidas ---");
            for (Partida p : llaves) {
                System.out.println(p.getP1().getNombre() + " vs " + p.getP2().getNombre() +
                        "| Fecha: " + p.getFechaFormateada()+ " | Estado: " + p.getEstado());
            }
        }

        /**
         * Programa fechas para las partidas actuales del torneo.
         * Asigna como maximo dos partidas por día:
         * Luego avanza dos días para seguir programando las siguientes partidas.
         * Al finalizar, guarda la próxima fecha disponible para usarla si el torneo
         * avanza a una nueva ronda.
         * @param fechaInicio fecha desde la cual se comenzarán a programar las partidas.
         */
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

        /**
         * Obtiene el nombre del torneo.
         * @return nombre del torneo.
         */
        public String getNombre() { return nombre; }

        /**
         * Modifica el nombre del torneo.
         * @param nombre nuevo nombre del torneo.
         */
        public void setNombre(String nombre) { this.nombre = nombre; }


        /**
         * Obtiene la disciplina del torneo.
         * @return disciplina del torneo.
         */
        public Disciplina getDisciplina() { return disciplina; }

        /**
         * Modifica la disciplina del torneo.
         * @param disciplina nueva disciplina del torneo.
         */
        public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }


        /**
         * Obtiene la lista de participantes inscritos.
         * @return lista de participantes inscritos.
         */
        public List<Participante> getInscritos() { return inscritos; }

        /**
         * Obtiene las partidas actuales del torneo.
         * @return lista de partidas actuales.
         */
        public List<Partida> getLlaves() { return llaves; }


        /**
         * Obtiene la fecha de inicio del torneo.
         * @return fecha de inicio.
         */
        public LocalDate getFechaInicio() {
            return fechaInicio;
        }

        /**
         * Modifica la fecha de inicio del torneo.
         * @param fechaInicio nueva fecha de inicio.
         */
        public void setFechaInicio(LocalDate fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        /**
         * Obtiene las partidas que todavía están pendientes.
         * @return lista de partidas pendientes.
         */
        public List<Partida> getPartidasPendientes(){
            List<Partida> partidasPendientes = new ArrayList<>();
            for(Partida partida : llaves){
                if(partida.getEstado() == EstadoPartida.PENDIENTE){
                    partidasPendientes.add(partida);
                }
            }
            return partidasPendientes;
        }

        /**
         * Obtiene el campeon del torneo segun el formato utilizado.
         * @return participante campeón, o null si aún no existe campeón.
         */
        public Participante getCampeon(){
            return formato.obtenerCampeon(llaves);
        }

        /**
         * Indica si el torneo ya finalizó.
         * @return true si el torneo terminó, false en caso contrario.
         */
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

        /**
         * Obtiene el formato del torneo.
         * @return formato del torneo.
         */
        public Formato getFormato() { return formato; }

        /**
         * Modifica el formato del torneo.
         * @param formato nuevo formato del torneo.
         */
        public void setFormato(Formato formato) { this.formato = formato; }


        /**
         * Metodo al cual se llama automaticamente cuando una partida registra su resultado.
         * Imprime informacion del encuentro, actualiza el resultado en el formato
         * correspondiente y revisa si todas las partidas de la ronda están completas.
         * @param partida partida que notificó el evento.
         */
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

        /**
         * Verifica si todas las partidas actuales están terminadas.
         * @return true si todas las partidas están TERMINADA, false si falta alguna.
         */
        private boolean partidasCompletas(){
            for (Partida partida: llaves){
                if (partida.getEstado() != EstadoPartida.TERMINADA){
                    return false;
                }
            }
            return true;
        }

        /**
         * Registra el resultado de una partida.
         * Luego delega el registro del resultado a la clase Partida.
         * @param partida partida a la que se le registrará el resultado.
         * @param puntaje1 puntaje del primer participante.
         * @param puntaje2 puntaje del segundo participante.
         * @throws EmpateException si hay empate en un formato que no lo permite.
         * @throws PuntajeInvalidoException si alguno de los puntajes es negativo.
         */
        public void registrarResultadosPartida(Partida partida, int puntaje1,int puntaje2 ){
            if (puntaje1 == puntaje2 && !formato.hayEmpates()){
                throw new EmpateException("formato no permite empates");
            }
            if (puntaje1 < 0 || puntaje2 < 0) {
                throw new PuntajeInvalidoException("Puntaje no puede ser negativo");
            }
            partida.registrarResultado(puntaje1,puntaje2);
        }

        /**
         * Continúa el torneo.
         * Solicita al formato generar la siguiente ronda usando las llaves actuales.
         * Si existe una nueva ronda, reemplaza las llaves actuales, agrega nuevamente
         * al torneo como observador de las nuevas partidas y programa nuevas fechas
         * si existe una próxima fecha disponible.
         * Si no hay siguiente ronda, intenta obtener el campeón y mostrar el fin
         * del torneo por consola.
         */
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
