package com.backend.commitoclock.infrastructure.gateway

import org.apache.batik.anim.dom.SAXSVGDocumentFactory
import org.apache.batik.util.XMLResourceDescriptor
import org.springframework.stereotype.Component
import java.net.URI
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

@Component
class CommitGateway {
    fun fetchCommitData(
        githubId: String,
        date: String
    ): Int {
        val uri = URI("https://ghchart.rshah.org/$githubId")
        val svgStream = uri.toURL().openStream()
        val parser = XMLResourceDescriptor.getXMLParserClassName()
        val factory = SAXSVGDocumentFactory(parser)
        val document = factory.createDocument(uri.toString(), svgStream)
        val xPath: XPath = XPathFactory.newInstance().newXPath()
        val expression = "//*[@data-date='$date']/@data-score"
        val result = xPath.evaluate(expression, document, XPathConstants.STRING) as String
        return result.toIntOrNull() ?: 0
    }
}
