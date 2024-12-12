// controller/pedidosController.js
const { client } = require('../Connection/mongoConnection') // Import the MongoClient

const BD = 'karbo_iMartinez'
const cPedidos = client.db(BD).collection('Pedidos') // Use the client to access the database and collection

// Obtener todos los pedidos
const obtenerPedidos = async (req, res) => {
	try {
		const pedidos = await cPedidos.find().toArray()
		res.status(200).json(pedidos)
	} catch (error) {
		res.status(500).json({ mensaje: 'Error al obtener los pedidos', error })
	}
}

// Obtener un pedido por ID
const obtenerPedidoPorId = async (req, res) => {
	try {
		const { id } = req.params
		const pedido = await cPedidos.findOne({ _id: new client.ObjectId(id) })
		if (!pedido) {
			return res.status(404).json({ mensaje: 'Pedido no encontrado' })
		}
		res.status(200).json(pedido)
	} catch (error) {
		res.status(500).json({ mensaje: 'Error al obtener el pedido', error })
	}
}

// Insertar un nuevo pedido
const insertarPedido = async (req, res) => {
	try {
		const nuevoPedido = req.body
		const resultado = await cPedidos.insertOne(nuevoPedido)
		res.status(201).json(resultado.insertedId)
	} catch (error) {
		res.status(500).json({ mensaje: 'Error al insertar el pedido', error })
	}
}

// Actualizar un pedido existente
const actualizarEstadoPedido = async (req, res) => {
	try {
		const { id } = req.params // ID del pedido
		const { estado } = req.body // Nuevo estado

		const estadosValidos = ['pendiente', 'entrante', 'primero', 'postre', 'cancelado']
		if (!estadosValidos.includes(estado)) {
			return res.status(400).json({ mensaje: 'Estado inválido' })
		}

		const resultado = await cPedidos.updateOne({ _id: new client.ObjectId(id) }, { $set: { estado } })

		if (resultado.matchedCount === 0) {
			return res.status(404).json({ mensaje: 'Pedido no encontrado' })
		}

		res.status(200).json({ mensaje: 'Estado del pedido actualizado correctamente' })
	} catch (error) {
		res.status(500).json({ mensaje: 'Error al actualizar el estado del pedido', error })
	}
}

module.exports = {
	obtenerPedidos,
	obtenerPedidoPorId,
	insertarPedido,
	actualizarEstadoPedido,
}
