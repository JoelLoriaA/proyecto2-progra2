/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.ucr.servicarpro.proyecto2.progra2.servicies;

/**
 *
 * @author Emanuel Araya
 */
import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;

import cr.ac.ucr.servicarpro.proyecto2.progra2.data.ClienteDAO;
import cr.ac.ucr.servicarpro.proyecto2.progra2.domain.Cliente;

public class ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteService() {
        try {
            this.clienteDAO = new ClienteDAO();
        } catch (JDOMException | IOException e) {
            throw new RuntimeException("Error inicializando ClienteDAO", e);
        }
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.findAll();
    }

    public Cliente buscarPorId(int id) {
        return clienteDAO.findByKey(id).orElse(null);
    }

    public void agregarCliente(Cliente cliente) {
        try {
            // Asigna un nuevo ID si es necesario
            if (cliente.getId() == 0) {
                cliente.setId(clienteDAO.getNextId());
            }
            clienteDAO.insertOrUpdate(cliente);
        } catch (IOException e) {
            throw new RuntimeException("Error agregando cliente", e);
        }
    }

    public void actualizarCliente(Cliente cliente) {
        try {
            clienteDAO.edit(cliente);
        } catch (IOException e) {
            throw new RuntimeException("Error actualizando cliente", e);
        }
    }

    public void borrarCliente(int id) {
        try {
            List<Cliente> clientes = clienteDAO.findAll();
            clientes.removeIf(c -> c.getId() == id);
            // Elimina todos y vuelve a guardar los restantes
            for (Cliente c : clientes) {
                clienteDAO.edit(c); // edit hace insertOrUpdate
            }
            // Elimina el cliente del XML (reescribiendo el archivo)
            // Solución simple: eliminar el archivo y volver a guardar todos
            java.io.File file = new java.io.File("xml_data/clientes.xml");
            if (file.exists()) file.delete();
            this.clienteDAO = new ClienteDAO();
            for (Cliente c : clientes) {
                clienteDAO.insertOrUpdate(c);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error borrando cliente", e);
        }
    }
}
