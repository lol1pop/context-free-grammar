import gui.MainForm
import tornadofx.*

class MainApp : App(MainForm::class)

data class DataGrammatics(
    val nonterminalSymbols : String,
    val terminalSymbols: String,
    val originSymbol: String,
    val rulesSymbol: Map<Char,List<String>>
)

class GeneratedSequence(grammatics: DataGrammatics) {

    private val nonterminals: String = grammatics.nonterminalSymbols
    private val terminals: String = grammatics.terminalSymbols
    private val selectedOriginSymbol = grammatics.originSymbol
    private val rules = grammatics.rulesSymbol

    val nonterminalsArray =  nonterminals.toCharArray()
    val terminalsArray =  terminals.toCharArray()
    private var listGeneratedString = listOf<String>()

    private fun isNonterminal(char: Char): Boolean = nonterminalsArray.contains(char)
    private fun isTerminal(char: Char): Boolean =  terminalsArray.contains(char)
    private fun detectInfiniteRecursion(generatedSequence: String, maxLen: Int): Boolean {
        val currentNonterminal  = generatedSequence.count { isNonterminal(it) }
        if(currentNonterminal > maxLen) return true
        return false
    }

    private fun generated(sequence: String, minLen: Int, maxLen: Int) {
        val currentTerminals = sequence.count { isTerminal(it) }
        if (currentTerminals > maxLen) return
        val indexNonterminal = sequence.indexOfAny(nonterminalsArray)
        if(indexNonterminal < 0) {
            if(sequence.length >= minLen){
                this.listGeneratedString = this.listGeneratedString + sequence
            }
            return
        }
        val nonteriman = sequence[indexNonterminal]
        for ( rule in rules[nonteriman]!!){
            val replaceString = sequence.replaceFirst(nonteriman.toString(),rule)
            if(detectInfiniteRecursion(replaceString, maxLen)) continue
            generated(replaceString, minLen, maxLen)
        }
    }

    fun start (minLen: Int, maxLen: Int): List<String> {
        val originSymbol = selectedOriginSymbol
        generated(originSymbol, minLen, maxLen)
        return this.listGeneratedString
    }

}

fun main(args: Array<String>) {
    //Application.launch(MainApp::class.java, *args)

}

