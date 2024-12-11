const express = require('express')
const app = express()
const router = require('./routes/index')
const PORT = 6240
const { connectDB } = require('./Connection/mongoConnection') // Import connectDB

app.use(express.json())
app.use('/api', router)

const iniciarServidor = async () => {
	try {
		await connectDB()

		app.listen(PORT, () => {
			console.log(`Servidor escuchando en http://localhost:${PORT}`)
		})
	} catch (err) {
		console.error('Error al conectar con la base de datos:', err)
	}
}

iniciarServidor()
