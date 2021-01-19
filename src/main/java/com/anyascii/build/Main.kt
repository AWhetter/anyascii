package com.anyascii.build

import com.anyascii.build.gen.generate
import com.ibm.icu.lang.UCharacter
import com.ibm.icu.lang.UCharacter.UnicodeBlock
import com.ibm.icu.lang.UCharacterCategory
import com.ibm.icu.lang.UScript
import java.nio.file.Path
import java.time.Duration
import java.time.Instant
import java.util.Locale
import kotlin.math.roundToInt

fun main() {
    val start = Instant.now()
    Locale.setDefault(Locale.ROOT)

    val table = Table(ASCII)
            .then(emojis())
            .then(custom())
            .normalize(NFKC)
            .normalize(NFKD)
            .then(unihan())
            .normalize(NFKC)
            .cased(ALL_CODE_POINTS)
            .then(integers())
            .transliterate()

    generate(table)

    table.minus(ASCII.keys).write("table.tsv")

    println(Duration.between(start, Instant.now()))
}

private fun integers() = Table().apply {
    for (cp in ALL_CODE_POINTS) {
        val n = UCharacter.getNumericValue(cp)
        if (n >= 0) {
            this[cp] = n.toString()
        }
    }
}

private fun custom() = Table()
        .then(combiningDiacriticalMarks())
        .then(variationSelectors())
        .then(halfwidthFullwidth())
        .then(Table("spacing-modifier-letters"))
        .then(Table("currency-symbols"))
        .then(Table("letterlike-symbols"))
        .then(Table("general-punctuation"))
        .then(Table("nko"))
        .then(Table("math-symbols"))
        .then(Table("kanbun"))
        .then(tags())
        .then(cjkMisc())
        .then(Table("kangxi-radicals"))
        .then(UnicodeBlock.CJK_STROKES.toTable { it.name.substringAfterLast(' ') })
        .then(yi())
        .then(vai())
        .then(ethiopic())
        .then(dominoes())
        .then(Table("ocr"))
        .then(Table("ol-chiki"))
        .then(cyrillic())
        .then(coptic())
        .then(Table("yijing"))
        .then(Table("box-drawing"))
        .then(UnicodeBlock.BLOCK_ELEMENTS.toTable { "#" })
        .then(Table("control-pictures"))
        .then(Table("bopomofo"))
        .then(hebrew())
        .then(cypriot())
        .then(braille())
        .then(Table("gothic"))
        .then(Table("lydian"))
        .then(Table("lycian"))
        .then(georgian())
        .then(armenian())
        .then(Table("thai"))
        .then(Table("number-forms"))
        .then(Table("latin-common"))
        .then(latin())
        .then(greek())
        .then(kana())
        .then(Table("lao"))
        .then(Table("runic"))
        .then(oldItalic())
        .then(Table("osmanya"))
        .then(deseret())
        .then(arabic())
        .then(Table("oriya"))
        .then(Table("bengali"))
        .then(Table("devanagari"))
        .then(Table("gurmukhi"))
        .then(Table("gujarati"))
        .then(Table("tamil"))
        .then(Table("telugu"))
        .then(Table("kannada"))
        .then(Table("malayalam"))
        .then(sinhala())
        .then(hangul())
        .then(Table("misc-symbols"))
        .then(Table("arrows"))
        .then(Table("misc-technical"))
        .then(Table("dingbats"))
        .then(Table("tifinagh"))
        .then(glagolitic())
        .then(baybayin())
        .then(khmer())
        .then(Table("ogham"))
        .then(Table("thaana"))
        .then(Table("myanmar"))
        .then(Table("phags-pa"))
        .then(ideographicDescription())
        .then(Table("modifier-tone-letters"))
        .then(Table("indic-number-forms"))
        .then(Table("ancient-symbols"))
        .then(Table("carian"))
        .then(Table("tai-xuan-jing"))
        .then(Table("misc-symbols-and-arrows"))
        .then(Table("old-arabian"))
        .then(Table("supplemental-punctuation"))
        .then(tibetan())
        .then(Table("geometric-shapes"))
        .then(Table("math-operators"))
        .then(canadianSyllabics())
        .then(meeteiMayek())
        .then(Table("buginese"))
        .then(Table("alchemical"))
        .then(Table("phoenician"))
        .then(linearAB())
        .then(Table("chess-symbols"))
        .then(Table("ornamental-dingbats"))
        .then(countingRodNumerals())
        .then(UnicodeBlock.SHORTHAND_FORMAT_CONTROLS.toTable { "" })
        .then(Table("miao"))
        .then(Table("makasar"))
        .then(Table("pau-cin-hau"))
        .then(Table("elbasan"))
        .then(Table("caucasian-albanian"))
        .then(Table("tai-le"))
        .then(Table("mongolian"))
        .then(bamum())
        .then(Table("syloti-nagri"))
        .then(Table("kayah-li"))
        .then(Table("rejang"))
        .then(Table("cham"))
        .then(Table("tai-viet"))
        .then(Table("javanese"))
        .then(Table("batak"))
        .then(Table("sundanese"))
        .then(cherokee())
        .then(Table("new-tai-lue"))
        .then(Table("saurashtra"))
        .then(syriac())
        .then(Table("limbu"))
        .then(Table("symbols-for-legacy-computing"))
        .then(Table("mandaic"))
        .then(Table("specials"))
        .then(Table("lisu"))
        .then(Table("mahjong-tiles"))
        .then(Table("playing-cards"))
        .then(Table("samaritan"))
        .then(Table("tai-tham"))
        .then(nushu())
        .then(adlam())
        .then(Table("sora-sompeng"))
        .then(osage())
        .then(medefaidrin())
        .then(egyptianHieroglyphs())
        .then(marchen())
        .then(Table("ugaritic"))
        .then(Table("shavian"))
        .then(warangCiti())
        .then(Table("dives-akuru"))
        .then(copticEpact())
        .then(Table("old-permic"))
        .then(Table("avestan"))
        .then(Table("kharoshthi"))
        .then(Table("multani"))
        .then(Table("mahajani"))
        .then(Table("sharada"))
        .then(Table("balinese"))
        .then(Table("lepcha"))
        .then(Table("old-persian"))
        .then(meroitic())
        .then(tirhuta())
        .then(modi())
        .then(takri())
        .then(dogra())
        .then(khudawadi())
        .then(nandinagari())
        .then(gunjalaGondi())
        .then(brahmi())
        .then(Table("pahawh-hmong"))
        .then(Table("vedic"))
        .then(manichaean())
        .then(Table("ottoman-siyaq-numbers"))
        .then(Table("indic-siyaq-numbers"))
        .then(mendeKikakui())
        .then(Table("wancho"))
        .then(Table("ideographic-symbols-and-punctuation"))
        .then(phaistosDisc())
        .then(Table("old-turkic"))

