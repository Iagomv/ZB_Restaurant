const mysql = require('mysql2')
DB_HOST = 'localhost'
const DB_PORT = 3306
DB_USER = 'root'
DB_PASSWORD = ''
DB_NAME = 'karbo_ivarela'

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
