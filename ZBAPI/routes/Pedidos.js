const express = require('express')
const router = express.Router()
const pedidosController = require('../controller/pedidosController') // Importamos el controlador

// Definimos las rutas para las personas
// Ruta para obtener todas las personas
router.get('/', pedidosController.obtenerPedidos)

// Ruta para crear una nueva persona
router.post('/', pedidosController.insertarPedido)

// Ruta para actualizar una persona existente por su ID
router.put('/:id', pedidosController.actualizarEstado)

// Ruta para eliminar una persona por su ID
router.delete('/:id', pedidosController.eliminarPedido)

module.exports = router // Exportamos las rutas para que se usen en el archivo principal
