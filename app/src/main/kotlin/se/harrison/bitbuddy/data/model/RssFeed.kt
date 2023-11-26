package se.harrison.bitbuddy.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root


@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(
  
  @field:Element(name = "channel")
  @param:Element(name = "channel")
  var channel: Channel = Channel()
)