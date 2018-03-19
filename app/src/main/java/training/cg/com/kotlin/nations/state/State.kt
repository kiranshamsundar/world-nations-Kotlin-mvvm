package training.cg.com.kotlin.nations.state

interface State<T> {

    fun <U> convert(converter: (State<T>) -> State<U>): State<U> = converter(this)

    fun value(): State<T> = this
}