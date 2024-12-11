const express = require('express')
const router = express.Router()
const pedidosController = require('../controller/pedidosController') // Importamos el controlador

// Ruta para obtener todos los pedidos
router.get('/', pedidosController.obtenerPedidos)

// Ruta para obtener un pedido por ID
router.get('/:id', pedidosController.obtenerPedidoPorId)

// Ruta para crear un nuevo pedido
router.post('/', pedidosController.insertarPedido)

// Ruta para actualizar el estado de un pedido por su ID
router.put('/:id', pedidosController.actualizarEstadoPedido)

module.exports = router // Exportamos las rutas para que se usen en el archivo principal
