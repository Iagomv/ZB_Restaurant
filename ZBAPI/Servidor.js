const express = require('express')
const app = express()
const router = require('./routes/index')
const PORT = 6240
const {MongoClient, ServerApiVersion, ObjectId} = require('mongodb')

app.use(express.json())
app.use('/api', router)

app.listen(PORT, () => {
  console.log(`Servidor escuchando en http://localhost:${PORT}`)
})
