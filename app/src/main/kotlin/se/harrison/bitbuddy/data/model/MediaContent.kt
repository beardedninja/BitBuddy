package se.harrison.bitbuddy.data.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "content", strict = false)
data class MediaContent @JvmOverloads constructor(

    @field:Attribute(name = "url")
    @param:Attribute(name = "url")
    var url: String = "",

    @field:Attribute(name = "type")
    @param:Attribute(name = "type")
    var type: String = "",

    @field:Attribute(name = "height")
    @param:Attribute(name = "height")
    var height: Int = 0,

    @field:Attribute(name = "width")
    @param:Attribute(name = "width")
    var width: Int = 0,

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    var description: String = ""
)