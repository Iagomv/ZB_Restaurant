class Pedido {
	constructor(mesa, cliente, camarero, entrante, comida, postre, bebida, estado, precio) {
		this.mesa = mesa
		this.cliente = cliente
		this.camarero = camarero
		this.entrante = entrante
		this.comida = comida
		this.postre = postre
		this.bebida = bebida
		this.estado = estado
		this.precio = precio
	}
}

// Ejemplo de uso:
const menu = [
	new Plato('Tartar de atún', 'Entrante', 10.5, 2),
	new Plato('Carne de Wagyu', 'Primero', 20.5, 1),
	new Plato('Bocaditos de naranja y albahaca', 'Postre', 10.5, 0),
]

const vino = new Vino('Pingus', 'Vino', 1289.9)

const pedido = new Pedido(1, 25, 3, menu, vino)

console.log(pedido)
