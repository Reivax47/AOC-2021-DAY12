fun main() {
    data class cave(val name:String, var mes_liens: MutableList<String>, val small_cave:Boolean)
    fun recursive_2( from:cave,  parcourt: MutableList<cave>, tableau : MutableList<cave>):Int {
        var reponse = 0
        parcourt.add(from)
        if (from.name == "end") {
            parcourt.removeAt(parcourt.size-1)
            return 1
        }
        from.mes_liens.filter { it != "start" }.forEach { un_lien ->
            val par_ici = tableau.filter { it.name ==  un_lien}[0]
            val small_cave_already_visited_twice = parcourt.filter { it.small_cave }.distinct() != parcourt.filter { it.small_cave }
            if ((par_ici.small_cave && small_cave_already_visited_twice
                && parcourt.filter { it == par_ici }.isEmpty()) || (par_ici.small_cave && !small_cave_already_visited_twice)

            ) {

                reponse += recursive_2(par_ici, parcourt, tableau)

            } else if (!par_ici.small_cave) {
                reponse += recursive_2(par_ici, parcourt, tableau)
            }
        }
        parcourt.removeAt(parcourt.size-1)
        return reponse
    }
    fun recursive( from:cave,  parcourt: MutableList<cave>, tableau : MutableList<cave>):Int {
        var reponse = 0
        parcourt.add(from)
        if (from.name == "end") {
            parcourt.removeAt(parcourt.size-1)
            return 1
        }
        from.mes_liens.forEach { un_lien ->
            val par_ici = tableau.filter { it.name ==  un_lien}[0]
            if (par_ici.small_cave && parcourt.filter { it == par_ici }.isEmpty()) {
                reponse += recursive(par_ici, parcourt, tableau)
            } else if (!par_ici.small_cave) {
                reponse += recursive(par_ici, parcourt, tableau)
            }
        }
        parcourt.removeAt(parcourt.size-1)
        return reponse
    }
    fun part1(input: List<String>): Int {
        var reponse = 0
        var laby = mutableListOf<cave>()

        input.forEach {
            val two_cave = it.split('-')
            for (i in 0 ..1) {
                if (laby.find { it.name == two_cave[i] } == null) {

                    var lien = mutableListOf<String>()
                    val small = two_cave[i] == two_cave[i].lowercase()
                    laby.add(cave(name = two_cave[i], mes_liens = lien, small_cave = small))
                }
            }
            val la_une = laby.find { it.name == two_cave[1] }
            val la_deux = laby.find { it.name == two_cave[0] }

            if (la_deux != null) {
                la_une?.mes_liens?.add(la_deux.name)
            }
            if (la_une != null) {
                la_deux?.mes_liens?.add(la_une.name)
            }
        }

        val depart = laby.filter { it.name == "start" }[0]
        var le_trajet = mutableListOf<cave>()

        return recursive(depart, le_trajet, laby)
    }


    fun part2(input: List<String>): Int {
        var reponse = 0
        var laby = mutableListOf<cave>()

        input.forEach {
            val two_cave = it.split('-')
            for (i in 0 ..1) {
                if (laby.find { it.name == two_cave[i] } == null) {

                    var lien = mutableListOf<String>()
                    val small = two_cave[i] == two_cave[i].lowercase()
                    laby.add(cave(name = two_cave[i], mes_liens = lien, small_cave = small))
                }
            }
            val la_une = laby.find { it.name == two_cave[1] }
            val la_deux = laby.find { it.name == two_cave[0] }

            if (la_deux != null) {
                la_une?.mes_liens?.add(la_deux.name)
            }
            if (la_une != null) {
                la_deux?.mes_liens?.add(la_une.name)
            }
        }

        val depart = laby.filter { it.name == "start" }[0]
        var le_trajet = mutableListOf<cave>()

        return recursive_2(depart, le_trajet, laby)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
