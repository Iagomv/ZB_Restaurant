const db = require('../db') // Importar la conexión a la base de datos

// Validación de los datos de entrada
const validacionDatosEntrada = (req, res, data) => {
	const { nombre, tipo, precio, stock, imagen } = data
	if (!nombre || !tipo || !precio || !stock || !imagen) {
		return res.status(400).json({ error: 'Faltan datos requeridos' })
	}
}

// Obtener todas las Bebidas
const obtenerBebidas = (req, res) => {
	const query = 'SELECT * FROM Zero_Bugs_Bebidas' // Consulta SQL para obtener todas las Bebidas
	db.query(query, (err, results) => {
		if (err) {
			return res.status(500).json({ error: 'Error en la base de datos' })
		}
		res.status(200).json(results) // Retorna todas las Bebidas en formato JSON
	})
}

// Crear una nueva Bebida
const crearBebida = (req, res) => {
	const { nombre, tipo, precio, stock, imagen } = req.body

	// Validación de los datos de entrada
	validacionDatosEntrada(req, res, req.body)

	const consulta = 'INSERT INTO Zero_Bugs_Bebidas (nombre, tipo, precio, stock, imagen) VALUES (?, ?, ?, ?, ?)'
	const values = [nombre, tipo, precio, stock, imagen]

	db.query(consulta, values, (err, result) => {
		if (err) {
			return res.status(500).json({ error: 'Error al crear la Bebida' })
		}
		res.status(201).json({ id: result.insertId, nombre, tipo, precio, stock, imagen })
	})
}

// Actualizar una Bebida existente
const actualizarBebida = (req, res) => {
	const { id } = req.params // Obtenemos el ID de la Bebida de los parámetros de la URL
	const { nombre, tipo, precio, stock, imagen } = req.body

	// Validación de los datos de entrada
	validacionDatosEntrada(req, res, req.body)

	const consulta = 'UPDATE Zero_Bugs_Bebidas SET nombre = ?, tipo = ?, precio = ?, stock = ?, imagen = ? WHERE id = ?'

	// Los valores a actualizar, incluyendo el id al final
	const values = [nombre, tipo, precio, stock, imagen, id]

	db.query(consulta, values, (err, result) => {
		if (err) {
			return res.status(500).json({ error: 'Error al actualizar la Bebida' })
		}

		if (result.affectedRows === 0) {
			return res.status(404).json({ error: 'Bebida no encontrada' })
		}

		// Respuesta exitosa con los datos actualizados
		res.status(200).json({ id, nombre, tipo, precio, stock, imagen })
	})
}

// Eliminar una Bebida
const eliminarBebida = (req, res) => {
	const { id } = req.params

	const query = 'DELETE FROM Zero_Bugs_Bebidas WHERE id = ?'
	db.query(query, [id], (err, result) => {
		if (err) {
			return res.status(500).json({ error: 'Error al eliminar la Bebida' })
		}

		if (result.affectedRows === 0) {
			return res.status(404).json({ error: 'Bebida no encontrada' })
		}

		res.status(200).json({ message: 'Bebida eliminada correctamente' })
	})
}

module.exports = {
	obtenerBebidas,
	crearBebida,
	actualizarBebida,
	eliminarBebida,
}
