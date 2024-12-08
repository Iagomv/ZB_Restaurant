const db = require('../db') // Importar la conexión a la base de datos

// Obtener todas las personas
const obtenerPersonas = (req, res) => {
	const query = 'SELECT * FROM Zero_Bugs_Personal' // Consulta SQL para obtener todas las personas
	db.query(query, (err, results) => {
		if (err) {
			return res.status(500).json({ error: 'Error en la base de datos' })
		}
		res.status(200).json(results) // Retorna todas las personas en formato JSON
	})
}

// Crear una nueva persona
const crearPersona = (req, res) => {
	const { nombre, apellidos, dni, edad, fecha_inicio, fecha_fin_contrato, horas_semanales, rol, imagen } = req.body

	// Validación de los datos de entrada
	if (
		!nombre ||
		!apellidos ||
		!dni ||
		!edad ||
		!rol ||
		!fecha_inicio ||
		!fecha_fin_contrato ||
		!horas_semanales ||
		!imagen
	) {
		return res.status(400).json({ error: 'Faltan datos requeridos' })
	}

	const consulta =
		'INSERT INTO Zero_Bugs_Personal (nombre, apellidos, dni, edad, fecha_inicio, fecha_fin_contrato, horas_semanales, rol, imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)'
	const values = [nombre, apellidos, dni, edad, fecha_inicio, fecha_fin_contrato, horas_semanales, rol, imagen]

	db.query(consulta, values, (err, result) => {
		if (err) {
			return res.status(500).json({ error: 'Error al crear la persona' })
		}
		res.status(201).json({ id: result.insertId, nombre, apellidos, dni, edad, rol })
	})
}

// Actualizar una persona existente
const actualizarPersona = (req, res) => {
	const { id } = req.params // Obtenemos el ID de la persona de los parámetros de la URL
	const { nombre, apellidos, dni, edad, fecha_inicio, fecha_fin_contrato, horas_semanales, rol, imagen } = req.body

	// Validación de los datos de entrada
	if (
		!nombre ||
		!apellidos ||
		!dni ||
		!edad ||
		!rol ||
		!fecha_inicio ||
		!fecha_fin_contrato ||
		!horas_semanales ||
		!imagen
	) {
		return res.status(400).json({ error: 'Faltan datos requeridos' })
	}

	const consulta =
		'UPDATE Zero_Bugs_Personal SET nombre = ?, apellidos = ?, dni = ?, edad = ?, fecha_inicio = ?, fecha_fin_contrato = ?, horas_semanales = ?, rol = ?, imagen = ? WHERE id = ?'

	// Los valores a actualizar, incluyendo el id al final
	const values = [nombre, apellidos, dni, edad, fecha_inicio, fecha_fin_contrato, horas_semanales, rol, imagen, id]

	db.query(consulta, values, (err, result) => {
		if (err) {
			return res.status(500).json({ error: 'Error al actualizar la persona' })
		}

		if (result.affectedRows === 0) {
			return res.status(404).json({ error: 'Persona no encontrada' })
		}

		// Respuesta exitosa con los datos actualizados
		res.status(200).json({
			id,
			nombre,
			apellidos,
			dni,
			edad,
			fecha_inicio,
			fecha_fin_contrato,
			horas_semanales,
			rol,
			imagen,
		})
	})
}

// Eliminar una persona
const eliminarPersona = (req, res) => {
	const { id } = req.params

	const query = 'DELETE FROM Zero_Bugs_Personal WHERE id = ?'
	db.query(query, [id], (err, result) => {
		if (err) {
			return res.status(500).json({ error: 'Error al eliminar la persona' })
		}

		if (result.affectedRows === 0) {
			return res.status(404).json({ error: 'Persona no encontrada' })
		}
	})
}

module.exports = {
	obtenerPersonas,
	crearPersona,
	actualizarPersona,
	eliminarPersona,
}
