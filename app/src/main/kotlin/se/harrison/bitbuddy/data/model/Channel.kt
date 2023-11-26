package se.harrison.bitbuddy.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(
  
  @field:Element(name = "title")
  @param:Element(name = "title")
  var title: String = "",
  
  @field:Element(name = "description")
  @param:Element(name = "description")
  var description: String = "",
  
  @field:ElementList(entry = "item", inline = true)
  @param:ElementList(entry = "item", inline = true)
  var items: List<Item> = mutableListOf()
)