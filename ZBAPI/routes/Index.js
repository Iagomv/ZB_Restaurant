//routes/index.js
const express = require('express')
const router = express.Router()

// Importamos las rutas específicas para cada recurso
const personasRoutes = require('./Personas')
const comidasRoutes = require('./Comidas.js')
const bebidasRoutes = require('./Bebidas')
const pedidosRoutes = require('./Pedidos')

// Usamos las rutas específicas para los recursos
router.use('/personas', personasRoutes)
router.use('/comidas', comidasRoutes)
router.use('/bebidas', bebidasRoutes)
router.use('/pedidos', pedidosRoutes)

// Ruta base de la API
router.get('/', (req, res) => {
	res.send('Bienvenido a la API del restaurante')
})

module.exports = router
