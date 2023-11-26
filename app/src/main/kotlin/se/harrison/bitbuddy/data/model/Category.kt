package se.harrison.bitbuddy.data.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "category", strict = false)
data class Category @JvmOverloads constructor(

    @field:Attribute(name = "domain", required = false)
    @param:Attribute(name = "domain", required = false)
    var domain: String = "",

    @field:Text(required = false)
    @param:Text(required = false)
    var value: String = ""
)