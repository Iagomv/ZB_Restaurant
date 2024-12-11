const uri = 'mongodb://localhost:27017' // Definimos la dirección de nuestra base de datos MongoDB (en este caso está en nuestro ordenador)
const client = new MongoClient(uri, {
  // Creamos una conexión con MongoDB
  serverApi: {
    version: ServerApiVersion.v1, // Usamos la versión 1 de la API de MongoDB
    strict: true, // Aseguramos que la conexión sea estricta
    deprecationErrors: true // Mostramos errores si algo está obsoleto
  }
})

// Exportar la conexión para usarla en otros archivos
module.exports = client
