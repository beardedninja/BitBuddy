package se.harrison.bitbuddy.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor(
  
  @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String = "",
  
  @field:Element(name = "link")
    @param:Element(name = "link")
    var link: String = "",
  
  @field:Element(name = "guid")
    @param:Element(name = "guid")
    var guid: String = "",
  
  @field:Element(name = "creator")
    @param:Element(name = "creator")
    @field:Namespace(prefix = "dc")
    @param:Namespace(prefix = "dc")
    var creator: String = "",
  
  @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    var description: String = "",
  
  @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    var pubDate: String = "",
  
  @field:Element(name = "updated")
    @param:Element(name = "updated")
    @field:Namespace(prefix = "atom")
    @param:Namespace(prefix = "atom")
    var updated: String = "",
  
  @field:ElementList(entry = "category", inline = true)
    @param:ElementList(entry = "category", inline = true)
    var categories: List<Category> = listOf(),
  
  @field:Element(name = "keywords", required = false)
    @param:Element(name = "keywords", required = false)
    @field:Namespace(prefix = "media")
    @param:Namespace(prefix = "media")
    var keywords: String = "",
  
  @field:Element(name = "content")
    @param:Element(name = "content")
    @field:Namespace(prefix = "media")
    @param:Namespace(prefix = "media")
    var content: MediaContent = MediaContent()
)