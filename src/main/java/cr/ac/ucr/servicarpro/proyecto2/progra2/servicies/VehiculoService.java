package cr.ac.ucr.servicarpro.proyecto2.progra2.servicies;

    import java.io.IOException;
    import java.util.List;

    import cr.ac.ucr.servicarpro.proyecto2.progra2.data.XmlRepositoryException;
    import cr.ac.ucr.servicarpro.proyecto2.progra2.data.dao.VehiculoDAO;
    import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Vehiculo;

    public class VehiculoService {
        private VehiculoDAO vehiculoDAO;

        public VehiculoService() {
            try {
                this.vehiculoDAO = new VehiculoDAO();
            } catch (Exception e) {
                throw new XmlRepositoryException("Error inicializando VehiculoDAO", e);
            }
        }

        public List<Vehiculo> listarVehiculos() {
            return vehiculoDAO.findAll();
        }

        public Vehiculo buscarPorPlaca(String numeroPlaca) {
            return vehiculoDAO.findByKey(numeroPlaca).orElse(null);
        }

        public void agregarVehiculo(Vehiculo vehiculo) throws IOException {
            try {
                validarVehiculo(vehiculo);
                validarPlacaUnica(vehiculo.getNumeroPlaca());
                vehiculoDAO.insertOrUpdate(vehiculo);
            } catch (XmlRepositoryException e) {
                throw new XmlRepositoryException("Error agregando vehículo", e);
            }
        }

        public void actualizarVehiculo(Vehiculo vehiculo) throws IOException {
            try {
                validarVehiculo(vehiculo);
                validarVehiculoExiste(vehiculo.getNumeroPlaca());
                vehiculoDAO.edit(vehiculo);
            } catch (XmlRepositoryException e) {
                throw new XmlRepositoryException("Error actualizando vehículo", e);
            }
        }

        public void borrarVehiculo(String numeroPlaca) {
            try {
                validarVehiculoExiste(numeroPlaca);
                vehiculoDAO.delete(numeroPlaca);
            } catch (Exception e) {
                throw new XmlRepositoryException("Error borrando vehículo", e);
            }
        }

        // Validaciones de negocio
        private void validarVehiculo(Vehiculo vehiculo) {
            if (vehiculo == null) {
                throw new IllegalArgumentException("El vehículo no puede ser nulo");
            }

            if (vehiculo.getNumeroPlaca() == null || vehiculo.getNumeroPlaca().trim().isEmpty()) {
                throw new IllegalArgumentException("El número de placa es obligatorio");
            }

            if (!validarFormatoPlaca(vehiculo.getNumeroPlaca())) {
                throw new IllegalArgumentException("Formato de placa inválido. Use formato ABC123 o ABC-123");
            }

            if (vehiculo.getMarca() == null || vehiculo.getMarca().trim().isEmpty()) {
                throw new IllegalArgumentException("La marca es obligatoria");
            }

            if (vehiculo.getColor() == null || vehiculo.getColor().trim().isEmpty()) {
                throw new IllegalArgumentException("El color es obligatorio");
            }

            if (vehiculo.getEstilo() == null || vehiculo.getEstilo().trim().isEmpty()) {
                throw new IllegalArgumentException("El estilo es obligatorio");
            }

            if (vehiculo.getAnio() < 1900 || vehiculo.getAnio() > java.time.LocalDate.now().getYear() + 1) {
                throw new IllegalArgumentException("Año del vehículo inválido. Debe estar entre 1900 y " + (java.time.LocalDate.now().getYear() + 1));
            }

            if (vehiculo.getVin() == null || vehiculo.getVin().trim().isEmpty()) {
                throw new IllegalArgumentException("El VIN es obligatorio");
            }

            if (!validarFormatoVin(vehiculo.getVin())) {
                throw new IllegalArgumentException("Formato de VIN inválido. Debe tener exactamente 17 caracteres alfanuméricos (sin I, O, Q)");
            }

            if (vehiculo.getCilindraje() <= 0) {
                throw new IllegalArgumentException("El cilindraje debe ser mayor a 0");
            }

            if (vehiculo.getDuenio() == null || vehiculo.getDuenio().trim().isEmpty()) {
                throw new IllegalArgumentException("El dueño es obligatorio");
            }
        }

        private boolean validarFormatoPlaca(String placa) {
            // Formato típico: ABC123 o ABC-123
            return placa.matches("^[A-Z]{3}[-]?[0-9]{3}$");
        }

        private boolean validarFormatoVin(String vin) {
            // VIN debe tener exactamente 17 caracteres alfanuméricos
            // Excluye I, O, Q para evitar confusión con 1, 0, 0
            return vin != null && vin.matches("^[A-HJ-NPR-Z0-9]{17}$");
        }

        private void validarPlacaUnica(String numeroPlaca) {
            if (vehiculoDAO.findByKey(numeroPlaca).isPresent()) {
                throw new IllegalArgumentException("Ya existe un vehículo con la placa: " + numeroPlaca);
            }
        }

        private void validarVehiculoExiste(String numeroPlaca) {
            if (vehiculoDAO.findByKey(numeroPlaca).isEmpty()) {
                throw new IllegalArgumentException("No existe un vehículo con la placa: " + numeroPlaca);
            }
        }
    }