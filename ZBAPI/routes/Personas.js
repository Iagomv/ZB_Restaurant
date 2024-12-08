const express = require('express')
const router = express.Router()
const personasController = require('../controller/personasController') // Importamos el controlador

// Definimos las rutas para las personas
// Ruta para obtener todas las personas
router.get('/', personasController.obtenerPersonas)

// Ruta para crear una nueva persona
router.post('/', personasController.crearPersona)

// Ruta para actualizar una persona existente por su ID
router.put('/:id', personasController.actualizarPersona)

// Ruta para eliminar una persona por su ID
router.delete('/:id', personasController.eliminarPersona)

module.exports = router // Exportamos las rutas para que se usen en el archivo principal
