const mysql = require('mysql2')
DB_HOST = 'localhost'
const DB_PORT = 3306
DB_USER = 'dam2'
DB_PASSWORD = 'Ka3b0134679'
DB_NAME = 'karbo_imartinez'
// Crear una conexión a la base de datos
const db = mysql.createConnection({
  host: DB_HOST,
  port: DB_PORT,
  user: DB_USER,
  password: DB_PASSWORD,
  database: DB_NAME
})

// Conectar a la base de datos y manejar errores
db.connect((err) => {
  if (err) {
    console.error('Error de conexión a la base de datos:', err.stack)
    return
  }
  console.log('Conexión a la base de datos exitosa')
})

// Exportar la conexión para usarla en otros archivos
module.exports = db
