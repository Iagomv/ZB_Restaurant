const express = require('express')
const router = express.Router()
const bebidasController = require('../controller/bebidasController') // Importamos el controlador

// Definimos las rutas para las personas
// Ruta para obtener todas las personas
router.get('/', bebidasController.obtenerBebidas)

// Ruta para crear una nueva persona
router.post('/', bebidasController.crearBebida)

// Ruta para actualizar una persona existente por su ID
router.put('/:id', bebidasController.actualizarBebida)

// Ruta para eliminar una persona por su ID
router.delete('/:id', bebidasController.eliminarBebida)

module.exports = router // Exportamos las rutas para que se usen en el archivo principal
