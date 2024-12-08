const express = require('express')
const router = express.Router()
const comidasController = require('../controller/comidasController') // Importamos el controlador

// Definimos las rutas para las personas
// Ruta para obtener todas las personas
router.get('/', comidasController.obtenerComidas)

// Ruta para crear una nueva persona
router.post('/', comidasController.crearComida)

// Ruta para actualizar una persona existente por su ID
router.put('/:id', comidasController.actualizarComida)

// Ruta para eliminar una persona por su ID
router.delete('/:id', comidasController.eliminarComida)

module.exports = router // Exportamos las rutas para que se usen en el archivo principal