private fun cyrillic() = Table("cyrillic")
        .cased(codePoints(UScript.CYRILLIC))
        .then(codePoints(UScript.CYRILLIC).filterName { "COMBINING CYRILLIC LETTER" in it }.alias { it.replace("COMBINING CYRILLIC", "CYRILLIC SMALL") })

private fun coptic() = Table("coptic")
        .cased(codePoints(UScript.COPTIC))

private fun yi() = Table()
        .then(0xa015, "w")
        .then(UnicodeBlock.YI_SYLLABLES.toTable { it.name.substringAfterLast(' ').lower() })
        .then(UnicodeBlock.YI_RADICALS.toTable { it.name.substringAfterLast(' ') })

private fun vai() = Table("vai")
        .then(codePoints(UScript.VAI).filter { it.category == UCharacterCategory.OTHER_LETTER }.toTable { it.name.substringAfterLast(' ').lower() })

private fun dominoes() = (0x1f030..0x1f093).toTable {
    val name = it.name.removePrefix("DOMINO TILE ")
    if ("BACK" in name) return@toTable "-"
    return@toTable name.split('-').let { it[1].takeLast(1) + it[2].takeLast(1) }
}

private fun cypriot() = codePoints(UScript.CYPRIOT).toTable { it.name.substringAfterLast(' ').lower() }

private fun braille() = Table("braille")
        .then(codePoints(UScript.BRAILLE).toTable { "{${it.name.substringAfterLast('-')}}" })

private fun georgian() = Table("georgian")
        .then(codePoints(UScript.GEORGIAN).filterName { "SMALL LETTER" in it }.alias { it.remove("SMALL ") })
        .transliterate()
        .cased(codePoints(UScript.GEORGIAN))

private fun armenian() = Table("armenian")
        .cased(codePoints(UScript.ARMENIAN))

private fun latin() = Table("latin")
        .cased(codePoints(UScript.LATIN))

