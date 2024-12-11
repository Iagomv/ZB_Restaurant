const db = require('../Connection/db') // Importar la conexión a la base de datos

// Validación de los datos de entrada
const validacionDatosEntrada = (req, res, data) => {
  const {nombre, tipo, precio, stock, imagen} = data
  if (!nombre || !tipo || !precio || !stock || !imagen) {
    return res.status(400).json({error: 'Faltan datos requeridos'})
  }
}

// Obtener todas las comidas
const obtenerComidas = (req, res) => {
  const query = 'SELECT * FROM Zero_Bugs_Comidas' // Consulta SQL para obtener todas las comidas
  db.query(query, (err, results) => {
    if (err) {
      return res.status(500).json({error: 'Error en la base de datos'})
    }
    res.status(200).json(results) // Retorna todas las comidas en formato JSON
  })
}

// Crear una nueva comida
const crearComida = (req, res) => {
  const {nombre, tipo, precio, stock, imagen} = req.body

  // Validación de los datos de entrada
  validacionDatosEntrada(req, res, req.body)

  const consulta = 'INSERT INTO Zero_Bugs_Comidas (nombre, tipo, precio, stock, imagen) VALUES (?, ?, ?, ?, ?)'
  const values = [nombre, tipo, precio, stock, imagen]

  db.query(consulta, values, (err, result) => {
    if (err) {
      return res.status(500).json({error: 'Error al crear la comida'})
    }
    res.status(201).json({id: result.insertId, nombre, tipo, precio, stock, imagen})
  })
}

// Actualizar una comida existente
const actualizarComida = (req, res) => {
  const {id} = req.params // Obtenemos el ID de la comida de los parámetros de la URL
  const {nombre, tipo, precio, stock, imagen} = req.body

  // Validación de los datos de entrada
  validacionDatosEntrada(req, res, req.body)

  const consulta = 'UPDATE Zero_Bugs_Comidas SET nombre = ?, tipo = ?, precio = ?, stock = ?, imagen = ? WHERE id = ?'

  // Los valores a actualizar, incluyendo el id al final
  const values = [nombre, tipo, precio, stock, imagen, id]

  db.query(consulta, values, (err, result) => {
    if (err) {
      return res.status(500).json({error: 'Error al actualizar la comida'})
    }

    if (result.affectedRows === 0) {
      return res.status(404).json({error: 'comida no encontrada'})
    }

    // Respuesta exitosa con los datos actualizados
    res.status(200).json({id, nombre, tipo, precio, stock, imagen})
  })
}

// Eliminar una comida
const eliminarComida = (req, res) => {
  const {id} = req.params

  const query = 'DELETE FROM Zero_Bugs_comidas WHERE id = ?'
  db.query(query, [id], (err, result) => {
    if (err) {
      return res.status(500).json({error: 'Error al eliminar la comida'})
    }

    if (result.affectedRows === 0) {
      return res.status(404).json({error: 'comida no encontrada'})
    }

    res.status(200).json({message: 'comida eliminada correctamente'})
  })
}

module.exports = {
  obtenerComidas,
  crearComida,
  actualizarComida,
  eliminarComida
}
