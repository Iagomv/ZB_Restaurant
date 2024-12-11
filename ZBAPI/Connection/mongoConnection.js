const { MongoClient, ServerApiVersion } = require('mongodb')
const uri = 'mongodb://localhost:27017' // URL of your MongoDB server

const client = new MongoClient(uri, {
	serverApi: {
		version: ServerApiVersion.v1,
		strict: true,
		deprecationErrors: true,
	},
})

// Function to connect to the database
const connectDB = async () => {
	try {
		// Connect to MongoDB server
		await client.connect()
		console.log('Conexión a la base de datos exitosa')
	} catch (err) {
		console.error('Error al conectar con la base de datos:', err)
		throw err // Rethrow error to be caught by the caller
	}
}

// Exporting the client and the connectDB function
module.exports = { client, connectDB }