private fun kana() = Table("kana")
        .then(codePoints(UScript.HIRAGANA).filterName { it.startsWith("HIRAGANA LETTER SMALL") }.alias { it.remove("SMALL ") })
        .then(codePoints(UScript.KATAKANA).filterName { it.startsWith("KATAKANA LETTER SMALL") }.alias { it.remove("SMALL ") })
        .then(codePoints(UScript.HIRAGANA).filter { it.nameAlias.startsWith("HENTAIGANA") }.toTable { it.nameAlias.substringAfterLast(' ').substringBefore('-').lower() })

private fun deseret() = Table("deseret")
        .cased(codePoints(UScript.DESERET))

private fun oldItalic() = Table("old-italic")
        .then((0x10320..0x10323).toTable { ROMAN_NUMERALS.getValue(it.intValue) })

private fun sinhala() = Table("sinhala")

private fun cjkMisc() = Table("cjk-misc")
        .then((0x3021..0x3029).toTable { "${(it - 0x3021 + 1)}" }) // hangzhou numerals
        .then((0x3220..0x3229).toTable { "(${(it - 0x3220 + 1)})" }) // parenthesized numbers
        .then((0x3280..0x3289).toTable { "(${(it - 0x3280 + 1)})" }) // circled numbers
        .then((0x32c0..0x32cb).toTable { "${(it - 0x32c0 + 1)}M" }) // telegraph months
        .then((0x3358..0x3370).toTable { "${(it - 0x3358)}H" }) // telegraph hours
        .then((0x33e0..0x33fe).toTable { "${(it - 0x33e0 + 1)}D" }) // telegraph days

private fun glagolitic() = Table("glagolitic")
        .cased(codePoints(UScript.GLAGOLITIC))
        .then(codePoints(UScript.GLAGOLITIC).filterName { it.startsWith("COMBINING") }.alias { it.replace("COMBINING GLAGOLITIC LETTER", "GLAGOLITIC SMALL LETTER") })

private fun baybayin() = (0x1700..0x177f).filterDefined().toTable { cp ->
    // tagalog, hanunoo, buhid, tagbanwa
    val name = cp.name.lower()
    val last = name.substringAfterLast(' ')
    when {
        "virama" in name || "pamudpod" in name -> ""
        "single punctuation" in name -> "|"
        "double punctuation" in name -> "||"
        last.length == 1 -> last
        else -> last.dropLast(1)
    }
}

private fun ideographicDescription() = UnicodeBlock.IDEOGRAPHIC_DESCRIPTION_CHARACTERS.toTable { "+" }

private fun tibetan() = Table("tibetan")
        .then(codePoints(UScript.TIBETAN).filterName { "SUBJOINED LETTER" in it || "FIXED-FORM" in it }.alias { it.remove(" SUBJOINED").remove(" FIXED-FORM") })

private fun canadianSyllabics() = Table("canadian-syllabics")
        .then(codePoints(UScript.CANADIAN_ABORIGINAL).toTable { it.name.substringAfterLast(' ').lower() })

private fun halfwidthFullwidth() = UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS.alias {
    var name = it.substringAfter(' ')
    if ("VOICED SOUND MARK" in name) name = name.replace("KATAKANA", "KATAKANA-HIRAGANA")
    else if (name.startsWith("FORMS ")) name = name.replace("FORMS", "BOX DRAWINGS")
    name
}

private fun countingRodNumerals() = UnicodeBlock.COUNTING_ROD_NUMERALS.toTable { NUMBER_SPELLOUT.parse(it.name.substringAfterLast(' ').lower()).toString() }

private fun bamum() = Table("bamum")
        .then((0xa6a0..0xa6e5).toTable { it.name.substringAfterLast(' ').lower().capitalize() })

private fun cherokee() = codePoints(UScript.CHEROKEE).filter { it.isLower() }.toTable { it.name.substringAfterLast(' ').lower() }
        .cased(codePoints(UScript.CHEROKEE))

private fun syriac() = Table("syriac")
        .then(UnicodeBlock.SYRIAC_SUPPLEMENT.alias { it.replace("SYRIAC LETTER MALAYALAM", "MALAYALAM LETTER") })

private fun nushu() = Table().apply {
    forEachLine(Path.of("input/NushuSources.txt")) { line ->
        if (line.isEmpty() || line.startsWith('#')) return@forEachLine
        val split = line.split('\t', limit = 3)
        if (split[1] != "kReading") return@forEachLine
        val cp = split[0].drop(2).toInt(16)
        this[cp] = split[2]
    }
}

