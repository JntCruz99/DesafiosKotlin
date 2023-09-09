class User(val id: Int, val name: String)

/* 
 * No Kotlin, a declaração de um objeto (por meio da palavra-chave object) 
 * é uma maneira concisa e eficaz de implementar o padrão Singleton.
 */
object UserManager {

    private val users = mutableListOf<User>()

    fun addUser(name: String) {
        //TODO: Inplementar a lógica de adicionar um novo Usuário na lista "users".
        //Dica: Utilize o tamanho da lista "users" para gerar seus IDs.
        val user = User(users.size + 1, name)
        users.add(user)
    }

    fun listUsers() {
        //TODO: Inplementar a impressão/listagem dos "users", seguindo o padrão definido no enunciado.
        users.forEachIndexed{ index, elemento ->
            var i = index + 1
            println("$i-${elemento.name}")
        }
    }
}


fun main(args: Array<String>) {
    val quantity = readLine()?.toIntOrNull() ?: 0
    for (i in 1..quantity) {
        val name = readLine() ?: ""
        UserManager.addUser(name)
    }
    UserManager.listUsers()
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class Product(val name: String, val price: Double, val quantity: Int)

class CustomOrder private constructor(
    val customerName: String,
    val products: List<Product>,
    val total: Double,
    val deliveryAddress: String
) {
    class Builder {
        private var customerName: String = ""
        private var products: MutableList<Product> = mutableListOf()
        private var deliveryAddress: String = ""

        fun setCustomerName(name: String) = apply { customerName = name }
        fun addProduct(product: Product) = apply { products.add(product) }
        fun setDeliveryAddress(address: String) = apply { deliveryAddress = address }

        fun build(): CustomOrder {
            //TODO: Implemente a lógica para calcular o Total do Pedido (a partir dos dados de Produtos).
            var total: Double = 0.0
            for (pruduto in products){
                total += (pruduto.price * pruduto.quantity)
            }
            //TODO: Instancie corretamente um CustomOrder, consolidando o Builder!
            return CustomOrder(customerName, products, total, deliveryAddress)
        }
    }

    fun printReceipt() {
        println("${this.customerName}")
        this.products.forEachIndexed { index, product ->
            println("${index + 1}. ${product.name} | ${product.price} | ${product.quantity}")
        }
        println("Total: ${this.total}")
        println("End: ${this.deliveryAddress}")
    }
}

fun main() {
    val customerName = readLine() ?: ""

    val orderBuilder = CustomOrder.Builder().setCustomerName(customerName)

    val numProducts = readLine()?.toIntOrNull() ?: 0
    for (i in 1..numProducts) {
        val productName = readLine() ?: ""
        val productPrice = readLine()?.toDoubleOrNull() ?: 0.0
        val productQuantity = readLine()?.toIntOrNull() ?: 0

        orderBuilder.addProduct(Product(productName, productPrice, productQuantity))
    }

    val deliveryAddress = readLine() ?: ""

    val customOrder = orderBuilder.setDeliveryAddress(deliveryAddress).build()

    customOrder.printReceipt()
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////

// Antiga classe de conversão que só suporta a conversão de USD para GBP
class OldCurrencyConverter {
    fun convertUSDtoGBP(amount: Double): Double {
        return amount * 0.80 // 80% do valor em USD
    }
}

// Novo adaptador que usa a antiga conversão e aplica a taxa adicional de GBP para EUR
class CurrencyAdapter(private val oldConverter: OldCurrencyConverter) {
    fun convertUSDtoEUR(amount: Double): Double {
        //TODO: Implementar a lógica de adaptação usando o "oldConverter" e taxa definida no enunciado.
        return amount * 0.85
    }
}

fun main() {
    val input = readLine()?.toDoubleOrNull() ?: return
    val oldConverter = OldCurrencyConverter()
    val adapter = CurrencyAdapter(oldConverter)

    val amountInEUR = adapter.convertUSDtoEUR(input)

    println("USD: $input")
    println("EUR: $amountInEUR")
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import java.util.*

fun main() {
    val titulo = readLine() ?: ""
    val autor = readLine() ?: ""

    // Aqui usamos a Extension Function "generateSlug()" nas Strings de Entrada.
    val slugTitulo = titulo.generateSlug()
    val slugAutor = autor.generateSlug()

    println("Slug gerado para o livro:")
    println("$slugTitulo")
    print("_$slugAutor")
}

fun String.generateSlug(): String {

    val trimmed = this.trim()

    val slug = trimmed.replace(Regex("\\s+"), "-")

    val normalizedSlug = slug.replace(Regex("[^a-zA-Z0-9-]"), "").lowercase(Locale.getDefault())

    return normalizedSlug
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
fun main() {
    val urls = mutableListOf<String>()

    while (true) {
        val input = readLine() ?: break
        if (input.isBlank()) break
        urls.add(input)
    }

    println("Iniciando downloads...")

    // Cria uma lista de Pair (indice, tamanho)
    val results = mutableListOf<Pair<Int, Int>>()

    val threads = urls.mapIndexed { index, url ->
        Thread {
            val length = openLink(url)
            synchronized(results) {
                results.add(Pair(index, length))
            }
        }
    }

    // TODO: Inicie cada Thread para começar o processo de "download" paralelamente.
    threads.forEach { it.start() }
    // TODO: Aguarde até que todas as Threads terminem suas respectivas execuções.
    threads.forEach { it.join() }

    // Sort results by index to print in the correct order
    results.sortedBy { it.first }.forEachIndexed { idx, result ->
        println("Arq${idx + 1}: ${result.second}")
    }

    println("Tempo total: ${urls.size}")
}

fun openLink(url: String): Int {
    return url.length
}


