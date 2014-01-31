<?xml version="1.0" encoding="UTF-8"?>
<!-- ======================================================================
     === XML Stylesheet for conversion of tokenization provided as lists
     ===   of char positions (as in protein_concentration_testsuite.xsd)
     ===   into list of meaningful tokens (strings).
     ===   Core file used by other XSLTs.
     === copyright: EPFL 2011
     === created by: Jean-Cédric Chappelier
     === creation date: November 16th, 2011
     === version: $Revision: 58 $ $Date: 2011-11-29 18:00:59 +0100 (Tue, 29 Nov 2011) $
     ====================================================================== -->
<xsl:stylesheet version="2.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- ======================================================================
     For tokenizations: simply change root element name.
     ====================================================================== -->
<xsl:template match="tokenization">
  <tokenizedContent>
    <xsl:apply-templates/>
  </tokenizedContent>
</xsl:template>

<!-- ======================================================================
     For tokens (of tokenizations): extract the corresponding content.
     Pay attention to the special case where 'to' element is not provided.
     ====================================================================== -->
<xsl:template match="tokenization/token">
  <token id="{@id}">
    <xsl:choose>
      <xsl:when test="to">
        <xsl:value-of select="substring(../../rawContent, number(from)+1, number(to) - number(from))" />
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="substring(../../rawContent, number(from)+1, 1)" />
      </xsl:otherwise>
    </xsl:choose>
  </token>
</xsl:template>

</xsl:stylesheet>