private fun adlam() = Table("adlam")
        .cased(codePoints(UScript.ADLAM))

private fun combiningDiacriticalMarks() = Table()
        .then((0x363..0x36f).toTable { it.name.substringAfterLast(' ').lower() })
        .then(UnicodeBlock.COMBINING_DIACRITICAL_MARKS.toTable { "" })
        .then(UnicodeBlock.COMBINING_DIACRITICAL_MARKS_EXTENDED.toTable { "" })
        .then(UnicodeBlock.COMBINING_DIACRITICAL_MARKS_SUPPLEMENT.toTable { "" })
        .then(UnicodeBlock.COMBINING_MARKS_FOR_SYMBOLS.toTable { "" })
        .then(UnicodeBlock.COMBINING_HALF_MARKS.toTable { "" })

private fun variationSelectors() = Table()
        .then(UnicodeBlock.VARIATION_SELECTORS.toTable { "" })
        .then(UnicodeBlock.VARIATION_SELECTORS_SUPPLEMENT.toTable { "" })

private fun tags() = Table()
        .then((0xe0020..0xe007e).toTable { (it - 0xe0000).asString() })
        .then(UnicodeBlock.TAGS.toTable { "" })

private fun osage() = Table("osage")
        .cased(codePoints(UScript.OSAGE))

private fun medefaidrin() = Table("medefaidrin")
        .cased(codePoints(UScript.MEDEFAIDRIN))

private fun khmer() = Table("khmer")

private fun marchen() = Table("marchen")
        .then(codePoints(UScript.MARCHEN).alias { it.replace("MARCHEN", "TIBETAN") })

private fun warangCiti() = Table("warang-citi")
        .cased(codePoints(UScript.WARANG_CITI))

private fun copticEpact() = Table().then(0x102e0, "k")

private fun hebrew() = Table("hebrew")
        .then((0x591..0x5af).toTable { "" })

private fun meroitic() = Table("meroitic")
        .then(UnicodeBlock.MEROITIC_CURSIVE.codePoints().filterName { "TWELFTH" in it }.toTable { it.floatValue.times(12).roundToInt().toString() })

private fun tirhuta() = codePoints(UScript.TIRHUTA).toTable {
    val name = it.name.replace("GVANG", "SIGN ANUSVARA")
    val cp = codePoint(name.replace("TIRHUTA", "DEVANAGARI")) ?: codePoint(name.replace("TIRHUTA", "BENGALI"))
    checkNotNull(cp).asString()
}

private fun modi() = Table("modi")
        .then(codePoints(UScript.MODI).alias { it.replace("MODI", "DEVANAGARI") })

private fun takri() = codePoints(UScript.TAKRI).alias { it.remove("ARCHAIC ").replace("TAKRI", "DEVANAGARI") }

private fun dogra() = codePoints(UScript.DOGRA).alias { it.replace("DOGRA", "DEVANAGARI") }

private fun khudawadi() = codePoints(UScript.KHUDAWADI).alias { it.replace("KHUDAWADI", "DEVANAGARI") }

private fun nandinagari() = codePoints(UScript.NANDINAGARI).alias { it.replace("NANDINAGARI", "DEVANAGARI") }

private fun gunjalaGondi() = codePoints(UScript.GUNJALA_GONDI).toTable {
    val name = it.name.replace("VIRAMA", "SIGN VIRAMA")
    val cp = codePoint(name.replace("GUNJALA GONDI", "TELUGU")) ?: codePoint(name.replace("GUNJALA GONDI", "DEVANAGARI"))
    checkNotNull(cp).asString()
}

private fun brahmi() = Table("brahmi")

private fun manichaean() = Table("manichaean")

private fun meeteiMayek() = Table("meetei-mayek")
        .then(codePoints(UScript.MEITEI_MAYEK).filterName { it.endsWith("LONSUM") }.alias { it.substringBeforeLast(' ') })

private fun mendeKikakui() = codePoints(UScript.MENDE).filter { it.category == UCharacterCategory.OTHER_LETTER }.toTable { it.name.substringAfterLast(' ').lower().replace("ee", "e").replace("oo", "o") }
        .then((0x1e8d0..0x1e8d6).toTable { "" })

private fun phaistosDisc() = Table()
        .then(0x101fd, ",")
        .then(UnicodeBlock.PHAISTOS_DISC.toTable { String.format("%02d", it - 0x101d0 + 1) })