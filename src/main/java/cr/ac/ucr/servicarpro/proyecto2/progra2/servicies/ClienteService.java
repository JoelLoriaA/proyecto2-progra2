package cr.ac.ucr.servicarpro.proyecto2.progra2.servicies;

import java.io.IOException;
import java.util.List;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.data.XmlRepositoryException;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;

public class ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteService() {
        try {
            this.clienteDAO = new ClienteDAO();
        } catch (Exception e) {
            throw new XmlRepositoryException("Error inicializando ClienteDAO", e);
        }
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.findAll();
    }

    public Cliente buscarPorId(int id) {
        return clienteDAO.findByKey(id).orElse(null);
    }

    public void agregarCliente(Cliente cliente) throws IOException {
        try {
            if (cliente.getId() == 0) {
                cliente.setId(clienteDAO.getNextId());
            }
            clienteDAO.insertOrUpdate(cliente);
        } catch (XmlRepositoryException e) {
            throw new XmlRepositoryException("Error agregando cliente", e);
        }
    }

    public void actualizarCliente(Cliente cliente) throws IOException {
        try {
            clienteDAO.edit(cliente);
        } catch (XmlRepositoryException e) {
            throw new XmlRepositoryException("Error actualizando cliente", e);
        }
    }

    public void borrarCliente(int id) {
        try {
            List<Cliente> clientes = clienteDAO.findAll();
            clientes.removeIf(c -> c.getId() == id);
            java.io.File file = new java.io.File("xml_data/clientes.xml");
            if (file.exists()) file.delete();
            this.clienteDAO = new ClienteDAO();
            for (Cliente c : clientes) {
                clienteDAO.insertOrUpdate(c);
            }
        } catch (Exception e) {
            throw new XmlRepositoryException("Error borrando cliente", e);
        }
    }
}